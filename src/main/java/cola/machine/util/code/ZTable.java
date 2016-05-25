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

public class ZTable {
private String name;
private String tableName;
private ZMapper mapper;
public ZMapper getMapper() {
    return mapper;
}
public void setMapper(ZMapper mapper) {
    this.mapper = mapper;
}
public String getTableName() {
    return tableName;
}
public void setTableName(String tableName) {
    this.tableName = tableName;
}

private String remark;
public String getRemark() {
    return remark;
}
public void setRemark(String remark) {
    this.remark = remark;
}

private ZColum pk;
public ZColum getPk() {
    return pk;
}
public void init(){
    for( ZColum col : cols){
        if(col.isPk()){
            pk=col;
                    break;
        }
    }
}
public void setPk(ZColum pk) {
    this.pk = pk;
}

private  List<ZColum> cols ; 



public List<ZColum> getCols() {
    return cols;
}

public void setCols(List<ZColum> cols) {
    this.cols = cols;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public static class Handler implements JsonSerializer<ZTable>, JsonDeserializer<ZTable> {

  
    /**
     * @param json 参数
     * @param typeOfT 参数
     * @param context 上下文
     * @return RedisConfig
     * @throws JsonParseException 抛出异常
     */
    public ZTable deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        ZTable talbe =new ZTable();
        JsonObject jsonObject = json.getAsJsonObject();
        talbe = context.deserialize(json, ZTable.class);
//        List<ZColum> zcolums= jsonObject.get("column").;
        return talbe;
    }

 
    /**
     * @param src 参数
     * @param typeOfT 参数
     * @param context 上下文
     * @return JsonElement 抛出异常
     */
    public JsonElement serialize(ZTable src, Type typeOfT, JsonSerializationContext context) {
        // JsonObject result =new JsonObject();
        // result = context.serialize(src);
        return context.serialize(src);
    }


 

}
}
