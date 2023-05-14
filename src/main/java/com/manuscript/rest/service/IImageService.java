package com.manuscript.rest.service;

import com.manuscript.core.exceptions.NoImageFoundException;
import com.manuscript.core.exceptions.NoUserFoundException;
import com.manuscript.core.exceptions.UnauthorizedException;
import com.manuscript.rest.forms.request.ImageDataRequest;
import com.manuscript.rest.forms.request.ImageInfoRequest;
import com.manuscript.rest.forms.response.ImageDataResponse;
import com.manuscript.rest.forms.response.ImageInfoResponse;

import java.util.List;
import java.util.UUID;

public interface IImageService {

    ImageInfoResponse save(ImageInfoRequest imageInfoRequest)                                   throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException;
    ImageDataResponse saveData(ImageDataRequest imageDataRequest)                   throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException;
    ImageInfoResponse update(ImageInfoRequest imageInfoRequest)                                 throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException;
    ImageInfoResponse getById(UUID imageId, String userId)                              throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException;
    List<ImageDataResponse> getAllByIdData(UUID imageId, String userId)             throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException;
    List<ImageInfoResponse> getAllByUid(String userId)                                  throws IllegalArgumentException, NoUserFoundException;
    List<ImageInfoResponse> getAllPublicImages();
    List<ImageInfoResponse> getAllSharedImages(String userId)                           throws IllegalArgumentException, NoUserFoundException;
    void deleteById(UUID imageId, String userId)                                    throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException;
    void deleteDataById(UUID imageDataId, String userId)                            throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException;



}
