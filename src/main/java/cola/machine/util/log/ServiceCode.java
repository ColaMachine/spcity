package cola.machine.util.log;

public enum ServiceCode {

 
    /** 
     *类名_方法名
     */
    ValidCodeService("验证码服务"),
    USER_SERVICE("用户服务"),
    MERCHANT_QUERYCOUNT("统计商户总数"),
    MERCHANT_QUERYLIST("查询商户列表"),
    YUANQU_ESCAPE("园区逃生"),
    DEVICE_QUERYINFOCOUNT("统计商户下设备总数"),
    DEVICE_QUERYINFOLIST("查询商户下设备列表"),
    DEVICE_ESCAPE("园区下设备逃生"),
    REPORTCONTROLLER("园区统计报表"),
    StatWrapService("园区统计接口"),
    ORDERREPORTCONTROLLER_CRMLIST("crm用户列表"),
    ORDERREPORTCONTROLLER_PAYLIST("消费记录列表"),
    MERCHANT_PACKAGEADD("套餐信息增加"),
    MERCHANT_PACKAGEUPDATE("套餐信息更新"),
    MERCHANT_QUERYPACKAGELIST("查询套餐信息");
    /** 
     *方法名
     */
    private String serviceName;
    /**
     * 构造函数
     * @param context
     */
    private ServiceCode(String context){
        this.serviceName = context;
    }
}
