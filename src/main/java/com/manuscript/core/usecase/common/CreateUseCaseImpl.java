package com.manuscript.core.usecase.common;

import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateUseCaseImpl<M> implements ICreateUseCase<M> {

    private final IBaseRepositoryService<M> _serviceRepo;

    @Override
    public M create(M model) throws IllegalArgumentException {
        return _serviceRepo.save(model);
    }

}
