package com.manuscript.rest.service;

import com.google.api.client.json.Json;
import com.google.gson.JsonObject;
import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.core.domain.common.enums.AlgorithmStatus;
import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.core.exceptions.NoAlgorithmFoundException;
import com.manuscript.core.exceptions.UnauthorizedException;
import com.manuscript.core.usecase.custom.algorithm.*;
import com.manuscript.rest.forms.response.ImageDataResponse;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.forms.request.AlgorithmRequest;
import com.manuscript.rest.forms.response.AlgorithmResponse;
import com.manuscript.rest.forms.response.ImageInfoResponse;
import com.manuscript.rest.forms.response.UserResponse;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

@AllArgsConstructor
@Service
public class AlgorithmServiceImpl implements IAlgorithmService {
    // Algorithm service is expected to use user service to ensure sufficient developer role
    private final IUserService userService;
    private final IImageService imageService;
    //    private final IAnnotationService annotationService;
    private final IRestMapper<AlgorithmModel, AlgorithmRequest> algorithmRequestMapper;
    private final IRestMapper<AlgorithmModel, AlgorithmResponse> algorithmResponseMapper;
    private final ICreateAlgorithm createAlgorithmUseCase;
    private final IUpdateAlgorithm updateAlgorithmUseCase;
    private final IGetByIdAlgorithm getByIdAlgorithmUseCase;
    private final IGetByUrlAlgorithm getByUrlAlgorithmUseCase;
    private final IGetAllByUidAlgorithms getAllByUidAlgorithmsUseCase;
    private final IGetAllByUidRunnableAlgorithms getAllByUidRunnableAlgorithmsUseCase;
    private final IGetAllByAlgorithmStatusesAlgorithms getAllByAlgorithmStatusesAlgorithmsUseCase;
    private final IGetAllAlgorithms getAllAlgorithmsUseCase;
    private final IDeleteByIdAlgorithm deleteByIdAlgorithmUseCase;
    private final IDeleteByUrlAlgorithm deleteByUrlAlgorithmUseCase;
    private final String defaultRepoPath = "C:\\Users\\Public\\Repositories";

    @Override
    public void run(AlgorithmRequest algorithmRequest) throws Exception {
        verifyImagePermission(algorithmRequest.getImageDataId(), algorithmRequest.getUid());
        Optional<AlgorithmModel> optionalModel = getByIdAlgorithmUseCase.getById(algorithmRequest.getId());
        if (optionalModel.isPresent()) {
            /** get relevant information from the request **/
            AlgorithmModel algorithmModel = optionalModel.get();
            String repoUrl = algorithmModel.getUrl();
            String repoName = repoUrl.substring(repoUrl.lastIndexOf('/') + 1);
            Path repoPath = Paths.get(defaultRepoPath + "\\" + repoName);
            String userEmail = userService.getByUid(algorithmRequest.getUid()).getEmail();
            String algorithmInput = makeDataJsonString(algorithmRequest.getImageDataId(),algorithmRequest.getUid());
            makeAlgorithmDockerfile(repoPath, algorithmInput, userEmail);     //create dockerfile
            buildDockerImage(repoPath, repoName);           //build docker
            runDockerContainer(repoName);                   //run docker
            //algorithm should print its json into "output.txt" file
        }
        throw new NoAlgorithmFoundException("No algorithm found.");
    }

    @Override
    public AlgorithmResponse create(AlgorithmRequest algorithmRequest) {
        verifyUserDeveloperRole(algorithmRequest.getUid());
        checkIfAlgorithmExists(algorithmRequest.getUrl());
        AlgorithmModel algorithmModel = algorithmRequestMapper.restToModel(algorithmRequest);
        algorithmModel = createAlgorithmUseCase.create(algorithmModel);
        return algorithmResponseMapper.modelToRest(algorithmModel);
    }

    @Override
    public AlgorithmResponse update(AlgorithmRequest algorithmRequest) {
        verifyUserDeveloperRole(algorithmRequest.getUid());
        verifyAlgorithmAuthorization(algorithmRequest.getUid(), algorithmRequest.getId(), algorithmRequest.getUrl());
        AlgorithmModel algorithmModel = algorithmRequestMapper.restToModel(algorithmRequest);
        AlgorithmModel oldModel = getByIdAlgorithmUseCase.getById(algorithmModel.getId()).get();
        algorithmModel = updateAlgorithmUseCase.update(algorithmModel);
        if (algorithmModel.getStatus() == AlgorithmStatus.Trial && oldModel.getStatus() == AlgorithmStatus.CloudStaging) {
            cloneRepo(algorithmModel.getUrl());
        }
        return algorithmResponseMapper.modelToRest(algorithmModel);
    }

