package com.intive.intivemvpframework.data.executor;

import com.intive.intivemvpframework.domain.interactor.ComposedScheduler;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AndroidComposedScheduler implements ComposedScheduler {

    private final JobExecutor mJobExecutor;

    @Inject
    public AndroidComposedScheduler() {
        mJobExecutor = new JobExecutor();
    }

    @Override
    public <T> Observable.Transformer<T, T> applyScheduling() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(final Observable<T> tObservable) {
                return tObservable
                        .subscribeOn(Schedulers.from(mJobExecutor))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}