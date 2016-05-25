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

						email : {
						/*	email : true,*/
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

						email : {
							isemail : "请填写真实的邮箱",
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
						$(element).closest('.form-group').removeClass(
								'has-info').addClass('has-error');
					},

					success : function(e) {
						$(e).closest('.form-group').removeClass('has-error')
								.addClass('has-info');
									$(e).closest('.form-group').append("<div class='right'>正确</div>");
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

		$('#form-signin input').keypress(function(e) {alert(1);
			/*if (e.which == 13) {
				if ($(e).closest('.form-signin').validate().form()) {
					$(e).closest('.form-horizontal').submit();
				}
				return false;
			}*/
		});
	};
	return {
		init : function() {
			handleSubmit();
		}
	};

}();

function register() {
	if (!$("#register_form").valid()) {
		return;
	}

	$("#registerBtn").attr("disabled", "disabled");
	showWait();

	var jso = changeForm2Jso("#register_form");
	dialog.showWait();
	$.post(PATH + "/registerPost.json", jso, function(data) {
		dialog.hideWait();
		if (data[AJAX_RESULT] == AJAX_SUCC) {
			window.location = PATH + "/index.htm";
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
$(document).ready(function() {
	$("#registerBtn").bind('click',register).removeAttr("disabled");

	//注册表单初始化
	registerValidator.init();
	//checkCookie();
	//show user name and password

});


