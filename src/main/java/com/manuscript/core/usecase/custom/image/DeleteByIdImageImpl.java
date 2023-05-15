package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.image.models.ImageInfoModel;
import com.manuscript.core.usecase.common.DeleteByIdUseCaseImpl;

public class DeleteByIdImageImpl extends DeleteByIdUseCaseImpl<ImageInfoModel> implements IDeleteByIdImage{
    public DeleteByIdImageImpl(IBaseRepositoryService<ImageInfoModel> _serviceRepo) { super(_serviceRepo); }
}
