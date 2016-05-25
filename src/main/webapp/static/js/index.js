/**
 * 
 */
var App={
		init: function(){
			var _this = this;
			/*$('.menu ul a').click(function(event){alert(1);
				event.preventDefault();
				if(isNull($(this).attr("href"))){
					if($(this).parent().hasClass('open')){
						$(this).parent().removeClass('open');
						$(this).parent().find("ul").slideUp(500);
					}else{
						$(this).parent().parent().find('li').removeClass('open');
						
						$(this).parent().find("ul").slideDown(500);
						$(this).parent().addClass('open');
					}
				}else{
					$(this).parent().parent().find('a').removeClass('active');
					$(this).addClass('active');
					_this.loadPage($(this).attr("href"));
				}
			});alert(123);*/
			$('#logout').click(function(evt){
			    $('#page').confirm({
			        winId: 'window-msg-dialog',
			        isFixed: false,
			        tltTxt: '提示消息',
			        cntHTML: '确认退出吗',
			        confirmCallback:	function(){window.location=PATH+"/logout.htm"}
			    });
			     $('#window-msg-dialog').jDrag({container:'#page', handle:'.window-title-bar'});
			
			   /* jDialog.confirm('退出系统', '确实要退出吗？', function(){
					_this.logout();
				});
			    
			    zconfirm("退出系统","确实要退出吗",function(){alert(1)});*/
			    // 阻止冒泡和默认行为事件
			    evt.preventDefault();
			    evt.stopPropagation();
			    
			    
			});
			/*$('#logout').click(function(){
				
			});*/
			$('#userprofile').click(function(){
				_this.userprofile();
			});
			$('#userpasswd').click(function(){
				_this.userpasswd();
			});
		},
		
		loadPage: function(url, fun){
		//	jLoading.start();
			$.ajax({
				type: 'GET',
				url: url,
				dataType: 'html',
				success: function(data){
					//jLoading.close();
					
					$('#main').html(data);
					if(typeof fun == 'function') fun();
				},
				error: function(){
					//jLoading.close();
					//jDialog.alert('加载页面失败', '系统错误')
				}
			});
		},
}



$(function(){
	App.init();
	//AwifiJoint.loadPage('/account/overview.htm');
	//App.loadPage('/account/accounthomepage.htm');
//App.loadPage('/calendar/index2');
});


