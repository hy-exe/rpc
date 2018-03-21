package com.rpc.common.tcp.domain;

/**
 * @author yin.huang
 * @date 2018年3月20日 下午5:10:01
 */
public class BaseXipResponse extends AbstractSignal {

  /**
   * 
   */
  private static final long serialVersionUID = 8135918685322026679L;

  private int               errorCode;

  private String            errorMessage;

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public static <T extends BaseXipResponse> T createRespForError(Class<T> clazz, int errorCode, String errorMessage) {
    T resp;
    try {
      resp = clazz.newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
      return null;
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      return null;
    }

    resp.setErrorCode(errorCode);
    resp.setErrorMessage(errorMessage);

    return resp;
  }

}
