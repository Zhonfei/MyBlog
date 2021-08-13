package com.delta.zf.sys.dao;

import com.delta.zf.sys.bean.EmRoleInfo;
import com.delta.zf.sys.dao.provider.EmRoleInfoSqlProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2021/7/17.
 */
@Repository
@Scope("prototype")
public interface EmRoleInfoDao {

    @SelectProvider(type = EmRoleInfoSqlProvider.class,method = "getRolesByUid")
    List<EmRoleInfo> getRolesByUid(Integer uid);

    @SelectProvider(type = EmRoleInfoSqlProvider.class,method = "getRolesAll")
    List<EmRoleInfo> getRolesAll();
}
