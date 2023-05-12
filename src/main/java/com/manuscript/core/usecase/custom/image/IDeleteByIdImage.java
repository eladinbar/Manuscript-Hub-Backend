package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.core.usecase.common.IDeleteByIdUseCase;

import java.util.UUID;

public interface IDeleteByIdImage extends IDeleteByIdUseCase<ImageModel> {
    @Override
    void deleteById(UUID id) throws IllegalArgumentException;
}
