package com.manuscript.persistence.nosql.mapping;


import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.persistence.nosql.documents.ImageDocument;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ImageEntityMapperImpl implements IRepositoryEntityMapper<ImageModel, ImageDocument> {
    @Override
    public ImageDocument modelToEntity(ImageModel model) {
        if (model.getId() == null) {
            model.setId(UUID.randomUUID());
        }
        return ImageDocument.builder()
                .id(model.getId())
                .fileName(model.getFileName())
                .data(model.getData())
                .build();
    }

    @Override
    public ImageModel entityToModel(final ImageDocument tEntity) {
        if (tEntity.getId() == null) {
            tEntity.setId(UUID.randomUUID());
        }
        return ImageModel.builder()
                .id(tEntity.getId())
                .fileName(tEntity.getFileName())
                .data(tEntity.getData())
                .build();
    }

}
