package com.manuscript.persistence.nosql.impl;


import com.manuscript.core.domain.document.models.DocumentModel;
import com.manuscript.core.domain.document.repository.IDocumentRepositoryService;
import com.manuscript.persistence.nosql.entities.DocumentEntity;
import com.manuscript.persistence.common.mapping.IRepositoryEntityMapper;
import com.manuscript.persistence.nosql.repositories.IDocumentRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DocumentServiceNoSqlImpl implements IDocumentRepositoryService {

    private final IDocumentRepo repo;
    private final IRepositoryEntityMapper<DocumentModel, DocumentEntity> mapper;

    public List<DocumentModel> getAllActiveVideosInfo() {
        return null;
    }

    public DocumentModel save(DocumentModel model) throws IllegalArgumentException {
        final DocumentEntity toSave = mapper.modelToEntity(model);
        final DocumentEntity result = repo.save(toSave);
        return mapper.entityToModel(result);
    }

    public List<DocumentModel> getAll() {
        List<DocumentModel> result = new ArrayList<>();
        repo.findAll().forEach(videoInfoEntity -> result.add(mapper.entityToModel(videoInfoEntity)));
        return result;
    }

    public Optional<DocumentModel> getById(UUID id) throws IllegalArgumentException {
        return repo.findById(id).map(mapper::entityToModel);
    }

    public boolean isExists(UUID id) throws IllegalArgumentException {
        return repo.existsById(id);
    }

    public void deleteAll() {
        repo.deleteAll();
    }

    @Override
    public void deleteById(DocumentModel model) {

    }
}
