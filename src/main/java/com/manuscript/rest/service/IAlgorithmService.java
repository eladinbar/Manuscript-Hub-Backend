package com.manuscript.rest.service;

import com.manuscript.rest.request.AlgorithmRequest;
import com.manuscript.rest.response.AlgorithmResponse;

import java.util.UUID;

public interface IAlgorithmService {
    AlgorithmResponse create(AlgorithmRequest algorithmRequest);
    AlgorithmResponse update(AlgorithmRequest algorithmRequest);
    AlgorithmResponse get(UUID algorithmId);
    void delete(AlgorithmRequest algorithmRequest);
    void deleteAllByUserId(UUID userId);
}
