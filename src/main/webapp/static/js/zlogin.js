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
var loginForm={
    ids:{
    root:null,
        form:null,
        picCaptchaInput:null,
        picCaptchaImg:null,
        userName:null,
        pwd:null,
        rememberMe:null,
        forgetLink:null,
        submitBtn:null,
    },
    doms:{

    },
    init:function(){
        var cfg={
        root:"#login",
            form:"#login_form",
            userName:"#email",
            pwd:"#loginpwd",
            picCaptchaInput:"#loginPicCaptchaInput",
            picCaptchaImg:"#loginPicCaptchaImg",
            rememberMe:"#rememberme",
            forgetLink:"#forgetLink",
            submitBtn:"#loginBtn",
        };

        extend(this.ids,cfg);
        for(var i in this.ids){
            this.doms[i]=$(this.ids[i]);
            if(!this.doms[i]){
                console.log(this.ids[i] +"doesn't find ");
            }
        }

        extend(this.valid,BaseValidator);
       this.validator= validator(this.doms.form,this.valid);
        //this.doms.form.validate(this.valid);
        this.doms.submitBtn.removeAttribute("disabled");
        this.addEventListener();
    },
    addEventListener:function(){
        //注册按钮
        var that=this;
        this.doms.submitBtn.click(this.submit.Apply(this) );
        this.doms.picCaptchaImg.click(this.getPicCaptcha.Apply(this));
        this.doms.forgetLink.click(this.forgetLink.Apply(this));
       // this.doms.picCaptchaImg.trigger("click");
        this.getPicCaptcha();
        this.doms.picCaptchaInput.onkeydown=function(e){
            var keycode=document.all?event.keyCode:e.which;
            if(keycode==13){
                that.submit();
            }
        }
         this.doms.pwd.onkeydown=function(e){
            var keycode=document.all?event.keyCode:e.which;
            if(keycode==13){
                that.submit();
            }
        }
    },
    forgetLink:function(){

        forgetPwdForm.show();
        //emailValidForm.setEmail("371452875@qq.com");
        //emailValidForm.show();
    },
    //登录按扭提交
    submit:function(){
        if(!this.validator.valid(this.doms.form)){
            return;
        }
        var dialogId = dialog.showWait();
        var jso = changeForm2Jso("#login_form");
        //jso.password=$.md5(jso.password);
        //先禁用按钮
        $("#loginBtn").setAttribute("disabled", "disabled");
        //alert($("#rememberme").attr("checked"));
        //判断是否使用记住功能
        if ($("#rememberme").getAttribute("checked") == 'checked') {//alert("选中了记住我");
        /*	console.log("选中了记住我" );*/
            console.log("username:" + jso.email);
            setCookie('username', jso.email, 365);
            setCookie('password', jso.password, 365);
        }
        Ajax.post(PATH + "/loginPost.json", jso, function(data) {
            if (data[AJAX_RESULT] == AJAX_SUCC) {
                window.location = PATH + "/index.htm";
            } else {
            dialog.close(dialogId);
            dialog.alert(data.msg);
              /*  var ul = $("#login_form").find(".failure").find("ul");
                ul.empty();
                ul.append("<li>" + data[AJAX_MSG] + "</li>");
                if (data[AJAX_ERRORS] && data[AJAX_ERRORS].length > 0) {
                    for ( var i in data[AJAX_ERRORS]) {
                        ul.append("<li style='color:red'>" + data[AJAX_ERRORS][i]
                                + "</li>");
                    }
                }*/

            }
            $("#loginBtn").removeAttribute("disabled", "");
        });
    },
    //获取验证码图片点击事件
    getPicCaptcha:function(){
        that =this;
        Ajax.getJSON(PATH+"/code/img/request.json",null,function(result){
            if(result.r==AJAX_SUCC){
               that.doms.picCaptchaImg.setAttribute("src","data:image/png;base64,"+result.data.imgdata);
            }else{
                dialog.error(result.msg);
            }
        });
    },
    valid:
    {
        rules : {
            email : {
                required : true,
              /*  email : true,*/
                rangelength : [ 1, 50 ],
                isemailorphone : true
            },
            pwd : {
                stringCheck : true,
                required : true,
                rangelength : [ 6, 15 ],
            },
            picCaptcha:{
                 required : true,
            }
        },
        messages : {
            email : {
                required : "邮箱/手机号未填写",
              /*  isemailorphone:true,*/
                rangelength : "邮箱/手机号长度应在50字符以内",
            },
            pwd : {
                required : "密码未填写",//TODO 增加判断
                rangelength : "密码应由6~20个的数字或字母组成"
            },
            picCaptcha:{
                             required : "请输入验证码",
            }
        },

    },

}
var registerForm={
    ids:{
            root:null,//根节点
            form:null,//表单
            picCaptchaInput:null,//图片输入框
            picCaptchaImg:null,//图片验证码img
            userName:null,//
            realName:null,
            pwd:null,
            pwdrepeat:null,
            submitBtn:null,
        },
    doms:{

    },

    init:function(){
         var cfg={
                    root:"#register",
                    form:"#registerForm",
                    userName:"#email",
                    pwd:"#pwd",
                    pwdrepeat:"#pwdrepeat",
                    realName:"#username",
                    picCaptchaInput:"#regPicCaptchaInput",
                    picCaptchaImg:"#regPicCaptchaImg",
                    submitBtn:"#registerBtn",
                };

                extend(this.ids,cfg);
                for(var i in this.ids){
                    this.doms[i]=$(this.ids[i]);
                    if(!this.doms[i]){
                        console.log(this.ids[i] +"doesn't find ");
                    }
                }
                extend(this.valid,BaseValidator);
                this.validator= validator(this.doms.form,this.valid);

                //this.doms.form.validate(this.valid);
                this.doms.submitBtn.removeAttribute("disabled");
                this.addEventListener();

        //this.doms.form=$("#registerForm");
        // this.modal=$("#registerEnterModal");
         //this.registerEnterForm=$("#registerEnterForm");

       // this.registerBtn=this.doms.form.find("#registerBtn");

        //this.registerEnterBtn=this.modal.find("#registerEnterBtn");
         //this.registerBtn.removeAttribute("disabled");
       // this.registerEnterBtn.removeAttribute("disabled");
       // this.addEventListener();
         //  extend(this.valid,BaseValidator);
           //     this.doms.form.validate(this.valid);

    },
    addEventListener:function(){

         this.doms.submitBtn.click(this.submit.Apply(this) );
        this.doms.picCaptchaImg.click(this.getPicCaptcha.Apply(this));
        //注册按钮




    },
       //获取验证码图片点击事件
    getPicCaptcha:function(){
        that =this;
        Ajax.getJSON(PATH+"/code/img/request.json",null,function(result){
            if(result.r==AJAX_SUCC){
               that.doms.picCaptchaImg.setAttribute("src","data:image/png;base64,"+result.data.imgdata);
            }else{
                dialog.error(result.msg);
            }
        });
    },

    submit:function () {
     if(!this.validator.valid(this.doms.form)){
    	//if (!this.doms.form.valid()) {
    		return;
    	}
    	var _this=this;
    	this.doms.submitBtn.setAttribute("disabled", "disabled");
        var jso=changeForm2Jso(this.ids.form);
        Ajax.post(PATH + "/registerPost.json", jso, function(data) {
             _this.doms.submitBtn.removeAttribute("disabled");
           if (data[AJAX_RESULT] == AJAX_SUCC) {
                //如果是用手机注册的就弹出手机验证码 发送窗口
                if(StringUtil.isPhone(jso.email)){
                //手机号复制

                    smsValidForm.setPhone(jso.email);
                    smsValidForm.show();
                    //registerForm.registerEnterForm.find("#phone").text(jso.email);
                    //registerForm.modal.modal("show");
                }else
                if(StringUtil.isEmail(jso.email)){
                //手机号复制
                    emailValidForm.setEmail(jso.email);
                     emailValidForm.show();
                    /*registerForm.registerEnterForm.find("#phone").text(jso.email);
                    registerForm.modal.modal("show");*/
                }


           } else {
            _this.alert(data[AJAX_MSG],data[AJAX_ERRORS]);

           }

        });

    	//如果是邮箱注册的就弹出邮箱验证码 发送窗口
    },
    alert:function(msg,arr){
    dialog.alert(msg);
    return;
       var ul = this.doms.root.find(".failure").find("ul");
       ul.empty();
       ul.append("<li>" + msg + "</li>");
       if (arr) {
           for ( var key in arr) {
               ul.append("<li>" + arr[key] + "</li>");
           }
       }
    },
    valid:{
        rules : {
            username : {
                required : true,
                rangelength : [ 3, 15 ]
            },
            email : {
            /*	email : true,*/
                required : true,
                isemailorphone:true,
                rangelength : [ 1, 50 ],
            /*	isemail : true*/
            },
            pwd : {
                stringCheck : true,
                required : true,
                rangelength : [ 6, 15 ]
            },
            pwdrepeat : {
                stringCheck : true,
                required : true,
                rangelength : [ 6, 15 ],
                equalTo : "#pwd"
            }
        },
        messages : {
            username : {
                required : "请填写真实的姓名",
                rangelength : "姓名长度应在5~15个字符"
            },
            email : {
                email : "请填写真实的邮箱",
                required : "邮箱未填写",
                rangelength : "邮箱长度应在50字符以内"
            },
            pwd : {
                required : "密码未填写",
                rangelength : "密码应由6~20个的数字或字母组成"
            },
            pwdrepeat : {
                required : "密码未填写",
                rangelength : "密码应由6~20个的数字或字母组成",
                equalTo : "两次输入密码不同"
            }
        }
    },


}
var smsValidForm={
 ids:{
    root:null,
            form:null,//form id
            smsCaptchaInput:null,//短信验证码输入框id
            smsCaptchaBtn:null,//短信验证码获取按钮
            phone:null,//手机span
            submitBtn:null,//提交按钮
        },
    doms:{

    },
    valid:{
        rules : {
            smsCaptcha : {
                required : true,
                rangelength : [ 3, 6 ]
            },

        },
        messages : {
            smsCaptcha : {
                required : "请填写短信验证码",
                rangelength : "长度应在3~6个字符"
            },

        }
    },
    init:function(){
        var cfg={
            root:"#smsValidDiv",
            form:"#smsValidForm",
            smsCaptchaInput:"#smsCaptcha",
            smsCaptchaBtn:"#smsCaptchaBtn",
            phone:"#phone",
            submitBtn:"#smsValidBtn",
        };


        extend(this.ids,cfg);
        this.doms.root=$(this.ids["root"]);
        for(var i in this.ids){if(i=="root")continue;
           var dom= this.doms.root.find(this.ids[i]);
            if(!dom){
                console.log(this.ids[i] +" doesn't find ");
            }else{
                 this.doms[i]=dom;
            }
        }
        extend(this.valid,BaseValidator);
        //this.doms.form.validate(this.valid);
        this.validator= validator(this.doms.form,this.valid);

        this.doms.submitBtn.removeAttribute("disabled");
        this.addEventListener();

    },

    captchaCutdown:function(smsBtn){
        var self =$(smsBtn);
        self.setAttribute('disabled', true);
        self.text('发送中');
        var time =  60;
        var sI = setInterval(function() {
            time = time - 1;
            if (time > 0) {
                self.text(time + '秒后重试');
            } else {
                window.clearInterval(sI);
                self.text('重新获取');
                self.removeAttribute("disabled");
            }
        }, 1000);
    },
   submit:function(){

    	var jso={};
    	jso.smsCaptcha=this.doms.smsCaptchaInput.val();
    	jso.phone=this.doms.phone.text();
    	if(StringUtil.isBlank(jso.smsCaptcha)|| jso.smsCaptcha.length<4){
    	    dialog.alert("请输入验证码");
    	    return;
    	}
        Ajax.post(PATH+"/validPhone.json",jso,function(result){
            if(result.r==AJAX_SUCC){
                 window.location=PATH+"/index.htm";
            }else{
                dialog.alert(result.msg);
            }

        });



    },
    setPhone:function(phone){
        this.doms.phone.text(phone);
    },
    show:function(){
        
        this.doms.root.modal("show");
    },
    addEventListener:function(){

        var _this=this;
        // this.registerEnterBtn.removeAttribute("disabled");
        this.doms.submitBtn.click(this.submit.Apply(this));
        this.doms.smsCaptchaBtn.click(function(){
            _this.captchaCutdown(this);
            //发送短信
            Ajax.getJSON(PATH+"/code/sms/request.json",{"phone":_this.doms.phone.text()},function(result){
                if(result.r==AJAX_SUCC){
                   dialog.alert("发送成功");
                }else{
                    dialog.error(result.msg);
                }
            });
        });
    }
};




