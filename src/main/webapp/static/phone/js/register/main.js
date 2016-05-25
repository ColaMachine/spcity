$(function() {
	var cutdownTime = 61;
	var $username = $('input[name=username]');
	var $captcha = $('input[name=captcha]');
	var $password = $('input[name=password]');
	var $passwordAgain = $('input[name=password_again]');
	var $regmsg = $(".errorMsg");
	//验证手机号格式
	function mobileStyle(str) {
		var re = /^1\d{10}$/
		if (!re.test(str)) {
			return true;
		}
	}
	//验证密码格式
	function pwdStyle(str) {
		var re = /^[0-9 | A-Z | a-z]{6,20}$/;
		if (!re.test(str)) {
			return true;
		}
	}

	$('.submit').on('tap', function() {
		var username = $username.val().trim() || '';
		var captcha = $captcha.val().trim() || '';
		var password = $password.val() || '';
		var passwordAgain = $passwordAgain.val() || '';
		if (username == '' || mobileStyle(username)) {
			$regmsg.text("请输入正确的手机号");
			return false;
		}

		if (captcha == '' || !(/^[0-9]{4}$/).test(captcha)) {
			$regmsg.text("请输入正确的验证码");
			return false;
		}

		if (password == '' || pwdStyle(password)) {
			$regmsg.text("请输入正确的密码格式");
			return false;
		}

		if (passwordAgain == '' || (password != passwordAgain)) {
			$regmsg.text('两次输入的密码不一致');
			return false;
		}
		
		if (!$('#agree').is(':checked')) {
			$regmsg.text('请阅读并同意《爱WIFI用户协议》');
			return false;
		}
		console.log("注册中")
		var data;
		data = {
			username: username,
			password: password,
			captcha: captcha
		};

		loading.show();
		console.log(data);
		$.ajax({
			url: PATH + 'ms/reg/save',
			dataType: 'JSON',
			data: data,
			async: false,
			success: function(data, textStatus, jqXHR) {
				if (data.success) {
					alert("注册成功");
					window.location.href = PATH + "ms/login/init";
				} else {
					alert(data.message);
				}
			},
			error: function(XHR, textStatus, errorThrown) {
				alert(textStatus || '系统异常，请稍后再试...');
			},
			complete: function(XHR, textStatus) {
				loading.hide();
			}
		});
	});

	$('.captcha').find('button').on('click', function() {
		var self = $(this);
		var username = $username.val().trim()||'';
		if (username == '' || mobileStyle(username)) {
			$regmsg.text("请输入正确的手机号");
			return false;
		}

		self.attr('disabled', true);
		self.text('发送中');
		var time = cutdownTime;
		var sI = setInterval(function() {
			time = time - 1;
			if (time !== 0) {
				self.text(time + '秒后重试');
			} else {
				window.clearInterval(sI);
				time = cutdownTime;
				self.text('重新获取');
				self.attr('disabled', false);
			}
		}, 1000);

		// 调用API
//		var uri = Uri.query(),
		var	data;
		var username = $username.val().trim()||'';
		data = {
			mobile: username,
			type: 'ms_reg'
		};

		$.ajax({
			url: PATH + 'ms/sms/send',
			dataType: 'JSONP',
			jsonp: 'callback',
			header: {
				'cache-control': 'no-cache'
			},
			data: data,
			success: function(data, textStatus, jqXHR) {
				if (data[0].success) {} else {
					alert(data[0].message);
				}
			},
			error: function(XHR, textStatus, errorThrown) {
				alert(textStatus || '系统异常，请稍后再试...');
			}
		});
	});

	
});