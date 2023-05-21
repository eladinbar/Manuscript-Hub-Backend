package com.manuscript.rest.mapping.response;

import com.manuscript.core.domain.image.models.ImageInfoModel;
import com.manuscript.rest.forms.response.ImageInfoResponse;
import com.manuscript.rest.mapping.IRestMapper;
import org.springframework.stereotype.Service;

@Service
public class ImageResponseMapperImpl implements IRestMapper<ImageInfoModel, ImageInfoResponse> {
    @Override
    public ImageInfoResponse modelToRest(ImageInfoModel imageInfoModel) {
        return ImageInfoResponse.builder()
                .id(imageInfoModel.getId())
                .uid(imageInfoModel.getUid())
                .title(imageInfoModel.getTitle())
                .author(imageInfoModel.getAuthor())
                .publicationDate(imageInfoModel.getPublicationDate())
                .description(imageInfoModel.getDescription())
                .tags(imageInfoModel.getTags())
                .sharedUserIds(imageInfoModel.getSharedUserIds())
                .status(imageInfoModel.getStatus())
                .privacy(imageInfoModel.getPrivacy())
                .createdTime(imageInfoModel.getCreatedTime())
                .updatedTime(imageInfoModel.getUpdatedTime())
                .build();
    }

    @Override
    public ImageInfoModel restToModel(ImageInfoResponse imageInfoResponse) {
        return ImageInfoModel.builder()
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
