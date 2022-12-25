package com.manuscript.rest.mapping;

import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.rest.response.ImageResponse;
import org.springframework.stereotype.Service;

@Service
public class ImageResponseMapperImpl implements IRestMapper<ImageModel, ImageResponse> {
    public ImageResponse modelToRest(ImageModel model) {
        return ImageResponse.builder()
                .documentId(model.getId())
                .fileName(model.getFileName())
                .data(model.getData())
                .build();
    }

    public ImageModel restToModel(ImageResponse rest) {
        return ImageModel.builder()
                .id(rest.getDocumentId())
                .createdTime(rest.getCreatedTime())
                .updatedTime(rest.getUpdatedTime())
                .fileName(rest.getFileName())
                .data(rest.getData())
                .build();
    }
}
