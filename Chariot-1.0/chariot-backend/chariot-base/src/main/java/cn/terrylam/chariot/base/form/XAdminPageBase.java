package cn.terrylam.chariot.base.form;

import java.io.Serializable;

/**
 * 所有的List请求的基类
 *
 * @author liaoqiqi
 * @version 2013-12-4
 */
public abstract class XAdminPageBase<T> implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7999737352570996142L;

	/**
	 * type
	 */
	protected Class<T> type;

	/**
	 * 
	 * @param type
	 */
	protected XAdminPageBase(Class<T> type) {
		this.type = type;
	}

	public static final String ORDER_TOKEN = ",";

	private int page = 1;

	private int limit = 25;

	// private int pageNum = 1;//dwz 默认页码,与pageNo同步

	// private int numPerPage = 25;//dwz 默认每页大小,与pageSize同步

	private String orderField = null;

	private String orderDirection = "desc";

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

// public int getPageNum() {
	// return pageNum;
	// }
	//
	// public void setPageNum( int pageNum ) {
	// this.pageNum = pageNum;
	// this.pageNo = pageNum;
	// }
	//
	// public int getNumPerPage() {
	// return numPerPage;
	// }
	//
	// public void setNumPerPage( int numPerPage ) {
	// this.numPerPage = numPerPage;
	// this.pageSize = numPerPage;
	// }

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		if (orderDirection.toLowerCase().equals("asc") || orderDirection.toLowerCase().equals("desc")) {
			this.orderDirection = orderDirection;
		}
	}

	public String getOrderSql() {

		return null;
	}

}
