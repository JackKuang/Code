package com.hurenjieee.code.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectTest {

    public static void main(String[] args){
        try {
            //三种方法调用
            //1.调用某个对象的getClass()方法
            //Personer personer1 = new Personer();
            //Class class1 = personer1.getClass();
            
            //2.调用某个类的class属性来获取该类对应的Class对象
            //Class class2=Personer.class;
            //Personer personer2 = (Personer) class2.newInstance();
            
            //3.使用Class类中的forName()静态方法; (最安全/性能最好)
            Class class3 = Class.forName("com.hurenjieee.code.reflect.Personer");
            Personer personer3 = (Personer)class3.newInstance();
            
            System.out.println("所有方法信息");
            //获取Person类的所有方法信息
            Method[] method=class3.getDeclaredMethods();
            for(Method m:method){
                System.out.println(m.toString());
            }

            System.out.println("所有成员属性信息");
            //获取Person类的所有成员属性信息
            Field[] field=class3.getDeclaredFields();
            for(Field f:field){
                System.out.println(f.toString());
            }

            System.out.println("所有构造方法信息");
            //获取Person类的所有构造方法信息
            Constructor[] constructor=class3.getDeclaredConstructors();
            for(Constructor c:constructor){
                System.out.println(c.toString());
            }
            
            //调用方法
            Method method2 = class3.getMethod("setName",String.class);
            method2.invoke(personer3,"张三");
            Method method3 = class3.getMethod("testName");
            method3.invoke(personer3);
            Method method4 = class3.getMethod("testNameShow",String.class);
            System.out.println(method4.invoke(personer3,"AAA"));
            
            //调用属性
            Field field2 = class3.getField("nameShow");
            field2.set(personer3,"李四");
            System.out.println(field2.get(personer3));
            
            //调用构造方法
            Constructor constructor2 = class3.getConstructor(String.class);
            Personer personer4= (Personer) constructor2.newInstance("王五");
            System.out.println(personer4.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
}
