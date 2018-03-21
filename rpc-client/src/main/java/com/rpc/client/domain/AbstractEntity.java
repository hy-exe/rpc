/*******************************************************************************
 * CopyRight (c) 2005-2013 JoyReach Ltd. All rights reserved. 
 * Filename: AbstractEntity.java 
 * Creator: wenqiang.xu 
 * Version: 1.0 Date: Jan. 11, 2014 3:45:08 PM
 * Description:
 *******************************************************************************/
package com.rpc.client.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author kun.zheng
 * @version 2014-9-16 下午6:26:29
 */
public class AbstractEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -1110827792219362154L;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
