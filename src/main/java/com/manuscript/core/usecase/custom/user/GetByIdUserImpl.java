package com.manuscript.core.usecase.custom.user;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.user.model.UserModel;
import com.manuscript.core.usecase.common.GetByIdUseCaseImpl;

public class GetByIdUserImpl extends GetByIdUseCaseImpl<UserModel> implements IGetByIdUser {

    public GetByIdUserImpl(IBaseRepositoryService<UserModel> _serviceRepo) {
        super(_serviceRepo);
    }
}
