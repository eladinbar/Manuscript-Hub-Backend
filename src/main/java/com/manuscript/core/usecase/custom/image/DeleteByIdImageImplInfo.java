package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.image.models.ImageInfoModel;
import com.manuscript.core.usecase.common.DeleteByIdUseCaseImpl;

public class DeleteByIdImageImplInfo extends DeleteByIdUseCaseImpl<ImageInfoModel> implements IDeleteByIdImageInfo {
    public DeleteByIdImageImplInfo(IBaseRepositoryService<ImageInfoModel> _serviceRepo) { super(_serviceRepo); }
}
