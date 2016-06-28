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

public class ExpertArtical {
    /**编号**/
    private Long id;
    public Long getId(){
        return id;
    }    public void setId(Long id){
        this.id=id;
    }/**专家id**/
    private Long expertId;
    public Long getExpertId(){
        return expertId;
    }    public void setExpertId(Long expertId){
        this.expertId=expertId;
    }/**内容**/
    private String content;
    public String getContent(){
        return content;
    }    public void setContent(String content){
        this.content=content;
    }
}
