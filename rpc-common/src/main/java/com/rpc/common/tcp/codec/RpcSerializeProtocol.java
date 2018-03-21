package com.rpc.common.tcp.codec;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author yin.huang
 * @date 2018年3月20日 下午1:54:00
 */
public enum RpcSerializeProtocol {
  
  JDKSERIALIZE("jdknative"), KRYOSERIALIZE("kryo"), HESSIANSERIALIZE("hessian");
  
  private String serializeProtocol;

  private RpcSerializeProtocol(String serializeProtocol) {
      this.serializeProtocol = serializeProtocol;
  }

  public String toString() {
      ReflectionToStringBuilder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE);
      return ReflectionToStringBuilder.toString(this);
  }

  public String getProtocol() {
      return serializeProtocol;
  }
  
}
