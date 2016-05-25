package cola.machine.config;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cola.machine.mng.PathManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author dozen.zhang
 *
 */
public class Config {

    private ValidCodeConfig validCode;
    public ValidCodeConfig getValidCode() {
        return validCode;
    }

    public void setValidCode(ValidCodeConfig validCode) {
        this.validCode = validCode;
    }


    /**
     * 短信每次发送量
     */
    private int pvSmsSendAmount;
    public int getPvSmsSendAmount() {
        return pvSmsSendAmount;
    }

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
    public static  int a ;
    public static  String b;
    public static  Double c ;
    public static  Integer d;
    /**
     * 系统日志配置
     */
    private SystemConfig system = new SystemConfig();
    /**
     * 缓存配置
     */
    private CacheConfig cache = new CacheConfig();
    public void setPvSmsSendAmount(int pvSmsSendAmount) {
        this.pvSmsSendAmount = pvSmsSendAmount;
    }

    public void setSystem(SystemConfig system) {
        this.system = system;
    }

    public void setCache(CacheConfig cache) {
        this.cache = cache;
    }

    public void setImage(ImageConfig image) {
        this.image = image;
    }


    /**
     * 图片服务配置
     */
    private ImageConfig image = new ImageConfig();

    public ImageConfig getImage() {
        return image;
    }

    /**
     * 单例 配置
     */
    private static Config CONFIG;

    /**
     * 单例配置获取
     * @return Config 
     */
    public static Config getInstance() {
        if (CONFIG == null) {
            try {
                CONFIG = Config.load(Config.getConfigFile());
            } catch (IOException e) {
                LOGGER.error("config load error 配置文件初始化报错", e);
                e.printStackTrace();
                assert (false);
            }
        }
        return CONFIG;
    }

    public SystemConfig getSystem() {
        return system;
    }

    public CacheConfig getCache() {
        return cache;
    }

    /**
     * 获取配置文件路径
     * @return String
     */
    public static Path getConfigFile() {
        return PathManager.getInstance().getClassPath().resolve("config.cfg");
    }

    /*
     * public static void save(Path toFile,Config config) throws IOException{
     * try(BufferedWriter writer =Files.newBufferedWriter(toFile,
     * Charset.forName("UTF-8"))){ createGson().toJson(config,writer); } }
     * 
     */

    /**
     * 加载配置文件
     * @param fromFile 参数
     * @return config 对象
     * @throws IOException IO流异常
     */
    public static Config load(Path fromFile) throws IOException {
        LOGGER.info("read config file{}", fromFile);
        try (Reader reader = Files.newBufferedReader(fromFile, Charset.forName("UTF-8"))) {
            Gson gson = createGson();
            Config configOri =new Config();
            JsonElement baseConfig = gson.toJsonTree(configOri);
            JsonParser parser = new JsonParser();
            JsonElement config = parser.parse(reader);
            if (!config.isJsonObject()) {
                return new Config();
            } else {
                merge(baseConfig.getAsJsonObject(), config.getAsJsonObject());
                return gson.fromJson(baseConfig, Config.class);
            }
        } catch (JsonParseException e) {
            throw new IOException("Failed to load config", e);
        }
    }

    /**
     * 两个jsonobject 合并
     * @param target 参数
     * @param from 参数
     */
    public static void merge(JsonObject target, JsonObject from) {
        for (Map.Entry<String, JsonElement> entry : from.entrySet()) {
           // System.out.println(entry.getKey());
            if (entry.getValue().isJsonObject()) {
                boolean bool = target.has(entry.getKey());
                JsonElement ele=target.get(entry.getKey());
                if (target.has(entry.getKey()) && target.get(entry.getKey()).isJsonObject()) {
                    merge(target.get(entry.getKey()).getAsJsonObject(), entry.getValue().getAsJsonObject());
                } else {
                    target.remove(entry.getKey());
                    target.add(entry.getKey(), entry.getValue());
                }
            } else {
                target.remove(entry.getKey());
                target.add(entry.getKey(), entry.getValue());
            }
        }

    }

    /**
     * 如果需要做一下非默认的json转换操作可以放在这里操作
     * @return Object
     */
    public static Gson createGson() {
        return new GsonBuilder().
       
         /* registerTypeAdapter(RedisConfig.class, new RedisConfig.Handler()).*/create();
         
    }
    public static void main(String args[]){
        Config.getInstance();
    }
}
