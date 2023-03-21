package com.manuscript.infrastructure.persistence.sql.mapping;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.infrastructure.persistence.sql.entities.AlgorithmEntity;
import org.springframework.stereotype.Service;

@Service
public class AlgorithmEntityMapperImpl implements IRepositoryEntityMapper<AlgorithmModel, AlgorithmEntity> {
    @Override
    public AlgorithmEntity modelToEntity(AlgorithmModel model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public AlgorithmModel entityToModel(AlgorithmEntity tEntity) {
        throw new UnsupportedOperationException();
    }
}
