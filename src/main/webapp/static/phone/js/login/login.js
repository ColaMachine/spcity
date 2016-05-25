function url_to_json(url) {
	if (url === '')
		return '';
	var pairs = (url || location.search).slice(1).split('&');
	var result = {};
	for (var idx in pairs) {
		var pair = pairs[idx].split('=');
		if (!!pair[0])
			result[pair[0]] = decodeURIComponent(pair[1] || '');
	}
	return result;
}
var params = url_to_json(location.search);
// alert(location.search);

$(function() {
	var $username = $('input[name=username]');
	var $password = $('input[name=password]');
	var $loginmsg = $(".errorMsg");
	//用户输入去除错误信息
	$username.on("change", function() {
		$loginmsg.text('');
	});
	$password.on("change", function() {
		$loginmsg.text('');
	});
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
	//登录
	$('#login').on('click', function() {
		var self = $(this);
		var username = $username.val().trim() || '';
		var password = $password.val() || '';
		if (username == '' || password == '' || mobileStyle(username) || pwdStyle(password)) {
			$loginmsg.text('请输入正确的帐号或密码');
			return false;
		}
		var data;
		data = {
			username: username,
			password: password
		};
		loading.show();
		$.ajax({
			type: "GET",
			url: PATH + '/loginPost.json',
			success: function(datas) {
				if (datas && datas.r == 1) {

				}
			},
			error: function(XHR, textStatus, errorThrown) {
				alert(textStatus || '系统异常，请稍后再试...');
			},
			complete: function(XHR, textStatus) {
				loading.hide();
			}
		});



		var uri = Uri.query(); //  js/comm/uri.js 
		var deviceIda = $("#userMac").val();
		self.text('认证中...');alert(123);
		if (deviceIda == undefined || deviceIda == null || deviceIda == "") {
			loginWeizhan("登录成功！");
			return false;
		} else {

		}
		var data;
		data = {
			username: username,
			password: $.md5(password),
			deviceId: $("#devId").val(),
			terMac: $("#userMac").val(),
			terIp: $("#userIp").val(),
			acName: $("#acName").val(),
			acIp: $("#acIp").val()
		};
		console.log(data);
		var accessUrl = $("#access_url").val();


		$.ajax({
			url: url,
			dataType: 'JSONP',
			jsonp: 'callback',
			header: {
				'cache-control': 'no-cache'
			},
			data: data,
			async: false,
			success: function(data, textStatus, jqXHR) {
				if (data.resultCode == '0') {
					var gw_address = typeof($("#gw_address").val()) == "undefined" ? '' : $("#gw_address").val();
					gw_address += typeof($("#gw_port").val()) == "undefined" ? '' : ':' + $("#gw_port").val();
					if ($("#portal_type").val() == 'authFatAP') {
						var url = 'http://' + gw_address + '/smartwifi/auth?url=' + encodeURIComponent($("#url").val());
						url += '&user_mac=' + (typeof($("#userMac").val()) == "undefined" ? '' : $("#userMac").val());
						url += '&ac_name=' + (typeof($("#acName").val()) == "undefined" ? '' : $("#acName").val());
						url += '&token=' + data.data;
					} else {
						var url = $("#host").val() + '?dev_id=' + $("#devId").val() + '&dev_mac=' + $("#ap_mac").val() + '&site_id=' + '1';
						url += '&user_mac=' + (typeof($("#userMac").val()) == "undefined" ? '' : $("#userMac").val());
						url += '&ac_name=' + (typeof($("#acName").val()) == "undefined" ? '' : $("#acName").val());
						url = url
							//+ '&' + $.param(ps);
					}
					$.ajax({
						url: url,
						async: true,
						dataType: 'JSONP',
						jsonp: 'callback',
						header: {
							'cache-control': 'no-cache'
						},
						success: function(data, textStatus, jqXHR) {},
						error: function(xhr, status, error) {}
					});
					loginWeizhan("登录成功,您现在可以上网了！");
					return false;

				} else {
					console.log(data);
					alert("用户名或密码错误(或用户不存在)");
					return false;
				}
			},
			error: function(XHR, textStatus,
				errorThrown) {
				self.attr('disabled', false);
				alert(textStatus || '系统异常，请稍后再试...');
			},
			complete: function(XHR, textStatus) {
				self.attr('disabled', false);
				self.text('登录');
			}
		});
		self.attr('disabled', false);
	});

});

function loginWeizhan(msg) {
	var username = $('input[name=username]').val().trim();
	var password = $('input[name=password]').val();
	var dt = {
		username: username,
		password: password
	};

	$.ajax({
		url: PATH + 'ms/login/save.shtml',
		dataType: 'JSON',
		data: dt,
		async: false,
		success: function(rs, textStatus, jqXHR) {
			if (rs.success) {
				if ($("#devId").val() != null && $("#devId").val() != "") {
					alert("您现在可以上网了");
					window.location.href = '/ms/index';
				} else {
					alert("登录成功");
					window.location.href = '/ms/index';
				}
			} else {
				alert(rs.message);
			}
		},
		error: function(XHR, textStatus, errorThrown) {
			console.log(XHR, textStatus, errorThrown);
			alert("登录微站出错了");
		},
		complete: function(XHR, textStatus) {
			$('#login').text('登录');
		}
	});
}