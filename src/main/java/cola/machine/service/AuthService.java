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

import cola.machine.bean.*;
import cola.machine.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cola.machine.util.CacheUtil;
import cola.machine.util.ResultUtil;
import cola.machine.util.StringUtil;
import cola.machine.util.UUIDUtil;
import cola.machine.util.ValidateUtil;
import core.page.Page;
import core.action.ResultDTO;

@Service("authService")
public class AuthService extends BaseService {
	private static final Logger logger = LoggerFactory
			.getLogger(AuthService.class);



    @Resource
    private SysAuthMapper authMapper;

    /**
     * 根据用户id查询用户资源信息
     * @param userid
     * @return
     */
    public List<SysResource> listResourcesByUserid(Long userid){
       return  authMapper.selectResourceByUserId(userid);
    }
    public List<SysResource> listMenuResourcesByUserid(Long userid){
        return  authMapper.selectMenuResourceByUserId(userid);
    }
}
