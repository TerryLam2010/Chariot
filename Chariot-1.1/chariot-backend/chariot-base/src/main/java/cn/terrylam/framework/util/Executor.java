/**
 * Copyright (c)  terrylam.cn . All rights reserved.
 *
 * created.
 */
package cn.terrylam.framework.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author TerryLam
 * 为一些定时任务或其他业务场景提供简单线程池来处理
 */
public class Executor {

	private final static int POOL_SIZE = 20;

	private static ExecutorService pool;
    /**
     * 把需要处理的线程加入到线程池中
     * @param command 具体的业务处理线程 需要自己实现
     */
	public static void execute( Runnable command ) {
		pool.execute( command );
	}

	static {
		pool = Executors.newFixedThreadPool( POOL_SIZE );
	}

	public static void shutdown() {
		pool.shutdown();
	}

}
