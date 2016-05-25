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

import cola.machine.bean.SysUserResource;
import cola.machine.dao.SysUserResourceMapper;
import cola.machine.util.CacheUtil;
import cola.machine.util.ResultUtil;
import cola.machine.util.UUIDUtil;
import cola.machine.util.ValidateUtil;
import cola.machine.util.StringUtil;
import core.page.Page;
import java.util.StringTokenizer;
import cola.machine.bean.SysUserResource;
import cola.machine.dao.SysUserResourceMapper;

import cola.machine.bean.SysUser;
import cola.machine.bean.SysResource;
import cola.machine.dao.SysUserMapper;
import cola.machine.dao.SysResourceMapper;

import core.action.ResultDTO;

@Service("sysUserResourceService")
public class SysUserResourceService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(SysUserResourceService.class);
    @Resource
    private SysUserResourceMapper sysUserResourceMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysResourceMapper sysResourceMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SysUserResource> listByParams4Page(HashMap params) {
        return sysUserResourceMapper.listByParams4Page(params);
    }
    public List<SysUserResource> listByParams(HashMap params) {
        return sysUserResourceMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return sysUserResourceMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param SysUserResource
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SysUserResource sysUserResource) {
        // 进行字段验证
       ValidateUtil<SysUserResource> vu = new ValidateUtil<SysUserResource>();
        ResultDTO result = vu.valid(sysUserResource);
        if (result.getR() != 1) {
            return result;
        }
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (sysUserResource.getId()==null) {
               
            sysUserResourceMapper.insert(sysUserResource);
        } else {
             sysUserResourceMapper.updateByPrimaryKey(sysUserResource);
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
        sysUserResourceMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public SysUserResource selectByPrimaryKey(Long id){
       return sysUserResourceMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            sysUserResourceMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
     /**
         * 多项关联保存
         * @param uids
         * @param rids
         * @return
         */
        public ResultDTO msave(String uids,String rids) {
            if(StringUtil.isBlank(uids)){
                return ResultUtil.getResult(101,"参数错误");
            }

            String[] uidAry= uids.split(",");
            String[] ridAry=rids.split(",");
            Long[] uidAryReal =new  Long[uidAry.length];
            Long[] ridAryReal =new  Long[ridAry.length];
            for(int i=0;i<uidAry.length;i++){
                if(!StringUtil.checkNumeric(uidAry[i])){
                    return ResultUtil.getResult(101,"参数错误");
                }
                uidAryReal[i]=Long.valueOf(uidAry[i]);
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
             if(uidAryReal!=null)
            for(int i=0;i< uidAryReal.length;i++){
                //
                SysUser sysUser = sysUserMapper.selectByPrimaryKey(uidAryReal[i]);
                if(sysUser==null ){
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
            for(int i=0;i<uidAryReal.length;i++){
                for(int j=0;j<ridAryReal.length;j++){
                   SysUserResource sysUserResource =new  SysUserResource();
                    Long uid =uidAryReal[i];
                    Long rid =ridAryReal[j];
                    //查找是否已经有关联数据了

                    params.put("rid",rid);
                    params.put("uid",uid);
                    int count = sysUserResourceMapper.countByParams(params);
                    if(count>0)continue;
                    sysUserResource.setRid(rid);
                    sysUserResource.setUid(uid);
                    sysUserResourceMapper.insert(sysUserResource);
                }
            }
            //删除多余的数据
            params.clear();
            params.put("rids",ridAryReal);
            params.put("uids",uidAryReal);
            sysUserResourceMapper.deleteExtra(params);
            //delete from SysUserRole where uid in (1,2,3,4,5) and rid not in(1,2,3)
            return ResultUtil.getSuccResult();
        }
}
