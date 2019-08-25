package com.jason.db;

import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * author: zhangkai
 * date: 2019-08-22
 * description:
 */
public class MarioDb {

    private static Sql2o sql2o = null;

    private MarioDb() {

    }

    /**
     * 初始化数据库配置
     * @param url
     * @param user
     * @param pass
     */
    public static void init(String url, String user, String pass) {
        sql2o = new Sql2o(url, user, pass);
    }

    public static void init(DataSource dataSource) {
        sql2o = new Sql2o(dataSource);
    }

    public static <T> T get(String sql, Class<T> clazz) {
        return get(sql, clazz, null);
    }

    public static <T> T get(String sql, Class<T> clazz, Map<String, Object> params) {
        Connection con = sql2o.open();
        Query query = con.createQuery(sql);
        executeQuery(query, params);
        T t = query.executeAndFetchFirst(clazz);
        con.close();
        return t;
    }

    public static <T> List<T> getList(String sql, Class<T> clazz, Map<String, Object> params) {
        Connection con = sql2o.open();
        Query query = con.createQuery(sql);
        executeQuery(query, params);
        List<T> list = query.executeAndFetch(clazz);
        con.close();
        return list;
    }


    private static void executeQuery(Query query, Map<String, Object> params) {
        if (params != null && params.size() > 0) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                query.addParameter(key, params.get(key));
            }
        }
    }

}
