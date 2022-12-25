package com.manuscript.rest.mapping;

import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.rest.request.ImageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ImageRequestMapperImpl implements IRestMapper<ImageModel, ImageRequest> {
    public ImageRequest modelToRest(ImageModel model) {
        return ImageRequest.builder()
                .fileName(model.getFileName())
                .data(model.getData())
                .documentId(model.getId())
                .build();

    }

    public ImageModel restToModel(ImageRequest rest) {
        return ImageModel.builder()
                .fileName(rest.getFileName())
                .data(rest.getData())
                .id(rest.getDocumentId())
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();
    }
}
