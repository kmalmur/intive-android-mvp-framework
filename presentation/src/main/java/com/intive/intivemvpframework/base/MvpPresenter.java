package com.intive.intivemvpframework.base;

import android.support.annotation.UiThread;

public interface MvpPresenter<V extends MvpView> {

    /**
     * Set or attach the view to this presenter. Typically this method will be invoked from:
     * <ul>
     * <li><code>Activity.onCreate</code></li>
     * <li><code>Fragment.onDestroy</code></li>
     * <li><code>View.onAttachToWindow</code></li>
     * </ul>
     */
    @UiThread
    void attachView(V view);

    /**
     * Will be called if the view has been destroyed. Typically this method will be invoked from:
     * <ul>
     * <li><code>Activity.onStop()</code></li>
     * <li><code>Fragment.onDestroyView()</code></li>
     * <li><code>View.onDetachedFromWindow()</code></li>
     * </ul>
     */
    @UiThread
    void detachView();
}