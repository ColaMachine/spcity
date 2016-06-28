<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name=”viewport” content=”width=device-width, initial-scale=1, maximum-scale=1″>
<title>后台管理系统</title>
  <link rel="stylesheet" type="text/css" href="/spcity/static/css/bootstrap.min.css" >
   <link rel="stylesheet" type="text/css" href="/spcity/static/css/main.css" >
    <link rel="stylesheet" type="text/css" href="/spcity/static/css/menu2.css" >

    <link rel="stylesheet" type="text/css" href="/spcity/static/css/head.css" >
        <link rel="stylesheet" type="text/css" href="/spcity/static/css/global.css" >


	<script type="text/javascript" charset="utf-8" src="${path}/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${path}/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="${path}/ueditor/lang/zh-cn/zh-cn.js"></script>

>>>>>>> a8cf3d82b4af7e791c7de4b2f7d89ad15eb4bb05
<script type="text/javascript" >
var WEBCONTEXT="${path}";
var PATH="${path}";



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


<script type="text/javascript" src="${path}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${path}/static/js/common.js"></script>
<script type="text/javascript" src="${path}/static/js/menu.js"></script>
<script type="text/javascript" src="${path}/static/js/index.js"></script>
<script type="text/javascript" src="${path}/static/js/bootstrap.min.js"></script>


<script type="text/javascript" >
Ajax.getJSON(PATH+"/auth/menu/list.json",null,function(result){
var menuList =result.data;
<% HttpSession s= request.getSession();
    String resourceStr = (String)s.getAttribute("resourceStr");
%>

    zMenu.init("menu",menuList,{id:"id",url:"url",pid:"pid",name:"name"});
});
var resources="<%=resourceStr%>";




includeCSS([
"/static/css/grid.css",
"/static/css/widget.css",
"/static/css/zTreeStyle.css",
"/static/css/font-awesome.css",
]);



includeJS([
"/static/js/DateUtils.js",
"/static/js/grid.js",
  "/static/js/jquery.form.js",
  "/static/js/grid.locale-en.js",
  "/static/js/My97DatePicker/WdatePicker.js",
  "/static/js/jquery.validate.js",
  "/static/js/additional-methods.js",
"/static/js/jquery.ztree.core-3.5.js",
"/static/js/jquery.ztree.excheck-3.5.js",
          ]);


</script>



</html>