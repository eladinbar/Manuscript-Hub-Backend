package com.manuscript.rest.mapping;

import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.rest.request.ImageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ImageRequestMapperImpl implements IRestMapper<ImageModel, ImageRequest> {
    public ImageRequest modelToRest(ImageModel imageModel) {
        return ImageRequest.builder()
                .fileName(imageModel.getFileName())
                .data(imageModel.getData())
                .documentId(imageModel.getId())
                .status(imageModel.getStatus())
                .uid(imageModel.getUid())
                .build();

    }

    public ImageModel restToModel(ImageRequest imageRequest) {
        return ImageModel.builder()
                .fileName(imageRequest.getFileName())
                .data(imageRequest.getData())
                .id(imageRequest.getDocumentId())
                .uid(imageRequest.getUid())
                .createdTime(new Date())
                .updatedTime(new Date())
                .status(imageRequest.getStatus())
                .build();
    }
}
