package com.manuscript.persistence.nosql.impl;


import com.manuscript.core.domain.document.models.ImageModel;
import com.manuscript.core.domain.document.repository.IImageRepositoryService;
import com.manuscript.persistence.nosql.documents.ImageDocument;
import com.manuscript.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.persistence.nosql.repositories.IImageRepo;
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

    public List<ImageModel> getAllActiveVideosInfo() {
        return null;
    }

    public ImageModel save(ImageModel model) throws IllegalArgumentException {
        final ImageDocument toSave = mapper.modelToEntity(model);
        final ImageDocument result = repo.save(toSave);
        return mapper.entityToModel(result);
    }

    public List<ImageModel> getAll() {
        List<ImageModel> result = new ArrayList<>();
        repo.findAll().forEach(videoInfoEntity -> result.add(mapper.entityToModel(videoInfoEntity)));
        return result;
    }

    public Optional<ImageModel> getById(UUID id) throws IllegalArgumentException {
        return repo.findById(id).map(mapper::entityToModel);
    }

    public boolean isExists(UUID id) throws IllegalArgumentException {
        return repo.existsById(id);
    }

    public void deleteAll() {
        repo.deleteAll();
    }

    @Override
    public void deleteById(ImageModel model) {

    }
}
