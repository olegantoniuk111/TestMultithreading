package gans;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.naming.InsufficientResourcesException;

public class Transfer implements Callable<Boolean> {
	public Transfer(Account accountFrom, Account accountTo, int amount) {
		super();
		this.accountFrom = accountFrom;
		this.accountTo = accountTo;
		this.amount = amount;
	}

	private Account accountFrom;
	private Account accountTo;
	private int amount;

	@Override
	public Boolean call() throws Exception {
		try {
			if (accountFrom.getLock().tryLock(1L, TimeUnit.SECONDS)) {
				try {
					if (accountTo.getLock().tryLock(3L, TimeUnit.SECONDS)) {
						try {
							if (accountFrom.getBalance() < amount) {
							System.out.println("ne vustachae koshtiv");
								throw new InsufficientResourcesException("NO money");
							} else {
								accountFrom.withdraw(amount);
								accountTo.deposit(amount);
								Thread.sleep(3000);
							}

						} finally {
							accountFrom.getLock().unlock();
						}
					} else {
						accountTo.incFailCounter();
						return false;
					}
				} finally {
					accountTo.getLock().unlock();
				}
			} else {
				accountFrom.incFailCounter();
				return false;
			}
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		return true;

	}

}
