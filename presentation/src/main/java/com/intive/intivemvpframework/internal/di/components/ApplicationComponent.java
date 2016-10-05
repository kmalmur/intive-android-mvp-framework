package com.intive.intivemvpframework.internal.di.components;

import com.intive.intivemvpframework.domain.repository.UserRepository;
import com.intive.intivemvpframework.internal.di.modules.ApplicationModule;
import com.intive.intivemvpframework.view.activity.BaseActivity;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(BaseActivity baseActivity);

    //Exposed to sub-graphs.
    Context context();

    UserRepository userRepository();
}
