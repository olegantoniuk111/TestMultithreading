package gans;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
	public Account(String name, int balance) {
		super();
		this.name = name;
		this.balance = balance;
		this.lock = new ReentrantLock();
		this.failCounter = new AtomicInteger(0);
		System.out.println( this.name +"account initialized with balance" + balance + "\n");
	}

	private String name;
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

	

	public void withdraw(int amount) {
		balance = balance - amount;
		System.out.println(this.name + " znyato "   + amount);
	}

	public void deposit(int amount) {
		balance = balance + amount;
		System.out.println(this.name + " zarahovano  "   + amount);
	}

	public void incFailCounter() {
		this.failCounter.incrementAndGet();
		System.out.println(this.name+"failcounter incremanted and =="+this.failCounter);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
