/**
 * 
 */
package com.rpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author yin.huang
 * @date 2018年3月19日 上午10:03:49
 *
 */
public class Booter {

  private static final Logger logger = LoggerFactory.getLogger(Booter.class);

  public static void main(String[] args) {
    try {
      String[] sources = new String[] { "spring/applicationContext.xml", "spring/applicationContext-persistence.xml", "spring/applicationContext-biz.xml",
          "spring/applicationContext-dao.xml", "spring/applicationContext-service.xml", "spring/applicationContext-cache.xml" };
      new ClassPathXmlApplicationContext(sources);
      
      logger.info("rpc info Server Started... ");
    } catch (Exception e) {
      logger.error("rpc info Server Start Error: ", e);
      System.exit(-1);
    }
  }
}
