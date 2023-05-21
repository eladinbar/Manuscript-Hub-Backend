package com.manuscript.rest.mapping.request;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.rest.forms.request.AnnotationRequest;
import com.manuscript.rest.mapping.IRestMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AnnotationRequestMapperImpl implements IRestMapper<AnnotationModel, AnnotationRequest> {
    @Override
    public AnnotationRequest modelToRest(AnnotationModel annotationModel) {
        return AnnotationRequest.builder()
                .id(annotationModel.getId())
                .uid(annotationModel.getUid())
                .imageDataId(annotationModel.getImageDataId())
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
                .id(annotationRequest.getId())
                .uid(annotationRequest.getUid())
                .imageDataId(annotationRequest.getImageDataId())
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
