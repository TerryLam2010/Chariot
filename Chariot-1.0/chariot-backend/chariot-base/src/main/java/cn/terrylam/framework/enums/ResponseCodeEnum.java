package cn.terrylam.framework.enums;

public enum ResponseCodeEnum {

    SUCCESS(200, "成功"),
    ERROR(500, "服务器错误"),
    BAD_REQUEST(400, "参数错误"),
    SC_NOT_FOUND(404, "资源不存在"),
    FORBIDDEN(403, "禁止访问");

    private int code;
    private String desc;

    ResponseCodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
