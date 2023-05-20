package com.manuscript.infrastructure.persistence.sql.mapping;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.infrastructure.persistence.sql.entities.AlgorithmEntity;
import com.manuscript.infrastructure.persistence.sql.entities.AnnotationEntity;
import com.manuscript.infrastructure.persistence.sql.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class AnnotationEntityMapperImpl implements IRepositoryEntityMapper<AnnotationModel, AnnotationEntity> {
    private final UUID NIL = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @Override
    public AnnotationEntity modelToEntity(AnnotationModel annotationModel) {
        AnnotationEntity annotationEntity = AnnotationEntity.builder()
                .user(new UserEntity())
                .imageDataId(annotationModel.getImageDataId())
                .algorithm(null)
                .content(annotationModel.getContent())
                .startX(annotationModel.getStartX())
                .startY(annotationModel.getStartY())
                .endX(annotationModel.getEndX())
                .endY(annotationModel.getEndY())
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();
        annotationEntity.getUser().setUid(annotationModel.getUid());
        if(!annotationModel.getAlgorithmId().equals(NIL)) {
            annotationEntity.setAlgorithm(new AlgorithmEntity());
            annotationEntity.getAlgorithm().setId(annotationModel.getAlgorithmId());
        }
        return annotationEntity;
    }

    public AnnotationEntity modelToEntity(AnnotationModel annotationModel, UserEntity user, AlgorithmEntity algorithm) {
        return AnnotationEntity.builder()
                .user(user)
                .imageDataId(annotationModel.getImageDataId())
                .algorithm(algorithm)
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
    public AnnotationModel entityToModel(AnnotationEntity annotationEntity) {
        if(annotationEntity.getId() == null)
            throw new IllegalArgumentException("Annotation Entity's ID must not be null.");
        return AnnotationModel.builder()
                .id(annotationEntity.getId())
                .uid(annotationEntity.getUser().getUid())
                .imageDataId(annotationEntity.getImageDataId())
                .algorithmId(annotationEntity.getAlgorithm() != null ? annotationEntity.getAlgorithm().getId() : NIL)
                .content(annotationEntity.getContent())
                .startX(annotationEntity.getStartX())
                .startY(annotationEntity.getStartY())
                .endX(annotationEntity.getEndX())
                .endY(annotationEntity.getEndY())
                .createdTime(annotationEntity.getCreatedTime())
                .updatedTime(annotationEntity.getUpdatedTime())
                .build();
    }
}
