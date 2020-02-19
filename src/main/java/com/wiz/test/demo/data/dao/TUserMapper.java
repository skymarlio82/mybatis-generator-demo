package com.wiz.test.demo.data.dao;

import com.wiz.test.demo.data.entity.TUser;
import com.wiz.test.demo.data.entity.TUserExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TUserMapper {
    long countByExample(TUserExample example);

    int deleteByExample(TUserExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(TUser record);

    int insertSelective(TUser record);

    TUser selectOneByExample(TUserExample example);

    TUser selectOneByExampleSelective(@Param("example") TUserExample example, @Param("selective") TUser.Column ... selective);

    List<TUser> selectByExampleSelective(@Param("example") TUserExample example, @Param("selective") TUser.Column ... selective);

    List<TUser> selectByExample(TUserExample example);

    TUser selectByPrimaryKeySelective(@Param("userId") Integer userId, @Param("selective") TUser.Column ... selective);

    TUser selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") TUser record, @Param("example") TUserExample example);

    int updateByExample(@Param("record") TUser record, @Param("example") TUserExample example);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);
}