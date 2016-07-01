package cola.machine.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.*;

import cola.machine.bean.SysResource;
import cola.machine.bean.SysUser;
import cola.machine.service.AuthService;
import cola.machine.util.*;
import cola.machine.util.log.ServiceMsg;
import cola.machine.util.rules.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cola.machine.constants.SysConfig;
import cola.machine.service.UserService;
import cola.machine.service.ValidCodeService;
import core.action.ResultDTO;

@Controller
// @RequestMapping("/")
public class OUserController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(OUserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
    /*
     * @InitBinder // 此处的参数也可以是ServletRequestDataBinder类型 public void
     * initBinder(ServletRequestDataBinder binder) throws Exception { DateFormat
     * df = new SimpleDateFormat("yyyy-MM-dd"); CustomDateEditor dateEditor =
     * new CustomDateEditor(df, true); binder.registerCustfomEditor(Date.class,
     * dateEditor); }
     */
/*  @Log(name="您访问了aop2方法")*/
    @RequestMapping(value = "/login.htm", method = RequestMethod.GET)
    public String loginGet(HttpServletRequest request) {
        // String s =request.getParameter("s");
        // s.substring(12);
        // logger.debug("s");
        // System.out.println(123);
        //getJedis().set("1","2");
        request.setAttribute("path", SysConfig.PATH);

        System.out.println("登录页面");
        return "/jsp/login.jsp";
    }

   /* @RequestMapping(value = "/user/listTree.json", method = RequestMethod.GET)
    public @ResponseBody ResultDTO listAllUsers() {
        List userList = new ArrayList();
        Iterator iter = MySessionContext.getMyMap().keySet().iterator();
        while (iter.hasNext()) {
            Object key = iter.next();
            Object val = MySessionContext.getMyMap().get(key);
            if (val != null) {
                HttpSession session = (HttpSession) val;
                Object userObj = session.getAttribute("user");
                session.invalidate();
                if (userObj != null) {
                    System.out.println(((SysUser) userObj).getEmail());
                    userList.add(((SysUser) userObj).getEmail());
                }
            }
        }
        return ResultUtil.getDataResult(userList);
    }*/

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index3(HttpServletRequest request) {
        request.setAttribute("path", SysConfig.PATH);
        return "/jsp/index.jsp";
    }

    /**
     * 说明:登录提交
     *
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月14日上午11:33:39
     */
    @RequestMapping(value = "/loginPost.json", method = RequestMethod.POST)
    public @ResponseBody ResultDTO loginPost(HttpServletRequest request) {
        String email = request.getParameter("email");
        String pwd = request.getParameter("pwd");
        String imgCaptcha = request.getParameter("picCaptcha");
        String smsCaptcha = request.getParameter("smsCaptcha");
        //String sessionid = request.getParameter("sessionid");
        ValidCodeService validCodeService = new ValidCodeService();

        ResultDTO result = validCodeService.remoteValidSms(email, smsCaptcha);
        if (!result.isRight()) {
            // return result;
        }
        result = validCodeService.imgValidCode("calendar",request.getRequestedSessionId(), imgCaptcha);
        if (!result.isRight()) {
            return result;
        }

        result = this.userService.loginValid(email, pwd);
        if (result.isRight()) {
            SysUser user = (SysUser) result.getData();
            request.getSession().setAttribute("user", user);
            List<SysResource> resources = authService.listResourcesByUserid(user.getId());
            List<String> resStr = new ArrayList<String>();
            for(SysResource res:resources){
                resStr.add(res.getCode());

            }
            request.getSession().setAttribute("resourceList", resStr);
            request.getSession().setAttribute("resourceStr", StringUtil.join(",",resStr.toArray(new String[resStr.size()])));
            result.setData(null);
        }

        //若果密码输入多次 增加 验证码 和锁定功能
        return result;
    }

    /**
     * 说明:转到注册页面
     *
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月14日上午11:33:55
     */
    @RequestMapping(value = "/register.htm", method = RequestMethod.GET)
    public String registerGet(HttpServletRequest request) {
        request.setAttribute("path", SysConfig.PATH);return "/jsp/register.jsp";
    }


    /**
     * 说明:注册提交
     *
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    @RequestMapping(value = "/registerPost.json", method = RequestMethod.POST)
    public @ResponseBody ResultDTO registerPost( HttpServletRequest request) {
        // 新注册的用户激活状态为false
        // 判断邮箱是否邮箱
        // 判断用户名是否有效
        // 判断注册邮箱是否重复

        // 两次密码输入是否相同
        // 密码是否有效
        // 验证码是否有效
        String email = request.getParameter("email");
        String username =request.getParameter("username");
        String password = request.getParameter("pwd");
        String imgCaptcha = request.getParameter("picCaptcha");

        // String smsCaptcha = request.getParameter("smsCaptcha");
        String sessionid = request.getRequestedSessionId();

        ValidateUtil vu = new ValidateUtil();
        String validStr="";

        vu.add("username", username, "用户名",  new Rule[]{new Required(),new Length(20),new NotEmpty()});
        vu.add("password", password, "密码",  new Rule[]{new Required(),new Length(50),new NotEmpty()});

        try {
            validStr = vu.validateString();
        } catch (Exception e) {

            return ResultUtil.getResult(302,"验证参数错误");
        }
        if(StringUtil.isNotEmpty(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        ValidCodeService validCodeService = new ValidCodeService();


        SysUser user =new SysUser();
        user.setPassword(password);
        user.setUsername(username);
        if(StringUtil.isPhone(email)){
            user.setTelno(email);
        }else if(StringUtil.isEmail(email)){
            user.setEmail(email);
        }else{
            return ResultUtil.getResult(301,"邮箱或手机号输入错误");
        }
        ResultDTO result = validCodeService.remoteValidImg(sessionid, imgCaptcha);
        if(!result.isRight()){
            return result;
        }
        result = this.userService.saveRegisterUser(user);// .loginValid(loginName,
        // pwd);
        if (result.isRight()) {
            HttpSession session = request.getSession();
            user.setPassword("");
            // user.setStatus(1);
            session.setAttribute("user", user);
        }
        return result;

    }
    @RequestMapping(value = "/requestValidPhoneCode.json", method = RequestMethod.POST)
    public @ResponseBody ResultDTO requestValidPhoneCode( HttpServletRequest request) {
        SysUser user = (SysUser)request.getSession().getAttribute("user");
        if(user==null ){
            return ResultUtil.getResult(300,"未登陆");
        }
        if(StringUtil.isBlank(user.getTelno())){
            return ResultUtil.getResult(301,"手机号未填写");
        }
        String phone =user.getTelno();

        ValidCodeService validCodeService=new ValidCodeService();

        //status 位置标识 0000 新注册  冻结 邮箱验证  手机验证
        int status =user.getStatus();
        if((status & 1) ==1){
            //说明已经激活过了
            return ResultUtil.getResult(301,"手机已验证过了");
        }
        ResultDTO result = validCodeService.getSmsValidCode("calendar",phone);
        return result;
    }

    /**
     * 说明:激活邮件回跳页面
     *
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月14日上午11:35:09
     */
    @RequestMapping(value = "/validEmail.json", method = RequestMethod.POST)
    public @ResponseBody ResultDTO activeWithEmail(HttpServletRequest request) {
        String code = request.getParameter("code");
        String email = request.getParameter("email");
        ResultDTO result;

        result = this.userService.activeWithEmail(email,code);

        return result;
    }
    @RequestMapping(value = "/validPhone.json", method = RequestMethod.POST)
    public @ResponseBody ResultDTO validPhone( HttpServletRequest request) {
        // 新注册的用户激活状态为false
        // 判断邮箱是否邮箱
        // 判断用户名是否有效
        // 判断注册邮箱是否重复
        // 两次密码输入是否相同
        // 密码是否有效
        // 验证码是否有效
        HttpSession session = request.getSession();
        SysUser user = (SysUser)session.getAttribute("user");
        if(user==null ){
            return ResultUtil.getResult(300,"未登陆");
        }
        if(StringUtil.isBlank(user.getTelno())){
            return ResultUtil.getResult(301,"手机号未填写");
        }
        String phone =user.getTelno();
        // String phone = request.getParameter("phone");
        String smsCaptcha = request.getParameter("smsCaptcha");
        if(StringUtil.isBlank(smsCaptcha)||smsCaptcha.length()<4||smsCaptcha.length()>12 ){
            return ResultUtil.getResult(301,"请填写正确验证码");
        }
        ValidCodeService validCodeService=new ValidCodeService();
        ResultDTO result = validCodeService.remoteValidSms(phone, smsCaptcha);
        if(!result.isRight()){
            return result;
        }
        //status 位置标识 0000 新注册  冻结 邮箱验证  手机验证
        int status =user.getStatus();
        if((status & 1) ==1){
            //说明已经激活过了
            return ResultUtil.getResult(301,"手机已验证过了");
        }
        status = status | 1;
        userService.updateStatus(status,user.getId());
        //result = this.userService.saveRegisterUser(user);// .loginValid(loginName,
        // pwd);
        if (result.isRight()) {

            user.setPassword("");
            user.setStatus(status);
            session.setAttribute("user", user);
        }
        return result;
    }

    /**
     * 说明:等待激活页面
     *
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:35
     */
    @RequestMapping(value = "/waitActive.htm", method = RequestMethod.GET)
    public String waitActive(HttpServletRequest request) {
        return "/active/waitActive.html";
    }

    /**
     * 说明:激活邮件回跳页面
     *
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月14日上午11:35:09
     */
    @RequestMapping(value = "/active.htm", method = RequestMethod.GET)
    public String active(HttpServletRequest request) {
        String activeid = request.getParameter("activeid");
        ResultDTO result;
        if (StringUtil.isNotEmpty(activeid)) {
            result = this.userService.updateUserActive(activeid);
        } else {
            request.setAttribute("msg", "激活url无效");
            return "/error.jsp";
        }
        if (result.isRight()) {
            // 把用户信息传入到session 中并让他登录到首页
            SysUser user = (SysUser) result.getData();
            request.getSession().setAttribute("user", user);
        } else {
            // 提示激活url无效
            request.setAttribute("msg", result.getMsg());
            return "/error.jsp";
        }
        return "/active/active.jsp";
    }

    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        // System.out.println(request.getParameter("path"));
        // System.out.println(request.getSession().getAttribute("path"));
        // System.out.println(request.getServletContext().getAttribute("path"));
        request.setAttribute("path", SysConfig.PATH);
        // logger.debug("s");
        // LogUtil.debug("nihao");
        // System.out.println(123);
        return "/jsp/index.jsp";
    }

    @RequestMapping(value = "/index2", method = RequestMethod.GET)
    public String index2(HttpServletRequest request) {
        // System.out.println(request.getParameter("path"));
        // System.out.println(request.getSession().getAttribute("path"));
        // System.out.println(request.getServletContext().getAttribute("path"));
        request.setAttribute("path", "/calendar");
        return "/index.jsp";
    }

    @RequestMapping(value = "/forgetpwd.htm", method = RequestMethod.GET)
    public String forgetPwd(HttpServletRequest request) {
        RandomValidateCode r = new RandomValidateCode();
        String[] returnStr = new String[2];
        try {
            returnStr = r.getImgRandcode();
        } catch (Exception e) {
        }
        String imgName = returnStr[0];
        String code = returnStr[1];
        request.setAttribute("imgname", imgName);
        request.getSession().setAttribute("validatecode", code);
        // TODO 需增加回收机制 回收已经生成过的图片
        return "/static/html/zforgetpwd.html";
    }

    @RequestMapping(value = "/validatecode", method = RequestMethod.GET)
    public @ResponseBody ResultDTO validateCode(HttpServletRequest request) {
        RandomValidateCode r = new RandomValidateCode();
        String[] returnStr = new String[2];
        try {
            returnStr = r.getImgRandcode();
        } catch (Exception e) {
        }
        String imgName = returnStr[0];
        String code = returnStr[1];
        ResultDTO result = new ResultDTO();
        request.getSession().setAttribute("validatecode", code);
        result.setR(1);
        result.setData(imgName);

        return result;
    }

    @RequestMapping(value = "/forgetpwd/save.json", method = RequestMethod.POST)
    public @ResponseBody ResultDTO sendPwdRstEmail(HttpServletRequest request) {
        // 生成图片
        // 得到验证码
//        String validatecode = (String) request.getSession().getAttribute("validatecode");
        // 验证验证码
//        String code = request.getParameter("code");
        /*if (!validatecode.equals(code)) {
            return this.getWrongResultFromCfg("validatecode.wrong");
        }*/

        if(StringUtil.isEmail(request.getParameter("phone"))){
            String email = request.getParameter("phone");
            return userService.saveSendPwdrstEmail(email);
        }else{
            return this.getResult(301,"参数错误");
        }

        // 发送邮件
        // return "/login/pwdreset.jsp";
    }

    /**
     * 说明:从密码重置链接中跳转到系统的密码重置页面
     *
     * @param id
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月20日下午4:24:22
     */
    @RequestMapping(value = "/pwdrst/edit.htm", method = RequestMethod.POST)
    public String editPwdrst(@PathVariable String id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "/login/pwdreset.jsp";
    }

    @RequestMapping(value = "/pwdrst/save.json", method = RequestMethod.POST)
    public @ResponseBody ResultDTO savePwdrst(HttpServletRequest request) {
        String account=request.getParameter("account");
        if(StringUtil.isBlank(account)){
            return ResultUtil.getResult(301,"账号不能为空");
        }
        if(StringUtil.isEmail(account)){

        }else if(StringUtil.isPhone(account)){

        }else{
            return ResultUtil.get(ServiceMsg.ACCOUNT_FORMAT_ERR);
        }
        String pwd = request.getParameter("pwd");
        String code = request.getParameter("code");


        ResultDTO result = userService.savePwdrst(account,pwd, code);
        // 发送邮件
        return result;
    }

    @RequestMapping(value = "/logout.htm", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "/jsp/login.jsp";
    }

    @RequestMapping(value = "/user/edit.htm", method = RequestMethod.GET)
    public String userEdit(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "/static/html/userEdit.html";
    }
/*
    @RequestMapping(value = "/user/edit.htm", method = RequestMethod.GET)
    public String editUser(@PathVariable String userid, HttpServletRequest request) {
        User user = this.userService.getUserByUserid(userid);
        request.setAttribute("user", user);
        return "/user/editUser";
    }

    @RequestMapping(value = "/user/view.htm", method = RequestMethod.GET)
    public String viewUser(@PathVariable String userid, HttpServletRequest request) {
        User user = this.userService.getUserByUserid(userid);
        request.setAttribute("user", user);
        return "/user/viewUser";
    }*/





    public static void main(String[] args) {
        ApplicationContext ac = new FileSystemXmlApplicationContext("C:\\Users\\dozen.zhang\\Documents\\calendar\\src\\main\\resources\\config\\xml\\applicationContext.xml");
        Object object = ac.getBean("validCodeService");
        System.out.println(object);
    }


}
