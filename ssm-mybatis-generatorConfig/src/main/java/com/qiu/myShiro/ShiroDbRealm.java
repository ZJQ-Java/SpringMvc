package com.qiu.myShiro;

import com.qiu.controller.LoginController;
import com.qiu.dao.pojo.User;
import com.qiu.server.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ShiroDbRealm extends AuthorizingRealm {
    private static  final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private             UserService userService;
    public static final String      SESSION_USER_KEY = "SESSION_USER_KEY";

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        /*User user = (User) SecurityUtils.getSubject().getSession().getAttribute(ShiroDbRealm.SESSION_USER_KEY);*/
        log.info("doGetAuthorizationInfo 进入授权");
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole(user.getRole().trim());
        return info;
    }

    /**
     * 认证回调函数，登录信息和用户验证信息验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {
        log.info("doGetAuthenticationInfo 进入认证");
        // 把token转换成User对象
        User userLogin = tokenToUser((UsernamePasswordToken) authcToken);
        // 验证用户是否可以登录
        User ui = userService.doUserLogin(userLogin);
        if (ui == null)
            return null; // 异常处理，找不到数据
        // 设置session
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(ShiroDbRealm.SESSION_USER_KEY, ui);
        //当前 Realm 的 name
        String realmName = this.getName();
        //登陆的主要信息: 可以是一个实体类的对象, 但该实体类的对象一定是根据 token 的 username 查询得到的.
        //第一个参数principal 可以在授权的时候获取到
        return new SimpleAuthenticationInfo(ui, ui.getPassword(), ByteSource.Util.bytes(ui.getSalt()), realmName);
    }

    private User tokenToUser(UsernamePasswordToken authcToken) {
        User user = new User();
        user.setUsername(authcToken.getUsername());
        user.setPassword(String.valueOf(authcToken.getPassword()));
        return user;
    }

}