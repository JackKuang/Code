package com.hurenjieee.code.thread;

public class Thread1 extends Thread {

    private String name;

    public Thread1(String name) {
        super();
        this.name = name;
    }

    @Override
    public void run(){
        for ( int i = 1 ; i < 10 ; i++ ) {
            System.out.println(name + "运行第" + i + "次");
            try {
            	//在指定的毫秒数内让当前正在执行的线程休眠（暂停执行）
                sleep((int) (Math.random()*10));
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
    }

}
