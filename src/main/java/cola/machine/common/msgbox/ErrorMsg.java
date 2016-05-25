package cola.machine.common.msgbox;

public class ErrorMsg {
	//系统级错误
    public static int SYSTEM_ERROR=1;
  //依赖级错误
	public static int PARAM_ERROR=2;
	//业务级错误
	public static int BUSINESS_ERROR=3;
	//依赖级
	public static int THIRD_PART_ERROR=4;
	//交互级
	public static int INFO=5;
	//未知错误
	public static int UNKNOW=99;
}
