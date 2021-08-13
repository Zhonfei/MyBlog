package com.delta.zf.sys.dao.provider;

/**
 * Created by Administrator on 2021/7/17.
 */
public class EmBaseInfoSqlProvider {

    public String loadUserByUsername(String s){
        String sql = null;
        sql = "select * from em_base_info where em_name = '"+s+"'";
        return sql;
    }
}
