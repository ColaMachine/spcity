
/**
 *
 *
 * Author:
 *	zzw <zzw1986@gmail.com>
 *	
 *
 * File: td_view.js
 * Create Date: 2013-9-23 8:29
 *
 *
 */



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
		captchaTpl: '<div class="ui-row-90 awui-text-center" style="min-height: 28px"><span class="awui-red" id="error"></span></div><div class=awui-row-90><input class=awui-main-input id=username placeholder="请输入手机号" maxlength=11></div><div class=awui-row-90 id=captchaDiv><div class="awui-row-47 awui-left"><input class="awui-row-47 awui-main-input" placeholder=请输入验证码 id=smsCaptcha maxlength=4></div><div class="awui-row-47 awui-right"><button type="button" class="awui-back-gray awui-gray awui-half-btn awui-text-center" id=smsCaptchaGet>获取验证码</button></div></div><div class="awui-row-90 awui-login-btngroup"><button class="awui-back-orange awui-white awui-half-btn awui-text-center" id=sure>确认</button></div>',
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
				this.mainContain.html(this.captchaPicTpl);
				//this.picCaptchaClick();
				//this.getCpatchaClick();
			} else {
				this.mainContain.html(this.captchaTpl);
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
			this. $username = $main.find('#username');
			this.$smsCaptcha = $main.find('#smsCaptcha');
			this.$picCaptcha = $main.find('#picCaptcha');
			this.$loginBtn = $main.find('#sure');
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
			
			this.$loginBtn.on('click', function() {
				
				var username, captcha;
				username = that.$username.val() || '';
				
				if(that.captchaType=="pic"){
					captcha = that.$picCaptcha.val() || '';
				}else{
					captcha = that.$smsCaptcha.val() || '';
				}
				//6位验证码验证
				
				if(captcha.length>0){
					if(that.checker.checkCaptcha(captcha)){
						
					}
					
				}

				if (that.checker.checkCaptcha(captcha) || that.checker.checkUserName(username)) {
					that.alert("请输入正确的帐号或验证码");
					return false;
				}
				
				var data = {
					phone: username,
					code: captcha
				};
				that.sureAjax(data);
			});
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
						that.$picCaptchaGet.prop("src","data:image/png;base64,"+data.data.imgdata);
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
		sureAjax: function(data) {
			var that = this;
			that.awui_loading.show();
			data.systemno=this.systemNo;
			var url = this.url.smsSureUrl || '';
			if(this.captchaType=="pic"){
				url = this.url.picSureUrl || '';
				data.sessionid=that.$main.find("#sessionid").val();
			}
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
				success: function(datas) {
					if(datas.r==0){
						that.alert("验证成功");
					}else{
						that.alert(datas.msg)
					}
					console.log(datas);
				},
				error: function(XHR, textStatus, errorThrown) {},
				complete: function(XHR, textStatus) {
					that.awui_loading.hide();
				}
			});
		},
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
				var re = /^[0-9A-Za-z]{4}$/;
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