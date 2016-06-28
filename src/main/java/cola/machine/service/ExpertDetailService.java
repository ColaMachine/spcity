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

import cola.machine.bean.ExpertDetail;
import cola.machine.dao.ExpertDetailMapper;
import cola.machine.util.CacheUtil;
import cola.machine.util.ResultUtil;
import cola.machine.util.UUIDUtil;
import cola.machine.util.ValidateUtil;
import cola.machine.util.StringUtil;
import core.page.Page;
import core.action.ResultDTO;

@Service("expertDetailService")
public class ExpertDetailService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(ExpertDetailService.class);
    @Resource
    private ExpertDetailMapper expertDetailMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<ExpertDetail> listByParams4Page(HashMap params) {
        return expertDetailMapper.listByParams4Page(params);
    }
    public List<ExpertDetail> listByParams(HashMap params) {
        return expertDetailMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return expertDetailMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param ExpertDetail
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(ExpertDetail expertDetail) {
        // 进行字段验证
      /* ValidateUtil<ExpertDetail> vu = new ValidateUtil<ExpertDetail>();
        ResultDTO result = vu.valid(expertDetail);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (expertDetail.getId()==null) {
            //expertDetail.setCreatetime(new Timestamp(new Date().getTime()));
            expertDetailMapper.insert(expertDetail);
        } else {
           // expertDetail.setUpdatetime(new Timestamp(new Date().getTime()));
            expertDetailMapper.updateByPrimaryKeySelective(expertDetail);
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
        expertDetailMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public ExpertDetail selectByPrimaryKey(Long id){
       return expertDetailMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            expertDetailMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}
