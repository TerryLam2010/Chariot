package cn.terrylam.chariot.base.form;

import java.io.Serializable;

/**
 * 所有的List请求的基类
 *
 * @author liaoqiqi
 * @version 2013-12-4
 */
public abstract class RequestPageBase<T> implements Serializable {

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
	protected RequestPageBase(Class<T> type) {
		this.type = type;
	}
	
	public static final String ORDER_TOKEN = ",";

	private int pageNo = 1;

	private int pageSize = 25;

	// private int pageNum = 1;//dwz 默认页码,与pageNo同步

	// private int numPerPage = 25;//dwz 默认每页大小,与pageSize同步

	private String orderField = null;

	private String orderDirection = "desc";

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo( int pageNo ) {
		this.pageNo = pageNo;
		// this.pageNum = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize( int pageSize ) {
		this.pageSize = pageSize;
		// this.numPerPage = pageSize;
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

	public void setOrderField( String orderField ) {
		this.orderField = orderField;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection( String orderDirection ) {
		if( orderDirection.toLowerCase().equals( "asc" ) || orderDirection.toLowerCase().equals( "desc" ) ) {
			this.orderDirection = orderDirection;
		}
	}

	public String getOrderSql() {
		/*GeliOrm geliOrm = SpringCtxUtils.getBean( GeliOrm.class );
		String orderColumns = "";
		String comma = " , ";
		if( StringUtils.isNotBlank( orderField ) ) {
			String[] orderArray = orderField.split( "," );
			for( String field : orderArray ) {
				String column = geliOrm.getColumnByField( type, field );
				if( StringUtils.isNotBlank( column ) ) {

					orderColumns = orderColumns + column + comma;
				}
			}
		}
		if( orderColumns != null && orderColumns.endsWith( comma ) ) {
			orderColumns = orderColumns.substring( 0, orderColumns.length() - comma.length() );
			return orderColumns + " " + orderDirection;
		}*/
		return null;
	}

}
