package com.intive.intivemvpframework.data.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Decorated {@link java.util.concurrent.ThreadPoolExecutor}
 */
public class JobExecutor implements Executor {

    private static final int INITIAL_POOL_SIZE = 3;

    private static final int MAX_POOL_SIZE = 5;

    // Sets the amount of time an idle thread waits before terminating
    private static final int KEEP_ALIVE_TIME = 10;

    // Sets the Time Unit to seconds
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    private final BlockingQueue<Runnable> mWorkQueue;

    private final ThreadPoolExecutor mThreadPoolExecutor;

    private final ThreadFactory mThreadFactory;

    public JobExecutor() {
        mWorkQueue = new LinkedBlockingQueue<>();
        mThreadFactory = new JobThreadFactory();
        mThreadPoolExecutor = new ThreadPoolExecutor(INITIAL_POOL_SIZE, MAX_POOL_SIZE,
                KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, this.mWorkQueue, this.mThreadFactory);
    }

    @Override
    public void execute(final Runnable runnable) {
        if (runnable == null) {
            throw new IllegalArgumentException("Runnable to execute cannot be null");
        }
        mThreadPoolExecutor.execute(runnable);
    }

    private static class JobThreadFactory implements ThreadFactory {

        private static final String THREAD_NAME = "android_";

        private int counter = 0;

        @Override
        public Thread newThread(final Runnable runnable) {
            return new Thread(runnable, THREAD_NAME + counter++);
        }
    }
}