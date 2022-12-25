package com.manuscript.rest.mapping;

import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.rest.request.ImageRequest;
import org.springframework.stereotype.Service;

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
                .build();
    }
}
