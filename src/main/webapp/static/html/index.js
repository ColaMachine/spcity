/**
 *
*
* Author:
*	zzw <zzw1986@gmail.com>
*	
*
* File: index.js
* Create Date: 2014-04-10 19:54:40
*
*
*/
/**
 * 页面初始化
 */
function init(){
	if($.cookie("USERNAME")!=null){
		$("#USERNAME").val($.cookie("USERNAME"));
	}else{
		$("#USERNAME").val('');
		$("#PASSWORD").val('');
	}
	
}
/**
 * 登录
 */
function doLogin(){//document.getElementById("loginForm").submit();return;
	//判断是否记住帐号
	var isRemember= $("#REMBERACCOUNT").is(':checked');
	if(isRemember){
		$.cookie("USERNAME", $("#USERNAME").val(),{expires:10});  
	}
	var jso={};
	jso.USERNAME= $("#USERNAME").val();
	jso.PASSWORD= $("#PASSWORD").val();
	AjaxFun("loginAjax.doLogin",jso,show);
}
/**
 * 展示回传信息
 * @param {Object} data
 */
function show(data){
	if(data.RESULT){
		
		window.location="/calendar/calendar/CalendarView.html";
	}else{
		alert(data.MSG);
	}
}
/**
 * 注册
 */
function register(){
	$('#REG_USERNAME_MSG').html("");
	$('#REG_MAILADDR_MSG').html("");
	$('#REG_PASSWORD_MSG').html("");
	var username =$('#REG_USERNAME').val();
	var mailaddr=$('#REG_MAILADDR').val()
	var password=$('#REG_PASSWORD').val();
	if(isNull(username) ){
		$('#REG_USERNAME_MSG').html("姓名不能为空,请填写姓名!");
	}/*else if($('#REG_USERNAME').val().length<6 || $('#REG_USERNAME_MSG').val().length>12){
		$('#REG_USERNAME_MSG').html("姓名应由长度6~12位字母、汉字或数字组成!");
	}*/else if( !/^[\u2E80-\u9FFFa-zA-Z0-9]{6,12}$/gi.test( username)  ){ //reg=/^13/d{9}$/gi; 
		$('#REG_USERNAME_MSG').html("姓名应由长度6~12位字母、汉字或数字组成!");
	}else if(isNull(mailaddr) ){
		$('#REG_MAILADDR_MSG').html("邮箱地址不能为空,请填写邮件地址!");
	}else if( ! /^([a-z0-9A-Z]+[-|\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\.)+[a-zA-Z]{2,}$/gi.test(mailaddr )       ){
		$('#REG_MAILADDR_MSG').html("请填写有效的邮件地址!");
	}else
	if(isNull(password) ){
		$('#REG_PASSWORD_MSG').html("密码不能为空,请填写密码!");
	}else
	if(! /^([a-z0-9A-Z]){6,12}$/gi.test( password )  ){
		$('#REG_PASSWORD_MSG').html("密码请由6到12位的数字字母组成!");
	}
	else{
		
		var jso={};
		jso.USERNAME=username;
		jso.MAILADDR=mailaddr;
		jso.PASSWORD=password;
		$("#register_btn").attr("disabled",true);
		AjaxFun("loginAjax.doRegister",jso,function(data){
			$("#register_btn").attr("disabled",false);
			if(data.RESULT){
				alert("恭喜您，注册陈功了!我们已发送一封激活邮件到你的邮箱里。请及时激活帐号！");
				
				//隐藏登录框体
							//隐藏登录框体
				$("#login-box").hide();
			
				//隐藏注册框体;
				$("#register-box").hide();
			}else{
				$("#REG_RETURN_MSG").html(data.MSG);
			}
		});
		
	}
}
