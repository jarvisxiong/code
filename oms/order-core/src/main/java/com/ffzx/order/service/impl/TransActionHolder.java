package com.ffzx.order.service.impl;

import org.springframework.transaction.TransactionStatus;

public class TransActionHolder {
	private static ThreadLocal<TransactionStatus> holder = new ThreadLocal<>();
	private static ThreadLocal<Boolean> isLastLoop = new ThreadLocal<>();

	public static void setFlag(TransactionStatus status) {
		holder.set(status);
	};

	public static TransactionStatus getCurrentStatus() {
		return holder.get();
	};

	public static void removeFlag() {
		holder.remove();
	};

	public static boolean hasBeginTransaction() {
		return holder.get() == null ? false : true;
	};

	public static void setIsLastLoop() {
		isLastLoop.set(true);
	};

	public static void removeLastLoopFlag() {
		isLastLoop.remove();
	}

	public static boolean isLastLoop() {
		return isLastLoop.get() == null ? false : isLastLoop.get();
	}
}
