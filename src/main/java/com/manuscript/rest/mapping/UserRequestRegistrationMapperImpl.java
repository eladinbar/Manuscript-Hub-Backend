package com.manuscript.rest.mapping;


import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.rest.request.UserRegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public class UserRequestRegistrationMapperImpl implements IRestMapper<UserModel, UserRegistrationRequest> {
    public UserRegistrationRequest modelToRest(UserModel model) {
        return UserRegistrationRequest.builder()
                .email(model.getEmail())
                .uid(model.getUid())
                .name(model.getName())
                .phoneNumber(model.getPhoneNumber())
                .role(model.getRole())
                .build();

    }

    public UserModel restToModel(UserRegistrationRequest rest) {
        return UserModel.builder()
                .email(rest.getEmail())
                .uid(rest.getUid())
                .name(rest.getName())
                .phoneNumber(rest.getPhoneNumber())
                .role(rest.getRole())
                .build();
    }
}
