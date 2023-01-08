package com.manuscript.core.usecase.custom.user;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.usecase.common.GetByIdUseCaseImpl;

public class GetByUIdUserImpl implements IGetByUidUser {

//    public GetByUIdUserImpl(IBaseRepositoryService<UserModel> _serviceRepo) {
//        super(_serviceRepo);
//    }

    @Override
    public UserModel getByUid(String uid) {
        return null;
    }
}
