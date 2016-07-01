
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
                    root:"#registerWrap",
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

        //this.doms.form=$$("#registerForm");
        // this.modal=$$("#registerEnterModal");
         //this.registerEnterForm=$$("#registerEnterForm");

       // this.registerBtn=this.doms.form.find("#registerBtn");

        //this.registerEnterBtn=this.modal.find("#registerEnterBtn");
         //this.registerBtn.removeAttribute("disabled");
       // this.registerEnterBtn.removeAttribute("disabled");
       // this.addEventListener();
         //  extend(this.valid,BaseValidator);
           //     this.doms.form.validate(this.valid);

    },
    addEventListener:function(){

         this.doms.submitBtn.onclick=this.submit.Apply(this );
        this.doms.picCaptchaImg.onclick=this.getPicCaptcha.Apply(this);
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


window.onload=function(){



	registerForm.init();
smsValidForm.init();
emailValidForm.init();

}




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
            this.doms[i]=$$(this.ids[i]);
            if(!this.doms[i]){
                console.log(this.ids[i] +"doesn't find ");
            }
        }



        extend(this.ids,cfg);
        this.doms.root=$$(this.ids["root"]);
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

    	var jso={};
    	jso.code=this.doms.smsCaptchaInput.value;
    	jso.email=this.doms.phone.innerText;

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
        this.doms.phone.innerText=phone;
    },
    show:function(){
     $(this.doms.root).modal("show");
        //this.doms.root.style.display="block";
    },
    addEventListener:function(){

        var _this=this;
        // this.registerEnterBtn.removeAttribute("disabled");
        this.doms.submitBtn.onclick=this.submit.Apply(this);
        this.doms.smsCaptchaBtn.onclick=function(){
            _this.captchaCutdown(this);
            //发送短信
            Ajax.post(PATH+"/forgetpwd/save.json",{"phone":_this.doms.phone.innerText},function(result){
                                                if(result.r==AJAX_SUCC){
                                                   dialog.alert("发送成功");
                                                }else{
                                                    dialog.error(result.msg);
                                                }
                                            });

        };
    }
};


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
        this.doms.root=$$(this.ids["root"]);
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

    	var jso={};
    	jso.smsCaptcha=this.doms.smsCaptchaInput.value;
    	jso.phone=this.doms.phone.innerText;
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
        this.doms.phone.innerText=phone;
    },
    show:function(){
            $(this.doms.root).modal("show");
      //  this.doms.root.style.display="block";
         //this.doms.root.style.display="block";
    },
    addEventListener:function(){

        var _this=this;
        // this.registerEnterBtn.removeAttribute("disabled");
        this.doms.submitBtn.onclick=this.submit.Apply(this);
        this.doms.smsCaptchaBtn.onclick=function(){
            _this.captchaCutdown(this);
            //发送短信
            Ajax.getJSON(PATH+"/code/sms/request.json",{"phone":_this.doms.phone.innerText},function(result){
                if(result.r==AJAX_SUCC){
                   dialog.alert("发送成功");
                }else{
                    dialog.error(result.msg);
                }
            });
        };
    }
};