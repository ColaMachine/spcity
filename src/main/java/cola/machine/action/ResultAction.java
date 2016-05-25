/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月8日
 * 文件说明: 
 */
package cola.machine.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cola.machine.core.msg.ErrorMessage;
import core.page.Page;
import core.action.ResultDTO;

public class ResultAction {
    protected  int SUCC =0;
    protected  int FAIL =1;
Logger logger=LoggerFactory.getLogger(this.getClass());
	/**
	 * 返回成功，默认代码1
	 * @return
	 * @author 宋展辉
	 */
    protected ResultDTO getResult(){
        return getResult(SUCC, null, null,null);
    }
    
    /**
     * 返回成功，代码result
     * @param result
     * @return
     * @author 宋展辉
     */
    protected ResultDTO getResult(int result){
        return getResult(result, null, null,null);
    }
    
	/**
	 * 返回成功，数据data
	 * @param data
	 * @return
	 * @author 宋展辉
	 */
	protected ResultDTO getResult(Object data){
		return getResult(SUCC, data, null,null);
	}
	
	/**
	 * 返回成功，数据data,分页page
	 * @param data
	 * @return
	 * @author 宋展辉
	 */
	protected ResultDTO getResult(Object data,Page page){
		return getResult(SUCC, data, null,page);
	}
	
	/**
	 * 返回错误请求，错误代码result，错误说明msg
	 * @param result
	 * @param msg
	 * @return
	 * @author 宋展辉
	 */
	protected ResultDTO getResult(int result, String msg){
        return getResult(result, null, msg,null);
    }
	
	/**返回错误请求，根据错误代码名code获取错误代码及说明
	 * @param code
	 * @return
	 * @author 宋展辉
	 */
	protected ResultDTO getWrongResultFromCfg(String code){
		String result = ErrorMessage.getErrorMsgCode(code);
		String msg = ErrorMessage.getErrorMsg(code);
		if(result==null||msg==null){
			logger.error("错误代码名\""+code+"\"不存在");
			result ="999";
			msg = "未知错误，详细情况请查看日志";
		}
		return getResult(Integer.valueOf(result), msg);
		
	}
	
	/**返回未登录错误
	 * @return
	 * @author zzw
	 */
	protected ResultDTO getNotLogging(){
		return getWrongResultFromCfg("err.logic.notlogging");
		
	}
	
	/**
	 * 自定义返回
	 * @param result
	 * @param data
	 * @param msg
	 * @return
	 * @author 宋展辉
	 */
	protected ResultDTO getResult(int result, Object data, String msg){
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
	protected ResultDTO getResult(int result, Object data, String msg , Page page){
        return new ResultDTO(result, data, msg, page);
    }
	
}
