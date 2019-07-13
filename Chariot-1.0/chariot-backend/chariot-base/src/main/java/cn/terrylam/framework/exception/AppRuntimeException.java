package cn.terrylam.framework.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class AppRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -6021077900819863433L;

    private String errorCode = null;
    private Object[] args = null;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public AppRuntimeException() {
        super();
    }

    public AppRuntimeException(String errorCode) {
        super();
        setErrorCode(errorCode);
    }

    public AppRuntimeException(Throwable cause) {
        super(cause);
    }

    public AppRuntimeException(String errorCode, Throwable cause) {
        super(cause);
        setErrorCode(errorCode);
    }

    public AppRuntimeException(String errorCode, String message, Throwable cause) {
        this(message, cause);
        setErrorCode(errorCode);
    }

    public AppRuntimeException(String errorCode, String message) {
        super(message);
        setErrorCode(errorCode);
    }

    public AppRuntimeException(String errorCode, String message, Object[] args) {
        super(message);
        setErrorCode(errorCode);
        setArgs(args);
    }

    public AppRuntimeException(String errorCode, Object[] args) {
        super();
        setErrorCode(errorCode);
        setArgs(args);
    }

    public AppRuntimeException(String errorCode, Throwable cause, Object[] args) {
        super(cause);
        setErrorCode(errorCode);
        setArgs(args);
    }

    public AppRuntimeException(String errorCode, String message, Throwable cause, Object[] args) {
        super(message, cause);
        setErrorCode(errorCode);
        setArgs(args);
    }

    public String getStackTraceString() {
        StringWriter sw = new StringWriter();
        printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }


    /**
     * @return 返回 args。
     */
    public Object[] getArgs() {
        return args;
    }

    /**
     * @param args 要设置的 args。
     */
    public void setArgs(Object[] args) {
        this.args = args;
    }


}
