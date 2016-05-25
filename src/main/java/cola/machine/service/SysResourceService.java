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

import cola.machine.bean.SysResource;
import cola.machine.dao.SysResourceMapper;
import cola.machine.util.CacheUtil;
import cola.machine.util.ResultUtil;
import cola.machine.util.UUIDUtil;
import cola.machine.util.ValidateUtil;
import cola.machine.util.StringUtil;
import core.page.Page;
import core.action.ResultDTO;

@Service("sysResourceService")
public class SysResourceService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(SysResourceService.class);
    @Resource
    private SysResourceMapper sysResourceMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SysResource> listByParams4Page(HashMap params) {
        return sysResourceMapper.listByParams4Page(params);
    }
    public List<SysResource> listByParams(HashMap params) {
        return sysResourceMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return sysResourceMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param SysResource
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SysResource sysResource) {
        // 进行字段验证
       ValidateUtil<SysResource> vu = new ValidateUtil<SysResource>();
        ResultDTO result = vu.valid(sysResource);
        if (result.getR() != 1) {
            return result;
        }
         //逻辑业务判断判断
       //判断是否有uq字段
               HashMap params =new HashMap();
        params.put("code",sysResource.getCode());
        params.put("url",sysResource.getUrl());
        int count = sysResourceMapper.countByOrParams(params);
        if(StringUtil.isNull(sysResource.getId())&& count>0||count>1 ){
            return ResultUtil.getResult(302,"字段唯一不能重复");
        }

       //判断是更新还是插入
        if (sysResource.getId()==null) {
               
            sysResourceMapper.insert(sysResource);
        } else {
             sysResourceMapper.updateByPrimaryKey(sysResource);
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
        sysResourceMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public SysResource selectByPrimaryKey(Long id){
       return sysResourceMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            sysResourceMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}
