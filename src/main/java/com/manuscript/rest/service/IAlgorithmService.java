package com.manuscript.rest.service;

import com.manuscript.rest.request.AlgorithmRequest;
import com.manuscript.rest.response.AlgorithmResponse;

import java.util.List;
import java.util.UUID;

public interface IAlgorithmService {
    AlgorithmResponse create(AlgorithmRequest algorithmRequest);
    AlgorithmResponse update(AlgorithmRequest algorithmRequest);
    AlgorithmResponse get(UUID algorithmId);
    AlgorithmResponse delete(AlgorithmRequest algorithmRequest);
    List<AlgorithmResponse> deleteAllByUserId(UUID userId);
}
