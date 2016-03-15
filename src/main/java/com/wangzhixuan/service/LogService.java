package com.wangzhixuan.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.wangzhixuan.model.SysLog;

/**
 * @description：操作日志
 * @author：zhixuan.wang
 * @date：2015/10/30 10:35
 */
public interface LogService {

    void insertLog(SysLog sysLog);

    Page<SysLog> findDataGrid(Page<SysLog> sysLogPage);
}
