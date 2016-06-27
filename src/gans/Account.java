package gans;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
	private int balance;
	private Lock lock;
	private AtomicInteger failCounter;

	public AtomicInteger getFailCounter() {
		return failCounter;
	}

	public Lock getLock() {
		return lock;
	}

	public void setLock(Lock lock) {
		this.lock = lock;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public Account(int initAccount) {
		lock = new ReentrantLock();
		this.balance = initAccount;
		failCounter = new AtomicInteger(0);

		System.out.println("ACCOUNT CREATED  BALANCE " + initAccount + "\n");
	}

	public void withdraw(int amount) {
		balance = balance - amount;
		System.out.println();
	}

	public void deposit(int amount) {
		balance = balance + amount;
	}

	public void incFailCounter() {
		this.failCounter.incrementAndGet();
	}
}
