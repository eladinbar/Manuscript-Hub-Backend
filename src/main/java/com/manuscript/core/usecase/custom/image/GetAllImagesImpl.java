package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.core.usecase.common.GetAllUseCaseImpl;

public class GetAllImagesImpl extends GetAllUseCaseImpl<ImageModel> implements IGetAllImages {

    public GetAllImagesImpl(IBaseRepositoryService<ImageModel> _serviceRepo) {
        super(_serviceRepo);
    }
}
