package com.manuscript.core.usecase.common;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;

import java.util.List;

public class GetAllUseCaseImpl<M> implements IGetAllUseCase<M> {

    private final IBaseRepositoryService<M> _serviceRepo;

    public GetAllUseCaseImpl(IBaseRepositoryService<M> serviceRepo) {
        this._serviceRepo = serviceRepo;
    }

    @Override
    public List<M> getAll() {
        return _serviceRepo.getAll();
    }

}
