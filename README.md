# java.completableFuture
자바 비동기 처리 클래스

기본 코드	

	CompletableFuture<String> future = new CompletableFuture<>();
	Executors.newCachedThreadPool().submit(() -> {
		Thread.sleep(2000);
		future.complete("Finished");
		return null;
	});	
	log(future.get());

테스트 코드1 
	
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


    
테스트코드 2 ( 비동기 처리 결과 동기 처리 ) 

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