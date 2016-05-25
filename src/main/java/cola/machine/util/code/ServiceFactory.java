/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2016年3月12日
 * 文件说明: 
 */
package cola.machine.util.code;

import cola.machine.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceFactory extends DefaultGenCodeFactory {
    private static final Logger logger = LoggerFactory.getLogger(ServiceFactory.class);
    HashMap<String, ZTable> allTable;
    ZTable table ;
    private Map root ;
    public ServiceFactory(HashMap<String, ZTable> allTable, Map root) {
        this.allTable = allTable;
        this.root=root;
    }
    public void getConfilictJudge(String name){
        int count =0;
        StringBuffer sb = new StringBuffer();
        ZTable table = allTable.get(name);
        tabNo=2;
        line(sb,"HashMap params =new HashMap();");


        List<ZColum> colums =table.getCols();
        for(int i=0;i<colums.size();i++){
            ZColum col = colums.get(i);
            if(col.isUq()){
                count++;
                line(sb,"params.put(\""+col.getName()+"\","+StringUtil.getabc(table.getName())+".get"+ StringUtil.getAbc(col.getName())+"());");
            }
        }
        if(count>0){
            line(sb,"int count = "+StringUtil.getabc(table.getName())+"Mapper.countByOrParams(params);");
            String nullValue ="null";
            line(sb,"if(StringUtil.isNull("+StringUtil.getabc(table.getName())+".get"+StringUtil.getAbc(table.getPk().getName())+"())&& count>0||count>1 ){");
            lineForw(sb,"return ResultUtil.getResult(302,\"字段唯一不能重复\");");
            lineBack(sb,"}");
            root.put("distinctCheck",sb.toString());
            System.out.println(table.getName()+sb.toString());
        }else{
            root.put("distinctCheck","");
        }

    }
    public void getService(String name) {
        logger.info("getService");
        this.table=allTable.get(name);

        if(table.getMapper()!=null && table.getMapper().getMapper().equals(table.getName())){
            StringBuffer sb =new StringBuffer();
            ZColum parentCol=GenCodeHelper.getColFromCols(table.getCols(),table.getMapper().getParentid());
            ZColum childCol=GenCodeHelper.getColFromCols(table.getCols(),table.getMapper().getChildid());
            GenCodeHelper.changeMySqlType2JavaType(parentCol.getType());
            root.put("parentCol",parentCol);
            root.put("childCol",childCol);

            root.put("parentType",GenCodeHelper.changeMySqlType2JavaType(parentCol.getType()));
            root.put("childType",GenCodeHelper.changeMySqlType2JavaType(childCol.getType()));

        }

    }

}
