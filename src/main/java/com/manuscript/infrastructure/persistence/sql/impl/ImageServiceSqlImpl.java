package com.manuscript.infrastructure.persistence.sql.impl;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.core.domain.image.repository.IImageRepositoryService;
import com.manuscript.core.exceptions.NoImageFoundException;
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
    public ImageModel update(ImageModel imageModel) throws IllegalArgumentException, NoImageFoundException {
        Optional<ImageEntity> optionalImageEntity = repo.findById(imageModel.getImageId());
        if (!optionalImageEntity.isPresent()){
            throw new NoImageFoundException();
        }
        ImageEntity newImageEntity = mapper.modelToEntity(imageModel);
        ImageEntity currentImageEntity = optionalImageEntity.get();
        currentImageEntity.setTitle(newImageEntity.getTitle());
        currentImageEntity.setAuthor(newImageEntity.getAuthor());
        currentImageEntity.setPublicationDate(newImageEntity.getPublicationDate());
        currentImageEntity.setDescription(newImageEntity.getDescription());
        currentImageEntity.setTags(newImageEntity.getTags());
        currentImageEntity.setSharedUserIds(newImageEntity.getSharedUserIds());
        currentImageEntity.setPrivacy(newImageEntity.getPrivacy());
        newImageEntity = repo.save(currentImageEntity);
        return mapper.entityToModel(newImageEntity);
    }

    @Override
    public Optional<ImageModel> getById(UUID id) throws IllegalArgumentException {
        Optional<ImageEntity> optionalImageEntity = repo.findById(id);
        if (!optionalImageEntity.isPresent()){
            return Optional.empty();
        }
        ImageModel imageModel = mapper.entityToModel(optionalImageEntity.get());
        return Optional.of(imageModel);
    }

    @Override
    public List<ImageModel> getAllByUidImages(String userId) throws IllegalArgumentException {
        List<ImageEntity> imageEntityList = repo.getImagesByUid(userId);
        List<ImageModel> result = ImageEntityListToModelList(imageEntityList);
        return result;
    }

    @Override
    public List<ImageModel> getAllPublicImages() {
        List<ImageEntity> imageEntityList = repo.getImagesByPrivacy(Privacy.Public);
        List<ImageModel> result = ImageEntityListToModelList(imageEntityList);
        return result;
    }

    @Override
    public List<ImageModel> getAllSharedImages(String userId) {
        List<ImageEntity> imageEntityList = repo.getImagesByPrivacyAndUid(Privacy.Shared, userId);
        List<ImageModel> result = ImageEntityListToModelList(imageEntityList);
        return result;
    }

    @Override
    public void deleteById(UUID id) {
        repo.deleteById(id);
    }

    public boolean existsById(UUID id) throws IllegalArgumentException {
        return repo.existsById(id);
    }

    @Override
    public List<ImageModel> getAll() {
        List<ImageModel> result = new ArrayList<>();
        repo.findAll().forEach(imageDocument -> result.add(mapper.entityToModel(imageDocument)));
        return result;
    }

    @Override
    public void deleteAll() {
        repo.deleteAll();
    }

    private List<ImageModel> ImageEntityListToModelList(List<ImageEntity> imageEntityList){
        List<ImageModel> imageModelList = new ArrayList<>();
        for (ImageEntity imageEntity : imageEntityList){
            ImageModel imageModel = mapper.entityToModel(imageEntity);
            imageModelList.add(imageModel);
        }
        return imageModelList;
    }
}
