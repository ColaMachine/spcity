 <div class="modal-content">
    <form id="editForm" class="form-horizontal" method="post" action="/${abc}/save.json" enctype="multipart/form-data">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title">${table.remark}编辑</h4>
        </div>
        <div class="modal-body">
            ${edithtml}
            <#if table.mapper??>
            <div class="form-group">
        <label for="createtime" class="col-sm-2 control-label">${child.remark}:</label>
        <div class="col-sm-10 z-col-table">
            <ul id="treeDemo" class="ztree"></ul>
        </div>
       </div>
       </#if>
        </div>
        <div class="modal-footer">
            <button type="button" onclick="save()" class="btn btn-primary">保存</button>
            <button type="button" onclick="cancel()"  class="btn btn-default">取消</button>
        </div>
    </form>
</div><!-- /.modal-content -->
        
<script type="text/javascript">

$(document).ready(function() {
    $("#editForm").validate({
        rules: {
            ${jsrules}
        },
        messages:{
            ${jsmsg}
        }
    });
    //获取传入参数
 
        Ajax.getJSON("${abc}/view.json?id="+getParam("id"),null,function(data){
            if(data.r==AJAX_SUCC){
                if(data.data.bean){
                    fillJso2Form("#editForm",data.data.bean);
                }
                <#if table.mapper??>
                if(data.data.childs){
                    var treeObj=$.fn.zTree.init($("#treeDemo"), setting, data.data.childs);
                    if(data.data.childMaps!=null){
                        for(var i=0;i<data.data.childMaps.length;i++){
                            var node = treeObj.getNodeByParam("id",data.data.childMaps[i].${table.mapper.childid});
                            treeObj.checkNode(node,true,true);
                        }
                    }
                }
                </#if>
            }else{
                zerror("获取信息失败"+data.msg,"错误",function(){
                    goPage("${abc}/list.htm");
                });
            }
            
        });
        
    
});
 <#if table.mapper??>
var setting = {
           check: {
               enable: true
           },
           data: {
               simpleData: {
                   enable: true
               }
           }
       };
</#if>
    function save(){
           if (!$("#editForm").valid())
                    return;
                     var jso = changeForm2Jso("#editForm");
                    <#if table.mapper??>
                      var childids=[];
                     var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                     var checkedNodes= treeObj.getCheckedNodes(true);
                     for(var i=0;i<checkedNodes.length;i++){
                         var obj=checkedNodes[i];
                         if(!checkedNodes[i].isParent){
                             
                             childids.push(checkedNodes[i].id);
                         }
                     }
                     if(childids.length==0){
                         //$("#childs").parent().parent().find(".error").text("请选择相应权限");
                          //flag=false;
                     }
                     jso.childids= childids.join(",");//alert(jso.childids);return;
                     </#if>
                     showWait();
                     Ajax.post(PATH+$("#editForm").attr("action"),jso,function(data){
                     hideWait();
                     if(data.r==0){
                        zalert(data.msg||"保存成功","提示",function(){
                            //goPage("${abc}/list.htm");
                            cancel();
                            $("#grid").jqGrid("reloadGrid");
                        });
                     }else{
                        zerror(data.msg, "失败");
                     }
                     },'json');
    }
    function cancel(){
        if(newWindow){
            $("#mymodal").modal("toggle");
        }else{
            goPage("${abc}/list.htm");
        }
    }
</script>