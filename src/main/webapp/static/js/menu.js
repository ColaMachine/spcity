var zMenu = {
	autoCollapse: true,
	animation: true,
	idName: "",
	urlName: "",
	pidName: "",
	menuName: "",
	init: function(id, data, option) {
		var ele = document.getElementById(id);
		if (ele) {
			var html = "<ul class='nav'>";
			//			document.getElementById(id).appendChild()
			this.idName = option.id;
			this.urlName = option.url;
			this.pidName = option.pid;
			this.menuName = option.name;
			for (var i = 0; i < data.length; i++) {
				if (StringUtil.isBlank(data[i][this.pidName]) || data[i][this.pidName] == 0) {
					html += this.createLi(data, data[i]);
				}
			}
			html += "</ul>";
			ele.innerHTML = html;
			this.addEvent(id);
		}
	},
	addEvent: function(id) {
		var _this = this;
		var menuWrap = document.getElementById(id);
		var aAry = document.getElementById(id).getElementsByTagName("a");
		/*for (var i = 0; i < aAry.length; i++) {
			aAry[i].onclick = function(event) {
				event.preventDefault();
				console.log("href:" + this.getAttribute("href"));
				if (isNull(this.getAttribute("href")) || this.getAttribute("href") == "null") {
					console.log("begin judge open or withdraw");
					var id = this.id;
					var ul = "m_u_" + id.substr(4);

					if (this.parentNode.className.indexOf(open) > 0) {
						this.parentNode.className = this.parentNode.className.replace("open", "");
						animation(this, "height", 0, 1000, function() {
							alert(1)
						});

					} else {
						console.log("open");
						console.log("child ul className:" + document.getElementById(ul).className);
						document.getElementById(ul).className += "open";
						animation(document.getElementById(ul), "height", 200, 1000, function() {
							alert(1);
							document.getElementById(ul).className += "open";
						});


					}


				} else {

				}
			}
		}
		return;*/
		$("#" + id).find('ul a').click(function(event) {

			event.preventDefault();
			//console.log($(".menu-wrap")[0].offsetWidth);
			if ($(".menu-wrap")[0].offsetWidth < 100) {
				//return;
			}

			if (isNull($(this).attr("href")) || $(this).attr("href") == "null") {

				var id = $(this).attr("id");
				var ul = "m_u_" + id.substr(4);
				//console.log($(this).parent().length);
				//查找当前所属的li状态是否是打开状态的
				if ($(this).parent().hasClass('open')) { //console.log("rm open");
					var that =this;
					$("#" + ul).slideUp(200, function() {$(that).parent().removeClass('open');});
					//$(this).parent().find("ul").eq(0).slideUp(200,function(){});
				} else { //console.log("add open");
					//$(".mark").removeClass("mark");
					//$(this).addClass("mark");
					//$(this).parent().parent().find('li').removeClass('open');
					//$(this).parent().parent().find('.open').find("ul").eq(0).slideUp(200);
					//打开当前节点,关闭同级节点里的open元素.

						var that =this;
					//$(this).parent().siblings().filter('.open').removeClass('open');
					//$(this).parent().find("ul").eq(0).slideDown(100);
					$("#" + ul).slideDown(200, function() {$( that).parent().addClass('open');});
					
				}
			} else {
				$(".menu-wrap").find('.active').removeClass('active');
				$(this).parent().parent().find('a').removeClass('active');
				$(this).addClass('active');
				_this.loadPage($(this).attr("href"));
			}
		});

		$('#userprofile').click(function() {
			_this.userprofile();
		});
		$('#userpasswd').click(function() {
			_this.userpasswd();
		});

	},
	loadPage: function(url, fun) {
		window.data = {};
		//截取参数
		var position = url.indexOf("?");
		if (position > 0) {
			var paramsStr = url.substring(position + 1);
			console.log("paramsStr:" + paramsStr);
			var arr = paramsStr.split("&");

			for (var i = 0; i < arr.length; i++) {
				var keyVal = arr[i].split("=");
				var key = keyVal[0];
				var val = keyVal[1];
				console.log(keyVal[0] + ":" + keyVal[1]);
				window.data[key] = val;
			}
		}
		/*$('.main-content').load(url, {"lname" : "Cai", "fname" : "Adam"}, function(){
			 $(".main-content").hide();
		    $(".main-content").fadeIn('slow');}
		  );return;*/
		//	jLoading.start();
		if(url.substr(0,1)=="/"){
		}else{
		    url="/"+url;
		}
		Ajax.get(PATH+url, null, function(data) {
            if(data.indexOf("504")!=-1){
                window.location="/spcity/login.htm";return;
            }
			$('.main').html(data);
			if (typeof fun == 'function') fun();
		});

	},
	createIcon: function() {

		return "<i class=\"material-icons\"><svg xmlns=\"http://www.w3.org/2000/svg\" width=\"48\" height=\"48\" viewBox=\"0 0 48 48\">" +
			"<rect x=\"20\" y=\"32\" width=\"8\" height=\"8\" fill=\"#0cc2aa\"></rect>" +
			"<rect x=\"8\" y=\"20\" width=\"8\" height=\"8\" fill=\"#0cc2aa\"></rect>" +
			"<rect x=\"20\" y=\"8\" width=\"8\" height=\"8\" fill=\"#0cc2aa\"></rect>" +
			"<rect x=\"32\" y=\"20\" width=\"8\" height=\"8\" fill=\"#0cc2aa\"></rect>" +
			"</svg></i>";
	},
	createLi: function(data, row) {
		var html =
			"<li ><a id=\"m_a_" + row["id"] + "\" href=\"" + row[this.urlName] + "\" ><span class='nav-icon'><i class='" + (StringUtil.isBlank(row["icon"])?"fa fa-diamond":row["icon"]) + "'></i></span><span class='nav-text'>" + row[this.menuName] + "</span>" + (isNull(row[this.urlName]) ? "<span class='nav-caret'><i class=\"fa fa-caret-down\"></i></span>" : "") + "</a><ul id=\"m_u_" + row["id"] + "\">";
		for (var i = 0; i < data.length; i++) {
			if (typeof data[i][this.pidName] != 'undefined' && data[i][this.pidName] != null && data[i][this.pidName] == row[this.idName]) { //说明有子项目
				html += this.createLi(data, data[i]);
			}
		}
		html += "</ul></li>";
		return html;
	}
}

