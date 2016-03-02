package com.wangzhixuan.service;

import com.wangzhixuan.model.Resource;
import com.wangzhixuan.model.User;
import com.wangzhixuan.model.vo.Tree;

import java.util.List;

/**
 * @description：资源管理
 * @author：zhixuan.wang
 * @date：2015/10/1 14:51
 */
public interface ResourceService {


    /**
     * <p>
     * 获取指定 userId 资源列表
     * </p>
     * @param userId
     * 				用户ID
     * @return
     */
    List<Resource> getListByUserId(Long userId);
    
    
    /**
     * <p>
     * 获取指定 userId 权限 URL 列表
     * </p>
     * @param userId
     * 				用户ID
     * @return
     */
    List<String> getUrlListByUserId(Long userId);
	
    /**
     * 根据用户查询树形菜单列表
     *
     * @param currentUser
     * @return
     */
    List<Tree> findTree(User currentUser);

    /**
     * 查询所有资源
     *
     * @return
     */
    List<Resource> findResourceAll();

    /**
     * 添加资源
     *
     * @param resource
     */
    void addResource(Resource resource);

    /**
     * 查询二级数
     *
     * @return
     */
    List<Tree> findAllTree();

    /**
     * 查询三级数
     *
     * @return
     */
    List<Tree> findAllTrees();

    /**
     * 更新资源
     *
     * @param resource
     */
    void updateResource(Resource resource);

    /**
     * 根据id查询资源
     *
     * @param id
     * @return
     */
    Resource findResourceById(Long id);

    /**
     * 根据id删除资源
     *
     * @param id
     */
    void deleteResourceById(Long id);

}
