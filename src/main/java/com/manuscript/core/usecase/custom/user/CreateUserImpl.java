package com.manuscript.core.usecase.custom.user;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.usecase.common.CreateUseCaseImpl;

public class CreateUserImpl extends CreateUseCaseImpl<UserModel> implements ICreateUser {

    public CreateUserImpl(IBaseRepositoryService<UserModel> _serviceRepo) {
        super(_serviceRepo);
    }
}
