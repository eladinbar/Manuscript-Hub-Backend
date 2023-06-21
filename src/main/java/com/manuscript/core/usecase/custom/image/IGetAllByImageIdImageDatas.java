package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.image.models.ImageDataModel;

import java.util.List;
import java.util.UUID;

public interface IGetAllByImageIdImageDatas {
    List<ImageDataModel> getAllByImageId(UUID imageId);
}
