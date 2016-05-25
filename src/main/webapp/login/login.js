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

function doRegister() {
	document.location = "register.html";
}
$(document).ready(function() {
	//$("#loginBtn").bind('click',function(){alert('tt!')});
	//登录表单验证器初始化
	loginValidator.init();
	//注册表单初始化
	registerValidator.init();

})

/**
 * 登录表单验证器
 */
var loginValidator = function() {
	var handleSubmit = function() {
		$('#login_form').validate(
				{
					errorElement : 'div',
					errorClass : 'help-block',
					focusInvalid : false,
					rules : {
						email : {
							required : true,
							email : true,
							rangelength : [ 1, 50 ],
							isemail : true
						},
						pwd : {
							stringCheck : true,
							required : true,
							rangelength : [ 6, 15 ],
						},
					},
					messages : {
						email : {
							required : "邮箱未填写",
							email : "请填写真实的邮箱",
							rangelength : "邮箱长度应在50字符以内",

						},
						pwd : {
							required : "密码未填写",
							rangelength : "密码应由6~20个的数字或字母组成"
						}
					},

					highlight : function(element) {
						$(element).closest('.form-signin').removeClass(
								'has-info').addClass('has-error');
					},

					success : function(e) {
						$(e).closest('.form-signin').removeClass('has-error')
								.addClass('has-info');
						$(e).remove();
					},

					errorPlacement : function(error, element) {
						error.insertAfter(element);
					},

					submitHandler : function(form) {
						login();

					},
					invalidHandler : function(form) {
					}
				});

		$('#form-signin input').keypress(function(e) {
			if (e.which == 13) {
				if ($(e).closest('.form-signin').validate().form()) {
					$(e).closest('.form-horizontal').submit();
				}
				return false;
			}
		});
		//注册表单初始化
		registerValidator.init();
	};
	return {
		init : function() {
			handleSubmit();
		}
	};

}();

var form_type = "login";
//切换登录表单和注册表单
function changeForm() {
	$("#registerBtn").removeAttr("disabled");
	$("#loginBtn").removeAttr("disabled");
	if (form_type == "login") {
		$("#register_form").show();
		$("#login_form").hide();
		form_type = "register"
	} else {
		form_type = "login";
		$("#register_form").hide();
		$("#login_form").show();
	}
}

/**
 *注册表单验证器
 */
var registerValidator = function() {
	var handleSubmit = function() {
		$('#register_form').validate(
				{
					errorElement : 'div',
					errorClass : 'help-block',
					focusInvalid : false,
					rules : {
						username : {
							required : true,
							rangelength : [ 3, 15 ]
						},
						email : {
							email : true,
							required : true,
							rangelength : [ 1, 50 ],
							isemail : true
						},
						pwd : {
							stringCheck : true,
							required : true,
							rangelength : [ 6, 15 ]
						},
						pwdrepeat : {
							stringCheck : true,
							required : true,
							rangelength : [ 6, 15 ],
							equalTo : "#pwd"
						}
					},
					messages : {
						username : {
							required : "请填写真实的姓名",
							rangelength : "姓名长度应在5~15个字符"
						},
						email : {
							email : "请填写真实的邮箱",
							required : "邮箱未填写",
							rangelength : "邮箱长度应在50字符以内"
						},
						pwd : {
							required : "密码未填写",
							rangelength : "密码应由6~20个的数字或字母组成"
						},
						pwdrepeat : {
							required : "密码未填写",
							rangelength : "密码应由6~20个的数字或字母组成",
							equalTo : "两次输入密码不同"
						}
					},

					highlight : function(element) {
						$(element).closest('.form-signin').removeClass(
								'has-info').addClass('has-error');
					},

					success : function(e) {
						$(e).closest('.form-signin').removeClass('has-error')
								.addClass('has-info');
						$(e).remove();
					},

					errorPlacement : function(error, element) {
						error.insertAfter(element);
					},

					submitHandler : function(form) {
						register();

					},
					invalidHandler : function(form) {
					}
				});

		$('#form-signin input').keypress(function(e) {
			if (e.which == 13) {
				if ($(e).closest('.form-signin').validate().form()) {
					$(e).closest('.form-horizontal').submit();
				}
				return false;
			}
		});
	};
	return {
		init : function() {
			handleSubmit();
		}
	};

}();

/***
 ** 取cookie值
 *
 */
function getCookie(c_name) {
	if (document.cookie.length > 0) {
		c_start = document.cookie.indexOf(c_name + "=")
		if (c_start != -1) {
			c_start = c_start + c_name.length + 1
			c_end = document.cookie.indexOf(";", c_start)
			if (c_end == -1)
				c_end = document.cookie.length
			return unescape(document.cookie.substring(c_start, c_end))
		}
	}
	return ""
}
//设置cookie值
function setCookie(c_name, value, expiredays) {
	var exdate = new Date()
	exdate.setDate(exdate.getDate() + expiredays)
	document.cookie = c_name + "=" + escape(value)
			+ ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
}
checkCookie();
function checkCookie() {
	var username = getCookie('username')
	//alert(username);
	$("#login-email").val(username);
	return;
	if (username != null && username != "") {//$("#username")
		console.log('Welcome again ' + username + '!')

	} else {
		username = prompt('Please enter your name:', "")
		if (username != null && username != "") {
			setCookie('username', username, 7)
		}
	}
}
/**
 *登录
 *
 **/
function login() {

	//alert($("#login_form").valid());
	var jso = changeForm2Jso("#login_form");
	//先禁用按钮
	$("#loginBtn").attr("disabled", "disabled");
	//alert($("#rememberme").attr("checked"));
	//判断是否使用记住功能
	if ($("#rememberme").attr("checked") == 'checked') {//alert("选中了记住我");
		setCookie('username', jso.email, 365);
	}
	$.post(CONTEXTPATH + "/loginPost", jso, function(data) {
		if (data[AJAX_RESULT] == AJAX_SUCC) {
			window.location = CONTEXTPATH + "/index.htm";
		} else {
			var ul = $("#login_form").find(".failure").find("ul");
			ul.empty();
			ul.append("<li>" + data[AJAX_MSG] + "</li>");
			if (data[AJAX_ERRORS] && data[AJAX_ERRORS].length > 0) {
				for ( var i in data[AJAX_ERRORS]) {
					ul.append("<li style='color:red'>" + data[AJAX_ERRORS][i]
							+ "</li>");
				}
			}

		}
		$("#loginBtn").removeAttr("disabled", "");
	});
}
function register() {
	$("#registerBtn").attr("disabled", "disabled");
	var jso = changeForm2Jso("#register_form");
	$.post(CONTEXTPATH + "/registerPost", jso, function(data) {
		if (data[AJAX_RESULT] == AJAX_SUCC) {
			window.location = CONTEXTPATH + "/index";
		} else {

			var ul = $("#register_form").find(".failure").find("ul");
			ul.empty();
			ul.append("<li>" + data[AJAX_MSG] + "</li>");
			if (data[AJAX_ERRORS]) {

				for ( var key in data[AJAX_ERRORS]) {
					ul.append("<li>" + data[AJAX_ERRORS][key] + "</li>");
				}
			}
		}
		$("#registerBtn").removeAttr("disabled");
	});
}
