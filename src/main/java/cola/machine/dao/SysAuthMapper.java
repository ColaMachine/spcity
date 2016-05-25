package cola.machine.dao;
 import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;

import cola.machine.bean.SysResource;
import cola.machine.bean.SysUser;

public interface SysAuthMapper {
    
    /**
     * 说明:根据用户id获取所有权限资源
     * @param id
     * @return
     * @return List<SysResource>
     * @author dozen.zhang
     * @date 2016年3月18日下午9:01:44
     */
    public List<SysResource> selectResourceByUserId(Long id);
    public List<SysResource> selectMenuResourceByUserId(Long id);
}
