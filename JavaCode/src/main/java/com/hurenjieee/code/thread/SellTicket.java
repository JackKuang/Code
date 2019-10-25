package com.hurenjieee.code.thread;

public class SellTicket {
	public static void main(String[] args) {
		Ticket t = new Ticket();
		new Thread(t).start();
		new Thread(t).start();
	}
}

class Ticket implements Runnable {

	private int ticket = 10;

    @Override
	public void run() {
		while (ticket > 0) {
			synchronized (this) {
					ticket--;
					System.out.println("当前票数为：" + ticket);	
			}
		}

	}
}