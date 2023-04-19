package com.manuscript.rest.mapping;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.rest.response.AlgorithmResponse;
import org.springframework.stereotype.Service;

@Service
public class AlgorithmResponseMapperImpl implements IRestMapper<AlgorithmModel, AlgorithmResponse> {
    @Override
    public AlgorithmResponse modelToRest(AlgorithmModel algorithmModel) {
        return AlgorithmResponse.builder()
                .id(algorithmModel.getId())
                .uid(algorithmModel.getUid())
                .url(algorithmModel.getUrl())
                .createdTime(algorithmModel.getCreatedTime())
                .updatedTime(algorithmModel.getUpdatedTime())
                .build();
    }

    @Override
    public AlgorithmModel restToModel(AlgorithmResponse algorithmResponse) {
        return AlgorithmModel.builder()
                .id(algorithmResponse.getId())
                .uid(algorithmResponse.getUid())
                .url(algorithmResponse.getUrl())
                .createdTime(algorithmResponse.getCreatedTime())
                .updatedTime(algorithmResponse.getUpdatedTime())
                .build();
    }
}
