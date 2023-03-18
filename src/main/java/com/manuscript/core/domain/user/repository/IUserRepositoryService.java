package com.manuscript.core.domain.user.repository;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.user.models.UserModel;

import java.util.Optional;

public interface IUserRepositoryService extends IBaseRepositoryService<UserModel> {
    Optional<UserModel> getByUid(String uid);
}
