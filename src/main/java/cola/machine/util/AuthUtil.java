package cola.machine.util;

import cola.machine.bean.SysResource;
import cola.machine.bean.SysUser;
import com.alibaba.fastjson.JSON;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AuthUtil {
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @modificationHistory.
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static boolean hasPermission(HttpServletRequest request, String resource) {
      // SysUser user = request.getSession().getAttribute("user");
        List<SysResource> resourceList = ( List<SysResource>) request.getSession().getAttribute("resourceList");
        if(resourceList.contains(resource))
            return true;
        else
            return false;
    }

    public static void hasPermissionE(HttpServletRequest request, String resource) throws Exception {
        // SysUser user = request.getSession().getAttribute("user");
        List<SysResource> resourceList = ( List<SysResource>) request.getSession().getAttribute("resourceList");
        if(!resourceList.contains(resource))
            throw new Exception("没有权限");
    }

}
