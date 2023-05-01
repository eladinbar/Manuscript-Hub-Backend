package com.manuscript.core.usecase.custom.user;

import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.domain.user.repository.IUserRepositoryService;
import com.manuscript.core.exceptions.NoUserFoundException;

import java.util.Optional;

public class GetByEmailUserImpl implements IGetByEmailUser{
    private final IUserRepositoryService userRepoService;

    public GetByEmailUserImpl(IUserRepositoryService userRepoService) {
        this.userRepoService = userRepoService;
    }


    @Override
    public Optional<UserModel> getByEmail(String email) {
        Optional<UserModel> userByEmail = userRepoService.getByEmail(email);
        return userByEmail;
    }
}
