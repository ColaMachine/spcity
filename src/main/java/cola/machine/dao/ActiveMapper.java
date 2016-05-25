package cola.machine.dao;

import cola.machine.bean.Active;

public interface ActiveMapper {
	public int countAll();
	
	public Active selectActiveById(String userId);

	public void insertActive(Active active);

	public void updateActive(Active active);

	public void deleteActive(int activeid);



}
