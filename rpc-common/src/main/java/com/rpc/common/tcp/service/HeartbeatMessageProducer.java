package com.rpc.common.tcp.service;

import com.rpc.common.tcp.domain.HeartbeatReq;

/**
 * @author yin.huang
 * @date 2018年3月19日 下午3:54:07
 */
public class HeartbeatMessageProducer {

  private String ip;

  private int    port;

  private String groupName;

  private String routerName;

  protected HeartbeatReq setCommonAttr(HeartbeatReq req) {
    req.getServerSnapshot().setIp(ip);
    req.getServerSnapshot().setPort(port);
    req.setGroupName(groupName);
    req.setRouterName(routerName);
    return req;
  }

  public HeartbeatReq getHeartbeatReq() {
    HeartbeatReq req = new HeartbeatReq();
    setCommonAttr(req);
    return req;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public String getRouterName() {
    return routerName;
  }

  public void setRouterName(String routerName) {
    this.routerName = routerName;
  }

}
