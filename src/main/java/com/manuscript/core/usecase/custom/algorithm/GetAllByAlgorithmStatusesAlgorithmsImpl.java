package com.manuscript.core.usecase.custom.algorithm;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.core.domain.algorithm.repository.IAlgorithmRepositoryService;
import com.manuscript.core.domain.common.enums.AlgorithmStatus;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class GetAllByAlgorithmStatusesAlgorithmsImpl implements IGetAllByAlgorithmStatusesAlgorithms {
    private final IAlgorithmRepositoryService _serviceRepo;

    @Override
    public List<AlgorithmModel> getAllByAlgorithmStatuses(Set<AlgorithmStatus> algorithmStatuses) throws IllegalArgumentException {
        return _serviceRepo.getAllByAlgorithmStatuses(algorithmStatuses);
    }
}
