package com.manuscript.infrastructure.persistence.sql.mapping;

import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.infrastructure.persistence.sql.entities.ImageEntity;
import com.manuscript.infrastructure.persistence.sql.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ImageEntityMapperImpl implements IRepositoryEntityMapper<ImageModel, ImageEntity> {

    @Override
    public ImageEntity modelToEntity(ImageModel imageModel) {
        UserEntity mockUserEntity = new UserEntity();
        mockUserEntity.setUid(imageModel.getUserId());
        return ImageEntity.builder()
                .id(imageModel.getImageId())
                .user(mockUserEntity)
                .title(imageModel.getTitle())
                .author(imageModel.getAuthor())
                .publicationDate(imageModel.getPublicationDate())
                .description(imageModel.getDescription())
                //TODO: tags
                //TODO: sharedUserIds
                .status(imageModel.getStatus())
                .privacy(imageModel.getPrivacy())
                .createdTime(imageModel.getCreatedTime())
                .updatedTime(imageModel.getUpdatedTime())
                .build();
    }

    @Override
    public ImageModel entityToModel(ImageEntity imageEntity) {
        return ImageModel.builder()
                .imageId(imageEntity.getId())
                .userId(imageEntity.getUser().getUid())
                .title(imageEntity.getTitle())
                .author(imageEntity.getAuthor())
                .publicationDate(imageEntity.getPublicationDate())
                .description(imageEntity.getDescription())
                //TODO: tags
                //TODO: sharedUserIds
                .status(imageEntity.getStatus())
                .privacy(imageEntity.getPrivacy())
                .createdTime(imageEntity.getCreatedTime())
                .updatedTime(imageEntity.getUpdatedTime())
                .build();
    }
}
