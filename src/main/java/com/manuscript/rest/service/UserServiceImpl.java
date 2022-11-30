package com.manuscript.rest.service;


import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.usecase.common.IGetByIdUseCase;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private final IRestMapper<UserModel, UserResponse> mapperResponseInfoModel;
    private final IGetByIdUseCase<UserModel> getByIdUserUseCase;

    @Override
    public UserResponse get(UUID id) {
        return mapperResponseInfoModel.modelToRest(getByIdUserUseCase.getById(id).get());
        //todo: need to change if represent
    }


}
