/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package cola.machine.action;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cola.machine.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import cola.machine.service.SysUserService;
import cola.machine.bean.SysUser;
import cola.machine.util.rules.*;
import core.page.Page;

import cola.machine.util.ValidateUtil;
import core.util.RequestUtil;
import core.action.ResultDTO;

@Controller
@RequestMapping("/sysUser")
public class SysUserController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SysUserController.class);
    /** 权限service **/
    @Autowired
    private SysUserService sysUserService;
    
    /**
     * 说明: 跳转到角色列表页面
     * 
     * @return
     * @return String
     * @author dozen.zhang
     * @date 2015年11月15日下午12:30:45
     */
    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    public String list() {
        return "/static/html/SysUserList.html";
    }

    @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
    public String listMapper() {
        return "/static/html/SysUserListMapper.html";
    }

    /**
     * 说明:ajax请求角色信息
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午12:31:55
     */
    @RequestMapping(value = "/list.json")
    @ResponseBody
    public Object list(HttpServletRequest request) {
        Page page = RequestUtil.getPage(request);
        if(page ==null){
             return this.getWrongResultFromCfg("err.param.page");
        }
        
        HashMap<String,Object> params= new HashMap<String,Object>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String username = request.getParameter("username");
        if(!StringUtil.isBlank(username)){
            params.put("username",username);
        }
        String usernameLike = request.getParameter("usernameLike");
        if(!StringUtil.isBlank(usernameLike)){
            params.put("usernameLike",usernameLike);
        }
        String password = request.getParameter("password");
        if(!StringUtil.isBlank(password)){
            params.put("password",password);
        }
        String passwordLike = request.getParameter("passwordLike");
        if(!StringUtil.isBlank(passwordLike)){
            params.put("passwordLike",passwordLike);
        }
        String nkname = request.getParameter("nkname");
        if(!StringUtil.isBlank(nkname)){
            params.put("nkname",nkname);
        }
        String nknameLike = request.getParameter("nknameLike");
        if(!StringUtil.isBlank(nknameLike)){
            params.put("nknameLike",nknameLike);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String email = request.getParameter("email");
        if(!StringUtil.isBlank(email)){
            params.put("email",email);
        }
        String emailLike = request.getParameter("emailLike");
        if(!StringUtil.isBlank(emailLike)){
            params.put("emailLike",emailLike);
        }
        String telno = request.getParameter("telno");
        if(!StringUtil.isBlank(telno)){
            params.put("telno",telno);
        }
        String telnoLike = request.getParameter("telnoLike");
        if(!StringUtil.isBlank(telnoLike)){
            params.put("telnoLike",telnoLike);
        }
        String idcard = request.getParameter("idcard");
        if(!StringUtil.isBlank(idcard)){
            params.put("idcard",idcard);
        }
        String idcardLike = request.getParameter("idcardLike");
        if(!StringUtil.isBlank(idcardLike)){
            params.put("idcardLike",idcardLike);
        }
        String sex = request.getParameter("sex");
        if(!StringUtil.isBlank(sex)){
            params.put("sex",sex);
        }
        String birth = request.getParameter("birth");
        if(!StringUtil.isBlank(birth)){
            if(StringUtil.checkNumeric(birth)){
                params.put("birth",birth);
            }else if(StringUtil.checkDateStr(birth, "yyyy-MM-dd")){
                params.put("birth",DateUtil.parseToDate(birth, "yyyy-MM-dd"));
            }
        }
        String birthBegin = request.getParameter("birthBegin");
        if(!StringUtil.isBlank(birthBegin)){
            if(StringUtil.checkNumeric(birthBegin)){
                params.put("birthBegin",birthBegin);
            }else if(StringUtil.checkDateStr(birthBegin, "yyyy-MM-dd")){
                params.put("birthBegin",DateUtil.parseToDate(birthBegin, "yyyy-MM-dd"));
            }
        }
        String birthEnd = request.getParameter("birthEnd");
        if(!StringUtil.isBlank(birthEnd)){
            if(StringUtil.checkNumeric(birthEnd)){
                params.put("birthEnd",birthEnd);
            }else if(StringUtil.checkDateStr(birthEnd, "yyyy-MM-dd")){
                params.put("birthEnd",DateUtil.parseToDate(birthEnd, "yyyy-MM-dd"));
            }
        }
        String integral = request.getParameter("integral");
        if(!StringUtil.isBlank(integral)){
            params.put("integral",integral);
        }
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            params.put("address",address);
        }
        String addressLike = request.getParameter("addressLike");
        if(!StringUtil.isBlank(addressLike)){
            params.put("addressLike",addressLike);
        }
        String weichat = request.getParameter("weichat");
        if(!StringUtil.isBlank(weichat)){
            params.put("weichat",weichat);
        }
        String weichatLike = request.getParameter("weichatLike");
        if(!StringUtil.isBlank(weichatLike)){
            params.put("weichatLike",weichatLike);
        }
        String qq = request.getParameter("qq");
        if(!StringUtil.isBlank(qq)){
            params.put("qq",qq);
        }
        String face = request.getParameter("face");
        if(!StringUtil.isBlank(face)){
            params.put("face",face);
        }
        String faceLike = request.getParameter("faceLike");
        if(!StringUtil.isBlank(faceLike)){
            params.put("faceLike",faceLike);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                params.put("createtime",createtime);
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtime",new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeBegin = request.getParameter("createtimeBegin");
        if(!StringUtil.isBlank(createtimeBegin)){
            if(StringUtil.checkNumeric(createtimeBegin)){
                params.put("createtimeBegin",createtimeBegin);
            }else if(StringUtil.checkDateStr(createtimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeBegin",new Timestamp( DateUtil.parseToDate(createtimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeEnd = request.getParameter("createtimeEnd");
        if(!StringUtil.isBlank(createtimeEnd)){
            if(StringUtil.checkNumeric(createtimeEnd)){
                params.put("createtimeEnd",createtimeEnd);
            }else if(StringUtil.checkDateStr(createtimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeEnd",new Timestamp( DateUtil.parseToDate(createtimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                params.put("updatetime",updatetime);
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetime",new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeBegin = request.getParameter("updatetimeBegin");
        if(!StringUtil.isBlank(updatetimeBegin)){
            if(StringUtil.checkNumeric(updatetimeBegin)){
                params.put("updatetimeBegin",updatetimeBegin);
            }else if(StringUtil.checkDateStr(updatetimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetimeBegin",new Timestamp( DateUtil.parseToDate(updatetimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeEnd = request.getParameter("updatetimeEnd");
        if(!StringUtil.isBlank(updatetimeEnd)){
            if(StringUtil.checkNumeric(updatetimeEnd)){
                params.put("updatetimeEnd",updatetimeEnd);
            }else if(StringUtil.checkDateStr(updatetimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetimeEnd",new Timestamp( DateUtil.parseToDate(updatetimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        params.put("page",page);
        List<SysUser> sysUsers = sysUserService.listByParams4Page(params);
        return ResultUtil.getResult(sysUsers, page);
    }
    
   /**
    * 说明:ajax请求角色信息 无分页版本
    * @return Object
    * @author dozen.zhang
    * @date 2015年11月15日下午12:31:55
    */
    @RequestMapping(value = "/listAll.json")
    @ResponseBody
    public Object listAll(HttpServletRequest request) {
                HashMap<String,Object> params= new HashMap<String,Object>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String username = request.getParameter("username");
        if(!StringUtil.isBlank(username)){
            params.put("username",username);
        }
        String usernameLike = request.getParameter("usernameLike");
        if(!StringUtil.isBlank(usernameLike)){
            params.put("usernameLike",usernameLike);
        }
        String password = request.getParameter("password");
        if(!StringUtil.isBlank(password)){
            params.put("password",password);
        }
        String passwordLike = request.getParameter("passwordLike");
        if(!StringUtil.isBlank(passwordLike)){
            params.put("passwordLike",passwordLike);
        }
        String nkname = request.getParameter("nkname");
        if(!StringUtil.isBlank(nkname)){
            params.put("nkname",nkname);
        }
        String nknameLike = request.getParameter("nknameLike");
        if(!StringUtil.isBlank(nknameLike)){
            params.put("nknameLike",nknameLike);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String email = request.getParameter("email");
        if(!StringUtil.isBlank(email)){
            params.put("email",email);
        }
        String emailLike = request.getParameter("emailLike");
        if(!StringUtil.isBlank(emailLike)){
            params.put("emailLike",emailLike);
        }
        String telno = request.getParameter("telno");
        if(!StringUtil.isBlank(telno)){
            params.put("telno",telno);
        }
        String telnoLike = request.getParameter("telnoLike");
        if(!StringUtil.isBlank(telnoLike)){
            params.put("telnoLike",telnoLike);
        }
        String idcard = request.getParameter("idcard");
        if(!StringUtil.isBlank(idcard)){
            params.put("idcard",idcard);
        }
        String idcardLike = request.getParameter("idcardLike");
        if(!StringUtil.isBlank(idcardLike)){
            params.put("idcardLike",idcardLike);
        }
        String sex = request.getParameter("sex");
        if(!StringUtil.isBlank(sex)){
            params.put("sex",sex);
        }
        String birth = request.getParameter("birth");
        if(!StringUtil.isBlank(birth)){
            if(StringUtil.checkNumeric(birth)){
                params.put("birth",birth);
            }else if(StringUtil.checkDateStr(birth, "yyyy-MM-dd")){
                params.put("birth",DateUtil.parseToDate(birth, "yyyy-MM-dd"));
            }
        }
        String birthBegin = request.getParameter("birthBegin");
        if(!StringUtil.isBlank(birthBegin)){
            if(StringUtil.checkNumeric(birthBegin)){
                params.put("birthBegin",birthBegin);
            }else if(StringUtil.checkDateStr(birthBegin, "yyyy-MM-dd")){
                params.put("birthBegin",DateUtil.parseToDate(birthBegin, "yyyy-MM-dd"));
            }
        }
        String birthEnd = request.getParameter("birthEnd");
        if(!StringUtil.isBlank(birthEnd)){
            if(StringUtil.checkNumeric(birthEnd)){
                params.put("birthEnd",birthEnd);
            }else if(StringUtil.checkDateStr(birthEnd, "yyyy-MM-dd")){
                params.put("birthEnd",DateUtil.parseToDate(birthEnd, "yyyy-MM-dd"));
            }
        }
        String integral = request.getParameter("integral");
        if(!StringUtil.isBlank(integral)){
            params.put("integral",integral);
        }
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            params.put("address",address);
        }
        String addressLike = request.getParameter("addressLike");
        if(!StringUtil.isBlank(addressLike)){
            params.put("addressLike",addressLike);
        }
        String weichat = request.getParameter("weichat");
        if(!StringUtil.isBlank(weichat)){
            params.put("weichat",weichat);
        }
        String weichatLike = request.getParameter("weichatLike");
        if(!StringUtil.isBlank(weichatLike)){
            params.put("weichatLike",weichatLike);
        }
        String qq = request.getParameter("qq");
        if(!StringUtil.isBlank(qq)){
            params.put("qq",qq);
        }
        String face = request.getParameter("face");
        if(!StringUtil.isBlank(face)){
            params.put("face",face);
        }
        String faceLike = request.getParameter("faceLike");
        if(!StringUtil.isBlank(faceLike)){
            params.put("faceLike",faceLike);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                params.put("createtime",createtime);
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtime",new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeBegin = request.getParameter("createtimeBegin");
        if(!StringUtil.isBlank(createtimeBegin)){
            if(StringUtil.checkNumeric(createtimeBegin)){
                params.put("createtimeBegin",createtimeBegin);
            }else if(StringUtil.checkDateStr(createtimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeBegin",new Timestamp( DateUtil.parseToDate(createtimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeEnd = request.getParameter("createtimeEnd");
        if(!StringUtil.isBlank(createtimeEnd)){
            if(StringUtil.checkNumeric(createtimeEnd)){
                params.put("createtimeEnd",createtimeEnd);
            }else if(StringUtil.checkDateStr(createtimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeEnd",new Timestamp( DateUtil.parseToDate(createtimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                params.put("updatetime",updatetime);
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetime",new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeBegin = request.getParameter("updatetimeBegin");
        if(!StringUtil.isBlank(updatetimeBegin)){
            if(StringUtil.checkNumeric(updatetimeBegin)){
                params.put("updatetimeBegin",updatetimeBegin);
            }else if(StringUtil.checkDateStr(updatetimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetimeBegin",new Timestamp( DateUtil.parseToDate(updatetimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeEnd = request.getParameter("updatetimeEnd");
        if(!StringUtil.isBlank(updatetimeEnd)){
            if(StringUtil.checkNumeric(updatetimeEnd)){
                params.put("updatetimeEnd",updatetimeEnd);
            }else if(StringUtil.checkDateStr(updatetimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetimeEnd",new Timestamp( DateUtil.parseToDate(updatetimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        List<SysUser> sysUsers = sysUserService.listByParams(params);
        return ResultUtil.getResult(sysUsers);
    }
    
    /**
     * @param request 发请求
     * @return Object
     */
    @RequestMapping(value = "/edit.htm")
    public Object edit( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/SysUserEdit.html";
    }
    @RequestMapping(value = "/view.htm")
    public Object viewPage( HttpServletRequest request) {
        return "/static/html/SysUserView.html";
    }
   
    @RequestMapping(value = "/view.json")
    @ResponseBody
    public Object view(HttpServletRequest request) {
            String id = request.getParameter("id");
        HashMap<String,Object> result =new HashMap<String,Object>();
        if(!StringUtil.isBlank(id)){
            SysUser bean = sysUserService.selectByPrimaryKey(Long.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        SysUser bean = sysUserService.selectByPrimaryKey(Long.valueOf(id));
        HashMap<String,Object> result =new HashMap<String,Object>();
        result.put("bean", bean);
        return this.getResult(bean);*/
    }

    
    /**
     * 说明:保存角色信息
     * 
     * @param request
     * @return
     * @throws Exception
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:00
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "/save.json")
    @ResponseBody
    public Object save(HttpServletRequest request) throws Exception {
        SysUser sysUser =new  SysUser();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            sysUser.setId(Long.valueOf(id)) ;
        }
        
        String username = request.getParameter("username");
        if(!StringUtil.isBlank(username)){
            sysUser.setUsername(String.valueOf(username)) ;
        }
        
        String password = request.getParameter("password");
        if(!StringUtil.isBlank(password)){
            sysUser.setPassword(String.valueOf(password)) ;
        }
        
        String nkname = request.getParameter("nkname");
        if(!StringUtil.isBlank(nkname)){
            sysUser.setNkname(String.valueOf(nkname)) ;
        }
        
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            sysUser.setType(Integer.valueOf(type)) ;
        }
        
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            sysUser.setStatus(Integer.valueOf(status)) ;
        }
        
        String email = request.getParameter("email");
        if(!StringUtil.isBlank(email)){
            sysUser.setEmail(String.valueOf(email)) ;
        }
        
        String telno = request.getParameter("telno");
        if(!StringUtil.isBlank(telno)){
            sysUser.setTelno(String.valueOf(telno)) ;
        }
        
        String idcard = request.getParameter("idcard");
        if(!StringUtil.isBlank(idcard)){
            sysUser.setIdcard(String.valueOf(idcard)) ;
        }
        
        String sex = request.getParameter("sex");
        if(!StringUtil.isBlank(sex)){
            sysUser.setSex(Integer.valueOf(sex)) ;
        }
        
        String birth = request.getParameter("birth");
        if(!StringUtil.isBlank(birth)){
            sysUser.setBirth(Date.valueOf(birth)) ;
        }
        
        String integral = request.getParameter("integral");
        if(!StringUtil.isBlank(integral)){
            sysUser.setIntegral(Integer.valueOf(integral)) ;
        }
        
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            sysUser.setAddress(String.valueOf(address)) ;
        }
        
        String weichat = request.getParameter("weichat");
        if(!StringUtil.isBlank(weichat)){
            sysUser.setWeichat(String.valueOf(weichat)) ;
        }
        
        String qq = request.getParameter("qq");
        if(!StringUtil.isBlank(qq)){
            sysUser.setQq(Long.valueOf(qq)) ;
        }
        
        String face = request.getParameter("face");
        if(!StringUtil.isBlank(face)){
            sysUser.setFace(String.valueOf(face)) ;
        }
        
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            sysUser.setRemark(String.valueOf(remark)) ;
        }
        
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            sysUser.setCreatetime(Timestamp.valueOf(createtime)) ;
        }
        
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            sysUser.setUpdatetime(Timestamp.valueOf(updatetime)) ;
        }
        */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            sysUser.setId(Long.valueOf(id));
        }
        String username = request.getParameter("username");
        if(!StringUtil.isBlank(username)){
            sysUser.setUsername(username);
        }
        String password = request.getParameter("password");
        if(!StringUtil.isBlank(password)){
            sysUser.setPassword(password);
        }
        String nkname = request.getParameter("nkname");
        if(!StringUtil.isBlank(nkname)){
            sysUser.setNkname(nkname);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            sysUser.setType(Integer.valueOf(type));
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            sysUser.setStatus(Integer.valueOf(status));
        }
        String email = request.getParameter("email");
        if(!StringUtil.isBlank(email)){
            sysUser.setEmail(email);
        }
        String telno = request.getParameter("telno");
        if(!StringUtil.isBlank(telno)){
            sysUser.setTelno(telno);
        }
        String idcard = request.getParameter("idcard");
        if(!StringUtil.isBlank(idcard)){
            sysUser.setIdcard(idcard);
        }
        String sex = request.getParameter("sex");
        if(!StringUtil.isBlank(sex)){
            sysUser.setSex(Integer.valueOf(sex));
        }
        String birth = request.getParameter("birth");
        if(!StringUtil.isBlank(birth)){
            if(StringUtil.checkNumeric(birth)){
                sysUser.setBirth(new Date(birth));
            }else if(StringUtil.checkDateStr(birth, "yyyy-MM-dd")){
                sysUser.setBirth(DateUtil.parseToDate(birth, "yyyy-MM-dd"));
            }
        }
        String integral = request.getParameter("integral");
        if(!StringUtil.isBlank(integral)){
            sysUser.setIntegral(Integer.valueOf(integral));
        }
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            sysUser.setAddress(address);
        }
        String weichat = request.getParameter("weichat");
        if(!StringUtil.isBlank(weichat)){
            sysUser.setWeichat(weichat);
        }
        String qq = request.getParameter("qq");
        if(!StringUtil.isBlank(qq)){
            sysUser.setQq(Long.valueOf(qq));
        }
        String face = request.getParameter("face");
        if(!StringUtil.isBlank(face)){
            sysUser.setFace(face);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            sysUser.setRemark(remark);
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                sysUser.setCreatetime(Timestamp.valueOf(createtime));
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                sysUser.setCreatetime(new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                sysUser.setUpdatetime(Timestamp.valueOf(updatetime));
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                sysUser.setUpdatetime(new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "主键",  new Rule[]{new Digits(15,0)});
        vu.add("username", username, "用户名",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("password", password, "密码",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("nkname", nkname, "昵称",  new Rule[]{new Length(20)});
        vu.add("type", type, "类型",  new Rule[]{new Digits(4,0)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","2","3"}),new NotEmpty()});
        vu.add("email", email, "邮箱地址",  new Rule[]{new Length(50),new EmailRule()});
        vu.add("telno", telno, "手机号码",  new Rule[]{new Length(11),new PhoneRule()});
        vu.add("idcard", idcard, "身份证号码",  new Rule[]{new Length(18)});
        vu.add("sex", sex, "性别",  new Rule[]{new Digits(1,0)});
        vu.add("birth", birth, "出生年月",  new Rule[]{new DateValue("yyyy-MM-dd")});
        vu.add("integral", integral, "积分",  new Rule[]{new Digits(11,0)});
        vu.add("address", address, "地址",  new Rule[]{new Length(50)});
        vu.add("weichat", weichat, "微信",  new Rule[]{new Length(20)});
        vu.add("qq", qq, "qq",  new Rule[]{new Digits(11,0)});
        vu.add("face", face, "头像",  new Rule[]{new Length(100)});
        vu.add("remark", remark, "备注",  new Rule[]{new Length(200)});
        vu.add("createtime", createtime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updatetime", updatetime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotEmpty(sysUser.getPassword())){
        try {
            sysUser.setPassword(MD5Util.getStringMD5String(sysUser.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }}
        if(StringUtil.isNotEmpty(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return sysUserService.save(sysUser);
       
    }

    @RequestMapping(value = "/del.json")
    @ResponseBody
    public Object delete(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        Long id = Long.valueOf(idStr);
        sysUserService.delete(id);
        return this.getResult(SUCC);
    }
     /**
     * 多行删除
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/mdel.json")
    @ResponseBody
    public Object multiDelete(HttpServletRequest request) {
        String idStr = request.getParameter("ids");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        String idStrAry[]= idStr.split(",");
        Long idAry[]=new Long[idStrAry.length];
        for(int i=0,length=idAry.length;i<length;i++){
            ValidateUtil vu = new ValidateUtil();
            String validStr="";
            String id = idStrAry[i];
                    vu.add("id", id, "主键",  new Rule[]{});

            try{
                validStr=vu.validateString();
            }catch(Exception e){
                e.printStackTrace();
                validStr="验证程序异常";
                return ResultUtil.getResult(302,validStr);
            }
            
            if(StringUtil.isNotEmpty(validStr)) {
                return ResultUtil.getResult(302,validStr);
            }
            idAry[i]=Long.valueOf(idStrAry[i]);
        }
       return  sysUserService.multilDelete(idAry);
    }

    /**
     * 导出
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/export.json")
    @ResponseBody   
    public Object exportExcel(HttpServletRequest request){
               HashMap<String,Object> params= new HashMap<String,Object>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String username = request.getParameter("username");
        if(!StringUtil.isBlank(username)){
            params.put("username",username);
        }
        String usernameLike = request.getParameter("usernameLike");
        if(!StringUtil.isBlank(usernameLike)){
            params.put("usernameLike",usernameLike);
        }
        String password = request.getParameter("password");
        if(!StringUtil.isBlank(password)){
            params.put("password",password);
        }
        String passwordLike = request.getParameter("passwordLike");
        if(!StringUtil.isBlank(passwordLike)){
            params.put("passwordLike",passwordLike);
        }
        String nkname = request.getParameter("nkname");
        if(!StringUtil.isBlank(nkname)){
            params.put("nkname",nkname);
        }
        String nknameLike = request.getParameter("nknameLike");
        if(!StringUtil.isBlank(nknameLike)){
            params.put("nknameLike",nknameLike);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String email = request.getParameter("email");
        if(!StringUtil.isBlank(email)){
            params.put("email",email);
        }
        String emailLike = request.getParameter("emailLike");
        if(!StringUtil.isBlank(emailLike)){
            params.put("emailLike",emailLike);
        }
        String telno = request.getParameter("telno");
        if(!StringUtil.isBlank(telno)){
            params.put("telno",telno);
        }
        String telnoLike = request.getParameter("telnoLike");
        if(!StringUtil.isBlank(telnoLike)){
            params.put("telnoLike",telnoLike);
        }
        String idcard = request.getParameter("idcard");
        if(!StringUtil.isBlank(idcard)){
            params.put("idcard",idcard);
        }
        String idcardLike = request.getParameter("idcardLike");
        if(!StringUtil.isBlank(idcardLike)){
            params.put("idcardLike",idcardLike);
        }
        String sex = request.getParameter("sex");
        if(!StringUtil.isBlank(sex)){
            params.put("sex",sex);
        }
        String birth = request.getParameter("birth");
        if(!StringUtil.isBlank(birth)){
            if(StringUtil.checkNumeric(birth)){
                params.put("birth",birth);
            }else if(StringUtil.checkDateStr(birth, "yyyy-MM-dd")){
                params.put("birth",DateUtil.parseToDate(birth, "yyyy-MM-dd"));
            }
        }
        String birthBegin = request.getParameter("birthBegin");
        if(!StringUtil.isBlank(birthBegin)){
            if(StringUtil.checkNumeric(birthBegin)){
                params.put("birthBegin",birthBegin);
            }else if(StringUtil.checkDateStr(birthBegin, "yyyy-MM-dd")){
                params.put("birthBegin",DateUtil.parseToDate(birthBegin, "yyyy-MM-dd"));
            }
        }
        String birthEnd = request.getParameter("birthEnd");
        if(!StringUtil.isBlank(birthEnd)){
            if(StringUtil.checkNumeric(birthEnd)){
                params.put("birthEnd",birthEnd);
            }else if(StringUtil.checkDateStr(birthEnd, "yyyy-MM-dd")){
                params.put("birthEnd",DateUtil.parseToDate(birthEnd, "yyyy-MM-dd"));
            }
        }
        String integral = request.getParameter("integral");
        if(!StringUtil.isBlank(integral)){
            params.put("integral",integral);
        }
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            params.put("address",address);
        }
        String addressLike = request.getParameter("addressLike");
        if(!StringUtil.isBlank(addressLike)){
            params.put("addressLike",addressLike);
        }
        String weichat = request.getParameter("weichat");
        if(!StringUtil.isBlank(weichat)){
            params.put("weichat",weichat);
        }
        String weichatLike = request.getParameter("weichatLike");
        if(!StringUtil.isBlank(weichatLike)){
            params.put("weichatLike",weichatLike);
        }
        String qq = request.getParameter("qq");
        if(!StringUtil.isBlank(qq)){
            params.put("qq",qq);
        }
        String face = request.getParameter("face");
        if(!StringUtil.isBlank(face)){
            params.put("face",face);
        }
        String faceLike = request.getParameter("faceLike");
        if(!StringUtil.isBlank(faceLike)){
            params.put("faceLike",faceLike);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                params.put("createtime",createtime);
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtime",new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeBegin = request.getParameter("createtimeBegin");
        if(!StringUtil.isBlank(createtimeBegin)){
            if(StringUtil.checkNumeric(createtimeBegin)){
                params.put("createtimeBegin",createtimeBegin);
            }else if(StringUtil.checkDateStr(createtimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeBegin",new Timestamp( DateUtil.parseToDate(createtimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeEnd = request.getParameter("createtimeEnd");
        if(!StringUtil.isBlank(createtimeEnd)){
            if(StringUtil.checkNumeric(createtimeEnd)){
                params.put("createtimeEnd",createtimeEnd);
            }else if(StringUtil.checkDateStr(createtimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeEnd",new Timestamp( DateUtil.parseToDate(createtimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                params.put("updatetime",updatetime);
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetime",new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeBegin = request.getParameter("updatetimeBegin");
        if(!StringUtil.isBlank(updatetimeBegin)){
            if(StringUtil.checkNumeric(updatetimeBegin)){
                params.put("updatetimeBegin",updatetimeBegin);
            }else if(StringUtil.checkDateStr(updatetimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetimeBegin",new Timestamp( DateUtil.parseToDate(updatetimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeEnd = request.getParameter("updatetimeEnd");
        if(!StringUtil.isBlank(updatetimeEnd)){
            if(StringUtil.checkNumeric(updatetimeEnd)){
                params.put("updatetimeEnd",updatetimeEnd);
            }else if(StringUtil.checkDateStr(updatetimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetimeEnd",new Timestamp( DateUtil.parseToDate(updatetimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        // 查询list集合
        List<SysUser> list =sysUserService.listByParams(params);
        // 存放临时文件
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "list.xlsx");
        String folder = request.getSession().getServletContext()
                .getRealPath("/")
                + "xlstmp";
        File folder_file = new File(folder);
        if (!folder_file.exists()) {
            folder_file.mkdir();
        }
        String fileName = folder + File.separator
                + DateUtil.formatToString(new Date(), "yyyyMMddHHmmssSSS")
                + ".xlsx";
        // 得到导出Excle时清单的英中文map
        LinkedHashMap<String, String> colTitle = new LinkedHashMap<String, String>();
        colTitle.put("id", "主键");
        colTitle.put("username", "用户名");
        colTitle.put("password", "密码");
        colTitle.put("nkname", "昵称");
        colTitle.put("type", "类型");
        colTitle.put("status", "状态");
        colTitle.put("email", "邮箱地址");
        colTitle.put("telno", "手机号码");
        colTitle.put("idcard", "身份证号码");
        colTitle.put("sex", "性别");
        colTitle.put("birth", "出生年月");
        colTitle.put("integral", "积分");
        colTitle.put("address", "地址");
        colTitle.put("weichat", "微信");
        colTitle.put("qq", "qq");
        colTitle.put("face", "头像");
        colTitle.put("remark", "备注");
        colTitle.put("createtime", "创建时间");
        colTitle.put("updatetime", "更新时间");
        List finalList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            SysUser sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("username",  list.get(i).getUsername());
            map.put("password",  list.get(i).getPassword());
            map.put("nkname",  list.get(i).getNkname());
            map.put("type",  list.get(i).getType());
            map.put("status",  list.get(i).getStatus());
            map.put("email",  list.get(i).getEmail());
            map.put("telno",  list.get(i).getTelno());
            map.put("idcard",  list.get(i).getIdcard());
            map.put("sex",  list.get(i).getSex());
            map.put("birth",  list.get(i).getBirth());
            map.put("integral",  list.get(i).getIntegral());
            map.put("address",  list.get(i).getAddress());
            map.put("weichat",  list.get(i).getWeichat());
            map.put("qq",  list.get(i).getQq());
            map.put("face",  list.get(i).getFace());
            map.put("remark",  list.get(i).getRemark());
            map.put("createtime",  list.get(i).getCreatetime());
            map.put("updatetime",  list.get(i).getUpdatetime());
            finalList.add(map);
        }
        try {
            if (cola.machine.util.ExcelUtil.getExcelFile(finalList, fileName, colTitle) != null) {
                return this.getResult(SUCC,fileName,"导出成功");
            }
            /*
             * return new ResponseEntity<byte[]>(
             * FileUtils.readFileToByteArray(new File(fileName)), headers,
             * HttpStatus.CREATED);
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.getResult(0, "数据为空，导出失败");
    
    }
    @RequestMapping(value = "/import.json")
    public void importExcel(){
        
    }
}
