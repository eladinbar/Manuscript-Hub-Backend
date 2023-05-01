package com.manuscript.infrastructure.persistence.sql.mapping;

import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.infrastructure.persistence.sql.entities.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserEntityMapperImpl implements IRepositoryEntityMapper<UserModel, UserEntity> {
    @Override
    public UserEntity modelToEntity(UserModel userModel) {
        return UserEntity.builder()
                .id(userModel.getId())
                .uid(userModel.getUid())
                .email(userModel.getEmail())
                .name(userModel.getName())
                .role(userModel.getRole())
                .status(userModel.getStatus())
                .phoneNumber(userModel.getPhoneNumber())
                .createdTime(userModel.getCreatedTime())
                .updatedTime(userModel.getUpdatedTime()).build();
    }

    @Override
    public UserModel entityToModel(final UserEntity userEntity) {
        return UserModel.builder()
                .id(userEntity.getId())
                .uid(userEntity.getUid())
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .role(userEntity.getRole())
                .status(userEntity.getStatus())
                .phoneNumber(userEntity.getPhoneNumber())
                .createdTime(userEntity.getCreatedTime())
                .updatedTime(userEntity.getUpdatedTime()).build();
    }
}
