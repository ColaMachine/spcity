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

import cola.machine.bean.SysUserRole;
import cola.machine.dao.SysUserRoleMapper;
import cola.machine.util.CacheUtil;
import cola.machine.util.ResultUtil;
import cola.machine.util.UUIDUtil;
import cola.machine.util.ValidateUtil;
import cola.machine.util.StringUtil;
import core.page.Page;
import java.util.StringTokenizer;
import cola.machine.bean.SysUserRole;
import cola.machine.dao.SysUserRoleMapper;

import cola.machine.bean.SysUser;
import cola.machine.bean.SysRole;
import cola.machine.dao.SysUserMapper;
import cola.machine.dao.SysRoleMapper;

import core.action.ResultDTO;

@Service("sysUserRoleService")
public class SysUserRoleService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(SysUserRoleService.class);
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SysUserRole> listByParams4Page(HashMap params) {
        return sysUserRoleMapper.listByParams4Page(params);
    }
    public List<SysUserRole> listByParams(HashMap params) {
        return sysUserRoleMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return sysUserRoleMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param SysUserRole
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SysUserRole sysUserRole) {
        // 进行字段验证
       ValidateUtil<SysUserRole> vu = new ValidateUtil<SysUserRole>();
        ResultDTO result = vu.valid(sysUserRole);
        if (result.getR() != 1) {
            return result;
        }
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (sysUserRole.getId()==null) {
               
            sysUserRoleMapper.insert(sysUserRole);
        } else {
             sysUserRoleMapper.updateByPrimaryKey(sysUserRole);
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
        sysUserRoleMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public SysUserRole selectByPrimaryKey(Long id){
       return sysUserRoleMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            sysUserRoleMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
     /**
         * 多项关联保存
         * @param uids
         * @param rids
         * @return
         */
        public ResultDTO msave(String uids,String roleids) {
            if(StringUtil.isBlank(uids)){
                return ResultUtil.getResult(101,"参数错误");
            }

            String[] uidAry= uids.split(",");
            String[] roleidAry=roleids.split(",");
            Long[] uidAryReal =new  Long[uidAry.length];
            Long[] roleidAryReal =new  Long[roleidAry.length];
            for(int i=0;i<uidAry.length;i++){
                if(!StringUtil.checkNumeric(uidAry[i])){
                    return ResultUtil.getResult(101,"参数错误");
                }
                uidAryReal[i]=Long.valueOf(uidAry[i]);
            }
            if(StringUtil.isBlank(roleids)){
                roleidAryReal=null;
                 roleidAry=null;
            }
            if(roleidAry!=null)
            for(int i=0;i<roleidAry.length;i++){
                if(!StringUtil.checkNumeric(roleidAry[i])){
                    return ResultUtil.getResult(101,"参数错误");
                }
                roleidAryReal[i]=Long.valueOf(roleidAry[i]);
            }
            //验证父亲id 正确性 是否存在
             if(uidAryReal!=null)
            for(int i=0;i< uidAryReal.length;i++){
                //
                SysUser sysUser = sysUserMapper.selectByPrimaryKey(uidAryReal[i]);
                if(sysUser==null ){
                    return ResultUtil.getResult(101,"数据不存在");
                }
                //查询的数据不存在
            }
             if(roleidAryReal!=null)
            for(int i=0;i<roleidAryReal.length;i++){
                 SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleidAryReal[i]);
                //查询的数据不存在
                if(sysRole==null ){
                    return ResultUtil.getResult(101,"数据不存在");
                }
            }
             HashMap params =new HashMap();
            //验证子id 正确性 是否存在
             if(roleidAryReal!=null)
            for(int i=0;i<uidAryReal.length;i++){
                for(int j=0;j<roleidAryReal.length;j++){
                   SysUserRole sysUserRole =new  SysUserRole();
                    Long uid =uidAryReal[i];
                    Long roleid =roleidAryReal[j];
                    //查找是否已经有关联数据了

                    params.put("roleid",roleid);
                    params.put("uid",uid);
                    int count = sysUserRoleMapper.countByParams(params);
                    if(count>0)continue;
                    sysUserRole.setRoleid(roleid);
                    sysUserRole.setUid(uid);
                    sysUserRoleMapper.insert(sysUserRole);
                }
            }
            //删除多余的数据
            params.clear();
            params.put("roleids",roleidAryReal);
            params.put("uids",uidAryReal);
            sysUserRoleMapper.deleteExtra(params);
            //delete from SysUserRole where uid in (1,2,3,4,5) and rid not in(1,2,3)
            return ResultUtil.getSuccResult();
        }
}
