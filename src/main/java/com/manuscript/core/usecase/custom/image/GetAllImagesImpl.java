package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.image.models.ImageInfoModel;
import com.manuscript.core.usecase.common.GetAllUseCaseImpl;

public class GetAllImagesImpl extends GetAllUseCaseImpl<ImageInfoModel> implements IGetAllImages {

    public GetAllImagesImpl(IBaseRepositoryService<ImageInfoModel> _serviceRepo) {
        super(_serviceRepo);
    }
}
