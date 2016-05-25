package cola.machine.util;



import core.action.ResultDTO;
import cola.machine.common.msgbox.MsgReturn;

public class RegexUtil {
	public static ResultDTO email(String email){
		if(email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")){
			return  ResultUtil.getSuccResult();
		}else{
			return ResultUtil.getWrongResultFromCfg("err.email.format");
		}
	}
	
	/**
	 * 说明:判断是否正确
	 * @param pwd
	 * @return
	 * @author dozen.zhang
	 * @date 2015年5月20日下午5:00:33
	 */
	public static ResultDTO pwd(String pwd){
		if(StringUtil.isBlank(pwd)  ){
			return ResultUtil.getWrongResultFromCfg("err.pwd.empty");
		}else if(pwd.length() <6 || pwd.length()>15){
			return ResultUtil.getWrongResultFromCfg("err.email.leng");
		}
		 return ResultUtil.getSuccResult();
	}
	
}
