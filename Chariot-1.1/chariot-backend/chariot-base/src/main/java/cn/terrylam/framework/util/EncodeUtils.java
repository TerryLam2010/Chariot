package cn.terrylam.framework.util;

import java.net.URLEncoder;

/**
 *
 */
public class EncodeUtils {

    /**
     * Encode data for use in HTML using HTML entity encoding
     * Only encode 6 base chars.
     * 
     * @param   input the text to encode for HTML
     * @return  input encoded for HTML
     */
    public static String encodeForHTML(String input) {
        if (input == null) {
            return input;
        }

        StringBuilder sb = new StringBuilder(input.length());

        for (int i = 0, c = input.length(); i < c; i++) {
            char ch = input.charAt(i);
            switch (ch) {
                case '&':
                    sb.append("&amp;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '"':
                    sb.append("&quot;");
                    break;
                case '\'':
                    sb.append("&#x27;");
                    break;
                case '/':
                    sb.append("&#x2F;");
                    break;
                default:
                    sb.append(ch);
            }
        }
        return sb.toString();
    }

    /**
     * Encode data for insertion inside a data value or function argument in JavaScript. Including user data 
     * directly inside a script is quite dangerous. Great care must be taken to prevent including user data
     * directly into script code itself, as no amount of encoding will prevent attacks there.
     * 
     * @param   input the text to encode for JavaScript
     * @return  input encoded for use in JavaScript
     */
    public static String encodeForJavascript(String input) {
        if (input == null) {
            return input;
        }

        StringBuilder sb = new StringBuilder(input.length());

        for (int i = 0, c = input.length(); i < c; i++) {
            char ch = input.charAt(i);

            // do not encode alphanumeric characters and ',' '.' '_'
            if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' ||
                    ch >= '0' && ch <= '9' ||
                    ch == ',' || ch == '.' || ch == '_') {
                sb.append(ch);
            } else {
                String temp = Integer.toHexString(ch);

                // encode up to 256 with \\xHH
                if (ch < 256) {
                    sb.append('\\').append('x');
                    if (temp.length() == 1) {
                        sb.append('0');
                    }
                    sb.append(temp.toLowerCase());

                // otherwise encode with \\uHHHH
                } else {
                    sb.append('\\').append('u');
                    for (int j = 0, d = 4 - temp.length(); j < d; j ++) {
                        sb.append('0');
                    }
                    sb.append(temp.toUpperCase());
                }
            }
        }

        return sb.toString();
    }

    /**
     * Encode data for use in Cascading Style Sheets (CSS) content.
     * 
     * @see <a href="http://www.w3.org/TR/CSS21/syndata.html#escaped-characters">CSS Syntax [w3.org]</a>
     * 
     * @param input 
     *         the text to encode for CSS
     * 
     * @return input encoded for CSS
     */
    public static String encodeForCSS(String input) {
        if (input == null) {
            return input;
        }

        StringBuilder sb = new StringBuilder(input.length());

        for (int i = 0, c = input.length(); i < c; i++) {
            char ch = input.charAt(i);

            // check for alphanumeric characters
            if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' ||
                    ch >= '0' && ch <= '9') {
                sb.append(ch);
            } else {
                // return the hex and end in whitespace to terminate
                sb.append('\\').append(Integer.toHexString(ch)).append(' ');
            }
        }
        return sb.toString();
    }

    /**
     * Encode parameter for use in a URL. This method performs 
     * <a href="http://en.wikipedia.org/wiki/Percent-encoding">URL encoding</a>
     * on the entire string.
     * 
     * @see <a href="http://en.wikipedia.org/wiki/Percent-encoding">URL encoding</a>
     * 
     * @param input 
     *         the text to encode for use in a URL
     * 
     * @return input 
     *         encoded for use in a URL
     */ 
    public static String encodeURIComponent(String input) {
        return encodeURIComponent(input, "utf-8");
    }

    /**
     * Encode parameter for use in a URL. This method performs 
     * <a href="http://en.wikipedia.org/wiki/Percent-encoding">URL encoding</a>
     * on the entire string.
     * 
     * @see <a href="http://en.wikipedia.org/wiki/Percent-encoding">URL encoding</a>
     * 
     * @param input 
     *         the text to encode for use in a URL
     * @param encoding 
     *      encoding to use
     * 
     * @return input 
     *         encoded for use in a URL
     */ 
    public static String encodeURIComponent(String input, String encoding) {
        if (input == null) {
            return input;
        }
        String result;
        try {
            result = URLEncoder.encode(input, encoding);
        } catch (Exception e) {
            result = "";
        }
        return result;
    }

    /**
     * Check is input a valid URL
     * URL must starts with http://, https:// or ftp://
     * URL must not contains char '\'' and '\"'
     * 
     * @param input URL to check
     * @return check result
     */
    public static boolean isValidURL(String input) {
        if (input == null || input.length() < 8) {
            return false;
        }
        char ch0 = input.charAt(0);
        if (ch0 == 'h') {
            if (input.charAt(1) == 't' &&
                input.charAt(2) == 't' &&
                input.charAt(3) == 'p') {
                char ch4 = input.charAt(4);
                if (ch4 == ':') {
                    if (input.charAt(5) == '/' &&
                        input.charAt(6) == '/') {

                        return isValidURLChar(input, 7);
                    } else {
                        return false;
                    }
                } else if (ch4 == 's') {
                    if (input.charAt(5) == ':' &&
                        input.charAt(6) == '/' &&
                        input.charAt(7) == '/') {

                        return isValidURLChar(input, 8);
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } else if (ch0 == 'f') {
            if( input.charAt(1) == 't' &&
                input.charAt(2) == 'p' &&
                input.charAt(3) == ':' &&
                input.charAt(4) == '/' &&
                input.charAt(5) == '/') {

                return isValidURLChar(input, 6);
            } else {
                return false;
            }
        }
        return false;
    }

    static boolean isValidURLChar(String url, int start) {
        for (int i = start, c = url.length(); i < c; i ++) {
            char ch = url.charAt(i);
            if (ch == '"' || ch == '\'') {
                return false;
            }
        }
        return true;
    }

}