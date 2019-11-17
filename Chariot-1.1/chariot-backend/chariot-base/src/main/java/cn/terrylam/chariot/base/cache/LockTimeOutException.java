package cn.terrylam.chariot.base.cache;

/**
 * @author TerryLam 2019/8/31
 * @version 1.0
 * @description
 **/
public class LockTimeOutException extends RuntimeException {

    private static final long serialVersionUID = 8343048459443313229L;

    public LockTimeOutException() {
        super("lock time out!");
    }

    public LockTimeOutException(String message) {
        super(message);
    }

    public LockTimeOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public LockTimeOutException(Throwable cause) {
        super(cause);
    }

    public LockTimeOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
