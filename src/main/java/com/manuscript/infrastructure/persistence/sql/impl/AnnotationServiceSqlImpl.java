package com.manuscript.infrastructure.persistence.sql.impl;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.core.domain.annotation.repository.IAnnotationRepositoryService;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.domain.user.repository.IUserRepositoryService;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.infrastructure.persistence.sql.entities.AnnotationEntity;
import com.manuscript.infrastructure.persistence.sql.repositories.IAnnotationRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class AnnotationServiceSqlImpl implements IAnnotationRepositoryService {
    private final IUserRepositoryService userServiceRepo;
    private final IAnnotationRepo repo;
    private final IRepositoryEntityMapper<AnnotationModel, AnnotationEntity> mapper;

    @Override
    public AnnotationModel save(AnnotationModel model) throws IllegalArgumentException {
        AnnotationEntity annotationEntity = mapper.modelToEntity(model);
        Optional<UserModel> user = userServiceRepo.getByUid(annotationEntity.getUser().getUid());
        if(!user.isPresent())
            throw new IllegalArgumentException("No user found.\n" +
                    "This should not happen, please contact an administrator.");
        annotationEntity.getUser().setId(user.get().getId());
        annotationEntity = repo.save(annotationEntity);
        return mapper.entityToModel(annotationEntity);
    }

    @Override
    public AnnotationModel update(AnnotationModel model) throws IllegalArgumentException {
        Optional<AnnotationEntity> oldAnnotation = repo.findById(model.getId());
        if(!oldAnnotation.isPresent())
            throw new IllegalArgumentException("No old annotation found.\n" +
                    "This should not happen, please contact an administrator.");
        AnnotationEntity annotationEntity = oldAnnotation.get();
        annotationEntity.setContent(model.getContent());
        annotationEntity.setStartX(model.getStartX());
        annotationEntity.setStartY(model.getStartY());
        annotationEntity.setEndX(model.getEndX());
        annotationEntity.setEndY(model.getEndY());
        annotationEntity.setUpdatedTime(new Date());
        annotationEntity = repo.save(annotationEntity);
        return mapper.entityToModel(annotationEntity);
    }

    @Override
    public boolean existsById(UUID id) throws IllegalArgumentException {
        return repo.existsById(id);
    }

    @Override
    public Optional<AnnotationModel> getById(UUID id) throws IllegalArgumentException {
        Optional<AnnotationEntity> annotationEntityOptional = repo.findById(id);
        if(annotationEntityOptional.isPresent()) {
            AnnotationModel annotationModel = mapper.entityToModel(annotationEntityOptional.get());
            return Optional.of(annotationModel);
        }
        return Optional.empty();
    }

    @Override
    public List<AnnotationModel> getAllByImageDataId(UUID imageDataId) {
        List<AnnotationEntity> annotationEntities = repo.findAllByImageDataId(imageDataId);
        List<AnnotationModel> annotationModels = new ArrayList<>();
        for (AnnotationEntity annotationEntity : annotationEntities) {
            annotationModels.add(mapper.entityToModel(annotationEntity));
        }
        return annotationModels;
    }

    @Override
    public List<AnnotationModel> getAll() {
        List<AnnotationEntity> annotationEntities = repo.findAll();
        List<AnnotationModel> annotationModels = new ArrayList<>();
        for (AnnotationEntity annotationEntity : annotationEntities) {
            annotationModels.add(mapper.entityToModel(annotationEntity));
        }
        return annotationModels;
    }

    @Override
    public void deleteById(UUID id) throws IllegalArgumentException {
        repo.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllByImageDataId(UUID imageDataId) {
        repo.deleteAllByImageDataId(imageDataId);
    }

    @Override
    public void deleteAll() {
        throw new RuntimeException("Unimplemented");
    }
}
