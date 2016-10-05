package com.intive.intivemvpframework.domain.interactor;

import com.intive.intivemvpframework.domain.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class GetUserListUseCaseTest {

    private GetUserListUseCase mGetUserListUseCase;

    @Mock
    private UserRepository mockUserRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mGetUserListUseCase = new GetUserListUseCase(mockUserRepository);
    }

    @Test
    public void testGetUserListUseCaseObservableHappyCase() {
        mGetUserListUseCase.buildUseCaseObservable();

        verify(mockUserRepository).users();
        verifyNoMoreInteractions(mockUserRepository);
    }
}
