package com.manuscript.rest.service;


import com.manuscript.rest.response.UserResponse;

import java.util.UUID;

public interface IUserService {
    UserResponse get(UUID id);

}


