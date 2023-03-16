package com.manuscript.rest.service;

import com.manuscript.rest.request.ImageRequest;
import com.manuscript.rest.response.ImageResponse;

import java.util.List;
import java.util.UUID;

public interface IImageService {
    ImageResponse getById(UUID id);
    void save(ImageRequest imageRequest);
    void deleteById(UUID id);
    void update(ImageRequest imageRequest);
    List<ImageResponse> getAll();
    List<ImageResponse> getAllByUid(String uid);
}
