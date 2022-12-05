package com.manuscript.core.usecase.custom.image;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.document.models.ImageModel;
import com.manuscript.core.usecase.common.UpdateUseCaseImpl;

public class UpdateImageImpl extends UpdateUseCaseImpl<ImageModel> implements IUpdateImage {

    public UpdateImageImpl(IBaseRepositoryService<ImageModel> _serviceRepo) {
        super(_serviceRepo);
    }
}
