package com.manuscript.rest.service;

import com.manuscript.core.exceptions.FailedUploadException;
import com.manuscript.core.exceptions.NoImageFoundException;
import com.manuscript.core.exceptions.NoUserFoundException;
import com.manuscript.core.exceptions.UnauthorizedException;
import com.manuscript.rest.forms.request.ImageDataRequest;
import com.manuscript.rest.forms.request.ImageRequest;
import com.manuscript.rest.forms.response.ImageDataResponse;
import com.manuscript.rest.forms.response.ImageResponse;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface IImageService {

    ImageResponse save(ImageRequest imageRequest)                                   throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException;
    ImageDataResponse saveData(ImageDataRequest imageDataRequest)                   throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException;
    ImageResponse update(ImageRequest imageRequest)                                 throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException;
    ImageResponse getById(UUID imageId, String userId)                              throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException;
    List<ImageDataResponse> getAllByIdData(UUID imageId, String userId)             throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException;
    List<ImageResponse> getAllByUid(String userId)                                  throws IllegalArgumentException, NoUserFoundException;
    List<ImageResponse> getAllPublicImages();
    List<ImageResponse> getAllSharedImages(String userId)                           throws IllegalArgumentException, NoUserFoundException;
    void deleteById(UUID imageId, String userId)                                    throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException;
    void deleteDataById(UUID imageDataId, String userId)                            throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException;



}
