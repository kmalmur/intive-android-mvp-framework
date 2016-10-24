package com.intive.intivemvpframework.domain.interactor;

import rx.Observable;
import rx.schedulers.Schedulers;

public class TestComposedScheduler implements ComposedScheduler {

    @Override
    public <T> Observable.Transformer<T, T> applyScheduling() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(final Observable<T> tObservable) {
                return tObservable
                        .subscribeOn(Schedulers.immediate())
                        .observeOn(Schedulers.immediate());
            }
        };
    }
}