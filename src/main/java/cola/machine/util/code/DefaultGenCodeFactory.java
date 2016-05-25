/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2016年3月12日
 * 文件说明: 
 */
package cola.machine.util.code;

public class DefaultGenCodeFactory {
    protected String ctrl = "\r\n";
    protected String tab = "    ";
    protected String tab2 = tab+tab;
    protected String tab3 = tab2+tab;
    protected String tab4 = tab2+tab2;
    protected int tabNo;
    public void line(StringBuffer sb,String str){
        for(int i=0;i<tabNo;i++){
            sb.append(tab);
        }
        sb.append(str).append(ctrl);
    }
    public void lineForw(StringBuffer sb,String str){
        tabNo++;
        line( sb, str);
    }
    public void lineBack(StringBuffer sb,String str){
        tabNo--;
        line( sb, str);
    }
    
}
