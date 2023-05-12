package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.core.usecase.common.DeleteByIdUseCaseImpl;

public class DeleteByIdImageImpl extends DeleteByIdUseCaseImpl<ImageModel> implements IDeleteByIdImage{
    public DeleteByIdImageImpl(IBaseRepositoryService<ImageModel> _serviceRepo) { super(_serviceRepo); }
}
