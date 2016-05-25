/**
 * photographs
 * @authors Ping YF (koakumaping@163.com)
 * @date    2015-11-10 10:38:51
 * @version $Id$
 */


$(function() {
	$(".loading-img").scrollLoading({
		// container: $("#images"),
		callback: function() {
			console.log($(this).attr('src'));
		}
	});
	
});