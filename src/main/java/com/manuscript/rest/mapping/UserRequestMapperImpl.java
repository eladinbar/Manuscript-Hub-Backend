package com.manuscript.rest.mapping;

import com.manuscript.core.domain.user.model.UserModel;
import com.manuscript.rest.request.UserRequest;
import org.springframework.stereotype.Service;

@Service
public class UserRequestMapperImpl implements IRestMapper<UserModel, UserRequest> {
    public UserRequest modelToRest(UserModel model) {
        return UserRequest.builder()
                .email(model.getEmail())
                .uid(model.getUid())
                .name(model.getName())
                .phoneNumber(model.getPhoneNumber())
                .userId(model.getUserId())
                .build();

    }

    public UserModel restToModel(UserRequest rest) {
        return UserModel.builder()
                .email(rest.getEmail())
                .uid(rest.getUid())
                .name(rest.getName())
                .phoneNumber(rest.getPhoneNumber())
                .userId(rest.getUserId())
                .build();
    }

}
