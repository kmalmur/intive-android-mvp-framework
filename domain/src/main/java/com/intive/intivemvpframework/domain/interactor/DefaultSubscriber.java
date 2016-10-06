package com.intive.intivemvpframework.domain.interactor;

/**
 * Default subscriber base class to be used whenever you want default error handling.
 */
public class DefaultSubscriber<T> extends rx.Subscriber<T> {

    @Override
    public void onCompleted() {
        // no-op by default.
    }

    @Override
    public void onError(final Throwable e) {
        // no-op by default.
    }

    @Override
    public void onNext(final T t) {
        // no-op by default.
    }
}
