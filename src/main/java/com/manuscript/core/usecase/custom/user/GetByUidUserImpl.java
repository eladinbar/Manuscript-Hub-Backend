package com.manuscript.core.usecase.custom.user;

import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.domain.user.repository.IUserRepositoryService;
import com.manuscript.core.exceptions.NoUserFoundException;

import java.util.Optional;

public class GetByUidUserImpl implements IGetByUidUser {
    private final IUserRepositoryService userRepoService;

    public GetByUidUserImpl(IUserRepositoryService userRepoService) {
        this.userRepoService = userRepoService;
    }

    @Override
    public Optional<UserModel> getByUid(String uid) {
        Optional<UserModel> userByUidOpt = userRepoService.getByUid(uid);
        if (userByUidOpt.isPresent()) {
            return userByUidOpt;
        }
        throw new NoUserFoundException();
    }
}
