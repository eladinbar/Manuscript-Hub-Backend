package com.manuscript.infrastructure.persistence.nosql.mapping;

import com.manuscript.core.domain.image.models.ImageDataModel;
import com.manuscript.infrastructure.persistence.nosql.common.mapping.IRepositoryDocumentMapper;
import com.manuscript.infrastructure.persistence.nosql.documents.ImageDataDocument;
import com.manuscript.infrastructure.persistence.nosql.service.IImageUtils;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ImageDataEntityMapperImpl implements IRepositoryDocumentMapper<ImageDataModel, ImageDataDocument> {
    private final IImageUtils imageUtils;

    @Override
    public ImageDataDocument modelToDocument(ImageDataModel imageDataModel) {
        if (imageDataModel.getImageDataId() == null) {
            imageDataModel.setImageDataId(UUID.randomUUID());
        }
        return ImageDataDocument.builder()
                .id(imageDataModel.getImageDataId())
                .imageId(imageDataModel.getImageId())
                .fileName(imageDataModel.getFileName())
                .data(imageUtils.encodeBase64String(imageDataModel.getData()))
                .index(imageDataModel.getIndex())
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();
    }

    @Override
    public ImageDataModel documentToModel(ImageDataDocument imageDataDocument) {
        if (imageDataDocument.getId() == null) {
            imageDataDocument.setId(UUID.randomUUID());
        }
        return ImageDataModel.builder()
                .imageDataId(imageDataDocument.getId())
                .imageId(imageDataDocument.getImageId())
                .fileName(imageDataDocument.getFileName())
                .data(imageUtils.decodeBase64(imageDataDocument.getData()))
                .index(imageDataDocument.getIndex())
                .build();
    }
}
