<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name=”viewport” content=”width=device-width, initial-scale=1, maximum-scale=1″>
<title>后台管理系统</title>
<script type="text/javascript" src="${path}/static/js/jquery.js"></script>
<script type="text/javascript" src="${path}/static/js/common.js"></script>
<script type="text/javascript" >
var WEBCONTEXT="${path}";
var PATH="${path}"; 
includeCSS(["/static/css/bootstrap.min.css",
"/static/css/font-awesome.css",
"/static/css/main.css",
"/static/css/menu.css",

/*"/static/css/form.css",
"/static/css/col.css",
"/static/css/font.css",
*/
  /*"/static/css/jqgrid.css",
 "/static/css/ui.jqgrid.css",*/
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
             "/static/js/location.js",
               "/static/js/ueditor/ueditor.config.js",
                 "/static/js/ueditor/ueditor.all.min.js",
                 "/static/js/ueditor/lang/zh-cn/zh-cn.js"

          ]);
</script>


</head>
<body>

<div id="page" class="page" style="">
	   <div class=" menu-wrap clearfix  ">
        <div class="logo">
            <!-- <span class="logo-head">aWiFi</span> -->
            
            <%-- <img src="${path}/statics/img/logo.png"></img>
 --%>           
             <div class="logo-desc" ><span class="nav-icon"><i onclick="$('#page').toggleClass('collapse1')" style="" class="fa fa-reorder">&nbsp;</i></span><span class="logo-desc-text"'>后台管理系统</span></div>
        </div>
        <div id="menu" class="menu">
        </div>
    </div>
	<div class="main-wrap">
		<div  class="head-wrap navbar white">

            <div class="navbar-item pull-left h5 ng-binding"  ng-bind="$state.current.data.title" id="pageTitle" >后台管理系统</div>
            <!-- 				<div class="dropdown pull-left navbar-item "  style="vertical-align:middle">
              <button class="btn btn-default dropdown-toggle" type="button"
              id="dropdownMenu1" data-toggle="dropdown"
              aria-haspopup="true" aria-expanded="true">
                Dropdown
                <span class="caret"></span>
              </button>
              <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                <li><a href="#">Action</a></li>
                <li><a href="#">Another action</a></li>
                <li><a href="#">Something else here</a></li>
                <li><a href="#">Separated link</a></li>
              </ul>
            </div> -->
            <ul class="login-info navbak navbar-nav pull-right">
                <li class="nav-item dropdown pos-stc-xs"><a class="nav-link"
                    href="" data-toggle="dropdown">
                    <i class="fa fa-bell-o"></i>
                        <span class="label label-sm up warn">0</span></a>
                </li>

                <li class="nav-item dropdown">
                    <a aria-expanded="false" class="nav-link clear" href="" data-toggle="dropdown">&nbsp;<span
                        class="avatar w-32"><img src="static/img/a0.jpg"
                            alt="..."> <i class="on b-white bottom"></i></span></a>
                            <ul id="menu1" class="pull-right dropdown-menu" aria-labelledby="drop4">
                                <li><a href="${path}/logout.htm" id="logout">登出</a></li>
                                    <!--<li role="separator" class="divider"></li>
                            <li><a href="#">修改资料</a></li>-->
                            </ul>
                        </li>

                                <!-- <li><a><img style="width:50px ;height:50px;" alt="头像" src="img/a0.jpg" /></a>
                        </li>-->

            </ul>
            <div id="dialog-zone"></div>
                        <!-- <div class="collapse navbar-toggleable-sm"   id="collapse">
                                <form
                                    class="navbar-form form-inline pull-right pull-none-sm navbar-item v-m ng-pristine ng-valid ng-scope"
                                    role="search">

                                    <div class="form-group l-h m-a-0">
                                        <div class="input-group input-group-sm">
                                            <input class="form-control p-x b-a rounded"
                                                placeholder="Search projects..." type="text"> <span
                                                class="input-group-btn"><button type="submit"
                                                    class="btn white b-a rounded no-b-l no-shadow">
                                                    <i class="fa fa-search"></i>
                                                </button></span>
                                        </div>
                                    </div>
                                </form>
                                <ul class="nav navbar-nav" >
                                    <li class="nav-item dropdown"><a aria-expanded="false"
                                        class="nav-link" href="" data-toggle="dropdown"><i
                                            class="fa fa-fw fa-plus text-muted"></i> <span>New</span></a>
                                    <div class="dropdown-menu dropdown-menu-scale ng-scope">
                                            <a href="#/app/inbox/compose" class="dropdown-item"
                                                ui-sref="app.inbox.compose"><span>Inbox</span></a> <a
                                                href="#/app/todo" class="dropdown-item" ui-sref="app.todo"><span>Todo</span></a>
                                            <a href="#/app/note/list" class="dropdown-item"
                                                ui-sref="app.note.list"><span>Note</span> <span
                                                class="label primary m-l-xs">3</span></a>
                                            <div class="dropdown-divider"></div>
                                            <a href="#/app/contact" class="dropdown-item"
                                                ui-sref="app.contact">Contact</a>
                                        </div></li>
                                </ul>
                            </div> -->
		</div>

    <div class="footer-wrap"></div>
    <div id="main " class="main" >
		<!--<div id="main " class="main-content  container " >
        
		<div class="row white" style="color:black;margin-top:15px;">
		    <div class="col-xs-4 ">


            </div>



		</div>
		<h1></h1>

			
			
		</div>--><!--  main content 结束 -->
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
              /*{id:1,name:"日志管理",url:"",pid:0,icon:"fa fa-bank"},
              {id:2,name:"访问日志",url:"",pid:1},
              {id:3,name:"异常日志",url:PATH+"/log/listRequestLog",pid:1},
              {id:21,name:"访问日志A",url:PATH+"/log/listRequestLog",pid:2},
              {id:22,name:"访问日志B",url:PATH+"/log/listRequestLog",pid:2},*/
              {id:5,name:"用户管理",url:"",pid:0,icon:"fa fa-diamond"},
             /* {id:51,name:"用户管理",url:PATH+"/user/list.htm",icon:"fa fa-spinner",pid:5},
              {id:6,name:"角色管理",url:PATH+"/auth/role/list.htm",pid:5},*/
              {id:7,name:"日历",url:PATH+"/static/html/CalendarView.html",icon:"fa fa-calendar",pid:0},
               {id:8,name:"组件库",url:"",icon:"fa fa-bug",pid:0},

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
//zMenu.init("menu",menuList,{id:"id",url:"url",pid:"pid",name:"name"});


</script>
</html>