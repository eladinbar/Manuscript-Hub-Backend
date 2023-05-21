package com.manuscript.core.usecase.custom.image;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.image.models.ImageInfoModel;
import com.manuscript.core.usecase.common.UpdateUseCaseImpl;

public class UpdateImageImpl extends UpdateUseCaseImpl<ImageInfoModel> implements IUpdateImage {

    public UpdateImageImpl(IBaseRepositoryService<ImageInfoModel> _serviceRepo) {
        super(_serviceRepo);
    }
}
