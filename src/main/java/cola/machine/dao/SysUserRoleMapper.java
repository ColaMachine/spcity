package cola.machine.dao;
 import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import cola.machine.bean.SysUserRole;

public interface SysUserRoleMapper {
    
    int deleteByPrimaryKey(Long id);

    
    int insert(SysUserRole record);

   
    int insertSelective(SysUserRole record);

    
    SysUserRole  selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param SysUserRole  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SysUserRole record);

    /**
     * 说明:根据主键修改record完整内容
     * @param SysUserRole  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SysUserRole record);

    /**
     * 说明:根据map查找bean结果集
     * @param SysUserRole  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysUserRole> listByParams(Map map);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param SysUserRole  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysUserRole> listByParams4Page(Map map);
    
    /**
     * 说明:根据map查找map结果集
     * @param SysUserRole  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SysUserRole record);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param SysUserRole  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SysUserRole> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SysUserRole record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
    int deleteExtra(HashMap map);
}
