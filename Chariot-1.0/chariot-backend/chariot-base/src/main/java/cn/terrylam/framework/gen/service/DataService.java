package cn.terrylam.framework.gen.service;


import cn.terrylam.framework.gen.vo.Column;
import cn.terrylam.framework.gen.vo.Table;
import cn.terrylam.framework.util.JavaNameUtil;
import cn.terrylam.framework.util.MetadataUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author TerryLam 2019/7/31
 * @version 1.0
 * @description
 **/
public class DataService {

    private String prefix = "";

    public DataService() {
    }

    public DataService(String prefix) {
        this.prefix = prefix;
    }


    public Table tableData(String tableName) {
        Table table = new Table();
        String tablePK = MetadataUtil.getTablePK(tableName);
        String entityTableName = tableName.replace(prefix,"");
        ResultSet columnSet = null;
        try {
            columnSet = MetadataUtil.getTableColumnsInfo(tableName);
            List<Column> columns = new ArrayList<>();
            while (columnSet.next()) {
                Column column = new Column();
                column.setColumnName(columnSet.getString("COLUMN_NAME"));
                column.setComment(columnSet.getString("REMARKS"));
                column.setColumnType(columnSet.getString("TYPE_NAME"));
                column.setJavaClassType(JavaNameUtil.dbtype2JavaType(column.getColumnType()));
                column.setFieldName(JavaNameUtil.toCamel(column.getColumnName()));
                column.setUpperCaseColumnName(JavaNameUtil.toPascal(column.getColumnName()));
                if (column.getColumnName().equals(tablePK)) {
                    column.setPrimaryKey(true);
                }
                columns.add(column);
            }
            table.setColumns(columns);
            table.setTableName(tableName);
            table.setTableComment(MetadataUtil.getCommentByTableName(tableName));
            table.setClassName(JavaNameUtil.toPascal(entityTableName));
            table.setUpperCaseClassName(JavaNameUtil.toCamel(entityTableName));
            table.setPrimaryKey(JavaNameUtil.toPascal(tablePK));
            table.setUpperCasePrimaryKey(JavaNameUtil.toCamel(tablePK));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    public List<Table> getAllTable(){
        List<Table> tables = new ArrayList<>();
        String[] tableNames = MetadataUtil.getTableNames();
        for (String tableName : tableNames) {
            if(!tableName.contains(prefix)) {
                continue;
            }
            Table table = tableData(tableName);
            tables.add(table);
        }
        return tables;
    }

    public List<Table> getByName(List<String> tableNames){
        List<Table> tables = new ArrayList<>();
        for (String tableName : tableNames) {
            if(!tableName.contains(prefix)) {
                continue;
            }
            Table table = tableData(tableName);
            tables.add(table);
        }
        return tables;
    }

}
