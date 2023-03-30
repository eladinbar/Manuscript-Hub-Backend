package com.manuscript.infrastructure.persistence.sql.impl;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.core.domain.annotation.repository.IAnnotationRepositoryService;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.infrastructure.persistence.sql.entities.AnnotationEntity;
import com.manuscript.infrastructure.persistence.sql.repositories.IAnnotationRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AnnotationServiceSqlImpl implements IAnnotationRepositoryService {
    private final IAnnotationRepo repo;
    private final IRepositoryEntityMapper<AnnotationModel, AnnotationEntity> mapper;

    @Override
    public AnnotationModel save(AnnotationModel model) throws IllegalArgumentException {
        AnnotationEntity annotationEntity = mapper.modelToEntity(model);
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
    public List<Optional<AnnotationEntity>> getAllByDocumentId(UUID documentId) {
//        List<AnnotationEntity> annotationEntities = new ArrayList<>();
//        repo.findAllById().forEach(annotationEntities::add);
//        List<AnnotationModel> annotationModels = new ArrayList<>();
//        for (AnnotationEntity annotationEntity : annotationEntities) {
//            annotationModels.add(mapper.entityToModel(annotationEntity));
//        }
//        return annotationModels;
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public List<AnnotationModel> getAll() {
        List<AnnotationEntity> annotationEntities = new ArrayList<>();
        repo.findAll().forEach(annotationEntities::add);
        List<AnnotationModel> annotationModels = new ArrayList<>();
        for (AnnotationEntity annotationEntity : annotationEntities) {
            annotationModels.add(mapper.entityToModel(annotationEntity));
        }
        return annotationModels;
    }

    @Override
    public void deleteById(AnnotationModel model) {
        repo.deleteById(model.getAnnotationId());
    }

    @Override
    public void deleteAllByDocumentId(UUID documentId) {
//        repo.delete();
    }

    @Override
    public void deleteAll() {
        throw new RuntimeException("Unimplemented");
    }
}
