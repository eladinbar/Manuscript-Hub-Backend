package com.manuscript.infrastructure.persistence.nosql.impl;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.core.domain.image.models.ImageDataModel;
import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.core.domain.image.repository.IImageDataRepositoryService;
import com.manuscript.core.exceptions.NoImageFoundException;
import com.manuscript.infrastructure.persistence.nosql.documents.ImageDataDocument;
import com.manuscript.infrastructure.persistence.nosql.repositories.IImageDataRepo;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.infrastructure.persistence.sql.entities.AnnotationEntity;
import com.manuscript.infrastructure.persistence.sql.repositories.IImageRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ImageDataServiceNoSqlImpl implements IImageDataRepositoryService {
    private final IImageDataRepo repo;
    private final IRepositoryEntityMapper<ImageDataModel, ImageDataDocument> mapper;

    @Override
    public ImageDataModel save(ImageDataModel imageDataModel) throws IllegalArgumentException {
        ImageDataDocument imageDataDocument = mapper.modelToEntity(imageDataModel);
        imageDataDocument = repo.save(imageDataDocument);
        ImageDataModel result = mapper.entityToModel(imageDataDocument);
        return result;
    }

    @Override
    public boolean existsById(UUID id) throws IllegalArgumentException {
        return repo.existsById(id);
    }

    @Override
    public Optional<ImageDataModel> getById(UUID id) throws IllegalArgumentException {
        Optional<ImageDataDocument> optionalImageDataDocument = repo.findById(id);
        if (!optionalImageDataDocument.isPresent())
            return Optional.empty();
        ImageDataModel imageDataModel = mapper.entityToModel(optionalImageDataDocument.get());
        return Optional.of(imageDataModel);
    }

    @Override
    public List<ImageDataModel> getAllByImageIdData(UUID imageId){
        List<ImageDataDocument> imageDataDocumentList = repo.findAllByImageId(imageId);
        List<ImageDataModel> imageDataModelList = new ArrayList<>();
        for (ImageDataDocument imageDataDocument : imageDataDocumentList) {
            imageDataModelList.add(mapper.entityToModel(imageDataDocument));
        }
        return imageDataModelList;
    }

    @Override
    public List<ImageDataModel> getAll() {
        return null;
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public void deleteAll() {

    }
}
