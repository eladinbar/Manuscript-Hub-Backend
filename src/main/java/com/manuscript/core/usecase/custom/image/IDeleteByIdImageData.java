package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.image.models.ImageDataModel;
import com.manuscript.core.usecase.common.IDeleteByIdUseCase;

import java.util.UUID;

public interface IDeleteByIdImageData extends IDeleteByIdUseCase<ImageDataModel> {
    @Override
    void deleteById(UUID id) throws IllegalArgumentException;
}
