/**
 * @author Yaohaixiao
 * @version 1.0.1 Beta
 */
(function($){
	var EMPTY = '', 
	    SPACE = ' ',  
		DOT = '.',
		SHARP = '#',
		
		ID = 'id',
		CLASS = 'class',
		REL = 'rel',
		TITLE = 'title',
		SRC = 'src',
		ALT = 'alt',
		WIDTH = 'width',
		HEIGHT = 'height',
		MARGIN_TOP = 'marginTop',
		TYPE = 'type',
		FILE = 'file',
		NAME = 'name',
		METHOD = 'method',
		ACTION = 'action',
		ENCTYPE = 'enctype', 
		FRM_DATA = 'multipart/form-data',
		TAB_INDEX = 'tabindex',
		DISABLED = 'disabled',
		
		DIV = '<div></div>',
		H2 = '<h2></h2>',
		H3 = '<h3></h3>',
		A = '<a></a>',
		HREF = 'href',
		UL = '<ul></ul>',
		LI = '<li></li>',
		IMG = '<img/>'
		P = '<p></p>',
		BUTTON = '<button></button>',
		IFRAME = '<iframe></iframe>',
		FORM = '<form></form>',
		INPUT = '<input/>',
		
		TXT_POST = 'POST',
		TXT_GET = 'GET',
		TXT_JSON = 'JSON',
		TXT_HTML = 'HTML',
		TXT_IMG = 'img',
		TXT_LI = 'li',
		
		TXT_NEW_WIN = '新窗口', 
		TXT_ERROR = '错误',
		TXT_INFO = '提示',
		TXT_SUCCESS = '成功',
		TXT_WARN = '警告', 
		TXT_CONFIRM = '确认',
	    TXT_MIN = '最小化窗口', 
	    TXT_TOOGLE = '伸缩窗口',
	    TXT_CLOSE = '关闭窗口',
		TXT_ENTER = '确定',
		TXT_CANCEL = '取消',
		
		CLS_WIN = 'window', 
		CLS_WIN_COMMON = 'window-common',
		CLS_WIN_DIALOG = 'window-dialog',
		CLS_WIN_INFO = 'window-info',
		CLS_WIN_WARN = 'window-warn',
		CLS_WIN_ERROR = 'window-error',
		CLS_WIN_CONFIRM = 'window-confirm',
		CLS_WIN_SUCCESS = 'window-correct',
		CLS_TLT_BAR = 'window-title-bar', 
		CLS_TLT = 'window-title', 
		CLS_TITLE_BTNS = 'window-title-buttons',
		CLS_BTN_MIN = 'window-min-bar',
		CLS_BTN_TOGGLE = 'window-toggle-bar', 
		CLS_BTN_CLOSE = 'window-close-bar fa fa-close',
		CLS_WIN_CNT = 'window-content', 
		CLS_BTNS_BAR = 'window-buttons-bar', 
		CLS_BTN_CONFIRM = 'window-button-confirm', 
		CLS_BTN_CANCEL = 'window-button-cancel',
		CLS_STATUS_BAR = 'window-status-bar',
		CLS_IFRAME_HACK = 'iframe-hack',
		CLS_IE6_FIXED = 'window-ie6-fixed',
		
		CLS_CHKBOX = 'checkbox',
		CLS_CHECK = 'check',
		CLS_CHECKED = 'checked',
		CLS_HIDE = 'hide',
		CLS_BTN = 'button', 
		CLS_SHARDOW = 'shardow',
		CLS_DRAGGBLE = 'draggable',
		CLS_CONTENT = 'content',
		CLS_TABS = 'tabs',
		CLS_CURRENT = 'current',
		
		hackIframeURL = 'blank.htm',
		byId = function(el){
			var doc = document;
			
			if (el.tagName && el.nodeType === 1) {
				return el;
			}
			else {
				if (doc.getElementById) {
					return doc.getElementById(el);
				}
				else{
					return null;
				}
			}
		},
		windowsCounter = 0;
	
	/**
	 * Window 构造函数
	 * 
	 * @constructor Window
	 * @param {Object} config
	 * @return jQuery.Window
	 */
	$.Window = function(config){
		/**
		 * 窗口控件的初始化配置项
		 * 
		 * @property setting
		 */
		this.setting = {
			target: document.body,
			isFixed: true,
			winClass: CLS_WIN,
			winId: EMPTY,
			tltBarClass: CLS_TLT_BAR,
			tltClass: CLS_TLT,
			tltTxt: TXT_NEW_WIN,
			hasTitleBar: true,
			tltBtnBarClass: CLS_TITLE_BTNS,
			minBtnClass: CLS_BTN_MIN,
			minCallback: null,
			hasMinBtn: false,
			toggleBtnClass: CLS_BTN_TOGGLE,
			toggleCallback: null,
			hasToggleBtn: false,
			closeBtnClass: CLS_BTN_CLOSE,
			closeCallback: null,
			hasCloseBtn: true,
			hasTitleButtons: true,
			cntClass: CLS_WIN_CNT,
			cntHTML: EMPTY,
			cntFn: null,
			btnBarClass: CLS_BTNS_BAR,
			hasBtnBar: true,
			btnConfirmClass: CLS_BTN_CONFIRM,
			confirmTxt: EMPTY,
			confirmCallback: null,
			btnCancelClass: CLS_BTN_CANCEL,
			cancelTxt: EMPTY,
			cancelCallback: null,
			statusBarClass: CLS_STATUS_BAR,
			hasStatusBar: false,
			hasShardow: true,
			isFixedPos: true,
			isRemoveWindow: false,
			isDraggable: true,
			ajax: null
		};
		// 合并配置项
		$.extend(this.setting, config);
		
		// 窗口的各个主要组件
		this.Window = null;
		this.TitleBar = null;
		this.Title = null;
		this.TitleButtonsBar = null;
		this.ButtonMin = null;
		this.ButtonToggle = null;
		this.ButtonClose = null;
		this.Content = null;
		this.ButtonsBar = null;
		this.ButtonConfirm = null;
		this.ButtonCancel = null;
		this.StatusBar = null;
		this.Shardow = null;
		
		// 创建窗口、显示窗口、定位窗口、绑定各个控件的处理事件
		this.build().show().fixPosition().bind();
		
		return this;
	};
	
	// TODO: 1. 设计 statusbar 的效果
	//       2. 添加 drag 的功能
	$.Window.prototype = {
		
		/**
		 * 创建 Window 组件
		 * 
		 * @method build
		 * @return jQuery.Window
		 */ 
		build: function(){
			// 创建窗口和半透明遮罩层
			this.createWindow().createShardow();
            
			return this;
		},
		/**
		 * 创建 Window 组件的最外层框架
		 * 
		 * @method createWindow
		 * @return jQuery.Window
		 */
		createWindow: function(){
			var setting = this.setting,
			    target = $(setting.target),
				winId = setting.winId,
				isFixed = setting.isFixed,
				jWindow = null;
			
			windowsCounter += 1;
			winId = winId ? winId : CLS_WIN + windowsCounter;
			jWindow = byId(winId);
			
			// 在没有创建窗口的时候创建一个新的窗口 DOM 组件
			if (!jWindow) {
				// 创建窗口，添加 className 和 id
				this.Window = $(DIV).addClass(CLS_WIN + SPACE + setting.winClass + SPACE + CLS_HIDE).attr(ID, winId);
				
				// 创建标题栏、窗口最大最小按钮栏、工具栏、正文内容、按钮栏和状态栏
				this.createTitleBar().createTitleButtonsBar().createToolPannel().createContent().createButtonsBar().craeteStautsBar();
			}
			else {
				this.Window = $(SHARP + winId);
			}
			
				
			// 将窗口添加到指定的父节点
			target.append(this.Window);	
			
			if (!isFixed) {
				this.Window.css('position', 'absolute');
			}
				
			return this;	
		},
		/**
		 * 创建标题栏
		 * 
		 * @method createTitleBar
		 * @return jQuery.Window
		 */
		createTitleBar: function(){
			var setting = this.setting;
			
			if (setting.hasTitleBar) {
				// 创建标题栏
				this.TitleBar = $(DIV).addClass(setting.tltBarClass);
				
				// 创建标题
				this.Title = $(H2).addClass(setting.tltClass).text(setting.tltTxt);
				
				if(setting.isDraggable){
					this.Title.addClass(CLS_DRAGGBLE);
				}
				
				// 将标题添加到标题栏
				this.TitleBar.append(this.Title);
				
				// 将标题栏添加到 jWindow 窗口中
				this.Window.append(this.TitleBar);
			}
			
			return this;
		},
		/**
		 * 创建窗口大小控制按钮栏
		 * 
		 * @method createTitleButtonsBar
		 * @return jQuery.Window
		 */
		createTitleButtonsBar: function(){
			var setting = this.setting;
			
			if (setting.hasTitleButtons) {
				// 创建最大最小按钮栏
				this.TitleButtonsBar = $(DIV).addClass(setting.tltBtnBarClass);
				
				// 创建最小化、伸缩窗口和关闭按钮
				this.createMinBtn().createToggleBtn().createCloseBtn();
			}
			
			// 如果有标题栏，将关闭按钮添加到标题栏
			if (setting.hasTitleBar) {
				this.TitleBar.append(this.TitleButtonsBar);
			}
			else {
				// 如果没有标题栏，直接将关闭按钮添加到 jWindow 窗口
				this.Window.append(this.TitleButtonsBar);
			}
			
			return this;
		},
		/**
		 * 创建最小化按钮
		 * 
		 * @method createMinBtn
		 * @return jQuery.Window
		 */
		createMinBtn: function(){
			return this;
		},
		/**
		 * 创建伸缩按钮
		 * 
		 * @method createToggleBtn
		 * @return jQuery.Window
		 */
		createToggleBtn: function(){
			return this;
		},
		/**
		 * 创建关闭按钮
		 * 
		 * @method createToggleBtn
		 * @return jQuery.Window
		 */
		createCloseBtn: function(){
			var setting = this.setting;
			
		    if (setting.hasCloseBtn) {
				// 创建关闭按钮
				this.ButtonClose = $(A).addClass(setting.closeBtnClass).attr(TITLE, TXT_CLOSE).text(TXT_CLOSE);				
				
				// 将关闭按钮添加到 titleButtons 栏
				this.TitleButtonsBar.append(this.ButtonClose);
			}
			
			return this;
		},
		/**
		 * 创建工具条
		 * 
		 * @method createToolPannel
		 * @return jQuery.Window
		 */
		createToolPannel: function(){
			return this;
		},
		/**
		 * 创建窗体主要内容
		 * 
		 * @method createContent
		 * @return jQuery.Window
		 * 
		 * TODO: 是否增加添加内容完后执行的回调函数
		 */
		createContent: function(){
			var setting = this.setting,
			    ajax = setting.ajax,
			    ajaxMethodType = TXT_GET,
				ajaxPath = EMPTY,
				ajaxDataType = TXT_JSON;
				
			
			// 创建内容容器
			this.Content = $(DIV).addClass(setting.cntClass);
			
			// 创建内容
			if (setting.cntHTML) {
				// 根据HTML代码生成内容
				this.Content.html(setting.cntHTML);
			}
			else {
				// 根据自定义函数创建内容
				if (setting.cntFn && $.isFunction(setting.cntFn)) {
					// 根据HTML代码生成内容
					this.Content.html(setting.cntFn());
				}
				else {
					if (ajax) {
						ajaxPath = ajax.url;
						if(ajax.type){
							ajaxMethodType = ajax.type;
						}
						if (ajax.dataType) {
							ajaxDataType = ajax.dataType;
						}
						
						$.ajax({
							type: ajaxMethodType,
							url: ajaxPath,
							success: function(data){
								var htmlContent = EMPTY;
								
								switch (ajaxDataType) {
									case TXT_JSON:
										htmlContent = data.html;
										break;
									case TXT_HTML:
										htmlContent = data;
										break;
								}
								
								// 根据HTML代码生成内容
								this.Content.html(htmlContent);
							},
							dataType: ajaxDataType
						});
					}
				}
			}
			
			// 将正文内容添加到窗口中
			this.Window.append(this.Content);
			
			return this;
		},
		/**
		 * 创建窗体按钮栏
		 * 
		 * @method createButtonsBar
		 * @return jQuery.Window
		 */
		createButtonsBar: function(){
			var setting = this.setting;
			
			if (setting.hasBtnBar) {
				// 创建按钮栏
				this.ButtonsBar = $(DIV).addClass(setting.btnBarClass);
				
				// 创建确定和取消按钮
				this.createConfirmButton().createCancelButton();
                
				// 将按钮添加到按钮栏
				this.Window.append(this.ButtonsBar);
			}
			
			return this;
		},
		/**
		 * 创建确定按钮
		 * 
		 * @method createConfirmButton
		 * @return jQuery.Window
		 */
		createConfirmButton: function(){
			var setting = this.setting;
			
			// 创建确定按钮
			if (setting.confirmTxt) {
				// 创建确定按钮
				this.ButtonConfirm = $(BUTTON).addClass(CLS_BTN + SPACE + setting.btnConfirmClass).text(setting.confirmTxt);
				
				this.ButtonsBar.append(this.ButtonConfirm);
			}
			
			return this;
		},
		/**
		 * 创建取消按钮
		 * 
		 * @method createCancelButton
		 * @return jQuery.Window
		 */
		createCancelButton: function(){
			var setting = this.setting;
			
			// 创建取消按钮
			if (setting.cancelTxt) {
				// 创建确定按钮
				this.ButtonCancel = $(BUTTON).addClass(CLS_BTN + SPACE + setting.btnCancelClass).text(setting.cancelTxt);
				
				this.ButtonsBar.append(this.ButtonCancel);
			}
			
			return this;
		},
		/**
		 * 创建状态栏
		 * 
		 * @method craeteStautsBar
		 * @return jQuery.Window
		 */
		craeteStautsBar: function(){
			return this;
		},
		/**
		 * 创建遮罩层
		 * 
		 * @method createShardow
		 * @return jQuery.Window
		 */
		createShardow: function(){
			var setting = this.setting,
				target = $(setting.target),
				Shardow = byId(CLS_SHARDOW),
				docHeight = $(target).height(),
				hackIframe = null;
			
			// 创建遮罩层 DOM 组件
			if (setting.hasShardow && !Shardow) {
				this.Shardow = $(DIV).addClass(CLS_SHARDOW + SPACE + CLS_HIDE).attr(ID, CLS_SHARDOW);
				
				// IE6  中用 iframe HACK 处理，让遮罩层可以盖住 selectbox
				if ($.browser.msie && $.browser.version < 7) {
					hackIframe = $(IFRAME).addClass(CLS_IFRAME_HACK).attr(ID, CLS_IFRAME_HACK).attr(SRC, hackIframeURL);
					this.Shardow.append(hackIframe);
				}
			}
			else {
				this.Shardow = $(SHARP + CLS_SHARDOW);
			}
			
			target.append(this.Shardow);		
			console.log(setting.target);
			this.Shardow.css(HEIGHT, docHeight);
			
			return this;
		},
		/**
		 * 显示窗口
		 * 
		 * @method show
		 * @return jQuery.tabsView
		 */
		show: function(){
			var Window = this.Window,
			    Shardow = this.Shardow;
			
			// 显示窗口	
			if (Window) {
				Window.removeClass(CLS_HIDE);				
			}
			
			// 显示遮罩层
			if (Shardow) {
				Shardow.removeClass(CLS_HIDE);
			}
			
			return this;
		},
		/**
		 * 隐藏窗口
		 * 
		 * @method hide
		 * @return jQuery.tabsView
		 */
		hide: function(){
			var Window = this.Window,
			    Shardow = this.Shardow;
				
			// 隐藏窗口
			if (Window) {
				Window.addClass(CLS_HIDE);
			}
			
			// 隐藏遮罩层
			if (Shardow) {
				Shardow.addClass(CLS_HIDE);
			}
			
			// 移除窗口组件
			this.removeWindow();
			
			return this;
		},
		/**
		 * 移除窗口组件
		 * 
		 * @method removeWindow
		 * @return jQuery.tabsView
		 */
		removeWindow: function(){
			var setting = this.setting,
			    Window = this.Window,
			    Shardow = this.Shardow;
			
			// 关闭时是否移除 Window 控件
			if(setting.isRemoveWindow){
				if (Window) {
					Window.remove();
				}
				
				if (Shardow) {
					Shardow.remove();
				}
			}
			
			return this;
		},
		/**
		 * 固定 Window 窗口位置
		 * 
		 * @method fixPosition
		 * @return {Object} jQuery.Window
		 */
		fixPosition: function(){
			var Window = this.Window,
			    browser = $.browser,
			    dialogOffsetHeight = 0, 
				marginTop = 0;
			
			if (Window) {
				dialogOffsetHeight = Window.height();
				marginTop = -((dialogOffsetHeight + 10) / 2);
				
				// 给 Dialog 定位
				Window.css(MARGIN_TOP, marginTop);
				
				// 如果设置了不在 viewport 中固定位置，则窗口的 position 属性设置为 absolute;
				if (!this.setting.isFixedPos) {
					Window.addClass(CLS_IE6_FIXED);
				}
				else {
					// IE6  中固定位置的  HACK
					if (browser.msie && browser.version < 7) {
						Window.addClass(CLS_IE6_FIXED);
					}
				}
			}
			
			return this;
		},
		/**
		 * 绑定各个 DOM 组件的相应事件处理函数
		 * 
		 * @method bind
		 * @return {Object} jQuery.Window
		 */ 
	    bind: function(){
			this._onCloseBtnClick()._onConfirmBtnClick()._onCancelBtnClick()._onWindowResize();
			
			return this;
		},
		/**
		 * 绑定 closeBtn 的 click 事件的处理函数
		 * 
		 * @method _onCloseBtnClick
		 * @private 
		 * @return {Object} jQuery.Window
		 */
		_onCloseBtnClick: function(){
			var jWindow = this,
			    setting = this.setting,
				jButtonClose = this.ButtonClose;
			
			if (jButtonClose) {
				// 绑定关闭按钮的处理事件
				if (setting.closeCallback && $.isFunction(setting.closeCallback)) {
					jButtonClose.click(function(evt){
						setting.closeCallback();
						jWindow.hide.call(jWindow);
						
						// 阻止冒泡和默认行为事件
						evt.preventDefault();
						evt.stopPropagation();
					});
				}
				else {
					jButtonClose.click(function(evt){
						jWindow.hide.call(jWindow);
						
						// 阻止冒泡和默认行为事件
						evt.preventDefault();
						evt.stopPropagation();
					});
				}
			}
			
			return this;
		},
		/**
		 * 绑定 confirmBtn 的 click 事件的处理函数
		 * 
		 * @method _onConfirmBtnClick
		 * @private 
		 * @return {Object} jQuery.Window
		 */
		_onConfirmBtnClick: function(){
			var jWindow = this, 
			    setting = this.setting,
				jButtonConfirm = this.ButtonConfirm;
			
			if (jButtonConfirm) {
				// 绑定确定按钮的事件处理函数
				if (setting.confirmCallback && $.isFunction(setting.confirmCallback)) {
					jButtonConfirm.click(function(){
						setting.confirmCallback();
						
						if (jWindow.Window.hasClass(CLS_WIN_DIALOG)) {
							jWindow.hide.call(jWindow);
						}
					});
				}
				else {
					jButtonConfirm.click(function(){
						jWindow.hide.call(jWindow);
					});
				}
			}
			
			return this;
		},
		/**
		 * 绑定 cancelBtn 的 click 事件的处理函数
		 * 
		 * @method _onCancelBtnClick
		 * @private 
		 * @return {Object} jQuery.Window
		 */
		_onCancelBtnClick: function(){
			var jWindow = this, 
			    setting = this.setting,
				jButtonCancel = this.ButtonCancel;
			
			if (jButtonCancel) {
				// 绑定取消按钮的事件处理函数
				if (setting.cancelCallback && $.isFunction(setting.cancelCallback)) {
					jButtonCancel.click(function(){
						setting.cancelCallback();
						
						if (jWindow.jWindow.hasClass(CLS_WIN_DIALOG)) {
							jWindow.hide.call(jWindow);
						}
					});
				}
				else {
					jButtonCancel.click(function(){
						jWindow.hide.call(jWindow);
					});
				}
			}
			
			return this;
		},
		/**
		 * IE6 中绑定 window 的 resize 和 scroll 事件，固定窗口位置
		 * 
		 * @method _onWindowResize
		 * @private 
		 * @return {Object} jQuery.Window
		 */
		_onWindowResize: function(){
			var Window = this.Window,
			    setting = this.setting,
				browser = $.browser,
			    dialogOffsetHeight = 0,  
				marginTop = 0;
			
			// IE6  中固定位置的  HACK
			if (Window && browser.msie && browser.version < 7) {
				dialogOffsetHeight = Window.height();
				marginTop = -((dialogOffsetHeight + 10) / 2);
				 
				// 添加 resize 事件
				$(window).resize(function(){
					var scrollTop = $(window).scrollTop();
					Window.css(MARGIN_TOP, marginTop + scrollTop);
				});
				
				// 添加 scroll 事件
				$(window).scroll(function(){
					var scrollTop = $(window).scrollTop();
					Window.css(MARGIN_TOP, marginTop + scrollTop);
				});
			}
			
			return this;
		}
	};
	
	// 创建 window 插件
	$.fn.extend({
		window: function(config){
			config.target = $(this);
			
			return new $.Window(config);
		},
		// 信息 Dialog 窗口
		msg: function(config){
			config.target = $(this);
			
			var jDialog = new $.Window($.extend({
				tltTxt: TXT_INFO,
				winId: CLS_WIN_INFO,
				winClass: CLS_WIN_DIALOG + SPACE + CLS_WIN_INFO,
				confirmTxt: TXT_ENTER,
				isRemoveWindow: true,
				isDraggable: true
			}, config));
			
			return jDialog;
		},
		// 警告 Dialog 窗口
		warn: function(config){
			config.target = $(this);
			
			var jDialog = new $.Window($.extend({
				tltTxt: TXT_WARN,
				winId: CLS_WIN_WARN,
				winClass: CLS_WIN_DIALOG + SPACE + CLS_WIN_WARN,
				confirmTxt: TXT_ENTER,
				isRemoveWindow: true,
				isDraggable: false
			}, config));
			
			return jDialog;
		},
		// 错误 Dialog 窗口
		error: function(config){
			config.target = $(this);
			var jDialog = new $.Window($.extend({
				tltTxt: TXT_ERROR,
				winId: CLS_WIN_ERROR,
				winClass: CLS_WIN_DIALOG + SPACE + CLS_WIN_ERROR,
				confirmTxt: TXT_ENTER,
				isRemoveWindow: true,
				isDraggable: false
			}, config));
			
			return jDialog;
		},
		// 确认 Dialog 窗口
		confirm: function(config){
			config.target = $(this);
			var jDialog = new $.Window($.extend({
				tltTxt: TXT_CONFIRM,
				winId: CLS_WIN_CONFIRM,
				winClass: CLS_WIN_DIALOG + SPACE + CLS_WIN_CONFIRM,
				cntHTML: EMPTY,
				confirmTxt: TXT_ENTER,
				cancelTxt: TXT_CANCEL,
				isRemoveWindow: true,
				isDraggable: true
			}, config));
			 	  
			return jDialog;
		},
		// 成功 Dialog 窗口
		success: function(config){
			config.target = $(this);
			var jDialog = new $.Window($.extend({
				tltTxt: TXT_SUCCESS,
				winId: CLS_WIN_SUCCESS,
				winClass: CLS_WIN_DIALOG + SPACE + CLS_WIN_SUCCESS,
				confirmTxt: TXT_ENTER,
				isRemoveWindow: true,
				isDraggable: false
			}, config));
			
			return jDialog;
		}
	});
})(jQuery);