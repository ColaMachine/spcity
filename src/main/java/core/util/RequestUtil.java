package core.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cola.machine.util.StringUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import core.page.Page;


public class RequestUtil {
	
	
	 /**
	  * 是否为Ajax请求
	  * @author syf
	  * @creationDate. 2015年5月29日 下午2:48:57 
	  * @param request
	  * @return
	  */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		return (request.getHeader("x-requested-with") == null) ? false : true;
	}
	
	/**
	 * 向客户端输出
	 * @author syf
	 * @creationDate. 2015年5月29日 下午2:55:08 
	 * @param response
	 * @param outObj
	 * @param outEncoding
	 * @throws IOException
	 */
	public static void write(HttpServletResponse response, Object outObj, String outEncoding) throws IOException {
		response.setCharacterEncoding(outEncoding);
		PrintWriter out = null;
		out = response.getWriter();
		out.print(outObj);
		out.flush();
		out.close();
	}
	
	/**
	 * 从request中获取page
	 * @param request
	 * @return
	 * @author dozen.zhang
	 */
	public static Page getPage(HttpServletRequest request){
	    String curPage = request.getParameter("curPage");
	    String pageSize = request.getParameter("pageSize");
		/*if(StringUtil.isBlank(curPage)||StringUtil.isBlank(pageSize) ){
			return null;
		}*/
		if(StringUtil.isBlank(curPage)){
			curPage ="1";
		}
		if(StringUtil.isBlank(pageSize)){
			pageSize ="10";
		}
	    Page page =new Page();
	    page.setCurPage(Integer.valueOf(curPage));
	    page.setPageSize(Integer.valueOf(pageSize));
	    return page;
	}

}