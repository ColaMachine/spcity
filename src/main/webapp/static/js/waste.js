
/**
 *
 *
 * Author: zzw <zzw1986@gmail.com>
 *
 *
 * File: common.js Create Date: 2014-04-10 19:54:40
 *
 *
**/
/**
 * 对于模态框 tab页进行自动绑定触发事件
 */
function pageinit(){
	$("*[data-toggle='modal']").each(function(){
		if($(this).attr("data-target")){
			$(this).on("click",function(){
				dialog.showMask();
				$($(this).attr("data-target")).show();
			});
		}

	});

	$("*[data-dismiss='modal']").each(function(){
		$(this).on("click",function(){
		dialog.hideMask();
		$(this).closest(".modal").hide();
		});

	});

	$(".menu li").each(function(){
		$(this).on("click",function(){
			$(".select").removeClass("select");
			$(this).addClass("select");
		});
	})

	$(".nav-tabs li").each(function(){
		$(this).on("click",function(){
			$(".active").removeClass("active");
			$(this).addClass("active");
		});
	});
}




function initGrid(gridParam){
	 $(grid_selector).jqGrid(gridParam);
	 return  $(grid_selector);
}

