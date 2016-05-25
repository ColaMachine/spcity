package cola.machine.register.dao;

import java.util.Map;

import cola.machine.register.bean.VerticalBean;

public interface VerticalDao {
	/** 
	* @Title: insertVertical 
	* @Description: 插入验证信息 用来发送验证邮件
	* @param: 
	* @return void    返回类型 
	* @author zhangzw
	* @date 2014年6月23日
	* @throws 
	*/
	public void  insertVertical(VerticalBean bean);
	
	/** 
	* @Title: selectVerticalByParam 
	* @Description: 获取
	* @param: 
	* @return void    返回类型 
	* @author zhangzw
	* @date 2014年6月23日
	* @throws 
	*/
	public void selectVerticalByParam(Map map);
	
	/** 
	* @Title: deleteVerticalByParam 
	* @Description: 删除
	* @param: 
	* @return void    返回类型 
	* @author zhangzw
	* @date 2014年6月23日
	* @throws 
	*/
	public void deleteVerticalByParam(Map map);
	
	
}
