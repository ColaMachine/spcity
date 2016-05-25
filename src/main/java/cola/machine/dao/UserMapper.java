package cola.machine.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cola.machine.bean.SysUser;

public interface UserMapper {
	public int countAll();
	
	public SysUser selectUserByUsername(String username);

	public List<SysUser> fuzzyQuery(String name);

	public SysUser selectUserByEmail(String email);

	public List<SysUser> selectAll();

    public SysUser selectUserByTelno(String telno);

    public int countUserByEmail(String email);

    public int countUserByTelno(String telno);

	public void resetPwd(SysUser user);

	void updateStatus(SysUser user);
}
