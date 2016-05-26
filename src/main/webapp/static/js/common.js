/**
 * 
 * 
 * Author: zzw <zzw1986@gmail.com>
 * 
 * 
 * File: common.js Create Date: 2014-04-10 19:54:40
 * 
 * 
 */

// AJAX璋冪敤 濡傦細ACWS.ajax('common/service/UserSelect/Init', inputData,
// afterInit,{async:false});

String.prototype.trim= function(){  
    // 用正则表达式将前后空格  
    // 用空字符串替代。  
    return this.replace(/(^\s*)|(\s*$)/g, "");  
}
var PATH="/spcity";

function AjaxClass()
{
    var XmlHttp = false;
    try
    {
        XmlHttp = new XMLHttpRequest();        //FireFox专有
    }
    catch(e)
    {
        try
        {
            XmlHttp = new ActiveXObject("MSXML2.XMLHTTP");
        }
        catch(e2)
        {
            try
            {
                XmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            catch(e3)
            {
                alert("你的浏览器不支持XMLHTTP对象，请升级到IE6以上版本！");
                XmlHttp = false;
            }
        }
    }

    var me = this;
    this.Method = "POST";
    this.Url = "";
    this.Async = true;
    this.Arg = "";
    this.dataType="";
    this.data=null;
    this.CallBack = function(){};
    this.Loading = function(){};

    this.Send = function()
    {
        if (this.Url=="")
        {
            return false;
        }
        if (!XmlHttp)
        {
            return IframePost();
        }
        if(this.data){
            var arr=new Array();
                        for(var key in this.data){
                            arr.push(key+"="+this.data[key]);
                        }
                        this.data= arr.join("&");

        }
        if(this.Method=="GET"&&this.data){

            if(this.Url.indexOf("?")!=-1){
               if(this.Url.indexOf("=")!=-1){
                    this.Url+="&"+arr.join("&");
               }else{
                 this.Url+=arr.join("&");
               }

            }else{
                this.Url+="?"+arr.join("&");
            }
        }

        XmlHttp.open (this.Method, this.Url, this.Async);
        if (this.Method=="POST")
        {
            XmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        }
       if (this.dataType=="JSON")
            {
                XmlHttp.setRequestHeader("Content-Type","application/json");

            }
        XmlHttp.onreadystatechange = function()
        {
            if (XmlHttp.readyState==4)
            {
                var Result = false;
                if (XmlHttp.status==200)
                {
                    Result = XmlHttp.responseText;
                }
                XmlHttp = null;
                //alert(typeof Result)
                me.CallBack(Result);
            }
             else
             {
                me.Loading();
             }
        }
        if (this.Method=="POST")
        {
            XmlHttp.send(this.data);
        }
        else
        {
            XmlHttp.send(null);
        }
    }

    //Iframe方式提交
    function IframePost()
    {
        var Num = 0;
        var obj = document.createElement("iframe");
        obj.attachEvent("onload",function(){ me.CallBack(obj.contentWindow.document.body.innerHTML); obj.removeNode() });
        obj.attachEvent("onreadystatechange",function(){ if (Num>=5) {alert(false);obj.removeNode()} });
        obj.src = me.Url;
        obj.style.display = 'none';
        document.body.appendChild(obj);
    }
}
function ajax(options){



/*----------------------------调用方法------------------------------
    var Ajax = new AjaxClass();         // 创建AJAX对象
    Ajax.Method = "POST";               // 设置请求方式为POST
    Ajax.Url = "default.asp"            // URL为default.asp
    Ajax.Async = true;                  // 是否异步
    Ajax.Arg = "a=1&b=2";               // POST的参数
    Ajax.Loading = function(){          //等待函数
        document.write("loading...");
    }
    Ajax.CallBack = function(str)       // 回调函数
    {
        document.write(str);
    }
    Ajax.Send();                        // 发送请求
   -----------------------------------------------------------
    var Ajax = new AjaxClass();         // 创建AJAX对象
    Ajax.Method = "GET";                // 设置请求方式为POST
    Ajax.Url = "default.asp?a=1&b=2"    // URL为default.asp
    Ajax.Async = true;                  // 是否异步
    Ajax.Loading = function(){          //等待函数
        document.write("loading...");
    }
    Ajax.CallBack = function(str)       // 回调函数
    {
        document.write(str);
    }
    Ajax.Send();
    --------------------------------------------------------------------*/
    //1.创建对象

      var Ajax = new AjaxClass();         // 创建AJAX对象
        Ajax.Method = options.type;                // 设置请求方式为POST
        Ajax.Url = options.url;    // URL为default.asp
        Ajax.Async = true;                  // 是否异步
        Ajax.dataType =options.dataType;                  // 是否异步
        Ajax.data =options.data;

        Ajax.Loading = function(){          //等待函数
           // document.write("loading...");
        }
        Ajax.CallBack = options.success ;
        Ajax.Send();

}
var Ajax={
 get:function(url,data,callback){
    this.AjaxFun(url,data,callback,{type:"GET"});
 },
 getJSON:function(url,data,callback){
    this.AjaxFun(url,data,callback,{type:"GET",dataType:"JSON"});
 },
 post:function(url,data,callback){
    this.AjaxFun(url,data,callback,{type:"POST"});
 },
 AjaxFun:function (url, inputData, callback, options, callbackOnError) {
         	var contextUrl = window.location.href;
         	options = options || {};
         	options.url =  url;
         	options.type = options.type||"POST";
         	options.data = inputData;
         	options.contentType="application/x-www-form-urlencoded; charset=utf-8";
         	//options.data = encodeURIComponent(JSON.stringify(inputData));
         	if (typeof options.async == 'undifined')
         		options.async = false;
         	var param = options.inputData;
         	options.success = function(outputData) {//alert("success")
         		if(outputData.r=="504"){
         			window.location=PATH+"/login.htm";
         		}
         		if (typeof callback == 'function') {
         			callback(outputData);
         		}
         	};
         	options.error = function(jqXHR, textStatus, errorThrown) {//alert("error")
         		// $("body").unmask();
         		var responseText = jqXHR.responseText || "";
         		if ((jqXHR.status == 500 || jqXHR.status == 1000)
         				&& responseText.indexOf("dwr.engine.http.1000") >= 0) {
         			top.location.replace(acwsContext + '/acwsui/pages/logout.htm');// /j_acegi_logout
         			return;
         		}
         		if (typeof callbackOnError == 'function') {
         			callbackOnError(errorThrown);
         		} else {//alert(typeof callbackOnError);
         			alert('参数不是function');
         		}
         	};
         	delete options['inputData'];
         	//ajax(options);
         	$.ajax(options);
         }
};
/*function Get(url,data,callback){
	AjaxFun(url,data,callback,{type:"get"});
}
function GetJSON(url,data,callback){
	AjaxFun(url,data,callback,{type:"get",dataType:"json"});
}
function Post(url,data,callback){
	AjaxFun(url,data,callback);
}*/
/**
 * 
 */
function changeForm2JsoTemp(formId) {
	var formNode = document.getElementById(formId);
	var elements = formNode.childNodes;
	var jso = {};
	for (var i = 0; i < elements.length; i++) {
		var ele = elements[i];
		if (typeof ele != 'undefineded') {
			if (ele.tagName.toUpperCase() == 'INPUT') {
				jso[ele.id] = ele.value;
			} else if (ele.tagName.toUpperCase() == 'SELECT') {
				jso[ele.id] = ele.value;
			} else if (ele.tagName.toUpperCase() == 'CHECKBOX') {
				if (ele.checked) {
					jso[ele.id] = true;
				} else {
					jso[ele.id] = false;
				}
			} else if (ele.tagName == 'TEXTAREA') {
				jso[ele.id] = ele.value;
			}

		}
	}
	return jso;
}




function changeForm2Jso(formId) {
	var jso = {};
	var arr = $( formId).serializeArray();
	for (var i = 0; i < arr.length; i++) {
		jso["" + arr[i].name] = arr[i].value;
	}
	return jso;
}
function fillJso2Form(formId,jso){
	var arr = $(formId).serializeArray();
	for (var i = 0; i < arr.length; i++) {
		var dom = $(formId).find("[name='"+ arr[i].name+"']");
		if(dom !=null && typeof jso[arr[i].name] !='undefined'){
			var val="";
			if(dom.attr("datatype")=="date"&& /^\d*$/.test(jso[ arr[i].name])){
				var format = dom.attr("format");
				val =new Date(jso[arr[i].name]).format(format);
			}else{
				val=jso[ arr[i].name];
			}
			if(dom.is('span')){
				dom.text(val);
			}else{
				dom.val(val);
			}
		}
	}
}
function fillJso2FormSpan(formId,jso){
	for(var key in jso){
		var dom = $(formId).find("#"+key);
		if(dom !=null ){
			var val="";

			if(dom.attr("datatype")=="date"&& /^\d*$/.test(jso[key])){
				var format = dom.attr("format");
				val =new Date(jso[key]).format(format);
			}else if(dom.attr("datatype")=="map"/*&& /^\d*$/.test(jso[key])*/){

				console.log(dom.attr("data"));
				var map = eval("("+dom.attr("data")+")");

				val=map[jso[key]];

			}else{
				val=jso[key];
			}
			if(dom.is('span')){
				dom.text(val);
			}
		}
	}
}
/**
 * 判断是否为空
 */
function isNull(str) {
		if(typeof str != 'string'){
			//alert("isNull 使用错误,参数是" +(typeof str));
			if(typeof str == 'undefined' || null == str){
				return true
			}else{
				return false;
			}
		}else 
	if (typeof str == 'undefined' || str == null || str.trim() == '')
		return true;
	else
		return false;

}

/**
 * 限制字符长度
 * 
 * @param {Object}
 *            it
 */
function limitLength(it, maxlength) {
	if (it.value.length > maxlength) {
		// _alert("您输入的内容已经超过"+maxlength+"个字!");
		it.value = it.value.substr(0, maxlength);
	}
}

function handlerAjaxReturnAndGoUrl(data, url) {
	if (data.result) {
		window.location = url;
	} else {
		dialog.alert(data.msg);
	}
}
function handlerAjaxReturnAndExeFun(data, fun) {
	if (data.result) {
		fun();
	} else {
		dialog.alert(data.msg);
	}
}
/**-------------地市县区多选框插件----------------------------------------------**/
var AreaBox = {
		it:null,
		o:null,
		wrap:null,
		sel:null,
		sProvince:null,//select 控件
		sCity:null,
		container:null,
		sCounty:null,
		
		dataStore:null,
		init : function(it, o) {
		
		this.it=it;
		this.o = $.extend({

			sData : null,
			outputId : null
		}, o || {});
		this. wrap = $(it);
		this.wrap
				.append($("<div class='col-sm-12' id='zareamcbox'>"
						+ "<select style='width:80px'    ></select> "
						+ "<select  style='width:80px'    ></select>"
						+ "<select  style='width:80px'  ></select>"
						+ "<button type='button' class='btn btn-sm' style='height:30px'>"
						+ "<i class='icon-plus align-top bigger-125'></i>添加"
						+ "</button>	"
						+ "</div>"
						+ "<input type='text' name='selected-areas' id='selected-areas' value='' placeholder='请选择地区 ...' style='display: none;'/>"));

		var sel = $("select", this.wrap);// $("select",wrap);
		this.sProvince = sel.eq(0);// $("#provinceSelect");
		 this.sCity = sel.eq(1);// $("#citySelect");//sel.eq(1);
		 this.sCounty = sel.eq(2);
		// var loc = new Location();
		 this.sProvince.empty();
		 this.sCity.empty();
		 this.sCounty.empty();
		// loc.fillOption(sProvince , '0',null);
		// loc.fillOption(sCity , '0,'+sProvince.val(),null);
		var addButton = $("button",this. wrap).eq(0);
		var sPval, sCval;
		this. container = $(".tags").eq(0);
		this. dataStore = {};
		// 添加已经有的数据到box中
		for (var i = 0; i < o.sData.length; i++) {
			this.addArea(o.sData[i]);
		}
		addButton.click(function(area) {
			
			return function(){
			// addArea(sProvince.val()+"|"+ sCity.val());
			var name = "";
			if (area.sCounty && area.sCounty.val()) {
				name = area.sProvince.find("option:selected").text() + "|"
						+ area.sCity.find("option:selected").text() + "|"
						+ area.sCounty.find("option:selected").text();
			} else if (area.sCity.val()) {
				name = area.sProvince.find("option:selected").text() + "|"
						+ area.sCity.find("option:selected").text();
			} else {
				name = area.sProvince.find("option:selected").text();
			}
			area.addArea(name);
		};}(this));

		this.sProvince.change(function(area) {
			return function(){
				area.sCity.empty();
				area.getCityForMulti();
			};
		}(this));
		if (this.sCounty)
			this.sCity.change(function(area) {
				return function(){
				area.sCounty.empty();
				area.getCountyForMulti();
				};
			}(this));
		this.getProvinceForMulti();
	},

	getProvinceForMulti : function() {
			
		$.post(PATH + "/member/getProvince", {}, function(area) {
			return function(data){
			jQuery("<option value='0'>全国</option>").appendTo(area.sProvince);
			for (var i = 0, length = data.list.length; i < length; i++) {
				jQuery(
						"<option value='" + data.list[i] + "'>" + data.list[i]
								+ "</option>").appendTo(area.sProvince);
			}
			area.getCityForMulti();
			};
		}(this));
	},

	getCityForMulti : function() {
		$.post(PATH + "/member/getCity", {
			areaname : this.sProvince.val()
		}, function(area) {
			return function(data){
				area.sCity.empty();// alert(sProvince.val());
			 $("<option value=''>--请选择--</option>").appendTo( area.sCity);
			for (var i = 0, length = data.list.length; i < length; i++) {

				jQuery(
						"<option value='" + data.list[i] + "'>" + data.list[i]
								+ "</option>").appendTo( area.sCity);
			}
			if (area.sCounty)
				area.getCountyForMulti();
			};
		}(this));
	},

	getCountyForMulti : function() {
		$.post(PATH + "/member/getCounty", {
			areaname : this.sCity.val()
		}, function(area) {
			return function(data){
				area.sCounty.empty();// alert(sProvince.val());
			$("<option value=''>--请选择--</option>").appendTo(area.sCounty);
			for (var i = 0, length = data.list.length; i < length; i++) {
				jQuery(
						"<option value='" + data.list[i] + "'>" + data.list[i]
								+ "</option>").appendTo(area.sCounty);
			}
			};
		}(this));
	},
	/*
	 * sProvince.change(function() {//alert("sProvincechange"); sCity.empty();
	 * loc.fillOption(sCity , '0,'+sProvince.val()); });
	 */
	addArea : function(name) {
		if (isNull(name))
			return;
		var _province = "";
		var _city = "";
		var _county = "";
		if (name == "全国" && $(this.container).find(".tag").length > 0) {
			dialog.alert("你已经选择了地区,无法添加全国");
			return;
		}
		if ($(this.container).find(".tag[name='全国']").length > 0) {
			dialog.alert("你已经选择全国,无需添加其他地区");
			return;
		}
		// 添加的只是省
		// 添加的是全国
		if (name == "全国") {
			_province = name.trim();
		} else if (name.indexOf("|") <= 0) {
			_province = name.trim();

			if (this.dataStore[_province]) {
				dialog.alert("你已经选择了省份,不能再添加");
				return;
			}
		} else {// 添加的是省市
			if (name.lastIndexOf("|") > name.indexOf("|")) {// 含有区
				_county = name.split("|")[2];
			}
			_province = name.split("|")[0];
			_city = name.split("|")[1];
		}
		
		//选择的是省
		if (this.dataStore[_province] && this.dataStore[_province]["all"]) {
			dialog.alert("你已经选择了省份,不能再添加");
			return;
		}
		//有选择市
		if (_city) {
			if (this.dataStore[_province] && this.dataStore[_province][_city]
					&& this.dataStore[_province][_city]["all"]) {
				dialog.alert("你已经选择了省市,不能再添加");
				return;
			}
			if (!_county)
			if (this.dataStore[_province] && this.dataStore[_province][_city]) {
				for (k in this.dataStore[_province][_city]) {
					dialog.alert("你已经选择了省市,不能再添加");
					return;
				}
			}
			
		}
		//有选择区
		if (_county) {
			if (this.dataStore[_province] && this.dataStore[_province][_city]
					&& this.dataStore[_province][_city]["all"]) {
				dialog.alert("你已经选择了上级区域,不能再添加");
				return;
			}
			if (this.dataStore[_province] && this.dataStore[_province][_city]
				&& this.dataStore[_province][_city][_county]) {
					dialog.alert("你已经选择了该省市区,不能再添加");
					return;
			}
			
		}
		/*if (_county) {// 加的是省市区
			if (this.dataStore[_province] && this.dataStore[_province]["all"]) {
				alert("数据冲突！");
				return;
			}
			if (this.dataStore[_province] && this.dataStore[_province][_city]
					&& this.dataStore[_province][_city]["all"]) {
				alert("数据冲突！");
				return;
			}
			if (this.dataStore[_province] && this.dataStore[_province][_city]
					&& this.dataStore[_province][_city][_county]) {
				alert("数据冲突！");
				return;
			}
			// this.dataStore[_province][_city][_county]=1;
		} else if (_city) {// 加的是省市
			if (this.dataStore[_province] && this.dataStore[_province]["all"]) {
				alert("数据冲突！");
				return;
			}
			if (this.dataStore[_province] && this.dataStore[_province][_city]
					&& this.dataStore[_province][_city]["all"]) {
				alert("数据冲突！");
				return;
			}
			if (this.dataStore[_province] && this.dataStore[_province][_city]) {
				for (k in this.dataStore[_province][_city]) {
					alert("数据冲突!");
					return;
				}
			}
			// this.dataStore[_province][_city]["all"]=1;
		} else if (_province) {// 加的是省
			if (this.dataStore[_province]) {
				for (k in this.dataStore[_province]) {
					alert("数据冲突!");
					return;
				}
			}

		}*/
		var str = "<span name='"
				+ name
				+ "' class='tag'>"
				+ name
				+ "<button type='button'  class='close'>x</button></span>";
		// 判断是否有数据了
		var ele = $(str);
		this.container.append(ele);
		$("button", ele).click(function(area) {
			return function(){
			area.removeArea(this);
			}
		}(this));
		
		if (_county) {
			if (!this.dataStore[_province]) {
				this.dataStore[_province] = {};
			}
			if (!this.dataStore[_province][_city]) {
				this.dataStore[_province][_city] = {};
			}
			this.dataStore[_province][_city][_county] = 1;
		} else if (_city) {
			if (!this.dataStore[_province]) {
				this.dataStore[_province] = {};
			}
			if (!this.dataStore[_province][_city]) {
				this.dataStore[_province][_city] = {};
			}
			this.dataStore[_province][_city]['all'] = 1;
		} else {
			if (!this.dataStore[_province]) {
				this.dataStore[_province] = {};
			}
			this.dataStore[_province]["all"] = 1;
		}
		this.writeInput();
	},

	removeArea : function(it) {
		var name = $(it).parent().attr("name");
		var _province = "";
		var _city = "";
		var _county = "";

		// 添加的只是省
		if (name == "全国") {
			_province = name.trim();
		} else if (name.indexOf("|") <= 0) {
			_province = name.trim();

		} else {// 添加的是省市
			if (name.lastIndexOf("|") > name.indexOf("|")) {// 含有区
				_county = name.split("|")[2];
			}
			_province = name.split("|")[0];
			_city = name.split("|")[1];
		}

		if (_county) {
			this.dataStore[_province][_city][_county] = null;
			delete this.dataStore[_province][_city][_county];
		} else if (_city) {
			this.dataStore[_province][_city]['all'] = null;
			delete this.dataStore[_province][_city]['all'];
		} else {
			this.dataStore[_province]['all'] = null;
			delete this.dataStore[_province]['all'];
		}
		$(it).parent().remove();
		this.writeInput();
	},

	writeInput : function() {
		var areaNameArr = new Array();
		this.wrap.find(".tag").each(function() {
			areaNameArr.push($(this).attr("name"));
		});
		$('#' + this.o.outputId).val(areaNameArr.join(","));
	},
	viewMode:function(){
		this.sProvince.hide();
		this.sCity.hide();
		this.sCounty.hide();
		$("button",this.wrap).hide();
	},
	getSeleteds : function() {
		var arr = new Array();
		$(this.wrap).find(".tag").each(function() {
			arr.push($(this).attr("name"));
		});

		return arr.join(",");
	}
};
/*$.fn.zAreaMCBox=function(o){
	var areaBox =  Object.create(AreaBox);
	areaBox.init(this,o);
	return areaBox;
}
;
*/
/**-------------地市县区多选框插件end----------------------------------------------**/
function getCellValue(id, index) {
	return $("input:checkbox[value='" + id + "']").parent().parent().parent()
			.find("td").eq(index).text();
}

function goPage(currentPage, everyPage) {
	console.log(currentPage);
	zMenu.loadPage(currentPage);
	return;
//	window.location.href = $("#searchForm")[0].action + "?fy=1&currentPage="
//			+ currentPage + "&everyPage=" + everyPage;
}
function openMenu(id) {
	$("#" + id).addClass("open");
	$("#" + id).find("ul").css("display", "block");
}

function openMenu(level1, level2) {

}
function getAuditHtml(value,module) {
	var strs= "";
	for(var i=0;i<arguments.length;i++){
		strs+="'"+arguments[i]+"',";
	}
	return "<a style='margin-left:2px' href=\"javascript:void(0)\" onclick=\""	+(module?module+".":"")+"auditInfo("+strs.substr(1)+")\" >审核</a>";
}
function getEditHtml(value,module) {
	var strs= "";
	for(var i=0;i<arguments.length;i++){
		strs+=",'"+arguments[i]+"'";
	}
	return "<a style='margin-left:2px' href=\"javascript:void(0)\" onclick=\""	+(module?module+".":"")+"editInfo("+strs.substr(1)+")\" >修改</a>";
}
function getDelHtml(value,module) {
	var strs= "";
	for(var i=0;i<arguments.length;i++){
		strs+=",'"+arguments[i]+"'";
	}
	return "<a style='margin-left:2px' href=\"javascript:void(0)\" onclick=\""	+(module?module+".":"")+"deleteInfo("+strs.substr(1)+")\" >删除</a>";
}
function getViewHtml(value,module) {
	var strs= "";
	for(var i=0;i<arguments.length;i++){
		strs+=",'"+arguments[i]+"'";
	}
    return "<a style='margin-left:2px' href=\"javascript:void(0)\" onclick=\""	+(module?module+".":"")+"viewInfo("+strs.substr(1)+")\" >查看</a>";
}
function getLockHtml(value,module) {
	var strs= "";
	for(var i=0;i<arguments.length;i++){
		strs+=",'"+arguments[i]+"'";
	}
	return "<a style='margin-left:2px' href=\"javascript:void(0)\" onclick=\""	+(module?module+".":"")+"lockinfo("+strs.substr(1)+")\" >禁用</a>";
	}
function getUnLockHtml(value,module) {
	var strs= "";
	for(var i=0;i<arguments.length;i++){
		strs+=",'"+arguments[i]+"'";
	}
	return "<a style='margin-left:2px' href=\"javascript:void(0)\" onclick=\""	+(module?module+".":"")+"unlockinfo("+strs.substr(1)+")\" >启用</a>";
	}


var gridHelper={
		getAuditHtml:function(value,module) {
			var strs= "";
			for(var i=0;i<arguments.length;i++){
				strs+="'"+arguments[i]+"',";
			}
			return "<a style='margin-left:2px' href=\"javascript:void(0)\" onclick=\""	+(module?module+".":"")+"auditInfo("+strs.substr(0,strs.length-1)+")\" >审核</a>";
		},
		getEditHtml:function(value,module) {
			var strs= "";
			for(var i=0;i<arguments.length;i++){
				strs+=",'"+arguments[i]+"'";
			}
			return "<a style='margin-left:2px' href=\"javascript:void(0)\" onclick=\""	+(module?module+".":"")+"editInfo("+strs.substr(1)+")\" >修改</a>";
		},
		getDelHtml:function(value,module) {
			var strs= "";
			for(var i=0;i<arguments.length;i++){
				strs+=",'"+arguments[i]+"'";
			}
			return "<a style='margin-left:2px' href=\"javascript:void(0)\" onclick=\""	+(module?module+".":"")+"deleteInfo("+strs.substr(1)+")\" >删除</a>";
		}
		,getViewHtml:function(value,module) {
			var strs= "";
			for(var i=0;i<arguments.length;i++){
				strs+=",'"+arguments[i]+"'";
			}
		    return "<a style='margin-left:2px' href=\"javascript:void(0)\" onclick=\""	+(module?module+".":"")+"viewInfo("+strs.substr(1)+")\" >查看</a>";
		}
}
var Auth={
    hasResource:function(Str){
        if(resources.indexOf(Str)>-1){
            return true;
        }else{
            return false;
        }
    }
}
/*function getEditHtml1(value) {
	return "<div title=\"\" style=\"float:left;cursor:pointer;\" class=\"ui-pg-div ui-inline-edit\" id=\"jEditButton_1\" onclick=\"editinfo('"
			+ value
			+ "')\" onmouseover=\"jQuery(this).addClass('ui-state-hover');\" onmouseout=\"jQuery(this).removeClass('ui-state-hover')\" data-original-title=\"编辑所选行\"><span class=\"ui-icon ui-icon-pencil\"></span></div>";
}


function getDelHtml1(value) {
	return "<div title=\"\" style=\"float:left;cursor:pointer;\" class=\"ui-pg-div ui-inline-edit\" id=\"jEditButton_1\" onclick=\"deleteinfo('"
			+ value
			+ "')\" onmouseover=\"jQuery(this).addClass('ui-state-hover');\" onmouseout=\"jQuery(this).removeClass('ui-state-hover')\" data-original-title=\"删除所选行\"><span class=\"ui-icon ui-icon-trash\"></span></div>";
}
function getViewHtml1(value) {
	return "<div title=\"\" style=\"float:left;cursor:pointer;\" class=\"ui-pg-div ui-inline-edit\" id=\"jEditButton_1\" onclick=\"viewinfo('"
			+ value
			+ "')\" onmouseover=\"jQuery(this).addClass('ui-state-hover');\" onmouseout=\"jQuery(this).removeClass('ui-state-hover')\" data-original-title=\"查看所选行\"><span class=\"ui-icon icon-zoom-in\"></span></div>";
}
function getLockHtml1(value) {
	return "<div title=\"\" style=\"float:left;cursor:pointer;padding-top:3px\" class=\"ui-pg-div ui-inline-edit\" id=\"jEditButton_1\" onclick=\"lockinfo('"
			+ value
			+ "')\" onmouseover=\"jQuery(this).addClass('ui-state-hover');\" onmouseout=\"jQuery(this).removeClass('ui-state-hover')\" data-original-title=\"禁用\"><span class=\"ui-icon icon-lock \"></span></div>";
}
function getUnLockHtml1(value) {
	return "<div title=\"\" style=\"float:left;cursor:pointer;padding-top:3px\" class=\"ui-pg-div ui-inline-edit\" id=\"jEditButton_1\" onclick=\"unlockinfo('"
			+ value
			+ "')\" onmouseover=\"jQuery(this).addClass('ui-state-hover');\" onmouseout=\"jQuery(this).removeClass('ui-state-hover')\" data-original-title=\"启用\"><span class=\"ui-icon icon-unlock \"></span></div>";
}
*/
//var aiwifi = {};
//aiwifi.alert = bootbox.alert;
//aiwifi.confirm = bootbox.confirm;
function showMask() {
	$("#maskModal").modal('show');
}
function hideMask() {
	$("#maskModal").modal('hide');
}

/*function showMsg(caption, contenttext) {
	$("#errormsg").remove();
	var str = "<div id='errormsg' class='alert alert-warning' id='errormsg' >"
			+ "<a href='#' class='close' data-dismiss='alert'> &times; </a> <strong>"
			+ caption + "</strong>" + contenttext + "</div>";
	$("form").prepend(str);
}*/
function showMsg(caption, contenttext) {
	
	dialog.alert(contenttext);
}

function initProCitySel(jso) {
	var defProvince = jso.defProvince;
	var defCity = jso.defCity;
	var provinceId = jso.provinceId;
	var cityId = jso.cityId;

	$("#" + provinceId).change(function() {
		getCity(cityId, provinceId);
	})

	// 初始化省
	$.get(PATH + "/member/getProvince", {}, function(data) {

		jQuery("<option value=''>--请选择--</option>").appendTo("#" + provinceId);
		for (var i = 0, length = data.list.length; i < length; i++) {
			if (defProvince && defProvince == data.list[i]) {
				jQuery(
						"<option selected value='" + data.list[i] + "'>"
								+ data.list[i] + "</option>").appendTo(
						"#" + provinceId);
			} else {
				jQuery(
						"<option value='" + data.list[i] + "'>" + data.list[i]
								+ "</option>").appendTo("#" + provinceId);
			}
		}
		getCity(cityId, provinceId, defCity);
	});

}

function getCity(cityId, provinceId, defCity) {
	$.post(PATH + "/member/getCity", {
		areaname : $("#" + provinceId).val()
	}, function(data) {
		$("#" + cityId).empty();
		jQuery("<option value=''>--请选择--</option>").appendTo("#" + cityId);
		for (var i = 0, length = data.list.length; i < length; i++) {
			if (defCity && defCity == data.list[i]) {
				jQuery(
						"<option selected value='" + data.list[i] + "'>"
								+ data.list[i] + "</option>").appendTo(
						"#" + cityId);
			} else {
				jQuery(
						"<option value='" + data.list[i] + "'>" + data.list[i]
								+ "</option>").appendTo("#" + cityId);
			}
		}
	});
}
/**菜单 导航条**/
function navPath(level1, level2) {
	//alert($("#"+level1).find(".menu-text").text() );
	//alert($("#"+level2).text() );
	$("#nav_fir").text($("#" + level1).find(".menu-text").text());
	$("#nav_sec").text($("#" + level2).text());

	$("#" + level1).addClass("open");
	$("#" + level1).find("ul").css("display", "block");
}
/**表格刷新**/
function reloadGrid(){
	jQuery("#grid-table").trigger("reloadGrid");
	
}
/**查询**/
function searchData(){
	searchParams= changeForm2Jso('searchForm');
	
	$("#grid-table").jqGrid('setGridParam',{ 
        postData:searchParams,
        page:1 
    }).trigger("reloadGrid"); //重新载入  
    
    //jQuery("#grid-table").setPostData(searchParams).trigger("reloadGrid");
}
//ajax 成功标识位
var  AJAX_SUCC=0;
//ajax 失败标识位
var AJAX_FAIL=1;
//ajax 成功与否标识位
var AJAX_RESULT="r";
//ajax 返回消息标识位
var AJAX_MSG="msg";
//多条消息返回标识位
var AJAX_ERRORS="errors";

/**
 * 动态加载js 
 * @param data 数组
 */
function includeJS(data){
	for(var i=0;i<data.length;i++){
		if(!isNull(data[i]) ){
		if(!isNull(PATH) && data[i].indexOf(PATH)==-1){
			data[i]=PATH+data[i];
		}
			document.write("<script id='"+i+"' type='text/javascript' src='"+data[i]+"'  charset='utf-8'> </script>");
		}
	}
	
	
}
/**
 * 动态引入css 
 * @param data 数组
 */
function includeCSS(data){
	for(var i=0;i<data.length;i++){
		if(!isNull(data[i]) ){
			if(!isNull(PATH) && data[i].indexOf(PATH)==-1){
				data[i]=PATH+data[i];
			}
				document.write("<link rel='stylesheet' href='"+data[i]+"' type='text/css' />");
		}
	}
	
	
}
/**
 * 跳转链接同一用此处
 * @param url
 */
function goUrl(url){//alert(url);
	window.location=PATH+url;
}



function LoadJS( id, fileUrl ) 
{ 

    var scriptTag = document.getElementById( id ); 

    var oHead = document.getElementsByTagName('HEAD').item(0); 

    var oScript= document.createElement("script"); 

    if ( scriptTag  ) oHead.removeChild( scriptTag  ); 

    oScript.id = id; 

    oScript.type = "text/jsx"; 

    oScript.src=fileUrl ; 

    oHead.appendChild( oScript); 

} 




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
				showMask();
				$($(this).attr("data-target")).show();
			});
		}
		
	});
	
	$("*[data-dismiss='modal']").each(function(){
		$(this).on("click",function(){
		hideMask();
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




/**
 * 闭包
 */
Function.prototype.Apply = function(thisObj)
{
    var _method = this;
    return function(data)
    {
        return _method.apply(thisObj,[data]);
    };
};

function goMvcUrl(str){
	  mvc.routeRun(str);
}

function ajaxResultHandler(result){
	//权限问题
	//登陆问题
	if(typeof result =='string'){
		result= eval("("+result+")");
	}
	if(result.r!=1){
		if(result.r==504  ){
			dialog.alert(result.msg);
			window.location=WEBCONTEXT+"/login";
			
		}
		if(result.r==505  ){
			dialog.alert(result.msg);
		}
		return result;
	}
	else
	return result;
}

function showMask(){
	if($(".mask").length==0){
		$("body").append($("<div class=\"mask\" > </div>"));
	}
	$(".mask").show()
}

function showModal(id,w,h){
	if(typeof h !='undefined' && h!=null )
	$(id).css("height",h);
	if(typeof w !='undefined' && w!=null )
	$(id).css("width",w);
	$(id).show();
	showMask();
}


function hideModal(id){
	$(id).hide();
	$(".mask").hide()
}

function showErrorMsg(formid,msg){
	var div = $(formid).find(".alert");
	if(div.length==0){
		var div = $("<div class=\"alert alert-danger\"></div>");
		 $(formid).find(".modal-body").prepend(div);
	}
	div.eq(0).html(msg);
	div.eq(0).show();
}


function clearErrorMsg(formid){
	 $(formid).find(".alert").hide();
}

function showWait(msg){
	
	//showMask();
	//$(".wait").show();
	return layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
}
function hideWait(index){
	//hideWaitTrue();
	///setTimeout("hideWaitTrue()",100);
	layer.close(index);
}
function hideWaitTrue(){
	$(".mask").hide()
	$(".wait").hide();
}


function zWidgetBase(){
	showMask();
	if($(".widget").length>0){
		
	}else{
		$("body").append("<div class='widget'></div>");
	}
}/*
function zconfirm(msg,title,fn){
	zWidgetBase();
	var html=$("<div class=\"zwidget_wrap\">"+
	"<div class=\"zwidget_header\"><span>"+title+"</span> <a onclick=\"$(this).parent().parent().hide();$('.mask').hide()\"><img class=\"zclose\" src=\"/static/img/closeIcon.png\"></img></a></div>"+
	"<div class=\"zbody\">"+
	"<div class=\"zinfo-icon\"><img src=\"/static/img/exclamation.png\"/></div>"+
	"<div class=\"zinfo\">"+msg+"</div>"+
	"<div class=\"zbutton_wrap\"><a style='width:20%' onclick=\"$(this).parent().parent().parent().hide();$('.mask').hide()\">确定</a><a style='width:20%' onclick=\"$(this).parent().parent().parent().hide();$('.mask').hide()\">确定</a></div>"+
	"</div>"+
	"</div>");
	$(".widget").html(html);
	if (typeof(fn) != "undefined") 
	$(html).find(".zbutton_wrap").find("a").click(fn);
}*/

/*
function zdialogue(msg,title,src,fontcolor,fn){
	zWidgetBase();
	var html=$("<div class=\"zwidget_wrap\">"+
	"<div class=\"zwidget_header\"><span>"+title+"</span> <a onclick=\"$(this).parent().parent().hide();$('.mask').hide()\"><img class=\"zclose\" src=\""+PATH+"/static/img/closeIcon.png\"></img></a></div>"+
	"<div class=\"zbody\">"+
	"<div class=\"zinfo-icon\"><img src=\""+src+"\"/></div>"+
	"<div class=\"zinfo\" style=\"color:"+fontcolor+"\">"+msg+"</div>"+
	"<div class=\"zbutton_wrap\"><a onclick=\"$(this).parent().parent().parent().hide();$('.mask').hide()\">确定</a></div>"+
	"</div>"+
	"</div>");
	$(".widget").html(html);
	if (typeof(fn) != "undefined") 
	$(html).find(".zbutton_wrap").find("a").click(fn);
}*/
var dialog={
    alert:function(msg,fn){
        if(typeof fn != 'undefined'){
            return layer.alert(msg,fn);
        }else{
          return layer.alert(msg);
        }
    },
    close:function(index){
        layer.close(index);
    },
    closeWindow:function(index){
        layer.close(index);
    },
    confirm:function(msg,fn){
        return layer.confirm(msg,fn);
    },
    error:function(msg,fn){
        if(typeof fn != 'undefined'){
                layer.alert(msg,fn);
            }else{
              layer.alert(msg);
            }
    },
     showWait:function(msg){

    	//showMask();
    	//$(".wait").show();
    	return layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
    },
     hideWait:function(index){
    	//hideWaitTrue();
    	///setTimeout("hideWaitTrue()",100);
    	layer.close(index);
    }
,
    window:function(url,flag){
        window.data={};
        //截取参数
        var position=url.indexOf("?");
        if(position>0){
        var paramsStr= url.substring(position+1);
        console.log("paramsStr:"+paramsStr);
        var arr= paramsStr.split("&");

        for(var i=0;i<arr.length;i++){
            var keyVal=arr[i].split("=");
            var key=keyVal[0];
            var val=keyVal[1];
            console.log(keyVal[0]+":"+keyVal[1]);
            window.data[key]=val;
        }
        }
        //  jLoading.start();
        $.ajax({
            type: 'GET',
            url: PATH+url,
            dataType: 'html',
            success: function(data){
                //jLoading.close();
                if(flag){
                     $('.modal-dialog').html(data);
                     $('.modal-dialog').show();
                }else{
                    $(".main").html(data);
                }

                if(typeof fun == 'function') fun();
            },
            error: function(){
                //jLoading.close();
                //jDialog.alert('加载页面失败', '系统错误')
            }
        });
    }
}
function zdialogue(jso){
	showMask()
	if(StringUtil.isBlank(jso.title)){
		jso.title="提示";
	}
	var html=$("<div class=\"zwidget_wrap \">"+
	"<div class=\"zwidget_header\"><span>"+jso.title+"</span> <a class='zclose'  onclick=\"$(this).parent().parent().hide();$('.mask').hide()\"><i class=' fa fa-close'></i></a></div>"+
	"<div class=\"zbody \">"+
	"<div class=\"zinfo-icon\"><i  class='"+jso.icon+"'></i></div>"+//<img src=\""+jso.src+"\"/>
	"<div class=\"zinfo\" style=\"color:"+jso.fontcolor+"\">"+jso.msg+"</div>"+
	"</div><div class='zfooter' ><div class=\"zbutton_wrap row\">"+
	(jso.type== "confirm"?"<button type=\"button\" class=\"col-xs-5 pull-left btn btn-primary\" >确定</button><button type=\"button\" class=\"col-xs-5 pull-right btn btn-default\" >取消</button>":
		"<button class='col-xs-12 btn btn-primary' >确定</button>")+
	"</div></div>"+
	"</div>");
	if($(".widget").length>0){
		$(".widget").html(html);
	}else{
		$("body").append(html);
	}
	
	if (typeof(jso.okfn) != "undefined") {
		$(html).find(".zbutton_wrap").find(".btn-primary").click(jso.okfn);
	}
		
	$(html).find(".btn").click(function(){$(html).fadeOut();$(".mask").fadeOut()});
	
	if (typeof(jso.cancelfn) != "undefined") {
		$(html).find(".zbutton_wrap").find(".btn-default").click(jso.okfn);
		$(html).find(".zwidget_header").find(".zclose").click(jso.cancelfn);
		$(html).find(".zwidget_header").find(".zclose").click(function(){$(html).fadeOut();$(".mask").fadeOut()});
	}else{
		$(html).find(".zwidget_header").find(".zclose").click(function(){$(html).fadeOut();$(".mask").fadeOut()});
	}
}
/*
function zalert(msg,title,fn){
	zWidgetBase();
	if(StringUtil.isBlank(title)){
		title="提示";
	}
	var html=$("<div class=\"zwidget_wrap\">"+
	"<div class=\"zwidget_header\"><span>"+title+"</span> <a class='zclose'  onclick=\"$(this).parent().parent().hide();$('.mask').hide()\"><i class=' fa fa-close'></i></a></div>"+
	"<div class=\"zbody\">"+
	"<div class=\"zinfo-icon\"><i  class='fa fa-check-circle'></i></div>"+
	"<div class=\"zinfo\">"+msg+"</div>"+
	"<div class=\" zbutton_wrap\"><a class='btn btn-primary' onclick=\"$(this).parent().parent().parent().hide();$('.mask').hide()\">确定</a></div>"+
	"</div>"+
	"</div>");
	$(".widget").html(html);
	if (typeof(fn) != "undefined") 
	$(html).find(".zbutton_wrap").find("a").click(fn);
}*/
function zerror(msg,title,fn){
	zdialogue({"msg":msg,type:"alert","title":title,fontcolor:"#777777",src:PATH+"/static/img/exclamation.png",okfn:fn,icon:"fa fa-exclamation-circle"});
}
function zconfirm(msg,title,okfn,cancelfn){
	zdialogue({"msg":msg,type:"confirm","title":title,fontcolor:"#777777",src:PATH+"/static/img/exclamation.png",okfn:okfn,cancelfn:cancelfn,icon:"fa fa-info-circle"});
}
function zalert(msg,title,fn){
	zdialogue({"msg":msg,type:"alert","title":title,fontcolor:"#777777",src:PATH+"/static/img/nike.png",okfn:fn,icon:"fa fa-check-circle"});
}
function zwarn(msg,title,fn){
	zdialogue({"msg":msg,type:"alert","title":title,fontcolor:"#777777",src:PATH+"/static/img/exclamation.png",okfn:fn,icon:"fa fa-exclamation-circle"});
}
var Tool={};
Tool.isNull=function(it){
	if(it==null || typeof it=='undefinded'){
		return true;
	}
	return null;
}
var StringUtil={};
StringUtil.isNull=function(it){
	if(it==null || typeof it=='undefinded' || it==''){
		return true;
	}
	return null;
}
StringUtil.isBlank=function(it){
	if(it==null || typeof it=='undefinded' || it==''){
		return true;
	}
	return null;
}
StringUtil.isPhone=function(it){
return /^[1][3578][0-9]{9}$/.test(it);
}
StringUtil.isEmail=function(it){

return /^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/.test(it);
}
function getParam(key){
	if(window.data)
		return window.data[key]||"";
	return null;
}


function chkFloat(number, intLength, floatLength) {
	var strTemp= number.value.toString();
	number.value=strTemp.replace(/[^\.^\d]*/g,"");
	console.log(number.value)
	var strNumber = number.value;
	strNumber = strNumber.replace("-", "");
	var pos = strNumber.indexOf(".");
	if (pos == -1) {
		if (strNumber.length > intLength) {
			number.value = strNumber.substring(0, intLength);
		}
	} else {
		if (strNumber.length - pos > floatLength + 1) {
			number.value = strNumber.substring(0, pos + floatLength + 1);
		}
		if (pos > intLength) {
			number.value = number.value.substring(0, intLength) + number.value.substring(pos);
		}
	}
}
//alert("1234567890123456789".replace(/\d{10}(\d*)/,""));
function chkInt(number, intLength) {
	//number.value="12.45676778";return;
	var strTemp= number.value.toString();
	//console.log(strTemp.replace(/[^\d]/g,""));
	number.value=number.value.replace(/[^\d]/g,"");
	//;
	var strNumber = number.value;
	var pos = strNumber.indexOf(".");
	if (pos == -1) {
		if (strNumber.length > intLength) {
			number.value = strNumber.substring(0, intLength);
		}
	} else {
		number.value = strNumber.substring(0, pos);
	}
}

function clearNoNum(obj){   obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  

obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是. 

 obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.   

obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");

}


function showDialogue(url,modal){
    if(!modal){
        goPage(PATH+url);
    }else{
         zwindow(PATH+url);
        //$("#mymodal").modal("toggle");
    }
}


var setting = {
           check: {
               enable: true
           },
           data: {
               simpleData: {
                   enable: true
               }
           }
       };

var BaseValidator={
	errorElement : 'div',
            					errorClass : 'help-block-left-animation',
            					focusInvalid : true,

                                highlight : function(element) {
            						/*$(element).closest('.form-signin').removeClass(
            								'has-info').addClass('has-error');*/
            					},

            					success : function(e) {
            						$(e).closest('.form-signin').removeClass('has-error')
            								.addClass('has-info');
            						$(e).remove();
            					},

            					errorPlacement : function(error, element) {
            					    $(element).closest('.form-group').append(error);
            						//error.insertAfter(element);
            					},

            					submitHandler : function(form) {
            						register();

            					},
            					invalidHandler : function(form) {

            					}
}

function zImageUtil(config) {
	var o = {
		imgDom: null, //回显的image的id
		maxHeight: null, //预设的最大高度
		maxWidth: null, //预设的最大宽度
		inputDom:null,
		postUrl: null, //提交的url"/calendar/image/upload.json"
		preShow: true,
		callback: null,

		fileChange: function(e) {//
			var f = e.files[0]; //一次只上传1个文件，其实可以上传多个的
			var FR = new FileReader();//创建fileReader
			var _this = this;

			FR.onload = function(f) {//fr  readAsDataURL 的时候触发方法

				_this.compressImg(this.result, 300, function(data) { //压缩完成后执行的callback
					console.log("压缩完成后执行的callback");
					//document.getElementById('imgData').value = data;//写到form元素待提交服务器
					//document.getElementById('myImg').src = data;//压缩结果验证
					if (_this.preShow) {
						console.log("img pre show");
						_this.imgDom.src = data;
						console.log(_this.imgDom);

					}
					console.log("begin send img");
					var json = {};
					// json.imageName= "myImage.png";
					data = data.substring(22);
					// alert(data)
					json.imageData = encodeURIComponent(data);
					console.log("begin post");

					Ajax.post(_this.postUrl,
						json,
						function(data) {
							if (data.r == 1) {
								_this.imgDom.src = PATH+"/" + data.data;
								$(_this).parent().find("#picurl")
								console.log("imgUrl:" + data.data);
							} else {
								//	                        		zalert(data.msg);
							}
							if (_this.callback != null)
								_this.callback(data);
						}
					);
				});
			};
			FR.readAsDataURL(f); //先注册onload，再读取文件内容，否则读取内容是空的
		},
		compressImg: function(imgData, maxHeight, onCompress) {
			var _this = this;
			if (!imgData)
				return;
			onCompress = onCompress || function() {};
			maxHeight = maxHeight || this.maxHeight; //默认最大高度200px
			var canvas = document.createElement('canvas');
			var img = new Image();
			console.log("maxHeight:" + maxHeight);
			img.onload = function() {
				if (img.height > maxHeight) { //按最大高度等比缩放
					img.width *= maxHeight / img.height;
					img.height = maxHeight;
				}
				canvas.width = img.width;
				canvas.height = img.height;
				var ctx = canvas.getContext("2d");

				ctx.clearRect(0, 0, canvas.width, canvas.height); // canvas清屏

				//重置canvans宽高 canvas.width = img.width; canvas.height = img.height;
				console.log("width:" + img.width + "height:" + img.height);
				ctx.drawImage(img, 0, 0, img.width, img.height); // 将图像绘制到canvas上
				console.log("begin compress img");
				onCompress(canvas.toDataURL("image/png")); //必须等压缩完才读取canvas值，否则canvas内容是黑帆布
			};
			// 记住必须先绑定事件，才能设置src属性，否则img没内容可以画到canvas
			console.log("begin origin data load:");
			img.src = imgData;

		},
		init: function(jso) {
		var _this =this;
			this.imgDom = jso.imgDom;
			this.maxHeight = jso.maxHeight;
			this.maxWidth = jso.maxWidth;
			this.postUrl = jso.postUrl;
			this.callback = jso.callback;
			this.inputDom=jso.inputDom;
			var _this =this;
			$(this.imgDom).click(function(){
                $(_this.inputDom).trigger("click");
			})

				$(this.inputDom).change(function(){
            			    _this.fileChange(_this.inputDom);
                })
		},
	};
	o.init(config);
	return o;
}
  function intNum(val,defval) {
			val = parseInt(val,10);
			if (isNaN(val)) { return defval || 0;}
			return val;
		};
var jsonReader={
    root: "data",
    page: "page.curPage",
    total: "page.totalPage",
    records: "page.totalCount",
    repeatitems: true,
    cell: "cell",
    id: "id",
    userdata: "userdata",
    subgrid: {root:"rows", repeatitems: true, cell:"cell"}
};

function zImageUtil(config) {
	var o = {
		imgDom: null, //回显的image的id
		maxHeight: null, //预设的最大高度
		maxWidth: null, //预设的最大宽度
		postUrl: null, //提交的url"/calendar/image/upload.json"
		preShow: true,
		callback: null,
        fileInput:null,
		fileChange: function(e) {
			var f = e.files[0]; //一次只上传1个文件，其实可以上传多个的
			var FR = new FileReader();
			var _this = this;

			FR.onload = function(f) {

				_this.compressImg(this.result, 300, function(data) { //压缩完成后执行的callback
					console.log("压缩完成后执行的callback");
					//document.getElementById('imgData').value = data;//写到form元素待提交服务器
					//document.getElementById('myImg').src = data;//压缩结果验证
					if (_this.preShow) {
						console.log("img pre show");
						_this.imgDom.src = data;

						console.log(_this.imgDom);

					}
					console.log("begin send img");
					var json = {};
					// json.imageName= "myImage.png";
					data = data.substring(22);
					// alert(data)
					json.imageData = encodeURIComponent(data);
					console.log("begin post");

					Ajax.post(_this.postUrl,
						json,
						function(data) {
							if (data.r == 1) {
								_this.imgDom.src = PATH+"/" + data.data;
								$(_this).parent().find("#picurl")
								console.log("imgUrl:" + data.data);
							} else {
								//	                        		zalert(data.msg);
							}
							if (_this.callback != null)
								_this.callback(data);
						}
					);
				});
			};
			FR.readAsDataURL(f); //先注册onload，再读取文件内容，否则读取内容是空的
		},
		compressImg: function(imgData, maxHeight, onCompress) {
			var _this = this;
			if (!imgData)
				return;
			onCompress = onCompress || function() {};
			maxHeight = maxHeight || this.maxHeight; //默认最大高度200px
			var canvas = document.createElement('canvas');
			var img = new Image();
			console.log("maxHeight:" + maxHeight);
			img.onload = function() {
				if (img.height > maxHeight) { //按最大高度等比缩放
					img.width *= maxHeight / img.height;
					img.height = maxHeight;
				}
				canvas.width = img.width;
				canvas.height = img.height;
				var ctx = canvas.getContext("2d");

				ctx.clearRect(0, 0, canvas.width, canvas.height); // canvas清屏

				//重置canvans宽高 canvas.width = img.width; canvas.height = img.height;
				console.log("width:" + img.width + "height:" + img.height);
				ctx.drawImage(img, 0, 0, img.width, img.height); // 将图像绘制到canvas上
				console.log("begin compress img");
				onCompress(canvas.toDataURL("image/png")); //必须等压缩完才读取canvas值，否则canvas内容是黑帆布
			};
			// 记住必须先绑定事件，才能设置src属性，否则img没内容可以画到canvas
			console.log("begin origin data load:");
			img.src = imgData;

		},
		init: function(jso) {
			this.imgDom = jso.imgDom;
			this.maxHeight = jso.maxHeight;
			this.maxWidth = jso.maxWidth;
			this.postUrl = jso.postUrl;
			this.callback = jso.callback;
			this.fileInput=jso.fileInput;
            var that =this;
			 $(that.fileInput).change(function(){

              //  console.log("imgDom:"+nowImg);
                //var imageUtil= new zImageUtil({imgDom:nowImg,postUrl:"/calendar/image/upload.json",maxWidth:633,maxHeight:300});
                that.fileChange(this);
            });
            $(this.imgDom).click(function(){


                $(that.fileInput).trigger("click");
            });
		},
	};
	o.init(config);
	return o;
}




(function() {
	var Awifi_UI = function() {
		this.name = 'Awifi_UI';
		this.version = '1.0';
	}
	this.Awifi_UI = new Awifi_UI();
	Awifi_UI = this.Awifi_UI;

	Awifi_UI.captcha = {
		systemNo:'',
		host: '',
		//包含组件的div
		mainContain: '',
		//模板
		captchaTpl: '<div class="ui-row-90 awui-text-center" style="min-height: 28px"><span class="awui-red" id="error"></span></div><div class=awui-row-90><input class=awui-main-input id=username placeholder="请输入手机号" maxlength=11></div><div class=awui-row-90 id=captchaDiv><div class="awui-row-47 awui-left"><input class="awui-row-47 awui-main-input" placeholder=请输入验证码 id=picCaptcha maxlength=4></div><div class="awui-row-47 awui-right"><button type="button" class="awui-back-gray awui-gray awui-half-btn awui-text-center" id=smsCaptchaGet>获取验证码</button></div></div><div class="awui-row-90 awui-login-btngroup"><button class="awui-back-orange awui-white awui-half-btn awui-text-center" id=sure>确认</button></div>',
		captchaPicTpl: '<div class="ui-row-90 awui-text-center" style="min-height: 28px"><span class="awui-red" id="error"></span></div><div class=awui-row-90><input class=awui-main-input id=username placeholder="请输入手机号" maxlength=11></div><div class=awui-row-90 id=captchaDiv><div class="awui-row-47 awui-left"><input class="awui-row-47 awui-main-input" placeholder=请输入验证码 id=picCaptcha maxlength=4></div><div class="awui-row-47 awui-right awui-text-center"><img src=img/forget.png id=picCaptchaGet class="awui-captchaPic"></div></div><div class="awui-row-90 awui-login-btngroup"><button class="awui-back-orange awui-white awui-half-btn awui-text-center" id=sure>确认</button></div>',
		//请求地址
		url: {
			smsCaptchaUrl: '',
			picCaptchaUrl: '',
			sureUrl: ''
		},
		//计数
		captchaCutdownTime: 60,
		//参数
		params: {
			appid: '',
			timestamp: '',
			token: ''
		},
		captchaType: '',
		$main:null ,
		$username:null,
		$smsCaptcha:null,
		$picCaptcha:null,
		$loginBtn:null,
		$error:null,
		/**
		 * 初始化
		 * @param {Object} config
		 */
		init: function(config) {
			if (!config) {
				console.log("配置缺失");
				return false;
			}
			//JQuery对象
			if (config.mainContain.length>0) {//alert($("#login_form").length)
				this.mainContain = config.mainContain;
				console.log("mainContain:" + this.mainContain);
			} else {
				console.log("配置参数缺失,请指定mainContain（组件dom容器）");
				return false;
			}

			//portal参数
			if (config.params) {
				this.params = config.params;
			}

			if (config.systemNo) {
				this.systemNo = config.systemNo;
			}


			//请求host
			if (config.host) {
				this.host = config.host;
			}
			//url
			if (config.url) {
				this.url = config.url;
			}
			//验证码类型
			if (config.captchaType) {
				this.captchaType = config.captchaType;
			}
			//验证码倒计时
			this.captchaCutdownTime = typeof(config.captchaCutdownTime) == 'undefined' ? this.captchaCutdownTime : config.captchaCutdownTime;

			if (this.captchaType == 'pic') {
				//this.mainContain.html(this.captchaPicTpl);
				//this.picCaptchaClick();
				//this.getCpatchaClick();
			} else {
				//this.mainContain.html(this.captchaTpl);
				//this.getCaptcha();
			}

			this.$main = this.mainContain;
			if(this.mainContain.length==0){
				console.log("mainContain not exist");
			}
			var $main=this.$main;
			if($main.length==0){
				console.log("$main not exist");
			}
			this. $username = $main.find('.username');
			this.$smsCaptcha = $main.find('#smsCaptcha');
			this.$picCaptcha = $main.find('#picCaptcha');
			this.$loginBtn = $main.find('#loginBtn');
			this. $error = $main.find("#error");
			this.$picCaptchaGet=$main.find("#picCaptchaGet");
			this.$smsCaptchaGet=$main.find("#smsCaptchaGet");
			this.addEventListener();
		},

		initValid:function(){

		},
		/**
		 * 获取验证码
		 */
		initSmsCaptcha: function() {
			var that = this;
			var checker = that.checker;
			var $username =this.$username;
			var $error =this.$error;

			/*this.$smsCaptchaGet.on('click', function() {
				if ($(this).attr("disabled")) {
					return;
				}
				var username = $username.val() || '';
				if (checker.checkUserName(username)) {
					$error.text("请输入正确的帐号");
					return false;
				}

				var appid = params.appid || '';
				var timestamp = params.timestamp || '';
				var token = params.token || '';
				var data = {
					phone: username,
					appid: appid,
					timestamp: timestamp,
					token: token
				};
				that.getCaptchaAjax(data);
			});*/
		},
		picCaptchaClick: function() {
			console.log("picCaptchaClick");
			//TODO
			var that = this;
			var params = that.params;
			var phone =  new Date().getTime();
			var appid = params.appid || '';
			var timestamp = params.timestamp || '';
			var token = params.token || '';
			var data = {
				systemno: this.systemNo,
				/*appid: appid,
				timestamp: timestamp,*/
				sessionid: that.$main.find("#sessionid").val()
			};
			that.getPicCaptchaAjax(data);
		},
		smsCaptchaClick: function() {//TODO
			var that = this;
			var params = that.params;
			var phone = this.$username.val();
			var appid = params.appid || '';
			var timestamp = params.timestamp || '';
			var token = params.token || '';
			var data = {
				phone: phone,
				appid: appid,
				timestamp: timestamp,
				token: token,
				systemno:this.systemNo

			};
			if(this.checker.isBlank(phone)){
				this.alert("请先填写手机号");
				return;
			}
			that.captchaCutdown(this.$smsCaptchaGet);
			that.getSmsCaptchaAjax(data);
		},
		alert:function(str){
			this.$error.text(str);
		},
		/**
		 * 绑定事件
		 */
		addEventListener: function() {
			var that = this;
			var checker = this.checker;
			var params = this.params;
			if(this.$picCaptchaGet.length>0){
				this.$picCaptchaGet.on('click', function() {
					that.picCaptchaClick();
				});
				//显示验证码
				this.$picCaptchaGet.trigger("click");
			}else{
				console.log("$pciCaptchaGet not exist");
			}
			if(this.$smsCaptchaGet.length>0){
				this.$smsCaptchaGet.on('click', function() {
					that.smsCaptchaClick();
				});
			}else{
				console.log("$smsCaptchaGet not exist");
			}

			/*this.$loginBtn.on('click', function() {

				var username, captcha;
				username = that.$username.val() || '';
				captcha = that.$smsCaptcha.val() || '';

				//6位验证码验证

				if(that.$smsCaptcha.length>0){
					if(that.checker.checkCaptcha(captcha)){

					}

				}

				if (that.checker.checkCaptcha(captcha) || that.checker.checkUserName(username)) {
					that.alert("请输入正确的帐号或验证码");
					return false;
				}

				var data = {
					phone: username,
					code: captcha,
					appid: appid,
					timestamp: timestamp,
					token: token
				};
				that.sureAjax(data);
			});*/
		},

		/**
		 * 短信验证码请求
		 * @param {Object} data
		 */
		getPicCaptchaAjax: function(data) {
			var that = this;
			var type = this.captchaType;
			var url;
			url = this.url.picCaptchaUrl || '';
			url = this.host + url;
			$.ajax({
				url: url,
				data: data,
				type:'GET',
				dataType: 'JSONP',
				jsonp: 'callback',
				jsonpCallback:'getName',
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				header: {
					'cache-control': 'no-cache'
				},
				success: function(data, textStatus, jqXHR) {
					if (data.r == 0 && that.$picCaptcha) {
						//that.$picCaptchaGet.prop("src",that.host+"/"+data.data.img);
						//that.$picCaptchaGet.find("img").prop("src",that.host+"/"+data.data.img+"?r="+Math.random());
						that.$picCaptchaGet.find("img").prop("src","data:image/png;base64,"+data.data.imgdata);
						//暂存token
						var $sessionid= that.$main.find("#sessionid");
						if($sessionid.length==0){
							$sessionid=$("<input type='hidden' id='sessionid' name='sessionid' />");
							that.$main.append($sessionid);

						}
						$sessionid.val(data.data.sessionid);
					}
				},
				error: function(XHR, textStatus, errorThrown) {}
			});
		},
		/**
		 * 验证码请求
		 * @param {Object} data
		 */
		getSmsCaptchaAjax: function(data) {
			var that = this;
			var type = this.captchaType;
			var url;
			url = this.url.smsCaptchaUrl || '';
			url = this.host + url;
			$.ajax({
				url: url,
				data: data,
				dataType: 'JSONP',
				type:'GET',
				jsonp: 'callback',
				jsonpCallback:'getName',
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				header: {
					'cache-control': 'no-cache'
				},
				success: function(data, textStatus, jqXHR) {
					if (data.r == 0 ) {

					}else{
						that.alert(data.msg);
						console.log(data);
					}

				},
				error: function(XHR, textStatus, errorThrown) {}
			});
		},
		/*sureAjax: function(data) {
			var that = this;
			that.awui_loading.show();
			var url = this.url.sureUrl || '';
			url = this.host + url;
			$.ajax({
				url: url,
				data: data,
				dataType: 'JSONP',
				jsonp: 'callback',
				header: {
					'cache-control': 'no-cache'
				},
				success: function(datas) {
					console.log(datas);
				},
				error: function(XHR, textStatus, errorThrown) {},
				complete: function(XHR, textStatus) {
					that.awui_loading.hide();
				}
			});
		},*/
		/**
		 * 倒计时 传入按钮
		 * @param {Object} self
		 */
		captchaCutdown: function(self) {
			self.attr('disabled', true);
			self.text('发送中');
			var time = this.captchaCutdownTime || 60;
			var sI = setInterval(function() {
				time = time - 1;
				if (time > 0) {
					self.text(time + '秒后重试');
				} else {
					window.clearInterval(sI);
					self.text('重新获取');
					self.removeAttr("disabled");
				}
			}, 1000);
		},

		/**
		 * 参数验证
		 */
		checker: {
			isBlank:function(it){
				if(it==null || typeof it=='undefinded' || it==''){
					return true;
				}
				return null;
			},
			checkUserName: function(str) {
				var re = /^1\d{10}$/
				if (!re.test(str)) {
					return true;
				}
			},
			checkPassword: function(str) {
				var re = /^[0-9 | A-Z | a-z]{6,20}$/;
				if (!re.test(str)) {
					return true;
				}
			},
			checkCaptcha: function(str) {
				var re = /^[0-9]{4}$/;
				if (!re.test(str)) {
					return true;
				}
			},
			checkAuthCaptcha: function(str) {
				var re = /^[0-9]{6}$/;
				if (!re.test(str)) {
					return true;
				}
			}
		},
		/**
		 * 获取uri参数
		 * @param {Object} name
		 */
		getParam: function(name) {
			if (!name) {
				return '';
			}
			var search = document.location.search;
			var pattern = new RegExp("[?&]" + name + "\=([^&]+)", "g");
			var matcher = pattern.exec(search);
			var items = null;
			if (null != matcher) {
				try {
					items = decodeURIComponent(decodeURIComponent(matcher[1]));
				} catch (e) {
					try {
						items = decodeURIComponent(matcher[1]);
					} catch (e) {
						items = matcher[1];
					}
				}
			}
			return items;
		},
		/**
		 * 加载状态
		 */
		awui_loading: {
			loadingHtml: '<div class="loading" id="awui_loading" style="{{height}}">' +
				'<div class="loading-warp" style="{{style}}">' +
				'<div class="loading-content"><span class="loading-circle loading-circle-one"></span></div>' +
				'<div class="loading-content"><span class="loading-circle loading-circle-two"></span></div>' +
				'<div class="loading-content"><span class="loading-circle loading-circle-three"></span></div>' +
				'</div>' +
				'</div>',
			topPx: 0,
			heightPx: 0,
			getHeight: function() {
				return document.body.clientHeight + 60;
			},
			init: function() {
				var self = this;
				self.topPx = window.screen.availHeight / 2 + window.scrollY;
				self.heightPx = self.getHeight();
			},
			show: function(height) {
				var self = this;
				self.init();
				height = height || self.heightPx;
				var leftPx = document.body.scrollWidth / 2 - 70 / 2;
				var html = this.loadingHtml.replace('{{style}}', 'margin-top:' + self.topPx + 'px;left:' + leftPx + 'px').replace('{{height}}', 'height:' + self.heightPx + 'px');
				$('body').css({
					'overflow': 'hidden'
				}).append(html);
			},
			hide: function() {
				$('#awui_loading').remove();
				$('body').css({
					'overflow': ''
				})
			}
		}
	}

})();