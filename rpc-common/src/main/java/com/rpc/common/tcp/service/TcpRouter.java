package com.rpc.common.tcp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rpc.common.tcp.domain.IpPortPair;

/**
 * @author yin.huang
 * @date 2018年3月19日 下午2:08:38
 */
public class TcpRouter implements Sender {

  private static Logger                        logger       = LoggerFactory.getLogger(TcpRouter.class);

  private ConcurrentMap<IpPortPair, TcpClient> connectors   = new ConcurrentHashMap<IpPortPair, TcpClient>();

  private long                                 retryTimeout = 1;

  private List<IpPortPair>                     snapshot     = new ArrayList<IpPortPair>();

  private AtomicInteger                        index        = new AtomicInteger(0);

  @Override
  public void send(Object bean) {
    TcpClient connector = next();
    if (connector != null) {
      logger.info("sendAndWait: connector=[{}]" + connector + ", bean=[{}]" + bean);
      connector.send(bean);
    } else {
      logger.info("send: no route, msg [{}] lost.");
    }
  }

  @Override
  public void send(Object bean, Receiver receiver) {
    // TODO Auto-generated method stub
    TcpClient connector = next();
    if (connector != null) {
      logger.info("send: bean=[{}]" + bean);
      connector.send(bean, receiver);
    } else {
      logger.info("send: no route, msg [{}] lost." + bean);
    }
  }

  @Override
  public Object sendAndWait(Object bean) {
    TcpClient connector = next();
    if (connector != null) {
      return connector.sendAndWait(bean);
    } else {
      logger.info("send: no route, msg [{}] lost." + bean + " route=[{}]" + connector);
      return null;
    }
  }

  @Override
  public Object sendAndWait(Object bean, long timeout, TimeUnit timeUnit) {
    TcpClient connector = next();
    if (connector != null) {
      return connector.sendAndWait(bean, timeout, timeUnit);
    } else {
      logger.info("send: no route, msg [{}] lost." + bean);
      return null;
    }
  }

  private TcpClient next() {
    // 轮训
    int index = getIndex(snapshot.size());
    if (index >= 0) {
      IpPortPair info = snapshot.get(index);
      return getConnector(info);
    }

    return null;
  }

  private int getIndex(int total) {

    if (total > 0) {
      int next = index.getAndIncrement();
      if (next < 0) {
        next = 0;
        index.set(next);
      }
      return next % total;
    }
    return -1;
  }

  private TcpClient getConnector(IpPortPair info) {
    if (connectors.containsKey(info)) {
      return connectors.get(info);
    } else {
      return null;
    }
  }

  public void doRefreshRoute(List<IpPortPair> infos) {

    Collections.sort(infos);

    if (!snapshot.equals(infos)) {
      logger.info("doRefreshRoute() update routes info:[{}]." + infos);
      snapshot.clear();
      snapshot.addAll(infos);
    }

    for (IpPortPair info : snapshot) {
      createConnector(info.getIp(), info.getPort());
    }

    // 删除无效连接
    for (IpPortPair key : connectors.keySet()) {
      if (!snapshot.contains(key)) {
        TcpClient out = connectors.remove(key);
        if (null != out) {
          out.stop();
        }
      }
    }
  }

  private TcpClient createConnector(String ip, int port) {
    IpPortPair key = new IpPortPair(ip, port);
    TcpClient connector = connectors.get(key);

    if (null == connector) {
      connector = new TcpClient();
      TcpClient oldConnector = connectors.putIfAbsent(key, connector);
      if (null != oldConnector) {
        connector.stop();
        connector = oldConnector;
      } else {
        // connector.setReceiver(this.receiver);
        connector.setConnectIp(ip);
        connector.setConnectPort(port);
        connector.setRetryTimeout(this.retryTimeout);
        connector.start();
      }
    }
    return connector;
  }

}
