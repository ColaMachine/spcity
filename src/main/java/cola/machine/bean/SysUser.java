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

public class SysUser {
    /**主键**/
    private Long id;
    public Long getId(){
        return id;
    }    public void setId(Long id){
        this.id=id;
    }/**用户名**/
    private String username;
    public String getUsername(){
        return username;
    }    public void setUsername(String username){
        this.username=username;
    }/**密码**/
    private String password;
    public String getPassword(){
        return password;
    }    public void setPassword(String password){
        this.password=password;
    }/**昵称**/
    private String nkname;
    public String getNkname(){
        return nkname;
    }    public void setNkname(String nkname){
        this.nkname=nkname;
    }/**类型**/
    private Integer type;
    public Integer getType(){
        return type;
    }    public void setType(Integer type){
        this.type=type;
    }/**状态**/
    private Integer status;
    public Integer getStatus(){
        return status;
    }    public void setStatus(Integer status){
        this.status=status;
    }/**邮箱地址**/
    private String email;
    public String getEmail(){
        return email;
    }    public void setEmail(String email){
        this.email=email;
    }/**手机号码**/
    private String telno;
    public String getTelno(){
        return telno;
    }    public void setTelno(String telno){
        this.telno=telno;
    }/**身份证号码**/
    private String idcard;
    public String getIdcard(){
        return idcard;
    }    public void setIdcard(String idcard){
        this.idcard=idcard;
    }/**性别**/
    private Integer sex;
    public Integer getSex(){
        return sex;
    }    public void setSex(Integer sex){
        this.sex=sex;
    }/**出生年月**/
    private Date birth;
    public Date getBirth(){
        return birth;
    }    public void setBirth(Date birth){
        this.birth=birth;
    }/**积分**/
    private Integer integral;
    public Integer getIntegral(){
        return integral;
    }    public void setIntegral(Integer integral){
        this.integral=integral;
    }/**地址**/
    private String address;
    public String getAddress(){
        return address;
    }    public void setAddress(String address){
        this.address=address;
    }/**微信**/
    private String weichat;
    public String getWeichat(){
        return weichat;
    }    public void setWeichat(String weichat){
        this.weichat=weichat;
    }/**qq**/
    private Long qq;
    public Long getQq(){
        return qq;
    }    public void setQq(Long qq){
        this.qq=qq;
    }/**头像**/
    private String face;
    public String getFace(){
        return face;
    }    public void setFace(String face){
        this.face=face;
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
    }/**更新时间**/
    private Timestamp updatetime;
    public Timestamp getUpdatetime(){
        return updatetime;
    }    public void setUpdatetime(Timestamp updatetime){
        this.updatetime=updatetime;
    }
}
