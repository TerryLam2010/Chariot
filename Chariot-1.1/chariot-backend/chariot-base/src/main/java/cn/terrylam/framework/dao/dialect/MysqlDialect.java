package cn.terrylam.framework.dao.dialect;


public class MysqlDialect implements Dialect {

    @Override
    public String buildPage(String sql, int pageNo, int pageSize) {
        if (pageSize == 0) {
            return sql;
        }

        StringBuffer sb = new StringBuffer(sql);
        int offset = (pageNo - 1) * pageSize;
        int limit = pageSize;
        sb.append(" limit");
        if (limit <= 0) {
            sb.append(" 0");
            return sb.toString();
        }
        if (offset > 0) {
            sb.append(" ").append(offset);
            sb.append(",");
        } else {
            sb.append(" ");
        }
        if (limit > 0) {
            sb.append(limit);
        }
        return sb.toString();
    }

    @Override
    public String getCountSql(String sql) {
        sql = sql.split("(ORDER BY|order by)")[0];
        int idx = sql.indexOf("FROM");
        if (idx == -1) {
            idx = sql.indexOf("from");
        }
        sql = "select count(*) from " + sql.substring(idx + 4);
        return sql;
    }

    @Override
    public String getSqlFlag() {
        return " ##repository";
    }

    @Override
    public int getDatabaseName() {
        return MYSQL;
    }

    private static final String delimited = "";

    @Override
    public String getDelimited() {
        return delimited;
    }
}
