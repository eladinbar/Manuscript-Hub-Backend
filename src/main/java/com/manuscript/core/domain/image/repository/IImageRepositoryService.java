package com.manuscript.core.domain.image.repository;


import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.image.models.ImageInfoModel;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IImageRepositoryService extends IBaseRepositoryService<ImageInfoModel> {
    List<ImageInfoModel> getAllByUidImageInfos(String userId);
    List<ImageInfoModel> getAllPublicImages();
    List<ImageInfoModel> getAllSharedImages(String userId);
    List<String> getAllEmailsByImageInfoId(UUID imageInfoId, String ownerUid);
    Map<Privacy, List<ImageInfoModel>> getImageInfoByTextSearch(String searchText, String userId);
    ImageInfoModel transferOwnership(ImageInfoModel imageInfoModel, String newOwnerUid);
}
