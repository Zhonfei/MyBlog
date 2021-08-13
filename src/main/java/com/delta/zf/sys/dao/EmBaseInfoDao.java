package com.delta.zf.sys.dao;


import com.delta.zf.sys.bean.EmBaseInfo;
import com.delta.zf.sys.dao.provider.EmBaseInfoSqlProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2021/7/17.
 */
@Repository
public interface EmBaseInfoDao{

    @SelectProvider(type = EmBaseInfoSqlProvider.class,method = "loadUserByUsername")
    EmBaseInfo loadUserByUsername(String s);
}
