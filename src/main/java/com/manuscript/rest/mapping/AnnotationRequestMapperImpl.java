package com.manuscript.rest.mapping;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.rest.request.AnnotationRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AnnotationRequestMapperImpl implements IRestMapper<AnnotationModel, AnnotationRequest> {
    @Override
    public AnnotationRequest modelToRest(AnnotationModel annotationModel) {
        return AnnotationRequest.builder()
                .id(annotationModel.getAnnotationId())
                .uid(annotationModel.getUserId())
                .imageId(annotationModel.getImageId())
                .algorithmId(annotationModel.getAlgorithmId())
                .content(annotationModel.getContent())
                .startX(annotationModel.getStartX())
                .startY(annotationModel.getStartY())
                .endX(annotationModel.getEndX())
                .endY(annotationModel.getEndY())
                .build();
    }

    @Override
    public AnnotationModel restToModel(AnnotationRequest annotationRequest) {
        return AnnotationModel.builder()
                .annotationId(annotationRequest.getId())
                .userId(annotationRequest.getUid())
                .imageId(annotationRequest.getImageId())
                .algorithmId(annotationRequest.getAlgorithmId())
                .content(annotationRequest.getContent())
                .startX(annotationRequest.getStartX())
                .startY(annotationRequest.getStartY())
                .endX(annotationRequest.getEndX())
                .endY(annotationRequest.getEndY())
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();
    }
}
