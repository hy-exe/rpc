package com.rpc.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public final class SecurityUtils {
  private static final Logger LOGGER =
      Logger.getLogger(SecurityUtils.class.getName());

public static String md5(final String str) {
  if (str == null) {
      return null;
  }
  final byte[] buffer = StringUtils.getBytes(str);
  MessageDigest messageDigest = null;
  try {
      messageDigest = MessageDigest.getInstance("md5");
      messageDigest.update(buffer);
      return bytes2Hex(messageDigest.digest());
  } catch (NoSuchAlgorithmException e) {
      LOGGER.info("md5 error:" + e.getMessage());
  }
  return null;
}



/**
* 转为十六进制.
*
* @param bts
* @return
* @see
*/
public static String bytes2Hex(byte[] bts) {
  String des = "";
  String tmp = null;
  for (int i = 0; i < bts.length; i++) {
      tmp = (Integer.toHexString(bts[i] & 0xFF));
      if (tmp.length() == 1) {
          des += "0";
      }
      des += tmp;
  }
  return des;
}

/**
* AES解密
*
* @param str 待解密内容
* @param in  解密密钥 必须为16的整数倍
* @return
*/
public static byte[] aesDecrypt(byte[] str, String in) {
  try {
      SecretKeySpec key = new SecretKeySpec(in.getBytes(), "AES");
      // 创建密码器
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      IvParameterSpec zeroIv = new IvParameterSpec(
              "0102030405060708".getBytes());
      // 初始化
      cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
      // 加密
      return cipher.doFinal(str);
  } catch (Exception e) {
      e.printStackTrace();
      return null;
  }
}

/**
* AES加密
*
* @param content 需要加密的内容 待加密内容的长度必须是16的倍数
* @param in      加密密码 必须是16位
* @return
*/
public static String aesEncrypt(String content, String in) {
  try {
      SecretKeySpec key = new SecretKeySpec(in.getBytes(), "AES");
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      byte[] byteContent = content.getBytes("utf-8");
      IvParameterSpec zeroIv = new IvParameterSpec(
              "0102030405060708".getBytes());
      cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
      return bytes2Hex(cipher.doFinal(byteContent));
  } catch (Exception e) {
      e.printStackTrace();
      return null;
  }
}
}
