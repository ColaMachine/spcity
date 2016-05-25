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

import cola.machine.service.${Abc}Service;
import cola.machine.bean.${Abc};
import cola.machine.util.ResultUtil;
import cola.machine.util.ValidateUtil;
import cola.machine.util.rules.*;
import core.page.Page;

import cola.machine.util.StringUtil;
import cola.machine.util.ValidateUtil;
import core.util.RequestUtil;
import core.action.ResultDTO;
import cola.machine.util.DateUtil;
<#if table.mapper??>
import cola.machine.bean.${table.mapper.mapper};
import cola.machine.service.${table.mapper.mapper}Service;
import cola.machine.bean.${table.mapper.child};
import cola.machine.service.${table.mapper.child}Service;
</#if>
@Controller
@RequestMapping("/${abc}")
public class ${Abc}Controller extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(${Abc}Controller.class);
    /** 权限service **/
    @Autowired
    private ${Abc}Service ${abc}Service;
    
    <#if table.mapper??>
     <#if table.mapper.mapper==table.name>

     <#else>
    @Autowired
    private <@getAbc>${table.mapper.mapper}</@getAbc>Service <@getabc>${table.mapper.mapper}</@getabc>Service;

     @Autowired
    private <@getAbc>${table.mapper.child}</@getAbc>Service <@getabc>${table.mapper.child}</@getabc>Service;
</#if>
    </#if>
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
        return "/static/html/${Abc}List.html";
    }

    @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
    public String listMapper() {
        return "/static/html/${Abc}ListMapper.html";
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
        
${getSearchParam}
        params.put("page",page);
        List<${Abc}> ${abc}s = ${abc}Service.listByParams4Page(params);
        return ResultUtil.getResult(${abc}s, page);
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
        ${getSearchParam}
        List<${Abc}> ${abc}s = ${abc}Service.listByParams(params);
        return ResultUtil.getResult(${abc}s);
    }
    
    /**
     * @param request 发请求
     * @return Object
     */
    @RequestMapping(value = "/edit.htm")
    public Object edit( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/${Abc}Edit.html";
    }
    @RequestMapping(value = "/view.htm")
    public Object viewPage( HttpServletRequest request) {
        return "/static/html/${Abc}View.html";
    }
   
    @RequestMapping(value = "/view.json")
    @ResponseBody
    public Object view(HttpServletRequest request) {
    ${controllerViewMethod}
      /*  String id = request.getParameter("id");
        ${Abc} bean = ${abc}Service.selectByPrimaryKey(<@javaType>${table.pk.type}</@javaType>.valueOf(id));
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
        ${Abc} ${abc} =new  ${Abc}();
        /*<#list table.cols as col>
        String ${col.name} = request.getParameter("${col.name}");
        if(!StringUtil.isBlank(${col.name})){
            ${abc}.set${col.name[0]?upper_case}${col.name[1..]}(<@javaType>${col.type}</@javaType>.valueOf(${col.name})) ;
        }
        </#list>*/
${setParam}
        //valid
${validCode}
          <#if table.mapper??>
            <#if table.mapper.parent==table.name>
        String childids = request.getParameter("childids");
        return ${abc}Service.saveWithChilds(${abc},childids);
        <#else>
          return ${abc}Service.save(${abc});
          </#if>

        <#else>
        return ${abc}Service.save(${abc});
        </#if>
       
    }
   <#if table.mapper??>
   <#if table.mapper.mapper==Abc>
    @RequestMapping(value = "/msave.json")
    @ResponseBody
    public Object msave(HttpServletRequest request) throws Exception {
        String ${table.mapper.parentid}s= request.getParameter("${table.mapper.parentid}s");
        String ${table.mapper.childid}s= request.getParameter("${table.mapper.childid}s");
        return ${table.name?uncap_first}Service.msave( ${table.mapper.parentid}s, ${table.mapper.childid}s);
    }
    </#if>
    </#if>

    @RequestMapping(value = "/del.json")
    @ResponseBody
    public Object delete(HttpServletRequest request) {
        String ${table.pk.name}Str = request.getParameter("${table.pk.name}");
        if(StringUtil.isBlank(${table.pk.name}Str)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        <@javaType>${table.pk.type}</@javaType> ${table.pk.name} = <@javaType>${table.pk.type}</@javaType>.valueOf(${table.pk.name}Str);
        ${abc}Service.delete(${table.pk.name});
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
        String idStr = request.getParameter("${table.pk.name}s");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        String idStrAry[]= idStr.split(",");
        <@javaType>${table.pk.type}</@javaType> idAry[]=new <@javaType>${table.pk.type}</@javaType>[idStrAry.length];
        for(int i=0,length=idAry.length;i<length;i++){
            ValidateUtil vu = new ValidateUtil();
            String validStr="";
            String <@getabc>${table.pk.name}</@getabc> = idStrAry[i];
            ${idvalid}
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
            idAry[i]=<@javaType>${table.pk.type}</@javaType>.valueOf(idStrAry[i]);
        }
       return  ${abc}Service.multilDelete(idAry);
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
       ${getSearchParam}
        // 查询list集合
        List<${Abc}> list =${abc}Service.listByParams(params);
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
        <#list table.cols as col>
        colTitle.put("${col.name}", "${col.remark}");
        </#list>
        List finalList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            ${Abc} sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
          <#list table.cols as col>
            map.put("${col.name}",  list.get(i).get${col.name[0]?upper_case}${col.name[1..]}());
          </#list>
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
