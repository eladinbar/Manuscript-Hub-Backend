package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.image.models.ImageInfoModel;

import java.util.List;

public interface IGetAllSharedImages {
    List<ImageInfoModel> getAllSharedImages(String userId);
}
