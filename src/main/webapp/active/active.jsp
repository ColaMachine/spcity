<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
</head>
<body>
<h1>激活成功</h1>
<a href="index"><span id="seconds">7</span>秒后跳转首页,或点击这里手动跳转</a>
</body>
<script type="text/javascript" >
var seconds=7;
setTimeout("minusSeconds()",1000);
function minusSeconds(){
//alert("now:"+document.getElementById("seconds").innerText);
	if(seconds==0){
		window.location="<%=path%>/index";
	}else{
		seconds--;
		document.getElementById("seconds").innerHTML=seconds;
		setTimeout("minusSeconds()",1000);
	}
}
</script>
</html>