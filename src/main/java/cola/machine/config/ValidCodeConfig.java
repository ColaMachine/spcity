package cola.machine.config;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * @author dozen.zhang
 *
 */
public class ValidCodeConfig {
    private String serverUrl;
    public String getServerUrl() {
        return serverUrl;
    }
    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
    private int smsLength;
    private int imgLength;
    public int getSmsLength() {
        return smsLength;
    }
    public void setSmsLength(int smsLength) {
        this.smsLength = smsLength;
    }
    public int getImgLength() {
        return imgLength;
    }
    public void setImgLength(int imgLength) {
        this.imgLength = imgLength;
    }
    public int getSmsCharType() {
        return smsCharType;
    }
    public void setSmsCharType(int smsCharType) {
        this.smsCharType = smsCharType;
    }
    public int getImgCharType() {
        return imgCharType;
    }
    public void setImgCharType(int imgCharType) {
        this.imgCharType = imgCharType;
    }
    public int getSmsLiveTime() {
        return smsLiveTime;
    }
    public void setSmsLiveTime(int smsLiveTime) {
        this.smsLiveTime = smsLiveTime;
    }
    public int getImgLiveTime() {
        return imgLiveTime;
    }
    public void setImgLiveTime(int imgLiveTime) {
        this.imgLiveTime = imgLiveTime;
    }
    public HashMap<String, SystemValidCodeConfig> getSystems() {
        return systems;
    }
    public void setSystems(HashMap<String, SystemValidCodeConfig> systems) {
        this.systems = systems;
    }
    private int smsCharType;
    private int imgCharType;
    private int smsLiveTime;
    private int imgLiveTime;
    private int smsRefreshTime;
    private int imgRefreshTime;
    private HashMap<String,SystemValidCodeConfig> systems;
    public int getSmsRefreshTime() {
        return smsRefreshTime;
    }
    public void setSmsRefreshTime(int smsRefreshTime) {
        this.smsRefreshTime = smsRefreshTime;
    }
    public int getImgRefreshTime() {
        return imgRefreshTime;
    }
    public void setImgRefreshTime(int imgRefreshTime) {
        this.imgRefreshTime = imgRefreshTime;
    }
  
}
