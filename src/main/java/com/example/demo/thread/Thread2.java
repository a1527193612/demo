package com.example.demo.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Thread2 {
	//创建线程池
	public static ExecutorService executor = Executors.newFixedThreadPool(10);
	public static void main(String[] args) {
		//method1();
		//method2();
		//method3();
		//method4();
		CompletableFuture<Object>  future1 = CompletableFuture.supplyAsync(()->{
			System.out.println("任务1开始");
			System.out.println(Thread.currentThread().getId()+Thread.currentThread().getName());
			System.out.println("任务1结束");
			return 10/2;
		}, executor);
		
		CompletableFuture<Object>  future2 = CompletableFuture.supplyAsync(()->{
			System.out.println("任务2开始");
			System.out.println(Thread.currentThread().getId()+Thread.currentThread().getName());
			System.out.println("任务2结束");
			return "hello";
		}, executor);
		
		CompletableFuture<Object>  future3 = CompletableFuture.supplyAsync(()->{
			System.out.println("任务3开始");
			System.out.println(Thread.currentThread().getId()+Thread.currentThread().getName());
			System.out.println("任务3结束");
			return "word";
		}, executor);
		
		CompletableFuture<Object> allOf = CompletableFuture.anyOf(future1,future2,future3);
		try {
			allOf.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void method4() {
		CompletableFuture<Object>  future1 = CompletableFuture.supplyAsync(()->{
			System.out.println("任务1开始");
			System.out.println(Thread.currentThread().getId()+Thread.currentThread().getName());
			System.out.println("任务1结束");
			return 10/2;
		}, executor);
		
		CompletableFuture<Object>  future2 = CompletableFuture.supplyAsync(()->{
			System.out.println("任务2开始");
			System.out.println(Thread.currentThread().getId()+Thread.currentThread().getName());
			System.out.println("任务2结束");
			return "hello";
		}, executor);
		
		future1.runAfterEitherAsync(future2, ()->{
			System.out.println("1,2只有一个做完就可以了");
		},executor);
		future1.acceptEitherAsync(future2, res->{
			System.out.println("1,2只有一个做完就可以了");
			System.out.println("我没有返回值");
		},executor);
		future1.applyToEitherAsync(future2, res->{
			System.out.println("1,2只有一个做完就可以了");
			System.out.println("我有返回值");
			return res;
		},executor);
	}
	private static void method3() {
		CompletableFuture<Integer>  future1 = CompletableFuture.supplyAsync(()->{
			System.out.println("任务1开始");
			System.out.println(Thread.currentThread().getId()+Thread.currentThread().getName());
			System.out.println("任务1结束");
			return 10/2;
		}, executor);
		
		CompletableFuture<String>  future2 = CompletableFuture.supplyAsync(()->{
			System.out.println("任务2开始");
			System.out.println(Thread.currentThread().getId()+Thread.currentThread().getName());
			System.out.println("任务2结束");
			return "hello";
		}, executor);
		
		//runAfterBothAsync  不能接受前面的结果
		future1.runAfterBothAsync(future2, ()->{
			System.out.println("任务3开始");
		},executor);
		
		future1.thenAcceptBothAsync(future2,(f1,f2)->{
			System.out.println("任务3开始");
			System.out.println("future1 res:"+f1);
			System.out.println("future2 res:"+f2);
			System.out.println("我能接受到前面返回的结果");
		},executor);
		
		future1.thenCombineAsync(future2,(f1,f2)->{
			System.out.println("我可以返回出数据");
			return f1+f2;
		}, executor);
	}
	private static void method2() {
		//方法执行完成后的处理
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(()->{
			System.out.println(Thread.currentThread().getId()+Thread.currentThread().getName());
			return 10/2;
		}, executor).handle((res,e)->{
			if (res!=null) {
				return res*2;
			}
			return 0;
		});
		try {
			System.out.println(future.get());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CompletableFuture  future1 = CompletableFuture.supplyAsync(()->{
			System.out.println(Thread.currentThread().getId()+Thread.currentThread().getName());
			return 10/2;
		}, executor).thenApply(res->{
			System.out.println("上一步的结果"+res);
			System.out.println("我能接受上一次的结果，而且我有返回值");
			return res*2;
		});
		try {
			System.out.println(future1.get());
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	private static void method1() {
		//runAsync  没有返回值
		CompletableFuture.runAsync(()->{
			System.out.println(Thread.currentThread().getId()+Thread.currentThread().getName());
		},executor); 
		//supplyAsync  有返回值
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(()->{
			System.out.println(Thread.currentThread().getId()+Thread.currentThread().getName());
			return 10/2;
		}, executor).whenComplete((res,e)->{
			//虽然能得到异常信息，但是没法修改返回数据
			System.out.println("异步任务完成....结果是"+res);
			System.out.println("异常是"+e.getMessage());
		}).exceptionally(throwable->{
			//感受异常，并修改结果
			
			return 10;
		});
		try {
			System.out.println(future.get());
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("出异常了");
		}
	}
}
