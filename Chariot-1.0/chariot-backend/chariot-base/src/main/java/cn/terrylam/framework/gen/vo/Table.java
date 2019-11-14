package cn.terrylam.framework.gen.vo;

import java.util.List;

/**
 * @author TerryLam 2019/7/31
 * @version 1.0
 * @description
 **/
public class Table {

    private String tableName;

    private String tableComment;

    private List<Column> columns;

    private String className;

    private String upperCaseClassName;

    private String primaryKey;

    private String upperCasePrimaryKey;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getUpperCaseClassName() {
        return upperCaseClassName;
    }

    public void setUpperCaseClassName(String upperCaseClassName) {
        this.upperCaseClassName = upperCaseClassName;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getUpperCasePrimaryKey() {
        return upperCasePrimaryKey;
    }

    public void setUpperCasePrimaryKey(String upperCasePrimaryKey) {
        this.upperCasePrimaryKey = upperCasePrimaryKey;
    }
}
