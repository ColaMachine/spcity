<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name=”viewport” content=”width=device-width, initial-scale=1, maximum-scale=1″>
<title>后台管理系统</title>
  <link rel="stylesheet" type="text/css" href="${path}/static/css/bootstrap.min.css" >
   <link rel="stylesheet" type="text/css" href="${path}/static/css/main.css" >
    <link rel="stylesheet" type="text/css" href="${path}/static/css/menu3.css" >
    <link rel="stylesheet" type="text/css" href="${path}/static/css/style.css" >
 <link rel="stylesheet" type="text/css" href="${path}/static/css/collapse.css" >

<script type="text/javascript" src="${path}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${path}/static/js/common.js"></script>
<script type="text/javascript" src="${path}/static/js/menu.js"></script>
	<!--<script type="text/javascript" src="${path}/static/js/react.js"></script>-->

	<script type="text/javascript" charset="utf-8" src="${path}/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${path}/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="${path}/ueditor/lang/zh-cn/zh-cn.js"></script>

<%
String path = request.getContextPath();

String basePath = request.getScheme()+"://"+request.getServerName()
+":"+request.getServerPort()+path+"/";

pageContext.setAttribute("basePath",basePath);
%>

<script type="text/javascript" >
var WEBCONTEXT="${path}";
var PATH="${path}";
includeCSS([
"/static/css/font-awesome.css",



 "/static/css/grid.css",
  "/static/css/head.css",
   "/static/css/global.css",
    "/static/css/widget.css",
    /*"/static/css/window.css",*/
    "/static/css/zTreeStyle.css",
   /*  "/static/css/layer.css"*/

]);


includeJS(["/static/js/menu.js" ,
         /*  "/static/js/validmsg.js",*/
           "/static/js/DateUtils.js",
          /* "/static/js/jquery-ui.min.js",*/
          "/static/js/grid.js",
              /*"/static/js/jquery.jqGrid.js",*/
            "/static/js/jquery.form.js",
            "/static/js/grid.locale-en.js",
            "/static/js/My97DatePicker/WdatePicker.js",
            "/static/js/jquery.validate.js",
            "/static/js/additional-methods.js",
            "/static/js/index.js",
            "/static/js/window.js",
           "/static/js/bootstrap.min.js",
            "/static/js/drag.js",
            "/static/js/dialog.js",
            "/static/js/jquery.ztree.core-3.5.js",
            "/static/js/jquery.ztree.excheck-3.5.js",
             /* "/static/js/layer.js",*/
           /*  "/static/js/location.js",*/


          ]);
</script>


</head>
<body>

<div id="page" class="page pace-done" >
	   <nav class="page-nav menu-wrap clearfix  navbar-default navbar-static-side">
        <div class="logo">
            <!-- <span class="logo-head">aWiFi</span> -->

            <%-- <img src="${path}/statics/img/logo.png"></img>
 --%>
             <div class="logo-desc" ><span class="nav-icon"><i onclick="$('#page').toggleClass('collapse1')" style="" class="fa fa-reorder">&nbsp;</i></span><span class="logo-desc-text"'>后台管理系统</span></div>
        </div>
        <div id="menu" class="menu">
        </div>
    </nav>
	<div class="page-wrap main-wrap gray-bg">
		<div id="" class="page-hd row border-bottom">
                <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                    <div class="navbar-header">
                        <a id="collapse" class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                        <form role="search" class="navbar-form-custom" action="search_results.html">
                            <div class="form-group">
                                <input type="text" placeholder="查询..." class="form-control" name="top-search" id="top-search">
                            </div>
                        </form>
                    </div>
                    <ul class="nav navbar-top-links navbar-right">
                        <li>
                            <span class="m-r-sm text-muted welcome-message">欢迎来到后台管理系统.</span>
                        </li>
                        <li class="dropdown">
                            <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                                <i class="fa fa-envelope"></i>  <span class="label label-warning">0</span>
                            </a>
                            <ul class="dropdown-menu dropdown-messages">
                               <!-- <li>
                                    <div class="dropdown-messages-box">
                                        <a href="profile.html" class="pull-left">
                                            <img alt="image" class="img-circle" src="img/a7.jpg">
                                        </a>
                                        <div class="media-body">
                                            <small class="pull-right">46h ago</small>
                                            <strong>Mike Loreipsum</strong> started following <strong>Monica Smith</strong>. <br>
                                            <small class="text-muted">3 days ago at 7:58 pm - 10.06.2014</small>
                                        </div>
                                    </div>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <div class="dropdown-messages-box">
                                        <a href="profile.html" class="pull-left">
                                            <img alt="image" class="img-circle" src="img/a4.jpg">
                                        </a>
                                        <div class="media-body ">
                                            <small class="pull-right text-navy">5h ago</small>
                                            <strong>Chris Johnatan Overtunk</strong> started following <strong>Monica Smith</strong>. <br>
                                            <small class="text-muted">Yesterday 1:21 pm - 11.06.2014</small>
                                        </div>
                                    </div>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <div class="dropdown-messages-box">
                                        <a href="profile.html" class="pull-left">
                                            <img alt="image" class="img-circle" src="img/profile.jpg">
                                        </a>
                                        <div class="media-body ">
                                            <small class="pull-right">23h ago</small>
                                            <strong>Monica Smith</strong> love <strong>Kim Smith</strong>. <br>
                                            <small class="text-muted">2 days ago at 2:30 am - 11.06.2014</small>
                                        </div>
                                    </div>
                                </li>
                                <li class="divider"></li>-->
                                <li>
                                    <div class="text-center link-block">
                                        <a href="mailbox.html">
                                            <i class="fa fa-envelope"></i> <strong>暂无消息</strong>
                                        </a>
                                    </div>
                                </li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                                <i class="fa fa-bell"></i>  <span class="label label-primary">0</span>
                            </a>
                            <ul class="dropdown-menu dropdown-alerts">
                                <li>
                                    <a href="mailbox.html">
                                        <div>
                                            <i class="fa fa-envelope fa-fw"></i> 你 有  0 条消息
                                            <span class="pull-right text-muted small">0 分钟之前</span>
                                        </div>
                                    </a>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <a href="profile.html">
                                        <div>
                                            <i class="fa fa-twitter fa-fw"></i> 0 个关注
                                            <span class="pull-right text-muted small">0 分钟之前</span>
                                        </div>
                                    </a>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <a href="grid_options.html">
                                        <div>
                                            <i class="fa fa-upload fa-fw"></i> 系统重启
                                            <span class="pull-right text-muted small">很久之前</span>
                                        </div>
                                    </a>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <div class="text-center link-block">
                                        <a href="#">
                                            <strong>查看所有消息</strong>
                                            <i class="fa fa-angle-right"></i>
                                        </a>
                                    </div>
                                </li>
                            </ul>
                        </li>


                        <li>
                            <a id="loginOut">
                                <i class="fa fa-sign-out"></i> 退出
                            </a>
                        </li>
                    </ul>

                </nav>
                </div>
