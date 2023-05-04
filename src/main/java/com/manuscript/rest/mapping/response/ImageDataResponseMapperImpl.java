package com.manuscript.rest.mapping.response;

import com.manuscript.core.domain.image.models.ImageDataModel;
import com.manuscript.rest.forms.request.ImageDataRequest;
import com.manuscript.rest.forms.response.ImageDataResponse;
import com.manuscript.rest.mapping.IRestMapper;
import org.springframework.stereotype.Service;

@Service
public class ImageDataResponseMapperImpl implements IRestMapper<ImageDataModel, ImageDataResponse> {

    @Override
    public ImageDataResponse modelToRest(ImageDataModel imageDataModel){
        return ImageDataResponse.builder()
                .imageDataId(imageDataModel.getImageDataId())
                .imageId(imageDataModel.getImageId())
                .fileName(imageDataModel.getFileName())
                .data(imageDataModel.getData())
                .index(imageDataModel.getIndex())
                .build();
    }

    @Override
    public ImageDataModel restToModel(ImageDataResponse imageDataResponse){
        return ImageDataModel.builder()
                .imageDataId(imageDataResponse.getImageDataId())
                .imageId(imageDataResponse.getImageId())
                .fileName(imageDataResponse.getFileName())
                .data(imageDataResponse.getData())
                .index(imageDataResponse.getIndex())
                .build();
    }
}
