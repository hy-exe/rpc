package com.rpc.common.tcp.domain;

/**
 * @author yin.huang
 * @date 2018年3月20日 下午5:02:19
 */
public class GetInfoReq extends BaseXipRequest {

  /**
   * 
   */
  private static final long serialVersionUID = 8712288094746682010L;

  private String            name;

  private String            msg;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

}
