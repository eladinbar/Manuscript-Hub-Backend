package com.manuscript.infrastructure.persistence.sql.mapping;


import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.infrastructure.persistence.sql.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserEntityMapperImpl implements IRepositoryEntityMapper<UserModel, UserEntity> {
    @Override
    public UserEntity modelToEntity(UserModel model) {
        if (model.getId() == null) {
            model.setId(UUID.randomUUID());
        }
        return UserEntity.builder()
                .id(model.getId())
                .createdTime(model.getCreatedTime())
                .updatedTime(model.getUpdatedTime())
                .email(model.getEmail())
                .name(model.getName())
                .uid(model.getUid())
                .phoneNumber(model.getPhoneNumber())
                .status(model.getStatus())
                .build();
    }

    @Override
    public UserModel entityToModel(final UserEntity tEntity) {
        if (tEntity.getId() == null) {
            tEntity.setId(UUID.randomUUID());
        }
        return UserModel.builder()
                .id(tEntity.getId())
                .createdTime(tEntity.getCreatedTime())
                .updatedTime(tEntity.getUpdatedTime())
                .email(tEntity.getEmail())
                .name(tEntity.getName())
                .status(tEntity.getStatus())
                .uid(tEntity.getUid())
                .phoneNumber(tEntity.getPhoneNumber())
                .build();
    }

}
