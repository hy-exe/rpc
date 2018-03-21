package com.rpc.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {

  public static String getMd5(String key) {
    return HexUtil.toHexString(md5(key));
  }

  public static String getMd5(String key, String charsetName) {
    return HexUtil.toHexString(md5(key, charsetName));
  }

  static MessageDigest getDigest() {
    try {
      return MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Calculates the MD5 digest and returns the value as a 16 element
   * <code>byte[]</code>.
   * 
   * @param data
   *          Data to digest
   * @return MD5 digest
   */
  public static byte[] md5(byte[] data) {
    return getDigest().digest(data);
  }

  /**
   * Calculates the MD5 digest and returns the value as a 16 element
   * <code>byte[]</code>.
   * 
   * @param data
   *          Data to digest
   * @return MD5 digest
   */
  public static byte[] md5(String data) {
    return md5(data.getBytes());
  }

  /**
   * Calculates the MD5 digest and returns the value as a 16 element
   * <code>byte[]</code>.
   * 
   * @param data
   *          Data to digest
   * @return MD5 digest
   */
  public static byte[] md5(String data, String charsetName) {
    try {
      return md5(data.getBytes(charsetName));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Calculates the MD5 digest and returns the value as a 32 character hex
   * string.
   * 
   * @param data
   *          Data to digest
   * @return MD5 digest as a hex string
   */
  public static String md5Hex(byte[] data) {
    return HexUtil.toHexString(md5(data));
  }

  /**
   * Calculates the MD5 digest and returns the value as a 32 character hex
   * string.
   * 
   * @param data
   *          Data to digest
   * @return MD5 digest as a hex string
   */
  public static String md5Hex(String data) {
    return HexUtil.toHexString(md5(data));
  }

  public static void main(String[] args) {
    System.out.println(Md5Utils.getMd5(
        "10098460077095324982865920026769359111.50.105.170178095656451000149259715918997159189金币礼包欢乐斗地主ZQYD3b5123b8036b4dbcaea441e8d2e5d566", "UTF-8"));
  }
}
