package com.manuscript.core.domain.algorithm.repository;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.core.domain.common.repository.IBaseRepositoryService;

import java.util.Optional;

public interface IAlgorithmRepositoryService extends IBaseRepositoryService<AlgorithmModel> {
    Optional<AlgorithmModel> getByUrl(String url);
    void deleteByUrl(String url);
}
