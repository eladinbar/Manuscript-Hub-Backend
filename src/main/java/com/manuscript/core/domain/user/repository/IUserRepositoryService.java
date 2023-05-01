package com.manuscript.core.domain.user.repository;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.infrastructure.persistence.sql.entities.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepositoryService extends IBaseRepositoryService<UserModel> {
    Optional<UserModel> getByUid(String uid);

    Optional<UserModel> getById(UUID id);
    Optional<UserModel> getByEmail(String email);
    void deleteById(UUID id);
}
