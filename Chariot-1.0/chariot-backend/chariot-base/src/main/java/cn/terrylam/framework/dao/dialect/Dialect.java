package cn.terrylam.framework.dao.dialect;


public interface Dialect {
    public static final int MYSQL = 1;
    public static final int ORACLE = 2;

    public String buildPage(String sql, int pageNo, int pageSize);

    public String getCountSql(String sql);

    public String getSqlFlag();

    public String getDelimited();

    public int getDatabaseName();
}
