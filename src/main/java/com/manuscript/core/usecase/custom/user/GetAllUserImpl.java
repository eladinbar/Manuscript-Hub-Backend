package com.manuscript.core.usecase.custom.user;

import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.usecase.common.GetAllUseCaseImpl;

public class GetAllUserImpl extends GetAllUseCaseImpl<UserModel> implements IGetAllUser {

    public GetAllUserImpl(IBaseRepositoryService<UserModel> _serviceRepo) {
        super(_serviceRepo);
    }
}
