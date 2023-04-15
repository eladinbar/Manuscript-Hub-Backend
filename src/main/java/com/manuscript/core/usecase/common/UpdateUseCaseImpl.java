package com.manuscript.core.usecase.common;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;

public class UpdateUseCaseImpl<M> implements IUpdateUseCase<M> {

    private final IBaseRepositoryService<M> _serviceRepo;

    public UpdateUseCaseImpl(IBaseRepositoryService<M> serviceRepo) {
        this._serviceRepo = serviceRepo;
    }

    @Override
    public M update(M model) throws IllegalArgumentException {
        return _serviceRepo.update(model);
    }
}
