package com.manuscript.rest.mapping;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.rest.response.AnnotationResponse;
import org.springframework.stereotype.Service;

@Service
public class AnnotationResponseMapperImpl implements IRestMapper<AnnotationModel, AnnotationResponse> {
    @Override
    public AnnotationResponse modelToRest(AnnotationModel annotationModel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public AnnotationModel restToModel(AnnotationResponse annotationResponse) {
        throw new UnsupportedOperationException();
    }
}
