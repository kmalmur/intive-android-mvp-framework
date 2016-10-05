package com.intive.intivemvpframework.base;

import android.support.annotation.UiThread;

public class MvpBasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V mView;

    @UiThread
    @Override
    public void attachView(final V view) {
        mView = view;
    }

    /**
     * Get the attached view.
     *
     * @return if view is not attached, otherwise the concrete view instance
     * @throws IllegalStateException when view is not attached
     */
    @UiThread
    public V getView() {
        if (!isViewAttached()) {
            throw new IllegalStateException("View is not attached");
        }
        return mView;
    }

    /**
     * Checks if a view is attached to this presenter.
     */
    @UiThread
    public boolean isViewAttached() {
        return mView != null;
    }

    @UiThread
    @Override
    public void detachView() {
        mView = null;
    }
}