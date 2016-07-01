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
            root:"#login_wrap",
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
            this.doms[i]=$$(this.ids[i]);
            if(!this.doms[i]){
                console.log(this.ids[i] +"doesn't find ");
            }
        }

        extend(this.valid,BaseValidator);
       this.validator= validator(this.doms.form,this.valid);
        //this.doms.form.validate(this.valid);
        this.doms.submitBtn.removeAttribute("disabled");
        this.addEventListener();
        this.checkCookie();
    },
    addEventListener:function(){
        //注册按钮
        var that=this;
        this.doms.submitBtn.onclick=this.submit.Apply(this) ;
        this.doms.picCaptchaImg.onclick=this.getPicCaptcha.Apply(this);
        this.doms.forgetLink.onclick=this.forgetLink.Apply(this);
       // this.doms.picCaptchaImg.trigger("click");
        this.getPicCaptcha();
        this.doms.picCaptchaInput.onkeydown=function(e){
            var keycode=document.all?event.keyCode:e.which;
            if(keycode==13){
                that.submit();
                this.blur();
            }
        }
         this.doms.pwd.onkeydown=function(e){
            var keycode=document.all?event.keyCode:e.which;
            if(keycode==13){
                that.submit();
                this.blur();
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
        var jso={};
        jso.email=this.doms.userName.value;
        jso.pwd=this.doms.pwd.value;
        jso.picCaptcha=this.doms.picCaptchaInput.value;
        //jso.password=$$.md5(jso.password);
        //先禁用按钮
        $$("#loginBtn").setAttribute("disabled", "disabled");
        //alert($$("#rememberme").attr("checked"));
        //判断是否使用记住功能
        if ($$("#rememberme").getAttribute("checked") == 'checked') {//alert("选中了记住我");
        /*	console.log("选中了记住我" );*/
            console.log("username:" + jso.email);
            this.setCookie('username', jso.email, 365);
            this.setCookie('password', jso.password, 365);
        }
        Ajax.post(PATH + "/loginPost.json", jso, function(data) {
            if (data[AJAX_RESULT] == AJAX_SUCC) {
                window.location = PATH + "/index.htm";
            } else {
            dialog.close(dialogId);
            dialog.alert(data.msg);
              /*  var ul = $$("#login_form").find(".failure").find("ul");
                ul.empty();
                ul.append("<li>" + data[AJAX_MSG] + "</li>");
                if (data[AJAX_ERRORS] && data[AJAX_ERRORS].length > 0) {
                    for ( var i in data[AJAX_ERRORS]) {
                        ul.append("<li style='color:red'>" + data[AJAX_ERRORS][i]
                                + "</li>");
                    }
                }*/

            }
            $$("#loginBtn").removeAttribute("disabled", "");
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

/***
 ** 取cookie值
 *
 */
getCookie:function (c_name) {
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
},
//设置cookie值
setCookie:function (c_name, value, expiredays) {console.log("expiredays:"+expiredays);
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + expiredays);
	console.log(c_name + "=" + escape(value)
			+ ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString()));
	document.cookie = c_name + "=" + escape(value)
			+ ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
},

checkCookie:function () {
	var username = this.getCookie('username')

	//alert(username+$$("#login-email").val());
	//return;
	if (username != null && username != "") {//alert('Welcome again '+username+'!')

        this.doms.userName.value=username;

	}

	else {
		//username = prompt('Please enter your name:', "")
		if (username != null && username != "") {
			this.setCookie('username', username, 7)
		}
	}
}

}




var forgetPwdForm={
 ids:{      root:null,
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
                rangelength : [ 3,50 ]
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
                rangelength : "长度应在3~50个字符"
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
        this.doms.root=$$(this.ids["root"]);
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
        var self =$$(smsBtn);
        self.setAttribute('disabled', true);
        self.innerText='发送中';
        var time =  60;
        var sI = setInterval(function() {
            time = time - 1;
            if (time > 0) {
                self.innerText=time + '秒后重试';
            } else {
                window.clearInterval(sI);
                self.innerText='重新获取';
                self.removeAttribute("disabled");
            }
        }, 1000);
    },
   submit:function(){
        if(! this.validator.valid(this.doms.form)){
            return;
        }
    	var jso={};
    	jso.code=this.doms.smsCaptchaInput.value;
    	jso.account=this.doms.phone.value;
    	jso.pwd=this.doms.pwd.value;
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
        this.doms.phone.innerText=phone;
    },
    show:function(){
        dialog.showModal(this.doms.root);
    },
    hide:function(){
            hideModal(this.doms.root);
    },
    addEventListener:function(){

        var _this=this;
        // this.registerEnterBtn.removeAttribute("disabled");
        this.doms.submitBtn.onclick=this.submit.Apply(this);
        this.doms.smsCaptchaBtn.onclick=function(){
            _this.captchaCutdown(this);
           // forgetpwd/save.json
            if(StringUtil.isEmail(_this.doms.phone.value)){
                 Ajax.post(PATH+"/forgetpwd/save.json",{"phone":_this.doms.phone.value},function(result){
                                    if(result.r==AJAX_SUCC){
                                       dialog.alert("发送成功");
                                    }else{
                                        dialog.error(result.msg);
                                    }
                                });
            }else if(StringUtil.isPhone(_this.doms.phone.value)){
                  //发送短信
                Ajax.getJSON(PATH+"/code/sms/request.json",{"phone":_this.doms.phone.value},function(result){
                    if(result.r==AJAX_SUCC){
                       dialog.alert("发送成功");
                    }else{
                        dialog.error(result.msg);
                    }
                });
            }

        };
    }
};

function doRegister() {
	document.location = "register.html";
}


var form_type = "login";
//切换登录表单和注册表单
/*function changeForm() {
	$$("#registerBtn").removeAttribute("disabled");
	$$("#loginBtn").removeAttribute("disabled");
	if (form_type == "login") {
		$$("#register_form").show();
		$$("#login_form").hide();
		form_type = "register"
	} else {
		form_type = "login";
		$$("#register_form").hide();
		$$("#login_form").show();
	}
}*/


/**
 *登录
 *
 **/

window.onload=function(){

	//$$("#loginBtn").bind('click',function(){alert('tt!')});
	//登录表单验证器初始化
	loginForm.init();
	//loginValidator.init();
	//注册表单初始化
	//registerValidator.init();
	//registerForm.init();
	//smsValidForm.init();
	forgetPwdForm.init();
    //emailValidForm.init();

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



}
/*
$$(document).ready(function() {



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






















