package com.manuscript.rest.service;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.core.exceptions.NoAlgorithmFoundException;
import com.manuscript.core.exceptions.UnauthorizedException;
import com.manuscript.core.usecase.custom.algorithm.*;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.request.AlgorithmRequest;
import com.manuscript.rest.response.AlgorithmResponse;
import com.manuscript.rest.response.ImageResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private final IGetAllAlgorithms getAllAlgorithmsUseCase;
    private final IDeleteByIdAlgorithm deleteByIdAlgorithmUseCase;
    private final IDeleteByUrlAlgorithm deleteByUrlAlgorithmUseCase;

    @Override
    public void run(AlgorithmRequest algorithmRequest) {
        verifyImagePermission(algorithmRequest.getImageId(), algorithmRequest.getUid());
        Optional<AlgorithmModel> optionalModel = getByIdAlgorithmUseCase.getById(algorithmRequest.getId());
        if(optionalModel.isPresent()) {
            AlgorithmModel algorithmModel = optionalModel.get();
            //TODO establish connection with algorithm via algorithmModel.url
            //TODO place results within a variable
            //TODO call 'addAnnotation' or 'updateAnnotation' accordingly
        }
        throw new NoAlgorithmFoundException();
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
        algorithmModel = updateAlgorithmUseCase.update(algorithmModel);
        return algorithmResponseMapper.modelToRest(algorithmModel);
    }

    @Override
    public AlgorithmResponse getById(UUID algorithmId) {
        Optional<AlgorithmModel> optionalModel = getByIdAlgorithmUseCase.getById(algorithmId);
        if(optionalModel.isPresent()) {
            AlgorithmModel algorithmModel = optionalModel.get();
            return algorithmResponseMapper.modelToRest(algorithmModel);
        }
        throw new NoAlgorithmFoundException();
    }

    @Override
    public AlgorithmResponse getByUrl(String url) {
        Optional<AlgorithmModel> optionalModel = getByUrlAlgorithmUseCase.getByUrl(url);
        if(optionalModel.isPresent()) {
            AlgorithmModel algorithmModel = optionalModel.get();
            return algorithmResponseMapper.modelToRest(algorithmModel);
        }
        throw new NoAlgorithmFoundException();
    }

    @Override
    public List<AlgorithmResponse> getAll() {
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
    public void deleteAllByUserId(UUID userId) {
        throw new RuntimeException("Unimplemented");
    }

    private void verifyUserDeveloperRole(String uid) {
        if(userService.getByUid(uid).getRole().equals(Role.User))
            throw new UnauthorizedException("User does not have sufficient authorization to upload an algorithm");
    }

    private boolean algorithmExists(UUID algorithmId) {
        try {
            // If no algorithm is found, an exception is thrown
            getByIdAlgorithmUseCase.getById(algorithmId);
        } catch(Exception e) {
            return false;
        }
        return true;
    }

    private void checkAlgorithmAvailability(UUID algorithmId) {
        // If no algorithm is found, an exception is thrown
        getByIdAlgorithmUseCase.getById(algorithmId);
    }

    private void verifyAlgorithmAuthorization(String uid, UUID algorithmId, String url) {
        Optional<AlgorithmModel> optionalAlgorithm = Optional.empty();
        if(algorithmId != null)
            optionalAlgorithm = getByIdAlgorithmUseCase.getById(algorithmId);
        if(!optionalAlgorithm.isPresent())
            optionalAlgorithm = getByUrlAlgorithmUseCase.getByUrl(url);
        if(optionalAlgorithm.isPresent()) {
            AlgorithmModel algorithmModel = optionalAlgorithm.get();
            if (userService.getByUid(uid).getRole().equals(Role.Admin) || algorithmModel.getUid().equals(uid))
                return;
            throw new UnauthorizedException("User has no authorization to modify this algorithm.");
        }
        throw new NoAlgorithmFoundException("No algorithm with the given ID or URL exists.");
    }

    private void verifyAlgorithmAuthorization(String uid, UUID algorithmId) {
        Optional<AlgorithmModel> optionalAlgorithm = getByIdAlgorithmUseCase.getById(algorithmId);
        if(optionalAlgorithm.isPresent()) {
            AlgorithmModel algorithmModel = optionalAlgorithm.get();
            if (userService.getByUid(uid).getRole().equals(Role.Admin) || algorithmModel.getUid().equals(uid))
                return;
            throw new UnauthorizedException("User has no authorization to modify this algorithm.");
        }
        throw new NoAlgorithmFoundException("No algorithm with the given ID exists.");
    }

    private void verifyAlgorithmAuthorization(String uid, String url) {
        Optional<AlgorithmModel> optionalAlgorithm = getByUrlAlgorithmUseCase.getByUrl(url);
        if(optionalAlgorithm.isPresent()) {
            AlgorithmModel algorithmModel = optionalAlgorithm.get();
            if (userService.getByUid(uid).getRole().equals(Role.Admin) || algorithmModel.getUid().equals(uid))
                return;
            throw new UnauthorizedException("User has no authorization to modify this algorithm.");
        }
        throw new NoAlgorithmFoundException("No algorithm with the given URL exists.");
    }

    private void checkIfAlgorithmExists(String url) {
        if(getByUrlAlgorithmUseCase.getByUrl(url).isPresent())
            throw new IllegalArgumentException("An algorithm with the given URL already exists.");
    }

    private void verifyImagePermission(UUID imageId, String uid) {
        //TODO when workspace sharing is added, permission verification needs to be modified
        ImageResponse image = imageService.getById(imageId);
        if(!image.getUid().equals(uid))
            throw new UnauthorizedException();
    }
}
