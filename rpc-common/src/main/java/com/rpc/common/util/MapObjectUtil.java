package com.rpc.common.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author kun.zheng
 * @version 2014-9-25 下午1:55:11
 */
public class MapObjectUtil {

  /**
   * 将对象组装成Map对象
   * 
   * @param obj
   *          Date类型，将自动转化成String类型，格式:yyyy-MM-dd HH:mm:ss
   * @return
   */
  public static Map<String, Object> getMap(Object obj) {

    Map<String, Object> map = null;
    if (obj != null) {
      try {
        map = new HashMap<String, Object>();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
          String name = field.getName();
          String typeName = field.getType().getSimpleName();
          field.setAccessible(true);
          if (typeName.equals("String")) {
            if (field.get(obj) != null) {
              map.put(name, (String) field.get(obj));
            }
          } else if (typeName.equals("Short") || typeName.equals("short")) {
            if (field.get(obj) != null) {
              map.put(name, (Short) field.get(obj));
            }
          } else if (typeName.equals("Integer") || typeName.equals("int")) {
            if (field.get(obj) != null) {
              map.put(name, (Integer) field.get(obj));
            }
          } else if (typeName.equals("Long") || typeName.equals("long")) {
            if (field.get(obj) != null) {
              map.put(name, (Long) field.get(obj));
            }
          } else if (typeName.equals("Double") || typeName.equals("double")) {
            if (field.get(obj) != null) {
              map.put(name, (Double) field.get(obj));
            }
          } else if (typeName.equals("Float") || typeName.equals("float")) {
            if (field.get(obj) != null) {
              map.put(name, (Float) field.get(obj));
            }
          } else if (typeName.equals("Date")) {
            if (field.get(obj) != null) {
              SimpleDateFormat df = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
              map.put(name, df.format((Date) field.get(obj)));
            }
          }
        }
      } catch (SecurityException e) {
        e.printStackTrace();
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    return map;
  }
}
