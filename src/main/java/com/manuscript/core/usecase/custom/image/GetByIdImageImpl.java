package com.manuscript.core.usecase.custom.image;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.core.usecase.common.GetByIdUseCaseImpl;

public class GetByIdImageImpl extends GetByIdUseCaseImpl<ImageModel> implements IGetByIdImage {

    public GetByIdImageImpl(IBaseRepositoryService<ImageModel> _serviceRepo) {
        super(_serviceRepo);
    }
}
