package com.rpc.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Description:加密工具类
 */
public class SHAUtil {
  private static final Log log = LogFactory.getLog(SHAUtil.class);

  /**
   * 计算消息SHA-1摘要。
   * 
   * @param data
   *          计算摘要的数据。
   * @return 摘要结果。(20字节)
   */
  public static String shaStr(String data) {
    byte[] by = data.getBytes();
    return shaStr(by, 0, by.length);
  }

  /**
   * 计算消息SHA-1摘要。
   * 
   * @param data
   *          计算摘要的数据。
   * @param offset
   *          数据偏移地址。
   * @param length
   *          数据长度。
   * @return 摘要结果。(20字节)
   */
  public static String shaStr(byte[] data, int offset, int length) {
    try {
      MessageDigest sha = MessageDigest.getInstance("SHA-1");
      sha.update(data, offset, length);
      return byteArrayToHexString(sha.digest());
    } catch (NoSuchAlgorithmException ex) {
      log.error(ex.toString());
      return null;
    }
  }

  /**
   * Description :字节数组转16进制
   * 
   * @param params
   *          b(byte[])-需要转换的字节数组
   * @return result retVal(String)-转化后的十六进制字符串;
   * @exception 该应用不存在
   */
  public static String byteArrayToHexString(byte[] b) {
    String result = "";
    for (int i = 0; i < b.length; i++) {
      result += byteToHexString(b[i]);
    }
    return result;
  }

  public static String byteToHexString(byte b) {
    int n = b;
    if (n < 0) {
      n = 256 + n;
    }
    int d1 = n / 16;
    int d2 = n % 16;
    return hexDigits[d1] + hexDigits[d2];
  }

  private static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
}
