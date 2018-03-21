package com.rpc.common.util;

import java.lang.reflect.Method;

import org.apache.commons.lang.ArrayUtils;

/**
 * @author yin.huang
 * @date 2018年1月23日 下午4:36:08
 */
public class ClassUtil {

  public static Method[] getAllMethodOf(final Class<?> courseClass) {
    Method[] methods = null;

    Class<?> itr = courseClass;
    while (!itr.equals(Object.class)) {
      methods = (Method[]) ArrayUtils.addAll(itr.getDeclaredMethods(), methods);
      itr = itr.getSuperclass();
    }

    return methods;
  }

  public static String getSimpleName(Class<?> c) {
    return null != c ? c.getSimpleName() : null;
  }
}
