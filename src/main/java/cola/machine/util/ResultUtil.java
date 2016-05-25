/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月11日
 * 文件说明: 
 */
package cola.machine.util;

import cola.machine.util.log.ServiceCode;
import cola.machine.util.log.ServiceMsg;
import org.apache.tools.ant.types.spi.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cola.machine.core.msg.ErrorMessage;

import core.page.Page;

import core.action.ResultDTO;

public class ResultUtil {
    public static int succ=0;
    public static int fail=1;
	private static Logger logger =LoggerFactory.getLogger(ResultUtil.class);
	/**
	 * 返回成功，默认代码1
	 * @return
	 * @author 宋展辉
	 */
    public static  ResultDTO getResult(){
        return getResult(succ, null, null,null);
    }
    
    /**
     * 返回成功，代码result
     * @param result
     * @return
     * @author 宋展辉
     */
    public static  ResultDTO getResult(int result){
        return getResult(result, null, null,null);
    }
    
	/**
	 * 返回成功，数据data
	 * @param data
	 * @return
	 * @author 宋展辉
	 */
	public static  ResultDTO getResult(Object data){
		return getResult(succ, data, null,null);
	}
	public static  ResultDTO getResult(ServiceMsg msg){
		return get(msg);
	}
	
	public static ResultDTO getSuccResult(){
		return getResult(succ, null, null,null);
	}
	
	public static ResultDTO getSuccResult(String code ){
		String result = ErrorMessage.getErrorMsgCode(code);
		String msg = ErrorMessage.getErrorMsg(code);
		if(result==null||msg==null){
			logger.error("错误代码名\""+code+"\"不存在");
			msg = "操作成功";
		}
//		return getResult(Integer.valueOf(result), msg);
		return getResult(succ, msg);
	}
	
	
	public static ResultDTO getFailResult(String errcode){
		return getResult(fail, null, null,null);
	}
	
	/**
	 * 返回成功，数据data,分页page
	 * @param data
	 * @return
	 * @author 宋展辉
	 */
	public static  ResultDTO getResult(Object data,Page page){
		return getResult(succ, data, null,page);
	}
	
	public static  ResultDTO getDataResult(Object data){
        return getResult(succ, data, null,null);
    }
	/**
	 * 返回错误请求，错误代码result，错误说明msg
	 * @param result
	 * @param msg
	 * @return
	 * @author 宋展辉
	 */
	public static  ResultDTO getResult(int result, String msg){
        return getResult(result, null, msg,null);
    }
	public static  ResultDTO getResult(int service ,int type,int result, String msg){
		return getResult(service*100000+type*1000+result, null, msg,null);
	}
	/**返回错误请求，根据错误代码名code获取错误代码及说明
	 * @param code
	 * @return
	 * @author 宋展辉
	 */
	public static  ResultDTO getWrongResultFromCfg(String code){
		String result = ErrorMessage.getErrorMsgCode(code);
		String msg = ErrorMessage.getErrorMsg(code);
		if(result==null||msg==null){
			logger.error("错误代码名\""+code+"\"不存在");
			result ="999";
			msg = "未知错误，详细情况请查看日志";
		}
		return getResult(Integer.valueOf(result), msg);
		
	}
	public static  ResultDTO get(ServiceMsg msg){


		return getResult(msg.ordinal(),  msg.toString());

	}
	/**返回未登录错误
	 * @return
	 * @author zzw
	 */
	public static  ResultDTO getNotLogging(){
		return getWrongResultFromCfg("err.logic.session.notexsist");
		
	}
	
	/**
	 * 自定义返回
	 * @param result
	 * @param data
	 * @param msg
	 * @return
	 * @author 宋展辉
	 */
	public static  ResultDTO getResult(int result, Object data, String msg){
		return getResult(result, data, msg,null);
	}
	
	/**
	 * 自定义返回
	 * @param result
	 * @param data
	 * @param msg
	 * @param page
	 * @return
	 * @author 宋展辉
	 */
	public static  ResultDTO getResult(int result, Object data, String msg , Page page){
        return new ResultDTO(result, data, msg, page);
    }

	public static ResultDTO getResult(ServiceCode serviceCode, int paramError, int i, ServiceMsg serviceCode_err) {
		return getResult(serviceCode.ordinal()*100000+paramError*1000+i, null, serviceCode_err.toString(),null);

	}
}
