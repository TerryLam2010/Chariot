package cn.terrylam.framework.exception;

import org.springframework.http.HttpStatus;

public class AccessForbiddenException extends Exception {

    private static final long serialVersionUID = 8343048459443313229L;

    private int errorCode = HttpStatus.FORBIDDEN.value();
    private String message = HttpStatus.FORBIDDEN.getReasonPhrase();

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public AccessForbiddenException() {
        super();
    }

    public AccessForbiddenException(String msg) {
        super(msg);
        setMessage(msg);
    }

    public AccessForbiddenException(int errorCode) {
        super();
        setErrorCode(errorCode);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}

