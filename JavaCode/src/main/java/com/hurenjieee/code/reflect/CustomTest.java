package com.hurenjieee.code.reflect;

/**
 * @author renjie.hu@guuidea.com
 * @date 2020/1/3 16:17
 */
public class CustomTest {

  public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
      Class clazz = Class.forName("com.hurenjieee.code.reflect.RedisUserImpl");
      CustomInterface customInterface = (CustomInterface) clazz.newInstance();
      customInterface.init();
  }
}
