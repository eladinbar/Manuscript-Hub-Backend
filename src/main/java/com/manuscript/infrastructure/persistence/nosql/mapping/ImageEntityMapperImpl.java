package com.manuscript.infrastructure.persistence.nosql.mapping;

import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.infrastructure.persistence.nosql.service.IImageUtils;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.infrastructure.persistence.nosql.documents.ImageDocument;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ImageEntityMapperImpl implements IRepositoryEntityMapper<ImageModel, ImageDocument> {
    private final IImageUtils imageUtils;
    @Override
    public ImageDocument modelToEntity(ImageModel imageModel) {
        if (imageModel.getId() == null) {
            imageModel.setId(UUID.randomUUID());
        }
        return ImageDocument.builder()
                .id(imageModel.getId())
                .fileName(imageModel.getFileName())
                .data(imageUtils.encodeBase64String(imageModel.getData()))
                .createdTime(imageModel.getCreatedTime())
                .updatedTime(imageModel.getUpdatedTime())
                .status(imageModel.getStatus())
                .privacy(imageModel.getPrivacy())
                .uid(imageModel.getUid())
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
                .data(imageUtils.decodeBase64(imageDocument.getData()))
                .createdTime(imageDocument.getCreatedTime())
                .updatedTime(imageDocument.getUpdatedTime())
                .status(imageDocument.getStatus())
                .privacy(imageDocument.getPrivacy())
                .uid(imageDocument.getUid())
                .build();
    }
}
