package com.sonin.common.tool.pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.*;

/**
 * @Author sonin
 * @Date 2021/6/21 16:57
 */
public class CustomThreadPool {

	private static final Log log = LogFactory.getLog(CustomThreadPool.class);

	private String threadPoolName;
	private Boolean daemon;
	private Integer priority;
	private UncaughtExceptionHandler uncaughtExceptionHandler;
	private Integer corePoolSize;
	private Integer maximumPoolSize;
	private Long keepAliveTime;
	private TimeUnit timeUnit;
	private Integer queueCapacity;
	private RejectedExecutionHandler rejectedExecutionHandler;

	private CustomThreadPool() {
		threadPoolName = "default-" + System.currentTimeMillis();
		daemon = false;
		priority = 5;
		uncaughtExceptionHandler = (thread, exception) -> {
			log.error(thread.getName() + " => " + exception.getMessage());
		};
		corePoolSize = Runtime.getRuntime().availableProcessors();
		maximumPoolSize = 2 * Runtime.getRuntime().availableProcessors();
		keepAliveTime = 0L;
		timeUnit = TimeUnit.MICROSECONDS;
		queueCapacity = 10 * Runtime.getRuntime().availableProcessors();
		rejectedExecutionHandler = new ThreadPoolExecutor.AbortPolicy();
	}

	private String getThreadPoolName() {
		return threadPoolName;
	}

	private void setThreadPoolName(String threadPoolName) {
		this.threadPoolName = threadPoolName;
	}

	private Boolean getDaemon() {
		return daemon;
	}

	private void setDaemon(Boolean daemon) {
		this.daemon = daemon;
	}

	private Integer getPriority() {
		return priority;
	}

	private void setPriority(Integer priority) {
		this.priority = priority;
	}

	private UncaughtExceptionHandler getUncaughtExceptionHandler() {
		return uncaughtExceptionHandler;
	}

	private void setUncaughtExceptionHandler(UncaughtExceptionHandler uncaughtExceptionHandler) {
		this.uncaughtExceptionHandler = uncaughtExceptionHandler;
	}

	private Integer getCorePoolSize() {
		return corePoolSize;
	}

	private void setCorePoolSize(Integer corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	private Integer getMaximumPoolSize() {
		return maximumPoolSize;
	}

	private void setMaximumPoolSize(Integer maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}

	private Long getKeepAliveTime() {
		return keepAliveTime;
	}

	private void setKeepAliveTime(Long keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	private TimeUnit getTimeUnit() {
		return timeUnit;
	}

	private void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	private Integer getQueueCapacity() {
		return queueCapacity;
	}

	private void setQueueCapacity(Integer queueCapacity) {
		this.queueCapacity = queueCapacity;
	}

	private RejectedExecutionHandler getRejectedExecutionHandler() {
		return rejectedExecutionHandler;
	}

	private void setRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler) {
		this.rejectedExecutionHandler = rejectedExecutionHandler;
	}

	public static class Builder {

		private final CustomThreadPool customThreadPool;

		public Builder() {
			customThreadPool = new CustomThreadPool();
		}

		public ThreadPoolExecutor build() {
			String threadPoolName = customThreadPool.getThreadPoolName();
			Boolean daemon = customThreadPool.getDaemon();
			Integer priority = customThreadPool.getPriority();
			UncaughtExceptionHandler uncaughtExceptionHandler = customThreadPool.getUncaughtExceptionHandler();
			Integer corePoolSize = customThreadPool.getCorePoolSize();
			Integer maximumPoolSize = customThreadPool.getMaximumPoolSize();
			Long keepAliveTime = customThreadPool.getKeepAliveTime();
			TimeUnit timeUnit = customThreadPool.getTimeUnit();
			Integer queueCapacity = customThreadPool.getQueueCapacity();
			RejectedExecutionHandler rejectedExecutionHandler = customThreadPool.getRejectedExecutionHandler();
			ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("pool-" + threadPoolName + "-%d").setDaemon(daemon).setPriority(priority).setUncaughtExceptionHandler(uncaughtExceptionHandler).build();
			return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit, new LinkedBlockingQueue<>(queueCapacity), threadFactory, rejectedExecutionHandler);
		}

		public Builder threadPoolName(String threadPoolName) {
			customThreadPool.setThreadPoolName(threadPoolName);
			return this;
		}

		public Builder daemon(Boolean daemon) {
			customThreadPool.setDaemon(daemon);
			return this;
		}

		public Builder priority(Integer priority) {
			customThreadPool.setPriority(priority);
			return this;
		}

		public Builder uncaughtExceptionHandler(UncaughtExceptionHandler uncaughtExceptionHandler) {
			customThreadPool.setUncaughtExceptionHandler(uncaughtExceptionHandler);
			return this;
		}

		public Builder corePoolSize(Integer corePoolSize) {
			customThreadPool.setCorePoolSize(corePoolSize);
			if (customThreadPool.getMaximumPoolSize() < corePoolSize) {
				customThreadPool.setMaximumPoolSize(corePoolSize);
			}
			return this;
		}

		public Builder maximumPoolSize(Integer maximumPoolSize) {
			customThreadPool.setMaximumPoolSize(maximumPoolSize);
			if (customThreadPool.getCorePoolSize() > maximumPoolSize) {
				customThreadPool.setCorePoolSize(maximumPoolSize);
			}
			return this;
		}

		public Builder keepAliveTime(Long keepAliveTime) {
			customThreadPool.setKeepAliveTime(keepAliveTime);
			return this;
		}

		public Builder timeUnit(TimeUnit timeUnit) {
			customThreadPool.setTimeUnit(timeUnit);
			return this;
		}

		public Builder queueCapacity(Integer queueCapacity) {
			customThreadPool.setQueueCapacity(queueCapacity);
			return this;
		}

		public Builder rejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler) {
			customThreadPool.setRejectedExecutionHandler(rejectedExecutionHandler);
			return this;
		}
	}

}
