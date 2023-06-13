package com.manuscript.rest.mapping.request;

import com.manuscript.core.domain.image.models.ImageDataModel;
import com.manuscript.rest.forms.request.ImageDataRequest;
import com.manuscript.rest.mapping.IRestMapper;
import org.springframework.stereotype.Service;

@Service
public class ImageDataRequestMapperImpl implements IRestMapper<ImageDataModel, ImageDataRequest> {

    @Override
    public ImageDataRequest modelToRest(ImageDataModel imageDataModel){
        return ImageDataRequest.builder()
                .imageDataId(imageDataModel.getId())
                .imageId(imageDataModel.getImageId())
                .fileName(imageDataModel.getFileName())
                .data(imageDataModel.getData())
                .index(imageDataModel.getIndex())
                .build();
    }

    @Override
    public ImageDataModel restToModel(ImageDataRequest imageDataRequest){
        return ImageDataModel.builder()
                .id(imageDataRequest.getImageDataId())
                .imageId(imageDataRequest.getImageId())
                .fileName(imageDataRequest.getFileName())
                .data(imageDataRequest.getData())
                .index(imageDataRequest.getIndex())
                .build();
    }
}
