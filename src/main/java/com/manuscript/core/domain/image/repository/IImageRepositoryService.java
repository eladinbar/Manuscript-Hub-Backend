package com.manuscript.core.domain.image.repository;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.image.models.ImageModel;

import java.util.List;

public interface IImageRepositoryService extends IBaseRepositoryService<ImageModel> {
    List<ImageModel> getAllByUidImages(String userId);
    List<ImageModel> getAllPublicImages();
    List<ImageModel> getAllSharedImages(String userId);
}
