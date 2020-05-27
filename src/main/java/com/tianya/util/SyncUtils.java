package com.tianya.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author changwenbo
 * @date 2020/5/27 14:42
 */
@Component
public class SyncUtils {

	private ExecutorService es = Executors.newCachedThreadPool();

	public void submit(Runnable r) {
		es.submit(r);
	}
}
