package com.hurenjieee.code.reflect;

public class Personer {

    private String name;

    public String nameShow;

    static{
        System.out.println("静态代码块运行");
    }
    
    public Personer() {
        super();
        System.out.println("构造方法");
    }

    public Personer(String name) {
        super();
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void testName(){
        System.out.println("无参方法调用：" + this.name);
    }

    public String testNameShow(String arg){
        System.out.println("有参方法调用：" + arg);
        return name;
    }
}
