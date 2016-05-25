package cola.machine.bean;

import java.sql.Timestamp;

public class Pwdrst {
private String idpwdrst;
private long userid;
private Timestamp createtime;
private boolean used;
private Timestamp resttime;
public String getIdpwdrst() {
	return idpwdrst;
}
public void setIdpwdrst(String idpwdrst) {
	this.idpwdrst = idpwdrst;
}
public Long getUserid() {
	return userid;
}
public void setUserid(Long userid) {
	this.userid = userid;
}
public Timestamp getCreatetime() {
	return createtime;
}
public void setCreatetime(Timestamp createtime) {
	this.createtime = createtime;
}
public boolean isUsed() {
	return used;
}
public void setUsed(boolean used) {
	this.used = used;
}
public Timestamp getResttime() {
	return resttime;
}
public void setResttime(Timestamp resttime) {
	this.resttime = resttime;
}

}
