package com.manuscript.infrastructure.persistence.sql.mapping;

import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.infrastructure.persistence.sql.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserEntityMapperImpl implements IRepositoryEntityMapper<UserModel, UserEntity> {
    @Override
    public UserEntity modelToEntity(UserModel userModel) {
        return UserEntity.builder()
                .id(userModel.getId())
                .createdTime(userModel.getCreatedTime())
                .updatedTime(userModel.getUpdatedTime())
                .email(userModel.getEmail())
                .name(userModel.getName())
                .uid(userModel.getUid())
                .role(userModel.getRole())
                .phoneNumber(userModel.getPhoneNumber())
                .status(userModel.getStatus())
                .build();
    }

    @Override
    public UserModel entityToModel(final UserEntity userEntity) {
        return UserModel.builder()
                .id(userEntity.getId())
                .uid(userEntity.getUid())
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .status(userEntity.getStatus())
                .role(userEntity.getRole())
                .phoneNumber(userEntity.getPhoneNumber())
                .createdTime(userEntity.getCreatedTime())
                .updatedTime(userEntity.getUpdatedTime())
                .build();
    }
}
