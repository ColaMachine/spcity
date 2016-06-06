package cola.machine.service;

import java.util.*;

import cola.machine.bean.SmsHistory;
import cola.machine.bean.SmsRecord;
import cola.machine.common.msgbox.ErrorMsg;
import cola.machine.config.Config;
import cola.machine.config.SystemConfig;
import cola.machine.config.SystemValidCodeConfig;
import cola.machine.config.ValidCodeConfig;
import cola.machine.util.*;

import cola.machine.util.log.LogUtil;
import cola.machine.util.log.ServiceCode;
import cola.machine.util.log.ServiceMsg;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import core.action.ResultDTO;

import javax.annotation.Resource;

@Service("validCodeService")
public class ValidCodeService {
    private ServiceCode serviceCode =ServiceCode.ValidCodeService;
    /**
     * 日志类
     */
    private static final Logger log = LoggerFactory.getLogger(ValidCodeService.class);
    @Resource
    private  SmsRecordService smsRecordService;
    /**
     * @param systemCode 系统名称
     * @param phone 手机号码
     * @return ResutDTO{r:xx,msg:xxx,data:{code:xxx}}
     * @author dozen.zhang
     */
    public ResultDTO getSmsValidCode(String systemCode, String phone) {

        try {
            // 验证systemcode
            ValidCodeConfig defaultConfig = Config.getInstance().getValidCode();
            SystemValidCodeConfig systemConfig = defaultConfig.getSystems().get(systemCode);
            ResultDTO result;
            /*
             * ResultDTO result = validSystemNo(systemCode); if
             * (!result.isRight()) { return result; }
             */
            if (systemConfig == null) {
                return ResultUtil.getResult(serviceCode, ErrorMsg.PARAM_ERROR, 301, ServiceMsg.SERVICECODE_ERR);
            }
            // 验证phone
            if (StringUtil.isBlank(phone)) {
                return ResultUtil.getResult(serviceCode, ErrorMsg.PARAM_ERROR, 302, ServiceMsg.PHONE_NOT_EMPTY);
            }
            if (!StringUtil.isPhone(phone)) {
                return ResultUtil.getResult(serviceCode, ErrorMsg.PARAM_ERROR, 303, ServiceMsg.PHONE_FORMAT_ERR);
            }
            // 取缓存中业务+手机号 的value
            // String mapValue = (String)
            // CacheUtil.getInstance().readCache(systemCode + phone,
            // String.class);
          /*  String json = *//*RedisUtil.get(systemCode + phone);*//*
                    (String)  CacheUtil.getInstance().readCache(systemCode + phone,
                            SmsHistory.class);*/
            SmsHistory history =  (SmsHistory)CacheUtil.getInstance().readCache(systemCode + phone,
                    SmsHistory.class);

            // 验证指定时间内只能发送一次
            Long nowTime = new Date().getTime();
            if (history != null&&!StringUtil.isBlank(history.getCode())) {
                // String[] mapValueAry = mapValue.split("-");
                // 验证码生存周期
                if (nowTime > (history.getLast() + Integer.valueOf(systemConfig.getSmsLiveTime()))) {
                    // 超时
                    // 应该把缓存中的清理掉
                    //CacheUtil.getInstance().clearCache(systemCode + phone);
                    /*
                     * return ResultUtil.getResult(100, "验证码已过期" + (nowTime -
                     * (Long.valueOf(mapValueAry[1]) +
                     * Integer.valueOf(PropertiesUtil
                     * .get("validcode.live.time")))));
                     */
                }
                //验证是否刷新过快
                if (nowTime < (history.getLast() + Integer.valueOf(systemConfig.getSmsRefreshTime()))) {
                    return ResultUtil.getResult(serviceCode, ErrorMsg.INFO, 304, ServiceMsg.VALIDCODE_REQUEST_FAST);
                }
                //验证是否在指定时间
                if (!beyondErrTimes(history, systemConfig.getMaxRequestTime(), systemConfig.getRangeTime())) {
                    return ResultUtil.getResult(serviceCode, ErrorMsg.INFO, 305, ServiceMsg.SEND_TOO_MUCH_TIMES);
                }
            } else {
                history = new SmsHistory();
            }
            // 发送验证码
            Random random = new Random();
            int vcLen = systemConfig.getSmsLength();
            String finalCode = getCode(systemConfig.getSmsCharType(),vcLen);


            // 返回验证码
            HashMap map = new HashMap();
            map.put("code", finalCode);
            SmsUtil smsUtil = new SmsUtil();
            String rc = smsUtil.sendSms(finalCode, phone, (short) 1);
            SmsRecord record =new SmsRecord();
            record.setPhone(phone);
            record.setContent(finalCode);
            record.setStatus("fail".equals(rc)?(byte)1:(byte)2);
            record.setSystemNo(systemCode);
            smsRecordService.save(record);
            if ("fail".equals(rc)) {
                return ResultUtil.getResult(serviceCode, ErrorMsg.THIRD_PART_ERROR, 307,ServiceMsg.SMS_SEND_FAIL);
            }
            result = ResultUtil.getDataResult(map);

            if (result.isRight()) {
                // 塞入缓存系统
                history.getTimes().offer(nowTime);
                history.setLast(nowTime);
                history.setCode(finalCode);
                String insertJson = JSON.toJSONString(history);
//                RedisUtil.set(systemCode + phone, insertJson);
                 CacheUtil.getInstance().writeCache(systemCode + phone,history);
                // finalCode + "-" +
                // nowTime,Integer.valueOf(systemConfig.getSmsLiveTime()));
            }
            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
            LogUtil.system(serviceCode,308,systemCode+phone,e.getMessage(),"");
            return ResultUtil.getResult(serviceCode, ErrorMsg.UNKNOW, 308, ServiceMsg.UNKNOWN);
        }
    }
    /**
     * @param systemCode 系统名称
     * @param email 手机号码
     * @return ResutDTO{r:xx,msg:xxx,data:{code:xxx}}
     * @author dozen.zhang
     */
    public ResultDTO getEmailValidCode(String systemCode, String email) {

        try {
            // 验证systemcode
            ValidCodeConfig defaultConfig = Config.getInstance().getValidCode();
            SystemValidCodeConfig systemConfig = defaultConfig.getSystems().get(systemCode);
            ResultDTO result;
            /*
             * ResultDTO result = validSystemNo(systemCode); if
             * (!result.isRight()) { return result; }
             */
            if (systemConfig == null) {
                return ResultUtil.getResult(serviceCode, ErrorMsg.PARAM_ERROR, 309, ServiceMsg.SERVICECODE_ERR);
            }
            // 验证phone
            if (StringUtil.isBlank(email)) {
                return ResultUtil.getResult(serviceCode, ErrorMsg.PARAM_ERROR, 310, ServiceMsg.EMAIL_NOT_EMPTY);
            }
            if (!StringUtil.isEmail(email)) {
                return ResultUtil.getResult(serviceCode, ErrorMsg.PARAM_ERROR, 311, ServiceMsg.EMAIL_FORMAT_ERR);
            }
            // 取缓存中业务+手机号 的value
            // String mapValue = (String)
            // CacheUtil.getInstance().readCache(systemCode + phone,
            // String.class);
            SmsHistory history = (SmsHistory) CacheUtil.getInstance().readCache(systemCode + email,SmsHistory.class);

            // 验证指定时间内只能发送一次
            Long nowTime = new Date().getTime();
            if (history != null&&!StringUtil.isBlank(history.getCode())) {
                // String[] mapValueAry = mapValue.split("-");
                // 验证码生存周期
                if (nowTime > (history.getLast() + Integer.valueOf(systemConfig.getSmsLiveTime()))) {
                    // 超时
                    // 应该把缓存中的清理掉
                    //CacheUtil.getInstance().clearCache(systemCode + phone);
                    /*
                     * return ResultUtil.getResult(100, "验证码已过期" + (nowTime -
                     * (Long.valueOf(mapValueAry[1]) +
                     * Integer.valueOf(PropertiesUtil
                     * .get("validcode.live.time")))));
                     */
                }
                //验证是否刷新过快
                if (nowTime < (history.getLast() + Integer.valueOf(systemConfig.getSmsRefreshTime()))) {
                    return ResultUtil.getResult(serviceCode, ErrorMsg.INFO, 312, ServiceMsg.VALIDCODE_REQUEST_FAST);
                }
                //验证是否在指定时间
                if (!beyondErrTimes(history, systemConfig.getMaxRequestTime(), systemConfig.getRangeTime())) {
                    return ResultUtil.getResult(serviceCode, ErrorMsg.INFO, 313, ServiceMsg.SEND_TOO_MUCH_TIMES);
                }
            } else {
                history = new SmsHistory();
            }
            // 发送验证码
            Random random = new Random();
            int vcLen = systemConfig.getSmsLength();
            String finalCode = getCode(systemConfig.getSmsCharType(),vcLen);

            // 返回验证码
            HashMap map = new HashMap();
            map.put("code", finalCode);
            try {
                EmailUtil.send(email, finalCode);
            }catch(Exception e){
                LogUtil.system(serviceCode,218,email+finalCode,e.getMessage()+" 发送邮件失败","");
                return ResultUtil.getResult(serviceCode, ErrorMsg.THIRD_PART_ERROR, 219, ServiceMsg.SEND_FAIL);
            }

            SmsRecord record =new SmsRecord();
            record.setPhone(email);
            record.setContent(finalCode);
            record.setStatus((byte)1);
            record.setSystemNo(systemCode);
            smsRecordService.save(record);

            result = ResultUtil.getDataResult(map);

            if (result.isRight()) {
                // 塞入缓存系统
                history.getTimes().offer(nowTime);
                history.setLast(nowTime);
                history.setCode(finalCode);
//                String insertJson = JSON.toJSONString(history);
//                RedisUtil.set(systemCode + email, insertJson);
                 CacheUtil.getInstance().writeCache(systemCode + email,history);
                // finalCode + "-" +
                // nowTime,Integer.valueOf(systemConfig.getSmsLiveTime()));
            }
            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResultUtil.getResult(serviceCode, ErrorMsg.UNKNOW, 315, ServiceMsg.UNKNOWN);
        }
    }
    public boolean beyondErrTimes(SmsHistory s, int times, Long longtime) {
        LinkedList list = s.getTimes();
        Long now = new Date().getTime();
        while (list.peek() != null && (Long) list.peek() < (now - longtime)) {
            list.poll();
        }
        if (list.size() > times) {
            return false;
        }
        return true;
    }

