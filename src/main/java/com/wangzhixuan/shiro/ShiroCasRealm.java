package com.wangzhixuan.shiro;

import com.google.common.collect.Sets;
import com.wangzhixuan.model.User;
import com.wangzhixuan.service.RoleService;
import com.wangzhixuan.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasAuthenticationException;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description：
 * @author：zhixuan.wang
 * @date：2015/11/27 12:21
 */
public class ShiroCasRealm extends CasRealm {

    private static Logger LOGGER = LoggerFactory.getLogger(ShiroCasRealm.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        LOGGER.info("Shiro开始登录认证");
        CasToken casToken = (CasToken) token;
        if (token == null)
            return null;
        String ticket = (String) casToken.getCredentials();
        if (!StringUtils.hasText(ticket))
            return null;
        TicketValidator ticketValidator = ensureTicketValidator();
        try {
            // 验证ticket
            Assertion casAssertion = ticketValidator.validate(ticket, getCasService());
            // 获取用户信息
            AttributePrincipal casPrincipal = casAssertion.getPrincipal();
            String name = casPrincipal.getName();
            LOGGER.debug("Validate ticket : {} in CAS server : {} to retrieve user : {}", new Object[]{ticket, getCasServerUrlPrefix(), name});
            casToken.setRememberMe(true);
            // 查询登陆用户
            User user = userService.findUserByLoginName(name);
            //获取该用户角色集合
            List<Long> roleList = roleService.findRoleIdListByUserId(user.getId());
            ShiroUser shiroUser = new ShiroUser(user.getId(), user.getLoginname(), user.getName(), roleList);
            // 认证缓存信息
            return new SimpleAuthenticationInfo(shiroUser, ticket, getName());
        } catch (TicketValidationException e) {
            throw new CasAuthenticationException((new StringBuilder()).append("Unable to validate ticket [").append(ticket).append("]").toString(), e);
        }
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        List<Long> roleList = shiroUser.roleList;

        Set<String> urlSet = Sets.newHashSet();
        for (Long roleId : roleList) {
            List<Map<Long, String>> roleResourceList = roleService.findRoleResourceListByRoleId(roleId);
            if (roleResourceList != null) {
                for (Map<Long, String> map : roleResourceList) {
                    if (org.apache.commons.lang3.StringUtils.isNoneBlank(map.get("url"))) {
                        urlSet.add(map.get("url"));
                    }

                }
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(urlSet);
        return info;
    }
}
