package com.manuscript.core.usecase.custom.user;

import com.manuscript.core.domain.user.models.UserModel;

import java.util.Optional;

public interface IGetByEmailUser {

    Optional<UserModel> getByEmail(String email);
}