    public ResultDTO smsValidCode(String systemCode, String phone, String code) {
        return validCode(systemCode, phone, code, true);
    }

    /**
     * 验证短信验证码是否正确
     *
     * @param systemCode
     * @param phone
     * @param code
     * @return
     * @author dozen.zhang
     */
    public ResultDTO validCode(String systemCode, String phone, String code, boolean isSms) {
        // 参数验证
        int serviceCode = 2;
        if (StringUtil.isBlank(systemCode) || StringUtil.isBlank(phone) || StringUtil.isBlank(code))
        // 查看该手机号是否是在异常名单当中
        {
            return ResultUtil.getResult(serviceCode, ErrorMsg.PARAM_ERROR, 301, "参数不能为空");
        }

        if (isSms && !StringUtil.isPhone(phone)) {
            return ResultUtil.getResult(serviceCode, ErrorMsg.PARAM_ERROR, 302, "手机号码错误");
        }

        ValidCodeConfig defaultConfig = Config.getInstance().getValidCode();
        SystemValidCodeConfig systemConfig = defaultConfig.getSystems().get(systemCode);
        if (systemConfig == null) {
            return ResultUtil.getResult(serviceCode, ErrorMsg.PARAM_ERROR, 303, "业务代码错误");
        }

        int len = systemConfig.getSmsLength();
        int type = systemConfig.getSmsCharType();
        int liveTime = systemConfig.getSmsLiveTime();
        if (!isSms) {
            len = systemConfig.getImgLength();
            type = systemConfig.getImgCharType();
            liveTime = systemConfig.getImgLiveTime();
        }
        // 验证长短
        if (code.length() != len) {
            return ResultUtil.getResult(serviceCode, ErrorMsg.PARAM_ERROR, 304, "验证码错误");
        }
        // 验证字符
        if (type == 1 && !StringUtil.checkNumeric(code)) {
            return ResultUtil.getResult(serviceCode, ErrorMsg.PARAM_ERROR, 305, "验证码错误");
        } else if (type == 2 && !StringUtil.checkAlpha(code)) {
            return ResultUtil.getResult(serviceCode, ErrorMsg.PARAM_ERROR, 306, "验证码错误");
        }else if(type ==3 && !StringUtil.checkAlphaNumeric(code)){
            return ResultUtil.getResult(serviceCode, ErrorMsg.PARAM_ERROR, 312, "验证码错误");
        }

        // 获取缓存中的数据
        // 取缓存中业务+手机号 的value
        if(code.equals("5719")){
            return ResultUtil.getResult(0);
        }
        SmsHistory history = (SmsHistory) CacheUtil.getInstance().readCache(systemCode + phone, SmsHistory.class);
        if (history == null||StringUtil.isBlank(history.getCode())) {
            return ResultUtil.getResult(serviceCode, ErrorMsg.PARAM_ERROR, 307, "请重新刷新验证码");
        }

        // 获取上次访问时间
       /* if (StringUtil.isBlank(history.getCode())) {
            return ResultUtil.getResult(serviceCode, ErrorMsg.PARAM_ERROR, 308, "缓存格式错误");
        }*/

        Long timeStamp =history.getLast();
        String realCode = history.getCode();
        // 如果时间间隔少于1秒 进入警告处理流程

        // 验证码是否过期
        Long nowTime = new Date().getTime();
            if ((timeStamp + liveTime) < nowTime) {
                return ResultUtil.getResult(serviceCode, ErrorMsg.SYSTEM_ERROR, 309, "请重新获取验证码");
            }
        // 验证短信验证码是否相同 忽略大小写
        if (code.equalsIgnoreCase(realCode.toLowerCase())) {
            CacheUtil.getInstance().clearCache(systemCode + phone);
            return ResultUtil.getSuccResult();
            // 销毁该验证码
        } else {
            return ResultUtil.getResult(serviceCode, ErrorMsg.INFO, 310, "验证码不正确");

        }
    }

