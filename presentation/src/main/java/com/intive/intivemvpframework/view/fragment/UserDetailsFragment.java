package com.intive.intivemvpframework.view.fragment;

import com.intive.intivemvpframework.R;
import com.intive.intivemvpframework.exception.ErrorMessageFactory;
import com.intive.intivemvpframework.internal.di.components.ActivityComponent;
import com.intive.intivemvpframework.model.UserModel;
import com.intive.intivemvpframework.presenter.UserDetailsPresenter;
import com.intive.intivemvpframework.view.UserDetailsView;
import com.intive.intivemvpframework.view.activity.UserDetailsActivity;
import com.intive.intivemvpframework.view.component.AutoLoadImageView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fragment that shows details of a certain user.
 */
public class UserDetailsFragment extends BaseFragment implements UserDetailsView {

    @Inject
    UserDetailsPresenter userDetailsPresenter;

    @Bind(R.id.iv_cover)
    AutoLoadImageView iv_cover;

    @Bind(R.id.tv_fullname)
    TextView tv_fullname;

    @Bind(R.id.tv_email)
    TextView tv_email;

    @Bind(R.id.tv_followers)
    TextView tv_followers;

    @Bind(R.id.tv_description)
    TextView tv_description;

    @Bind(R.id.rl_progress)
    RelativeLayout rl_progress;

    @Bind(R.id.rl_retry)
    RelativeLayout rl_retry;

    @Bind(R.id.bt_retry)
    Button bt_retry;

    private int mUserId;

    public UserDetailsFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(ActivityComponent.class).inject(this);

        mUserId = getArguments().getInt(UserDetailsActivity.INTENT_EXTRA_PARAM_USER_ID);

        userDetailsPresenter.setParameters(mUserId);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_user_details, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.userDetailsPresenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.userDetailsPresenter.detachView();
        ButterKnife.unbind(this);
    }


    @Override
    public void renderUser(final UserModel user) {
        if (user != null) {
            this.iv_cover.setImageUrl(user.getCoverUrl());
            this.tv_fullname.setText(user.getFullName());
            this.tv_email.setText(user.getEmail());
            this.tv_followers.setText(String.valueOf(user.getFollowers()));
            this.tv_description.setText(user.getDescription());
        }
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
        String errorMessage = ErrorMessageFactory.create(getActivity(), ex);
        this.showToastMessage(errorMessage);
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        userDetailsPresenter.retryClicked();
    }
}
