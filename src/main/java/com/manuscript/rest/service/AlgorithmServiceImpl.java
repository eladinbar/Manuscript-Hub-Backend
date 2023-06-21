package com.manuscript.rest.service;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.core.domain.common.enums.AlgorithmStatus;
import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.core.exceptions.NoAlgorithmFoundException;
import com.manuscript.core.exceptions.UnauthorizedException;
import com.manuscript.core.usecase.custom.algorithm.*;
import com.manuscript.core.usecase.custom.annotation.ICreateAnnotation;
import com.manuscript.core.usecase.custom.annotation.IDeleteByIdAnnotation;
import com.manuscript.core.usecase.custom.annotation.IUpdateAnnotation;
import com.manuscript.rest.forms.request.AnnotationRequest;
import com.manuscript.rest.forms.response.*;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.forms.request.AlgorithmRequest;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import javax.imageio.ImageIO;

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


    /** Annotation section **/
    private final IRestMapper<AnnotationModel, AnnotationRequest> annotationRequestMapper;
    private final IRestMapper<AnnotationModel, AnnotationResponse> annotationResponseMapper;
    private final ICreateAnnotation createAnnotationUseCase;
    private final IUpdateAnnotation updateAnnotationUseCase;
    private final IDeleteByIdAnnotation deleteByIdAnnotationUseCase;


    @Override
    public List<AnnotationResponse> run(AlgorithmRequest algorithmRequest) throws Exception {
        verifyImageModificationPermission(algorithmRequest.getImageDataId(), algorithmRequest.getUid());
        Optional<AlgorithmModel> optionalModel = getByIdAlgorithmUseCase.getById(algorithmRequest.getId());
        if (optionalModel.isPresent()) {
            /** get relevant information from the request **/
            AlgorithmModel algorithmModel = optionalModel.get();
            String repoUrl = algorithmModel.getUrl();
            String repoName = repoUrl.substring(repoUrl.lastIndexOf('/') + 1);
            Path repoPath = Paths.get(defaultRepoPath + "\\" + repoName);
            String userEmail = userService.getByUid(algorithmRequest.getUid()).getEmail();
            BufferedImage algorithmInput = makeInputImage(algorithmRequest.getImageDataId(), algorithmRequest.getUid());
            Path ioPath = makeInputOutputDirectory(repoPath, userEmail, algorithmInput);
            String dockerImageName = getDockerImageName(repoName);      //build docker
            runDockerContainer(dockerImageName, userEmail, ioPath);//run docker
            List<JSONObject> output = readOutput(ioPath);
            List<AnnotationResponse> annotationResponsesList = convertJsonToAnnotation(output, algorithmRequest);
            clearDir(ioPath.toFile());//delete io folder
            return annotationResponsesList;
        } else {
            throw new NoAlgorithmFoundException("No algorithm found.");
        }
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
    public AlgorithmResponse update(AlgorithmRequest algorithmRequest) throws Exception {
        verifyUserDeveloperRole(algorithmRequest.getUid());
        verifyAlgorithmAuthorization(algorithmRequest.getUid(), algorithmRequest.getId(), algorithmRequest.getUrl());
        AlgorithmModel algorithmModel = algorithmRequestMapper.restToModel(algorithmRequest);
        AlgorithmModel oldModel = getByUrlAlgorithmUseCase.getByUrl(algorithmModel.getUrl()).get();
        if (algorithmModel.getStatus() == AlgorithmStatus.CloudStaging && oldModel.getStatus() == AlgorithmStatus.Approved) {
            initDockerImageBuildProcess(algorithmModel.getUrl());
        } else if (algorithmModel.getStatus() == AlgorithmStatus.Declined && oldModel.getStatus() != AlgorithmStatus.Declined) {
            String repoUrl = algorithmModel.getUrl();
            String repoName = repoUrl.substring(repoUrl.lastIndexOf('/') + 1);
            Path repoPath = Paths.get(defaultRepoPath + "\\" + repoName);
            deleteDockerImage(repoPath, repoName);
            clearDir(repoPath.toFile());
        }
        algorithmModel = updateAlgorithmUseCase.update(algorithmModel);
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
    public List<AlgorithmResponse> getAllRunnable(String uid) {
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

    private void verifyImageModificationPermission(UUID imageDataId, String uid) {
        ImageDataResponse imageData = imageService.getByIdData(imageDataId, uid);
        ImageInfoResponse imageInfo = imageService.getByIdInfo(imageData.getInfoId(), uid);
        if (imageInfo.getPrivacy() == Privacy.Shared) {
            if (!imageInfo.getUid().equals(uid) && !imageInfo.getSharedUserIds().contains(uid))
                throw new UnauthorizedException("User is not authorized to make modifications to this image.");
        } else if (!imageInfo.getUid().equals(uid))
            throw new UnauthorizedException("User is not authorized to make modifications to this image.");
    }


    /** Docker related functions: **/
    private Path cloneRepo(String url) {
        try {
            String repoName = url.substring(url.lastIndexOf('/') + 1);
            Path repoPath = Paths.get(defaultRepoPath + "\\" + repoName);
            //clearDir(repoPath.toFile());
            repoPath = Files.createDirectory(repoPath);
            Git.cloneRepository()
                    .setURI(url + ".git")
                    .setDirectory(repoPath.toFile())
                    .call().close();
            return repoPath;
        } catch (IOException | GitAPIException e) {
            throw new NoAlgorithmFoundException("Unable to clone repository.");
        }
    }

    private boolean clearDir(File fileToDelete) {
        File[] contents = fileToDelete.listFiles();
        if (contents != null) {
            for (File file : contents) {
                clearDir(file);
            }
        }
        return fileToDelete.delete();
    }

    private void initDockerImageBuildProcess(String url) throws Exception {
        Path repoPath = cloneRepo(url);
        makeAlgorithmDockerfile(repoPath);
        String repoName = url.substring(url.lastIndexOf('/') + 1);
        buildDockerImage(repoPath, repoName);
    }

    private void makeAlgorithmDockerfile(Path repoPath) {
        try {
            File dockerfile = new File(repoPath.toString(), "Dockerfile");
            FileWriter fileWriter = new FileWriter(dockerfile);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write("FROM python:3.10\n\n");
            writer.write("RUN apt-get update && apt-get install -y tesseract-ocr\n");
            writer.write("RUN apt-get update && apt-get install -y libtesseract-dev\n");
            writer.write("RUN rm -rf /var/lib/apt/lists/*\n\n");
            writer.write("WORKDIR /app\n\n");
            writer.write("COPY . /app\n\n");
            writer.write("RUN pip install -r requirements.txt\n\n");
            //TODO placeholder for main function of the code
            writer.write("ENTRYPOINT [\"python\", \"main.py\"]");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getDockerImageName(String repoName) {
        String dockerImageName = repoName.toLowerCase().replace('@', '-').replace('.', '-');
        return dockerImageName;
    }

    private void buildDockerImage(Path cmdRunLocation, String repoName) throws Exception {
        File workingDirectory = new File(cmdRunLocation.toString());
        String dockerImageName = getDockerImageName(repoName);
        String[] cmd = {"docker", "build", "-t", dockerImageName, "."};
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            processBuilder.directory(workingDirectory);
            Process process = processBuilder.start();

            String outputStream;
            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((outputStream = stdoutReader.readLine()) != null) {
                System.out.println(outputStream);
            }
            BufferedReader stderrReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((outputStream = stderrReader.readLine()) != null) {
                System.out.println(outputStream);
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new Exception("Docker build failed.");
            }
        } catch (IOException | InterruptedException e) {
            throw new Exception("Docker build failed.");
        }
    }

    private void deleteDockerImage(Path cmdRunLocation, String repoName) throws Exception {
        File workingDirectory = new File(cmdRunLocation.toString());
        String dockerImageName = getDockerImageName(repoName);
        String[] cmd = {"docker", "rmi", dockerImageName};
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            processBuilder.directory(workingDirectory);
            Process process = processBuilder.start();

            String outputStream;
            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((outputStream = stdoutReader.readLine()) != null) {
                System.out.println(outputStream);
            }
            BufferedReader stderrReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((outputStream = stderrReader.readLine()) != null) {
                System.out.println(outputStream);
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new Exception("Docker delete failed.");
            }
        } catch (IOException | InterruptedException e) {
            throw new Exception("Docker delete failed.");
        }
    }

    private BufferedImage makeInputImage(UUID imageDataId, String uid) throws Exception {
        try {
            ImageDataResponse imageDataResponse = imageService.getByIdData(imageDataId, uid);
            byte[] data = imageDataResponse.getData();
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(data));
            //String stringData = Base64.getEncoder().encodeToString(data);
            //JSONObject jsonRequest = new JSONObject();
            //jsonRequest.put("DATA", stringData);
            //String stringRequest =  jsonRequest.toString();
            return image;
        } catch (IOException e) {
            throw new Exception("image creation failed");
        }
    }

    private Path makeInputOutputDirectory(Path repoPath, String userEmail, BufferedImage algorithmInput) {
        Path userDirPath = Paths.get(repoPath.toString() + "\\" + userEmail);
        try {
            //make image input file
            userDirPath = Files.createDirectory(userDirPath);
            FileOutputStream fos = new FileOutputStream(userDirPath.toString() + "\\input.png");
            ImageIO.write(algorithmInput, "png", fos);
            //make empty output file
            File outputFile = new File(userDirPath.toString(), "output.txt");
            FileWriter fileWriter = new FileWriter(outputFile);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write("");
            writer.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userDirPath;
    }

    public void runDockerContainer(String dockerImageName, String userEmail, Path IOPath) throws Exception {
        try {
            String containerName = userEmail.toLowerCase().replace('@', '-').replace('.', '-');
            String[] cmd = {"docker", "run",
                    "--name", containerName,
                    "--mount", "type=bind,source=" + IOPath.toString() + ",target=/app/" + userEmail,
                    "--rm",
                    dockerImageName, "/app/" + userEmail};
            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            Process process = processBuilder.start();

            String outputStream;
            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((outputStream = stdoutReader.readLine()) != null) {
                System.out.println(outputStream);
            }
            BufferedReader stderrReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((outputStream = stderrReader.readLine()) != null) {
                System.out.println(outputStream);
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new Exception("Docker run failed.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<JSONObject> readOutput(Path ioPath) throws Exception {
        try {
            Path outputFilePath = ioPath.resolve("output.txt");
            byte[] bytes = Files.readAllBytes(outputFilePath);
            String outputData = new String(bytes, StandardCharsets.UTF_8);
            JSONObject temp = new JSONObject(outputData);
            JSONArray annotationJsonArray = temp.getJSONArray("data");
            List<JSONObject> annotationJsons = new ArrayList<>();
            for (int i = 0; i < annotationJsonArray.length(); i++) {
                if (annotationJsonArray.get(i) instanceof JSONObject) {
                    annotationJsons.add(annotationJsonArray.getJSONObject(i));
                }
            }
            return annotationJsons;
        } catch (IOException e) {
            throw new Exception("failed to write");
        }
    }

    private List<AnnotationResponse> convertJsonToAnnotation(List<JSONObject> jsons, AlgorithmRequest algorithmRequest) {
        List<AnnotationResponse> annotationResponsesList = new ArrayList<>();
        for (JSONObject json : jsons) {
            AnnotationRequest annotationModel = AnnotationRequest.builder()
                    .uid(algorithmRequest.getUid())
                    .imageDataId(algorithmRequest.getImageDataId())
                    .algorithmId(algorithmRequest.getId())
                    .content(json.getString("content"))
                    .startX(json.getInt("startX"))
                    .startY(json.getInt("startY"))
                    .endX(json.getInt("endX"))
                    .endY(json.getInt("endY"))
                    .build();
            AnnotationResponse annotationResponse = createAnnotation(annotationModel);
            annotationResponsesList.add(annotationResponse);
        }
        return annotationResponsesList;
    }

    private void clearIO(Path ioPath) {

    }


    /** Annotation section **/
    @Override
    public AnnotationResponse createAnnotation(AnnotationRequest annotationRequest) {
        verifyImageModificationPermission(annotationRequest.getImageDataId(), annotationRequest.getUid());
        AnnotationModel annotationModel = annotationRequestMapper.restToModel(annotationRequest);
        annotationModel = createAnnotationUseCase.create(annotationModel);
        return annotationResponseMapper.modelToRest(annotationModel);
    }

    @Override
    public AnnotationResponse updateAnnotation(AnnotationRequest annotationRequest) {
        verifyImageModificationPermission(annotationRequest.getImageDataId(), annotationRequest.getUid());
        AnnotationModel annotationModel = annotationRequestMapper.restToModel(annotationRequest);
        annotationModel = updateAnnotationUseCase.update(annotationModel);
        return annotationResponseMapper.modelToRest(annotationModel);
    }

    @Override
    public void deleteByIdAnnotation(UUID annotationId, String uid, UUID imageDataId) {
        verifyImageModificationPermission(imageDataId, uid);
        deleteByIdAnnotationUseCase.deleteById(annotationId);
    }
}
