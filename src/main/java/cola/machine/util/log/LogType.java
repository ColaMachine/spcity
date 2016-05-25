package cola.machine.util.log;
/*编码详解 00111001
第一组3位数字表示service名 （需要维护在servicecode 里）
第二组2为数字表示错误类型 第一位代表大类，第二位可细分小类（已维护在LogType）
第三组3位数字表示明细错误(detailCode)*/
public enum LogType {
    TRACK(1,"正常打印消息"),//
    PARAM(2,"参数错误"),//参数错误前端参数错误 
    INFO(3,"告知用户"),//正常业务逻辑，非错误，需要告知用户 交互级别提醒 需要通知用户 如验证码不正确
    SERVICE(4,"服务错误"),//service 自身处理出错 业务级别错误
    THIRD(5,"第三方错误"),//依赖错误 service 内部调用第三方服务出错
    SYSTEM(6,"系统错误"),//system 系统级别错误
    UNKNOW(99,"未知异常");//未知异常
    private String serviceName;
    private int value;
    public String getServiceName() {
        return serviceName;
    }
    public int getValue() {
        return value;
    }
    private LogType(int value,String context){
        this.value=value;
        this.serviceName = context;
    }
}
