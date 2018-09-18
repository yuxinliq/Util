package com.yu.util.file;

import sun.nio.ch.ThreadPool;

import java.util.concurrent.*;

public class AbstractFileResolver {
    protected BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(10000);
    protected ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 30, TimeUnit.SECONDS, queue);

    public void waitUtilOver() {
        int activeCount = 1;
        while (activeCount > 0) {
            try {
                activeCount = executor.getActiveCount();
                System.out.println(activeCount);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }
}
