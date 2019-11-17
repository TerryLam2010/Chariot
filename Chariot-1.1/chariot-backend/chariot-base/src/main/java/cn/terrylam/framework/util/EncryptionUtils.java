package cn.terrylam.framework.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 需要对字符串Base64/MD5/SHA1/SHA256可直接调用此类方法实现
 * 用org.apache.commons.codec.*实现Base64/MD5/SHA1/SHA256算法
 */
public class EncryptionUtils   {
    /**
     * 对字符串Base64加密
     * @param data 需要加密的字符串
     * @return 返回字符串Base64加密后的字符串
     */
	public static String base64Encode( String data ) {
		return Base64.encodeBase64String( data.getBytes() );
	}
    /**
     * 字符串Base64解密
     * @param data 需要加密的字符串
     * @return 返回解密后的字节数组 
     */
	public static byte[] base64Decode( String data ) {
		return Base64.decodeBase64( data.getBytes() );
	}
    /**
     * 对字符串进行MD5加密 此加密方法不可逆
     * @param data 需要加密的字符串
     * @return
     */
	public static String md5( String data ) {
		return DigestUtils.md5Hex( data );
	}
    /**
     * 对字符串进行SHA1加密
     * @param data 需要加密的字符串
     * @return
     */
	public static String sha1( String data ) {
		return DigestUtils.shaHex( data );
	}
    /**
     * 对字符串进行SHA256加密
     * @param data 需要加密的字符串
     * @return
     */
	public static String sha256Hex( String data ) {
		return DigestUtils.sha256Hex( data );
	}
	

	/**
	 * @param args
	 */
	public static void main( String[] args ) {
		String base64 = base64Encode( "terry" );
		System.out.println( "base64 encode=" + base64 );

		byte[] buf = base64Decode( base64 );
		System.out.println( "base64 decode=" + new String( buf ) );

		String md5 = md5( "terry" );
		System.out.println( "md5=" + md5 + "\nlen=" + md5.length() );

		String sha1 = sha1( "terry" );
		System.out.println( "sha1=" + sha1 + "\nlen=" + sha1.length() );
		

	}

}
