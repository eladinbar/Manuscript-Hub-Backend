package com.manuscript.rest.service;


import com.manuscript.core.domain.user.model.UserModel;
import com.manuscript.core.usecase.common.IGetAllUseCase;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class VideoInfoServiceImpl implements IVideoUser {
    private final IRestMapper<UserModel, UserResponse> mapperResponseInfoModel;
    private final IGetAllUseCase<UserModel> getAllInfosModels;

    @Override
    public List<UserResponse> getAll() {
        return this.getAllInfosModels.getAll().stream().map(mapperResponseInfoModel::modelToRest).collect(Collectors.toList());
    }


}
