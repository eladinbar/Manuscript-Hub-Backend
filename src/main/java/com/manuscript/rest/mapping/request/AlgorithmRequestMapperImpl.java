package com.manuscript.rest.mapping.request;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.rest.forms.request.AlgorithmRequest;
import com.manuscript.rest.mapping.IRestMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AlgorithmRequestMapperImpl implements IRestMapper<AlgorithmModel, AlgorithmRequest> {
    @Override
    public AlgorithmRequest modelToRest(AlgorithmModel algorithmModel) {
        return AlgorithmRequest.builder()
                .id(algorithmModel.getId())
                .uid(algorithmModel.getUid())
                .url(algorithmModel.getUrl())
                .build();
    }

    @Override
    public AlgorithmModel restToModel(AlgorithmRequest algorithmRequest) {
        return AlgorithmModel.builder()
                .id(algorithmRequest.getId())
                .uid(algorithmRequest.getUid())
                .url(algorithmRequest.getUrl())
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();
    }
}
