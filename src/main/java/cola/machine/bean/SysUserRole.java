/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package cola.machine.bean;
import java.sql.Timestamp;
import java.util.Date;

public class SysUserRole {
    /**主键**/
    private Long id;
    public Long getId(){
        return id;
    }    public void setId(Long id){
        this.id=id;
    }/**用户id**/
    private Long uid;
    public Long getUid(){
        return uid;
    }    public void setUid(Long uid){
        this.uid=uid;
    }/**角色id**/
    private Long roleid;
    public Long getRoleid(){
        return roleid;
    }    public void setRoleid(Long roleid){
        this.roleid=roleid;
    }
}
