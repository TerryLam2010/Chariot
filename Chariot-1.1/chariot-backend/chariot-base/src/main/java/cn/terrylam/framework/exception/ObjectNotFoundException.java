package cn.terrylam.framework.exception;

import org.springframework.http.HttpStatus;

public class ObjectNotFoundException extends Exception {

    private static final long serialVersionUID = 8343048459443313229L;

    private int errorCode = HttpStatus.NOT_FOUND.value();

    private String message = null;

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ObjectNotFoundException() {
        super();
    }

    public ObjectNotFoundException(String msg) {
        super(msg);
        setMessage(msg);
    }

    public ObjectNotFoundException(Class<?> type) {
        super(type.getSimpleName() + " not found!");
        setMessage(super.getMessage());
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
