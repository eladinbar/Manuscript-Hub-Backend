package com.manuscript.rest.service;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.core.exceptions.NoAnnotationFoundException;
import com.manuscript.core.usecase.custom.annotation.*;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.forms.request.AnnotationRequest;
import com.manuscript.rest.forms.response.AnnotationResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class AnnotationServiceImpl implements IAnnotationService {
    private final IRestMapper<AnnotationModel, AnnotationRequest> annotationRequestMapper;
    private final IRestMapper<AnnotationModel, AnnotationResponse> annotationResponseMapper;
    private final ICreateAnnotation createAnnotationUseCase;
    private final IUpdateAnnotation updateAnnotationUseCase;
    private final IGetByIdAnnotation getByIdAnnotationUseCase;
    private final IGetAllByImageIdAnnotations getAllByImageIdAnnotationsUseCase;
    private final IDeleteByIdAnnotation deleteByIdAnnotationUseCase;
    private final IDeleteAllByImageDataIdAnnotations deleteAllByImageDataIdAnnotationsUseCase;

    @Override
    public AnnotationResponse create(AnnotationRequest annotationRequest) {
        AnnotationModel annotationModel = annotationRequestMapper.restToModel(annotationRequest);
        annotationModel = createAnnotationUseCase.create(annotationModel);
        return annotationResponseMapper.modelToRest(annotationModel);
    }

    @Override
    public AnnotationResponse update(AnnotationRequest annotationRequest) {
        AnnotationModel annotationModel = annotationRequestMapper.restToModel(annotationRequest);
        annotationModel = updateAnnotationUseCase.update(annotationModel);
        return annotationResponseMapper.modelToRest(annotationModel);
    }

    //Currently not in use
    @Override
    public AnnotationResponse getById(AnnotationRequest annotationRequest) {
        Optional<AnnotationModel> optionalAnnotation = getByIdAnnotationUseCase.getById(annotationRequest.getId());
        if(optionalAnnotation.isPresent()) {
            AnnotationModel annotationModel = optionalAnnotation.get();
            return annotationResponseMapper.modelToRest(annotationModel);
        }
        throw new NoAnnotationFoundException("No annotation with the given ID was found.");
    }

    @Override
    public List<AnnotationResponse> getAllByImageDataId(UUID imageDataId, String uid) {
        List<AnnotationModel> annotationModels = getAllByImageIdAnnotationsUseCase.getAllByImageId(imageDataId);
        List<AnnotationResponse> annotationResponses = new ArrayList<>();
        for(AnnotationModel annotationModel : annotationModels) {
            AnnotationResponse annotationResponse = annotationResponseMapper.modelToRest(annotationModel);
            annotationResponses.add(annotationResponse);
        }
        return annotationResponses;
    }

    @Override
    public void deleteById(UUID annotationId) {
        deleteByIdAnnotationUseCase.deleteById(annotationId);
    }

    @Override
    public void deleteAllByImageDataId(UUID imageDataId) {
        deleteAllByImageDataIdAnnotationsUseCase.deleteAllByImageDataId(imageDataId);
    }
}