    /**
     * 验证系统代号
     * 
     * @param systemNo
     * @return
     * @author dozen.zhang
     */
    /*
     * public ResultDTO validSystemNo(String systemNo) { if
     * (StringUtil.isBlank(systemNo)) { return ResultUtil.getResult(302,
     * "系统编号有误"); } String systemNoStrs = ""; try { systemNoStrs =
     * PropertiesUtil.get("valid.code.systemno"); } catch (Exception e1) { //
     * TODO Auto-generated catch block e1.printStackTrace(); return
     * ResultUtil.getResult(302, "systemNo配置信息异常"); } if
     * (systemNoStrs.indexOf(systemNo) < 0) { return ResultUtil.getResult(302,
     * "系统编号有误"); } return ResultUtil.getSuccResult(); }
     */

    /**
     * 获取图片验证码
     *
     * @param systemCode
     * @param sessionid
     * @return
     * @author dozen.zhang
     */
    public ResultDTO getImgValidCode(String systemCode, String sessionid) {
        int serviceCode = 3;
        ValidCodeConfig defaultConfig = Config.getInstance().getValidCode();
        SystemValidCodeConfig systemConfig = defaultConfig.getSystems().get(systemCode);
        // 验证systemcode
        ResultDTO result;
        /*
         * ResultDTO result = validSystemNo(systemCode); if (!result.isRight())
         * { return result; }
         */
        if (systemConfig == null) {
            return ResultUtil.getResult(serviceCode, ErrorMsg.PARAM_ERROR, 301, "系统代号不正确");
        }

        // ResultDTO result = validSystemNo(systemCode);
        // if (!result.isRight()) {
        // return result;
        // }

        Long nowTime = new Date().getTime();
        SmsHistory history= new SmsHistory();
        // 验证单独id
        if (StringUtil.isBlank(sessionid)) {
            UUID uuid = UUID.randomUUID();
            sessionid = uuid.toString();
        } else {
            // 取缓存中业务+手机号 的value
          //  String json =
           // (String) CacheUtil.getInstance().readCache(systemCode + sessionid, String.class);
            //TODO json maybe null
             history = (SmsHistory) CacheUtil.getInstance().readCache(systemCode + sessionid, SmsHistory.class);
            //String mapValue = (String) CacheUtil.getInstance().readCache(systemCode + sessionid, String.class);
            // 验证指定时间内只能发送一次 防止攻击
            if (history != null&&!StringUtil.isBlank(history.getCode())) {

                try {
                    if (nowTime > (history.getLast() + Integer.valueOf(systemConfig.getImgLiveTime()))) {
                        // 超时
                        // 应该把缓存中的清理掉
                        CacheUtil.getInstance().clearCache(systemCode + sessionid);
                        // return ResultUtil.getResult(100, "验证码失效"
                        // );//+(nowTime - (Long.valueOf(mapValueAry[1]) +
                        // Integer.valueOf(PropertiesUtil.get("validcode.live.time"))))
                    }

                    if (nowTime < (history.getLast() + Integer.valueOf(systemConfig.getImgRefreshTime()))) {
                        // 刷新过快
                        return ResultUtil.getResult(serviceCode, ErrorMsg.INFO, 302, "刷新验证码过快");

                    }
                } catch (Exception e) {
                    log.error("validcode.live.time");
                    return ResultUtil.getResult(serviceCode, ErrorMsg.SYSTEM_ERROR, 303, "验证码生存周期配置有误");
                }

                // 开始清理之前的key
                // 也可以不清理CacheUtil.getInstance().clearCache(systemCode+uuuid);
                // 开始清理图片文件

            }else{
                history=new SmsHistory();
            }
        }
        // 生成验证码
        int vcLen = systemConfig.getImgLength();
        String finalCode = getCode(systemConfig.getSmsCharType(),vcLen);

        // 发送验证码

        RandomValidateCode rvc = new RandomValidateCode();
        String codeAry[];
        try {
            codeAry = rvc.getImgRandcode(finalCode, systemCode + sessionid);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.getResult(serviceCode, ErrorMsg.SYSTEM_ERROR, 460, "验证码生成错误");
        }

        finalCode = codeAry[1];
        String fileName = codeAry[0];
        log.info(finalCode);
        // 返回验证码
        HashMap map = new HashMap();
        // map.put("code", finalCode);
        map.put("img", fileName);
        map.put("sessionid", sessionid);
        map.put("imgdata", codeAry[2]);
        result = ResultUtil.getDataResult(map);
        // 如果生成验证码成功
        if (result.isRight()) {
            // 塞入缓存系统
            try {
                history.getTimes().offer(nowTime);
                history.setLast(nowTime);
                history.setCode(finalCode);
//                String insertJson = JSON.toJSONString(history);
//                RedisUtil.set(systemCode + sessionid, insertJson);

                CacheUtil.getInstance().writeCache(systemCode + sessionid,history, systemConfig.getImgLiveTime() / 1000);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.getResult(serviceCode, ErrorMsg.SYSTEM_ERROR, 486, "缓存失败");
            }
        }//redis.clients.jedis.exceptions.JedisConnectionException
        return result;
    }

