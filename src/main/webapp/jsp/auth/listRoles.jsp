<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
   <div class="body_top" >
        <div  style="padding-left:3%">
            <button class="btn" onclick="goEdit()">新增角色</button>    
        </div>

       
    </div>

    <div id="grid" class="grid"></div>
    <div id="grid-pager" class="pager"></div>
</body>

<script>
var gridParam = {
    
    page:{
        curpage : 1,
        pagesize : 10
    },
    multiselect : false,
    url : '/auth/listRole.json',
    grid_selector : "#grid",
    pager_selector : "#grid-pager",
    searchParams : {
        name : '',
        curpage : 1,
        pagesize : 2
    },
    colNames : [ 'ID', '角色名称', '角色描述', '操作' ],
    colModel : [
            {
                name : 'id',
                width : 80
            },
            {
                name : 'rolename',
                width : 130
            },
            {
                name : 'remarks',
                width : 90,
            },
            
            {
                name : 'id',
                width : 150,
                formatter : function(value, grid, rows) {
                        return "<a class='option' name='edit' onclick='viewInfo(\""+value+"\")' >查询</a>"
                                + "<a class='option'  name='issue' onclick='editInfo(\""+value+"\")' >修改</a>"
                                + "<a class='option'  name='market' onclick=\"delInfo('"+value+"','"+rows["rolename"]+"')\" >删除</a>";
                    
                }
            } ]
            
            /*
             * ondblClickRow: function(id){
             * 
             * alert("双击选中"+id);
             *  },
             */
           
         /*
             * onSelectRow: function(id){ alert("单击选中"+id); },
             */
};

this.mygrid = $("#grid").jqGrid(this.gridParam); 
 function goEdit(){
     window.location='/role/edit';
 }
 function editInfo(id){
     window.location="/role/edit/"+id;
 }
 function delInfo(id,roleName){
     //弹窗
     zconfirm("确定删除角色名称:"+roleName,"删除",function(){
        $.post("/role/del/"+id,function(result){
            result=ajaxResultHandler(result);
            if(result.r==1){
                zalert("删除成功，角色名："+roleName,"删除",function(){
                    $("#grid").jqGrid("reloadGrid");
                });
            }else {
                zerror(result.msg,"提醒",function(){
                });
            }
        });
     });
 }
 function viewInfo(id){
     window.location="/role/viewPage/"+id;
 }
</script>
</html>