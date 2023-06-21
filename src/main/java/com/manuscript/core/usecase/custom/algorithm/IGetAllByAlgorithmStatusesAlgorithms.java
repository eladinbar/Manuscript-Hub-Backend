package com.manuscript.core.usecase.custom.algorithm;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.core.domain.common.enums.AlgorithmStatus;

import java.util.List;
import java.util.Set;

public interface IGetAllByAlgorithmStatusesAlgorithms {
    List<AlgorithmModel> getAllByAlgorithmStatuses(Set<AlgorithmStatus> algorithmStatuses) throws IllegalArgumentException;
}
