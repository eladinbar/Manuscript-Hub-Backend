package com.manuscript.core.usecase.custom.algorithm;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;

import java.util.List;

public interface IGetAllByUidAlgorithms {
    List<AlgorithmModel> getAllByUid(String uid) throws IllegalArgumentException;
}
