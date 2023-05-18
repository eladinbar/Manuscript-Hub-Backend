package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.image.models.ImageInfoModel;
import com.manuscript.core.domain.image.repository.IImageRepositoryService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class GetImageInfoByTextSearchImpl implements IGetImageInfoByTextSearch{
    private final IImageRepositoryService _serviceRepo;
    @Override
    public Map<Privacy, List<ImageInfoModel>> getImageInfoByTextSearch(String searchText, String userId) { return _serviceRepo.getImageInfoByTextSearch(searchText, userId); }
}
