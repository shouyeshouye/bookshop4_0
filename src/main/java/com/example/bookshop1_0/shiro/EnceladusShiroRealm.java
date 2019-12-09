package com.example.bookshop1_0.shiro;

import com.example.bookshop1_0.dao.UserMapper;
import com.example.bookshop1_0.entity.SysRole;
import com.example.bookshop1_0.entity.SysUser;
import com.example.bookshop1_0.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

@Log4j2
public class EnceladusShiroRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        //获取session中的用户，以下3种都可以
        // User user=(User) principal.getPrimaryPrincipal();
        //String userName=(String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String username = (String) principal.getPrimaryPrincipal();
        //查询数据库
        SysUser user = userService.findUserByName(username);
        //logger.info("##################执行Shiro权限授权##################user info is：{}" + JSONObject.toJSONString(user));
//      Set<String> userPermissions = new HashSet<String>();
        Set<String> userRoles = new HashSet<String>();
        for (SysRole role : user.getRoles()) {
            userRoles.add(role.getRole());
            log.info(role.getRole()+"====================================================");
//            List<SysPermission> rolePermissions = role.getPermissions();
//            for (SysPermission permission : rolePermissions) {
//                userPermissions.add(permission.getPermName());
//            }
        }
        //角色名集合
        info.setRoles(userRoles);
        //权限名集合,将权限放入shiro中,
        // 这里可以把url，按钮，菜单，api等当做资源来进行权限控制，从而对用户进行权限控制
        //info.addStringPermissions(userPermissions);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        SysUser user = userMapper.findByUserName(username);

        if (user == null) {
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()), getName());
        return authenticationInfo;
    }

}
