package com.manuscript.rest.mapping;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.rest.response.AnnotationResponse;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AnnotationResponseMapperImpl implements IRestMapper<AnnotationModel, AnnotationResponse> {
    @Override
    public AnnotationResponse modelToRest(AnnotationModel annotationModel) {
        return AnnotationResponse.builder()
                .annotationId(annotationModel.getAnnotationId())
                .userId(annotationModel.getUserId())
                .imageId(annotationModel.getImageId())
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
                .annotationId(annotationResponse.getAnnotationId())
                .userId(annotationResponse.getUserId())
                .imageId(annotationResponse.getImageId())
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
