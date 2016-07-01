<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>登录</title>

    <link rel="stylesheet" type="text/css" href="${path}/static/css/main.css" >
    <link rel="stylesheet" type="text/css" href="${path}/static/css/widget.css" >
        <link rel="stylesheet" type="text/css" href="${path}/static/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="${path}/static/css/style.css" >
    <link rel="stylesheet" type="text/css" href="${path}/static/css/custom.css" >
    <script type="text/javascript" >
    var PATH="${path}";
    </script>
    <script src="${path}/static/js/jquery.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="${path}/static/js/zregister.js" type="text/javascript"
            charset="utf-8"></script>
      <script src="${path}/static/js/common.js" type="text/javascript"
                charset="utf-8"></script>
                  <script src="${path}/static/js/bootstrap.min.js" type="text/javascript"
                                charset="utf-8"></script>
<style type="text/css">
.help-block-left-animation{
     color:red !important;
     text-align:left;
 }
</style>
</head>
<body class="gray-bg">

    <div id="registerWrap" class="middle-box text-center loginscreen   animated fadeInDown">
        <div>
            <div>

                <h1 style="font-size:110px" class="logo-name">海绵＋</h1>

            </div>
            <h3>注册 to 海绵+</h3>
            <p>注　册　一　个　账　号　瞧　瞧　吧.</p>
            <form id="registerForm" class="m-t" role="form" action="login.html">


                <div class="form-group">
                    <input type="text" id="username" name="username"  class="form-control" placeholder="姓名" required="">
                </div>
                <div class="form-group">
                    <input type="email" id="email"  name="email" class="form-control" placeholder="邮箱/手机" required="">
                </div>
                <div class="form-group">
                    <input type="password" id="pwd" name="pwd" class="form-control" placeholder="密码" required="">
                </div>
                 <div class="form-group">
                    <label
                            for="pwdrepeat" class="sr-only">再次输入密码</label> <input
                        type="password" id="pwdrepeat" name="pwdrepeat" maxlength="50"
                        class="form-control" placeholder="请再次输入密码" required/>
                </div>
                <div class="form-group">
                    <label for="regPicCaptchaInput" class="sr-only">图片验证码</label>
                    <div class="input-group">
                        <div style="position:relative;overflow:hidden">
                        <input type="text" id="regPicCaptchaInput" name="picCaptcha" class="form-control" placeholder="图片验证码">
                        </div>
                        <span  style="margin:0;padding:0" class="input-group-addon bg-white"><img style=" height:28px"  id="regPicCaptchaImg"
                                src="" alt="点击获取验证码"/></span>
                </div>
                <div class="form-group">
                           <div class="checkbox i-checks"><label> <input type="checkbox"><i></i> 同意此协议 </label></div>

                </div>
                <button type="submit" id="registerBtn" class="btn btn-primary block full-width m-b">注册</button>

                <p class="text-muted text-center"><small>已经有账号了吗?</small></p>
                <a class="btn btn-sm btn-white btn-block" href="${path}/login.htm">登录</a>
            </form>
            <p class="m-t"> <small>Inspinia we app framework base on Bootstrap 3 © 2014</small> </p>
        </div>
    </div>



<div class="modal fade" id="smsValidDiv"  tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <form id="smsValidForm" >
    <div class="modal-dialog modal-dialog-small" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title center" id="modalTitle">验证手机</h4>
                <h6 class="modal-title center" id="modalDescri">请输入你收到的6位数字短信验证码</h6>
            </div>
            <div class="modal-body">
                <form id="registerEnterForm" style="" class="form-signin"
                      action="${path}/pwdrestPost" method="post">
                    <div class="form-group">
                        <label for="username" class="sr-only">手机号码</label>
                        <span   id="phone"
                                name="phone"  class="form-control"
                        ></span>
                    </div>
                    <div class="form-group">
                        <label for="email" class="sr-only">手机验证码</label>
                        <div class="input-group">
                            <div style="position:relative;overflow:hidden">
                                <input type="text" id="smsCaptcha" name="smsCaptcha" class="form-control" placeholder="手机验证码">
                            </div>
                            <a id="smsCaptchaBtn" class="input-group-addon bg-white ">发送验证码</a>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer ">
                <button type="button" id="smsValidBtn" class="btn btn-primary center">点击进入</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="emailValidDiv"  tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <form id="emailValidForm">
    <div class="modal-dialog modal-dialog-small" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title center" id="modalTitle">验证邮箱</h4>
                <h6 class="modal-title center" id="modalDescri">请输入你收到的邮箱验证码</h6>
            </div>
            <div class="modal-body">
                <form id="registerEnterForm" style="" class="form-signin"
                      action="${path}/pwdrestPost" method="post">
                    <div class="form-group">
                        <label for="username" class="sr-only">邮箱地址</label>
                        <span   id="phone"
                                name="phone"  class="form-control"
                        ></span>
                    </div>
                    <div class="form-group">
                        <label for="email" class="sr-only">邮箱验证码</label>
                        <div class="input-group">
                            <div style="position:relative;overflow:hidden">
                                <input type="text" id="smsCaptcha" name="smsCaptcha" class="form-control" placeholder="邮箱验证码">
                            </div>
                            <a id="smsCaptchaBtn" class="input-group-addon bg-white ">发送验证码</a>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer ">
                <button type="button" id="smsValidBtn" class="btn btn-primary center">点击进入</button>
            </div>
        </div>
    </div>
        </form>
</div>


    <!-- Mainly scripts -->

    <!-- iCheck -->
    <script src="${path}/static/js/icheck.min.js"></script>
    <script>

        $(document).ready(function(){
            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
            });
        });
    </script>



</body>
</html>
