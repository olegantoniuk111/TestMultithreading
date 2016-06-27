package gans;

import java.util.concurrent.TimeUnit;

import javax.naming.InsufficientResourcesException;

public class Operations {

	public static void main(String[] args) {
		final Account gans = new Account(10);
		final Account kate = new Account(20);

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					transfer(gans, kate, 5);
				} catch (InsufficientResourcesException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();

	}

	public static void transfer(Account acc1, Account acc2, int sum) throws InsufficientResourcesException {
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
