package com.tianya.test;

/**
 * @Auther: Chang
 * @Date: 2018/11/4
 */
public class MyThreadPoolExecutor {

	public int corePoolSize;
	public int maximumPoolSize;

	public MyThreadPoolExecutor(Builder builder) {
		corePoolSize = builder.corePoolSize;
		maximumPoolSize = builder.maximumPoolSize;
	}

	public static class Builder {
		public int corePoolSize = 10;
		public int maximumPoolSize = 10;

		public MyThreadPoolExecutor build() {
			return new MyThreadPoolExecutor(this);
		}

		public Builder corePoolSize(int val) {
			this.corePoolSize = val;
			return this;
		}
	}
}











