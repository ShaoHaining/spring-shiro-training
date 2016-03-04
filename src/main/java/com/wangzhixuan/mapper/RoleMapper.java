package com.wangzhixuan.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.wangzhixuan.common.utils.PageInfo;
import com.wangzhixuan.model.Resource;
import com.wangzhixuan.model.Role;

public interface RoleMapper extends AutoMapper<Role> {

    /**
     * 查询角色列表
     *
     * @param pageInfo
     * @return
     */
    List findRolePageCondition(PageInfo pageInfo);

    /**
     * 角色统计
     *
     * @param pageInfo
     * @return
     */
    int findRolePageCount(PageInfo pageInfo);

    /**
     * 角色列表
     *
     * @return
     */
    List<Role> findRoleAll();

    /**
     * 根据角色查询资源id列表
     *
     * @param id
     * @return
     */
    List<Long> findResourceIdListByRoleId(Long id);

    /**
     * 根据角色id查询资源角色关联id列表
     *
     * @param
     * @return
     */
    List<Long> findRoleResourceIdListByRoleId(Long id);

    /**
     * 根据角色id查询资源id、链接列表
     *
     * @param id
     * @return
     */
    List<Map<Long, String>> findRoleResourceListByRoleId(Long id);

    /**
     * 查询角色下的菜单列表
     *
     * @param i
     * @return
     */
    List<Resource> findResourceIdListByRoleIdAndType(Long i);

}