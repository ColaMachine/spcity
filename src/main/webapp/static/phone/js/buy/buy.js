$(function() {
	$("#buy").on("click",function(){
		$('.mask').show();
		$('.modal').show();
	});
	$("#cancel").on("click",function(){
		$('.mask').hide();
		$('.modal').hide();
	});
});