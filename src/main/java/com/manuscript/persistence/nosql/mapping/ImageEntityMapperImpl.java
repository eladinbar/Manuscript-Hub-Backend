package com.manuscript.persistence.nosql.mapping;

import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.persistence.nosql.documents.ImageDocument;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ImageEntityMapperImpl implements IRepositoryEntityMapper<ImageModel, ImageDocument> {
    @Override
    public ImageDocument modelToEntity(ImageModel imageModel) {
        if (imageModel.getId() == null) {
            imageModel.setId(UUID.randomUUID());
        }
        return ImageDocument.builder()
                .id(imageModel.getId())
                .fileName(imageModel.getFileName())
                .data(imageModel.getData())
                .createdTime(imageModel.getCreatedTime())
                .updatedTime(imageModel.getUpdatedTime())
                .build();
    }

    @Override
    public ImageModel entityToModel(final ImageDocument imageDocument) {
        if (imageDocument.getId() == null) {
            imageDocument.setId(UUID.randomUUID());
        }
        return ImageModel.builder()
                .id(imageDocument.getId())
                .fileName(imageDocument.getFileName())
                .data(imageDocument.getData())
                .createdTime(imageDocument.getCreatedTime())
                .updatedTime(imageDocument.getUpdatedTime())
                .build();
    }

}
