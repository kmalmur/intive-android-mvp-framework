package com.intive.intivemvpframework.presenter;

import com.intive.intivemvpframework.base.MvpBasePresenter;
import com.intive.intivemvpframework.domain.User;
import com.intive.intivemvpframework.domain.interactor.DefaultSubscriber;
import com.intive.intivemvpframework.domain.interactor.GetUserListUseCase;
import com.intive.intivemvpframework.internal.di.PerActivity;
import com.intive.intivemvpframework.mapper.UserModelDataMapper;
import com.intive.intivemvpframework.model.UserModel;
import com.intive.intivemvpframework.view.UserListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import timber.log.Timber;

@PerActivity
public class UserListPresenter extends MvpBasePresenter<UserListView> {

    private final GetUserListUseCase mGetUserListUseCase;

    private final UserModelDataMapper mUserModelDataMapper;

    private Subscription mGetUserListUseCaseSubscription;

    @Inject
    public UserListPresenter(final GetUserListUseCase getUserListUserCase, final UserModelDataMapper userModelDataMapper) {

        this.mGetUserListUseCase = getUserListUserCase;
        this.mUserModelDataMapper = userModelDataMapper;
    }

    @Override
    public void attachView(final UserListView view) {
        super.attachView(view);

        loadUserList();
    }

    @Override
    public void detachView() {
        super.detachView();

        if (mGetUserListUseCaseSubscription != null) {
            mGetUserListUseCaseSubscription.unsubscribe();
        }
    }

    public void retryClicked() {
        loadUserList();
    }

    /**
     * Loads all users.
     */
    private void loadUserList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getUserList();
    }

    private void hideViewRetry() {
        getView().hideRetry();
    }

    private void showViewLoading() {
        getView().showLoading();
    }

    private void getUserList() {
        mGetUserListUseCaseSubscription = mGetUserListUseCase.execute(null)
                .subscribe(new UserListSubscriber());
    }

    public void onUserClicked(final UserModel userModel) {
        getView().viewUser(userModel);
    }

    private void hideViewLoading() {
        getView().hideLoading();
    }

    private void showViewRetry() {
        getView().showRetry();
    }

    private void showErrorMessage(final Exception errorBundle) {
        getView().showError(errorBundle);
    }

    private void showUsersCollectionInView(final Collection<User> usersCollection) {
        final Collection<UserModel> userModelsCollection =
                this.mUserModelDataMapper.transform(usersCollection);
        getView().renderUserList(userModelsCollection);
    }

    private final class UserListSubscriber extends DefaultSubscriber<List<User>> {

        @Override
        public void onCompleted() {
            UserListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            Timber.e(e, "Cannot get user list");
            UserListPresenter.this.hideViewLoading();
            UserListPresenter.this.showErrorMessage(new RuntimeException(e));
            UserListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(final List<User> users) {
            UserListPresenter.this.showUsersCollectionInView(users);
        }
    }
}
