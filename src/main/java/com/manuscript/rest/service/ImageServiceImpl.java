package com.manuscript.rest.service;


import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.core.usecase.common.IGetByIdUseCase;
import com.manuscript.core.usecase.custom.image.ICreateImage;
import com.manuscript.rest.mapping.ImageRequestMapperImpl;
import com.manuscript.rest.mapping.ImageResponseMapperImpl;
import com.manuscript.rest.request.ImageRequest;
import com.manuscript.rest.response.ImageResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class ImageServiceImpl implements IImageService {
    private final ImageRequestMapperImpl imageRequestMapper;
    private final ImageResponseMapperImpl imageResponseMapper;
    private final ICreateImage createImageUseCase;
    private final IGetByIdUseCase<ImageModel> getByIdDocumentUseCase;

    @Override
    public ImageResponse getById(UUID id) {
        return imageResponseMapper.modelToRest(getByIdDocumentUseCase.getById(id).get());
        //todo: need to change if represent
    }

    @Override
    public void save(ImageRequest imageRequest) {
        ImageModel model = imageRequestMapper.restToModel(imageRequest);
        createImageUseCase.create(model);
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public void update(ImageRequest imageRequest) {

    }
}
