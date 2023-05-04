package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.image.models.ImageDataModel;
import com.manuscript.core.domain.image.models.ImageModel;

import java.util.List;

public interface IGetAllByUidImages {
    List<ImageModel> getAllByUidImages(String userId);
}
