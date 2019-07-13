package cn.terrylam.framework.dao.dialect;


public class OracleDialect implements Dialect {

    @Override
    public String buildPage(String sql, int pageNo, int pageSize) {
        if (pageSize == 0) {
            return sql;
        }

        int offset = (pageNo - 1) * pageSize;
        int limit = pageSize;
        StringBuilder sb = new StringBuilder();
        sb.append("select b.* from ( ");
        sb.append("select a.*, rownum as r__n from ( ");

        sb.append(sql);
        sb.append(" ) a ");
        sb.append(" ) b where ");

        if (offset > 0) {
            sb.append(" b.r__n > ");
            sb.append(offset);
            if (limit > 0) {
                sb.append(" and b.r__n <= ");
                sb.append(offset + limit);
            }
        } else {
            sb.append(" b.r__n <= ");
            sb.append(limit);
        }

        return sb.toString();
    }

    @Override
    public String getCountSql(String sql) {
        return "select count(*) from (" + sql + ")";
    }

    @Override
    public String getSqlFlag() {
        return " --repository";
    }

    @Override
    public int getDatabaseName() {
        return ORACLE;
    }

    private static final String delimited = "";

    @Override
    public String getDelimited() {
        return delimited;
    }
}
