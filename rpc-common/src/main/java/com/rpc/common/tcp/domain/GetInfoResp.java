package com.rpc.common.tcp.domain;

/**
 * @author yin.huang
 * @date 2018年3月20日 下午5:02:19
 */
public class GetInfoResp extends BaseXipResponse {

  /**
   * 
   */
  private static final long serialVersionUID = 8712288094746682010L;

  private String            returnMsg;

  public String getReturnMsg() {
    return returnMsg;
  }

  public void setReturnMsg(String returnMsg) {
    this.returnMsg = returnMsg;
  }

}
