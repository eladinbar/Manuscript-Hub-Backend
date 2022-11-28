package com.manuscript.core.usecase.common;

public interface ICreateUseCase<M> {
    M create(M model) throws IllegalArgumentException;
}
