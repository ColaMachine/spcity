/**
 * 
 */
package cola.machine.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * json序列化及反序列化工具类。
 * @author awifi-core
 * @date 2015年1月7日 上午11:47:42
 */
public class JsonUtils {

	private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtils() {
    }

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    /**
     * 使用Jackson 数据绑定 将对象转换为 json字符串 还可以 直接使用 JsonUtils.getInstance().writeValueAsString(Object obj)方式
     * 
     * @param obj
     * @return
     */
    public static String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonGenerationException e) {
            logger.error("转换为json字符串失败" + e.toString());
        } catch (JsonMappingException e) {
            logger.error("转换为json字符串失败" + e.toString());
        } catch (IOException e) {
            logger.error("转换为json字符串失败" + e.toString());
        }
        return null;
    }

    /**
     * json字符串转化为 JavaBean 还可以直接JsonUtils.getInstance().readValue(String content,Class valueType)用这种方式
     * 
     * @param <T>
     * @param content
     * @param valueType
     * @return
     */
    public static <T> T toJavaBean(String content, Class<T> valueType) {
        try {
            return objectMapper.readValue(content, valueType);
        } catch (JsonParseException e) {
            logger.error("json字符串转化为 javabean失败" + e.toString());
        } catch (JsonMappingException e) {
            logger.error("json字符串转化为 javabean失败" + e.toString());
        } catch (IOException e) {
            logger.error("json字符串转化为 javabean失败" + e.toString());
        }
        return null;
    }

    /**
     * json字符串转化为list 还可以 直接使用 JsonUtils.getInstance().readValue(String content, new TypeReference<List<T>>(){})方式
     * 
     * @param <T>
     * @param content
     * @param valueType
     * @return
     * @throws IOException
     */
    public static <T> List<T> toJavaBeanList(String content, TypeReference<List<T>> typeReference) throws IOException {
        try {
            return objectMapper.readValue(content, typeReference);
        } catch (JsonParseException e) {
            logger.error("json字符串转化为 list失败,原因:" + e.toString());
            throw new RuntimeException("json字符串转化为 list失败");
        } catch (JsonMappingException e) {
            logger.error("json字符串转化为 list失败,原因" + e.toString());
            throw new JsonMappingException("json字符串转化为 list失败");
        } catch (IOException e) {
            logger.error("json字符串转化为 list失败,原因" + e.toString());
            throw new IOException("json字符串转化为 list失败");
        }
    }

    /**
     * 将一个参数Map转换成JSON字符串
     */
    public static String toJsonStringByMap(Map<String, List<String>> param) {
        if (param == null || param.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder("{");

        for (Entry<String, List<String>> p : param.entrySet()) {
            String key = p.getKey();
            List<String> vals = p.getValue();

            if (key.equals("msgId")) {
                sb.append("\"msgId\":").append(converter(vals.get(0))).append(",");
            } else if (key.equals("sender")) {
                sb.append("\"sender\":").append(converter(vals.get(0))).append(",");
            } else if (key.equals("createTime")) {
                sb.append("\"createTime\":").append(converter(vals.get(0))).append(",");
            } else if (key.equals("liveTime")) {
                sb.append("\"liveTime\":").append(converter(Integer.valueOf(vals.get(0)))).append(",");
            } else if (key.equals("receivers")) {
                sb.append("\"receivers\":").append(converter(vals)).append(",");
            } else if (key.equals("stationCodes")) {
                sb.append("\"stationCodes\":").append(converter(vals)).append(",");
            } else if (key.equals("StationTypes")) {
                sb.append("\"stationTypes\":").append(converter(vals)).append(",");
            } else if (key.equals("blackList")) {
                sb.append("\"blackList\":").append(converter(vals)).append(",");
            } else if (key.equals("blackListType")) {
                sb.append("\"blackListType\":").append(converter(vals.get(0))).append(",");
            } else if (key.equals("msgType")) {
                sb.append("\"msgType\":").append(converter(vals.get(0))).append(",");
            } else if (key.equals("format")) {
                sb.append("\"format\":").append(converter(vals.get(0))).append(",");
            } else if (key.equals("title")) {
                sb.append("\"title\":").append(converter(vals.get(0))).append(",");
            } else if (key.equals("content")) {
                sb.append("\"content\":").append(converter(vals.get(0))).append(",");
            } else if (key.equals("saveOffline")) {
                sb.append("\"saveOffline\":").append(converter(vals.get(0))).append(",");
            }
        }

        int lastCommaIndex = sb.lastIndexOf(",");
        sb.replace(lastCommaIndex, lastCommaIndex + 1, "");

        sb.append("}");

        return sb.toString();
    }

    /**
     * 转换器
     */
    private static String converter(String value) {

        return value == null ? "null" : ("\"" + value + "\"");
    }

    private static String converter(int value) {

        return String.valueOf(value);
    }

    private static String converter(List<String> values) {
        if (values == null || values.isEmpty()) {
            return "null";
        }

        StringBuilder sb = new StringBuilder("[");

        for (int i = 0, len = values.size(); i < len; i++) {
            sb.append(converter(values.get(i)));

            if (i < len - 1) {
                sb.append(",");
            }
        }
        sb.append("]");

        return sb.toString();
    }

    private static String converter(boolean value) {

        return String.valueOf(value);
    }

}
