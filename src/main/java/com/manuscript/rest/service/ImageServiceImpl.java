package com.manuscript.rest.service;


import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.core.usecase.common.IGetByIdUseCase;
import com.manuscript.core.usecase.custom.image.ICreateImage;
import com.manuscript.core.usecase.custom.image.IGetAllImage;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.mapping.ImageRequestMapperImpl;
import com.manuscript.rest.request.ImageRequest;
import com.manuscript.rest.response.ImageResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ImageServiceImpl implements IImageService {
    private final ImageRequestMapperImpl imageRequestMapper;
    private final IRestMapper<ImageModel, ImageResponse> imageResponseMapper;
    private final ICreateImage createImageUseCase;
    private final IGetAllImage getAllImagesUseCase;
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

    @Override
    public List<ImageResponse> getAll() {
        return getAllImagesUseCase.getAll().stream().map(imageResponseMapper::modelToRest).collect(Collectors.toList());
    }

    @Override
    public List<ImageResponse> getAllByUid(String uid) {
        return getAllImagesUseCase.getAll().stream().map(imageResponseMapper::modelToRest).collect(Collectors.toList()).stream()
                .filter(image ->
                       image.getUid() != null && image.getUid().equals(uid)).collect(Collectors.toList());

    }
}
