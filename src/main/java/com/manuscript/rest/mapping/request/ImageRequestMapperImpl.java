package com.manuscript.rest.mapping.request;

import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.rest.forms.request.ImageRequest;
import com.manuscript.rest.mapping.IRestMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ImageRequestMapperImpl implements IRestMapper<ImageModel, ImageRequest> {

    @Override
    public ImageRequest modelToRest(ImageModel imageModel) {
        return ImageRequest.builder()
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
    public ImageModel restToModel(ImageRequest imageRequest) {
        return ImageModel.builder()
                .id(imageRequest.getId())
                .uid(imageRequest.getUid())
                .title(imageRequest.getTitle())
                .author(imageRequest.getAuthor())
                .publicationDate(imageRequest.getPublicationDate())
                .description(imageRequest.getDescription())
                .tags(imageRequest.getTags())
                .sharedUserIds(imageRequest.getSharedUserIds())
                .status(imageRequest.getStatus())
                .privacy(imageRequest.getPrivacy())
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();
    }
}
