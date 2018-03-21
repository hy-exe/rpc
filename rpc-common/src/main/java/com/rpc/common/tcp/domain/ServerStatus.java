package com.rpc.common.tcp.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author yin.huang
 * @date 2018年3月19日 下午4:12:17
 */
public class ServerStatus implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 532141927140187643L;

  private String            ip;

  private int               port;

  private ServerCategory    category         = new ServerCategory();

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

  public ServerCategory getCategory() {
    return category;
  }

  public void setCategory(ServerCategory category) {
    this.category = category;
  }

  public void setDomain(String domain) {
    category.setDomain(domain);
  }

  public void setGroup(String group) {
    category.setGroup(group);
  }

  public boolean isSameDomain(ServerStatus server) {
    return category.isSameDomain(server.getCategory());
  }

  public String getDomain() {
    return category.getDomain();
  }

  public String getGroup() {
    return category.getGroup();
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }

}
