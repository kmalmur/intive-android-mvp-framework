package com.intive.intivemvpframework.domain.interactor;

import com.intive.intivemvpframework.domain.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class GetUserDetailsUseCaseTest {

    private static final int FAKE_USER_ID = 123;

    private GetUserDetailsUseCase mGetUserDetailsUseCase;

    @Mock
    private UserRepository mUserRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mGetUserDetailsUseCase = new GetUserDetailsUseCase(mUserRepository);
    }

    @Test
    public void testGetUserDetailsUseCaseObservableHappyCase() {
        mGetUserDetailsUseCase.buildUseCaseObservable(FAKE_USER_ID);

        verify(mUserRepository).user(FAKE_USER_ID);
        verifyNoMoreInteractions(mUserRepository);
    }
}
