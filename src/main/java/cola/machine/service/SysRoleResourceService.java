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

import cola.machine.bean.SysRoleResource;
import cola.machine.dao.SysRoleResourceMapper;
import cola.machine.util.CacheUtil;
import cola.machine.util.ResultUtil;
import cola.machine.util.UUIDUtil;
import cola.machine.util.ValidateUtil;
import cola.machine.util.StringUtil;
import core.page.Page;
import java.util.StringTokenizer;
import cola.machine.bean.SysRoleResource;
import cola.machine.dao.SysRoleResourceMapper;

import cola.machine.bean.SysRole;
import cola.machine.bean.SysResource;
import cola.machine.dao.SysRoleMapper;
import cola.machine.dao.SysResourceMapper;

import core.action.ResultDTO;

@Service("sysRoleResourceService")
public class SysRoleResourceService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(SysRoleResourceService.class);
    @Resource
    private SysRoleResourceMapper sysRoleResourceMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysResourceMapper sysResourceMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SysRoleResource> listByParams4Page(HashMap params) {
        return sysRoleResourceMapper.listByParams4Page(params);
    }
    public List<SysRoleResource> listByParams(HashMap params) {
        return sysRoleResourceMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return sysRoleResourceMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param SysRoleResource
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SysRoleResource sysRoleResource) {
        // 进行字段验证
      /* ValidateUtil<SysRoleResource> vu = new ValidateUtil<SysRoleResource>();
        ResultDTO result = vu.valid(sysRoleResource);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (sysRoleResource.getId()==null) {

            sysRoleResourceMapper.insert(sysRoleResource);
        } else {
            sysRoleResourceMapper.updateByPrimaryKeySelective(sysRoleResource);
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
        sysRoleResourceMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public SysRoleResource selectByPrimaryKey(Long id){
       return sysRoleResourceMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            sysRoleResourceMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
     /**
         * 多项关联保存
         * @param uids
         * @param rids
         * @return
         */
        public ResultDTO msave(String roleids,String rids) {
            if(StringUtil.isBlank(roleids)){
                return ResultUtil.getResult(101,"参数错误");
            }

            String[] roleidAry= roleids.split(",");
            String[] ridAry=rids.split(",");
            Long[] roleidAryReal =new  Long[roleidAry.length];
            Long[] ridAryReal =new  Long[ridAry.length];
            for(int i=0;i<roleidAry.length;i++){
                if(!StringUtil.checkNumeric(roleidAry[i])){
                    return ResultUtil.getResult(101,"参数错误");
                }
                roleidAryReal[i]=Long.valueOf(roleidAry[i]);
            }
            if(StringUtil.isBlank(rids)){
                ridAryReal=null;
                 ridAry=null;
            }
            if(ridAry!=null)
            for(int i=0;i<ridAry.length;i++){
                if(!StringUtil.checkNumeric(ridAry[i])){
                    return ResultUtil.getResult(101,"参数错误");
                }
                ridAryReal[i]=Long.valueOf(ridAry[i]);
            }
            //验证父亲id 正确性 是否存在
             if(roleidAryReal!=null)
            for(int i=0;i< roleidAryReal.length;i++){
                //
                SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleidAryReal[i]);
                if(sysRole==null ){
                    return ResultUtil.getResult(101,"数据不存在");
                }
                //查询的数据不存在
            }
             if(ridAryReal!=null)
            for(int i=0;i<ridAryReal.length;i++){
                 SysResource sysResource = sysResourceMapper.selectByPrimaryKey(ridAryReal[i]);
                //查询的数据不存在
                if(sysResource==null ){
                    return ResultUtil.getResult(101,"数据不存在");
                }
            }
             HashMap params =new HashMap();
            //验证子id 正确性 是否存在
             if(ridAryReal!=null)
            for(int i=0;i<roleidAryReal.length;i++){
                for(int j=0;j<ridAryReal.length;j++){
                   SysRoleResource sysRoleResource =new  SysRoleResource();
                    Long roleid =roleidAryReal[i];
                    Long rid =ridAryReal[j];
                    //查找是否已经有关联数据了

                    params.put("rid",rid);
                    params.put("roleid",roleid);
                    int count = sysRoleResourceMapper.countByParams(params);
                    if(count>0)continue;
                    sysRoleResource.setRid(rid);
                    sysRoleResource.setRoleid(roleid);
                    sysRoleResourceMapper.insert(sysRoleResource);
                }
            }
            //删除多余的数据
            params.clear();
            params.put("rids",ridAryReal);
            params.put("roleids",roleidAryReal);
            sysRoleResourceMapper.deleteExtra(params);
            //delete from SysUserRole where uid in (1,2,3,4,5) and rid not in(1,2,3)
            return ResultUtil.getSuccResult();
        }
}
