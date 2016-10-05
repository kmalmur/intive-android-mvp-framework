package com.intive.intivemvpframework.view;


import com.intive.intivemvpframework.base.MvpView;

/**
 * Interface representing a View that will use to load data.
 */
public interface LoadDataView extends MvpView {

    /**
     * Show a view with a progress bar indicating a loading process.
     */
    void showLoading();

    /**
     * Hide a loading view.
     */
    void hideLoading();

    /**
     * Show a retry view in case of an error when retrieving data.
     */
    void showRetry();

    /**
     * Hide a retry view shown if there was an error when retrieving data.
     */
    void hideRetry();

    /**
     * Show an error message
     *
     * @param ex exception to be shown
     */
    void showError(Exception ex);

}
