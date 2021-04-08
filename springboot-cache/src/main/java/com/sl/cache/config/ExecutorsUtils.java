package com.sl.cache.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author zouqi
 * @version Id: ExecutorsUtils, v 0.1 2020/3/30 16:11 zouqi Exp $
 */
public class ExecutorsUtils {

    private static final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("executors-utils-pool").build();
    /**
     * 缓存线程池
     */
    private static final ExecutorService executorService = new ThreadPoolExecutor(24, 64, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(300), namedThreadFactory);

    /**
     * 缓存线程池
     */
    private static final ExecutorService executorSecondCache    = new ThreadPoolExecutor(24, 64, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(300), namedThreadFactory);

    /**
     * 获取缓存线程池
     * @return
     */
    public static ExecutorService getExecutorSecondCache() {
        return executorSecondCache;
    }
    /**
     * 获取缓存线程池
     *
     * @return
     */
    public static ExecutorService getExecutorPool() {
        return executorService;
    }

}
