package com.manuscript.rest.mapping.request;

import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.rest.forms.request.ImageInfoRequest;
import com.manuscript.rest.mapping.IRestMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ImageRequestMapperImpl implements IRestMapper<ImageModel, ImageInfoRequest> {

    @Override
    public ImageInfoRequest modelToRest(ImageModel imageModel) {
        return ImageInfoRequest.builder()
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
                .build();
    }

    @Override
    public ImageModel restToModel(ImageInfoRequest imageInfoRequest) {
        return ImageModel.builder()
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
