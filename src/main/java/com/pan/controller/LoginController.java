package com.pan.controller;

import com.pan.dao.SysMenuDao;
import com.pan.util.ShiroUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class LoginController {

    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class) ;

    @Resource
    private SysMenuDao sysMenuDao;
    /**
     * 登录测试
     * http://localhost:8080/userLogin?userName=admin&passWord=admin
     */
    @RequestMapping("/userLogin")
    public void userLogin (
            @RequestParam(value = "userName") String userName,
            @RequestParam(value = "passWord") String passWord){
        try{
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord);
            subject.login(token);
            LOGGER.info("登录成功");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 服务器每次重启请求该接口之前必须先请求上面登录接口
     * http://localhost:8080/menu/list 获取所有菜单列表
     * 权限要求：sys:user:shiro
     */
    @RequestMapping("/menu/list")
    @RequiresPermissions("sys:user:shiro")
    public List list(){
        return sysMenuDao.selectList() ;
    }

    /**
     * 用户没有该权限，无法访问
     * 权限要求：ccc:ddd:bbb
     */
    @RequestMapping("/menu/list2")
    @RequiresPermissions("ccc:ddd:bbb")
    public List list2(){
        return sysMenuDao.selectList() ;
    }

    /**
     * 退出测试
     */
    @RequestMapping("/userLogOut")
    public String logout (){
        ShiroUtils.logout();
        return "success" ;
    }

}
