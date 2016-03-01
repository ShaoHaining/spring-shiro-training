package com.wangzhixuan.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

@TableName(value = "syslog")
public class SysLog implements Serializable {

	@TableField(exist = false)
    private static final long serialVersionUID = -8690056878905494181L;

	@TableId
    private Long id;

    @TableField(value = "login_name")
    private String loginName;

    @TableField(value = "role_name")
    private String roleName;

    @TableField(value = "opt_content")
    private String optContent;

    @TableField(value = "client_ip")
    private String clientIp;

    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getOptContent() {
        return optContent;
    }

    public void setOptContent(String optContent) {
        this.optContent = optContent == null ? null : optContent.trim();
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp == null ? null : clientIp.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SysLog{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", roleName='" + roleName + '\'' +
                ", optContent='" + optContent + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}