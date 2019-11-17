package cn.terrylam.framework.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

/**
 * 专门用业务逻辑校验出错的异常
 *
 * @author liaoqiqi
 * @version 2013-12-2
 */
public class FieldValidateException extends RuntimeException {

    private static final long serialVersionUID = 8343048459443313229L;

    private int errorCode = HttpStatus.NOT_FOUND.value();

    private Map<String, String> fieldMsgs;

    private String message = null;

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getFieldMsgs() {
        return fieldMsgs;
    }

    public void setFieldMsgs(Map<String, String> fieldMsgs) {
        this.fieldMsgs = fieldMsgs;
    }

    public FieldValidateException() {
        super();
    }

    public FieldValidateException(String tips) {
        super(tips);
        setMessage(super.getMessage());
    }

    public FieldValidateException(Map<String, String> fieldMsgs) {
        super("some field is illegal !");
        setMessage(super.getMessage());
        this.fieldMsgs = fieldMsgs;
    }

    public FieldValidateException appendMsg(String field, String tips) {

        if (fieldMsgs == null) {
            synchronized (FieldValidateException.class) {
                if (fieldMsgs == null) {
                    fieldMsgs = new HashMap<String, String>();
                }
            }
        }
        fieldMsgs.put(field, tips);
        return this;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
