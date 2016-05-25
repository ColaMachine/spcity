/**
 * setting
 * @authors pingyf (koakumaping@163.com)
 * @date    2015-11-04 11:04:51
 * @version $Id$
 */

$(function(){
	// logo上传
	 $('.setting-logo-file').on('change', function(){
         loading.show();
		 fileUpload(this.files, '/ms/merchant/upload/uploadImg?imgType=3', function(data){
		    if(data.success){
		    	var obj = $("#img");
		    	if(obj.attr("src") == undefined || obj == null){
		    		$("#logoimgdiv").html('<img class="setting-logo-img" src="' + data.data.linkPath +'" alt="logo" id="img">');
		    	} else {
		    		$('#img').attr('src', data.data.linkPath);
		    	}
	         } else {
	             alert(data.msg);
	         }
             loading.hide();
	    });
	 });
    
    // 计算文字居中位置,以及输入框的高度
    var sI = setInterval(function(){
        console.log($('.setting-logo-img').height());
        if($('.setting-logo-img').height() != 0){
            clearInterval(sI)
            var heightLogo = $('.setting-logo-img').height() + 20;
            var height = heightLogo / 2 - $('.setting-logo-img-change').height() / 2;
            $('.setting-logo-img-change').css({
                'margin-top': height
            });
            $('.setting-logo-file').css({
                'height': heightLogo
            });
        }
    }, 50);
    

	$('#saveBut').on('tap', function() {
		var merchantId = $("#shopSel").val();
		var logo = $("#img").attr("src");
		var fullName = $("#fullName").val().trim();
		if(fullName == ''){
			alert("商户名称不能为空");
			return false;
		}
		var remarks = $("#remarks").val().trim();
		var openTime = $("#openTime").val().trim();
		var closeTime = $("#closeTime").val().trim();
		var address = $("#address").val().trim();
		var style = $("#style_sel").val().trim();
		var data = {
            id: merchantId,
            thumb: logo,
            merchantName:fullName,
            remarks:remarks,
            openTime:openTime,
            closeTime:closeTime,
            address:address,
            style:style
        };
        console.log(data);
        $.ajax({
            url: '/ms/member/merchant/index/updateSetting',
            dataType: 'JSON',
            data: data,
            async: false,
            type: "POST",
            success: function(data, textStatus, jqXHR){
                if(data.success){
                	alert("保存成功");
                	window.location.reload();
                } else{
                    alert(data.message);
                }
            },
            error: function(XHR, textStatus, errorThrown) {
                alert(textStatus || '系统异常，请稍后再试...');
            },
            complete: function(XHR, textStatus){
            }
        });
	});
	
	$("#style_sel").change(function(){
		var val = $(this).val();
		var text = $(this).find("option:selected").text();
		if(val == "back-blue"){
			$(this).next(".clr-fix").find("div").css("background","#2C8FFF");
		}
		if(val == "back-yellow"){
			$(this).next(".clr-fix").find("div").css("background","#ffbc2c");
		}
		if(val == "back-red"){
			$(this).next(".clr-fix").find("div").css("background","#ff4a2c");
		}
		if(val == "back-cyan"){
			$(this).next(".clr-fix").find("div").css("background","#20ce97");
		}
		if(val == "back-purple"){
			$(this).next(".clr-fix").find("div").css("background","#ce20c8");
		}
		$(this).next(".clr-fix").find("span").text(text);
	});
    
});