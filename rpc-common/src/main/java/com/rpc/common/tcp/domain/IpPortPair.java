package com.rpc.common.tcp.domain;

import java.io.Serializable;

/**
 * @author yin.huang
 * @date 2018年3月19日 下午2:09:25
 */
public class IpPortPair implements Serializable, Comparable<IpPortPair> {

  /**
   * 
   */
  private static final long serialVersionUID = 6772184480808841884L;

  private String ip   = null;

  private int    port = 0;

  public IpPortPair() {
  }

  public IpPortPair(String ip, int port) {
    this.ip = ip;
    this.port = port;
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ip == null) ? 0 : ip.hashCode());
    result = prime * result + port;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    IpPortPair other = (IpPortPair) obj;
    if (ip == null) {
      if (other.ip != null)
        return false;
    } else if (!ip.equals(other.ip))
      return false;
    if (port != other.port)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return ip + ":" + port;
  }

  public int compareTo(IpPortPair o) {
    int rslt = this.ip.compareTo(o.ip);
    if (0 == rslt) {
      return this.port - o.port;
    }
    return rslt;
  }
}
