package com.manuscript.rest.service;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.core.usecase.custom.annotation.ICreateAnnotation;
import com.manuscript.core.usecase.custom.annotation.IGetByIdAnnotation;
import com.manuscript.core.usecase.custom.annotation.IUpdateAnnotation;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.request.AnnotationRequest;
import com.manuscript.rest.response.AnnotationResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AnnotationServiceImpl implements IAnnotationService {
    // Annotation service is expected to use image service for its various function needs
    // (e.g. get or verify existence of a specific image)
    private final IImageService imageService;
    private final IRestMapper<AnnotationModel, AnnotationRequest> annotationRequestMapper;
    private final IRestMapper<AnnotationModel, AnnotationResponse> annotationResponseMapper;
    private final ICreateAnnotation createAnnotationUseCase;
    private final IGetByIdAnnotation getByIdAnnotationUseCase;
    private final IUpdateAnnotation updateAnnotationUseCase;
}
