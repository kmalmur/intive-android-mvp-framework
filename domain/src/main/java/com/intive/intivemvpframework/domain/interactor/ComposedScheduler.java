package com.intive.intivemvpframework.domain.interactor;

import rx.Observable;

public interface ComposedScheduler {

    /**
     * Apply a thread scheduling {@link rx.Observable.Transformer} to an observable.
     *
     * @param <T> the {@link Observable} type.
     * @return the newly composed observable.
     */
    <T> Observable.Transformer<T, T> applyScheduling();
}