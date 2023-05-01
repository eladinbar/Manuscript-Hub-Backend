package com.manuscript.core.usecase.custom.algorithm;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.core.usecase.common.ICreateUseCase;

public interface ICreateAlgorithm extends ICreateUseCase<AlgorithmModel> {
    @Override
    AlgorithmModel create(AlgorithmModel model) throws IllegalArgumentException;
}
