package com.manuscript.infrastructure.persistence.sql.impl;

import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.domain.user.repository.IUserRepositoryService;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.infrastructure.persistence.sql.entities.UserEntity;
import com.manuscript.infrastructure.persistence.sql.repositories.IUserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UserServiceSqlImpl implements IUserRepositoryService {
    private final IUserRepo repo;
    private final IRepositoryEntityMapper<UserModel, UserEntity> mapper;

    @Override
    public UserModel save(UserModel model) throws IllegalArgumentException {
        final UserEntity toSave = mapper.modelToEntity(model);
        if (toSave.getCreatedTime() == null) {
            toSave.setCreatedTime(new Date());
            toSave.setUpdatedTime(new Date());
        }
        final UserEntity userEntity = repo.save(toSave);
        return mapper.entityToModel(userEntity);
    }

    @Override
    public UserModel update(UserModel model) throws IllegalArgumentException {
        Optional<UserEntity> oldUser = repo.findByUid(model.getUid());
        if (!oldUser.isPresent())
            throw new IllegalArgumentException("No old user found.\n" +
                    "This should not happen, please contact an administrator.");
        UserEntity userEntity = oldUser.get();
        userEntity.setName(model.getName());
        userEntity.setRole(model.getRole());
        userEntity.setStatus(model.getStatus());
        userEntity.setUpdatedTime(new Date());
        return mapper.entityToModel(userEntity);
    }

    @Override
    public List<UserModel> getAll() {
        List<UserModel> userModels = new ArrayList<>();
        repo.findAll().forEach(videoInfoEntity -> userModels.add(mapper.entityToModel(videoInfoEntity)));
        return userModels;
    }

    @Override
    public Optional<UserModel> getById(UUID id) throws IllegalArgumentException {
        return repo.findById(id).map(mapper::entityToModel);
    }

    @Override
    public Optional<UserModel> getByUid(String uid) {
        Optional<UserEntity> userEntityOptional = repo.findByUid(uid);
        if (userEntityOptional.isPresent()) {
            UserModel userModel = mapper.entityToModel(userEntityOptional.get());
            return Optional.of(userModel);
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserModel> getByEmail(String email) {
        Optional<UserEntity> userEntityOptional = repo.findByEmail(email);
        if (userEntityOptional.isPresent()) {
            UserModel userModel = mapper.entityToModel(userEntityOptional.get());
            return Optional.of(userModel);
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(UUID id) throws IllegalArgumentException {
        return repo.existsById(id);
    }

    @Override
    public void deleteAll() {
        repo.deleteAll();
    }

    @Override
    public void deleteById(UUID id) {
        repo.deleteById(id);
    }
}
