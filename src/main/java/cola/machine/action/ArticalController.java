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

import cola.machine.bean.SysUser;
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

import cola.machine.service.ArticalService;
import cola.machine.bean.Artical;
import cola.machine.util.rules.*;
import core.page.Page;

import cola.machine.util.ValidateUtil;
import core.util.RequestUtil;
import core.action.ResultDTO;

@Controller
@RequestMapping("/artical")
public class ArticalController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(ArticalController.class);
    /** 权限service **/
    @Autowired
    private ArticalService articalService;
    
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

        return "/static/html/ArticalList.html";
    }
    @RequestMapping(value = "/listAudit.htm", method = RequestMethod.GET)
    public String listAuditing() {

        return "/static/html/ArticalListAudit.html";
    }
    @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
    public String listMapper() {
        return "/static/html/ArticalListMapper.html";
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
        String title = request.getParameter("title");
        if(!StringUtil.isBlank(title)){
            params.put("title",title);
        }
        String titleLike = request.getParameter("titleLike");
        if(!StringUtil.isBlank(titleLike)){
            params.put("titleLike",titleLike);
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            params.put("content",content);
        }
        String contentLike = request.getParameter("contentLike");
        if(!StringUtil.isBlank(contentLike)){
            params.put("contentLike",contentLike);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
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
        List<Artical> articals = articalService.listByParams4Page(params);
        ResultDTO result=  ResultUtil.getResult(articals, page);

        return result;
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
        String title = request.getParameter("title");
        if(!StringUtil.isBlank(title)){
            params.put("title",title);
        }
        String titleLike = request.getParameter("titleLike");
        if(!StringUtil.isBlank(titleLike)){
            params.put("titleLike",titleLike);
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            params.put("content",content);
        }
        String contentLike = request.getParameter("contentLike");
        if(!StringUtil.isBlank(contentLike)){
            params.put("contentLike",contentLike);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
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

        List<Artical> articals = articalService.listByParams(params);
        return ResultUtil.getResult(articals);
    }
    
    /**
     * @param request 发请求
     * @return Object
     */
    @RequestMapping(value = "/edit.htm")
    public Object edit( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/ArticalEdit.html";
    }
    @RequestMapping(value = "/view.htm")
    public Object viewPage( HttpServletRequest request) {
        return "/static/html/ArticalView.html";
    }
   
    @RequestMapping(value = "/view.json")
    @ResponseBody
    public Object view(HttpServletRequest request) {
            String id = request.getParameter("id");
        HashMap<String,Object> result =new HashMap<String,Object>();
        if(!StringUtil.isBlank(id)){
            Artical bean = articalService.selectByPrimaryKey(Long.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        Artical bean = articalService.selectByPrimaryKey(Long.valueOf(id));
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
        Artical artical =new  Artical();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            artical.setId(Long.valueOf(id)) ;
        }
        
        String title = request.getParameter("title");
        if(!StringUtil.isBlank(title)){
            artical.setTitle(String.valueOf(title)) ;
        }
        
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            artical.setContent(String.valueOf(content)) ;
        }
        
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            artical.setType(Integer.valueOf(type)) ;
        }
        
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            artical.setStatus(Integer.valueOf(status)) ;
        }
        
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            artical.setRemark(String.valueOf(remark)) ;
        }
        
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            artical.setCreatetime(Timestamp.valueOf(createtime)) ;
        }
        
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            artical.setUpdatetime(Timestamp.valueOf(updatetime)) ;
        }
        */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            artical.setId(Long.valueOf(id));
            Artical articalOld = articalService.selectByPrimaryKey(artical.getId());
            if(articalOld==null ||artical.getId()==null || articalOld.getId()==0){
                return this.getResult(302,"原始数据不存在");
            }else
            if(articalOld.getStatus()==3){
                return this.getResult(303,"已审核数据不允许修改");
            }else  if(articalOld.getStatus()==4){
                artical.setStatus(1);
            }else  if(articalOld.getStatus()==1){
                artical.setStatus(1);
            }else{
                return this.getResult(304,"状态异常");
            }
        }else{
            artical.setStatus(1);
            //新增
        }
        String title = request.getParameter("title");
        if(!StringUtil.isBlank(title)){
            artical.setTitle(title);
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            artical.setContent(content);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            artical.setType(Integer.valueOf(type));
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            artical.setStatus(Integer.valueOf(status));
            if(artical.getStatus()==3){
                return this.getResult(301,"状态不正确");
            }
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            artical.setRemark(remark);
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                artical.setCreatetime(Timestamp.valueOf(createtime));
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                artical.setCreatetime(new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                artical.setUpdatetime(Timestamp.valueOf(updatetime));
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                artical.setUpdatetime(new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String pic = request.getParameter("pic");
        artical.setPic(pic);
        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "主键",  new Rule[]{new Digits(15,0)});
        vu.add("title", title, "标题",  new Rule[]{new Length(40),new NotEmpty()});
        vu.add("content", content, "正文",  new Rule[]{new Length(4000),new NotEmpty()});
        vu.add("type", type, "类型",  new Rule[]{new Digits(11,0),new CheckBox(new String[]{"1","2","3"})});
        vu.add("status", status, "状态",  new Rule[]{new Digits(11,0),new CheckBox(new String[]{"1","2","3","4","5"})});
        vu.add("remark", remark, "备注",  new Rule[]{new Length(200)});
        vu.add("createtime", createtime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updatetime", updatetime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("pic", updatetime, "图片",  new Rule[]{new Length(20)});
        validStr = vu.validateString();
        if(StringUtil.isNotEmpty(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }
        SysUser user =(SysUser)request.getSession().getAttribute("user");

        if(StringUtil.isBlank(id)){
            artical.setCreator(user.getId());
            artical.setCreatorname(user.getUsername());
            artical.setCreatetime(new Timestamp(new Date().getTime()));
        }

        artical.setUpdatetime(new Timestamp(new Date().getTime()));

        String html = artical.getContent()+artical.getTitle();
        if(MGCUtil.contain(html)){
            return ResultUtil.getResult(302,"含敏感词");
        }
       // artical.setCreator(user.getId());
        //artical.setCreatorname(user.getUsername());

        return articalService.save(artical);
       
    }

    @RequestMapping(value = "/del.json")
    @ResponseBody
    public Object delete(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        Long id = Long.valueOf(idStr);
        articalService.delete(id);
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
       return  articalService.multilDelete(idAry);
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
        String title = request.getParameter("title");
        if(!StringUtil.isBlank(title)){
            params.put("title",title);
        }
        String titleLike = request.getParameter("titleLike");
        if(!StringUtil.isBlank(titleLike)){
            params.put("titleLike",titleLike);
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            params.put("content",content);
        }
        String contentLike = request.getParameter("contentLike");
        if(!StringUtil.isBlank(contentLike)){
            params.put("contentLike",contentLike);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
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
        List<Artical> list =articalService.listByParams(params);
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
        colTitle.put("title", "标题");
        colTitle.put("content", "正文");
        colTitle.put("type", "类型");
        colTitle.put("status", "状态");
        colTitle.put("remark", "备注");
        colTitle.put("createtime", "创建时间");
        colTitle.put("updatetime", "更新时间");
        List finalList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Artical sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("title",  list.get(i).getTitle());
            map.put("content",  list.get(i).getContent());
            map.put("type",  list.get(i).getType());
            map.put("status",  list.get(i).getStatus());
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
   @RequestMapping(value = "/preview.htm")
    public String preview(){

           return "/static/html/ArticalList.html";

    }
    @RequestMapping(value = "/audit.htm")
    public String auditview(){

        return "/static/html/ArticalAudit.html";

    }

    /**
     * 审核
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/auditPost.json")
    @ResponseBody
    public Object auditPost(HttpServletRequest request) throws Exception{
        AuthUtil.hasPermissionE(request, "Res_ArticalAuth");
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getResult(303,"参数不存在");
        }

        String statusStr =request.getParameter("status");
        //String remark =request.getParameter("remark");
        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", idStr, "主键",  new Rule[]{new Digits(15,0)});
        vu.add("status", statusStr, "状态",  new Rule[]{new Digits(11,0),new CheckBox(new String[]{"1","2","3","4","5"})});
        //vu.add("remark", remark, "备注",  new Rule[]{new Length(200)});
        validStr = vu.validateString();
        if(StringUtil.isNotEmpty(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }
        Artical artical =new Artical();
        artical=articalService.selectByPrimaryKey(Long.valueOf(idStr));
        if(artical==null){
            return this.getResult(302,"数据不存在");
        }
        if(artical.getStatus()!=1){
            return this.getResult(301,"只有状态为新建的才能被审核");
        }

        //artical.setId(Long.valueOf(idStr));
       // artical.setRemark(remark);
        artical.setStatus(Integer.valueOf(statusStr));
        artical.setUpdatetime(new Timestamp(new Date().getTime()));
        return articalService.save(artical);
    }
}
