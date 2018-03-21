/**
 * 
 */
package com.rpc.common.tcp.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.rpc.common.util.DateUtil;

/**
 * 
 * @author yin.huang
 * @date 2018年1月26日 下午2:22:18
 *
 */
public class ServerSnapshot implements Serializable, Comparable<ServerSnapshot> {

  /**
   * 
   */
  private static final long serialVersionUID = 1512855862796997532L;

  private String            ip;

  private int               port;

  private long              heartbeatTime;

  private long              starttime;

  public ServerSnapshot() {
    this.heartbeatTime = System.currentTimeMillis();
    this.starttime = heartbeatTime;
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

  public Date getStarttime() {
    return new Date(starttime);
  }

  public long getRunningTime() {
    return Math.abs(DateUtil.getNumberOfSecondsBetween(heartbeatTime, starttime));
  }

  public void setStarttime(long starttime) {
    this.starttime = starttime;
  }

  public void setHeartbeatTime(long heartbeatTime) {
    this.heartbeatTime = heartbeatTime;
  }

  public long getHeartbeatTime() {
    return heartbeatTime;
  }

  public boolean isRunning(long timeOutMillis) {
    return (System.currentTimeMillis() - heartbeatTime) <= timeOutMillis * 1000;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  public int compareTo(ServerSnapshot o) {
    int rslt = this.getIp().compareTo(o.getIp());
    if (0 == rslt) {
      return this.getPort() - o.getPort();
    }
    return rslt;
  }
}
