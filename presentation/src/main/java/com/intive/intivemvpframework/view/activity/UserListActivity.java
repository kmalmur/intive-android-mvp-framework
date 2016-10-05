package com.intive.intivemvpframework.view.activity;

import com.intive.intivemvpframework.R;
import com.intive.intivemvpframework.internal.di.HasComponent;
import com.intive.intivemvpframework.internal.di.components.ActivityComponent;
import com.intive.intivemvpframework.internal.di.components.DaggerActivityComponent;
import com.intive.intivemvpframework.model.UserModel;
import com.intive.intivemvpframework.view.fragment.UserListFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

/**
 * Activity that shows a list of Users.
 */
public class UserListActivity extends BaseActivity implements HasComponent<ActivityComponent>,
        UserListFragment.UserListListener {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_layout);

        this.initializeInjector();
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new UserListFragment());
        }
    }

    private void initializeInjector() {
        this.mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public ActivityComponent getComponent() {
        return mActivityComponent;
    }

    @Override
    public void onUserClicked(UserModel userModel) {
        this.navigator.navigateToUserDetails(this, userModel.getUserId());
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, UserListActivity.class);
    }
}
