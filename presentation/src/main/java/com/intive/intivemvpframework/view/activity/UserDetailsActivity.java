package com.intive.intivemvpframework.view.activity;

import com.intive.intivemvpframework.R;
import com.intive.intivemvpframework.internal.di.HasComponent;
import com.intive.intivemvpframework.internal.di.components.ActivityComponent;
import com.intive.intivemvpframework.internal.di.components.DaggerActivityComponent;
import com.intive.intivemvpframework.view.fragment.UserDetailsFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

/**
 * Activity that shows details of a certain user.
 */
public class UserDetailsActivity extends BaseActivity implements HasComponent<ActivityComponent> {

    public static final String INTENT_EXTRA_PARAM_USER_ID = "org.android10.INTENT_PARAM_USER_ID";

    private static final String INSTANCE_STATE_PARAM_USER_ID = "org.android10.STATE_PARAM_USER_ID";

    private int userId;

    private ActivityComponent mComponent;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_layout);

        this.initializeActivity(savedInstanceState);
        this.initializeInjector();
    }

    /**
     * Initializes this activity.
     */
    private void initializeActivity(final Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.userId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_USER_ID, -1);
            UserDetailsFragment fragment = new UserDetailsFragment();

            Bundle b = new Bundle();
            b.putInt(INTENT_EXTRA_PARAM_USER_ID, userId);

            fragment.setArguments(b);

            addFragment(R.id.fragmentContainer, fragment);
        } else {
            this.userId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_USER_ID);
        }
    }

    private void initializeInjector() {
        this.mComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        if (outState != null) {
            outState.putInt(INSTANCE_STATE_PARAM_USER_ID, this.userId);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public ActivityComponent getComponent() {
        return mComponent;
    }

    public static Intent getCallingIntent(final Context context, final int userId) {
        Intent callingIntent = new Intent(context, UserDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_USER_ID, userId);
        return callingIntent;
    }
}
