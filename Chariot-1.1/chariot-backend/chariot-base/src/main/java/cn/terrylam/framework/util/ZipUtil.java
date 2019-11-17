package cn.terrylam.framework.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ZipUtil {

    /**
     * zip压缩
     * @param str 被压缩的字符串
     * @param encode 压缩的字符集编码
     * @return
     * @throws IOException
     */
    public static String compress(String str,String encode) throws IOException {  
        if (str == null || str.length() == 0) {  
            return str;  
        }  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        GZIPOutputStream gzip = new GZIPOutputStream(out);  
        gzip.write(str.getBytes());  
        gzip.close();  
        return out.toString(encode);  
    }  
  
  
    /**
     * zip解压缩  
     * @param str 需要解压的字符串
     * @param encode 解压的字符集编码
     * @return
     * @throws IOException
     */
    public static String uncompress(String str,String encode) throws IOException {  
        if (str == null || str.length() == 0) {  
            return str;  
        }  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        ByteArrayInputStream in = new ByteArrayInputStream(  
                str.getBytes(encode));  
        GZIPInputStream gunzip = new GZIPInputStream(in);  
        byte[] buffer = new byte[256];  
        int n;  
        while ((n = gunzip.read(buffer)) >= 0) {  
            out.write(buffer, 0, n);  
        }  
        return out.toString(encode);  
    } 
    
    /**
     * zip压缩
     * @param str 被压缩的字符串
     * @return
     * @throws IOException
     */
    public static String compress(String str) throws IOException {  
        if (str == null || str.length() == 0) {  
            return str;  
        }  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        GZIPOutputStream gzip = new GZIPOutputStream(out);  
        gzip.write(str.getBytes());  
        gzip.close();  
        return out.toString("ISO-8859-1");
    }  
  
  
    /**
     * zip解压缩  
     * @param str 需要解压的字符串
     * @return
     * @throws IOException
     */
    public static String uncompress(String str) throws IOException {  
        if (str == null || str.length() == 0) {  
            return str;  
        }  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        ByteArrayInputStream in = new ByteArrayInputStream(  
                str.getBytes("ISO-8859-1"));  
        GZIPInputStream gunzip = new GZIPInputStream(in);  
        byte[] buffer = new byte[256];  
        int n;  
        while ((n = gunzip.read(buffer)) >= 0) {  
            out.write(buffer, 0, n);  
        }  
        return out.toString();  
    }  
    
    
}
