package cn.terrylam.framework.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class IpUtils {
	public static String getIp(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String forwarded = request.getHeader("X-Forwarded-For");
        String realIp = request.getHeader("X-Real-IP");

        String ip = null;
        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;
            } else {
                ip = remoteAddr + "/" + forwarded;
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                ip = realIp + "/" + forwarded.replaceAll(", " + realIp, "");
            }
        }
        return ip;
    }

	private static String localIp;
	static {
		localIp = getLocalAddress();
	}
	public static String getIp() {
		return localIp;
	}

    static String getLocalAddress() {
        String result = null;
        Enumeration enu = null;
        try {
            enu = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            return "127.0.0.1";
        }
        while (enu.hasMoreElements()) {
            NetworkInterface ni = (NetworkInterface) (enu.nextElement());
            Enumeration ias = ni.getInetAddresses();
            while (ias.hasMoreElements()) {
                InetAddress i = (InetAddress) (ias.nextElement());
                String addr = i.getHostAddress();
				if (addr.startsWith("192.")) {
                    return addr;
                }
            }
        }

        return result;
    }

}
