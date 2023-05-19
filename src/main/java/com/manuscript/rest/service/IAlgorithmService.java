package com.manuscript.rest.service;

import com.manuscript.rest.request.AlgorithmRequest;
import com.manuscript.rest.response.AlgorithmResponse;

import java.util.List;
import java.util.UUID;

public interface IAlgorithmService {
    void run(AlgorithmRequest algorithmRequest);
    AlgorithmResponse create(AlgorithmRequest algorithmRequest);
    AlgorithmResponse update(AlgorithmRequest algorithmRequest);
    AlgorithmResponse getById(UUID algorithmId);
    AlgorithmResponse getByUrl(String url);
    List<AlgorithmResponse> getAll();
    void deleteById(UUID id, String uid);
    void deleteByUrl(String url, String uid);
    void deleteAllByUserId(UUID userId);
}
