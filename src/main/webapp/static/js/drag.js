
/* jDrag For jQuery 1.8+ */
;(function($){
	var jDragObj = null;
	var jDragContainer = '';
	var jDragMouseX, jDragMouseY, jDragElemTop, jDragElemLeft;
	var jDragOnDrop = null;
	var jDragOnMove = null;
	$.jDragSetPos = function(event){
		var pos = $.getMousePosition(event);
		
		var X = (pos.x - jDragMouseX);//偏移量
		var Y = (pos.y - jDragMouseY);		//偏移量
		if(jDragContainer != '' && $(jDragContainer).length == 1){
		
			var conpos = $(jDragContainer).position();
			var minleft = conpos.left;
			minleft += isNaN(parseInt($(jDragContainer).css('margin-left'))) ? 0 : parseInt($(jDragContainer).css('margin-left'));
			minleft += isNaN(parseInt($(jDragContainer).css('border-left-width'))) ? 0 : parseInt($(jDragContainer).css('border-left-width'));
			
			var maxleft = minleft + $(jDragContainer).innerWidth() - $(jDragObj).outerWidth();
			var mintop = conpos.top;
			mintop += isNaN(parseInt($(jDragContainer).css('margin-top'))) ? 0 : parseInt($(jDragContainer).css('margin-top'));
			mintop += isNaN(parseInt($(jDragContainer).css('border-top-width'))) ? 0 : parseInt($(jDragContainer).css('border-top-width'));
			var maxtop = mintop + $(jDragContainer).innerHeight() - $(jDragObj).outerHeight();
			var newY = jDragElemTop + Y;
			var newX =jDragElemLeft + X;
			newX-= isNaN(parseInt($(jDragObj).css('margin-left'))) ? 0 : parseInt($(jDragObj).css('margin-left'));;
			newY-= isNaN(parseInt($(jDragObj).css('margin-top'))) ? 0 : parseInt($(jDragObj).css('margin-top'));;
			if(newX < minleft) newX = minleft;
			if(newX > maxleft) newX = maxleft;
			if(newY < mintop) newY = mintop;
			if(newY > maxtop) newY = maxtop;
			//console.log("minleft"+minleft);
			console.log("maxleft"+maxleft);
			//console.log("jDragMouseX"+jDragMouseX);
			//console.log("pos.x"+pos.x);
			//console.log("marginleft"+(isNaN(parseInt($(jDragContainer).css('margin-left'))) ? 0 : parseInt($(jDragContainer).css('margin-left'))));
			//console.log("marginleft"+$(jDragContainer).css('margin-left'));
			//console.log("jDragContainer"+$(jDragContainer).attr('id'));
			//console.log("X"+X);
			//console.log("jDragElemLeft"+jDragElemLeft);
			//console.log("top"+newY+"left"+newX);
			$(jDragObj).css("top", newY);
			$(jDragObj).css("left", newX);
		}else{
			$(jDragObj).css("top", (jDragElemTop + Y));
			$(jDragObj).css("left", (jDragElemLeft + X));
		}
	};
	$(document).mousemove(function(event){
		event.stopPropagation();
		if(jDragObj != null){
			$.jDragSetPos(event);
			if(typeof(jDragOnMove) == 'function') jDragOnMove();
			return false;
		}
	});
	$(document).mouseup(function(event){
		event.stopPropagation();
		if(jDragObj != null){
			jDragObj = null;
			if(typeof(jDragOnDrop) == 'function') jDragOnDrop();
			return false;
		}
	});
	$.fn.extend({
		jDrag: function(options){
			var defaults = {
				container	: '',
				handle		: '',
				cursor		: 'move',
				ondrag		: null,
				onmove		: null,
				ondrop		: null
			};
			var opts = $.extend(defaults, options);
			return this.each(function(){
				//$(this).css('z-index', '10000');
				var $handle = (opts.handle == '') ? $(this) : $(this).find(opts.handle);
				$handle.css('cursor', opts.cursor);
				var dragobj = this;
				$handle.mousedown(function(event){
					event.stopPropagation();
					jDragObj = dragobj;
					jDragContainer = opts.container;
					$(dragobj).css('position','absolute');
					var pos = $.getMousePosition(event);
					jDragMouseX = pos.x;
					jDragMouseY = pos.y;
					jDragElemTop  = dragobj.offsetTop;
					jDragElemLeft = dragobj.offsetLeft;
					
					$.jDragSetPos(event);
					if(typeof(opts.ondrop) == 'function') jDragOnDrop = opts.ondrop;
					if(typeof(opts.onmove) == 'function') jDragOnMove = opts.onmove;
					if(typeof(opts.ondrag) == 'function') opts.ondrag();
					return false;
				});
			});
		}
	});
})(jQuery);


//Get Mouse Position
jQuery.getMousePosition = function(e){
	var posx = 0;
	var posy = 0;
	if(!e) var e = window.event;
	if(e.pageX || e.pageY){
		posx = e.pageX;
		posy = e.pageY;
	}else if(e.clientX || e.clientY){
		posx = e.clientX + document.body.scrollLeft + document.documentElement.scrollLeft;
		posy = e.clientY + document.body.scrollTop  + document.documentElement.scrollTop;
	}
	return {'x':posx, 'y':posy};
};
