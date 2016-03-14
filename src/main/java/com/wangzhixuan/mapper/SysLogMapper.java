package com.wangzhixuan.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.wangzhixuan.common.utils.PageInfo;
import com.wangzhixuan.model.SysLog;

public interface SysLogMapper extends AutoMapper<SysLog> {

	List findDataGrid( PageInfo pageInfo );


	int findDataGridCount( PageInfo pageInfo );
}