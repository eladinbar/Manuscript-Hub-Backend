package com.manuscript.infrastructure.persistence.sql.mapping;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.infrastructure.persistence.sql.entities.AlgorithmEntity;
import com.manuscript.infrastructure.persistence.sql.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AlgorithmEntityMapperImpl implements IRepositoryEntityMapper<AlgorithmModel, AlgorithmEntity> {
    @Override
    public AlgorithmEntity modelToEntity(AlgorithmModel algorithmModel) {
        AlgorithmEntity algorithmEntity = AlgorithmEntity.builder()
                .user(new UserEntity())
                .url(algorithmModel.getUrl())
                .createdTime(algorithmModel.getCreatedTime())
                .updatedTime(algorithmModel.getUpdatedTime())
                .build();
        algorithmEntity.getUser().setUid(algorithmModel.getUid());
        return algorithmEntity;
    }

    public AlgorithmEntity modelToEntity(AlgorithmModel algorithmModel, UserEntity user) {
        return AlgorithmEntity.builder()
                .user(user)
                .url(algorithmModel.getUrl())
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();
    }

    @Override
    public AlgorithmModel entityToModel(AlgorithmEntity algorithmEntity) {
        if(algorithmEntity.getId() == null)
            throw new IllegalArgumentException("Algorithm Entity's ID must not be null.");
        return AlgorithmModel.builder()
                .id(algorithmEntity.getId())
                .uid(algorithmEntity.getUser().getUid())
                .url(algorithmEntity.getUrl())
                .createdTime(algorithmEntity.getCreatedTime())
                .updatedTime(algorithmEntity.getUpdatedTime())
                .build();
    }
}
