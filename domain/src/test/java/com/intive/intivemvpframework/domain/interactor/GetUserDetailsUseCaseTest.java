package com.intive.intivemvpframework.domain.interactor;

import com.intive.intivemvpframework.domain.User;
import com.intive.intivemvpframework.domain.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class GetUserDetailsUseCaseTest {

    private static final int FAKE_USER_ID = 123;

    private GetUserDetailsUseCase mGetUserDetailsUseCase;

    @Mock
    private UserRepository mUserRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mGetUserDetailsUseCase = new GetUserDetailsUseCase(mUserRepository, new TestComposedScheduler());
    }

    @Test
    public void testGetUserDetailsUseCaseObservableHappyCase() {
        when(mUserRepository.user(FAKE_USER_ID)).thenReturn(Observable.just(new User(FAKE_USER_ID)));

        mGetUserDetailsUseCase.execute(FAKE_USER_ID);

        verify(mUserRepository).user(FAKE_USER_ID);
        verifyNoMoreInteractions(mUserRepository);
    }
}
