package cn.terrylam.framework.exception;

import org.springframework.http.HttpStatus;

public class EmpytCollectionException extends Exception {

	private static final long serialVersionUID = 8343048459443313229L;

	private int errorCode = HttpStatus.NOT_FOUND.value();

	private String message = null;

	public int getErrorCode() {
		return errorCode;
	}
	public String getMessage() {
		return message;
	}

	public EmpytCollectionException() {
		super();
	}

	public EmpytCollectionException(Class<?> type) {
		super(type.getSimpleName() + " collection is null!");
		setMessage( super.getMessage() );
	}
	public void setErrorCode( int errorCode ) {
		this.errorCode = errorCode;
	}
	public void setMessage( String message ) {
		this.message = message;
	}

}
