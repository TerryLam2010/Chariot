package cn.terrylam.framework.util;

import java.util.regex.Pattern;

/**
 * 字符串操作工具类
 * 已继承了org.apache.commons.lang3.StringUtils，
 * 如需使用一些工具方法时请仔细查看其api，其已有的方法可直接调用避免重复造轮子
 * http://commons.apache.org/proper/commons-lang/javadocs/api-3.1/org/apache/commons/lang3/StringUtils.html
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static String firstToUpper(String str) {
        if (str == null || "".equals(str)) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String firstToLower(String str) {
        if (str == null || "".equals(str)) {
            return str;
        }

        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }


    public static String splitTag(String input) {
        return splitTag(input, 0, null);
    }

    public static String splitTag(String input, int length) {
        return splitTag(input, length, "...");
    }

    public static String splitTag(String input, int length, String fill) {
        if (input == null || input.trim().equals("")) {
            return input;
        }
        // 去掉所有html元素,
        String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "");
        str = str.replaceAll("<[a-zA-Z]+[1-9]?[^><]*>", "");
        str = str.replaceAll("</[a-zA-Z]+[1-9]?>", "");
        if (length > 0) {
            int len = str.length();
            if (len <= length) {
                return str;
            } else {
                str = str.substring(0, length);
                if (fill != null) {
                    str += fill;
                }
            }
        }
        return str;
    }

    public static String substr(String input, int length) {
        return substr(input, length, "...");
    }

    public static String substr(String input, int length, String fill) {
        if (input == null) {
            return null;
        }
        if (input.length() > length) {
            input = input.substring(0, length);
            if (fill != null) {
                input += fill;
            }
        }
        return input;
    }

    public static String obj2String(Object obj) {
        if (obj == null) {
            return "";
        }
        return obj.toString();
    }

    /**
     * 替换xml文件中的特殊字符
     *
     * @param str
     * @return
     */
    public static String replaceXmlString(String str) {
        if (str == null) {
            return "";
        }
        str = str.replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("'", "&apos;")
                .replaceAll("\"", "&quot;");
        return str;
    }

    /**
     * 正则匹配
     *
     * @param reg
     * @param value
     * @return
     * @author Mike He
     */
    public static boolean regex(String reg, String value) {
        return Pattern.compile(reg).matcher(value).find();
    }


    public static String convert(String str, String dstCharset) {
        if (isEmpty(str))
            return "";
        try {
            return new String(str.getBytes(), dstCharset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String charsetConvert(String str, String srcCharset, String dstCharset) {
        if (isEmpty(str))
            return "";
        try {
            return new String(str.getBytes(srcCharset), dstCharset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将字符串从ISO格式转换为UTF-8格式<p>
     * <p>
     *
     * @param str
     * @return String
     */
    public static String iso2utf8(String str) {
        return charsetConvert(str, "ISO-8859-1", "UTF-8");
    }

    public static String gb23122iso(String str) {
        return charsetConvert(str, "GB2312", "ISO-8859-1");
    }

    public static String iso2gb2312(String str) {
        return charsetConvert(str, "ISO-8859-1", "GB2312");
    }

    /**
     * 将字符串从UTF-8格式转换为ISO格式<p>
     * <p>
     *
     * @param str
     * @return String
     */
    public static String utf82iso(String str) {
        return charsetConvert(str, "UTF-8", "ISO-8859-1");
    }

    /**
     * 将字符串从ISO格式转换为gb2312格式<p>
     * <p>
     *
     * @param str
     * @return String
     */
    public static String iso2gbk(String str) {
        return charsetConvert(str, "ISO-8859-1", "GBK");
    }

    /**
     * 将字符串从gb2312格式转换为ISO格式<p>
     * <p>
     *
     * @param str
     * @return String
     */
    public static String gbk2iso(String str) {
        return charsetConvert(str, "GBK", "ISO-8859-1");
    }

    /**
     * 将字符串从UTF-8格式转换为gbk格式<p>
     * <p>
     *
     * @param str
     * @return String
     */
    public static String utf82gbk(String str) {
        return charsetConvert(str, "UTF-8", "GBK");
    }

    /**
     * 将字符串从gb2312格式转换为UTF-8格式<p>
     * <p>
     *
     * @param str
     * @return String
     */
    public static String gbk2utf8(String str) {
        return charsetConvert(str, "GBK", "UTF-8");
    }

    public static String toISO(String str) {
        return convert(str, "ISO-8859-1");
    }

    /**
     * 去掉url的http协议头或https协议头
     *
     * @param url
     * @return
     * @author wyb
     * @update date 2016年11月11日
     */
    public static String urlToNoProtocol(String url) {
        if (isNotBlank(url)) {
            return url.replaceFirst("http:", "").replaceFirst("https:", "");
        }
        return url;
    }

}
