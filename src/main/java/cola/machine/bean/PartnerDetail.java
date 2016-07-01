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

public class PartnerDetail {
    /**编号**/
    private Long id;
    public Long getId(){
        return id;
    }    public void setId(Long id){
        this.id=id;
    }/**合作伙伴**/
    private Long partnerId;
    public Long getPartnerId(){
        return partnerId;
    }    public void setPartnerId(Long partnerId){
        this.partnerId=partnerId;
    }    private String Partner_name;
    public String getPartner_name(){
        return Partner_name;
    }    public void setPartner_name(String Partner_name){
        this.Partner_name=Partner_name;
    }/**内容**/
    private String content;
    public String getContent(){
        return content;
    }    public void setContent(String content){
        this.content=content;
    }
}
