package wang.wangxinarhat.geeweather.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

/**
 * 线程管理类
 *
 * @author wang
 */
public class ThreadManger {

    private static ThreadPool mThreadPool;

    public static ThreadPool getThreadPool() {

        if (mThreadPool == null) {
            synchronized (ThreadManger.class) {
                if (mThreadPool == null) {

                    int cpuCount = Runtime.getRuntime().availableProcessors();
                    int threadCount = cpuCount * 3 + 1;
                    mThreadPool = new ThreadPool(threadCount, threadCount, 1l);
                }
            }
        }

        return mThreadPool;
    }

    public static class ThreadPool {

        private int corePoolSize;// 核心线程数
        private int maximumPoolSize;// 最大线程数
        private long keepAliveTime;// 线程休息时间

        public ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;
        }

        ThreadPoolExecutor executor = null;

        public void execute(Runnable r) {
            if (executor == null) {
                executor = new ThreadPoolExecutor(corePoolSize,
                        maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>(),
                        Executors.defaultThreadFactory(), new AbortPolicy());
            }

            executor.execute(r);
        }

        /**
         * 将一个Runnable对象从消息队列移除
         *
         * @param r
         */
        public void cancel(Runnable r) {
            if (executor != null) {
                executor.getQueue().remove(r);
            }
        }
    }


}
