package com.manuscript.rest.mapping.request;


import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.rest.forms.request.UserRequest;
import com.manuscript.rest.mapping.IRestMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

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
                .status(Status.active)
                .phoneNumber(rest.getPhoneNumber())
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();
    }
}
