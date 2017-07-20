package com.kaishengit.util;

import java.io.UnsupportedEncodingException;

/**
 * Created by fankay on 2017/7/14.
 */
public class StringsUtil {

    public static String isoToUtf8(String str) {
        if(str == null || "".equals(str.trim())) {
            return null;
        }
        try {
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(str + "转码发生异常",ex);
        }
    }

}
