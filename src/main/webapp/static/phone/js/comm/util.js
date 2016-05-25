
HTMLElement.prototype.appendHTML = function(html) {
    var divTemp = document.createElement("div"),
    		nodes = null,
        // 文档片段，一次性append，提高性能
        fragment = document.createDocumentFragment();
    divTemp.innerHTML = html;
    nodes = divTemp.childNodes;
    for (var i=0, length=nodes.length; i<length; i+=1) {
       fragment.appendChild(nodes[i].cloneNode(true));
    }
    this.appendChild(fragment);
    // 据说下面这样子世界会更清净
    nodes = null;
    fragment = null;
};

var prependHTML = function(el, html) {
    var divTemp = document.createElement("div"), nodes = null
        , fragment = document.createDocumentFragment();

    divTemp.innerHTML = html;
    nodes = divTemp.childNodes;
    for (var i=0, length=nodes.length; i<length; i+=1) {
        fragment.appendChild(nodes[i].cloneNode(true));
    }
    // 插入到容器的前面 - 差异所在
    el.insertBefore(fragment, el.firstChild);
    // 内存回收？
    nodes = null;
    fragment = null;
};

// 反科里化
function uncurryThis(fn) {
    return function() {
        return Function.call.apply( fn, arguments );
    };
}

// 字符格式化函数
(function(window){
	var format = function(){
		if (arguments.length == 0){
			return '';
		} else {
			if (arguments.length == 1){
				return arguments[0];
			} else {
				var toReplace = arguments[0];
				for(var index = 1; index < arguments.length; index++){
					toReplace = toReplace.replace('{'+ (index-1) +'}', arguments[index]);
				}
				return toReplace;
			}
		}
	};

	window.format = window.format || format;
})(window);


(function(window){

	var tipHtml = '<div class="tip small-text" id="tip" style="{{style}}"><div class="tip-warp"">{{text}}</div></div>';

	var tip = function(text){
        if(document.getElementById('tip')){
        }

        var leftPx = document.body.scrollWidth / 2;
        var topPx = window.screen.availHeight / 2;

		var html = tipHtml.replace('{{text}}', text).replace('{{style}}', 'left:'+(leftPx-(text.length*109/7)/2)+'px;top:'+topPx+'px');
        $('body').append(html);

		setTimeout(function(){
			$('#tip').remove();
		}, 2000);
		return false;
	};

	window.tip = window.tip || tip;
})(window);



(function(window){


	var loadingHtml =
        '<div class="loading" id="loading" style="{{height}}">'+
            '<div class="loading-warp" style="{{style}}">'+
                '<div class="loading-content"><span class="loading-circle loading-circle-one"></span></div>'+
                '<div class="loading-content"><span class="loading-circle loading-circle-two"></span></div>'+
                '<div class="loading-content"><span class="loading-circle loading-circle-three"></span></div>'+
            '</div>'+
        '</div>';

    var loading = {
        topPx: 0,
        heightPx: 0,

        getHeight: function(){
            return document.body.clientHeight + 60;
        },

        init: function(){
            var self = this;
            self.topPx = window.screen.availHeight / 2 + window.scrollY;
            self.heightPx = self.getHeight();
        },

        show: function(height){
            var self = this;
            self.init();
            height = height || self.heightPx;
            var leftPx = document.body.scrollWidth / 2 - 70/2;
            var html = loadingHtml.replace('{{style}}', 'margin-top:'+self.topPx+'px;left:'+leftPx+'px').replace('{{height}}', 'height:'+ self.heightPx +'px');
            $('body').css({
                'overflow': 'hidden'
            }).append(html);
        },

        hide: function(){
            $('#loading').remove();
            $('body').css({
                'overflow': ''
            })
        }
    };

	window.loading = window.loading || loading;
})(window);