<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%
	String path = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no"
	name="viewport" id="viewport" />
<title>出错了</title>
</head>
<body>
	<img
		style="margin: 0px auto; width: 20%; margin-top: 11%; display: block;"
		src="<%=path %>/img/usercenter_message_no_image.png">
	<h4 align="center" style="color: #999999; margin-top: 0px">
		${msg} 
	</h4>

	<span id="errorspan" style="display: none"> <% Exception ex = (Exception)request.getAttribute("exception"); if(ex!=null){%>
		<a href="javascript:void(0)"
			onclick="if(document.getElementById('errorspan').style.display=='none')document.getElementById('errorspan').style.display='block';else document.getElementById('errorspan').style.display='none'">点击查看详细错误信息</a>
			<H2>
			Exception:
			<%= ex.getMessage()%></H2>
		<P /> <% ex.printStackTrace(new java.io.PrintWriter(out)); }%>
	</span>
</body>
</html>