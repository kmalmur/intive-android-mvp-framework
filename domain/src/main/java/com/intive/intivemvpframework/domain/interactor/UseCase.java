package com.intive.intivemvpframework.domain.interactor;

import rx.Observable;
import rx.functions.Action0;

public abstract class UseCase<P, R> {

    private Observable<R> mObservable;

    private final ComposedScheduler mComposedScheduler;

    protected UseCase() {
        mComposedScheduler = null;
    }

    protected UseCase(final ComposedScheduler composedScheduler) {
        mComposedScheduler = composedScheduler;
    }

    /**
     * Checks if observable is currently executing a job.
     *
     * @return true if observable is active, false otherwise
     */
    public boolean isExecuting() {
        return mObservable != null;
    }

    protected abstract Observable<R> createUseCaseObservable(final P param);

    public Observable<R> execute(final P param) {

        if (mObservable == null) {
            mObservable = createUseCaseObservable(param);

            if (mComposedScheduler != null) {
                mObservable = mObservable.compose(mComposedScheduler.<R>applyScheduling());
            }

            mObservable = mObservable.cache()
                    .doOnTerminate(new Action0() {
                        @Override
                        public void call() {
                            mObservable = null;
                        }
                    });
        }

        return mObservable;
    }
}