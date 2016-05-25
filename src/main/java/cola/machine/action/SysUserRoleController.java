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

import cola.machine.service.SysUserRoleService;
import cola.machine.bean.SysUserRole;
import cola.machine.util.ResultUtil;
import cola.machine.util.ValidateUtil;
import cola.machine.util.rules.*;
import core.page.Page;

import cola.machine.util.StringUtil;
import cola.machine.util.ValidateUtil;
import core.util.RequestUtil;
import core.action.ResultDTO;
import cola.machine.util.DateUtil;
import cola.machine.bean.SysUserRole;
import cola.machine.service.SysUserRoleService;
import cola.machine.bean.SysRole;
import cola.machine.service.SysRoleService;
@Controller
@RequestMapping("/sysUserRole")
public class SysUserRoleController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SysUserRoleController.class);
    /** 权限service **/
    @Autowired
    private SysUserRoleService sysUserRoleService;
    

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
        return "/static/html/SysUserRoleList.html";
    }

    @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
    public String listMapper() {
        return "/static/html/SysUserRoleListMapper.html";
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
        String uid = request.getParameter("uid");
        if(!StringUtil.isBlank(uid)){
            params.put("uid",uid);
        }
        String roleid = request.getParameter("roleid");
        if(!StringUtil.isBlank(roleid)){
            params.put("roleid",roleid);
        }

        params.put("page",page);
        List<SysUserRole> sysUserRoles = sysUserRoleService.listByParams4Page(params);
        return ResultUtil.getResult(sysUserRoles, page);
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
        String uid = request.getParameter("uid");
        if(!StringUtil.isBlank(uid)){
            params.put("uid",uid);
        }
        String roleid = request.getParameter("roleid");
        if(!StringUtil.isBlank(roleid)){
            params.put("roleid",roleid);
        }

        List<SysUserRole> sysUserRoles = sysUserRoleService.listByParams(params);
        return ResultUtil.getResult(sysUserRoles);
    }
    
    /**
     * @param request 发请求
     * @return Object
     */
    @RequestMapping(value = "/edit.htm")
    public Object edit( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/SysUserRoleEdit.html";
    }
    @RequestMapping(value = "/view.htm")
    public Object viewPage( HttpServletRequest request) {
        return "/static/html/SysUserRoleView.html";
    }
   
    @RequestMapping(value = "/view.json")
    @ResponseBody
    public Object view(HttpServletRequest request) {
            String id = request.getParameter("id");
        HashMap<String,Object> result =new HashMap<String,Object>();
        if(!StringUtil.isBlank(id)){
            SysUserRole bean = sysUserRoleService.selectByPrimaryKey(Long.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        SysUserRole bean = sysUserRoleService.selectByPrimaryKey(Long.valueOf(id));
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
        SysUserRole sysUserRole =new  SysUserRole();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            sysUserRole.setId(Long.valueOf(id)) ;
        }
        
        String uid = request.getParameter("uid");
        if(!StringUtil.isBlank(uid)){
            sysUserRole.setUid(Long.valueOf(uid)) ;
        }
        
        String roleid = request.getParameter("roleid");
        if(!StringUtil.isBlank(roleid)){
            sysUserRole.setRoleid(Long.valueOf(roleid)) ;
        }
        */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            sysUserRole.setId(Long.valueOf(id));
        }
        String uid = request.getParameter("uid");
        if(!StringUtil.isBlank(uid)){
            sysUserRole.setUid(Long.valueOf(uid));
        }
        String roleid = request.getParameter("roleid");
        if(!StringUtil.isBlank(roleid)){
            sysUserRole.setRoleid(Long.valueOf(roleid));
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "主键",  new Rule[]{new Digits(15,0)});
        vu.add("uid", uid, "用户id",  new Rule[]{new Digits(15,0),new NotEmpty()});
        vu.add("roleid", roleid, "角色id",  new Rule[]{new Digits(15,0),new NotEmpty()});
        validStr = vu.validateString();
        if(StringUtil.isNotEmpty(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

          return sysUserRoleService.save(sysUserRole);

       
    }
    @RequestMapping(value = "/msave.json")
    @ResponseBody
    public Object msave(HttpServletRequest request) throws Exception {
        String uids= request.getParameter("uids");
        String roleids= request.getParameter("roleids");
        return sysUserRoleService.msave( uids, roleids);
    }

    @RequestMapping(value = "/del.json")
    @ResponseBody
    public Object delete(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        Long id = Long.valueOf(idStr);
        sysUserRoleService.delete(id);
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
       return  sysUserRoleService.multilDelete(idAry);
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
        String uid = request.getParameter("uid");
        if(!StringUtil.isBlank(uid)){
            params.put("uid",uid);
        }
        String roleid = request.getParameter("roleid");
        if(!StringUtil.isBlank(roleid)){
            params.put("roleid",roleid);
        }

        // 查询list集合
        List<SysUserRole> list =sysUserRoleService.listByParams(params);
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
        colTitle.put("uid", "用户id");
        colTitle.put("roleid", "角色id");
        List finalList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            SysUserRole sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("uid",  list.get(i).getUid());
            map.put("roleid",  list.get(i).getRoleid());
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
