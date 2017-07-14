package com.kaishengit.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * Created by fankay on 2017/7/14.
 */
public class StringsUtil {

    private static Logger logger = LoggerFactory.getLogger(StringsUtil.class);

    public static String isoToUtf8(String str) {
        if(StringUtils.isEmpty(str)) {
            return null;
        }
        try {
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            logger.error("{}转码发生异常",str);
            throw new RuntimeException(str + "转码发生异常",ex);
        }
    }

}