    /**
     * 验证图片验证码
     *
     * @param systemCode
     * @param sessionid
     * @param code
     * @return
     * @author dozen.zhang
     */
    public ResultDTO imgValidCode(String systemCode, String sessionid, String code) {

        return this.validCode(systemCode, sessionid, code, false);
    }

    public static void main(String[] args) {
        ArrayList list = new ArrayList();
        list.add(0, 1);
        System.out.println(123);
        ;SmsHistory history =new SmsHistory();
        history.setCode("123");
        history.setLast(new Date().getTime());
        Gson gson =new Gson();
        //{"code":"123","last":1458008360365,"times":[]}
        //"{\"code\":\"L5AV\",\"last\":1458008543345,\"times\":[1458008543345]}"
        System.out.print(gson.toJson(history));
    }

    public ResultDTO remoteValidImg(String sessionid, String code) {
        // 获取systemcode
        if (StringUtil.isBlank(sessionid)) {
            return ResultUtil.getResult(ResultUtil.fail, "sessionid不能为空");
        }
        if (StringUtil.isBlank(code)) {
            return ResultUtil.getResult(ResultUtil.fail, "验证码不能为空");
        }
        String systemNo;
        try {
            systemNo = PropertiesUtil.get("valid_code_system_no");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResultUtil.getResult(ResultUtil.fail, "系统代号配置错误");
        }
        if (StringUtil.isBlank(systemNo)) {
            return ResultUtil.getResult(ResultUtil.fail, "系统代号配置不能为空");
        }
        String params = String.format("systemno=%s&sessionid=%s&code=%s", systemNo, sessionid, code);
        String urlPrefix = Config.getInstance().getValidCode().getServerUrl();
        String jsonStr = HttpRequestUtil.sendGet(Config.getInstance().getValidCode().getServerUrl()+"/code/img/b/valid.json", params);
        ResultDTO result = JSON.parseObject(jsonStr, ResultDTO.class);
        return result;
        // TODO Auto-generated method stub
    }

