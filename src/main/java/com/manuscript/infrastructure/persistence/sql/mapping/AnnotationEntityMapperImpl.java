package com.manuscript.infrastructure.persistence.sql.mapping;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.infrastructure.persistence.sql.entities.AnnotationEntity;
import org.springframework.stereotype.Service;

@Service
public class AnnotationEntityMapperImpl implements IRepositoryEntityMapper<AnnotationModel, AnnotationEntity> {
    @Override
    public AnnotationEntity modelToEntity(AnnotationModel model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public AnnotationModel entityToModel(AnnotationEntity tEntity) {
        throw new UnsupportedOperationException();
    }
}