var forgetPwdForm={
 ids:{
            form:null,//form id
            smsCaptchaInput:null,//短信验证码输入框id
            smsCaptchaBtn:null,//短信验证码获取按钮
            phone:null,//手机span
            submitBtn:null,//提交按钮
            pwd:null,
            pwdrepeat:null,

        },
    doms:{

    },
    valid:{
        rules : {
            forgetPwdAccount : {
                required : true,
                /*  email : true,*/
                rangelength : [ 1, 50 ],
                isemailorphone : true
            },
            forgetPwdCaptcha : {
                required : true,
                rangelength : [ 3,32 ]
            },
            forgetPwdpwd : {
                stringCheck : true,
                required : true,
                rangelength : [ 6, 15 ]
            },
            forgetPwdpwdrepeat : {
                stringCheck : true,
                required : true,
                rangelength : [ 6, 15 ],
                equalTo : "#forgetPwdpwd"
            }


        },
        messages : {
            forgetPwdAccount : {
                required : "邮箱/手机号未填写",
              /*  isemailorphone:true,*/
                rangelength : "邮箱/手机号长度应在50字符以内",
            },
            forgetPwdCaptcha : {
                required : "请填写短信验证码",
                rangelength : "长度应在3~6个字符"
            },

            forgetPwdpwd : {
                required : "密码未填写",
                rangelength : "密码应由6~20个的数字或字母组成"
            },
            forgetPwdpwdrepeat : {
                required : "密码未填写",
                rangelength : "密码应由6~20个的数字或字母组成",
                equalTo : "两次输入密码不同"
            }


        }
    },
    init:function(){

        var cfg={
            root:"#forgetPwdDiv",
            form:"#forgetPwdForm",
            smsCaptchaInput:"#forgetPwdCaptcha",
            smsCaptchaBtn:"#forgetPwdCaptchaBtn",
            phone:"#forgetPwdAccount",
            submitBtn:"#forgetPwdSubmitBtn",
            pwd:"#forgetPwdpwd",
            pwdrepeat:"#forgetPwdpwdrepeat",

        };

        extend(this.ids,cfg);
        this.doms.root=$(this.ids["root"]);
        for(var i in this.ids){
            if(i=="root")continue;
           var dom= this.doms.root.find(this.ids[i]);
            if(!dom){
                console.log(this.ids[i] +" doesn't find ");
            }else{
                 this.doms[i]=dom;
            }
        }
        extend(this.valid,BaseValidator);

        //this.doms.form.validate(this.valid);
        this.validator= validator(this.doms.form,this.valid);

        this.doms.submitBtn.removeAttribute("disabled");
        this.addEventListener();


    },

    captchaCutdown:function(smsBtn){
        var self =$(smsBtn);
        self.setAttribute('disabled', true);
        self.text('发送中');
        var time =  60;
        var sI = setInterval(function() {
            time = time - 1;
            if (time > 0) {
                self.text(time + '秒后重试');
            } else {
                window.clearInterval(sI);
                self.text('重新获取');
                self.removeAttribute("disabled");
            }
        }, 1000);
    },
   submit:function(){

    	var jso={};
    	jso.code=this.doms.smsCaptchaInput.val();
    	jso.account=this.doms.phone.val();
    	jso.pwd=this.doms.pwd.val();
    	if(StringUtil.isBlank(jso.code)|| jso.code.length<4){
    	    dialog.alert("请输入验证码");
    	    return;
    	}
        Ajax.post(PATH+"/pwdrst/save.json",jso,function(result){
            if(result.r==AJAX_SUCC){
                // window.location=PATH+"/index.htm";
                 dialog.alert("密码重置成功!");
            }else{
                dialog.alert(result.msg);
            }

        });



    },
    setPhone:function(phone){
        this.doms.phone.text(phone);
    },
    show:function(){
        this.doms.root.modal("show");
    },
     hide:function(){
            this.doms.root.modal("hide");
        },
    addEventListener:function(){

        var _this=this;
        // this.registerEnterBtn.removeAttribute("disabled");
        this.doms.submitBtn.click(this.submit.Apply(this));
        this.doms.smsCaptchaBtn.click(function(){
            _this.captchaCutdown(this);
           // forgetpwd/save.json
            if(StringUtil.isEmail(_this.doms.phone.val())){
                 Ajax.post(PATH+"/forgetpwd/save.json",{"phone":_this.doms.phone.val()},function(result){
                                    if(result.r==AJAX_SUCC){
                                       dialog.alert("发送成功");
                                    }else{
                                        dialog.error(result.msg);
                                    }
                                });
            }else if(StringUtil.isPhone(_this.doms.phone.val())){
                  //发送短信
                Ajax.getJSON(PATH+"/code/sms/request.json",{"phone":_this.doms.phone.val()},function(result){
                    if(result.r==AJAX_SUCC){
                       dialog.alert("发送成功");
                    }else{
                        dialog.error(result.msg);
                    }
                });
            }

        });
    }
};

