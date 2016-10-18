package com.intive.intivemvpframework.internal.di.modules;

import com.intive.intivemvpframework.AndroidApplication;
import com.intive.intivemvpframework.data.executor.AndroidComposedScheduler;
import com.intive.intivemvpframework.data.net.RestApi;
import com.intive.intivemvpframework.data.net.RestApiImpl;
import com.intive.intivemvpframework.data.repository.UserDataRepository;
import com.intive.intivemvpframework.domain.interactor.ComposedScheduler;
import com.intive.intivemvpframework.domain.repository.UserRepository;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {

    private final AndroidApplication application;

    public ApplicationModule(final AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    UserRepository provideUserRepository(final UserDataRepository userDataRepository) {
        return userDataRepository;
    }

    @Provides
    @Singleton
    RestApi provideRestApi() {
        return new RestApiImpl(application);
    }

    @Provides
    @Singleton
    ComposedScheduler provideComposedScheduler() {
        return new AndroidComposedScheduler();
    }
}
