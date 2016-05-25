/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2016年3月12日
 * 文件说明: 
 */
package cola.machine.util.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cola.machine.util.StringUtil;

public class ControllerFactory extends DefaultGenCodeFactory {
    private static final Logger logger = LoggerFactory.getLogger(ControllerFactory.class);
    HashMap<String, ZTable> allTable;
    private Map root ;
    public ControllerFactory(HashMap<String, ZTable> allTable,Map root) {
        this.allTable = allTable;
        this.root=root;
    }

    public void genCode(String name) {
        logger.info("genController");
        root.put("idvalid", getIdValid(allTable.get(name)));
        root.put("getSearchParam", getSearchParam(allTable.get(name)));
        root.put("setParam",  getSetParam(allTable.get(name)));
        root.put("validCode", getValidStr(allTable.get(name)));
        root.put("controllerViewMethod", getCtrlViewM(allTable.get(name)));
    }
    public  String getIdValid(ZTable table ){
        this.tabNo=2;
        ZColum zcol =table.getPk();
        String type =zcol.getType().toLowerCase();
        List rules =new ArrayList();
        if(type.startsWith("varchar") ||type.startsWith("char")  ){
            int length=Integer.valueOf(type.substring(type.indexOf("(")+1, type.indexOf(")")));
            rules.add(String.format("new Length(%d)", length));
        }
        if(type.startsWith("int")||type.startsWith("tinyint") ){
            Integer integer=GenCodeHelper.getIntFromKuoHao(type);
            if(integer!=null){
                rules.add(String.format("new Digits("+integer+",0)"));
            }else{
                rules.add(String.format("new Digits(10,0)"));
            }
        }
        if(StringUtil.isNotEmpty(zcol.getValid())){
            String[] validAry= zcol.getValid().split(",,");
            for(int j=0;j<validAry.length;j++){
                if(validAry[j].toLowerCase().startsWith("regex")){
                    String content=StringUtil.getContentBetween(validAry[j], "(", ")");
                    content=content.replace("\\", "\\\\");
                    rules.add(String.format("new Regex(%s)",content));
                }
                if(validAry[j].toLowerCase().startsWith("email")){
                    String content=StringUtil.getContentBetween(validAry[j], "(", ")");
                    rules.add(String.format("new EmailRule(%s)",content));
                }
                if(validAry[j].toLowerCase().startsWith("phone")){
                    String content=StringUtil.getContentBetween(validAry[j], "(", ")");
                    rules.add(String.format("new PhoneRule(%s)",content));
                }
                if(validAry[j].toLowerCase().startsWith("money")){
                    String content=StringUtil.getContentBetween(validAry[j], "(", ")");
                    rules.add(String.format("new MoneyRule(%s)",content));
                }
                
            }
        }
        StringBuffer sb =new StringBuffer();
        String ruleStr=StringUtil.join(",",rules.toArray());
        ruleStr=" new Rule[]{"+ruleStr+"}";
        line(sb,"vu.add(\""+zcol.getName()+"\", "+zcol.getName()+", \""+zcol.getRemark()+"\", "+ruleStr+");");
       return sb.toString();
    }
    private String getCtrlViewM(ZTable table) {
        this.tabNo=2;
        StringBuffer sb =new StringBuffer();
        line(sb,"String id = request.getParameter(\"id\");");
        line(sb,"HashMap<String,Object> result =new HashMap<String,Object>();");
        line(sb,"if(!StringUtil.isBlank(id)){");
        lineForw(sb,StringUtil.getAbc(table.getName())+" bean = "+StringUtil.getabc(table.getName())+"Service.selectByPrimaryKey("+GenCodeHelper.changeMySqlType2JavaType(table.getPk().getType())+".valueOf(id));");
        line(sb,"result.put(\"bean\", bean);");
            if(table.getMapper()!=null&& table.getName().equals(table.getMapper().getParent())){
                line(sb,"HashMap<String,String> params =new HashMap<String,String>();");
                line(sb,"params.put(\""+table.getMapper().getChild()+"\",id);");
                line(sb,"List<"+StringUtil.getAbc(table.getMapper().getMapper())+"> childMaps ="+StringUtil.getabc(table.getMapper().getMapper())+"Service.listByParams(new HashMap<String,String>());");
                line(sb,"result.put(\"childMaps\", childMaps);");
            }
            lineBack(sb,"}");
        if(table.getMapper()!=null&& table.getName().equals(table.getMapper().getParent())){
            line(sb,"List<"+StringUtil.getAbc(table.getMapper().getChild())+"> childs ="+StringUtil.getabc(table.getMapper().getChild())+"Service.listByParams(new HashMap<String,String>());");
            line(sb,"result.put(\"childs\", childs);");
        }
        line(sb,"return this.getResult(result);");
        return sb.toString();
    }
    public String getValidStr(ZTable table){
        StringBuffer sb=new StringBuffer();
        StringBuffer jssb=new StringBuffer();
        StringBuffer jsmsg=new StringBuffer();
        this.tabNo=2;
        line(sb,"ValidateUtil vu = new ValidateUtil();");
     
          line(sb,"String validStr=\"\";");
       for(int i=0;i<table.getCols().size();i++){
           ZColum zcol =table.getCols().get(i); 
           String type = zcol.getType().toLowerCase();
         
           List rules =new ArrayList();
           List jsrules =new ArrayList();
           List message=new ArrayList();
           if(type.startsWith("varchar")||type.startsWith("char")){
               int length=Integer.valueOf(type.substring(type.indexOf("(")+1, type.indexOf(")")));
               rules.add(String.format("new Length(%d)", length));
               jsrules.add(String.format("maxlength:%d", length));
               message.add(String.format("maxlength:\"%s不能多于%d个字符\"", zcol.getName(),length));
           }
           if(type.startsWith("int")||type.startsWith("bigint")||type.startsWith("tinyint")){
               Integer integer=GenCodeHelper.getIntFromKuoHao(type);
               if(integer!=null){
                   rules.add(String.format("new Digits("+integer+",0)"));
               }else{
                   rules.add(String.format("new Digits(10,0)"));
               }
               if(zcol.getShowValue()!=null){
                   Map<Integer, String> map = zcol.getShowValue();  
                   List strAry= new ArrayList();
                   for (Map.Entry<Integer, String> entry : map.entrySet()) {  
                       strAry.add("\""+entry.getKey() +"\"");
                   }  
                   
                   rules.add(String.format("new CheckBox(new String[]{"+StringUtil.join(",", strAry.toArray())+"})"));
                   jsrules.add("CheckBox:["+StringUtil.join(",", strAry.toArray())+"]");
                   message.add("CheckBox:\"必须输入"+StringUtil.join(",", strAry.toArray()).replaceAll("\"", "'")+"中的值\"");
               }
               jsrules.add(String.format("digits:true"));
               message.add(String.format("digits:\"必须输入整数\""));
           }
           if(type.startsWith("float")){
               int integer=Integer.valueOf(type.substring(type.indexOf("(")+1, type.indexOf(",")));
               int fraction=Integer.valueOf(type.substring(type.indexOf(",")+1, type.indexOf(")")));
               rules.add(String.format("new Digits(%d,%d)",integer,fraction));
               jsrules.add(String.format("number:true"));
               message.add(String.format("number:\"必须输入合法的数字（负数，小数）\""));
           }
           if(type.startsWith("double")){
               int integer=Integer.valueOf(type.substring(type.indexOf("(")+1, type.indexOf(",")));
               int fraction=Integer.valueOf(type.substring(type.indexOf(",")+1, type.indexOf(")")));
               rules.add(String.format("new Digits(%d,%d)",integer,fraction));
               jsrules.add(String.format("number:true"));
               message.add(String.format("number:\"必须输入合法的数字（负数，小数）\""));
           }
           if(type.equals("date")){
               rules.add(String.format("new DateValue(\"yyyy-MM-dd\")"));
               jsrules.add(String.format("dateISO:true"));
               message.add(String.format("dateISO:\"必须输入合法日期\""));
           }
           if(type.equals("datetime")){
               rules.add(String.format("new DateValue(\"yyyy-MM-dd HH:mm:ss\")"));
               jsrules.add(String.format("ymd:\"yyyy-MM-dd HH:mm:ss\""));
               message.add(String.format("ymd:\"必须输入合法日期\""));
           }
           if(type.equals("timestamp")){
               rules.add(String.format("new DateValue(\"yyyy-MM-dd HH:mm:ss\")"));
               jsrules.add(String.format("ymd:\"yyyy-MM-dd HH:mm:ss\""));
               message.add(String.format("ymd:\"必须输入合法日期\""));
           }
           if(zcol.isNn()){
               rules.add(String.format("new NotEmpty()"));
               jsrules.add(String.format("required:true"));
           }
           if(StringUtil.isNotEmpty(zcol.getValid())){
               String[] validAry= zcol.getValid().split(",,");
               for(int j=0;j<validAry.length;j++){
                   if(validAry[j].toLowerCase().startsWith("regex")){
                       String content=StringUtil.getContentBetween(validAry[j], "(", ")");
                       content=content.replace("\\", "\\\\");
                       rules.add(String.format("new Regex(%s)",content));
                       jsrules.add(String.format("regex:"+content));
                       message.add(String.format("regex:\"必须输入制定格式字符串\""));
                   }
                   if(validAry[j].toLowerCase().startsWith("email")){
                      // String content=StringUtil.getContentBetween(validAry[j], "(", ")");
                       rules.add(String.format("new EmailRule()"));
                       jsrules.add(String.format("email:true"));
                       //message.add(String.format("email:\"请输入正确邮箱地址\""));
                   }
                   if(validAry[j].toLowerCase().startsWith("phone")){
                       //String content=StringUtil.getContentBetween(validAry[j], "(", ")");
                       rules.add(String.format("new PhoneRule()"));
                       jsrules.add(String.format("phone:true"));
                   }
                   if(validAry[j].toLowerCase().startsWith("money")){
                      // String content=StringUtil.getContentBetween(validAry[j], "(", ")");
                       rules.add(String.format("new MoneyRule()"));
                       jsrules.add(String.format("money:true"));
                   }
                   
                   if(validAry[j].toLowerCase().startsWith("alpha_numeric")){
                       rules.add(String.format("new AlphaNumbericUnderlineRule()"));
                       jsrules.add(String.format("alphanumeric:true"));
                   }
                   if(validAry[j].toLowerCase().startsWith("alphanumeric")){
                       rules.add(String.format("new AlphaNumericRule()"));
                       jsrules.add(String.format("alphanumeric:true"));
                       message.add(String.format("regex:\"只能输入字母和数字\""));
                   }
                   if(validAry[j].toLowerCase().startsWith("alpha")){
                       rules.add(String.format("new AlphaRule()"));
                       jsrules.add(String.format("alpha:true"));
                       message.add(String.format("alpha:\"只能输入字母\""));
                   }
                   if(validAry[j].toLowerCase().startsWith("idcard")){
                       rules.add(String.format("new IDCardRule()"));
                       jsrules.add(String.format("IDCard:true"));
                       message.add(String.format("IDCard:\"身份证格式不正确\""));
                   }
               }
           }
           String ruleStr=StringUtil.join(",",rules.toArray());
           ruleStr=" new Rule[]{"+ruleStr+"}";
           line(sb,"vu.add(\""+zcol.getName()+"\", "+zcol.getName()+", \""+zcol.getRemark()+"\", "+ruleStr+");");
           line(jssb,zcol.getName()+":{");
           lineForw(jssb,StringUtil.join(",",jsrules.toArray()));
           lineBack(jssb,"},");
           line(jsmsg,zcol.getName()+":{");
           lineForw(jsmsg,StringUtil.join(",",message.toArray()));
           lineBack(jsmsg,"},");
       }
       sb.append(tab2+"validStr = vu.validateString();").append(ctrl);
       sb.append(tab2+"if(StringUtil.isNotEmpty(validStr)) {").append(ctrl);
       sb.append(tab3+String.format("return ResultUtil.getResult(%d,%s);",302,"validStr")).append(ctrl);
       sb.append(tab2+"}").append(ctrl);
       root.put("jsrules", jssb.toString());
        root.put(table.getName()+"jsrules", jssb.toString());
        if(table.getMapper()!=null && table.getMapper().getMapper().equals(table.getName())){

            root.put("parentjsrules",root.get(table.getMapper().getParent()+"jsrules"));
            root.put("parentjsmsg",root.get(table.getMapper().getParent()+"jsmsg"));
        }
       root.put("jsmsg", jsmsg.toString());
        root.put(table.getName()+"jsmsg", jsmsg.toString());
      return sb.toString();
        
    }
    public String getSetParam(ZTable table){
        this.tabNo=2;
        StringBuffer sb =new StringBuffer();
        for(int i=0;i<table.getCols().size();i++){
            ZColum zcol =table.getCols().get(i); 
            String type = zcol.getType().toLowerCase();
            
            line(sb,  "String " + zcol.getName() + " = request.getParameter(\"" + zcol.getName() + "\");");
            line(sb, "if(!StringUtil.isBlank(" + zcol.getName() + ")){");
            if(type.startsWith("date")||type.startsWith("timestamp")||type.startsWith("datetime")){
                lineForw(sb, "if(StringUtil.checkNumeric(" + zcol.getName() + ")){");
                if(type.startsWith("date")){
                    lineForw(sb, String.format("%s.set%s(%s);",StringUtil.getabc(table.getName()),StringUtil.getAbc(zcol.getName()), "new Date("+zcol.getName()+")"));
                }else{
                    lineForw(sb, String.format("%s.set%s(%s);",StringUtil.getabc(table.getName()),StringUtil.getAbc(zcol.getName()),  GenCodeHelper.changeMySqlType2JavaType(type)+".valueOf("+zcol.getName()+")"));
                }
                lineBack(sb,"}else if(StringUtil.checkDateStr(" + zcol.getName() + ", \"" + StringUtil.getYMDStr(type) + "\")){");
                lineForw(sb, String.format("%s.set%s(%s);",StringUtil.getabc(table.getName()),StringUtil.getAbc(zcol.getName()), GenCodeHelper.changeStrVar2BeanType(type, zcol.getName())));
                 lineBack(sb,"}");
            }else if(type.startsWith("long")||type.startsWith("bigint")||type.startsWith("tinyint")){
                lineForw(sb, String.format("%s.set%s(%s);",StringUtil.getabc(table.getName()),StringUtil.getAbc(zcol.getName()),  GenCodeHelper.changeMySqlType2JavaType(type)+".valueOf("+zcol.getName()+")"));
            }else{
                lineForw(sb, String.format("%s.set%s(%s);",StringUtil.getabc(table.getName()),StringUtil.getAbc(zcol.getName()), GenCodeHelper.changeStrVar2BeanType(type, zcol.getName())));
            }
            lineBack(sb,"}");
        }
           return sb.toString();
    }
    
