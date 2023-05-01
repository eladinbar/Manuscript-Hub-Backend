package com.manuscript.rest.service;

import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.exceptions.NoUserFoundException;
import com.manuscript.core.exceptions.UserAlreadyExistException;
import com.manuscript.core.usecase.custom.user.*;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.request.UserRequest;
import com.manuscript.rest.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private final IRestMapper<UserModel, UserResponse> userResponseMapper;
    private final IRestMapper<UserModel, UserRequest> userRequestMapper;
    private final IGetByIdUser getByIdUserUseCase;
    private final IGetByUidUser getByUidUserUseCase;
    private final ICreateUser createUserUseCase;
    private final IUpdateUser updateUserUseCase;
    private final IDeleteUserById deleteUserById;
    private final IGetByEmailUser getByEmailUseCase;


    @Override
    public UserResponse getById(UUID id) throws NoUserFoundException {
        Optional<UserModel> userByIdOpt = getByIdUserUseCase.getById(id);
        if (userByIdOpt.isPresent()) {
            UserModel userModel = userByIdOpt.get();
            return userResponseMapper.modelToRest(userModel);
        }
        throw new NoUserFoundException();
    }

    @Override
    public UserResponse getByUid(String uid) throws NoUserFoundException {
        Optional<UserModel> userByUidOpt = getByUidUserUseCase.getByUid(uid);
        if (userByUidOpt.isPresent()) {
            UserModel userModel = userByUidOpt.get();
            return userResponseMapper.modelToRest(userModel);
        }
        throw new NoUserFoundException();
    }

    @Override
    public UserResponse getByEmail(String email) throws NoUserFoundException {
        Optional<UserModel> userModelOptional = getByEmailUseCase.getByEmail(email);
        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();
            return userResponseMapper.modelToRest(userModel);
        }
        throw new NoUserFoundException();
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest) {
        //TODO Role? check
        UserModel userModel = userRequestMapper.restToModel(userRequest);
        return userResponseMapper.modelToRest(createUserUseCase.create(userModel));
    }

    @Override
    public void deleteUser(UUID id) throws NoUserFoundException {
        Optional<UserModel> userToDelete = getByIdUserUseCase.getById(id);
        if (userToDelete.isPresent()) {
            deleteUserById.deleteById(userToDelete.get().getId());
        }
        else {throw new NoUserFoundException();}
    }

    @Override
    public UserResponse save(UserRequest userRequest) throws UserAlreadyExistException {
        Optional<UserModel> userModelByUid = getByUidUserUseCase.getByUid(userRequest.getUid());
        Optional<UserModel> userModelByEmail = getByEmailUseCase.getByEmail(userRequest.getEmail());
        if (userModelByUid.isPresent() || userModelByEmail.isPresent()){
            throw new UserAlreadyExistException();
        }
        UserModel userModel =
                userRequestMapper.restToModel(userRequest);
        return userResponseMapper.modelToRest(createUserUseCase.create(userModel));
    }
}
