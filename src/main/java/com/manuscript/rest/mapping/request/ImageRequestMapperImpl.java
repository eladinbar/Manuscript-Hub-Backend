package com.manuscript.rest.mapping.request;

import com.manuscript.core.domain.image.models.ImageInfoModel;
import com.manuscript.rest.forms.request.ImageInfoRequest;
import com.manuscript.rest.mapping.IRestMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ImageRequestMapperImpl implements IRestMapper<ImageInfoModel, ImageInfoRequest> {

    @Override
    public ImageInfoRequest modelToRest(ImageInfoModel imageInfoModel) {
        return ImageInfoRequest.builder()
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
                .build();
    }

    @Override
    public ImageInfoModel restToModel(ImageInfoRequest imageInfoRequest) {
        return ImageInfoModel.builder()
                .id(imageInfoRequest.getId())
                .uid(imageInfoRequest.getUid())
                .title(imageInfoRequest.getTitle())
                .author(imageInfoRequest.getAuthor())
                .publicationDate(imageInfoRequest.getPublicationDate())
                .description(imageInfoRequest.getDescription())
                .tags(imageInfoRequest.getTags())
                .sharedUserIds(imageInfoRequest.getSharedUserIds())
                .status(imageInfoRequest.getStatus())
                .privacy(imageInfoRequest.getPrivacy())
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();
    }
}
