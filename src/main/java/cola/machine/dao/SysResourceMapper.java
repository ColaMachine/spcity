package cola.machine.dao;
 import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import cola.machine.bean.SysResource;

public interface SysResourceMapper {
    
    int deleteByPrimaryKey(Long id);

    
    int insert(SysResource record);

   
    int insertSelective(SysResource record);

    
    SysResource  selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param SysResource  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SysResource record);

    /**
     * 说明:根据主键修改record完整内容
     * @param SysResource  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SysResource record);

    /**
     * 说明:根据map查找bean结果集
     * @param SysResource  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysResource> listByParams(Map map);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param SysResource  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysResource> listByParams4Page(Map map);
    
    /**
     * 说明:根据map查找map结果集
     * @param SysResource  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SysResource record);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param SysResource  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SysResource> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SysResource record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}
