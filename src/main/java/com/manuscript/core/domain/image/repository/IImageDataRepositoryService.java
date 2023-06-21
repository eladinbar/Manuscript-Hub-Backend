package com.manuscript.core.domain.image.repository;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.image.models.ImageDataModel;

import java.util.List;
import java.util.UUID;

public interface IImageDataRepositoryService extends IBaseRepositoryService<ImageDataModel> {
    List<ImageDataModel> getAllByImageIdData(UUID imageId);

}
