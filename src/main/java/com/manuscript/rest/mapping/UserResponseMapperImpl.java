package com.manuscript.rest.mapping;


import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.rest.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class UserResponseMapperImpl implements IRestMapper<UserModel, UserResponse> {
    public UserResponse modelToRest(UserModel model) {
        return UserResponse.builder()
                .userId(model.getId())
                .createdTime(model.getCreatedTime())
                .updatedTime(model.getUpdatedTime())
                .uid(model.getUid())
                .email(model.getEmail())
                .name(model.getName())
                .phoneNumber(model.getPhoneNumber())
                .build();

    }

    public UserModel restToModel(UserResponse rest) {
        return UserModel.builder()
                .id(rest.getUserId())
                .createdTime(rest.getCreatedTime())
                .updatedTime(rest.getUpdatedTime())
                .uid(rest.getUid())
                .email(rest.getEmail())
                .name(rest.getName())
                .phoneNumber(rest.getPhoneNumber())
                .build();
    }

}
