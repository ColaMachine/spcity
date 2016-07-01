/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package cola.machine.service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cola.machine.bean.SysRole;
import cola.machine.dao.SysRoleMapper;
import cola.machine.util.CacheUtil;
import cola.machine.util.ResultUtil;
import cola.machine.util.UUIDUtil;
import cola.machine.util.ValidateUtil;
import cola.machine.util.StringUtil;
import core.page.Page;
import java.util.StringTokenizer;
import cola.machine.bean.SysRoleResource;
import cola.machine.dao.SysRoleResourceMapper;

import core.action.ResultDTO;

@Service("sysRoleService")
public class SysRoleService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(SysRoleService.class);
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRoleResourceMapper sysRoleResourceMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SysRole> listByParams4Page(HashMap params) {
        return sysRoleMapper.listByParams4Page(params);
    }
    public List<SysRole> listByParams(HashMap params) {
        return sysRoleMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return sysRoleMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param SysRole
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SysRole sysRole) {
        // 进行字段验证
      /* ValidateUtil<SysRole> vu = new ValidateUtil<SysRole>();
        ResultDTO result = vu.valid(sysRole);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (sysRole.getId()==null) {

            sysRoleMapper.insert(sysRole);
        } else {
            sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        }
        return ResultUtil.getSuccResult();
    }
     /*
     * 说明:和关联关系一起保存
     * @param SysRole
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO saveWithChilds(SysRole sysRole,String childids) {
        // 进行字段验证
        ValidateUtil<SysRole> vu = new ValidateUtil<SysRole>();
        ResultDTO result = vu.valid(sysRole);
        if (result.getR() != 1) {
            return result;
        }
         //逻辑业务判断判断
       
        //判断是更新还是插入
        boolean addFlag =false;

        if (sysRole.getId()==null) {
               
            sysRoleMapper.insert(sysRole);
            addFlag=true;
        } else {
             sysRoleMapper.updateByPrimaryKey(sysRole);
        }

         /***删除掉不在关系中的数据****/
        HashMap params =new HashMap();
        params.put("roleid", sysRole.getId());
        List<SysRoleResource> list = sysRoleResourceMapper.listByParams(params);
        for(SysRoleResource sysRoleResource : list){
            String rid = ""+sysRoleResource.getRid();
            if( !StringUtil.splitStrContains(childids,rid)){
                sysRoleResourceMapper.deleteByPrimaryKey(sysRoleResource.getId());
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
                    params.put("roleid", sysRole.getId());
                    params.put("rid", stNow);
                    int count = sysRoleResourceMapper.countByParams(params);
                    if(count>0){
                        continue;
                    }
                    
                }
                SysRoleResource sysRoleResource=new SysRoleResource();
              
                sysRoleResource.setRoleid(sysRole.getId());
                sysRoleResource.setRid(Long.valueOf(stNow));
                sysRoleResourceMapper.insert( sysRoleResource);
            }
        }

        return ResultUtil.getSuccResult();
    }
    /**
    * 说明:根据主键删除数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public void delete(Long  id){
        sysRoleMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public SysRole selectByPrimaryKey(Long id){
       return sysRoleMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            sysRoleMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}
