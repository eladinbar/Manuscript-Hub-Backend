package com.manuscript.core.usecase.custom.user;

import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.usecase.common.IDeleteByIdUseCase;
import com.manuscript.core.usecase.common.IGetByIdUseCase;

import java.util.UUID;

public interface IDeleteUserById extends IDeleteByIdUseCase<UserModel> {

}
