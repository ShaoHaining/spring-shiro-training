package com.wangzhixuan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wangzhixuan.mapper.SysLogMapper;
import com.wangzhixuan.model.SysLog;
import com.wangzhixuan.service.LogService;

/**
 * @description：
 * @author：zhixuan.wang
 * @date：2015/10/30 10:40
 */
@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private SysLogMapper sysLogMapper;


	@Override
	public void insertLog( SysLog sysLog ) {
		sysLogMapper.insert(sysLog);
	}


//	@Override
//	public void findDataGrid( PageInfo pageInfo ) {
//		pageInfo.setRows(sysLogMapper.findDataGrid(pageInfo));
//		pageInfo.setTotal(sysLogMapper.findDataGridCount(pageInfo));
//	}
	@Override
	public Page<SysLog> findDataGrid( Page<SysLog> sysLogPage ) {
		EntityWrapper<SysLog> ew = new EntityWrapper<SysLog>(null, "crTime DESC");
		List<SysLog> sysLogList = sysLogMapper.selectList(sysLogPage, ew);
		sysLogPage.setRecords(sysLogList);
		return sysLogPage;
	}
}
