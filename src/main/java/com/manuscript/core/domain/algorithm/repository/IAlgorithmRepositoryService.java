package com.manuscript.core.domain.algorithm.repository;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.core.domain.common.repository.IBaseRepositoryService;

import java.util.List;
import java.util.Optional;

public interface IAlgorithmRepositoryService extends IBaseRepositoryService<AlgorithmModel> {
    Optional<AlgorithmModel> getByUrl(String url);
    List<AlgorithmModel> getAllByUid(String uid);
    void deleteByUrl(String url);
}
