package com.manuscript.core.usecase.custom.algorithm;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.core.domain.algorithm.repository.IAlgorithmRepositoryService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GetAllByUidRunnableAlgorithmsImpl implements IGetAllByUidRunnableAlgorithms{
    private final IAlgorithmRepositoryService _serviceRepo;
    @Override
    public List<AlgorithmModel> getAllRunnable(String uid) {
        return _serviceRepo.getAllRunnable(uid);
    }
}
