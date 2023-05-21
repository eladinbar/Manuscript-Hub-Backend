package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.image.models.ImageInfoModel;
import com.manuscript.core.usecase.common.CreateUseCaseImpl;

public class CreateImageImpl extends CreateUseCaseImpl<ImageInfoModel> implements ICreateImage {
    public CreateImageImpl(IBaseRepositoryService<ImageInfoModel> _serviceRepo) { super(_serviceRepo); }
}
