package com.manuscript.rest.service;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.exceptions.NoAnnotationFoundException;
import com.manuscript.core.exceptions.UnauthorizedException;
import com.manuscript.core.usecase.custom.annotation.*;
import com.manuscript.rest.forms.response.ImageDataResponse;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.forms.request.AnnotationRequest;
import com.manuscript.rest.forms.response.AnnotationResponse;
import com.manuscript.rest.forms.response.ImageInfoResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

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
    private final IGetAllByImageIdAnnotations getAllByImageIdAnnotationsUseCase;
    private final IUpdateAnnotation updateAnnotationUseCase;
    private final IDeleteByIdAnnotation deleteByIdAnnotationUseCase;

    @Override
    public AnnotationResponse create(AnnotationRequest annotationRequest) {
        verifyImagePermission(annotationRequest.getImageDataId(), annotationRequest.getUid());
        checkAlgorithmAvailability(annotationRequest.getAlgorithmId());
        AnnotationModel annotationModel = annotationRequestMapper.restToModel(annotationRequest);
        annotationModel = createAnnotationUseCase.create(annotationModel);
        return annotationResponseMapper.modelToRest(annotationModel);
    }

    @Override
    public AnnotationResponse update(AnnotationRequest annotationRequest) {
        verifyImagePermission(annotationRequest.getImageDataId(), annotationRequest.getUid());
        checkAlgorithmAvailability(annotationRequest.getAlgorithmId());
        AnnotationModel annotationModel = annotationRequestMapper.restToModel(annotationRequest);
        annotationModel = updateAnnotationUseCase.update(annotationModel);
        return annotationResponseMapper.modelToRest(annotationModel);
    }

    //Currently not in use
    @Override
    public AnnotationResponse get(AnnotationRequest annotationRequest) {
        verifyImagePermission(annotationRequest.getImageDataId(), annotationRequest.getUid());
        Optional<AnnotationModel> optionalAnnotation = getByIdAnnotationUseCase.getById(annotationRequest.getId());
        if(optionalAnnotation.isPresent()) {
            AnnotationModel annotationModel = optionalAnnotation.get();
            return annotationResponseMapper.modelToRest(annotationModel);
        }
        throw new NoAnnotationFoundException();
    }

    @Override
    public List<AnnotationResponse> getAllByImageId(UUID imageDataId, String uid) {
        verifyImagePermission(imageDataId, uid);
        List<AnnotationModel> annotationModels = getAllByImageIdAnnotationsUseCase.getAllByImageId(imageDataId);
        List<AnnotationResponse> annotationResponses = new ArrayList<>();
        for(AnnotationModel annotationModel : annotationModels) {
            AnnotationResponse annotationResponse = annotationResponseMapper.modelToRest(annotationModel);
            annotationResponses.add(annotationResponse);
        }
        return annotationResponses;
    }

    @Override
    public void delete(UUID annotationId, UUID documentId, String uid) {
        verifyImagePermission(documentId, uid);
        deleteByIdAnnotationUseCase.deleteById(annotationId);
    }

    @Override
    public void deleteAllByDocumentId(UUID documentId, String uid) {
        throw new RuntimeException("Unimplemented");
    }

    private void verifyImagePermission(UUID imageDataId, String uid) {
        //TODO when workspace sharing is added, permission verification needs to be modified
        ImageDataResponse imageDataResponse = imageService.getByIdData(imageDataId, uid);
        ImageInfoResponse imageInfo = imageService.getByIdInfo(imageDataResponse.getInfoId(), uid);
        if(!imageInfo.getUid().equals(uid) || imageInfo.getStatus().equals(Status.Disabled))
            throw new UnauthorizedException();
    }

    private void checkAlgorithmAvailability(UUID algorithmId) {
        if(algorithmId.equals(NIL))
            return;
        // If no algorithm is found, an exception is thrown
        algorithmService.getById(algorithmId);
    }
}
