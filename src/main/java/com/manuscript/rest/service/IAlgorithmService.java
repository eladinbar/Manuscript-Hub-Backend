package com.manuscript.rest.service;

import com.manuscript.rest.request.AlgorithmRequest;
import com.manuscript.rest.response.AlgorithmResponse;

import java.util.UUID;

public interface IAlgorithmService {
    void run(AlgorithmRequest algorithmRequest);
    AlgorithmResponse create(AlgorithmRequest algorithmRequest);
    AlgorithmResponse update(AlgorithmRequest algorithmRequest);
    AlgorithmResponse getById(UUID algorithmId);
    void delete(UUID id, String uid);
    void deleteAllByUserId(UUID userId);
}
