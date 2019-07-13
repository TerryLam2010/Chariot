package cn.terrylam.framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpMessage;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import cn.terrylam.chariot.base.config.BaseProperties;


/**
 * http4工具类
 */
public class HttpClientUtils {

	public static final int CONNECT_TIME_OUT = 5000; // 设置超时时间(毫秒)

	public static final int RETRY_HANDLER_LIMIT = 2; // 超时重试次数
	//应用代理
	public static BaseProperties  baseProperties = SpringCtxUtils.getBean(BaseProperties.class);

	/**
	 * 通过HttpGet拿URL对应的数据
	 * 
	 * @param url 目标URL
	 * @param charset 解码字符
	 * @param proxy 是否使用代理
	 */
	public static String httpGet( String url, String charset, boolean proxy, Map<String, String> headers ) throws IOException {

		DefaultHttpClient httpClient = null;
		HttpGet httpGet = null;
		InputStream is = null;
		try {
			httpClient = new DefaultHttpClient();
			// 初始化参数
			initConfig( httpClient );

			if( proxy ) {
				initProxy( httpClient );
			}

			httpGet = new HttpGet( url );
			if( headers != null && !headers.isEmpty() ) {
				initHeaders( headers, httpGet );
			}

			HttpResponse response = httpClient.execute( httpGet );

			int statusCode = response.getStatusLine().getStatusCode();// 返回状态码
			if( statusCode == HttpStatus.SC_OK ) {
				if( isGzip( response ) ) {
					HttpEntity entity = response.getEntity();
					is = entity.getContent();
					GZIPInputStream gzin = new GZIPInputStream( is );
					InputStreamReader isr = new InputStreamReader( gzin, charset ); // 设置读取流的编码格式，自定义编码
					BufferedReader br = new BufferedReader( isr );
					String tempbf;
					StringBuffer sb = new StringBuffer();
					while( (tempbf = br.readLine()) != null ) {
						sb.append( tempbf );
						sb.append( "\r\n" );
					}
					isr.close();
					gzin.close();
					return sb.toString();
				}
				else {
					return EntityUtils.toString( response.getEntity(), charset );
				}
			}
		}
		finally {
			if( httpGet != null ) {
				httpGet.releaseConnection();
			}
			if( httpClient != null ) {
				httpClient.getConnectionManager().shutdown();
			}
			if( is != null ) {
				is.close();
			}
		}
		return "";
	}

