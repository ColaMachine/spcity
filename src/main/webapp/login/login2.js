/**
*
*
* Author:
*	zzw <zzw1986@gmail.com>
*	
*
* File: td_view.js
* Create Date: 2013-9-23 8:29
*
*
*/
$(document).ready(function(){
  $("#register-tab").click(function(){
   	if($("#register-tab").parent().attr('class')=='first active'){
   		
   	}else{
   		$("#register-tab").parent().attr('class','first active');
   		$("#sign-in-tab").parent().attr('class','');
   		//隐藏对象
   		$("#register-tab-content").show();
   		$("#sign-in-tab-content").hide();
   	}
  });
  
  $("#sign-in-tab").click(function(){
   	if($("#sign-in-tab").parent().attr('class')=='active'){
   		
   	}else{
   		$("#register-tab").parent().attr('class','first');
   		$("#sign-in-tab").parent().attr('class','active');
   		
   		$("#register-tab-content").hide();
   		$("#sign-in-tab-content").show();
   	}
  });
  	
  	
  	//addValidation("first-name",{'reg':/^[\u4e00-\u9faf]{0,2}$/,'error_msg':'含有无效字符'});
  	
	$("#first-name").focus(function(){
		if(isNull($("#first-name").val())){
			$("#first-name-disclaimer").show();
			$("#first-name-error").hide();
		}
	});
  	$("#first-name").blur(function(){
  		if(!checkFirstName()){
  			$("#first-name-error").show();
  			$("#first-name-disclaimer").hide();
  		}else{
  			$("#first-name-error").hide();
  			$("#first-name-disclaimer").show();
  		}
	});
	
	
	/*$("#last-name").focus(function(){
  		$("#last-name-disclaimer").show();
  		$("#last-name-error").hide();
	});*/
  	$("#last-name").blur(function(){
  		if(!checkLastName()){
  			$("#last-name-error").show();
  		}else{
  			$("#last-name-error").hide();
  		}
	});
	
	/**
	 * 邮箱验证
	 */
	$("#email").focus(function(){
  		if(isNull($("#first-name").val())){
			$("#email-disclaimer").show();
  		$("#email-error").hide();
		}
	});
  	$("#email").blur(function(){
  		if(!checkEmail()){
  			
  			
  			$("#email-error").show();
  			$("#email-disclaimer").hide();
  			
  			
  		}else{
  			checkEmailValid();
  			//$("#email-error").hide();
  			//$("#email-disclaimer").show();
  		}
	});
	
	
	//密码1
	
	$("#password").blur(function(){
  		if(!checkPassword()){
  			$("#password-error").show();
  		}else{
  			$("#password-error").hide();
  		}
	});
	//密码重复
	$("#password-repeat").blur(function(){
		if(!checkPasswordRepeat()){
  			$("#password-repeat-error").show();
  		}else{
  			$("#password-repeat-error").hide();
  		}
		
		
	});
	
	
	$("#username").blur(function(){
		if(!checkUserName()){
			$("#username-error").show();
		}else{
			$("#username-error").hide();
		}
	});
	$("#username").keyup(function(){
		//alert(e.keyCode)
		
		$("#username").val($("#username").val().replace(/[^a-zA-Z0-9]/g,''));
	});
	/*$("#username").afterpaste(function(){
		//alert(e.keyCode)
		
		$("#username").val($("#username").val().replace(/[^a-zA-Z0-9]/g,''));
	});*/
	
	//onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"
	
	
	
});
/**
 * 检查用户名
 */
/* 用户名只能由英文字母和数字构成 且必须含有字符  */
function checkUserName(){
	var userName = $("#username").val();
	if(isNull(userName)){
		$("#username-error").html("不能为空.");
		
	}else if(userName.length<4|| userName.length>20){
		$("#username-error").html("用户名长度应控制在4~20个字符.");
	}else if(!/^[0-9A-Za-z]*[a-zA-Z]+[0-9A-Za-z]*$/.test(userName)){
		$("#username-error").html("用户名只能由数字和字母组成，且必须含有字母.");
	}else{
		//ajax判断是否有重复的用户名
		var jso={};
		jso.userName=userName;
		AjaxFun("loginAjax.doCheckUserNameValid",jso,function(data){
			if(data.RESULT){
				//是存在同样的用户名的
				$("#username-error").html(data.MSG);
				$("#username-error").show();
			}else{
				
			}
		});
		return true;	
	}
	return false;
}

//展现消息
function show(){
	
}

function checkPassword(){
	
	var length=$("#password").val().length;
	if(isNull($("#password").val())){
		$("#password-error").html("不能为空.");
	}else
	if(length<6 || length>20 ){
		$("#password-error").html("密码长度应控制在6~20.");
	}else{
		if(!isNull($("#password-repeat").val())){
			checkPasswordRepeat();
		}
		return true;
	}
	return false;
}

function checkPasswordRepeat(){
	if(isNull($("#password-repeat").val())){
		$("#password-repeat-error").html("不能为空.");
	}else
	if ($("#password-repeat").val() != $("#password").val()) {
		$("#password-repeat-error").html("密码不匹配.");
	} else {
		return true;
	}
	return false;
}

function checkEmail(){
	if(! /^([a-z0-9A-Z]+[-|\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\.)+[a-zA-Z]{2,}$/gi.test($("#email").val())){
		return false;
	} else {
		return true;
	}
}

  		

function addValidation(id,param){
	
	
	
	
	if(param.reg){
		var _reg =new Regex(param.reg);
		
		$("#"+id).blur( function(){
			if(_reg.test($("#"+id).val())){
  				$("#last-name-error").show();
	  		}else{
	  			$("#last-name-error").hide();
	  		}
		});
	}
}
/**
 * 检测姓氏正确性
 */
function checkFirstName(){
	if(!isNull($("#first-name").val())){
		if(!/^[\u4e00-\u9faf]+$/.test($("#first-name").val())){
			return false;
  		}
	}
	return true;
}
function checkLastName(){
	if(!isNull($("#last-name").val())){
		if(!/^[\u4e00-\u9faf]+$/.test($("#last-name").val())){
			return false;
  		}
	}
	return true;
}
function validateRegistration(){
	if(checkUserName()&&
	checkPassword()&&
	checkPasswordRepeat()&&
	checkEmail()&&
	checkFirstName()&&
	checkLastName()
	
	
	
	){
		//检验帐号是否存在
		
		//检验密码是否存在
		
		
		
		return true;
	}else{
		return false;
	}
}
/**
*提交注册信息
*/
function registerSubmit(){
	var jso=changeForm2Jso("registration-form");
	AjaxFun("loginAjax.doRegister",jso,show);
}
/**
 * 检查邮件是否存在
 */
function checkEmailValid(){
	var email=$("#email").val();
	var jso={};
	jso.EMAIL=email;
	if(!isNull(email)){
		AjaxFun("loginAjax.doCheckEmailValid",jso,function(data){
			if(data.RESULT){
				$("#email-error").hide();
  				$("#email-disclaimer").show();
			}else{
				$("#email-disclaimer").hide();
				$("#email-error").html(data.MSG);
				$("#email-error").show();
			}
		});
	}
}

function checkUserNameValid(){
	var username=$("#username").val();
	var jso={};
	jso.USERNAME=username;
	if(!isNull(email)){
		AjaxFun("loginAjax.doCheckUserNameValid",jso,function(data){
			if(data.RESULT){
				$("#username-error").hide();
  				$("#username-disclaimer").show();
			}else{
				$("#username-disclaimer").hide();
				$("#username-error").html(data.MSG);
				$("#username-error").show();
			}
		});
	}
}
