package cola.machine.dao;

import java.util.List;

import cola.machine.bean.Pwdrst;

public interface PwdrstMapper {
	
	/**
	 * 说明:保存
	 * @param pwdrst
	 * @author dozen.zhang
	 * @date 2015年5月22日下午1:58:45
	 */
	public void insertPwdrst(Pwdrst pwdrst);

	/**
	 * 说明:获取
	 * @param id
	 * @return
	 * @author dozen.zhang
	 * @date 2015年5月22日下午1:58:54
	 */
	public Pwdrst selectPwdrstById(String id);

	/**
	 * 说明:设置为已使用
	 * @param id
	 * @author dozen.zhang
	 * @date 2015年5月22日下午1:59:03
	 */
	public void used(String id);

	/**
	 * 说明:删除 暂时没有应用场景
	 * @param activeid
	 * @author dozen.zhang
	 * @date 2015年5月22日下午1:59:22
	 */
	public void deletePwdrst(int activeid);

	/**
	 * 说明:是否重复发送激活邮件
	 * @param userid
	 * @return
	 * @author dozen.zhang
	 * @date 2015年5月22日下午1:59:35
	 */
	public List selectUnusedPwdrstByUserId(Long userid);



}