    /**
     * 在controller 中提取参数 并设置到map中
     * 
     * @return
     * @author dozen.zhang
     */
    public String getSearchParam(ZTable table) {
        this.tabNo=2;
        StringBuffer sb = new StringBuffer();
        line(sb,"HashMap<String,Object> params= new HashMap<String,Object>();");
        for (int i = 0; i < table.getCols().size(); i++) {
            ZColum zcol = table.getCols().get(i);
            String type = zcol.getType().toLowerCase();
            line(sb,  "String " + zcol.getName() + " = request.getParameter(\"" + zcol.getName() + "\");");
            line(sb, "if(!StringUtil.isBlank(" + zcol.getName() + ")){");
            if (type.startsWith("date") || type.startsWith("timestamp") || type.startsWith("datetime")) {
                lineForw(sb, "if(StringUtil.checkNumeric(" + zcol.getName() + ")){");
                lineForw(sb, String.format("params.put(\"%s\",%s);", zcol.getName(), zcol.getName()));
                lineBack(sb,"}else if(StringUtil.checkDateStr(" + zcol.getName() + ", \"" + StringUtil.getYMDStr(type) + "\")){");
                lineForw(sb,String.format("params.put(\"%s\",%s);", zcol.getName(),  GenCodeHelper.changeStrVar2BeanType(type, zcol.getName())));
                lineBack(sb,"}");
            } else {
                lineForw(sb, String.format("params.put(\"%s\",%s);",zcol.getName(),zcol.getName()) );
            }
            lineBack(sb,"}");

            if (type.startsWith("date") || type.startsWith("timestamp") || type.startsWith("datetime")) {
                line(sb,  "String " + zcol.getName() + "Begin = request.getParameter(\"" + zcol.getName() + "Begin\");");
                line(sb, "if(!StringUtil.isBlank(" + zcol.getName() + "Begin)){");
                lineForw(sb, "if(StringUtil.checkNumeric(" + zcol.getName() + "Begin)){");
                lineForw(sb, String.format("params.put(\"%sBegin\",%sBegin);", zcol.getName(), zcol.getName()));
                lineBack(sb,"}else if(StringUtil.checkDateStr(" + zcol.getName() + "Begin, \"" + StringUtil.getYMDStr(type) + "\")){");
                lineForw(sb,String.format("params.put(\"%sBegin\",%s);", zcol.getName(),  GenCodeHelper.changeStrVar2BeanType(type, zcol.getName()+"Begin")));
                lineBack(sb,"}");
                lineBack(sb,"}");
                
                
                line(sb,  "String " + zcol.getName() + "End = request.getParameter(\"" + zcol.getName() + "End\");");
                line(sb, "if(!StringUtil.isBlank(" + zcol.getName() + "End)){");
                lineForw(sb, "if(StringUtil.checkNumeric(" + zcol.getName() + "End)){");
                lineForw(sb, String.format("params.put(\"%sEnd\",%sEnd);", zcol.getName(), zcol.getName()));
                lineBack(sb,"}else if(StringUtil.checkDateStr(" + zcol.getName() + "End, \"" + StringUtil.getYMDStr(type) + "\")){");
                lineForw(sb,String.format("params.put(\"%sEnd\",%s);", zcol.getName(),  GenCodeHelper.changeStrVar2BeanType(type, zcol.getName()+"End")));
                lineBack(sb,"}");
                lineBack(sb,"}");
            }else if(type.startsWith("char") || type.startsWith("varchar")){
                line(sb,  "String " + zcol.getName() + "Like = request.getParameter(\"" + zcol.getName() + "Like\");");
                line(sb, "if(!StringUtil.isBlank(" + zcol.getName() + "Like)){");
                lineForw(sb, String.format("params.put(\"%sLike\",%sLike);", zcol.getName(), zcol.getName()));
                lineBack(sb,"}");
            }

        }

        return sb.toString();
    }
}
