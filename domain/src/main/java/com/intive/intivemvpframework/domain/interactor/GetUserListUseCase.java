package com.intive.intivemvpframework.domain.interactor;

import com.intive.intivemvpframework.domain.User;
import com.intive.intivemvpframework.domain.repository.UserRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class GetUserListUseCase {

    private final UserRepository userRepository;

    @Inject
    public GetUserListUseCase(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Observable<List<User>> buildUseCaseObservable() {
        return this.userRepository.users();
    }
}
