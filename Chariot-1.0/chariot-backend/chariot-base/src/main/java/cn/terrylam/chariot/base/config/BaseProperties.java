package cn.terrylam.chariot.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseProperties {

	// pc域名
	@Value("${app.domain:}")
	private String domain;

	// wap域名
	@Value("${app.wap.domain:}")
	private String wapDomain;

	// 应用代理ip
	@Value("${sys.proxy.ip:}")
	private String proxyIp;

	// 应用代理端口
	@Value("${sys.proxy.port:8080}")
	private int proxyPort;

	// 应用代理账号
	@Value("${sys.proxy.user:}")
	private String proxyUser;

	// 应用代理账号密码
	@Value("${sys.proxy.password:}")
	private String proxyPassword;


	
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getWapDomain() {
		return wapDomain;
	}

	public void setWapDomain(String wapDomain) {
		this.wapDomain = wapDomain;
	}

	public String getProxyIp() {
		return proxyIp;
	}

	public void setProxyIp(String proxyIp) {
		this.proxyIp = proxyIp;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyUser() {
		return proxyUser;
	}

	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}

	public String getProxyPassword() {
		return proxyPassword;
	}

	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}


}
