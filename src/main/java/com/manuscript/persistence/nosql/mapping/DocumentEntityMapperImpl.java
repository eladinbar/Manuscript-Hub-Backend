package com.manuscript.persistence.nosql.mapping;


import com.manuscript.core.domain.document.models.DocumentModel;
import com.manuscript.persistence.common.mapping.IRepositoryEntityMapper;
import com.manuscript.persistence.nosql.entities.DocumentEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DocumentEntityMapperImpl implements IRepositoryEntityMapper<DocumentModel, DocumentEntity> {
    @Override
    public DocumentEntity modelToEntity(DocumentModel model) {
        if (model.getId() == null) {
            model.setId(UUID.randomUUID());
        }
        return DocumentEntity.builder()
                .id(model.getId())
                .fileName(model.getFileName())
                .data(model.getData())
                .build();
    }

    @Override
    public DocumentModel entityToModel(final DocumentEntity tEntity) {
        if (tEntity.getId() == null) {
            tEntity.setId(UUID.randomUUID());
        }
        return DocumentModel.builder()
                .id(tEntity.getId())
                .fileName(tEntity.getFileName())
                .data(tEntity.getData())
                .build();
    }

}
