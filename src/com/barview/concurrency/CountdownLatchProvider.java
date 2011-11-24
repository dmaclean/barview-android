package com.barview.concurrency;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class CountdownLatchProvider {
	public static final String LATCH_DEALS = "deals";
	
	private static HashMap<String, CountDownLatch> latches;
	
	static {
		latches = new HashMap<String, CountDownLatch>();
	}
	
	public static void registerLatch(String name, CountDownLatch latch) {
		latches.put(name, latch);
	}
	
	public static CountDownLatch getLatch(String name) {
		return latches.get(name);
	}
}
