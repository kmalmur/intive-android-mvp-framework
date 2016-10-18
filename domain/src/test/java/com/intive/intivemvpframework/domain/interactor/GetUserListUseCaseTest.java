package com.intive.intivemvpframework.domain.interactor;

import com.intive.intivemvpframework.domain.User;
import com.intive.intivemvpframework.domain.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class GetUserListUseCaseTest {

    private GetUserListUseCase mGetUserListUseCase;

    @Mock
    private UserRepository mockUserRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mGetUserListUseCase = new GetUserListUseCase(mockUserRepository, new TestComposedScheduler());
    }

    @Test
    public void testGetUserListUseCaseObservableHappyCase() {
        final List<User> users = new ArrayList<>();

        when(mockUserRepository.users()).thenReturn(Observable.just(users));

        mGetUserListUseCase.execute(null);

        verify(mockUserRepository).users();
        verifyNoMoreInteractions(mockUserRepository);
    }
}
