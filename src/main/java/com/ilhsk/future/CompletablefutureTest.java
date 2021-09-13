
package com.ilhsk.future;

import java.util.concurrent.CompletableFuture;

import org.junit.Test;

/**
 * @Auth ilhsk
 * @Description
 * 
 *              <pre></pre>
 */
public class CompletablefutureTest {

	Runnable task = () -> {
		try {
			Thread.sleep(5 * 1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("TASK completed");
	};

	@Test
	public void completableFuture() throws Exception {

		CompletableFuture.runAsync(task).
			thenCompose(aVoid -> CompletableFuture.runAsync(task)).
			thenAcceptAsync(aVoid -> System.out.println("all tasks completed!!")).exceptionally(throwable -> {
			System.out.println("exception occurred!!");
			return null;
		});

		Thread.sleep(11 * 1000L);
	}

}
