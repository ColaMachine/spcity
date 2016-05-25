/**
 * imgUpload
 * @authors Ping YF (koakumaping@163.com)
 * @date    2015-11-05 15:07:50
 * @version $Id$
 */

$(function(){

	$('.img-upload-arround').on('tap', function(){
		console.log('img-upload');
		$('#overlay').show();

		var height = window.innerHeight;

		$('.img-upload-dialog').css({
			'display': 'block',
			'top': getScrollTop() + height - $('.img-upload-dialog').height()
		});
		$('.img-upload-dialog-input').attr('data-orgin', $(this).find('.img-upload').attr('alt'));
		if($(this).attr("uploadType") != undefined && $(this).attr("uploadType")) {
			
		}
		$('body').bind("touchmove",function(e){
            e.preventDefault();
        });
	});

    $('div#overlay').on('singletap', function(){
        $('.img-upload-dialog').css({
			'display': 'none'
		});
		$('.img-upload-dialog-input').attr('data-orgin', '');
        $('#overlay').hide();
        $('body').unbind("touchmove");
        startMove();
    });

    $('.img-upload-dialog-input').on('change', function(){
        loading.show();
    	fileUpload(this.files, '/ms/merchant/upload/uploadImg?imgType=' + $(this).attr("imgType"), function(data){
		    if(data.success) {
		    	var picId = $('.img-upload[alt='+$('.img-upload-dialog-input').attr('data-orgin')+']').attr("picId");
		    	var orderNum = $('.img-upload[alt='+$('.img-upload-dialog-input').attr('data-orgin')+']').attr("orderNum");
		        $('.img-upload[alt='+$('.img-upload-dialog-input').attr('data-orgin')+']').attr('src', data.data.linkPath);
		        $('.carousel-img[alt='+$('.img-upload-dialog-input').attr('data-orgin')+']').attr('src', data.data.linkPath);
		        updateMerchantPic(data.data.relativePath,picId,1,orderNum);
		    } else {
		    	alert(data.msg);
		    }
            loading.hide();
	    });
    });
    
    function hideUpload() {
    	$('.img-upload-dialog').css({
			'display': 'none'
		});
		$('.img-upload-dialog-input').attr('data-orgin', '');
        $('#overlay').hide();
        $('body').unbind("touchmove");
        startMove();
    }
    
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
                	$('.img-upload[alt='+$('.img-upload-dialog-input').attr('data-orgin')+']').attr('picid', data.data);
                	hideUpload();
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
    
    

    function stopMove(){
	    document.body.style.overflow='hidden';
	}

	function startMove(){
	    document.body.style.overflow='';
	}

	function getScrollTop(){ 
		var scrollTop=0; 
		if(document.documentElement && document.documentElement.scrollTop){ 
			scrollTop = document.documentElement.scrollTop; 
		}else if(document.body){ 
			scrollTop=document.body.scrollTop; 
		} 
		return scrollTop; 
	}

});