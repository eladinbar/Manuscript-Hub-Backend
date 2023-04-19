package com.manuscript.core.usecase.common;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GetAllUseCaseImpl<M> implements IGetAllUseCase<M> {

    private final IBaseRepositoryService<M> _serviceRepo;

    @Override
    public List<M> getAll() {
        return _serviceRepo.getAll();
    }
}
