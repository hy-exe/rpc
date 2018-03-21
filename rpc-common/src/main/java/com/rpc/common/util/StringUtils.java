package com.rpc.common.util;


import java.io.*;
import java.net.URLEncoder;
import java.util.logging.Logger;

/**
 * 字符串处理工具类.
 *
 * @author <a href="http://www.jiangzezhou.com">jiangzezhou</a>
 * @version 1.0.0.0, 6/16/15 09::55
 */
public final class StringUtils {

    private static final Logger LOGGER = Logger.getLogger(StringUtils.class.getName());

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final String NULL_STR = "null";

    private StringUtils() {

    }


    public static boolean isEmpty(final String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        return false;
    }

    public static String urlEncodeWithUtf8(String encodeStr) {
        if (isEmpty(encodeStr)) {
            return null;
        }
        try {
            encodeStr = URLEncoder.encode(encodeStr, "utf-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("urlEncode str error");
        }

        return encodeStr;
    }


    public static boolean isEmptyOrNull(final String str) {
        if (str == null || "".equals(str) || NULL_STR.equals(str)) {
            return true;
        }
        return false;
    }


    public static String getStringFromBytes(final byte[] bytes, final String charset) {
        try {
            return new String(bytes, charset);
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("Get string from bytes error!");
        }
        return null;
    }

    public static byte[] getBytes(final String str, final String charset) {
        if (str == null) {
            return null;
        }
        try {
            return str.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("getBytes error");
        }
        return null;
    }


    public static byte[] getBytes(final String str) {
        return getBytes(str, DEFAULT_CHARSET);
    }

    public static String getStringFromBytes(final byte[] bytes) {
        return getStringFromBytes(bytes, DEFAULT_CHARSET);
    }

    /**
     * 避免调用null值变为"null"字符串.
     *
     * @param mayBeNullStr
     * @return
     */
    public static String kill2StrNull(final String mayBeNullStr) {
        if (mayBeNullStr == null || NULL_STR.equals(mayBeNullStr)) {
            return "";
        }
        return mayBeNullStr;
    }

    public static String getStrFromInputStream(final InputStream inputStream) {
        if (null == inputStream) {
            return null;
        }
        String content = null;
        try {
            final StringBuilder lineBuff = new StringBuilder();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream, DEFAULT_CHARSET));
            String line = null;
            while ((line = reader.readLine()) != null) {
                lineBuff.append(line);
            }
            content = lineBuff.toString();
            reader.close();
        } catch (IOException e) {
            LOGGER.info("Get InputStream error!");
        }
        return content;
    }


}
