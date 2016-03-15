package com.wangzhixuan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.plugins.Page;
import com.wangzhixuan.common.utils.PageInfo;
import com.wangzhixuan.model.SysLog;
import com.wangzhixuan.service.LogService;

/**
 * @description：日志管理
 * @author：zhixuan.wang
 * @date：2015/10/30 18:06
 */
@Controller
@RequestMapping("/sysLog")
public class SysLogController extends BaseController {

	@Autowired
	private LogService logService;


	@Permission(action = Action.Skip)
	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public String manager() {
		return "/admin/syslog";
	}


	@ResponseBody
	@Permission(action = Action.Skip)
	@RequestMapping(value = "/dataGrid")
	public PageInfo dataGrid() {
		Page<SysLog> sysLogPage = getPage();
		sysLogPage = logService.findDataGrid(sysLogPage);
		PageInfo pageInfo = new PageInfo(sysLogPage.getCurrent(), sysLogPage.getPages());
		pageInfo.setRows(sysLogPage.getRecords());
		return pageInfo;
	}
}
