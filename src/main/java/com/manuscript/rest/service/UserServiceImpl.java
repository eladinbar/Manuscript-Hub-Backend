package com.manuscript.rest.service;

import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.exceptions.NoUserFoundException;
import com.manuscript.core.usecase.custom.user.ICreateUser;
import com.manuscript.core.usecase.custom.user.IGetByIdUser;
import com.manuscript.core.usecase.custom.user.IGetByUidUser;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.request.UserRegistrationRequest;
import com.manuscript.rest.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private final IRestMapper<UserModel, UserResponse> userResponseMapper;
    private final IRestMapper<UserModel, UserResponse> userRequestMapper;
    private final IGetByIdUser getByIdUserUseCase;
    private final IGetByUidUser getByUidUserUseCase;
    private final ICreateUser createUserUseCase;

    @Override
    public UserResponse getById(UUID id) {
        return userResponseMapper.modelToRest(getByIdUserUseCase.getById(id).get());
        //todo: need to change if represent
    }

    @Override
    public UserResponse getByUid(String uid) {
        Optional<UserModel> userByUidOpt = getByUidUserUseCase.getByUid(uid);
        if (userByUidOpt.isPresent()) {
            UserModel userModel = userByUidOpt.get();
            return userResponseMapper.modelToRest(userModel);
        }
        throw new NoUserFoundException();
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
