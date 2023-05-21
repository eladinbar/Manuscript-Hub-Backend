package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.image.models.ImageDataModel;
import com.manuscript.core.usecase.common.CreateUseCaseImpl;

public class CreateImageDataImpl extends CreateUseCaseImpl<ImageDataModel> implements ICreateImageData{
    public CreateImageDataImpl(IBaseRepositoryService<ImageDataModel> _serviceRepo) {
        super(_serviceRepo);
    }
}
