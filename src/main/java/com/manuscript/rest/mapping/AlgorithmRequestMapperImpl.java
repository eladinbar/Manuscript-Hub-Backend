package com.manuscript.rest.mapping;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.rest.request.AlgorithmRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AlgorithmRequestMapperImpl implements IRestMapper<AlgorithmModel, AlgorithmRequest> {
    @Override
    public AlgorithmRequest modelToRest(AlgorithmModel algorithmModel) {
        return AlgorithmRequest.builder()
                .algorithmId(algorithmModel.getAlgorithmId())
                .userId(algorithmModel.getUserId())
                .url(algorithmModel.getUrl())
                .build();
    }

    @Override
    public AlgorithmModel restToModel(AlgorithmRequest algorithmRequest) {
        return AlgorithmModel.builder()
                .algorithmId(algorithmRequest.getAlgorithmId())
                .userId(algorithmRequest.getUserId())
                .url(algorithmRequest.getUrl())
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();
    }
}
