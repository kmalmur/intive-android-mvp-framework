package com.intive.intivemvpframework.domain.interactor;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func0;
import rx.observers.TestSubscriber;
import rx.schedulers.TestScheduler;

import static org.junit.Assert.assertEquals;

public class UseCaseCacheTest {

    private UseCase<String, Long> mUseCase;

    private TestScheduler mScheduler = new TestScheduler();

    private int mObservableTriggerCounter;

    @Before
    public void setUp() {
        mScheduler = new TestScheduler();
        mObservableTriggerCounter = 0;
    }

    @Test
    public void testNewSubscriberDuringExecution() {
        initUseCase(1);

        final TestSubscriber<Long> firstTestSubscriber = new TestSubscriber<>();
        final TestSubscriber<Long> secondTestSubscriber = new TestSubscriber<>();

        mUseCase.execute("").subscribe(firstTestSubscriber);
        mUseCase.execute("").subscribe(secondTestSubscriber);

        mScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        firstTestSubscriber.assertValue(0L);
        secondTestSubscriber.assertValue(0L);
        firstTestSubscriber.assertCompleted();
        secondTestSubscriber.assertCompleted();
    }

    @Test
    public void testResubscribeDuringExecution() {
        initUseCase(1);

        final TestSubscriber<Long> firstTestSubscriber = new TestSubscriber<>();
        final TestSubscriber<Long> secondTestSubscriber = new TestSubscriber<>();

        mUseCase.execute("").subscribe(firstTestSubscriber);

        firstTestSubscriber.unsubscribe();

        mUseCase.execute("").subscribe(secondTestSubscriber);

        mScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        secondTestSubscriber.assertValue(0L);
        secondTestSubscriber.assertCompleted();
    }

    @Test
    public void testResubscribeAfterFirstOnNext() {
        initUseCase(2);

        final TestSubscriber<Long> firstTestSubscriber = new TestSubscriber<>();
        final TestSubscriber<Long> secondTestSubscriber = new TestSubscriber<>();

        mUseCase.execute("").subscribe(firstTestSubscriber);

        mScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        firstTestSubscriber.assertValue(0L);
        firstTestSubscriber.unsubscribe();

        mUseCase.execute("").subscribe(secondTestSubscriber);

        mScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        secondTestSubscriber.assertValues(0L, 1L);
    }

    @Test
    public void testCacheDuringExecution() {
        initUseCase(1);

        final TestSubscriber<Long> firstTestSubscriber = new TestSubscriber<>();
        final TestSubscriber<Long> secondTestSubscriber = new TestSubscriber<>();

        mUseCase.execute("").subscribe(firstTestSubscriber);
        mUseCase.execute("").subscribe(secondTestSubscriber);

        mScheduler.advanceTimeBy(2, TimeUnit.SECONDS);

        assertEquals(1, mObservableTriggerCounter);
    }

    private void initUseCase(final int itemsCount) {
        mUseCase = new UseCase<String, Long>() {
            @Override
            protected Observable<Long> createUseCaseObservable(final String param) {

                return Observable.defer(new Func0<Observable<Long>>() {
                    @Override
                    public Observable<Long> call() {
                        ++mObservableTriggerCounter;
                        return Observable.interval(1, TimeUnit.SECONDS, mScheduler).take(itemsCount)
                                .observeOn(mScheduler)
                                .subscribeOn(mScheduler);
                    }
                });
            }
        };
    }
}