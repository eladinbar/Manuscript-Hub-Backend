package com.manuscript.core.usecase.custom.image;


import com.manuscript.core.domain.image.models.ImageInfoModel;
import com.manuscript.core.usecase.common.ICreateUseCase;

public interface ICreateImage extends ICreateUseCase<ImageInfoModel> {
    @Override
    ImageInfoModel create(ImageInfoModel imageInfoModel) throws IllegalArgumentException;
}
