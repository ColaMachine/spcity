/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年12月26日
 * 文件说明: 
 */
package cola.machine.util.code;

import java.util.Map;

public class ZColum {
private String name;
private boolean nn;
private boolean pk;

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    private boolean edit;

    private boolean uq;

    public boolean isUq() {
        return uq;
    }

    public void setUq(boolean uq) {
        this.uq = uq;
    }

    private boolean ai;
public boolean isAi() {
    return ai;
}
public void setAi(boolean ai) {
    this.ai = ai;
}
private String def;
public boolean isNn() {
    return nn;
}
public void setNn(boolean nn) {
    this.nn = nn;
}
public boolean isPk() {
    return pk;
}
public void setPk(boolean pk) {
    this.pk = pk;
}
public String getDef() {
    return def;
}
public void setDef(String def) {
    this.def = def;
}
private String type;
private String remark;
private Map showValue;
public Map getShowValue() {
    return showValue;
}
public void setShowValue(Map showValue) {
    this.showValue = showValue;
}
private String valid;
public String getName() {
    return name;
}
public void setName(String name) {
    this.name = name;
}
public String getType() {
    return type;
}
public void setType(String type) {
    this.type = type;
}
public String getRemark() {
    return remark;
}
public void setRemark(String remark) {
    this.remark = remark;
}
public String getValid() {
    return valid;
}
public void setValid(String valid) {
    this.valid = valid;
}
/*
public String getJavaType(){
    return "long";
}*/
    /*
    name:"id",
    type:varchar(40),
    cn_name:"id",
    valid:"Length(min=1,max=40 ,message='图片名称不能超过40个')",*/
}
