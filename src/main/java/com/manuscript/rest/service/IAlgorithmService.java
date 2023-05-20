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
    List<AlgorithmResponse> getAll(String uid);
    List<AlgorithmResponse> getAllByUid(String uid);
    void deleteById(UUID id, String uid);
    void deleteByUrl(String url, String uid);
    void deleteAllByUid(String uid, String adminUid);
}
