package com.ffzx.order.utils;

/**
 * 
 * @author Administrator
 *
 */
public class DataSourceHolder {
	
	public static ThreadLocal<Integer> counter = new ThreadLocal<>();

	public static Integer add() {
		DynamicRoutingDataSource.setCurrentLookUpKey(DynamicRoutingDataSource.MASTER_DATA_SOURCE);
		Integer count = DataSourceHolder.counter.get();
		if (count == null) {
			count = 0;
		}
		count++;
		DataSourceHolder.counter.set(count);
		return count;
	}

	public static void decr(Integer count) {
		count--;
		DataSourceHolder.counter.set(count);
		if (0 == count.intValue()) {
			DynamicRoutingDataSource.removeCurrentLookUpKey();
		}
	}
}
