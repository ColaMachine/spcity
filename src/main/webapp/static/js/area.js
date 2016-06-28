
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