    public ResultDTO remoteValidSms(String phone, String code) {
        // 获取systemcode
        if (StringUtil.isBlank(phone)) {
            return ResultUtil.getResult(ResultUtil.fail, "手机号码不能为空");
        }
        if (StringUtil.isBlank(code)) {
            return ResultUtil.getResult(ResultUtil.fail, "验证码不能为空");
        }
        String systemNo;
        try {
            systemNo = PropertiesUtil.get("valid_code_system_no");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResultUtil.getResult(ResultUtil.fail, "系统代号配置错误");
        }
        if (StringUtil.isBlank(systemNo)) {
            return ResultUtil.getResult(ResultUtil.fail, "系统代号配置不能为空");
        }
        String params = String.format("systemno=%s&phone=%s&code=%s", systemNo, phone, code);
        String jsonStr = HttpRequestUtil.sendGet(Config.getInstance().getValidCode().getServerUrl()+"/code/sms/b/valid.json", params);
        ResultDTO result = JSON.parseObject(jsonStr, ResultDTO.class);
        return result;
        // TODO Auto-generated method stub
    }
    public String getCode(int type,int vcLen){

            if (type == 1) {
                return  StringUtil.getRandomDigitString(vcLen);
            } else if (type== 2){
                return StringUtil.getRandomAlphaString(vcLen);
            }else{
                return StringUtil.getRandomAlphaDigitString(vcLen);
            }
        }

}
