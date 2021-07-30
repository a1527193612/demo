package com.example.demo.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;

public class Thread1 {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		System.out.println("main......start....");
		new Thread01().start();
		new Runnable01().run(); 
		FutureTask<Integer> futureTask = new FutureTask<Integer>(new Callable01());
		new Thread(futureTask).start();
		//阻塞等待整个线程执行完成，获取结果
		Integer integer = futureTask.get();
		System.out.println("main......end....");
		
		new ThreadPoolExecutor(5, 100, 10, 
				TimeUnit.SECONDS, 
				new LinkedBlockingDeque<Runnable>(10000), 
				Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

	}
	
	public static class Thread01 extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("当前线程id"+Thread.currentThread().getId());
			
		}
	} 
	
	public static class Runnable01 implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("当前线程id"+Thread.currentThread().getId());
		}
		
	}
	
	public static class Callable01 implements Callable<Integer>{

		@Override
		public Integer call() throws Exception {
			// TODO Auto-generated method stub
			System.out.println("当前线程id"+Thread.currentThread().getId());
			return 0;
		}
		
	}

}