function doRegister() {
	document.location = "register.html";
}


var form_type = "login";
//切换登录表单和注册表单
/*function changeForm() {
	$("#registerBtn").removeAttribute("disabled");
	$("#loginBtn").removeAttribute("disabled");
	if (form_type == "login") {
		$("#register_form").show();
		$("#login_form").hide();
		form_type = "register"
	} else {
		form_type = "login";
		$("#register_form").hide();
		$("#login_form").show();
	}
}*/


/***
 ** 取cookie值
 *
 */
function getCookie(c_name) {
	console.log("cookie:"+document.cookie);
	if (document.cookie.length > 0) {
		c_start = document.cookie.indexOf(c_name + "=")
		if (c_start != -1) {
			c_start = c_start + c_name.length + 1
			c_end = document.cookie.indexOf(";", c_start)
			if (c_end == -1)
				c_end = document.cookie.length
			return unescape(document.cookie.substring(c_start, c_end))
		}
	}
	return ""
}
//设置cookie值
function setCookie(c_name, value, expiredays) {console.log("expiredays:"+expiredays);
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + expiredays);
	console.log(c_name + "=" + escape(value)
			+ ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString()));
	document.cookie = c_name + "=" + escape(value)
			+ ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
}

function checkCookie() {
	var username = getCookie('username')
	$("#login-email").value=username;
	//alert(username+$("#login-email").val());
	//return;
	if (username != null && username != "") {//alert('Welcome again '+username+'!')
		document.getElementById("login-email").value = username;

		$("#login-email").value=username;
	}

	else {
		//username = prompt('Please enter your name:', "")
		if (username != null && username != "") {
			setCookie('username', username, 7)
		}
	}
}
/**
 *登录
 *
 **/

