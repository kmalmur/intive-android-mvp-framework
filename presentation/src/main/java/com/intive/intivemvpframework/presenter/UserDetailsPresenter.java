package com.intive.intivemvpframework.presenter;

import com.intive.intivemvpframework.base.MvpBasePresenter;
import com.intive.intivemvpframework.domain.User;
import com.intive.intivemvpframework.domain.interactor.DefaultSubscriber;
import com.intive.intivemvpframework.domain.interactor.GetUserDetailsUseCase;
import com.intive.intivemvpframework.internal.di.PerActivity;
import com.intive.intivemvpframework.mapper.UserModelDataMapper;
import com.intive.intivemvpframework.model.UserModel;
import com.intive.intivemvpframework.view.UserDetailsView;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@PerActivity
public class UserDetailsPresenter extends MvpBasePresenter<UserDetailsView> {

    private final GetUserDetailsUseCase mGetUserDetailsUseCaseUseCase;

    private final UserModelDataMapper userModelDataMapper;

    private Subscription mGetUserDetailsSubscription;

    private int mUserId;

    @Inject
    public UserDetailsPresenter(final GetUserDetailsUseCase getUserDetailsUseCase, final UserModelDataMapper userModelDataMapper) {
        this.mGetUserDetailsUseCaseUseCase = getUserDetailsUseCase;
        this.userModelDataMapper = userModelDataMapper;
    }

    @Override
    public void attachView(final UserDetailsView view) {
        super.attachView(view);
        loadUserDetails();
    }

    @Override
    public void detachView() {
        super.detachView();

        if (mGetUserDetailsSubscription != null) {
            mGetUserDetailsSubscription.unsubscribe();
        }
    }

    /**
     * Initializes the presenter by start retrieving user details.
     */
    public void setParameters(final int userId) {
        mUserId = userId;
    }

    public void retryClicked() {
        loadUserDetails();
    }

    /**
     * Loads user details.
     */
    private void loadUserDetails() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getUserDetails();
    }

    private void hideViewRetry() {
        getView().hideRetry();
    }

    private void showViewLoading() {
        getView().showLoading();
    }

    private void getUserDetails() {
        mGetUserDetailsSubscription = mGetUserDetailsUseCaseUseCase.buildUseCaseObservable(mUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new UserDetailsSubscriber());
    }

    private void hideViewLoading() {
        getView().hideLoading();
    }

    private void showViewRetry() {
        getView().showRetry();
    }

    private void showErrorMessage(final Exception e) {
        getView().showError(e);
    }

    private void showUserDetailsInView(final User user) {
        final UserModel userModel = this.userModelDataMapper.transform(user);

        getView().renderUser(userModel);
    }

    private final class UserDetailsSubscriber extends DefaultSubscriber<User> {

        @Override
        public void onCompleted() {
            UserDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(final Throwable e) {
            UserDetailsPresenter.this.hideViewLoading();
            UserDetailsPresenter.this.showErrorMessage(new RuntimeException(e));
            UserDetailsPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(final User user) {
            UserDetailsPresenter.this.showUserDetailsInView(user);
        }
    }
}
