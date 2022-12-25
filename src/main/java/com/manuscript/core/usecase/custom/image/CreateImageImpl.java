package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.core.usecase.common.CreateUseCaseImpl;

public class CreateImageImpl extends CreateUseCaseImpl<ImageModel> implements ICreateImage {
    private final IBaseRepositoryService<ImageModel> _serviceRepo;

    public CreateImageImpl(IBaseRepositoryService<ImageModel> _serviceRepo) {
        super(_serviceRepo);
        this._serviceRepo = _serviceRepo;
    }

    @Override
    public ImageModel create(ImageModel imageModel) throws IllegalArgumentException {
        return _serviceRepo.save(imageModel);
    }
}