window.onload=function(){

	//$("#loginBtn").bind('click',function(){alert('tt!')});
	//登录表单验证器初始化
	loginForm.init();
	//loginValidator.init();
	//注册表单初始化
	//registerValidator.init();
	registerForm.init();
	smsValidForm.init();
	forgetPwdForm.init();
    emailValidForm.init();

//在这里面输入任何合法的js语句
//页面层-自定义
/*layer.open({
  type: 1,
  title: false,
  closeBtn: 1,
  shadeClose: false,
  skin: 'yourclass',
  content: '自定义HTML内容'
});*/

	checkCookie();

}
/*
$(document).ready(function() {



	//show user name and password

});
*/




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























var emailValidForm={
 ids:{
    root:null,
    form:null,//form id
    smsCaptchaInput:null,//短信验证码输入框id
    smsCaptchaBtn:null,//短信验证码获取按钮
    phone:null,//手机span
    submitBtn:null,//提交按钮
},
    doms:{

    },
    valid:{
        rules : {
            smsCaptcha : {
                required : true,
                rangelength : [ 3, 6 ]
            },

        },
        messages : {
            smsCaptcha : {
                required : "请填写短信验证码",
                rangelength : "长度应在3~6个字符"
            },

        }
    },
    init:function(){
        var cfg={
            root:"#emailValidDiv",
            form:"#smsValidForm",
            smsCaptchaInput:"#smsCaptcha",
            smsCaptchaBtn:"#smsCaptchaBtn",
            phone:"#phone",
            submitBtn:"#smsValidBtn",
        };

        extend(this.ids,cfg);
        for(var i in this.ids){
            this.doms[i]=$(this.ids[i]);
            if(!this.doms[i]){
                console.log(this.ids[i] +"doesn't find ");
            }
        }



        extend(this.ids,cfg);
        this.doms.root=$(this.ids["root"]);
        for(var i in this.ids){if(i=="root")continue;
           var dom= this.doms.root.find(this.ids[i]);
            if(!dom){
                console.log(this.ids[i] +" doesn't find ");
            }else{
                 this.doms[i]=dom;
            }
        }
        extend(this.valid,BaseValidator);

 //       this.doms.form.validate(this.valid);
        this.validator= validator(this.doms.form,this.valid);

        this.doms.submitBtn.removeAttribute("disabled");
        this.addEventListener();

    },

    captchaCutdown:function(smsBtn){
        var self =$(smsBtn);
        self.setAttribute('disabled', true);
        self.text('发送中');
        var time =  60;
        var sI = setInterval(function() {
            time = time - 1;
            if (time > 0) {
                self.text(time + '秒后重试');
            } else {
                window.clearInterval(sI);
                self.text('重新获取');
                self.removeAttribute("disabled");
            }
        }, 1000);
    },
   submit:function(){

    	var jso={};
    	jso.code=this.doms.smsCaptchaInput.val();
    	jso.email=this.doms.phone.text();

    	if(StringUtil.isBlank(jso.code)|| jso.code.length<4){
    	    dialog.alert("请输入验证码");
    	    return;
    	}
    	if(StringUtil.isBlank(jso.email) || !StringUtil.isEmail(jso.email)){
            dialog.alert("邮件地址不能为空");
            return;
        }
        Ajax.post(PATH+"/validEmail.json",jso,function(result){
            if(result.r==AJAX_SUCC){
                dialog.alert("邮件激活成功,点击自动登录",function(){
                     window.location=PATH+"/index.htm";
                });

            }else{
                dialog.alert(result.msg);
            }

        });



    },
    setEmail:function(phone){
        this.doms.phone.text(phone);
    },
    show:function(){
        this.doms.root.modal("show");
    },
    addEventListener:function(){

        var _this=this;
        // this.registerEnterBtn.removeAttribute("disabled");
        this.doms.submitBtn.click(this.submit.Apply(this));
        this.doms.smsCaptchaBtn.click(function(){
            _this.captchaCutdown(this);
            //发送短信
            Ajax.post(PATH+"/forgetpwd/save.json",{"phone":_this.doms.phone.text()},function(result){
                                                if(result.r==AJAX_SUCC){
                                                   dialog.alert("发送成功");
                                                }else{
                                                    dialog.error(result.msg);
                                                }
                                            });

        });
    }
};


jQuery=function( selector, context){
   	return  jQuery.fn.init( selector, context );
}


jQuery.fn=jQuery.prototype={

init:function(selector,context){

    if(selector[0]=='#'){
        var dom = document.getElementById(selector.replace("#",''));
        if(dom){
            dom.find=find;
        }
        return dom;
    }
    if(selector[0]=='.'){
        var dom = document.getElementById(selector.replace(".",''));
                if(dom){
                    dom[0].find=find;
                    return dom[0];
                }
                return dom;
    }


}
}

jQuery.extend=function(obj1,obj2){

}
jQuery.fn.init.prototype = jQuery.fn;
jQuery.extend = jQuery.fn.extend = function(obj1,obj2){
    for(var key in obj2){
        obj1[key]=obj2[key];
    }

}
function find(selector){
     if(selector[0]=='#'){
            var dom = getChild(this,selector);
            if(dom){
                dom.find=find;
            }
            return dom;
        }
        if(selector[0]=='.'){
         var dom =getChild(this,selector);
         if(dom){
                         dom[0].find=find;
                          return dom[0];
                     }
           return dom;
        }
}
$=jQuery;
