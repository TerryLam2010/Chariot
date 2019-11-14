package cn.terrylam.framework.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 负责连接数据库、获取表的元信息、列的元信息
 */
public class MetadataUtil {

    /*2. DatabaseMetaData接口常用的方法：获取数据库元数据
(1) ResultSet getTables(String catalog,String schemaPattern,String tableNamePattern,String[] types);   //获取表信息
(2) ResultSet getPrimaryKeys(String catalog,String schema,String table);  //获取表主键信息
(3) ResultSet getIndexInfo(String catalog,String schema,String table,boolean unique,boolean approximate);  //获取表索引信息
(4) ResultSet getColumns(String catalog,String schemaPattern,String tableNamePattern,String columnNamePattern); //获取表列信息*/
    private static DatabaseMetaData meta;

    private static JdbcTemplate jdbcTemplate;



    /**
     * 连接数据库获取数据库元数据
     */
    public static void openConnection() throws SQLException {
        DataSource dataSource = SpringCtxUtils.getBean(DataSource.class);
        jdbcTemplate = SpringCtxUtils.getBean(JdbcTemplate.class);
        Connection connection = DataSourceUtils.getConnection(dataSource);
        meta = connection.getMetaData();
    }

    /**
     * 获取表的注释
     * @param
     * @return
     */
    public static String getCommentByTableName(String tableName) throws SQLException {
        SqlRowSet rs = jdbcTemplate.queryForRowSet("SHOW CREATE TABLE " + tableName);
        String comment = null;
        if (rs != null && rs.next()) {
            comment=rs.getString(2);
            //判断字符，只获取注解部分
            int index = comment.lastIndexOf("=");
            comment = comment.substring(index+1);
        }
        return comment;
    }

    /**
     * 获取所有的表名
     * @return
     */
    public static String[] getTableNames(){
        ResultSet rs=null;
        List<String> nameList = new ArrayList<>();
        try{
            rs=meta.getTables(null,
                    null,
                    null,
                    new String[]{"TABLE"});
            while (rs.next()){
                String tName =rs.getString("TABLE_NAME");
                nameList.add(tName);//将取出来的表名放入集合中
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return (String[])nameList.toArray(new String[]{});
    }

    /**
     * 获取某个表中所有的列信息
     * @param tableName
     * @return
     * @throws Exception
     */
    public static ResultSet getTableColumnsInfo(String tableName)throws Exception{
        ResultSet rs= meta.getColumns(null,
                "%",tableName,"%");
        return rs;
    }


    /**
     * @author TerryLam 11:19 2019/2/18
     * @description 获取主键
     * @param
     * @param tableName
     * @return java.lang.String
     **/
    public static String getTablePK(String tableName) {
        String pkStr = "";
        ResultSet rs;
        try {
            rs = meta.getPrimaryKeys(null, null, tableName);
            if (null == rs) {
                return pkStr;
            }
            while (rs.next()) {
                pkStr = rs.getString("COLUMN_NAME");
            }
            // final
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pkStr;
    }
}