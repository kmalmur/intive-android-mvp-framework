package com.intive.intivemvpframework.internal.di.components;

import com.intive.intivemvpframework.internal.di.PerActivity;
import com.intive.intivemvpframework.internal.di.modules.ActivityModule;
import com.intive.intivemvpframework.view.fragment.UserDetailsFragment;
import com.intive.intivemvpframework.view.fragment.UserListFragment;

import android.app.Activity;

import dagger.Component;

/**
 * A base component upon which fragment's components may depend.
 * Activity-level components should extend this component.
 *
 * Subtypes of ActivityComponent should be decorated with annotation:
 * {@link com.intive.intivemvpframework.internal.di.PerActivity}
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    //Exposed to sub-graphs.
    Activity activity();

    void inject(UserListFragment userListFragment);

    void inject(UserDetailsFragment userDetailsFragment);
}
