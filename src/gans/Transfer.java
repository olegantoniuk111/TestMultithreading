package gans;


import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.naming.InsufficientResourcesException;

public class Transfer implements Callable<Boolean> {
	public Transfer(Account accountFrom, Account accountTo, int amount,int id) {
		super();
		this.accountFrom = accountFrom;
		this.accountTo = accountTo;
		this.amount = amount;
		this.id = id;
		
	}

	private Account accountFrom;
	private Account accountTo;
	private int amount;
	private int id;

	@Override
	public Boolean call() throws Exception {
	System.out.println("Transfer "+ this.id + "started");
		try {
			if (accountFrom.getLock().tryLock(5L, TimeUnit.SECONDS)) {
				try {
					if (accountTo.getLock().tryLock(5L, TimeUnit.SECONDS)) {
						try {
							if (accountFrom.getBalance() < amount) {
							System.out.println("ne vustachae koshtiv");
							throw new InsufficientResourcesException("NO money");
							} else {
								accountFrom.withdraw(amount);
								accountTo.deposit(amount);
								Thread.sleep(3000);
								System.out.println("Transfer " + this.id+ " done");
								
							}

						} finally {
							accountTo.getLock().unlock();
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

	public int getId() {
		return id;
	}

}
