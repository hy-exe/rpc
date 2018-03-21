package com.rpc.common.tcp.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author yin.huang
 * @date 2018年3月19日 下午4:13:05
 */
public class ServerCategory implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -4131788150869426488L;

  private String            domain;

  private String            group;

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public void setCategory(String category) {
    int idx = category.indexOf('@');
    if (-1 != idx) {
      setDomain(category.substring(idx + 1));
      setGroup(category.substring(0, idx));
    } else {
      setDomain(category);
    }
  }

  public int hashCode() {
    int result = 1;
    result = 31 * result + (domain == null ? 0 : domain.hashCode());
    result = 31 * result + (group == null ? 0 : group.hashCode());
    return result;
  }

  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    if (domain == null) {
      if (domain != null)
        return false;
    } else if (!domain.equals(domain))
      return false;
    if (group == null) {
      if (group != null)
        return false;
    } else if (!group.equals(group))
      return false;
    return true;
  }

  public boolean isSameDomain(ServerCategory other) {
    if (null == other) {
      return false;
    }

    if (domain == null) {
      return null == domain;
    }
    return domain.equals(domain);
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
