package com.intive.intivemvpframework.view.fragment;

import com.intive.intivemvpframework.R;
import com.intive.intivemvpframework.exception.ErrorMessageFactory;
import com.intive.intivemvpframework.internal.di.components.ActivityComponent;
import com.intive.intivemvpframework.model.UserModel;
import com.intive.intivemvpframework.presenter.UserListPresenter;
import com.intive.intivemvpframework.view.UserListView;
import com.intive.intivemvpframework.view.adapter.UsersAdapter;
import com.intive.intivemvpframework.view.adapter.UsersLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fragment that shows a list of Users.
 */
public class UserListFragment extends BaseFragment implements UserListView {

    @Inject
    UserListPresenter userListPresenter;

    @Inject
    UsersAdapter usersAdapter;

    @Bind(R.id.rv_users)
    RecyclerView rv_users;

    @Bind(R.id.rl_progress)
    RelativeLayout rl_progress;

    @Bind(R.id.rl_retry)
    RelativeLayout rl_retry;

    @Bind(R.id.bt_retry)
    Button bt_retry;

    private UserListListener userListListener;

    private UsersAdapter.OnItemClickListener onItemClickListener =
            new UsersAdapter.OnItemClickListener() {
                @Override
                public void onUserItemClicked(final UserModel userModel) {
                    if (UserListFragment.this.userListPresenter != null && userModel != null) {
                        UserListFragment.this.userListPresenter.onUserClicked(userModel);
                    }
                }
            };

    public UserListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        if (activity instanceof UserListListener) {
            this.userListListener = (UserListListener) activity;
        }
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(ActivityComponent.class).inject(this);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_user_list, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userListPresenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        userListPresenter.detachView();

        rv_users.setAdapter(null);
        ButterKnife.unbind(this);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        this.userListListener = null;
    }

    private void setupRecyclerView() {
        this.usersAdapter.setOnItemClickListener(onItemClickListener);
        this.rv_users.setLayoutManager(new UsersLayoutManager(getActivity()));
        this.rv_users.setAdapter(usersAdapter);
    }

    @Override
    public void showLoading() {
        this.rl_progress.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        this.rl_progress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showRetry() {
        this.rl_retry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        this.rl_retry.setVisibility(View.GONE);
    }

    @Override
    public void showError(final Exception ex) {
        String message = ErrorMessageFactory.create(getActivity(), ex);
        showToastMessage(message);
    }

    @Override
    public void renderUserList(final Collection<UserModel> userModelCollection) {
        if (userModelCollection != null) {
            this.usersAdapter.setUsersCollection(userModelCollection);
        }
    }

    @Override
    public void viewUser(final UserModel userModel) {
        if (this.userListListener != null) {
            this.userListListener.onUserClicked(userModel);
        }
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        userListPresenter.retryClicked();
    }

    /**
     * Interface for listening user list events.
     */
    public interface UserListListener {
        void onUserClicked(final UserModel userModel);
    }
}
