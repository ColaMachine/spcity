/**
 * photographs
 * 
 * @authors Ping YF (koakumaping@163.com)
 * @date 2015-11-06 10:35:21
 * @version $Id$
 */

$(function() {
    $(".loading-img").scrollLoading({
        // container: $("#images"),
        callback: function() {
            console.log($(this).attr('src'));
        }
    });

	$('.photographs-del').on('tap', function() {
		delPic($(this).attr("data-attr"));
	});

	$('#image').on('change',function() {
        loading.show();
		fileUpload(this.files,'/ms/merchant/upload/uploadImg?imgType=2',function(data) {
			if (data.success) {
				$('.container > div').prepend(
					'<div class="left row-5 top-fix-10">'
							+ '<div class="row-9">'
							+ '<img class="row-10 loading" data-url="/static/weizhan/blue/img/pic-wall/1.png" src="' + data.data.linkPath  +'" alt="loading">'
							+ '</div>'
							+ '<a href="javascript:"><span class="photographs-del right">'
							+ '<img class="photographs-del-icon" src="/static/weizhan/blue/img/pic-wall/del.png">删除'
							+ '</span></a>'
							+ '</div>');
				this.outerHTML += '';
				this.value = "";
				updateMerchantPic(data.data.relativePath,0,2,1);
			} else {
				this.outerHTML += '';
				this.value = "";
				alert(data.status);
			}
            loading.hide();
		});
	});
});

function updateMerchantPic(picPath,picId,picType,orderNum) {
	var data = {
		picPath:picPath,	
		picId:picId,	
		picType:picType,	
		orderNum:orderNum
	}
	$.ajax({
        url: '/ms/member/merchant/pic/update',
        dataType: 'JSON',
        data: data,
        async: false,
        type: "POST",
        success: function(data, textStatus, jqXHR){
            if(data.success) {
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
}



function delPic(picId) {
	$.ajax({
        url: '/ms/member/merchant/pic/del',
        dataType: 'JSON',
        data: {picId:picId},
        async: false,
        type: "POST",
        success: function(data, textStatus, jqXHR){
            if(data.success) {
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
}