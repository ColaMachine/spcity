<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%String path=request.getContextPath();  %>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">alert(location.href.split('#')[0])
wx.config({
    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    appId: '${appid}', // 必填，公众号的唯一标识
    timestamp:"${timestamp}", // 必填，生成签名的时间戳
    nonceStr: '${nonceStr}', // 必填，生成签名的随机串
    signature: '${signature}',// 必填，签名，见附录1
    jsApiList: ['onMenuShareTimeline'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
});
wx.ready(function(){
	document.write("weixin ready");
    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
});

wx.error(function(res){
	document.write("weixin error:"+res);
	for(var i in res){
		document.write(res[i]);
	}
    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。

});
wx.success(function(){
	document.write("weixin success:");
});


function fenxiang(){
	wx.onMenuShareTimeline({
	    title: '卡券系统', // 分享标题
	    link: 'http://192.168.34.117:8080/kaqm', // 分享链接
	    imgUrl: 'https://ss3.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/super/whfpf%3D425%2C260%2C50/sign=845923a27ad98d1076815f7147028c3c/f603918fa0ec08fafbb9a6f95fee3d6d55fbda37.jpg', // 分享图标
	    success: function () { 
	    	alert("success");
	        // 用户确认分享后执行的回调函数
	    },
	    cancel: function () { 
	    	alert("cancel");
	        // 用户取消分享后执行的回调函数
	    }
	});
}

</script>

</head>
<body>
<button onclick="fenxiang()">分享</button>
</body>
</html>