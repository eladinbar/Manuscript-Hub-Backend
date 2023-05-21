package com.manuscript.infrastructure.persistence.sql.mapping;

import com.manuscript.core.domain.image.models.ImageInfoModel;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.infrastructure.persistence.sql.entities.ImageEntity;
import com.manuscript.infrastructure.persistence.sql.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ImageEntityMapperImpl implements IRepositoryEntityMapper<ImageInfoModel, ImageEntity> {

    @Override
    public ImageEntity modelToEntity(ImageInfoModel imageInfoModel) {
        UserEntity mockUserEntity = new UserEntity();
        mockUserEntity.setUid(imageInfoModel.getUid());
        return ImageEntity.builder()
                .id(imageInfoModel.getId())
                .user(mockUserEntity)
                .title(imageInfoModel.getTitle())
                .author(imageInfoModel.getAuthor())
                .publicationDate(imageInfoModel.getPublicationDate())
                .description(imageInfoModel.getDescription())
                //TODO: tags
                //TODO: sharedUserIds
                .status(imageInfoModel.getStatus())
                .privacy(imageInfoModel.getPrivacy())
                .createdTime(imageInfoModel.getCreatedTime())
                .updatedTime(imageInfoModel.getUpdatedTime())
                .build();
    }

    @Override
    public ImageInfoModel entityToModel(ImageEntity imageEntity) {
        return ImageInfoModel.builder()
                .id(imageEntity.getId())
                .uid(imageEntity.getUser().getUid())
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
