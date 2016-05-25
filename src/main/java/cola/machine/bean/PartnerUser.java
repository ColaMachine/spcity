/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package cola.machine.bean;
import java.sql.Timestamp;

public class PartnerUser {
    /**id**/
    private Integer id;
    public Integer getId(){
        return id;
    }    public void setId(Integer id){
        this.id=id;
    }/**用户id**/
    private Integer userId;
    public Integer getUserId(){
        return userId;
    }    public void setUserId(Integer userId){
        this.userId=userId;
    }/**合作伙伴Id**/
    private Long partnerId;
    public Long getPartnerId(){
        return partnerId;
    }    public void setPartnerId(Long partnerId){
        this.partnerId=partnerId;
    }
}
