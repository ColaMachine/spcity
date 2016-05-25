/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2016年3月13日
 * 文件说明: 
 */
package cola.machine.util;

import java.io.*;

import cola.machine.mng.PathManager;

public class FileUtil {
    public static String readFile2Str(String path) throws IOException {
        File file = PathManager.getInstance().getHomePath().resolve(path).toFile();

        BufferedReader br = new BufferedReader(new FileReader(file));
        String s;
        StringBuffer templateStr = new StringBuffer();
        while ((s = br.readLine()) != null) {
            templateStr.append(s + "\r\n");
        }
        return templateStr.toString();
    }

    public static void main(String args[]){
try {
    FileReader fr = new FileReader("G://V2.2.7.txt");
    BufferedReader br = new BufferedReader(fr);
    String s;
    String name="";
    StringBuffer result =new StringBuffer();
    while ((s = br.readLine()) != null) {
       // System.out.println(s);
        //System.out.println(s.split("\\s+").length);
        String arr[] = s.split("\\s+");
        System.out.println(arr[0]);
        if(name.equals("")){
             name=arr[arr.length-1];
        }else if(arr[arr.length-1].trim().equals(name.trim())){

            result.append("'").append(arr[0]).append("',");

        }else{

           // System.out.println(name+"select distinct  IPAddress from wii_device_ssid where deviceid in(select id from wii_device where DevId in ("+result.toString()+"))");
            result=new StringBuffer();
            name=arr[arr.length-1];
        }
        if(arr.length==3){

        }

    }
   // System.out.println(name+"select distinct  IPAddress from wii_device_ssid where id in(select id from wii_device where DevId in ("+result.toString()+")");
    fr.close();
}catch(Exception e){
    e.printStackTrace();
}
    }
}
