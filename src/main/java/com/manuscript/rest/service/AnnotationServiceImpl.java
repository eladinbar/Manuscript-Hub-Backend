package com.manuscript.rest.service;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.core.exceptions.NoAnnotationFoundException;
import com.manuscript.core.exceptions.UnauthorizedException;
import com.manuscript.core.usecase.custom.annotation.ICreateAnnotation;
import com.manuscript.core.usecase.custom.annotation.IDeleteByIdAnnotation;
import com.manuscript.core.usecase.custom.annotation.IGetByIdAnnotation;
import com.manuscript.core.usecase.custom.annotation.IUpdateAnnotation;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.request.AnnotationRequest;
import com.manuscript.rest.response.AnnotationResponse;
import com.manuscript.rest.response.ImageResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AnnotationServiceImpl implements IAnnotationService {
    private final UUID NIL = UUID.fromString("00000000-0000-0000-0000-000000000000");
    // Annotation service is expected to use image service for its various function needs
    // (e.g. get or verify existence of a specific image)
    private final IImageService imageService;
    private final IAlgorithmService algorithmService;
    private final IRestMapper<AnnotationModel, AnnotationRequest> annotationRequestMapper;
    private final IRestMapper<AnnotationModel, AnnotationResponse> annotationResponseMapper;
    private final ICreateAnnotation createAnnotationUseCase;
    private final IGetByIdAnnotation getByIdAnnotationUseCase;
    private final IUpdateAnnotation updateAnnotationUseCase;
    private final IDeleteByIdAnnotation deleteByIdAnnotationUseCase;

    @Override
    public AnnotationResponse create(AnnotationRequest annotationRequest) {
        verifyImagePermission(annotationRequest.getImageId(), annotationRequest.getUserId());
        checkAlgorithmAvailability(annotationRequest.getAlgorithmId());
        //TODO ensure coordinates are within document bounds
        AnnotationModel annotationModel = annotationRequestMapper.restToModel(annotationRequest);
        annotationModel = createAnnotationUseCase.create(annotationModel);
        return annotationResponseMapper.modelToRest(annotationModel);
    }

    @Override
    public AnnotationResponse update(AnnotationRequest annotationRequest) {
        verifyImagePermission(annotationRequest.getImageId(), annotationRequest.getUserId());
        checkAlgorithmAvailability(annotationRequest.getAlgorithmId());
        //TODO ensure coordinates are within document bounds
        AnnotationModel annotationModel = annotationRequestMapper.restToModel(annotationRequest);
        annotationModel = updateAnnotationUseCase.update(annotationModel);
        return annotationResponseMapper.modelToRest(annotationModel);
    }

    @Override
    public AnnotationResponse get(AnnotationRequest annotationRequest) {
        verifyImagePermission(annotationRequest.getImageId(), annotationRequest.getUserId());
        Optional<AnnotationModel> optionalAnnotation = getByIdAnnotationUseCase.getById(annotationRequest.getAnnotationId());
        if(optionalAnnotation.isPresent()) {
            AnnotationModel annotationModel = optionalAnnotation.get();
            return annotationResponseMapper.modelToRest(annotationModel);
        }
        throw new NoAnnotationFoundException();
    }

    @Override
    public void delete(AnnotationRequest annotationRequest) {
        verifyImagePermission(annotationRequest.getImageId(), annotationRequest.getUserId());
        AnnotationModel annotationModel = annotationRequestMapper.restToModel(annotationRequest);
        deleteByIdAnnotationUseCase.deleteById(annotationModel);
    }

    @Override
    public void deleteAllByDocumentId(UUID documentId) {
        throw new RuntimeException("Unimplemented");
    }

    private void verifyImagePermission(UUID imageId, UUID userId) {
        //TODO when workspace sharing is added, permission verification needs to be modified
        ImageResponse image = imageService.getById(imageId);
        if(!image.getUserId().equals(userId))
            throw new UnauthorizedException();
    }

    private void checkAlgorithmAvailability(UUID algorithmId) {
        if(algorithmId.equals(NIL))
            return;
        // If no algorithm is found, an exception is thrown
        algorithmService.get(algorithmId);
    }
}
