package com.manuscript.core.usecase.custom.user;

import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.usecase.common.IGetByIdUseCase;

public interface IGetByUidUser {
    UserModel getByUid(String uid);
}
