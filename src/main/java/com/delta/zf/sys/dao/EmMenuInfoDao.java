package com.delta.zf.sys.dao;

import com.delta.zf.sys.bean.EmMenuInfo;
import com.delta.zf.sys.dao.provider.EmMenuInfoSqlProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2021/7/22.
 */
@Repository
public interface EmMenuInfoDao {

    @SelectProvider(type = EmMenuInfoSqlProvider.class,method = "getMenuByRole")
    List<EmMenuInfo> getMenuByRole(String roleCode);

    @Insert("insert into em_menu_info(menu_code,menu_pcode,menu_name,menu_url,menu_ico,menu_status) " +
            " values(#{menuCode},#{menuPcode},#{menuName},#{menuUrl},#{menuIco},'1')")
    Integer addNewMenu(EmMenuInfo emMenuInfo);

    @Select("select * from em_menu_info where menu_status= '1'")
    List<EmMenuInfo> getMenuAll();

    @Select("select * from em_menu_info where id = #{id} and menu_status= '1'")
    List<EmMenuInfo> getMenuById(Integer id);

    @UpdateProvider(type = EmMenuInfoSqlProvider.class,method = "updateMenuInfo")
    Integer updateMenuInfo(EmMenuInfo emMenuInfo);
}
