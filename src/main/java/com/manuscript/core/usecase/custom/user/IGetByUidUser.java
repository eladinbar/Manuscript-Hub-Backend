package com.manuscript.core.usecase.custom.user;

import com.manuscript.core.domain.user.models.UserModel;

import java.util.Optional;

public interface IGetByUidUser {
    Optional<UserModel> getByUid(String uid);
}
