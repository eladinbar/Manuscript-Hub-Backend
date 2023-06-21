package com.manuscript.infrastructure.persistence.sql.mapping;

import com.manuscript.core.domain.image.models.ImageInfoModel;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.infrastructure.persistence.sql.entities.ImageEntity;
import com.manuscript.infrastructure.persistence.sql.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
                .tags(new JSONArray(imageInfoModel.getTags()).toString())
                .sharedUserIds(new JSONArray(imageInfoModel.getSharedUserIds()).toString())
                .status(imageInfoModel.getStatus())
                .privacy(imageInfoModel.getPrivacy())
                .createdTime(imageInfoModel.getCreatedTime())
                .updatedTime(imageInfoModel.getUpdatedTime())
                .build();
    }

    public ImageInfoModel entityToModel(ImageEntity imageEntity) {
        List<String> tagsList = new ArrayList<>();
        List<String> sharedUserIdsList = new ArrayList<>();

        //Ensure the lists are not empty ("[]" is considered as empty)
        if(imageEntity.getTags() != null && imageEntity.getTags().length() > 2) {
            tagsList = Arrays.stream(imageEntity.getTags().substring(1, imageEntity.getTags().length() - 1).split(","))
                    .map(element -> element.trim().replaceAll("^\"|\"$", ""))
                    .collect(Collectors.toList());
        }

        if(imageEntity.getSharedUserIds() != null && imageEntity.getSharedUserIds().length() > 2) {
            sharedUserIdsList = Arrays.stream(imageEntity.getSharedUserIds().substring(1, imageEntity.getSharedUserIds().length() - 1).split(","))
                    .map(element -> element.trim().replaceAll("^\"|\"$", ""))
                    .collect(Collectors.toList());
        }

        return ImageInfoModel.builder()
                .id(imageEntity.getId())
                .uid(imageEntity.getUser().getUid())
                .title(imageEntity.getTitle())
                .author(imageEntity.getAuthor())
                .publicationDate(imageEntity.getPublicationDate())
                .description(imageEntity.getDescription())
                .tags(tagsList)
                .sharedUserIds(sharedUserIdsList)
                .status(imageEntity.getStatus())
                .privacy(imageEntity.getPrivacy())
                .createdTime(imageEntity.getCreatedTime())
                .updatedTime(imageEntity.getUpdatedTime())
                .build();
    }
}
