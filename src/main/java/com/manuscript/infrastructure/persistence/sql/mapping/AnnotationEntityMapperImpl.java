package com.manuscript.infrastructure.persistence.sql.mapping;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.infrastructure.persistence.sql.entities.AlgorithmEntity;
import com.manuscript.infrastructure.persistence.sql.entities.AnnotationEntity;
import com.manuscript.infrastructure.persistence.sql.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AnnotationEntityMapperImpl implements IRepositoryEntityMapper<AnnotationModel, AnnotationEntity> {
    @Override
    public AnnotationEntity modelToEntity(AnnotationModel annotationModel) {
        AnnotationEntity annotationEntity = AnnotationEntity.builder()
                .user(new UserEntity())
                .imageId(annotationModel.getImageId())
                .algorithm(new AlgorithmEntity())
                .content(annotationModel.getContent())
                .startX(annotationModel.getStartX())
                .startY(annotationModel.getStartY())
                .endX(annotationModel.getEndX())
                .endY(annotationModel.getEndY())
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();
        annotationEntity.getUser().setId(annotationModel.getUserId());
        annotationEntity.getAlgorithm().setId(annotationModel.getAlgorithmId());
        return annotationEntity;
    }

    public AnnotationEntity modelToEntity(AnnotationModel annotationModel, UserEntity user, AlgorithmEntity algorithm) {
        return AnnotationEntity.builder()
                .user(user)
                .imageId(annotationModel.getImageId())
                .algorithm(algorithm)
                .content(annotationModel.getContent())
                .startX(annotationModel.getStartX())
                .startY(annotationModel.getStartY())
                .endX(annotationModel.getEndX())
                .endY(annotationModel.getEndY())
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();
    }

    @Override
    public AnnotationModel entityToModel(AnnotationEntity annotationEntity) {
        return AnnotationModel.builder()
                .annotationId(annotationEntity.getId())
                .userId(annotationEntity.getUser().getId())
                .imageId(annotationEntity.getImageId())
                .algorithmId(annotationEntity.getAlgorithm().getId())
                .content(annotationEntity.getContent())
                .startX(annotationEntity.getStartX())
                .startY(annotationEntity.getStartY())
                .endX(annotationEntity.getEndX())
                .endY(annotationEntity.getEndY())
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();
    }
}
