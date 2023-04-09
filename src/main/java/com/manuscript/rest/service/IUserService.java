package com.manuscript.rest.service;


import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.rest.request.UserRegistrationRequest;
import com.manuscript.rest.request.UserRequest;
import com.manuscript.rest.response.UserResponse;

import java.util.UUID;

public interface IUserService {
    UserResponse getById(UUID id);
    UserResponse getByUid(String uid);
    UserResponse getByEmail(String email);
    UserResponse save(UserRequest userRequest);
    UserResponse updateUser(UserRequest UserRequest);
    void deleteUser(UUID id);

}


