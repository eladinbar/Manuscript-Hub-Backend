package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.image.models.ImageInfoModel;

import java.util.List;
import java.util.Map;

public interface IGetImageInfoByTextSearch {
    Map<Privacy, List<ImageInfoModel>> getImageInfoByTextSearch(String searchText, String userId);
}
