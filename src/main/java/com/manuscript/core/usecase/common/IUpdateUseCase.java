package com.manuscript.core.usecase.common;

public interface IUpdateUseCase<M> {
    M update(M model) throws IllegalArgumentException;
}
