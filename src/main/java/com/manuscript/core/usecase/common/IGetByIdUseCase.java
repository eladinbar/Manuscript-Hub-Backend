package com.manuscript.core.usecase.common;

import java.util.Optional;
import java.util.UUID;

public interface IGetByIdUseCase<M> {
    Optional<M> getById(UUID id) throws IllegalArgumentException;
}
//