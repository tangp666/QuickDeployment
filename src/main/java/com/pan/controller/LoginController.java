package com.pan.controller;

import com.pan.dao.SysMenuDao;
import com.pan.entity.ResultEntity;
import com.pan.enums.ResultEnum;
import com.pan.util.ShiroUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户登录
 * @author tangpan
 */
@Controller
public class LoginController {

    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class) ;

    @Resource
    private SysMenuDao sysMenuDao;

    /**
     * 项目启动默认login页面
     * @return
     */
    @RequestMapping("")
    public String login(){
        return "login";
    }

    /**
     * 登录测试
     * http://localhost:8080/userLogin?userName=admin&passWord=admin
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity userLogin (String username, String password){
        int code = -1;
        String message = "";
        try{
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);

            code = ResultEnum.SUCCESS.getCode();
            message = ResultEnum.SUCCESS.getMessage();
            LOGGER.info("登录成功");
        }catch (Exception e) {
            code = ResultEnum.EXCEPTION.getCode();
            message = e.getMessage();
            e.printStackTrace();
        }
        return new ResultEntity(code,message);
    }

    /**
     * 服务器每次重启请求该接口之前必须先请求上面登录接口
     * http://localhost:8080/menu/list 获取所有菜单列表
     * 权限要求：sys:user:shiro
     */
    @RequestMapping("/menu/list")
    @RequiresPermissions("sys:user:shiro")
    public List list(){
        return sysMenuDao.findALL() ;
    }

    /**
     * 用户没有该权限，无法访问
     * 权限要求：ccc:ddd:bbb
     */
    @RequestMapping("/menu/list2")
    @RequiresPermissions("ccc:ddd:bbb")
    public List list2(){
        return sysMenuDao.findALL() ;
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
