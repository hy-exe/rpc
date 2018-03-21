/**
 * 
 */
package com.rpc.common.tcp.domain;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 
 * @author yin.huang
 * @date 2018年1月26日 下午2:22:04
 *
 */
public class ServerGroup implements Serializable {

  /**
   * 
   */
  private static final long                         serialVersionUID = -6698622818534115468L;

  private String                                    name;

  private ConcurrentMap<IpPortPair, ServerSnapshot> servers          = new ConcurrentHashMap<IpPortPair, ServerSnapshot>();

  public ServerGroup(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ConcurrentMap<IpPortPair, ServerSnapshot> getServers() {
    return servers;
  }

  public void setServers(ConcurrentMap<IpPortPair, ServerSnapshot> servers) {
    this.servers = servers;
  }

}
