package com.rpc.common.tcp.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author yin.huang
 * @date 2018年3月19日 下午5:56:27
 */
public class AbstractSignal implements Serializable {

  /**
   * 
   */
  private static final long   serialVersionUID = -6961014185903986809L;

  private UUID                uuid             = UUID.randomUUID();

  private Map<String, Object> properties       = new HashMap<String, Object>();

  public void setIdentification(UUID id) {
    uuid = id;
  }

  public UUID getIdentification() {
    return uuid;
  }

  public Object getProperty(String key) {
    return properties.get(key);
  }

  public Map<String, Object> getProperties() {
    return Collections.unmodifiableMap(properties);
  }

  public void setProperty(String key, Object value) {
    properties.put(key, value);
  }

  public void setProperties(Map<String, Object> properties) {
    this.properties.clear();

    for (Map.Entry<String, Object> entry : properties.entrySet()) {
      if (null != entry.getValue()) {
        this.properties.put(entry.getKey(), entry.getValue());
      }
    }
  }

  public String toString() {

    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((properties == null) ? 0 : properties.hashCode());
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
    AbstractSignal other = (AbstractSignal) obj;
    if (properties == null) {
      if (other.properties != null)
        return false;
    } else if (!properties.equals(other.properties))
      return false;
    return true;
  }

}
