/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package cola.machine.action;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cola.machine.bean.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cola.machine.service.AuthService;
import cola.machine.service.UserService;
import cola.machine.util.ResultUtil;

import core.page.Page;

import core.action.ResultDTO;

@Controller
@RequestMapping("/auth")
public class AuthController {
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    /** 权限service **/
    @Autowired
    private AuthService authService;
    /** 用户service **/
    @Resource
    private UserService userService;


    @RequestMapping(value = "/menu/list.json")
    @ResponseBody
    public Object listMenu(HttpServletRequest request){
        String id=request.getParameter("id");
        SysUser user = (SysUser)request.getSession().getAttribute("user");
        return  ResultUtil.getResult(authService.listMenuResourcesByUserid(user.getId()));
    }

}
