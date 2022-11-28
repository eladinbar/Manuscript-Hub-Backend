package com.manuscript.rest.mapping;

public interface IRestMapper<M, R> {

    default R modelToRest(final M model) {
        throw new UnsupportedOperationException();
    }

    default M restToModel(final R rest) {
        throw new UnsupportedOperationException();
    }
}
