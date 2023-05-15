package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.image.models.ImageInfoModel;
import com.manuscript.core.usecase.common.IDeleteByIdUseCase;

import java.util.UUID;

public interface IDeleteByIdImage extends IDeleteByIdUseCase<ImageInfoModel> {
    @Override
    void deleteById(UUID id) throws IllegalArgumentException;
}
