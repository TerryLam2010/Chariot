package cn.terrylam.framework.gen.vo;

/**
 * @author TerryLam 2019/7/31
 * @version 1.0
 * @description
 **/
public class Column {

    private String columnName;

    private String fieldName;

    private String columnType;

    private String javaClassType;

    private String upperCaseColumnName;

    private String comment;

    private boolean isPrimaryKey;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getJavaClassType() {
        return javaClassType;
    }

    public void setJavaClassType(String javaClassType) {
        this.javaClassType = javaClassType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public String getUpperCaseColumnName() {
        return upperCaseColumnName;
    }

    public void setUpperCaseColumnName(String upperCaseColumnName) {
        this.upperCaseColumnName = upperCaseColumnName;
    }
}
