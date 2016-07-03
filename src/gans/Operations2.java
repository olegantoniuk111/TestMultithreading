package gans;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Operations2 {

	
	public static void main(String[] args) throws InterruptedException {
		
		ExecutorService service = Executors.newFixedThreadPool(3);		
		ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
		final Account a1 = new Account("KobainAccount", 1000000);
		final Account a2 = new Account("Armstrong", 50000);
		
		/*
		*FutureTask
		CountDownLatch 
		Semaphore  
		CyclicBarrier**/
		
	for (int i =0; i<10;i++){
		//service.submit(new Transfer(a1, a2, new Random().nextInt(1000), i));
		schedule.schedule(new Transfer(a2, a1, new Random().nextInt(150), i), 15,TimeUnit.SECONDS);
		
	}
		//service.shutdown();
		//service.awaitTermination(10,TimeUnit.SECONDS);
	
	}
}
