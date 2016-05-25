/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2016年3月13日
 * 文件说明: 
 */
package cola.machine.util.code;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import cola.machine.mng.PathManager;
import cola.machine.util.FileUtil;
import cola.machine.util.StringUtil;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerFactory {
    Path homePath = PathManager.getInstance().getHomePath();
    private Configuration cfg  = new Configuration();
    StringTemplateLoader temp = new StringTemplateLoader();
    public void init(){
       
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateLoader(temp);
    }
    public void loadTemp(String name ,String path) throws IOException{
        String tplStr = FileUtil.readFile2Str(path);
        temp.putTemplate(name, tplStr);
       
    }
    
    public void writeFile(Map root,String path,String name, String templateName) throws IOException, TemplateException {
        Template template;
        template = cfg.getTemplate(templateName,"UTF-8");
        homePath.resolve("temp").resolve(path).toFile().mkdirs();
        File file = homePath.resolve("temp").resolve(path).resolve(StringUtil.getAbc(name)).toFile();
        // Files.createFile(homePath.resolve(StringUtil.getAbc(table.getName())));
        FileWriter writer = new FileWriter(file);
        template.process(root, writer);
        writer.flush();
        writer.close();
        System.out.println(writer.toString());
    }
}
