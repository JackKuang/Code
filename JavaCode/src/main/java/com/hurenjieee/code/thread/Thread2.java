package com.hurenjieee.code.thread;

public class Thread2 implements Runnable {

    private String name;

    public Thread2(String name) {
        super();
        this.name = name;
    }

    @Override
    public void run(){
        for ( int i = 1 ; i < 10 ; i++ ) {
            System.out.println(name + "运行第" + i + "次");
            try {
            	//在指定的毫秒数内让当前正在执行的线程休眠（暂停执行）
                Thread.sleep((int) (Math.random()*10));
                if (i == 6) {  
                	Thread.yield();  
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
    }


}
