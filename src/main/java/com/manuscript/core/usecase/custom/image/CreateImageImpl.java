package com.manuscript.core.usecase.custom.image;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.document.models.ImageModel;
import com.manuscript.core.usecase.common.CreateUseCaseImpl;

public class CreateImageImpl extends CreateUseCaseImpl<ImageModel> implements ICreateImage {

    public CreateImageImpl(IBaseRepositoryService<ImageModel> _serviceRepo) {
        super(_serviceRepo);
    }
}
