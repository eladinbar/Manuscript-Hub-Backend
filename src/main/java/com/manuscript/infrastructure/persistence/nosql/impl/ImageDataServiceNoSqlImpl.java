package com.manuscript.infrastructure.persistence.nosql.impl;

import com.manuscript.core.domain.image.models.ImageDataModel;
import com.manuscript.core.domain.image.repository.IImageDataRepositoryService;
import com.manuscript.infrastructure.persistence.nosql.common.mapping.IRepositoryDocumentMapper;
import com.manuscript.infrastructure.persistence.nosql.documents.ImageDataDocument;
import com.manuscript.infrastructure.persistence.nosql.repositories.IImageDataRepo;
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
    private final IRepositoryDocumentMapper<ImageDataModel, ImageDataDocument> mapper;

    @Override
    public ImageDataModel save(ImageDataModel imageDataModel) throws IllegalArgumentException {
        ImageDataDocument imageDataDocument = mapper.modelToDocument(imageDataModel);
        imageDataDocument = repo.save(imageDataDocument);
        ImageDataModel result = mapper.documentToModel(imageDataDocument);
        return result;
    }

    @Override
    public Optional<ImageDataModel> getById(UUID id) throws IllegalArgumentException {
        Optional<ImageDataDocument> optionalImageDataDocument = repo.findById(id);
        if (!optionalImageDataDocument.isPresent())
            return Optional.empty();
        ImageDataModel imageDataModel = mapper.documentToModel(optionalImageDataDocument.get());
        return Optional.of(imageDataModel);
    }

    @Override
    public List<ImageDataModel> getAllByImageIdData(UUID imageId){
        List<ImageDataDocument> imageDataDocumentList = repo.findAllByImageId(imageId);
        List<ImageDataModel> imageDataModelList = new ArrayList<>();
        for (ImageDataDocument imageDataDocument : imageDataDocumentList) {
            imageDataModelList.add(mapper.documentToModel(imageDataDocument));
        }
        return imageDataModelList;
    }

    @Override
    public void deleteById(UUID id) { repo.deleteById(id); }

    @Override
    public boolean existsById(UUID id) throws IllegalArgumentException {
        return repo.existsById(id);
    }

    @Override
    public List<ImageDataModel> getAll() {
        return null;
    }

    @Override
    public void deleteAll() { }
}
