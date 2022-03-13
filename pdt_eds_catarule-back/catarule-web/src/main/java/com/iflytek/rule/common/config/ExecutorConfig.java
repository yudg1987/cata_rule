package com.iflytek.rule.common.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/** <br>
 * 标题: <br>
 * 描述: <br>
 * 公司: www.iflytek.com<br>
 * 
 * @autho dgyu
 * @time 2021年12月4日 下午10:02:09 */
@Configuration
public class ExecutorConfig {

	/** 线程池维护线程的最大数量. */
	@Value("${imortExcel.maxPoolSize:100}")
	private int maxPoolSize;

	/** 异步方法执行线程池大小 <br>
	 * 适用场景: <br>
	 * 调用方式: <br>
	 * 业务逻辑说明<br>
	 *
	 * @return
	 * @autho dgyu
	 * @time 2021年12月4日 下午10:03:45 */
	@Bean
	public Executor requestExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(maxPoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setQueueCapacity(Integer.MAX_VALUE);
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.setThreadNamePrefix("imort-excel-pool-");
		executor.initialize();

		return executor;
	}
}