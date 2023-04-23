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
                .email(model.getEmail())
                .uid(model.getUid())
                .name(model.getName())
                .phoneNumber(model.getPhoneNumber())
                .role(model.getRole())
                .newUser(false)
                .build();

    }

    @Override
    public UserModel restToModel(UserRequest rest) {
        return UserModel.builder()
                .email(rest.getEmail())
                .uid(rest.getUid())
                .id(UUID.randomUUID())
                .createdTime(new Date())
                .updatedTime(new Date())
                .name(rest.getName())
                .phoneNumber(rest.getPhoneNumber())
                .role(rest.getRole())
                .status("active")
                .build();
    }
}
