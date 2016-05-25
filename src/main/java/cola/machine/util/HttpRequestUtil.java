package cola.machine.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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

import com.alibaba.fastjson.JSON;

public class HttpRequestUtil {
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
    public static String sendGet(String url, String param) {
        StringBuffer result = new StringBuffer("");
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            // System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
            return "400";
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer("");
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-type", "text/html");
            
            conn.setRequestProperty("Accept-Encoding", "identity");
            
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            out.write(param);
            // flush输出流的缓冲
            out.flush();
            out.close();
            // 定义BufferedReader输入流来读取URL的响应
            if (conn.getResponseCode() != 200) {
                return "400";
            }
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            conn.disconnect();
        } catch (Exception e) {
            // //System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
            return "400";
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * 多个参数post
     * 
     * @author syf
     * @creationDate. 2015年7月14日 下午1:49:17
     * @param surl 参数
     * @param params 参数
     * @return String
     * @throws MalformedURLException 异常
     * @throws IOException 抛出IO异常
     * @throws UnsupportedEncodingException 编码异常
     */
    public static String sendPost(String surl, String[] params)
            throws MalformedURLException, IOException,
            UnsupportedEncodingException {
        URL url = new URL(surl);
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        OutputStreamWriter outputstream = new OutputStreamWriter(
                connection.getOutputStream(), "UTF-8");
        StringBuffer param = new StringBuffer("");
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                param.append(params[i]);
            }
            outputstream.write(param.toString()); // post的关键所在！
        }
        // remember to clean up
        outputstream.flush();
        outputstream.close();
        // 一旦发送成功，用以下方法就可以得到服务器的回应：
        String sCurrentLine = "";
        StringBuffer sTotalString = new StringBuffer("");
        InputStream l_urlStream = connection.getInputStream();
        // 传说中的三层包装阿！
        InputStreamReader reader = new InputStreamReader(l_urlStream);
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(
                l_urlStream));
        while ((sCurrentLine = l_reader.readLine()) != null) {
            sTotalString.append(sCurrentLine + "\r\n");
        }
        reader.close();
        l_reader.close();
        l_urlStream.close();
        // //System.out.println(sTotalString);
        return sTotalString.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost2(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     * 
     * @param url
     *            请求的URL地址
     * @param params
     *            请求的查询参数,可以为null
     * @param charset
     *            字符集
     * @param pretty
     *            是否美化
     * @return 返回请求响应的HTML
     */
    public static String doPost(String url, Map<String, String> params,
            String charset, boolean pretty) {
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        HttpMethod method = new PostMethod(url);
        // 设置Http Post数据
        if (params != null) {
            HttpMethodParams p = new HttpMethodParams();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                p.setParameter(entry.getKey(), entry.getValue());
            }
            method.setParams(p);
        }
        try {
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(method.getResponseBodyAsStream(),
                                charset));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (pretty) {
                        response.append(line).append(
                                System.getProperty("line.separator"));
                    } else {
                        response.append(line);
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return response.toString();
    }

    /**
     * @param url 参数
     * @param params 参数
     */
    public static void postForm(String url, Map<String, String> params) {
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        // 创建参数队列
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            formparams.add(new BasicNameValuePair(entry.getKey(), entry
                    .getValue()));
        }
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    System.out
                            .println("--------------------------------------");
                    System.out.println("Response content: "
                            + EntityUtils.toString(entity, "UTF-8"));
                    System.out
                            .println("--------------------------------------");
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param params
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost3(String url, Map<String, String> params) {
        OutputStream out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer("");
        System.out.println(JSON.toJSONString(params));
        try {
            byte[] body = ("[" + JSON.toJSONString(params) + "]")
                    .getBytes("utf-8");
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("Content-Type",
                    "applocation/x-www-form-urlencoded; charset=utf-8");
            conn.setRequestProperty("Content-Length",
                    String.valueOf(body.length));
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = conn.getOutputStream();
            // 发送请求参数
            out.write(body);
            // flush输出流的缓冲
            out.flush();
            out.close();
            // 定义BufferedReader输入流来读取URL的响应
            System.out.println(conn.getResponseCode());
            if (conn.getResponseCode() != 200) {
                return "400";
            }
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            System.out.println(result.toString());
            conn.disconnect();
        } catch (Exception e) {
            // //System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
            return "400";
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

}
