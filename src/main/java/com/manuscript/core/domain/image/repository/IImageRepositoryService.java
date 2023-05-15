package com.manuscript.core.domain.image.repository;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.image.models.ImageInfoModel;

import java.util.List;

public interface IImageRepositoryService extends IBaseRepositoryService<ImageInfoModel> {
    List<ImageInfoModel> getAllByUidImages(String userId);
    List<ImageInfoModel> getAllPublicImages();
    List<ImageInfoModel> getAllSharedImages(String userId);
}
