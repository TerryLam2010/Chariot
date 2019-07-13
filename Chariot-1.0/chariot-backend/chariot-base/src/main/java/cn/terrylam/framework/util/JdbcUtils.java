package cn.terrylam.framework.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 一般用于需要直接通过jdbc操作数据的业务场景 比如实现定时任务操作数据
 * 获取连接有两种方式：1、使用前需自行在classes目录下增加jdbc.properties并设置对应的值 2、通过指定的jndi名获取,自行指定
 * 数据操作事务自行硬编码控制
 *
 * @author yuanhuoqing
 */
public class JdbcUtils {
    private static Log log = LogFactory.getLog(JdbcUtils.class);

    /**
     * 创建Statement对象
     */
    private Statement stmt = null;

    /**
     * 创建PreparedStatement对象
     */
    private PreparedStatement preparedStatement = null;

    /**
     * 创建结果集对象
     */
    private ResultSet resultSet = null;

    private JdbcUtils() {

    }

    /**
     * 获得JdbcUtils实例
     *
     * @return JdbcUtils
     */
    public static JdbcUtils getJdbcUtilsInstance() {
        return threadLocal.get();
    }

    public static void removeJdbcUtils() {
        threadLocal.remove();
    }

    private static ThreadLocal<JdbcUtils> threadLocal = new ThreadLocal<JdbcUtils>() {
        @Override
        protected JdbcUtils initialValue() {
            return new JdbcUtils();
        }
    };

    /**
     * 创建一个数据库连接
     *
     * @return 一个数据库连接
     */
    public Connection getConnection() {
        Connection conn = null;
        try {
            DataSource source = SpringCtxUtils.getBean(DataSource.class);
            conn = source.getConnection();
        } catch (SQLException e) {
            log.error("#ERROR# :获取数连接对象失败，请检查！", e);
            e.printStackTrace();
        }

        return conn;
    }

    /**
     * @param conn 数据库连接 参数形式传入方便外围事务的控制
     * @param sql  sql语句
     * @return 返回查询结果集ResultSet对象
     */
    public ResultSet executeQuery(Connection conn, String sql) {
        ResultSet rs = null;
        try {
            // 创建执行SQL的对象
            Statement stmt = conn.createStatement();
            // 执行SQL，并获取返回结果
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            log.error("#ERROR# :执行sql语句出错，请检查！\n" + sql, e);
        }
        return rs;
    }

    /**
     * @param conn   数据库连接 参数形式传入方便外围事务的控制
     * @param sql    sql语句
     * @param params 参数值数组 严格按照顺序来传入
     * @return
     */
    public ResultSet executeQuery(Connection conn, String sql, Object... params) {
        try {
            // 调用sql
            preparedStatement = conn.prepareStatement(sql);
            // 参数赋值
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject(i + 1, params[i]);
                }
            }
            // 执行
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            log.error("#ERROR# :执行SQL语句出错，请检查！\n" + sql, e);
        }
        return resultSet;
    }

    /**
     * 在一个数据库连接上执行一个插入或更新的sql语句
     *
     * @param conn 数据库连接 参数形式传入方便外围事务的控制
     * @param sql  sql语句
     */
    public void executeSQL(Connection conn, String sql) {
        try {
            // 创建执行SQL的对象
            stmt = conn.createStatement();
            // 执行sql，并获取返回结果
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            log.error("#ERROR# :执行sql语句出错，请检查！\n" + sql, e);
        } finally {
            // 释放资源
            closeAll();
        }
    }

    /**
     * 在一个数据库连接上执行一个带参数的插入或更新的sql语句
     *
     * @param conn   数据库连接 参数形式传入方便外围事务的控制
     * @param sql    sql语句
     * @param params 参数值数组 严格按照顺序来传入
     * @return
     */
    public int executeUpdate(Connection conn, String sql, Object... params) {
        // 受影响的行数
        int affectedLine = 0;
        try {
            // 调用sql
            preparedStatement = conn.prepareStatement(sql);
            // 参数赋值
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject(i + 1, params[i]);
                }
            }
            // 执行
            affectedLine = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            log.error("#ERROR# :执行sql语句出错，请检查！\n" + sql, e);
        } finally {
            // 释放资源
            closeAll();
        }
        return affectedLine;
    }

    /**
     * 在一个数据库连接上执行一批插入或更新的sql语句
     *
     * @param conn    数据库连接
     * @param sqlList sql语句字符串集合
     */
    public void executeBatchSQL(Connection conn, List<String> sqlList) {
        try {
            // 创建执行SQL的对象
            Statement stmt = conn.createStatement();
            for (String sql : sqlList) {
                stmt.addBatch(sql);
            }
            // 执行SQL，并获取返回结果
            stmt.executeBatch();
        } catch (SQLException e) {
            log.error("#ERROR# :执行批量SQL语句出错，请检查！", e);
        } finally {
            // 释放资源
            closeAll();
        }
    }

    /**
     * @param conn   数据库连接 参数形式传入方便外围事务的控制
     * @param sql    sql语句
     * @param params 参数值数组 严格按照顺序来传入
     * @return List<Object>
     */
    public List<Object> excuteQuery(Connection conn, String sql, Object... params) {
        // 执行sql获得结果集
        ResultSet rs = executeQuery(conn, sql, params);
        // 创建ResultSetMetaData对象
        ResultSetMetaData rsmd = null;
        // 结果集列数
        int columnCount = 0;
        try {
            rsmd = rs.getMetaData();
            // 获得结果集列数
            columnCount = rsmd.getColumnCount();
        } catch (SQLException e) {
            log.error("#ERROR# :获得结果集列数出错，请检查！", e);
        }
        // 创建List
        List<Object> list = new ArrayList<Object>();
        try {
            // 将ResultSet的结果保存到List中
            while (rs.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    map.put(rsmd.getColumnLabel(i), rs.getObject(i));
                }
                list.add(map);
            }
        } catch (SQLException e) {
            log.error("#ERROR# :执行sql语句出错，请检查！", e);
        } finally {
            // 关闭所有资源
            closeAll();
        }
        return list;
    }

    public void closeConnection(Connection conn) {
        if (conn == null) {
            return;
        }
        try {
            if (!conn.isClosed()) {
                // 关闭数据库连接
                conn.close();
            }
        } catch (SQLException e) {
            log.error("#ERROR# :关闭数据库连接发生异常，请检查！", e);
        }
    }

    /**
     * 关闭除连接以外的所有资源
     */
    private void closeAll() {
        // 关闭结果集对象
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                log.error("#ERROR# :关闭数据库resultSet发生异常，请检查！", e);
            }
        }

        // 关闭PreparedStatement对象
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                log.error("#ERROR# :关闭数据库preparedStatement发生异常，请检查！", e);
            }
        }

        // 关闭PreparedStatement对象
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                log.error("#ERROR# :关闭数据库preparedStatement发生异常，请检查！", e);
            }
        }
    }

    /**
     * @param args
     */
    public void main(String[] args) {
    }

}
