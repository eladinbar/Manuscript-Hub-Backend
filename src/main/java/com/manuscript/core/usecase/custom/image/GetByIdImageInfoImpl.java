package com.manuscript.core.usecase.custom.image;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.image.models.ImageInfoModel;
import com.manuscript.core.usecase.common.GetByIdUseCaseImpl;

public class GetByIdImageInfoImpl extends GetByIdUseCaseImpl<ImageInfoModel> implements IGetByIdImageInfo {

    public GetByIdImageInfoImpl(IBaseRepositoryService<ImageInfoModel> _serviceRepo) {
        super(_serviceRepo);
    }
}
