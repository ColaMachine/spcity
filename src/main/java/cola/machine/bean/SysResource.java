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

public class SysResource {
    /**主键**/
    private Long id;
    public Long getId(){
        return id;
    }    public void setId(Long id){
        this.id=id;
    }/**父主键**/
    private Long pid;
    public Long getPid(){
        return pid;
    }    public void setPid(Long pid){
        this.pid=pid;
    }/**资源名称**/
    private String name;
    public String getName(){
        return name;
    }    public void setName(String name){
        this.name=name;
    }/**资源代码**/
    private String code;
    public String getCode(){
        return code;
    }    public void setCode(String code){
        this.code=code;
    }/**资源分类**/
    private String type;
    public String getType(){
        return type;
    }    public void setType(String type){
        this.type=type;
    }/**资源对应URL**/
    private String url;
    public String getUrl(){
        return url;
    }    public void setUrl(String url){
        this.url=url;
    }/**排序id**/
    private Integer order;
    public Integer getOrder(){
        return order;
    }    public void setOrder(Integer order){
        this.order=order;
    }/**状态**/
    private Integer status;
    public Integer getStatus(){
        return status;
    }    public void setStatus(Integer status){
        this.status=status;
    }/**备注**/
    private String remark;
    public String getRemark(){
        return remark;
    }    public void setRemark(String remark){
        this.remark=remark;
    }/**创建时间**/
    private Timestamp createtime;
    public Timestamp getCreatetime(){
        return createtime;
    }    public void setCreatetime(Timestamp createtime){
        this.createtime=createtime;
    }
}
