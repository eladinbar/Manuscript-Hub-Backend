package com.manuscript.rest.mapping.response;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.rest.forms.response.AnnotationResponse;
import com.manuscript.rest.mapping.IRestMapper;
import org.springframework.stereotype.Service;

@Service
public class AnnotationResponseMapperImpl implements IRestMapper<AnnotationModel, AnnotationResponse> {
    @Override
    public AnnotationResponse modelToRest(AnnotationModel annotationModel) {
        return AnnotationResponse.builder()
                .id(annotationModel.getId())
                .uid(annotationModel.getUid())
                .imageDataId(annotationModel.getImageDataId())
                .algorithmId(annotationModel.getAlgorithmId())
                .content(annotationModel.getContent())
                .startX(annotationModel.getStartX())
                .startY(annotationModel.getStartY())
                .endX(annotationModel.getEndX())
                .endY(annotationModel.getEndY())
                .createdTime(annotationModel.getCreatedTime())
                .updatedTime(annotationModel.getUpdatedTime())
                .build();
    }

    @Override
    public AnnotationModel restToModel(AnnotationResponse annotationResponse) {
        return AnnotationModel.builder()
                .id(annotationResponse.getId())
                .uid(annotationResponse.getUid())
                .imageDataId(annotationResponse.getImageDataId())
                .algorithmId(annotationResponse.getAlgorithmId())
                .content(annotationResponse.getContent())
                .startX(annotationResponse.getStartX())
                .startY(annotationResponse.getStartY())
                .endX(annotationResponse.getEndX())
                .endY(annotationResponse.getEndY())
                .createdTime(annotationResponse.getCreatedTime())
                .updatedTime(annotationResponse.getUpdatedTime())
                .build();
    }
}
