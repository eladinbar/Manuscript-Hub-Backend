package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.image.models.ImageDataModel;
import com.manuscript.core.usecase.common.DeleteByIdUseCaseImpl;

public class DeleteByIdImageDataImpl extends DeleteByIdUseCaseImpl<ImageDataModel> implements IDeleteByIdImageData {
    public DeleteByIdImageDataImpl(IBaseRepositoryService<ImageDataModel> _serviceRepo) { super(_serviceRepo); }
}
