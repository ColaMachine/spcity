<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<html>
<head>
  <link href="<%=path %>/css/bootstrap.min.css" rel="stylesheet">
    
<script src="<%=path %>/js/jquery.min.js" type="text/javascript"
	charset="utf-8"></script>
<script src="<%=path %>/js/jquery.validate.js" type="text/javascript"
	charset="utf-8"></script>
	<script src="<%=path %>/js/additional-methods.js" type="text/javascript"
	charset="utf-8"></script>
<style type="text/css">
body {
  padding-top: 40px;
  padding-bottom: 40px;
  background-color: #eee;
}

.form-signin {
  max-width: 330px;
  padding: 15px;
  margin: 0 auto;
}
.form-signin .form-signin-heading,
.form-signin .checkbox {
  margin-bottom: 10px;
}
.form-signin .checkbox {
  font-weight: normal;
}
.form-signin .form-control {
  position: relative;
  height: auto;
  -webkit-box-sizing: border-box;
     -moz-box-sizing: border-box;
          box-sizing: border-box;
  padding: 10px;
  font-size: 16px;
}
.form-signin .form-control:focus {
  z-index: 2;
}
.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}
.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
.failure{
color:red;
}
</style>
</head>
<body>

    <div  class="container">
      <form id="login_form" class="form-signin" action="<%=path %>/forgetpwd/save" method="post">
        <h3 class="form-signin-heading"><span id="form_title">密码重置,请输入绑定的邮箱</span>  </h3>  
	      
        <label for="email" class="sr-only">Email address</label>
        <input type="email" name="email" maxlength="50" class="form-control" placeholder="邮件地址" required  autofocus>
        <label for="validatecode" class="sr-only">Email address</label>
        <div class=" form-control " style="height:44px;">
        <input type="text" name="validatecode" maxlength="50" class="col-xs-4" placeholder="验证码" required  autofocus>
        <img id="validateimg" class="col-xs-5" src="<%=path%>/vc/${imgname}.jpg"></img>
        <a class="col-xs-3" href="javascript:void(0)" onclick="refreshImg()">刷新</a>
        </div>
        <div class="failure"  ><ul>
        </ul></div>
        <button class="btn btn-lg btn-primary btn-block"  id="loginBtn"   type="submit">发送重置邮件</button>
      </form>
    </div>
</body>
<script src="<%=path %>/js/common.js" type="text/javascript"
	charset="utf-8"></script>
<script  type="text/javascript">

var MyValidator = function() {
    var handleSubmit = function() {
        $('#login_form').validate({
        	errorElement: 'div',
			errorClass: 'help-block',
			focusInvalid: false,
            rules : {
            	 email : {
            		
            		 required : true,
                     rangelength:[1,50],
                     email:true,
                    
                 },
        validatorcode:{
        	 required : true,
        	 rangelength:[4,4],
        }
            },
            messages : {
            	email : {
            		email:"请填写真实的邮箱",
                     required : "邮箱未填写",
                     rangelength:"邮箱长度应在50字符以内",
                    
                 },
            validatorcode:{
           	 required : "请填写验证码",
           	 rangelength:"验证码不正确",
           }
            },

            highlight : function(element) {
              $(element).closest('.form-signin').removeClass('has-info').addClass('has-error');
            },

        	success: function (e) {
				$(e).closest('.form-signin').removeClass('has-error').addClass('has-info');
				$(e).remove();
			},
	
			errorPlacement: function (error, element) {
				 error.insertAfter(element); 
			},
	
			submitHandler: function (form) {
				commit();
			},
			invalidHandler: function (form) {
			}
        });

        $('#form-signin input').keypress(function(e) {
            if (e.which == 13) {
                if ($(e).closest('.form-signin').validate().form()) {
                	$(e).closest('.form-signin').submit();
                }
                return false;
            }
        });
    };
    return {
        init : function() {
            handleSubmit();
        }
    };

}();

MyValidator.init(); 
	
	function commit(){
			var jso = changeForm2Jso("#login_form");
		//先禁用按钮
		$("#loginBtn").attr("disabled","disabled");
		$.post("<%=path%>/forgetpwd/save",jso,function(data){
			if(data[AJAX_RESULT]==AJAX_SUCC){
				alert("重置邮件已经发送,现在跳回登录页");
				window.location="<%=path%>/login";
			}else{
				var ul = 	$("#login_form").find(".failure").find("ul");
				ul.empty();
				ul.append("<li>"+data.msg+"</li>");
				if(data.errors && data.errors.length>0){
					for(var i in data.errors){
						ul.append("<li style='color:red'>"+data.errors[i]+"</li>");
					}
				}
				
			}$("#loginBtn").removeAttr("disabled","");
		});
	}
	
	function refreshImg(){
		$.get("<%=path%>/validatecode",{},function(data){
			$("#validateimg").attr("src","<%=path%>/vc/"+data.imgsrc+".jpg");
		})
	
	}
  </script>
</html>
