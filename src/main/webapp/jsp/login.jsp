<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>登录</title>
 <%
      String path = request.getContextPath();

      String basePath = request.getScheme()+"://"+request.getServerName()
      +":"+request.getServerPort()+path+"/";
      pageContext.setAttribute("basePath",basePath);
      pageContext.setAttribute("path",path);
      %>
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
    <script src="${path}/static/js/zlogin.js" type="text/javascript"
            charset="utf-8"></script>
      <script src="${path}/static/js/common.js" type="text/javascript"
                charset="utf-8"></script>
<style type="text/css">
.help-block-left-animation{
     color:red !important;
 }
</style>
</head>

<body class="gray-bg">

    <div class="loginColumns animated fadeInDown">
        <div class="row">

            <div class="col-md-6">
                <h2 class="font-bold">欢迎来到 Welcome to </h2>
                <h1 style="font-size:130px;color: #e6e6e6;font-weight: 800;    letter-spacing: -10px;">海绵＋</h1>
                <p>
                    <span style="font-weight:bold">海绵城市</span>，是新一代城市雨洪管理概念，是指城市在适应环境变化和应对雨水带来的自然灾害等方面具有良好的“弹性”，也可称之为“水弹性城市”。
                </p>
                <p>
                </p>
 <p>
国际通用术语为“低影响开发雨水系统构建”。  下雨时吸水、蓄水、渗水、净水，需要时将蓄存的水“释放”并加以利用。
 </p>
                <p>
                    <small>The Sponge City</small>
                </p>

            </div>
            <div id="login_wrap" class="col-md-6">
                <div class="ibox-content">
                    <form id="login_form" class="m-t" role="form" action="index.html">
                        <div class="form-group">
                            <input type="email" id="email" name="email" class="form-control" placeholder="手机号/邮箱" required="">
                        </div>
                        <div class="form-group">
                            <input type="password"  id="loginpwd" name="pwd"  class="form-control" placeholder="密码" required="">
                        </div>
                         <div class="form-group">
                            <div class="input-group">
                                <div style="position:relative;overflow:hidden">
                                <input type="text" id="loginPicCaptchaInput" name="picCaptcha" class="form-control" placeholder="验证码">
                               </div>
                                <span  style="margin:0;padding:0" class="input-group-addon bg-white"><img  style=" height:28px" id="loginPicCaptchaImg"
                                        src="" alt="点击刷新验证码"/></span>
                            </div>
                        </div>
                        <button id="loginBtn" type="submit" class="btn btn-primary block full-width m-b">登录</button>


                          <div class="checkbox i-checks"><label> <input id="rememberme" type="checkbox"><i></i> 记住我 </label></div>

    <div><a id="forgetLink" href="javascript:void(0)" style="float: right"><small>忘记密码?</small></a></div>

                        <p class="text-muted text-center">
                            <small>没有账号?</small>
                        </p>
                        <a class="btn btn-sm btn-white btn-block" href="${path}/register.htm">创建一个账号</a>
                    </form>
                    <p class="m-t">
                        <small>为了您的愉悦体验,欢迎反馈您的使用体验 © 2015</small>
                    </p>
                </div>
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col-md-6">
                Copyright <mail>likegadfly@163.com</mail>
            </div>
            <div class="col-md-6 text-right">
               <small>© 2014-2020</small>
            </div>
        </div>
    </div>



<div class="modal fade "   id="forgetPwdDiv"  tabindex="-1" role="dialog" aria-labelledby="myModalLabel">

        <div class="modal-dialog modal-dialog-small" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title center" id="modalTitle">找回密码</h4>

                    <h6 class="modal-title center" id="modalDescri">请输入你的账号绑定邮箱或者绑定手机</h6>
                </div>
                <div class="modal-body">
                    <form id="forgetPwdForm" style="" class="form-signin"
                          action="${path}/pwdrestPost" method="post">
                        <div class="form-group">


                            <label for="forgetPwdAccount" class="sr-only">邮箱地址/手机号码</label>
                            <input id="forgetPwdAccount" type="text" name="forgetPwdAccount" maxlength="50"
                                   class="form-control username" placeholder="邮件地址/手机号码" required autofocus/>
                        </div>
                        <!--  <div class="form-group">
                              <label for="forgetPwdCaptcha" class="sr-only">图片验证码</label>
                              <div class="input-group">
                                  <div style="position:relative;overflow:hidden">
                                      <input type="text" id="loginPicCaptchaInput" name="forgetPwdCaptcha" class="form-control" placeholder="图片验证码">
                                  </div>
                              <span  class="input-group-addon bg-white"><img id="loginPicCaptchaImg"
                                                                             src="" alt="点击刷新验证码"/></span>
                              </div>
                          </div>-->
                        <div class="form-group">
                            <label for="email" class="sr-only">验证码</label>
                            <div class="input-group">
                                <div style="position:relative;overflow:hidden">
                                    <input type="text" id="forgetPwdCaptcha" name="forgetPwdCaptcha" class="form-control"  placeholder="验证码">
                                </div>
                                <a id="forgetPwdCaptchaBtn" class="input-group-addon bg-white ">发送验证码</a>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="pwd" class="sr-only">密码</label>
                            <input
                                    type="password" id="forgetPwdpwd" name="forgetPwdpwd" maxlength="50"
                                    class="form-control" placeholder="密码" required/>
                        </div>
                        <div class="form-group">
                            <label
                                    for="pwdrepeat" class="sr-only">再次输入密码</label> <input
                                type="password" id="forgetPwdpwdrepeat" name="forgetPwdpwdrepeat" maxlength="50"
                                class="form-control" placeholder="请再次输入密码" required/>
                        </div>
                    </form>
                </div>
                <div class="modal-footer ">
                    <button type="button" id="forgetPwdSubmitBtn" class="btn btn-primary center">重置密码</button>
                </div>
            </div>
        </div>

</div>
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
