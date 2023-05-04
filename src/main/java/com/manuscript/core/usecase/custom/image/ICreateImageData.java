package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.image.models.ImageDataModel;
import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.core.usecase.common.ICreateUseCase;

public interface ICreateImageData extends ICreateUseCase<ImageDataModel> {
    @Override
    ImageDataModel create(ImageDataModel imageDataModel) throws IllegalArgumentException;
}
