package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.image.models.ImageDataModel;
import com.manuscript.core.usecase.common.GetByIdUseCaseImpl;

public class GetByIdImageDataImpl extends GetByIdUseCaseImpl<ImageDataModel> implements IGetByIdImageData{

    public GetByIdImageDataImpl(IBaseRepositoryService<ImageDataModel> _serviceRepo) {
        super(_serviceRepo);
    }
}
