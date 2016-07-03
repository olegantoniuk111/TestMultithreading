package gans;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import javax.naming.InsufficientResourcesException;

public class Operations {

	public static void main(String[] args) throws InterruptedException {
		final Account gans = new Account("GansAccount", 2000);
		final Account kate = new Account("KateAccount", 1000);
		Semaphore semaphore = new Semaphore(1);

//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				try {
//					transfer(gans, kate, 5);
//				} catch (InsufficientResourcesException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			}
//		}).start();

		
			ExecutorService service = Executors.newFixedThreadPool(3);
			for (int i = 0; i < 10; i++) {
				service.submit(new Transfer(gans, kate, new Random().nextInt(250), i,semaphore) );
			}
			service.shutdown();
			service.awaitTermination(10, TimeUnit.SECONDS);
			
			
		}
	

	public static void transfer(Account acc1, Account acc2, int sum)
			throws InsufficientResourcesException {
		try {
			if (acc1.getLock().tryLock(60L, TimeUnit.SECONDS)) {
				try {
					if (acc2.getLock().tryLock(50L, TimeUnit.SECONDS)) {
						try {
							acc1.withdraw(sum);
							acc2.deposit(sum);
						} finally {
							acc2.getLock().unlock();
						}
					} else {
						acc2.incFailCounter();
					}
				} finally {
					acc1.getLock().unlock();
				}
			} else {
				acc1.incFailCounter();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
