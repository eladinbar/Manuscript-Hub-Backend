package com.manuscript.rest.service;

import com.manuscript.core.domain.common.enums.AlgorithmStatus;
import com.manuscript.rest.forms.request.AlgorithmRequest;
import com.manuscript.rest.forms.response.AlgorithmResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IAlgorithmService {
    void run(AlgorithmRequest algorithmRequest) throws Exception;
    AlgorithmResponse create(AlgorithmRequest algorithmRequest);
    AlgorithmResponse update(AlgorithmRequest algorithmRequest);
    AlgorithmResponse getById(UUID algorithmId);
    AlgorithmResponse getByUrl(String url);
    List<AlgorithmResponse> getAllByUid(String uid);
    List<AlgorithmResponse> getAllRunnable(String uid);
    List<AlgorithmResponse> getAllByAlgorithmStatuses(Set<AlgorithmStatus> algorithmStatuses, String uid);
    List<AlgorithmResponse> getAll(String uid);
    void deleteById(UUID id, String uid);
    void deleteByUrl(String url, String uid);
    void deleteAllByUid(String uid, String adminUid);
}
