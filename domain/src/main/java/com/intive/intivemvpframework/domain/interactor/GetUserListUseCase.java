package com.intive.intivemvpframework.domain.interactor;

import com.intive.intivemvpframework.domain.User;
import com.intive.intivemvpframework.domain.repository.UserRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class GetUserListUseCase extends UseCase<Void, List<User>> {

    private final UserRepository mUserRepository;

    @Inject
    public GetUserListUseCase(final UserRepository userRepository, final ComposedScheduler composedScheduler) {
        super(composedScheduler);
        mUserRepository = userRepository;
    }

    @Override
    protected Observable<List<User>> createUseCaseObservable(final Void param) {
        return mUserRepository.users();
    }
}