    @Override
    public AlgorithmResponse getById(UUID algorithmId) {
        Optional<AlgorithmModel> optionalModel = getByIdAlgorithmUseCase.getById(algorithmId);
        if (optionalModel.isPresent()) {
            AlgorithmModel algorithmModel = optionalModel.get();
            return algorithmResponseMapper.modelToRest(algorithmModel);
        }
        throw new NoAlgorithmFoundException();
    }

    @Override
    public AlgorithmResponse getByUrl(String url) {
        Optional<AlgorithmModel> optionalModel = getByUrlAlgorithmUseCase.getByUrl(url);
        if (optionalModel.isPresent()) {
            AlgorithmModel algorithmModel = optionalModel.get();
            return algorithmResponseMapper.modelToRest(algorithmModel);
        }
        throw new NoAlgorithmFoundException();
    }

    @Override
    public List<AlgorithmResponse> getAllByUid(String uid) {
        verifyUserDeveloperRole(uid);
        return getAllByUidAlgorithmsUseCase.getAllByUid(uid).stream().map(algorithmResponseMapper::modelToRest).collect(Collectors.toList());
    }

    @Override
    public List<AlgorithmResponse> getAllRunnable(String uid){
        return getAllByUidRunnableAlgorithmsUseCase.getAllRunnable(uid).stream().map(algorithmResponseMapper::modelToRest).collect(Collectors.toList());
    }

    @Override
    public List<AlgorithmResponse> getAllByAlgorithmStatuses(Set<AlgorithmStatus> algorithmStatuses, String uid) {
        verifyAlgorithmAcquisitionAuthorization(algorithmStatuses, uid);
        return getAllByAlgorithmStatusesAlgorithmsUseCase.getAllByAlgorithmStatuses(algorithmStatuses).stream().map(algorithmResponseMapper::modelToRest).collect(Collectors.toList());
    }

    @Override
    public List<AlgorithmResponse> getAll(String AdminUid) {
        verifyUserAdminRole(AdminUid);
        return getAllAlgorithmsUseCase.getAll().stream().map(algorithmResponseMapper::modelToRest).collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id, String uid) {
        verifyUserDeveloperRole(uid);
        verifyAlgorithmAuthorization(uid, id);
        deleteByIdAlgorithmUseCase.deleteById(id);
    }

    @Override
    public void deleteByUrl(String url, String uid) {
        verifyUserDeveloperRole(uid);
        verifyAlgorithmAuthorization(uid, url);
        deleteByUrlAlgorithmUseCase.deleteByUrl(url);
    }

    @Override
    public void deleteAllByUid(String uid, String adminUid) {
        throw new RuntimeException("Unimplemented");
    }

    private void verifyUserDeveloperRole(String uid) {
        if (userService.getByUid(uid).getRole().equals(Role.User))
            throw new UnauthorizedException("User does not have sufficient authorization to upload an algorithm.");
    }

    private void verifyUserAdminRole(String uid) {
        if (!userService.getByUid(uid).getRole().equals(Role.Admin))
            throw new UnauthorizedException("User does not have permission to perform this operation.");
    }

