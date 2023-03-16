package com.manuscript.rest.service;


import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.rest.request.UserRegistrationRequest;
import com.manuscript.rest.response.UserResponse;

import java.util.UUID;

public interface IUserService {
    UserResponse get(UUID id);
    UserModel save(UserRegistrationRequest userRegistrationRequest);

}


