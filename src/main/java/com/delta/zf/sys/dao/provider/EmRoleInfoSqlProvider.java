package com.delta.zf.sys.dao.provider;

/**
 * Created by Administrator on 2021/7/17.
 */
public class EmRoleInfoSqlProvider {

    public String getRolesByUid(Integer uid){
        String sql = null;
        sql = "select r.* from em_role_info r\n" +
                "inner join em_user_role ur on r.role_code = ur.role_id and ur.states = '1'\n" +
                " where ur.user_id = "+uid+" order by r.role_code desc";
        return sql;
    }

    public String getRolesAll(){
        String sql = "select * from em_role_info";
        System.out.println("sql:"+sql);
        return sql;
    }
}
