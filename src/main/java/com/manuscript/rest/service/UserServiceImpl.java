package com.manuscript.rest.service;


import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.usecase.common.ICreateUseCase;
import com.manuscript.core.usecase.common.IGetByIdUseCase;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.request.UserRegistrationRequest;
import com.manuscript.rest.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private final IRestMapper<UserModel, UserResponse> mapperResponseInfoModel;
    private final IGetByIdUseCase<UserModel> getByIdUserUseCase;
    private final ICreateUseCase<UserModel> createUserUseCase;

    @Override
    public UserResponse get(UUID id) {
        return mapperResponseInfoModel.modelToRest(getByIdUserUseCase.getById(id).get());
        //todo: need to change if represent
    }

    @Override
    public UserModel save(UserRegistrationRequest userRegistrationRequest) {
        return createUserUseCase.create(
                UserModel.builder()
                        .email(userRegistrationRequest.getEmail())
                        .uid(userRegistrationRequest.getUid())
                        .name(userRegistrationRequest.getName())
                        .id(UUID.randomUUID())
                        .status("active")
                        .createdTime(new Date())
                        .updatedTime(new Date())
                        .role(userRegistrationRequest.getRole())
                        .build());
    }


}
