package com.intive.intivemvpframework.domain.interactor;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;
import rx.functions.Func0;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class UseCaseTest {

    private UseCase<String, Long> mUseCase;

    private static final Long FAKE_RESULT = 11L;

    @Before
    public void setUp() {
        mUseCase = new UseCase<String, Long>() {
            @Override
            protected Observable<Long> createUseCaseObservable(final String param) {

                return Observable.defer(new Func0<Observable<Long>>() {
                    @Override
                    public Observable<Long> call() {
                        return Observable.just(FAKE_RESULT);
                    }
                });
            }
        };
    }

    @Test
    public void testUseCaseObservableSuccess() {
        final TestSubscriber<Long> testSubscriber = new TestSubscriber<>();

        mUseCase.execute("").subscribe(testSubscriber);

        testSubscriber.assertValue(FAKE_RESULT);
    }

    @Test
    public void testObservableIsExecuting() {
        final TestSubscriber<Long> testSubscriber = new TestSubscriber<>();
        final Observable<Long> observable = mUseCase.execute("");

        assertTrue(mUseCase.isExecuting());
        observable.subscribe(testSubscriber);
        assertFalse(mUseCase.isExecuting());
    }

    @Test
    public void testExecuteReturnTheSameObservableDuringExecution() {
        final Observable<Long> firstObservable = mUseCase.execute("");
        final Observable<Long> secondObservable = mUseCase.execute("");

        assertEquals(firstObservable, secondObservable);
    }

    @Test
    public void testExecuteReturnNewObservableAfterFinishExecution() {
        final Observable<Long> firstObservable = mUseCase.execute("");
        firstObservable.subscribe();

        final Observable<Long> secondObservable = mUseCase.execute("");

        assertNotEquals(firstObservable, secondObservable);
    }
}