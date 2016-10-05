package com.intive.intivemvpframework.domain.interactor;

import com.intive.intivemvpframework.domain.User;
import com.intive.intivemvpframework.domain.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;

public class GetUserDetailsUseCase {

    private final UserRepository userRepository;

    @Inject
    public GetUserDetailsUseCase(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Observable<User> buildUseCaseObservable(final int userId) {
        return userRepository.user(userId);
    }
}
