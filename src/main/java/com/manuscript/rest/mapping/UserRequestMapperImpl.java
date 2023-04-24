package com.manuscript.rest.mapping;


import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.rest.request.UserRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserRequestMapperImpl implements IRestMapper<UserModel, UserRequest> {
    @Override
    public UserRequest modelToRest(UserModel model) {
        return UserRequest.builder()
                .uid(model.getUid())
                .email(model.getEmail())
                .name(model.getName())
                .role(model.getRole())
                .phoneNumber(model.getPhoneNumber())
                .newUser(false)
                .build();

    }

    @Override
    public UserModel restToModel(UserRequest rest) {
        return UserModel.builder()
                .id(rest.getId())
                .uid(rest.getUid())
                .email(rest.getEmail())
                .name(rest.getName())
                .role(rest.getRole())
                .status("active")
                .phoneNumber(rest.getPhoneNumber())
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();
    }
}
