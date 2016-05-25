package cola.machine.util;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;

import org.springframework.stereotype.Component;


@Component
public class SmsUtil {

    /**
     * dao方法
     */
   /* @Resource(name = "daoSupport")
    private DaoSupport dao;*/

    /**
     * @param content 内容
     * @param mobile 手机
     * @param type 类型
     * @return String
     */
    public String sendSms(String content, String mobile, Short type) {
        String url;
        try {
            System.out.println(URLEncoder.encode(content, "UTF-8"));
            url = PropertiesUtil.get( "sms_server_url")
                    + "system/sendCode.htm?cellNumber=" + mobile + "&content="
                    + URLEncoder.encode(content, "UTF-8");
            String result = HttpRequestUtil.sendPost(url, "");
            HashMap<String, Object> sms = new HashMap<String, Object>();
            Date date = new Date();
            sms.put("Content", content);
            sms.put("CreateTime", date.getTime() / 1000);
            sms.put("InfoType", type);
            sms.put("Mobile", mobile);
            sms.put("SendResult", 1);
            if (!result.equals("400")) {
                sms.put("SendStatus", 2);
            } else {
                sms.put("SendStatus", 1);
            }
            sms.put("SendTime", date.getTime() / 1000);
            //dao.save("CrmBussinessEmpolyeeMapper.insertSms", sms);
            if (!result.equals("400")) {
                return "succ";
            } else {
                return "fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }


 

}
