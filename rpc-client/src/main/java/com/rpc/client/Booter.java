package com.rpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rpc.client.service.ICommonTcpService;

/**
 * 
 * @author yin.huang
 * @date 2018年3月19日 上午11:16:42
 *
 */
public class Booter {

  private static final Logger logger = LoggerFactory.getLogger(Booter.class);

  public static void main(String[] args) {

    // EmbbedJetty.main(args);

    try {
      String[] sources = new String[] { "spring/applicationContext.xml", "spring/applicationContext-persistence.xml", "spring/applicationContext-dao.xml",
          "spring/applicationContext-service.xml", "spring/applicationContext-cache.xml" };
      ClassPathXmlApplicationContext appt = new ClassPathXmlApplicationContext(sources);
      ICommonTcpService commonTcpService = (ICommonTcpService) appt.getBean("commonTcpService");
      Thread.sleep(2000);
      logger.info("rpc info client Started... ");
      commonTcpService.getBscInfo();
    } catch (Exception e) {
      logger.error("rpc info client Start Error: ", e);
      System.exit(-1);
    }

  }
}
