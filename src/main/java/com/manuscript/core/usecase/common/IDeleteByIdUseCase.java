package com.manuscript.core.usecase.common;

import java.util.UUID;

public interface IDeleteByIdUseCase<M> {
    void deleteById(M model) throws IllegalArgumentException;
}
