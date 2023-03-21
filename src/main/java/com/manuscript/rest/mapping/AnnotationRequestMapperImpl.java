package com.manuscript.rest.mapping;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.rest.request.AnnotationRequest;
import org.springframework.stereotype.Service;

@Service
public class AnnotationRequestMapperImpl implements IRestMapper<AnnotationModel, AnnotationRequest> {
    @Override
    public AnnotationRequest modelToRest(AnnotationModel annotationModel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public AnnotationModel restToModel(AnnotationRequest annotationRequest) {
        throw new UnsupportedOperationException();
    }
}
