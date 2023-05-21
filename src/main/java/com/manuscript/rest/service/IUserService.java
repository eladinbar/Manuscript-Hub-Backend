package com.manuscript.rest.service;


import com.manuscript.rest.forms.request.UserRequest;
import com.manuscript.rest.forms.response.UserResponse;

import java.util.UUID;

public interface IUserService {
    UserResponse getById(UUID id);
    UserResponse getByUid(String uid);
    UserResponse getByEmail(String email);
    UserResponse save(UserRequest userRequest);
    UserResponse updateUser(UserRequest UserRequest);
    void deleteUser(UUID id);

}


