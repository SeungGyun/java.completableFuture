
package com.ilhsk.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Auth ilhsk
 * @Description 
 * <pre></pre>   
 */
public class CompletableFuturePool {
	
	
	public static void main(String[] args) {
		//Thread Pool 설정 기본 CPU 갯수
		ExecutorService threadPool = Executors.newScheduledThreadPool(3);
		//잡 리스트로 등록
		List<CompletableFuture<String>> completableFutures = new ArrayList();
		//잡 등록
		System.out.println("순서 확인용![1]");
		CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
			int sleepCount = (int)(Math.random() * 10);
			try {				
				TimeUnit.SECONDS.sleep(sleepCount);
				System.out.println("프로세스 처리중");
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			return "처리 완료!"+sleepCount;
		},threadPool);
		completableFutures.add(cf1);
		CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
			int sleepCount = (int)(Math.random() * 10);
			try {				
				TimeUnit.SECONDS.sleep(sleepCount);
				System.out.println("프로세스 처리중");
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			return "처리 완료!"+sleepCount;
		},threadPool);
		completableFutures.add(cf2);
		CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(() -> {
			int sleepCount = (int)(Math.random() * 10);
			try {				
				TimeUnit.SECONDS.sleep(sleepCount);
				System.out.println("프로세스 처리중");
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			return "처리 완료!"+sleepCount;
		},threadPool);
		completableFutures.add(cf3);
		System.out.println("순서 확인용![2]");
		//처리된 결과 리스트에 담기
		List<String> resultList = CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()])).thenApplyAsync(cfResult -> completableFutures.stream().map(future -> future.join()).collect(Collectors.toList())).join();
		System.out.println("순서 확인용![3]");
		//종료 처리 필수 안하면 Thread 부족 현상 발생함
		threadPool.shutdown();
		
		///결과 확인
		for (String result : resultList) {
			System.out.println(result);
		}
		System.out.println("순서 확인용![4]");
		
	}
	

}
