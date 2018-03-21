package com.rpc.common.tcp.service;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rpc.common.tcp.annotion.BizMethod;
import com.rpc.common.tcp.domain.BusinessCourse;
import com.rpc.common.util.ClassUtil;

/**
 * @author yin.huang
 * @date 2018年3月19日 上午10:26:25
 */
public class SimpleDispatcher implements Receiver {

  private static final Logger           logger       = LoggerFactory.getLogger(SimpleDispatcher.class);

  private ExecutorService               mainExecutor = Executors.newSingleThreadExecutor();

  private Map<Class<?>, BusinessCourse> courseTable  = new HashMap<Class<?>, BusinessCourse>();

  @Override
  public void messageReceived(final Object input) {

    mainExecutor.submit(new Runnable() {

      public void run() {
        final Object message = input;

        BusinessCourse course = getCourse(message.getClass());
        if (course == null) {
          return;
        }
        try {
          invokeBizMethod(course, message);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

  }

  private void invokeBizMethod(BusinessCourse course, Object msg) {
    Method bizMethod = getBizMethod(course.getClass(), msg.getClass());
    if (null != bizMethod) {
      try {
        bizMethod.invoke(course, msg);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      logger.info("No biz method found for message [" + msg.getClass().getName() + "]. No process execute.");
    }
  }

  private Method getBizMethod(Class<?> courseClass, Class<?> beanClass) {
    Method[] methods = ClassUtil.getAllMethodOf(courseClass);

    for (Method method : methods) {
      BizMethod biz = method.getAnnotation(BizMethod.class);

      if (null != biz) {
        Class<?>[] params = method.getParameterTypes();
        if (params.length < 1) {
          logger.info("Method [" + method.getName() + "] found but only [" + params.length + "] parameters found, need to be 1.");
          continue;
        }
        if (params[0].isAssignableFrom(beanClass)) {
          return method;
        }
      }
    }
    return null;
  }

  private BusinessCourse getCourse(Class<?> clazz) {
    return courseTable.get(clazz);
  }

  public void setCourses(Collection<BusinessCourse> courses) {
    for (BusinessCourse course : courses) {
      Method[] methods = ClassUtil.getAllMethodOf(course.getClass());
      for (Method method : methods) {
        BizMethod biz = method.getAnnotation(BizMethod.class);
        if (null != biz) {
          Class<?>[] params = method.getParameterTypes();
          if (params.length < 1) {
            continue;
          }
          courseTable.put(params[0], course);
        }
      }
    }
  }

  public void setThreads(int threads) {
    this.mainExecutor = Executors.newFixedThreadPool(threads);
  }

}