function animation(it, attrname, finalValue, timeOut, fn) {
	var speed = 20;
	//finalValue = 100;
	var finalValues = it.childNodes;
	var totalHeight = 0;
	for (var i = 0; i < it.childNodes.length; i++) {
		totalHeight += it.childNodes[i].offsetHeight;
		//alert(it.childNodes[i].height);
	}
	finalValue = totalHeight;
	console.log("finalValue:" + finalValue);
	val = 0;
	var val = parseInt(it.style[attrname].replace("px", ""));
	it.style[attrname] = "0%";
	it.style.display = "block";
	it.style.overflowY = "hidden";
	it.style.overflowX = "hidden";
	if (val) {
		console.log("value exist:" + value);
	} else {
		val = 0;
	}
	// if( value>0){
	var distance = val - finalValue;
	var permeter = distance / timeOut * speed;

	setTimeout(function() {
		// it.style[attrname]=it.style[attrname]-permeter;
		// it.setAttribute(attrname,it.getAttribute(attrname)-permeter);
		changeAttr(it, attrname, val, permeter, finalValue, fn, speed);
	}, speed);
	//  }
}

function changeAttr(it, attrname, val, permeter, finalValue, fn, speed) {
	val -= permeter;
	console.log("value:" + val);
	if (Math.abs(val - finalValue) < Math.abs(permeter)) {
		//it.setAttribute(attrname,finalValue);
		it.style[attrname] = finalValue + "px";
		it.style.overflowY = "";
		it.style.overflowX = "";

		fn.call();
	} else {
		//   it.setAttribute(attrname,value);
		it.style["height"] = val + "px";
		console.log(attrname + ":" + it.style[attrname]);
		setTimeout(function() {
			//  it.setAttribute(attrname,it.getAttribute(attrname)-permeter);
			changeAttr(it, attrname, val, permeter, finalValue, fn);
		}, speed);
	}
}