package com.intive.intivemvpframework;

import com.intive.intivemvpframework.internal.di.components.ApplicationComponent;
import com.intive.intivemvpframework.internal.di.components.DaggerApplicationComponent;
import com.intive.intivemvpframework.internal.di.modules.ApplicationModule;
import com.squareup.leakcanary.LeakCanary;

import android.app.Application;

import timber.log.Timber;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initializeTimber();
        initializeInjector();
        initializeLeakDetection();
    }

    private void initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void initializeInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    private void initializeLeakDetection() {
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