    private boolean algorithmExists(UUID algorithmId) {
        try {
            // If no algorithm is found, an exception is thrown
            getByIdAlgorithmUseCase.getById(algorithmId);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void checkAlgorithmAvailability(UUID algorithmId) {
        // If no algorithm is found, an exception is thrown
        getByIdAlgorithmUseCase.getById(algorithmId);
    }

    private void verifyAlgorithmAcquisitionAuthorization(Set<AlgorithmStatus> statuses, String uid) {
        // Check if all algorithms requested are those in production or in trial
        if (statuses.stream()
                .allMatch(element -> element == AlgorithmStatus.Trial || element == AlgorithmStatus.Production)) {
            return;
        }
        // If not, verify that the user requesting these algorithms is an admin
        UserResponse user = userService.getByUid(uid);
        if (!user.getRole().equals(Role.Admin))
            if (!user.getRole().equals(Role.Developer) || !user.getUid().equals(uid))
                throw new UnauthorizedException("User has no authorization to get algorithms with the given statuses.");
    }

    private void verifyAlgorithmAuthorization(String uid, UUID algorithmId, String url) {
        Optional<AlgorithmModel> optionalAlgorithm = Optional.empty();
        if (algorithmId != null)
            optionalAlgorithm = getByIdAlgorithmUseCase.getById(algorithmId);
        if (!optionalAlgorithm.isPresent())
            optionalAlgorithm = getByUrlAlgorithmUseCase.getByUrl(url);
        if (optionalAlgorithm.isPresent()) {
            AlgorithmModel algorithmModel = optionalAlgorithm.get();
            if (userService.getByUid(uid).getRole().equals(Role.Admin) || algorithmModel.getUid().equals(uid))
                return;
            throw new UnauthorizedException("User has no authorization to modify this algorithm.");
        }
        throw new NoAlgorithmFoundException("No algorithm with the given ID or URL exists.");
    }

    private void verifyAlgorithmAuthorization(String uid, UUID algorithmId) {
        Optional<AlgorithmModel> optionalAlgorithm = getByIdAlgorithmUseCase.getById(algorithmId);
        if (optionalAlgorithm.isPresent()) {
            AlgorithmModel algorithmModel = optionalAlgorithm.get();
            if (userService.getByUid(uid).getRole().equals(Role.Admin) || algorithmModel.getUid().equals(uid))
                return;
            throw new UnauthorizedException("User has no authorization to modify this algorithm.");
        }
        throw new NoAlgorithmFoundException("No algorithm with the given ID exists.");
    }

    private void verifyAlgorithmAuthorization(String uid, String url) {
        Optional<AlgorithmModel> optionalAlgorithm = getByUrlAlgorithmUseCase.getByUrl(url);
        if (optionalAlgorithm.isPresent()) {
            AlgorithmModel algorithmModel = optionalAlgorithm.get();
            if (userService.getByUid(uid).getRole().equals(Role.Admin) || algorithmModel.getUid().equals(uid))
                return;
            throw new UnauthorizedException("User has no authorization to modify this algorithm.");
        }
        throw new NoAlgorithmFoundException("No algorithm with the given URL exists.");
    }

    private void checkIfAlgorithmExists(String url) {
        if (getByUrlAlgorithmUseCase.getByUrl(url).isPresent())
            throw new IllegalArgumentException("An algorithm with the given URL already exists.");
    }

    private void verifyImagePermission(UUID imageId, String uid) {
        //TODO when workspace sharing is added, permission verification needs to be modified
        ImageInfoResponse image = imageService.getByIdInfo(imageId, uid);
        if (!image.getUid().equals(uid))
            throw new UnauthorizedException();
    }

    /** Docker related functions:**/

    private void cloneRepo(String url) {
        try {
            String repoName = url.substring(url.lastIndexOf('/') + 1);
            Path repoPath = Paths.get(defaultRepoPath + "\\" + repoName);
            clearOldRepo(repoPath.toFile());
            repoPath = Files.createDirectory(repoPath);
            Git.cloneRepository()
                    .setURI(url + ".git")
                    .setDirectory(repoPath.toFile())
                    .call();
        }
        catch (IOException | GitAPIException e) {
            throw new NoAlgorithmFoundException("unable to clone repository");
        }
    }
    private boolean clearOldRepo(File fileToDelete){
        File[] contents = fileToDelete.listFiles();
        if (contents != null) {
            for (File file : contents) {
                clearOldRepo(file);
            }
        }
        return fileToDelete.delete();
    }

    private String makeDataJsonString(UUID imageDataId, String uid){
        ImageDataResponse imageDataResponse = imageService.getByIdData(imageDataId,uid);
        byte[] data = imageDataResponse.getData();
        String stringData = Base64.getEncoder().encodeToString(data);
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("DATA", stringData);
        String stringRequest =  jsonRequest.toString();
        return stringRequest;
    }
    private void makeAlgorithmDockerfile(Path repoPath, String algorithmInput, String userEmail) {
        try {
            File dockerfile = new File(repoPath.toString(), "Dockerfile");
            FileWriter fileWriter = new FileWriter(dockerfile);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write("FROM python:3.9\n");
            writer.write("WORKDIR /app/" + userEmail + "\n");
            writer.write("COPY . /app/" + userEmail + "\n");
            writer.write("RUN pip install\n");
            writer.write("CMD echo \"" + algorithmInput + "\" | python myCode.py\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void buildDockerImage(Path repoPath, String repoName) throws Exception {
        try {
            File workingDirectory = new File(repoPath.toString());
            String[] cmd = {"docker", "build", "-t", repoName, "."};
            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            processBuilder.directory(workingDirectory);
            Process process = processBuilder.start();
            process.waitFor();
            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stderrReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new Exception("docker build failed");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void runDockerContainer(String repoName) throws Exception{
        try {
            String[] cmd = {"docker", "run", repoName};
            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            Process process = processBuilder.start();
            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stderrReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new Exception("docker run failed");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}
