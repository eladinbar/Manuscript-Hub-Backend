package com.manuscript.rest.mapping.response;

import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.rest.forms.response.ImageResponse;
import com.manuscript.rest.mapping.IRestMapper;
import org.springframework.stereotype.Service;

@Service
public class ImageResponseMapperImpl implements IRestMapper<ImageModel, ImageResponse> {
    @Override
    public ImageResponse modelToRest(ImageModel imageModel) {
        return ImageResponse.builder()
                .id(imageModel.getId())
                .uid(imageModel.getUid())
                .title(imageModel.getTitle())
                .author(imageModel.getAuthor())
                .publicationDate(imageModel.getPublicationDate())
                .description(imageModel.getDescription())
                .tags(imageModel.getTags())
                .sharedUserIds(imageModel.getSharedUserIds())
                .status(imageModel.getStatus())
                .privacy(imageModel.getPrivacy())
                .createdTime(imageModel.getCreatedTime())
                .updatedTime(imageModel.getUpdatedTime())
                .build();
    }

    @Override
    public ImageModel restToModel(ImageResponse imageResponse) {
        return ImageModel.builder()
                .id(imageResponse.getId())
                .uid(imageResponse.getUid())
                .title(imageResponse.getTitle())
                .author(imageResponse.getAuthor())
                .publicationDate(imageResponse.getPublicationDate())
                .description(imageResponse.getDescription())
                .tags(imageResponse.getTags())
                .sharedUserIds(imageResponse.getSharedUserIds())
                .status(imageResponse.getStatus())
                .privacy(imageResponse.getPrivacy())
                .createdTime(imageResponse.getCreatedTime())
                .updatedTime(imageResponse.getUpdatedTime())
                .build();
    }
}
