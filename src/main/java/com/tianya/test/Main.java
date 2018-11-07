package com.tianya.test;

/**
 * @Auther: Chang
 * @Date: 2018/11/4
 */
public class Main {
	public static void main(String[] args) {
		MyThreadPoolExecutor tt = new MyThreadPoolExecutor.Builder().corePoolSize(5).build();

		System.out.println(tt.corePoolSize);
		System.out.println(tt.maximumPoolSize);
	}
}
