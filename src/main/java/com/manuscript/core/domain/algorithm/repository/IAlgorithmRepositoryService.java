package com.manuscript.core.domain.algorithm.repository;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.core.domain.common.enums.AlgorithmStatus;
import com.manuscript.core.domain.common.repository.IBaseRepositoryService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IAlgorithmRepositoryService extends IBaseRepositoryService<AlgorithmModel> {
    Optional<AlgorithmModel> getByUrl(String url);
    List<AlgorithmModel> getAllByUid(String uid);
    List<AlgorithmModel> getAllRunnable(String uid);
    List<AlgorithmModel> getAllByAlgorithmStatuses(Set<AlgorithmStatus> statuses);
    void deleteByUrl(String url);
}