<div class="row wrapper border-bottom white-bg page-heading">
                <div class="col-lg-10">
                    <h2 id="page-title">主页</h2>
                    <ol class="breadcrumb">
                        <li>
                            <a href="index.htm">主页</a>
                        </li>
                        <li>
                            <a id="page-title-2"></a>
                        </li>
                       <!-- <li class="active">
                            <strong>Flot Charts</strong>
                        </li>-->
                    </ol>
                </div>
                <div class="col-lg-2">

                </div>
            </div>

            <div class="page-ft footer-wrap footer">
                        <div class="pull-right">
                            10GB of <strong>250GB</strong> Free.
                        </div>
                        <div>
                            <strong>Copyright</strong> Example Company © 2014-2015
                        </div>
                    </div>

    <div id="main " class="page-bd  main clearfix wrapper wrapper-content animated fadeInRight" >

	</div>

</div>



</div>
<div class="widget"></div>
    <div class="mask" ></div>


</body>



<script type="text/javascript" >
Ajax.getJSON(PATH+"/auth/menu/list.json",null,function(result){
var menuList =result.data;
<% HttpSession s= request.getSession();
    String resourceStr = (String)s.getAttribute("resourceStr");
%>

    zMenu.init("menu",menuList,{id:"id",url:"url",pid:"pid",name:"name"});
});
var resources="<%=resourceStr%>";
var menuList=[
              {id:100,name:"Dashboard",url:"",pid:0,icon:"fa fa-dashboard"},
                {id:1001,name:"Dashboard V1",url:"www.baidu.com",pid:100,icon:"fa fa-circle-o"},
                {id:1002,name:"Dashboard V2",url:"www.baidu.om",pid:100,icon:"fa fa-circle-o"},
              /*{id:1,name:"日志管理",url:"",pid:0,icon:"fa fa-bank"},
              {id:2,name:"访问日志",url:"",pid:1},
              {id:3,name:"异常日志",url:PATH+"/log/listRequestLog",pid:1},
              {id:21,name:"访问日志A",url:PATH+"/log/listRequestLog",pid:2},
              {id:22,name:"访问日志B",url:PATH+"/log/listRequestLog",pid:2},*/
              {id:5,name:"用户管理",url:"",pid:0,icon:"fa fa-diamond"},
                {id:51,name:"level51",url:"",pid:5,icon:"fa fa-diamond"},
                 {id:511,name:"level511",url:"",pid:51,icon:"fa fa-diamond"},
                  {id:5111,name:"level5111",url:"",pid:511,icon:"fa fa-diamond"},
                                   {id:5112,name:"level5112",url:"",pid:511,icon:"fa fa-diamond"},
                  {id:512,name:"level512",url:"",pid:51,icon:"fa fa-diamond"},
             /* {id:51,name:"用户管理",url:PATH+"/user/list.htm",icon:"fa fa-spinner",pid:5},
              {id:6,name:"角色管理",url:PATH+"/auth/role/list.htm",pid:5},*/
              {id:7,name:"日历",url:PATH+"/static/html/CalendarView.html",icon:"fa fa-calendar",pid:0},
               {id:8,name:"组件库",url:"",icon:"fa fa-bug",pid:0},
                 {id:"8-1",name:"按钮",url:PATH+"/static/html/example/button.html",icon:"fa fa-spinner",pid:8},

                     {id:"8-2",name:"导航条",url:PATH+"/static/html/example/navbar.html",icon:"fa fa-spinner",pid:8},
              {id:9,name:"手机登录页面",url:PATH+"/login/login.htm",icon:"fa fa-spinner",pid:8},
              {id:10,name:"上传图片",url:PATH+"/static/html/imageCompress.html",icon:"fa fa-bank",pid:8},
              {id:11,name:"列表",url:PATH+"/static/html/example/table.html",icon:"fa fa-spinner",pid:8},
               {id:81,name:"地区选择",url:PATH+"/static/html/example/location.html",icon:"fa fa-spinner",pid:8},
                {id:82,name:"富文本编辑器",url:PATH+"/static/html/example/uedit.html",icon:"fa fa-spinner",pid:8},
              {id:12,name:"alert",url:PATH+"/static/html/example/alert.html",icon:"fa fa-spinner",pid:8},
             /* {id:13,name:"短信",url:PATH+"/smsBatch/list.htm",icon:"fa fa-spinner",pid:0},
              {id:14,name:"合作伙伴",url:PATH+"/PartnerUserlist.htm",icon:"fa fa-spinner",pid:0},*/
              {id:15,name:"用户",url:PATH+"/sysUser/list.htm",icon:"fa fa-spinner",pid:5},
              {id:16,name:"角色",url:PATH+"/sysRole/list.htm",icon:"fa fa-spinner",pid:5},
              {id:17,name:"资源",url:PATH+"/sysResource/list.htm",icon:"fa fa-spinner",pid:5},
              /*{id:18,name:"用户角色",url:PATH+"/sysUserRole/list.htm",icon:"fa fa-spinner",pid:5},
              {id:19,name:"角色资源",url:PATH+"/sysRoleResource/list.htm",icon:"fa fa-spinner",pid:5},
              {id:20,name:"用户资源",url:PATH+"/sysUserResource/list.htm",icon:"fa fa-spinner",pid:5},*/
               {id:21,name:"用户角色关联",url:PATH+"/sysUserRole/listMapper.htm",icon:"fa fa-spinner",pid:5},
               {id:22,name:"角色资源关联",url:PATH+"/sysRoleResource/listMapper.htm",icon:"fa fa-spinner",pid:5},
               {id:23,name:"用户资源关联",url:PATH+"/sysUserResource/listMapper.htm",icon:"fa fa-spinner",pid:5},
             /* {id:24,name:"短信验证码",url:PATH+"/smsRecord/list.htm",icon:"fa fa-spinner",pid:0},*/
                   {id:25,name:"系统配置",url:PATH+"/configuration/list.htm",icon:"fa fa-spinner",pid:0},
                    {id:26,name:"动物园管理",url:"",icon:"fa fa-spinner",pid:0},
                     {id:27,name:"最新视频",url:PATH+"/videoNew/list.htm",icon:"fa fa-spinner",pid:26},
                     {id:28,name:"最新视频",url:PATH+"/static/html/videoNew.html",icon:"fa fa-spinner",pid:26},
                         {id:30,name:"海绵城市",url:"",icon:"fa fa-spinner",pid:0},
                      {id:301,name:"专家智库",url:PATH+"/expert/list.htm",icon:"fa fa-spinner",pid:30},
                      {id:302,name:"新闻资讯",url:PATH+"/artical/list.htm",icon:"fa fa-spinner",pid:30},

              ]
zMenu.init("menu",menuList,{id:"id",url:"url",pid:"pid",name:"name"});
$(document).ready(function(){if($(".page-wrap").height()<$(window).height())
    $(".page-wrap").css("min-height",$(window).height()+$(window).scrollTop());
})
$(document).scroll(function(){if($(".page-wrap").height()<$(window).height())
    $(".page-wrap").css("min-height",$(window).height()+$(window).scrollTop());
})
$(document).resize(function(){
if($(".page-wrap").height()<$(window).height())
    $(".page-wrap").css("min-height",$(window).height()+$(window).scrollTop());
})
</script>
<script type="text/javascript" src="${path}/static/js/head.js"></script>
</html>