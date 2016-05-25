/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年12月26日
 * 文件说明: 
 */
package cola.machine.util.code;

import java.lang.reflect.Type;
import java.util.List;

import cola.machine.config.RedisConfig;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ZMapper {
    
    private String parent;
    private String child;
    private String parentid;
    private String childid;
    private String mapper;
    public String getParent() {
        return parent;
    }
    public void setParent(String parent) {
        this.parent = parent;
    }
    public String getChild() {
        return child;
    }
    public void setChild(String child) {
        this.child = child;
    }
    public String getParentid() {
        return parentid;
    }
    public void setParentid(String parentid) {
        this.parentid = parentid;
    }
    public String getChildid() {
        return childid;
    }
    public void setChildid(String childid) {
        this.childid = childid;
    }
    public String getMapper() {
        return mapper;
    }
    public void setMapper(String mapper) {
        this.mapper = mapper;
    }
}
