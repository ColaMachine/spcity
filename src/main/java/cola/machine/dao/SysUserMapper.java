package cola.machine.dao;
 import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import cola.machine.bean.SysUser;

public interface SysUserMapper {
    
    int deleteByPrimaryKey(Long id);

    
    int insert(SysUser record);

   
    int insertSelective(SysUser record);

    
    SysUser  selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param SysUser  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SysUser record);

    /**
     * 说明:根据主键修改record完整内容
     * @param SysUser  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SysUser record);

    /**
     * 说明:根据map查找bean结果集
     * @param SysUser  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysUser> listByParams(Map map);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param SysUser  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysUser> listByParams4Page(Map map);
    
    /**
     * 说明:根据map查找map结果集
     * @param SysUser  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SysUser record);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param SysUser  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SysUser> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SysUser record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}
