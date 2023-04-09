package com.manuscript.core.usecase.custom.user;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.domain.user.repository.IUserRepositoryService;
import com.manuscript.core.usecase.common.CreateUseCaseImpl;
import com.manuscript.core.usecase.common.DeleteByIdUseCaseImpl;

import java.util.UUID;

public class DeleteUserByIdImpl extends DeleteByIdUseCaseImpl<UserModel> implements IDeleteUserById {

    public DeleteUserByIdImpl(IUserRepositoryService userRepoService) {
        super(userRepoService);
    }
}
