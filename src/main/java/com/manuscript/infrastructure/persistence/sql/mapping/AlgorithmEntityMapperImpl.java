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
        algorithmEntity.getUser().setUid(algorithmModel.getUserId());
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
        return AlgorithmModel.builder()
                .algorithmId(algorithmEntity.getId())
                .userId(algorithmEntity.getUser().getUid())
                .url(algorithmEntity.getUrl())
                .createdTime(algorithmEntity.getCreatedTime())
                .updatedTime(algorithmEntity.getUpdatedTime())
                .build();
    }
}