	/**
	 * 通过HttpPost提交数据或获取URL返回的数据
	 * 
	 * @param url 目标URL
	 * @param charset 解码字符
	 * @param encoding 编码
	 * @param proxy 是否使用代理
	 * @param headers 头部参数
	 * @param params 参数
	 */
	public static String httpPost( String url, String charset, String encoding, boolean proxy, Map<String, String> headers,
	        Map<String, String> params ) throws IOException {

		DefaultHttpClient httpClient = null;
		HttpPost httpost = null;
		try {
			httpClient = new DefaultHttpClient();
			// 初始化参数
			if( proxy ) {
				initProxy( httpClient );
			}

			httpost = new HttpPost( url );
			if( headers != null && !headers.isEmpty() ) {
				initHeaders( headers, httpost );
			}

			if( params != null && !params.isEmpty() ) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				Set<String> keySet = params.keySet();
				for( String key : keySet ) {
					nvps.add( new BasicNameValuePair( key, params.get( key ) ) );
				}
				if( StringUtils.isNotBlank( encoding ) ) {
					httpost.setEntity( new UrlEncodedFormEntity( nvps, encoding ) );
				}
				else {
					httpost.setEntity( new UrlEncodedFormEntity( nvps ) );
				}
			}

			HttpResponse response = httpClient.execute( httpost );
			int statusCode = response.getStatusLine().getStatusCode();// 返回状态码
			String info = "";
			if( statusCode == HttpStatus.SC_OK ) {
				info = EntityUtils.toString( response.getEntity(), charset );
			}
			return info;
		}
		finally {
			if( httpost != null ) {
				httpost.releaseConnection();
			}
			if( httpClient != null ) {
				httpClient.getConnectionManager().shutdown();
			}
		}
	}

	public static String httpGet( String url, String charset, boolean proxy ) throws IOException {

		return httpGet( url, charset, proxy, null );
	}

	/**
	 * 通过HttpsGet拿URL对应的数据。 只支持https协议，访问http请用httpGet
	 * 
	 * @param url 目标URL
	 * @param charset 解码字符
	 * @param proxy 是否使用代理
	 * @param headers http 请求头设置：如 headers.put("Authorization","authorization");
	 * @update date 2014年08月19日
	 */
	public static String httpsGet( String url, String charset, boolean proxy, Map<String, String> headers ) throws IOException {

		DefaultHttpClient httpClient = null;
		HttpGet httpGet = null;
		try {
			httpClient = createHttpsClient();
			// 初始化参数
			initConfig( httpClient );

			if( proxy ) {
				initProxy(httpClient);
			}

			httpGet = new HttpGet( url );

			initHeaders( headers, httpGet );// headers
											// null会抛出RuntimeException，提示调用者。

			HttpResponse response = httpClient.execute( httpGet );
			int statusCode = response.getStatusLine().getStatusCode();// 返回状态码
			if( statusCode == HttpStatus.SC_OK ) {
				return EntityUtils.toString( response.getEntity(), charset ).trim();
			}
		}
		finally {
			if( httpGet != null ) {
				httpGet.releaseConnection();
			}
			if( httpClient != null ) {
				httpClient.getConnectionManager().shutdown();
			}
		}
		return "";
	}

	private static void initHeaders( Map<String, String> headers, HttpMessage httpMessage ) {

		for( Map.Entry<String, String> entrys : headers.entrySet() ) {
			String key = entrys.getKey();
			String value = entrys.getValue();
			Header header = new BasicHeader( key, value );
			httpMessage.addHeader( header );
		}
	}

	/**
	 * 初始化参数，统一入口
	 * 
	 * @param httpClient
	 */
	private static void initConfig( DefaultHttpClient httpClient ) {

		// httpclient4.2.3超时参数，必须设置，否则会阻塞，超时会有java.net.SocketTimeoutException
		httpClient.getParams().setParameter( CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECT_TIME_OUT );// 连接时间
		httpClient.getParams().setParameter( CoreConnectionPNames.SO_TIMEOUT, CONNECT_TIME_OUT );// 数据传输时间

		// 超时处理器
		httpClient.setHttpRequestRetryHandler( new SimpleHttpRequestRetryHandler() );
	}

	/**
	 * 初始化HttpClient实例代理
	 * 代理ip等相关信息一般从配置文件读取，也可直接硬编码指定
	 * @param httpClient
	 */
	private static void initProxy( HttpClient httpClient ) {

		// 代理基本配置
		String ip = baseProperties.getProxyIp();
		int port = baseProperties.getProxyPort();
		String user = baseProperties.getProxyUser();
		String password = baseProperties.getProxyPassword();

		// 代理
		HttpHost proxy = new HttpHost( ip, port );
		httpClient.getParams().setParameter( ConnRoutePNames.DEFAULT_PROXY, proxy );

		// 认证
		((DefaultHttpClient)httpClient).getCredentialsProvider().setCredentials(
		    new AuthScope( AuthScope.ANY_HOST, AuthScope.ANY_PORT ), new UsernamePasswordCredentials( user, password ) );

	}

	public static DefaultHttpClient createHttpsClient() {

		SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getInstance( "SSL" );

			// set up a TrustManager that trusts everything
			sslContext.init( null, new TrustManager[] { new X509TrustManager() {
				@Override
				public void checkClientTrusted( X509Certificate[] x509Certificates, String s ) throws CertificateException {

				}

				@Override
				public void checkServerTrusted( X509Certificate[] x509Certificates, String s ) throws CertificateException {

				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {

					return null;
				}
			} }, new SecureRandom() );
		}
		catch( NoSuchAlgorithmException e ) {
			throw new RuntimeException( e );
		}
		catch( KeyManagementException e ) {
			throw new RuntimeException( e );
		}

		SSLSocketFactory sf = new SSLSocketFactory( sslContext );
		Scheme httpsScheme = new Scheme( "https", 443, sf ); // https
		Scheme httpScheme = new Scheme( "http", 80, PlainSocketFactory.getSocketFactory() ); // http
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register( httpsScheme );
		schemeRegistry.register( httpScheme );

		// apache HttpClient version >4.2 should use
		// BasicClientConnectionManager
		ClientConnectionManager cm = new BasicClientConnectionManager( schemeRegistry );
		return new DefaultHttpClient( cm );
	}

	/**
	 * 超时重试处理
	 */
	static class SimpleHttpRequestRetryHandler implements HttpRequestRetryHandler {

		@Override
		public boolean retryRequest( IOException exception, int executionCount, HttpContext context ) {

			if( executionCount >= RETRY_HANDLER_LIMIT ) {
				// Do not retry if over max retry count
				return false;
			}
			if( exception instanceof InterruptedIOException ) {
				// Timeout
				return false;
			}
			if( exception instanceof UnknownHostException ) {
				// Unknown host
				return false;
			}
			if( exception instanceof ConnectException ) {
				// Connection refused
				return false;
			}
			if( exception instanceof SSLException ) {
				// SSL handshake exception
				return false;
			}
			HttpRequest request = (HttpRequest)context.getAttribute( ExecutionContext.HTTP_REQUEST );
			boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
			if( idempotent ) {
				// Retry if the request is considered idempotent
				return true;
			}
			return false;
		}

	}

	public static boolean isGzip( HttpResponse response ) {
		Header[] h = response.getHeaders( "Content-Encoding" );
		if( h != null && h.length > 0 ) {
			for( Header header : h ) {
				if( header.getValue().contains( "gzip" ) ) {
					return true;
				}
			}
		}
		return false;
	}
}
