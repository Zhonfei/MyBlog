package com.delta.zf.index;

/**
 * Created by Administrator on 2021/7/23.
 */
public class ConstSqlProvider {

    public static String HOME_PIE = "with s as \n" +
            "(select * from em_task where uname= '#uname#' and task_eg <> '1' and task_type <> '6')\n" +
            "select t.* from (\n" +
            "select s.task_over_state as name,\n" +
            " count(s.id)/(select count(*) from s) as y from s \n" +
            "group by s.task_over_state ) t\n" +
            "order by t.y desc";

    public static String HOME_3D = "with s as \n" +
            "(select * from em_task where uname= '#uname#' and task_eg <> '1' and task_type <> '6')\n" +
            "select t.y from (\n" +
            "select s.task_over_state as name,\n" +
            " count(s.id) as y from s\n" +
            "group by s.task_over_state ) t\n" +
            "order by t.y desc";

    public static String MENU_AUTH = "with s as (\n" +
            "select em_role_id,em_menu_code from em_role_menu rm \n" +
            "inner join em_menu_info mi on rm.em_menu_code = mi.menu_code\n" +
            " where rm.em_role_id = (select min(role_code) as rc from em_role_info where role_code > '#role_code#') and mi.menu_status = '1')\n" +
            "select mi.*,\n" +
            "s.em_role_id,\n" +
            " (select role_nameZH from em_role_info where role_code = s.em_role_id) as em_role_name,\n"+
            "case when em_menu_code is null then 0 else 1 end as flag\n" +
            "from em_menu_info mi \n" +
            "left join s on mi.menu_code = s.em_menu_code\n" +
            "where mi.menu_status = '1'\n" +
            "order by s.em_role_id desc,mi.menu_code asc";
}
