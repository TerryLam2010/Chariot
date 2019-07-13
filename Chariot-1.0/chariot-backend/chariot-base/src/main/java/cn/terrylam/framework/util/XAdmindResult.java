package cn.terrylam.framework.util;

import java.util.List;

import cn.terrylam.framework.enums.XAdminCodeEnum;

/**
 * web专用返回
 * 
 * @author Orange
 * @date 2018/10/28
 */
public class XAdmindResult {

	private int code;

	private String msg;

	private List<?> data;

	private int count;

	private XAdmindResult(XAdminCodeEnum xAdminCodeEnum) {
		this.code = xAdminCodeEnum.getCode();
		this.msg = xAdminCodeEnum.getDesc();
	}

	private XAdmindResult(XAdminCodeEnum xAdminCodeEnum, List<?> data, int count) {
		this.code = xAdminCodeEnum.getCode();
		this.msg = xAdminCodeEnum.getDesc();
		this.data = data;
		this.count = count;
	}

	private XAdmindResult(XAdminCodeEnum xAdminCodeEnum, String msg) {
		this.code = xAdminCodeEnum.getCode();
		this.msg = msg;
	}

	private XAdmindResult(XAdminCodeEnum xAdminCodeEnum, String msg, List<?> data, int count) {
		this.code = xAdminCodeEnum.getCode();
		this.msg = msg;
		this.data = data;
		this.count = count;
	}

	public static XAdmindResult success() {
		return new XAdmindResult(XAdminCodeEnum.SUCCESS);
	}

	public static XAdmindResult error() {
		return new XAdmindResult(XAdminCodeEnum.ERROR);
	}


	public static XAdmindResult build(XAdminCodeEnum xAdminCodeEnum) {
		return new XAdmindResult(xAdminCodeEnum);
	}

	public static XAdmindResult build(XAdminCodeEnum xAdminCodeEnum, List<?> data, int count) {
		return new XAdmindResult(xAdminCodeEnum, data, count);
	}

	public static XAdmindResult build(XAdminCodeEnum xAdminCodeEnum, String msg) {
		return new XAdmindResult(xAdminCodeEnum, msg);
	}

	public static XAdmindResult build(XAdminCodeEnum xAdminCodeEnum, String msg, List<?> data, int count) {
		return new XAdmindResult(xAdminCodeEnum, msg, data, count);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
