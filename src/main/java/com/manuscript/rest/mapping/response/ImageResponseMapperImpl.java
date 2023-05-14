package com.manuscript.rest.mapping.response;

import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.rest.forms.response.ImageInfoResponse;
import com.manuscript.rest.mapping.IRestMapper;
import org.springframework.stereotype.Service;

@Service
public class ImageResponseMapperImpl implements IRestMapper<ImageModel, ImageInfoResponse> {
    @Override
    public ImageInfoResponse modelToRest(ImageModel imageModel) {
        return ImageInfoResponse.builder()
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
    public ImageModel restToModel(ImageInfoResponse imageInfoResponse) {
        return ImageModel.builder()
                .id(imageInfoResponse.getId())
                .uid(imageInfoResponse.getUid())
                .title(imageInfoResponse.getTitle())
                .author(imageInfoResponse.getAuthor())
                .publicationDate(imageInfoResponse.getPublicationDate())
                .description(imageInfoResponse.getDescription())
                .tags(imageInfoResponse.getTags())
                .sharedUserIds(imageInfoResponse.getSharedUserIds())
                .status(imageInfoResponse.getStatus())
                .privacy(imageInfoResponse.getPrivacy())
                .createdTime(imageInfoResponse.getCreatedTime())
                .updatedTime(imageInfoResponse.getUpdatedTime())
                .build();
    }
}
