package cola.machine.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import core.page.Page;
import cola.machine.bean.AppExceptionLogExample;
import cola.machine.bean.FwLogExample;
import cola.machine.service.LogService;

/**
 * 日志记录接口
 */
@Controller
@RequestMapping("/log")
public class LogController extends BaseController {
	private final Logger log = LoggerFactory.getLogger(LogController.class);

	/** 
	 *日志service
	 */
	@Resource
	private LogService logService;

	/*
	 * @RequestMapping("/test") public String test(HttpServletRequest request){
	 * String s=request.getParameter("s"); s.substring(1); return "test"; }
	 */
	/**
	 * 说明:访问日志页面
	 * 
	 * @return
	 * @return String
	 * @author dozen.zhang
	 * @date 2015年11月11日上午10:04:28
	 */
	@RequestMapping("/listRequestLog.htm")
	public String listRequestLog() {
		return "jsp/log/listRequestLog.jsp";
	}

	/**
	 * 说明:请求访问日志分页数据
	 * @param curPage
	 * @param pageSize
	 * @param response
	 * @param request
	 * @param model
	 * @return json
	 * @throws Exception
	 * @return Object
	 * @author dozen.zhang
	 * @date 2015年11月11日上午10:05:59
	 */
	@RequestMapping(value = "/listRequestLog.json")
	@ResponseBody
	public Object listRequestLog(
			@RequestParam(value = "curpage", required = false) Integer curPage,
			@RequestParam(value = "pagesize", required = false) Integer pageSize,
			HttpServletResponse response, HttpServletRequest request,
			Model model) throws Exception {

		request.getParameter("pagesize");
		Page page = new Page();
		if (curPage != null) {
			page.setCurPage(curPage);
		}
		if (pageSize != null) {
			page.setPageSize(pageSize);
		}
		FwLogExample fwLogExample = new FwLogExample();
		fwLogExample.setPage(page);
		return getResult(logService.listRequests(fwLogExample));
	}
	/**
	 * 说明:访问异常日志列表页面
	 * @return
	 * @return String
	 * @author dozen.zhang
	 * @date 2015年11月11日上午10:08:37
	 */
	@RequestMapping("/listExceptionLog.htm")
	public String listExceptionLog() {
		return "jsp/log/listExceptionLog.jsp";
	}
	/**
	 * 说明:请求异常分页数据
	 * @param curPage
	 * @param pageSize
	 * @param response
	 * @param request
	 * @param model
	 * @return json
	 * @throws Exception
	 * @return Object
	 * @author dozen.zhang
	 * @date 2015年11月11日上午10:07:03
	 */
	@RequestMapping(value = "/listExceptionLog.json")
	@ResponseBody
	public Object listExceptionLog(
			@RequestParam(value = "curpage", required = false) Integer curPage,
			@RequestParam(value = "pagesize", required = false) Integer pageSize,
			HttpServletResponse response, HttpServletRequest request,
			Model model) throws Exception {

		request.getParameter("pagesize");
		Page page = new Page();
		if (curPage != null) {
			page.setCurPage(curPage);
		}
		if (pageSize != null) {
			page.setPageSize(pageSize);
		}
		AppExceptionLogExample example = new AppExceptionLogExample();
		example.setPage(page);
		return getResult(logService.listExceptionLog(example));
	}

}
