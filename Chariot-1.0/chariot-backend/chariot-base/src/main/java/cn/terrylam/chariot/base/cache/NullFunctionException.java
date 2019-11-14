package cn.terrylam.chariot.base.cache;

/**
 * @author TerryLam 2019/8/31
 * @version 1.0
 * @description
 **/
public class NullFunctionException extends RuntimeException {

    private static final long serialVersionUID = 8343048459443313229L;

    public NullFunctionException() {
        super("null function exception");
    }

    public NullFunctionException(String message) {
        super(message);
    }

    public NullFunctionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullFunctionException(Throwable cause) {
        super(cause);
    }

    public NullFunctionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
