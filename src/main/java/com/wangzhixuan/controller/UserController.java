package com.wangzhixuan.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wangzhixuan.common.Result;
import com.wangzhixuan.common.utils.PageInfo;
import com.wangzhixuan.model.Role;
import com.wangzhixuan.model.User;
import com.wangzhixuan.model.vo.UserVo;
import com.wangzhixuan.service.UserService;

/**
 * @description：用户管理
 * @author：zhixuan.wang @date：2015/10/1 14:51
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	/**
	 * 用户管理页
	 *
	 * @return
	 */
	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public String manager() {
		return "/admin/user";
	}

	/**
	 * 用户管理列表
	 *
	 * @param userVo
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/dataGrid", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo dataGrid(UserVo userVo, Integer page, Integer rows, String sort, String order) {
		PageInfo pageInfo = new PageInfo(page, rows);
		Map<String, Object> condition = Maps.newHashMap();

		if (StringUtils.isNoneBlank(userVo.getName())) {
			condition.put("name", userVo.getName());
		}
		if (userVo.getOrganizationId() != null) {
			condition.put("organizationId", userVo.getOrganizationId());
		}
		if (userVo.getCreatedateStart() != null) {
			condition.put("startTime", userVo.getCreatedateStart());
		}
		if (userVo.getCreatedateEnd() != null) {
			condition.put("endTime", userVo.getCreatedateEnd());
		}
		pageInfo.setCondition(condition);
		userService.findDataGrid(pageInfo);
		return pageInfo;
	}

	/**
	 * 添加用户页
	 *
	 * @return
	 */
	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public String addPage() {
		return "/admin/userAdd";
	}

	/**
	 * 添加用户
	 *
	 * @param userVo
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(UserVo userVo) {
		Result result = new Result();
		User u = userService.findUserByLoginName(userVo.getLoginname());
		if (u != null) {
			result.setMsg("用户名已存在!");
			return result;
		}
		try {
			userVo.setPassword(DigestUtils.md5Hex(userVo.getPassword()));
			userService.addUser(userVo);
			result.setSuccess(true);
			result.setMsg("添加成功");
			return result;
		} catch (RuntimeException e) {
			logger.error("添加用户失败：{}", e);
			result.setMsg(e.getMessage());
			return result;
		}
	}

	/**
	 * 编辑用户页
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(Long id, Model model) {
		UserVo userVo = userService.findUserVoById(id);
		List<Role> rolesList = userVo.getRolesList();
		List<Long> ids = Lists.newArrayList();
		for (Role role : rolesList) {
			ids.add(role.getId());
		}
		model.addAttribute("roleIds", ids);
		model.addAttribute("user", userVo);
		return "/admin/userEdit";
	}

	/**
	 * 编辑用户
	 *
	 * @param userVo
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Result edit(UserVo userVo) {
		Result result = new Result();
		User user = userService.findUserByLoginName(userVo.getLoginname());
		if (user != null && user.getId() != userVo.getId()) {
			result.setMsg("用户名已存在!");
			return result;
		}
		try {
			userVo.setPassword(DigestUtils.md5Hex(userVo.getPassword()));
			userService.updateUser(userVo);
			result.setSuccess(true);
			result.setMsg("修改成功！");
			return result;
		} catch (RuntimeException e) {
			logger.error("修改用户失败：{}", e);
			result.setMsg(e.getMessage());
			return result;
		}
	}

	/**
	 * 修改密码页
	 *
	 * @return
	 */
	@RequestMapping(value = "/editPwdPage", method = RequestMethod.GET)
	public String editPwdPage() {
		return "/admin/userEditPwd";
	}

	/**
	 * 修改密码
	 *
	 * @param request
	 * @param oldPwd
	 * @param pwd
	 * @return
	 */
	@RequestMapping("/editUserPwd")
	@ResponseBody
	public Result editUserPwd(String oldPwd, String pwd) {
		Result result = new Result();
//		if (!getCurrentUser().getPassword().equals(DigestUtils.md5Hex(oldPwd))) {
//			result.setMsg("老密码不正确!");
//			return result;
//		}

		try {
			userService.updateUserPwdById(getCurrentUserId(), DigestUtils.md5Hex(pwd));
			result.setSuccess(true);
			result.setMsg("密码修改成功！");
			return result;
		} catch (Exception e) {
			logger.error("修改密码失败：{}", e);
			result.setMsg(e.getMessage());
			return result;
		}
	}

	/**
	 * 删除用户
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Result delete(Long id) {
		try {
			userService.deleteUserById(id);
			return retResult("删除成功！", true);
		} catch (RuntimeException e) {
			logger.error("删除用户失败：{}", e);
			return retResult(e.getMessage(), false);
		}
	}
}
