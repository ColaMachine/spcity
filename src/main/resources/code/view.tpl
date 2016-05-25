 <div id="${Abc}View" class="modal-content">
    <form id="editForm" class="form-horizontal" method="post" action="/${abc}/save.json" enctype="multipart/form-data">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title">${table.remark}查看</h4>
        </div>
        <div class="modal-body">
            ${viewhtml}
        </div>
        <div class="modal-footer">
            <button type="button"   class="btn btn-default cancelBtn">取消</button>
        </div>
    </form>
</div><!-- /.modal-content -->

<!--<div class="body_title">| ${table.remark}编辑</div>
<form id="editForm" class="form-horizontal" method="post" action="/${abc}/save.json" enctype="multipart/form-data">
 ${viewhtml}
   <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
       <button type="button"   class="btn btn-default cancelBtn">返回</button>
    </div>
  </div>
</form>
</div>
</div>-->
<script type="text/javascript">
var ${abc}View={
    modal:false,
    root:$("#${table.name}View"),
    init:function(){
    var that = this;
        this.addEventListener();
        //获取传入参数
            if(!StringUtil.isBlank(getParam("id"))){
                Ajax.getJSON("${abc}/view.json?id="+getParam("id"),null,function(data){
                    if(data.r==AJAX_SUCC){
                        fillJso2FormSpan("#${Abc}View",data.data.bean);
                    }else{
                        dialog.error("获取信息失败"+data.msg,function(index){
                            that.cancel();
                            dialog.close(index);
                        });
                    }

                });

            }
    },
    cancel:function(){
        if(${abc}View.modal){
            dialog.closeWindow("${abc}View");
        }else{
            goPage("${abc}/list.htm");
        }
    },
    addEventListener:function(){
        $(this.root).find(".cancelBtn").click(${abc}View.cancel);
    }
}
$(document).ready(function() {
    ${abc}View.init();
});
   

</script>