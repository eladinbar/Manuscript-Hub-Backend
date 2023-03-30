package com.manuscript.rest.service;

import com.manuscript.rest.request.AnnotationRequest;
import com.manuscript.rest.response.AnnotationResponse;

import java.util.UUID;

public interface IAnnotationService {
    AnnotationResponse create(AnnotationRequest annotationRequest);

    AnnotationResponse update(AnnotationRequest annotationRequest);

    AnnotationResponse get(AnnotationRequest annotationRequest);

    void delete(AnnotationRequest annotationRequest);

    void deleteAllByDocumentId(UUID documentId);
}
