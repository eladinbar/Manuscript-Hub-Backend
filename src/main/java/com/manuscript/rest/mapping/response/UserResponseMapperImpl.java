package com.manuscript.rest.mapping.response;


import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.rest.forms.response.UserResponse;
import com.manuscript.rest.mapping.IRestMapper;
import org.springframework.stereotype.Service;

@Service
public class UserResponseMapperImpl implements IRestMapper<UserModel, UserResponse> {
    @Override
    public UserResponse modelToRest(UserModel model) {
        return UserResponse.builder()
                .id(model.getId())
                .uid(model.getUid())
                .email(model.getEmail())
                .name(model.getName())
                .role(model.getRole())
                .status(model.getStatus())
                .phoneNumber(model.getPhoneNumber())
                .createdTime(model.getCreatedTime())
                .updatedTime(model.getUpdatedTime())
                .build();

    }

    @Override
    public UserModel restToModel(UserResponse rest) {
        return UserModel.builder()
                .id(rest.getId())
                .uid(rest.getUid())
                .email(rest.getEmail())
                .name(rest.getName())
                .role(rest.getRole())
                .status(rest.getStatus())
                .phoneNumber(rest.getPhoneNumber())
                .createdTime(rest.getCreatedTime())
                .updatedTime(rest.getUpdatedTime())
                .build();
    }

}
