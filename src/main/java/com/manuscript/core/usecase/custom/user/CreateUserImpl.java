package com.manuscript.core.usecase.custom.user;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.usecase.common.CreateUseCaseImpl;

public class CreateUserImpl extends CreateUseCaseImpl<UserModel> implements ICreateUser {
    private final IBaseRepositoryService<UserModel> _serviceRepo;

    public CreateUserImpl(IBaseRepositoryService<UserModel> _serviceRepo) {
        super(_serviceRepo);
        this._serviceRepo = _serviceRepo;
    }

    @Override
    public UserModel create(UserModel userModel) throws IllegalArgumentException {
        return _serviceRepo.save(userModel);
    }
}
