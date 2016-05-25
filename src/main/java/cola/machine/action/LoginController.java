package cola.machine.action;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cola.machine.util.HttpRequestUtil;
import cola.machine.util.PropertiesUtil;
@Controller
@RequestMapping("/login")
public class LoginController {
    @RequestMapping(value = "/login.htm", method = RequestMethod.GET)
    public String loginGet(HttpServletRequest request) {
        // String s =request.getParameter("s");
        // s.substring(12);
        // logger.debug("s");
        // System.out.println(123);
        String sessionId = request.getRequestedSessionId();
     /*   HashMap map =new HashMap();
        map.put("appid", "calendar");
        map.put("timestamp", new Date().getTime());
        map.put("phone", value);*/
       
        return "/static/html/zlogin.html";
    }
    @RequestMapping(value = "/getPicCode.htm", method = RequestMethod.GET)
    @ResponseBody
    public Object getValidCode(HttpServletRequest request){
        String sessionid = request.getRequestedSessionId();
        
        HashMap map =new HashMap();
        map.put("appid", "calendar");
        map.put("timestamp", new Date().getTime());
        map.put("token", "token");
        map.put("sessionid", sessionid);
        String jsonStr= JSON.toJSONString(map);
         String result="";
        try {
            result = HttpRequestUtil.sendGet(PropertiesUtil.get("valid_server_url")+"/getSms", jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // HashMap  result =(HashMap)JSON.parse(jsonStr,HashMap.class);
        System.out.println(result);
         // result.put("", value);
         return  result;
    }
}
