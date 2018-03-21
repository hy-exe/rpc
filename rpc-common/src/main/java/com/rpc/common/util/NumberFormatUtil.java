/*******************************************************************************
 * Copyright (c) 2014 ,Inc. All Rights Reserved.	
 * Filename:    NumberFormatUtil.java
 * Creator:     yunfu.wang
 * Create-Date: 2014-9-26 上午11:40:14
 *******************************************************************************/
package com.rpc.common.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 
 * @author yunfu.wang
 * @version $ NumberFormatUtil 2014-9-26 上午11:40:14 $
 */
public class NumberFormatUtil {

  public static double notCarryDecimal(double formatNum) {
    String formatStr = String.valueOf(formatNum);
    if (formatStr.indexOf(".") == -1) {
      return formatNum;
    } else {
      if (formatNum == (long) formatNum) {
        return formatNum;
      }
      int num = formatStr.length() - formatStr.indexOf(".") - 1;
      if (num < 2) {
        return formatNum;
      }
      return Double.parseDouble(formatStr.substring(0, formatStr.indexOf(".") + 3));
    }
  }

  public static double notCarryDecimal(double formatNum, int remainNum) {
    String formatStr = String.valueOf(formatNum);
    if (formatStr.indexOf(".") == -1) {
      return formatNum;
    } else {
      if (formatNum == (long) formatNum) {
        return formatNum;
      }
      int num = formatStr.length() - formatStr.indexOf(".") - 1;
      if (num < remainNum) {
        remainNum = num;
      }
      return Double.parseDouble(formatStr.substring(0, formatStr.indexOf(".") + remainNum + 1));
    }
  }

  public static String notCarryDecimalStr(double formatNum) {
    String formatStr = String.valueOf(formatNum);
    if (formatStr.indexOf(".") == -1) {
      return formatStr;
    } else {
      if (formatNum == (long) formatNum) {
        return String.valueOf((long) formatNum);
      }
      int num = formatStr.length() - formatStr.indexOf(".") - 1;
      if (num < 2) {
        return formatStr;
      }
      return formatStr.substring(0, formatStr.indexOf(".") + 3);
    }
  }

  public static String notCarryDecimalStr(double formatNum, int remainNum) {
    String formatStr = String.valueOf(formatNum);
    if (formatStr.indexOf(".") == -1) {
      return formatStr;
    } else {
      if (formatNum == (long) formatNum) {
        return String.valueOf((long) formatNum);
      }
      int num = formatStr.length() - formatStr.indexOf(".") - 1;
      if (num < remainNum) {
        remainNum = num;
      }
      return formatStr.substring(0, formatStr.indexOf(".") + remainNum + 1);
    }
  }

  public static double retainDecimal(double formatNum, int retainNum) {
    String format = "0.";
    for (int i = 0; i < retainNum; i++) {
      format += "#";
    }
    DecimalFormat df = new DecimalFormat(format);
    String formatStr = df.format(formatNum);
    return Double.parseDouble(formatStr);
  }

  public static String retainDecimalStr(double formatNum, int retainNum) {
    String format = "0.";
    for (int i = 0; i < retainNum; i++) {
      format += "#";
    }
    DecimalFormat df = new DecimalFormat(format);
    return df.format(formatNum);
  }

  public static String compareResult(double sourceData, double comparativeData) {
    DecimalFormat df = new DecimalFormat("#.##");
    NumberFormat nf = NumberFormat.getPercentInstance();
    if (sourceData >= comparativeData) {
      double num = 1;
      if (comparativeData != 0) {
        num = ((sourceData - comparativeData) / comparativeData);
      }
      return "+" + df.format(sourceData - comparativeData) + "(↑" + nf.format(num) + ")";
    } else {
      double num = 1;
      if (comparativeData != 0) {
        num = ((sourceData - comparativeData) / comparativeData);
      }
      return "-" + df.format(comparativeData - sourceData) + "(↓" + nf.format(num) + ")";
    }
  }

  public static String newAddCompareResult(long sourceData, long comparativeData) {
    if (comparativeData == 0) {
      return (sourceData - comparativeData) + "(--)";
    } else {
      long difference = sourceData - comparativeData;
      NumberFormat nf = NumberFormat.getPercentInstance();
      if (difference >= 0) {
        return difference + "(↑" + nf.format(difference * 1.0 / comparativeData) + ")";
      } else {
        return difference + "(↓" + nf.format(difference * (-1.0) / comparativeData) + ")";
      }
    }
  }

  public static String percentFomart(double sourceData) {
    NumberFormat nf = NumberFormat.getPercentInstance();
    if (sourceData == 1 || sourceData == 0) {
      return nf.format(sourceData);
    }
    return NumberFormatUtil.notCarryDecimalStr(sourceData * 100) + "%";
  }

  public static float retainDecimalFloat(float formatNum, int retainNum) {
    String format = "0.";
    for (int i = 0; i < retainNum; i++) {
      format += "#";
    }
    DecimalFormat df = new DecimalFormat(format);
    String formatStr = df.format(formatNum);
    return Float.parseFloat(formatStr);
  }

  public static float notCarryDecimalFloat(float formatNum, int remainNum) {
    String formatStr = String.valueOf(formatNum);
    if (formatStr.indexOf(".") == -1) {
      return formatNum;
    } else {
      if (formatNum == (long) formatNum) {
        return formatNum;
      }
      int num = formatStr.length() - formatStr.indexOf(".") - 1;
      if (num < remainNum) {
        remainNum = num;
      }
      return Float.parseFloat(formatStr.substring(0, formatStr.indexOf(".") + remainNum + 1));
    }
  }

  public static void main(String[] args) {
    System.out.println(retainDecimalFloat(0.5f, 3));
  }
}
