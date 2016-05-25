$(function() {

	// checkbox联动
	$('.info-sex').find('input[type=checkbox]').on('change', function() {
		// alert($(this).is(':checked'));
		// console.log($('.info-sex').find('input[type=checkbox]').not($(this)));

		$('.info-sex').find('input[type=checkbox]').not($(this)).each(function() {
			if ($(this).is(':checked')) {
				$(this).removeAttr('checked');
			}
		});
	});

	// 性别还原
	var sex = $("#sex-info").val();
	switch (sex) {
		case '0':
			$("#secret").attr("checked", 'true');
			break;
		case '1':
			$("#male").attr("checked", 'true');
			break;
		case '2':
			$("#female").attr("checked", 'true');
			break;
	}

	// 头像上传
	$('.info-avatar-file').on('change', function() {
		loading.show();
		fileUpload(this.files, '/ms/merchant/upload/uploadImg.shtml?imgType=4', function(data) {
			if (data.success) {
				$('.info-avatar-img').attr('src', data.data.linkPath);
			} else {
				alert(data.message);
			}
			loading.hide();
		});
	});

	// 保存
	$('#save').on('tap', function() {
		var img = $('.info-avatar-img').attr('src')||'';
		var name ,nickName,sex,birthday;
		
		alert("保存没验证");
		

		var data = {
			touxiang: img,
			nick: username,
			sex: sex,
			birthday: birthday,
			address: address
		};
		loading.show();
		$.post(
			'',
			data
		).success(function(data) {
			loading.hide();
			if (data.success) {
				alert("保存成功");
			} else {
				alert(data.message);
			}
		}).fail(function() {
			loading.hide();
			tip('保存失败');
		});

	});
});