package com.manuscript.infrastructure.persistence.nosql.impl;

import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.core.domain.image.repository.IImageRepositoryService;
import com.manuscript.infrastructure.persistence.nosql.documents.ImageDocument;
import com.manuscript.infrastructure.persistence.nosql.repositories.IImageRepo;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ImageServiceNoSqlImpl implements IImageRepositoryService {
    private final IImageRepo repo;
    private final IRepositoryEntityMapper<ImageModel, ImageDocument> mapper;

    @Override
    public ImageModel save(ImageModel imageModel) throws IllegalArgumentException {
        final ImageDocument toSave = mapper.modelToEntity(imageModel);
        final ImageDocument result = repo.save(toSave);
        return mapper.entityToModel(result);
    }

    @Override
    public List<ImageModel> getAll() {
        List<ImageModel> result = new ArrayList<>();
        repo.findAll().forEach(videoInfoEntity -> result.add(mapper.entityToModel(videoInfoEntity)));
        return result;
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

    }
}
