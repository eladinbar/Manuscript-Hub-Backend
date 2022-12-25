package com.manuscript.core.usecase.custom.image;


import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.core.usecase.common.ICreateUseCase;

public interface ICreateImage extends ICreateUseCase<ImageModel> {
    @Override
    ImageModel create(ImageModel model) throws IllegalArgumentException;
}
