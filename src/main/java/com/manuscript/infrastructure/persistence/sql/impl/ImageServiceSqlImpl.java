package com.manuscript.infrastructure.persistence.sql.impl;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.core.domain.image.repository.IImageRepositoryService;
import com.manuscript.infrastructure.persistence.nosql.documents.ImageDataDocument;
import com.manuscript.infrastructure.persistence.sql.entities.ImageEntity;
import com.manuscript.infrastructure.persistence.sql.repositories.IImageRepo;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ImageServiceSqlImpl implements IImageRepositoryService {
    private final IImageRepo repo;
    private final IRepositoryEntityMapper<ImageModel, ImageEntity> mapper;

    @Override
    public ImageModel save(ImageModel imageModel) throws IllegalArgumentException {
        if (imageModel.getImageId() == null) {
            imageModel.setImageId(UUID.randomUUID());
        }
        ImageEntity imageEntity = mapper.modelToEntity(imageModel);
        imageEntity = repo.save(imageEntity);
        return mapper.entityToModel(imageEntity);
    }

    @Override
    public List<ImageModel> getAll() {
        List<ImageModel> result = new ArrayList<>();
        repo.findAll().forEach(imageDocument -> result.add(mapper.entityToModel(imageDocument)));
        return result;
    }

    @Override
    public List<ImageModel> getAllPublicImages() {
        List<ImageModel> result = new ArrayList<>();
        repo.getImagesByPrivacy(Privacy.Public).forEach(imageDocument -> result.add(mapper.entityToModel(imageDocument)));
        return result;
    }

    @Override
    public List<ImageModel> getAllByUidImages(String userId) {
        return null; //TODO
    }

    @Override
    public List<ImageModel> getAllSharedImages(String userId) {
        return null; //TODO
    }

    @Override
    public Optional<ImageModel> getById(UUID id) throws IllegalArgumentException {
        return repo.findById(id).map(mapper::entityToModel);
    }

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
