package cn.terrylam.framework.enums;

public enum XAdminCodeEnum {

    SUCCESS(0, "成功"), ERROR(1, "错误");

    private int code;
    private String desc;

    XAdminCodeEnum(int code, String desc) {
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
