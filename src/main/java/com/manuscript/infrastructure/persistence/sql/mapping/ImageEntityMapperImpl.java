package com.manuscript.infrastructure.persistence.sql.mapping;

import com.manuscript.core.domain.image.models.ImageInfoModel;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.infrastructure.persistence.sql.entities.ImageEntity;
import com.manuscript.infrastructure.persistence.sql.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.json.JSONArray;

import java.util.Collections;
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
                .tags(new JSONArray(imageInfoModel.getTags()))
                .sharedUserIds(new JSONArray(imageInfoModel.getSharedUserIds()))
                .status(imageInfoModel.getStatus())
                .privacy(imageInfoModel.getPrivacy())
                .createdTime(imageInfoModel.getCreatedTime())
                .updatedTime(imageInfoModel.getUpdatedTime())
                .build();
    }

    public ImageInfoModel entityToModel(ImageEntity imageEntity) {
        List<String> tagsList = getNullableList(imageEntity.getTags());
        List<String> sharedUserIdsList = getNullableList(imageEntity.getSharedUserIds());

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

    private List<String> getNullableList(JSONArray jsonArray) {
        if (jsonArray != null) {
            return jsonArray.toList()
                    .stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
}
