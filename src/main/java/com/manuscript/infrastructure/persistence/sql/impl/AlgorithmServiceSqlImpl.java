package com.manuscript.infrastructure.persistence.sql.impl;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.core.domain.algorithm.repository.IAlgorithmRepositoryService;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.infrastructure.persistence.sql.entities.AlgorithmEntity;
import com.manuscript.infrastructure.persistence.sql.entities.UserEntity;
import com.manuscript.infrastructure.persistence.sql.repositories.IAlgorithmRepo;
import com.manuscript.infrastructure.persistence.sql.repositories.IUserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class AlgorithmServiceSqlImpl implements IAlgorithmRepositoryService {
    private final IUserRepo userRepo;
    private final IAlgorithmRepo repo;
    private final IRepositoryEntityMapper<AlgorithmModel, AlgorithmEntity> mapper;

    @Override
    public AlgorithmModel save(AlgorithmModel model) throws IllegalArgumentException {
        AlgorithmEntity algorithmEntity = mapper.modelToEntity(model);
        Optional<UserEntity> optionalUser = userRepo.findByUid(algorithmEntity.getUser().getUid());
        if(!optionalUser.isPresent())
            throw new IllegalArgumentException("No user found.\n" +
                    "This should not happen, please contact an administrator.");
        UserEntity user = optionalUser.get();
        algorithmEntity.setUser(user);
        algorithmEntity = repo.save(algorithmEntity);
        return mapper.entityToModel(algorithmEntity);
    }

    @Override
    public AlgorithmModel update(AlgorithmModel model) throws IllegalArgumentException {
        Optional<AlgorithmEntity> oldAlgorithm = Optional.empty();
        if(model.getId() != null)
            oldAlgorithm = repo.findById(model.getId());
        if(!oldAlgorithm.isPresent())
            oldAlgorithm = repo.findByUrl(model.getUrl());
        if(!oldAlgorithm.isPresent())
            throw new IllegalArgumentException("No old algorithm found.\n" +
                    "This should not happen, please contact an administrator.");
        AlgorithmEntity algorithmEntity = oldAlgorithm.get();
        algorithmEntity.setTitle(model.getTitle());
        algorithmEntity.setDescription(model.getDescription());
        algorithmEntity.setModelType(model.getModelType());
        algorithmEntity.setUrl(model.getUrl());
        algorithmEntity.setStatus(model.getStatus());
        algorithmEntity.setUpdatedTime(new Date());
        algorithmEntity = repo.save(algorithmEntity);
        return mapper.entityToModel(algorithmEntity);
    }

    @Override
    public boolean existsById(UUID id) throws IllegalArgumentException {
        return repo.existsById(id);
    }

    @Override
    public Optional<AlgorithmModel> getById(UUID id) throws IllegalArgumentException {
        return repo.findById(id).map(mapper::entityToModel);
    }

    @Override
    public Optional<AlgorithmModel> getByUrl(String url) throws IllegalArgumentException {
        return repo.findByUrl(url).map(mapper::entityToModel);
    }

    @Override
    public List<AlgorithmModel> getAll() {
        List<AlgorithmModel> result = new ArrayList<>();
        repo.findAll().forEach(invitationRequest -> result.add(mapper.entityToModel(invitationRequest)));
        return result;
    }

    @Override
    public void deleteById(UUID id) {
        repo.deleteById(id);
    }

    @Override
    public void deleteByUrl(String url) {
        repo.deleteByUrl(url);
    }

    @Override
    public void deleteAll() {
        throw new RuntimeException("Unimplemented");
    }
}
