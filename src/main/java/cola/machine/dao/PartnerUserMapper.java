package cola.machine.dao;
 import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import cola.machine.bean.PartnerUser;

public interface PartnerUserMapper {
    
    int deleteByPrimaryKey(Integer id);

    
    int insert(PartnerUser record);

   
    int insertSelective(PartnerUser  record);

    
    PartnerUser  selectByPrimaryKey(Integer id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param PartnerUser  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(PartnerUser record);

    /**
     * 说明:根据主键修改record完整内容
     * @param PartnerUser  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(PartnerUser record);

    /**
     * 说明:根据map查找bean结果集
     * @param PartnerUser  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<PartnerUser> listByParams(Map map);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param PartnerUser  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<PartnerUser> listByParams4Page(Map map);
    
    /**
     * 说明:根据map查找map结果集
     * @param PartnerUser  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(PartnerUser record);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param PartnerUser  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<PartnerUser> selectBeanByMap4Page(HashMap map);
    
    int countByBean(PartnerUser record);*/
    
    int countByParams(HashMap map);
      
      
}
