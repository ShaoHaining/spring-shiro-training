package com.wangzhixuan.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.kisso.Token;
import com.baomidou.kisso.common.shiro.ShiroPermission;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.wangzhixuan.common.utils.Config;
import com.wangzhixuan.mapper.ResourceMapper;
import com.wangzhixuan.mapper.RoleMapper;
import com.wangzhixuan.mapper.UserRoleMapper;
import com.wangzhixuan.model.Resource;
import com.wangzhixuan.model.User;
import com.wangzhixuan.model.vo.Tree;
import com.wangzhixuan.service.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService, ShiroPermission {

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Tree> findTree(User user) {
        List<Tree> trees = Lists.newArrayList();
        // 超级管理
        if (user.getLoginName().equals("admin")) {
            List<Resource> resourceFather = resourceMapper.findResourceAllByTypeAndPidNull(Config.RESOURCE_MENU);
            if (resourceFather == null) {
                return null;
            }

            for (Resource resourceOne : resourceFather) {
                Tree treeOne = new Tree();

                treeOne.setId(resourceOne.getId());
                treeOne.setText(resourceOne.getName());
                treeOne.setIconCls(resourceOne.getIcon());
                treeOne.setAttributes(resourceOne.getUrl());
                List<Resource> resourceSon = resourceMapper.findResourceAllByTypeAndPid(Config.RESOURCE_MENU, resourceOne.getId());

                if (resourceSon != null) {
                    List<Tree> tree = Lists.newArrayList();
                    for (Resource resourceTwo : resourceSon) {
                        Tree treeTwo = new Tree();
                        treeTwo.setId(resourceTwo.getId());
                        treeTwo.setText(resourceTwo.getName());
                        treeTwo.setIconCls(resourceTwo.getIcon());
                        treeTwo.setAttributes(resourceTwo.getUrl());
                        tree.add(treeTwo);
                    }
                    treeOne.setChildren(tree);
                } else {
                    treeOne.setState("closed");
                }
                trees.add(treeOne);
            }
            return trees;
        }

        // 普通用户
        Set<Resource> resourceIdList = Sets.newHashSet();
        List<Long> roleIdList = userRoleMapper.findRoleIdListByUserId(user.getId());
        for (Long i : roleIdList) {
            List<Resource> resourceIdLists = roleMapper.findResourceIdListByRoleIdAndType(i);
            for (Resource resource: resourceIdLists) {
                resourceIdList.add(resource);
            }
        }
        for (Resource resource : resourceIdList) {
                if (resource != null && resource.getPid() == null) {
                    Tree treeOne = new Tree();
                    treeOne.setId(resource.getId());
                    treeOne.setText(resource.getName());
                    treeOne.setIconCls(resource.getIcon());
                    treeOne.setAttributes(resource.getUrl());
                    List<Tree> tree = Lists.newArrayList();
                    for (Resource resourceTwo : resourceIdList) {
                        if (resourceTwo.getPid() != null && resource.getId().longValue() == resourceTwo.getPid().longValue()) {
                            Tree treeTwo = new Tree();
                            treeTwo.setId(resourceTwo.getId());
                            treeTwo.setText(resourceTwo.getName());
                            treeTwo.setIconCls(resourceTwo.getIcon());
                            treeTwo.setAttributes(resourceTwo.getUrl());
                            tree.add(treeTwo);
                        }
                    }
                    treeOne.setChildren(tree);
                    trees.add(treeOne);
                }
        }
        return trees;
    }

    @Override
    public List<Resource> findResourceAll() {
        return resourceMapper.findResourceAll();
    }

    @Override
    public void addResource(Resource resource) {
        resourceMapper.insert(resource);
    }

    @Override
    public List<Tree> findAllTree() {
        List<Tree> trees = Lists.newArrayList();
        // 查询所有的一级树
        List<Resource> resources = resourceMapper.findResourceAllByTypeAndPidNull(Config.RESOURCE_MENU);
        if (resources == null) {
            return null;
        }
        for (Resource resourceOne : resources) {
            Tree treeOne = new Tree();

            treeOne.setId(resourceOne.getId());
            treeOne.setText(resourceOne.getName());
            treeOne.setIconCls(resourceOne.getIcon());
            treeOne.setAttributes(resourceOne.getUrl());
            // 查询所有一级树下的菜单
            List<Resource> resourceSon = resourceMapper.findResourceAllByTypeAndPid(Config.RESOURCE_MENU, resourceOne.getId());

            if (resourceSon != null) {
                List<Tree> tree = Lists.newArrayList();
                for (Resource resourceTwo : resourceSon) {
                    Tree treeTwo = new Tree();
                    treeTwo.setId(resourceTwo.getId());
                    treeTwo.setText(resourceTwo.getName());
                    treeTwo.setIconCls(resourceTwo.getIcon());
                    treeTwo.setAttributes(resourceTwo.getUrl());
                    tree.add(treeTwo);
                }
                treeOne.setChildren(tree);
            } else {
                treeOne.setState("closed");
            }
            trees.add(treeOne);
        }
        return trees;
    }

    @Override
    public List<Tree> findAllTrees() {
        List<Tree> treeOneList = Lists.newArrayList();

        // 查询所有的一级树
        List<Resource> resources = resourceMapper.findResourceAllByTypeAndPidNull(Config.RESOURCE_MENU);
        if (resources == null) {
            return null;
        }

        for (Resource resourceOne : resources) {
            Tree treeOne = new Tree();

            treeOne.setId(resourceOne.getId());
            treeOne.setText(resourceOne.getName());
            treeOne.setIconCls(resourceOne.getIcon());
            treeOne.setAttributes(resourceOne.getUrl());

            List<Resource> resourceSon = resourceMapper.findResourceAllByTypeAndPid(Config.RESOURCE_MENU, resourceOne.getId());

            if (resourceSon == null) {
                treeOne.setState("closed");
            } else {
                List<Tree> treeTwoList = Lists.newArrayList();

                for (Resource resourceTwo : resourceSon) {
                    Tree treeTwo = new Tree();

                    treeTwo.setId(resourceTwo.getId());
                    treeTwo.setText(resourceTwo.getName());
                    treeTwo.setIconCls(resourceTwo.getIcon());
                    treeTwo.setAttributes(resourceTwo.getUrl());

                    /***************************************************/
                    List<Resource> resourceSons = resourceMapper.findResourceAllByTypeAndPid(Config.RESOURCE_BUTTON, resourceTwo.getId());

                    if (resourceSons == null) {
                        treeTwo.setState("closed");
                    } else {
                        List<Tree> treeThreeList = Lists.newArrayList();

                        for (Resource resourceThree : resourceSons) {
                            Tree treeThree = new Tree();

                            treeThree.setId(resourceThree.getId());
                            treeThree.setText(resourceThree.getName());
                            treeThree.setIconCls(resourceThree.getIcon());
                            treeThree.setAttributes(resourceThree.getUrl());

                            treeThreeList.add(treeThree);
                        }
                        treeTwo.setChildren(treeThreeList);
                    }
                    /***************************************************/

                    treeTwoList.add(treeTwo);
                }
                treeOne.setChildren(treeTwoList);
            }

            treeOneList.add(treeOne);
        }
        return treeOneList;
    }

    @Override
    public void updateResource(Resource resource) {
        int update = resourceMapper.updateById(resource);
        if (update != 1) {
            throw new RuntimeException("更新失败");
        }
    }

    @Override
    public Resource findResourceById(Long id) {
        return resourceMapper.selectById(id);
    }

    @Override
    public void deleteResourceById(Long id) {
        int delete = resourceMapper.deleteById(id);
        if (delete != 1) {
            throw new RuntimeException("删除失败");
        }
    }

	@Override
	public List<Resource> getListByUserId( Long userId ) {
		return resourceMapper.selectByUserId(userId);
	}

	@Override
	public List<String> getUrlListByUserId( Long userId ) {
		List<String> urlList = new ArrayList<String>();
		List<Resource> resList = getListByUserId(userId);
		if ( resList == null ) {
			return urlList;
		}
		for ( Resource res : resList ) {
			if ( StringUtils.isNoneBlank(res.getUrl()) ) {
				urlList.add(res.getUrl());
			}
		}
		return urlList;
	}

	@Override
	public List<String> getPermissions( Token token ) {
		if ( token == null ) {
			return null;
		}
		return getUrlListByUserId(token.getId());
	}

}
