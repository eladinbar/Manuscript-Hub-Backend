package com.manuscript.rest.service;

import com.manuscript.rest.request.ImageRequest;
import com.manuscript.rest.response.ImageResponse;

import java.util.List;
import java.util.UUID;

public interface IImageService {
    ImageResponse getById(UUID id);
    ImageResponse save(ImageRequest imageRequest);
    void deleteById(UUID id);
    ImageResponse update(ImageRequest imageRequest);
    List<ImageResponse> getAll();
    List<ImageResponse> getAllByUid(String uid);
    List<ImageResponse> getAllPublicImages();
}
