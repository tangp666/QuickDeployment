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
public class LoginController extends BaseController{

    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class) ;

    /**
     * 项目启动默认login页面
     * @return
     */
    @RequestMapping("")
    public String login(){
        return "login";
    }

    /**
     * 项目启动默认login页面
     * @return
     */
    @RequestMapping("login")
    public String login2(){
        return "login";
    }

    /**
     * 登录
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
     * 退出
     */
    @RequestMapping("/loginOut")
    public String logout (){
        ShiroUtils.logout();
        return "index" ;
    }

    /**
     * 系统图标
     * @return
     */
    @RequestMapping("fontIcoList")
    public String fontIcoList(){
        return "fontIcoList";
    }

}
