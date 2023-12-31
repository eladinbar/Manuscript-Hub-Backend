package com.manuscript.rest.service;

import com.manuscript.rest.forms.request.AnnotationRequest;
import com.manuscript.rest.forms.response.AnnotationResponse;

import java.util.List;
import java.util.UUID;

public interface IAnnotationService {
    AnnotationResponse getById(AnnotationRequest annotationRequest);
    List<AnnotationResponse> getAllByImageDataId(UUID imageDataId, String uid);
    void deleteAllByImageDataId(UUID imageDataId);
}
