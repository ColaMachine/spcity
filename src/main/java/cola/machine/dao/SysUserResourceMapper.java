package cola.machine.dao;
 import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import cola.machine.bean.SysUserResource;

public interface SysUserResourceMapper {
    
    int deleteByPrimaryKey(Long id);

    
    int insert(SysUserResource record);

   
    int insertSelective(SysUserResource record);

    
    SysUserResource  selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param SysUserResource  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SysUserResource record);

    /**
     * 说明:根据主键修改record完整内容
     * @param SysUserResource  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SysUserResource record);

    /**
     * 说明:根据map查找bean结果集
     * @param SysUserResource  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysUserResource> listByParams(Map map);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param SysUserResource  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysUserResource> listByParams4Page(Map map);
    
    /**
     * 说明:根据map查找map结果集
     * @param SysUserResource  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SysUserResource record);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param SysUserResource  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SysUserResource> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SysUserResource record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
    int deleteExtra(HashMap map);
}
