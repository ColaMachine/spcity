/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2016年3月12日
 * 文件说明: 
 */
package cola.machine.util.code;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import cola.machine.util.StringUtil;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class GenCodeHelper {

    /**
     * 代码字符串  new Timestamp(DateUtil.parseToDate(startTime,"yyyy-MM-dd").getTime())
     * @param type
     * @param name
     * @return
     */
    public static String changeStrVar2BeanType(String type,String name){
    StringBuffer sb = new StringBuffer();
    if (type.startsWith("timestamp")) {
        sb.append("new Timestamp( DateUtil.parseToDate(" +name+ ", \"" + StringUtil.getYMDStr(type) + "\").getTime())");
    }else
    if (type.startsWith("date")||type.startsWith("datetime")) {
        sb.append("DateUtil.parseToDate(" + name + ", \"" + StringUtil.getYMDStr(type) + "\")");
    }else
    if (type.startsWith("int")||type.startsWith("Long")||type.startsWith("tinyint")) {
        sb.append(changeMySqlType2JavaType(type)+".valueOf("+name+")");
    }else{
        sb.append(name);
    }
    return sb.toString();
}

    public static ZColum getColFromCols(List<ZColum> cols ,String colName){
        for(ZColum col:cols ){
            if(col.getName().equals(colName)){
                return col;
            }
        }
        return null;
    }
public static Integer getIntFromKuoHao(String str){
    int index = str.indexOf("(");
    if(index==-1){
        return null;
    }
    else{
        int end =str.indexOf(")");
        if(end>index){
            String val = str.substring(index+1,end);
            return Integer.valueOf(val);
        }else{
            return null;
        }
        
    }
}

    /**
     * mysql 的字段类型转换成java的类型
     * @param type
     * @return
     */
    public static String changeMySqlType2JavaType(String type) {
    String typeName = null;
    type = type.toLowerCase();
    if (type.startsWith("varchar")) {
        typeName = "String";
    } else if (type.startsWith("int")) {
        typeName = "Integer";
    } else if (type.startsWith("bigint")) {
        typeName = "Long";
    } else if (type.startsWith("float")) {
        typeName = "Float";
    } else if (type.startsWith("double")) {
        typeName = "Double";
    } else if (type.startsWith("date")) {
        typeName = "Date";
    } else if (type.startsWith("timestamp")) {
        typeName = "Timestamp";
    } else if (type.startsWith("text")) {
        typeName = "String";
    }
    else if (type.startsWith("char")) {
        typeName = "String";
    } else if (type.startsWith("tinyint")) {
        typeName = "Byte";
    }
    return typeName;
}

}
