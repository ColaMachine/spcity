/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年6月29日
 * 文件说明: 
 */
package cola.machine.oauth.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cola.machine.util.UUIDUtil;

/**
 * 版权所有：公众信息
 * 项目名称:awifi-core
 * 创建者: dozen.zhang
 * 创建日期: 2015年6月26日
 * 文件说明: 
 */
@Controller
@RequestMapping("/oauth")
public class OAuthController {
	
@RequestMapping(value = "/request_token")
public String request_token(HttpServletRequest request){
	String request_token= UUIDUtil.getUUID();
	return "redirect:/oauth/authorize?oauth_token="+request_token;
}
}
