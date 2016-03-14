package com.wangzhixuan.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.wangzhixuan.common.utils.PageInfo;
import com.wangzhixuan.model.User;
import com.wangzhixuan.model.vo.UserVo;

public interface UserMapper extends AutoMapper<User> {

    /**
     * 用户列表
     *
     * @param pageInfo
     * @return
     */
    List findUserPageCondition(PageInfo pageInfo);

    /**
     * 统计用户
     *
     * @param pageInfo
     * @return
     */
    int findUserPageCount(PageInfo pageInfo);

    /**
     * 根据用户id查询用户带部门
     *
     * @param id
     * @return
     */
    UserVo findUserVoById(Long id);
}