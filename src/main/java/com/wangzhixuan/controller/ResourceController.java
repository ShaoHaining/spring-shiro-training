package com.wangzhixuan.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Permission;
import com.wangzhixuan.common.Result;
import com.wangzhixuan.model.Resource;
import com.wangzhixuan.model.vo.Tree;
import com.wangzhixuan.service.ResourceService;

/**
 * @description：资源管理
 * @author：zhixuan.wang
 * @date：2015/10/1 14:51
 */
@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseController {

    @Autowired
    private ResourceService resourceService;

    /**
     * 菜单树
     *
     * @return
     */
    @Permission(action = Action.Skip)
    @RequestMapping(value = "/tree", method = RequestMethod.POST)
    @ResponseBody
    public List<Tree> tree(HttpServletRequest request) {
        return resourceService.findTree(getCurrentUser());
    }

    /**
     * 资源管理页
     *
     * @return
     */
    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String manager() {
        return "admin/resource";
    }

    /**
     * 资源管理列表
     *
     * @return
     */
    @Permission(action = Action.Skip)
    @ResponseBody
    @RequestMapping(value = "/treeGrid", method = RequestMethod.POST)
    public List<Resource> treeGrid() {
        List<Resource> treeGrid = resourceService.findResourceAll();
        return treeGrid;
    }

    /**
     * 添加资源页
     *
     * @return
     */
    @Permission(action = Action.Skip)
    @RequestMapping("/addPage")
    public String addPage() {
        return "/admin/resourceAdd";
    }

    /**
     * 添加资源
     *
     * @param resource
     * @return
     */
    @Permission(action = Action.Skip)
    @ResponseBody
    @RequestMapping("/add")
    public Result add(Resource resource) {
        Result result = new Result();
        try {
            resourceService.addResource(resource);
            result.setSuccess(true);
            result.setMsg("添加成功！");
            return result;
        } catch (RuntimeException e) {
            logger.error("添加资源失败：{}", e);
            result.setMsg(e.getMessage());
            return result;
        }
    }

    /**
     * 二级资源树
     *
     * @return
     */
    @Permission(action = Action.Skip)
    @ResponseBody
    @RequestMapping("/allTree")
    public List<Tree> allTree() {
        return resourceService.findAllTree();
    }

    /**
     * 三级资源树
     *
     * @return
     */
    @Permission(action = Action.Skip)
    @ResponseBody
    @RequestMapping(value = "/allTrees", method = RequestMethod.POST)
    public List<Tree> allTrees() {
        return resourceService.findAllTrees();
    }

    /**
     * 编辑资源页
     *
     * @param request
     * @param id
     * @return
     */
    @Permission(action = Action.Skip)
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, Long id) {
        Resource resource = resourceService.findResourceById(id);
        request.setAttribute("resource", resource);
        return "/admin/resourceEdit";
    }

    /**
     * 编辑资源
     *
     * @param resource
     * @return
     */
    @Permission(action = Action.Skip)
    @ResponseBody
    @RequestMapping("/edit")
    public Result edit(Resource resource) {
        Result result = new Result();
        try {
            resourceService.updateResource(resource);
            result.setSuccess(true);
            result.setMsg("编辑成功！");
            return result;
        } catch (RuntimeException e) {
            logger.error("编辑资源失败：{}", e);
            result.setMsg(e.getMessage());
            return result;
        }
    }

    /**
     * 删除资源
     *
     * @param id
     * @return
     */
    @Permission(action = Action.Skip)
    @ResponseBody
    @RequestMapping("/delete")
    public Result delete(Long id) {
        Result result = new Result();
        try {
            resourceService.deleteResourceById(id);
            result.setMsg("删除成功！");
            result.setSuccess(true);
            return result;
        } catch (RuntimeException e) {
            logger.error("删除资源失败：{}", e);
            result.setMsg(e.getMessage());
            return result;
        }
    }

}
