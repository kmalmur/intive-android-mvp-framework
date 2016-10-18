package com.intive.intivemvpframework.domain.interactor;

import com.intive.intivemvpframework.domain.User;
import com.intive.intivemvpframework.domain.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;

public class GetUserDetailsUseCase extends UseCase<Integer, User> {

    private final UserRepository mUserRepository;

    @Inject
    public GetUserDetailsUseCase(final UserRepository userRepository, final ComposedScheduler composedScheduler) {
        super(composedScheduler);
        mUserRepository = userRepository;
    }

    @Override
    protected Observable<User> createUseCaseObservable(final Integer userId) {
        return mUserRepository.user(userId);
    }
}
