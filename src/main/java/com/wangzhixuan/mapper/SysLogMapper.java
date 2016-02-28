package com.wangzhixuan.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.wangzhixuan.common.utils.PageInfo;
import com.wangzhixuan.model.SysLog;

public interface SysLogMapper extends AutoMapper<SysLog> {
	int deleteByPrimaryKey(Long id);

	int insert(SysLog record);

	int insertSelective(SysLog record);

	SysLog selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(SysLog record);

	int updateByPrimaryKey(SysLog record);

	List findDataGrid(PageInfo pageInfo);

	int findDataGridCount(PageInfo pageInfo);
}