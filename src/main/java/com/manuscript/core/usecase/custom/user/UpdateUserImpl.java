package com.manuscript.core.usecase.custom.user;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.usecase.common.UpdateUseCaseImpl;

public class UpdateUserImpl extends UpdateUseCaseImpl<UserModel> implements IUpdateUser {

    public UpdateUserImpl(IBaseRepositoryService<UserModel> _serviceRepo) {
        super(_serviceRepo);
    }
}
