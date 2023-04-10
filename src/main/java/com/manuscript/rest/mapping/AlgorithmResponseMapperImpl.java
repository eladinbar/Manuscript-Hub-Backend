package com.manuscript.rest.mapping;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.rest.response.AlgorithmResponse;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AlgorithmResponseMapperImpl implements IRestMapper<AlgorithmModel, AlgorithmResponse> {
    @Override
    public AlgorithmResponse modelToRest(AlgorithmModel algorithmModel) {
        return AlgorithmResponse.builder()
                .algorithmId(algorithmModel.getAlgorithmId())
                .userId(algorithmModel.getUserId())
                .url(algorithmModel.getUrl())
                .createdTime(algorithmModel.getCreatedTime())
                .updatedTime(algorithmModel.getUpdatedTime())
                .build();
    }

    @Override
    public AlgorithmModel restToModel(AlgorithmResponse algorithmResponse) {
        return AlgorithmModel.builder()
                .algorithmId(algorithmResponse.getAlgorithmId())
                .userId(algorithmResponse.getUserId())
                .url(algorithmResponse.getUrl())
                .createdTime(algorithmResponse.getCreatedTime())
                .updatedTime(algorithmResponse.getUpdatedTime())
                .build();
    }
}
