/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package cola.machine.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cola.machine.bean.${Abc};
import cola.machine.dao.${Abc}Mapper;
import cola.machine.util.CacheUtil;
import cola.machine.util.ResultUtil;
import cola.machine.util.UUIDUtil;
import cola.machine.util.ValidateUtil;
import cola.machine.util.StringUtil;
import core.page.Page;
<#if table.mapper??>
import java.util.StringTokenizer;
import cola.machine.bean.${table.mapper.mapper};
import cola.machine.dao.${table.mapper.mapper}Mapper;

</#if>
 <#if table.mapper??>
           <#if table.mapper.mapper==Abc>
import cola.machine.bean.${table.mapper.parent};
import cola.machine.bean.${table.mapper.child};
import cola.machine.dao.${table.mapper.parent}Mapper;
import cola.machine.dao.${table.mapper.child}Mapper;

    </#if>
        </#if>
import core.action.ResultDTO;

@Service("${abc}Service")
public class ${Abc}Service extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(${Abc}Service.class);
    @Resource
    private ${Abc}Mapper ${abc}Mapper;
        <#if table.mapper??>
           <#if table.mapper.mapper==table.name>
    @Resource
    private ${table.mapper.parent}Mapper ${table.mapper.parent?uncap_first}Mapper;
    @Resource
    private ${table.mapper.child}Mapper ${table.mapper.child?uncap_first}Mapper;
           <#else>
    @Resource
    private <@getAbc>${table.mapper.mapper}</@getAbc>Mapper <@getabc>${table.mapper.mapper}</@getabc>Mapper;
           </#if>
       </#if>
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<${Abc}> listByParams4Page(HashMap params) {
        return ${abc}Mapper.listByParams4Page(params);
    }
    public List<${Abc}> listByParams(HashMap params) {
        return ${abc}Mapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return ${abc}Mapper.countByParams(params);
    }

    /*
     * 说明:
     * @param ${Abc}
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(${Abc} ${abc}) {
        // 进行字段验证
      /* ValidateUtil<${Abc}> vu = new ValidateUtil<${Abc}>();
        ResultDTO result = vu.valid(${abc});
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       <#if distinctCheck??>
       ${distinctCheck}
       </#if>
       //判断是更新还是插入
        if (${abc}.get${table.pk.name[0]?upper_case}${table.pk.name[1..]}()==null) {
            <#if table.pk.type?starts_with("varchar")>
                ${abc}.set${table.pk.name[0]?upper_case}${table.pk.name[1..]}(UUIDUtil.getUUID());
            <#else>
            </#if>
            ${abc}.setCreatetime(new Timestamp(new Date().getTime()));
            ${abc}Mapper.insert(${abc});
        } else {
            ${abc}.setUpdatetime(new Timestamp(new Date().getTime()));
            ${abc}Mapper.updateByPrimaryKeySelective(${abc});
        }
        return ResultUtil.getSuccResult();
    }
    <#if table.mapper??>
     <#if table.mapper.parent== table.name>
     /*
     * 说明:和关联关系一起保存
     * @param ${Abc}
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO saveWithChilds(${Abc} ${abc},String childids) {
        // 进行字段验证
        ValidateUtil<${Abc}> vu = new ValidateUtil<${Abc}>();
        ResultDTO result = vu.valid(${abc});
        if (result.getR() != 1) {
            return result;
        }
         //逻辑业务判断判断
       
        //判断是更新还是插入
        boolean addFlag =false;

        if (${abc}.get${table.pk.name[0]?upper_case}${table.pk.name[1..]}()==null) {
            <#if table.pk.type?starts_with("varchar")>
                ${abc}.set${table.pk.name[0]?upper_case}${table.pk.name[1..]}(UUIDUtil.getUUID());
            <#else>
               
            </#if>
            ${abc}Mapper.insert(${abc});
            addFlag=true;
        } else {
             ${abc}Mapper.updateByPrimaryKey(${abc});
        }

         /***删除掉不在关系中的数据****/
        HashMap params =new HashMap();
        params.put("${table.mapper.parentid}", <@getabc>${abc}</@getabc>.get<@getAbc>${table.pk.name}</@getAbc>());
        List<${table.mapper.mapper}> list = <@getabc>${table.mapper.mapper}</@getabc>Mapper.listByParams(params);
        for(${table.mapper.mapper} <@getabc>${table.mapper.mapper}</@getabc> : list){
            String ${table.mapper.childid} = ""+<@getabc>${table.mapper.mapper}</@getabc>.get<@getAbc>${table.mapper.childid}</@getAbc>();
            if( !StringUtil.splitStrContains(childids,${table.mapper.childid})){
                <@getabc>${table.mapper.mapper}</@getabc>Mapper.deleteByPrimaryKey(<@getabc>${table.mapper.mapper}</@getabc>.getId());
            }
        }
        
        if(!StringUtil.isBlank(childids)){
            StringTokenizer st = new StringTokenizer(childids,",",false);
            while( st.hasMoreElements() ){
                String stNow=  st.nextToken(); 
                //System.out.println(stNow);
                //进行ids验证
                if(!StringUtil.checkNumeric(stNow)){
                    return ResultUtil.getResult(302,"子元素id格式不正确");
                }
                //查询是否有关联数据有的话就不用再插入了 如果是新增数据就不用判断了
                if(!addFlag){
                    params.clear();
                    params.put("${table.mapper.parentid}", ${abc}.getId());
                    params.put("${table.mapper.childid}", stNow);
                    int count = <@getabc>${table.mapper.mapper}</@getabc>Mapper.countByParams(params);
                    if(count>0){
                        continue;
                    }
                    
                }
                ${table.mapper.mapper} <@getabc>${table.mapper.mapper}</@getabc>=new ${table.mapper.mapper}();
              
                <@getabc>${table.mapper.mapper}</@getabc>.set<@getAbc>${table.mapper.parentid}</@getAbc>(${abc}.get<@getAbc>${table.pk.name}</@getAbc>());
                <@getabc>${table.mapper.mapper}</@getabc>.set<@getAbc>${table.mapper.childid}</@getAbc>(${serviceSaveWithChilds});
                <@getabc>${table.mapper.mapper}</@getabc>Mapper.insert( <@getabc>${table.mapper.mapper}</@getabc>);
            }
        }

        return ResultUtil.getSuccResult();
    }
     </#if>
    </#if>
    /**
    * 说明:根据主键删除数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public void delete(<@javaType>${table.pk.type}</@javaType>  ${table.pk.name}){
        ${abc}Mapper.deleteByPrimaryKey(${table.pk.name});
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public ${Abc} selectByPrimaryKey(<@javaType>${table.pk.type}</@javaType> id){
       return ${abc}Mapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(<@javaType>${table.pk.type}</@javaType>[] idAry) {
        for(int i=0;i<idAry.length;i++){
            ${abc}Mapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
      <#if table.mapper??>
         <#if table.mapper.mapper==Abc>
     /**
         * 多项关联保存
         * @param uids
         * @param rids
         * @return
         */
        public ResultDTO msave(String ${table.mapper.parentid}s,String ${table.mapper.childid}s) {
            if(StringUtil.isBlank(${table.mapper.parentid}s)){
                return ResultUtil.getResult(101,"参数错误");
            }

            String[] ${table.mapper.parentid}Ary= ${table.mapper.parentid}s.split(",");
            String[] ${table.mapper.childid}Ary=${table.mapper.childid}s.split(",");
            ${parentType}[] ${table.mapper.parentid}AryReal =new  ${parentType}[${table.mapper.parentid}Ary.length];
            ${childType}[] ${table.mapper.childid}AryReal =new  ${childType}[${table.mapper.childid}Ary.length];
            for(int i=0;i<${table.mapper.parentid}Ary.length;i++){
                if(!StringUtil.checkNumeric(${table.mapper.parentid}Ary[i])){
                    return ResultUtil.getResult(101,"参数错误");
                }
                ${table.mapper.parentid}AryReal[i]=Long.valueOf(${table.mapper.parentid}Ary[i]);
            }
            if(StringUtil.isBlank(${table.mapper.childid}s)){
                ${table.mapper.childid}AryReal=null;
                 ${table.mapper.childid}Ary=null;
            }
            if(${table.mapper.childid}Ary!=null)
            for(int i=0;i<${table.mapper.childid}Ary.length;i++){
                if(!StringUtil.checkNumeric(${table.mapper.childid}Ary[i])){
                    return ResultUtil.getResult(101,"参数错误");
                }
                ${table.mapper.childid}AryReal[i]=Long.valueOf(${table.mapper.childid}Ary[i]);
            }
            //验证父亲id 正确性 是否存在
             if(${table.mapper.parentid}AryReal!=null)
            for(int i=0;i< ${table.mapper.parentid}AryReal.length;i++){
                //
                ${table.mapper.parent?cap_first} ${table.mapper.parent?uncap_first} = ${table.mapper.parent?uncap_first}Mapper.selectByPrimaryKey(${table.mapper.parentid}AryReal[i]);
                if(${table.mapper.parent?uncap_first}==null ){
                    return ResultUtil.getResult(101,"数据不存在");
                }
                //查询的数据不存在
            }
             if(${table.mapper.childid}AryReal!=null)
            for(int i=0;i<${table.mapper.childid}AryReal.length;i++){
                 ${table.mapper.child?cap_first} ${table.mapper.child?uncap_first} = ${table.mapper.child?uncap_first}Mapper.selectByPrimaryKey(${table.mapper.childid}AryReal[i]);
                //查询的数据不存在
                if(${table.mapper.child?uncap_first}==null ){
                    return ResultUtil.getResult(101,"数据不存在");
                }
            }
             HashMap params =new HashMap();
            //验证子id 正确性 是否存在
             if(${table.mapper.childid}AryReal!=null)
            for(int i=0;i<${table.mapper.parentid}AryReal.length;i++){
                for(int j=0;j<${table.mapper.childid}AryReal.length;j++){
                   ${table.name?cap_first} ${table.name?uncap_first} =new  ${table.name?cap_first}();
                    ${parentType} ${table.mapper.parentid} =${table.mapper.parentid}AryReal[i];
                    ${childType} ${table.mapper.childid} =${table.mapper.childid}AryReal[j];
                    //查找是否已经有关联数据了

                    params.put("${table.mapper.childid}",${table.mapper.childid});
                    params.put("${table.mapper.parentid}",${table.mapper.parentid});
                    int count = ${abc}Mapper.countByParams(params);
                    if(count>0)continue;
                    ${table.name?uncap_first}.set${table.mapper.childid?cap_first}(${table.mapper.childid});
                    ${table.name?uncap_first}.set${table.mapper.parentid?cap_first}(${table.mapper.parentid});
                    ${table.name?uncap_first}Mapper.insert(${table.name?uncap_first});
                }
            }
            //删除多余的数据
            params.clear();
            params.put("${table.mapper.childid}s",${table.mapper.childid}AryReal);
            params.put("${table.mapper.parentid}s",${table.mapper.parentid}AryReal);
            ${table.name?uncap_first}Mapper.deleteExtra(params);
            //delete from SysUserRole where uid in (1,2,3,4,5) and rid not in(1,2,3)
            return ResultUtil.getSuccResult();
        }
          </#if>
              </#if>
}
