<div id="${table.name}MapperList" class="rgt_body ">
    <div class="body_title">| ${table.remark}</div>
        <div class="body_top" >
            <form class="form-inline app-search">
${parentsearchhtml}
                <button type="button" class="btn btn-default searchBtn">查询</button>
            </form>
        <div >
             <button class="btn saveBtn" >保存</button>
        </div>
    </div>
     <div class="col-sm-6 mapper-grid-parent-wrap" >
    <table id="${table.name}MapperGrid" class="grid"></table>
    <div id="${table.name}MapperGrid-Pager" class="pager"></div>
    </div>
     <div class="col-sm-6 mapper-grid-child-wrap">
            <ul id="treeDemo" class="ztree"></ul>
        </div>
</div>
<script>
var ${abc}List={
    modal:false,
    mygrid:null,
    treeObj:null,
    root:$("#${table.name}MapperList"),
    init:function(){
        this.mygrid = $(this.root).find(".grid").jqGrid(this.gridParam);
        this.addEventListener();
        this.initTree();
    },
    initTree:function(){
        var that =this;
        Ajax.getJSON(PATH+"/${table.mapper.child?uncap_first}/listAll.json",null,function(result){
            if(result.r==AJAX_SUCC){
                if(result.data){
                     that.treeObj=$.fn.zTree.init($(that.root).find("#treeDemo"), setting, result.data);
                }
            }else{
                dialog.error("获取信息失败"+data.msg);
            }
        });
    },
    addEventListener:function(){
           $(this.root).find(".saveBtn").click(this.saveInfo.Apply(this));
           $(this.root).find(".searchBtn").click(this.searchInfo.Apply(this));
    },
    gridParam:{
                  datatype: "json",
                          viewrecords: true, sortorder: "desc", caption:"",
                          rowNum:10,
                          rowList:[10,20,30],
                  multiselect : true,
                  url : PATH+'/${table.mapper.parent?uncap_first}/list.json',
                  grid:"#${table.name}MapperGrid",
                  pager:"#${table.name}MapperGrid-Pager",
                  jsonReader:jsonReader,
                   autowidth:true,
                  colNames : [
                   <#list parentTable.cols as col><#if col_index!=0>,</#if>"${col.remark}"</#list> , '操作' ],
                  colModel : [
                           <#list parentTable.cols as col>
                          {
                              name : '${col.name}',
                              width : 80,
                              <#if col.showValue?exists>
                              formatter : function(value, grid, rows) {
                                  var map ={<#list col.showValue?keys as key>'${key}':'${col.showValue[key]}',</#list>};
                                  return map[value];
                              }
                              </#if>
                              <#if col.type=="timestamp">
                               formatter : function(value, grid, rows) {
                                  return new Date(value).format("yyyy-MM-dd");
                              }
                              </#if>
                          } <#if col_index<parentTable.cols?size-1>,</#if>
                           </#list>
                          ,
                          {
                              name : 'operation',
                              width : 150,
                              formatter : function(value, grid, rows) {
                                  return getViewHtml(rows.${parentTable.pk.name},"${abc}List")+getEditHtml(rows.${parentTable.pk.name},"${abc}List")+getDelHtml(rows.${parentTable.pk.name},"${abc}List");
                              }
                          }
                    ],

                    onSelectRow: function(id){ //alert("单击选中"+id);
                    var that =this;
                  //  var treeObj = $.fn.zTree.getZTreeObj("tree");
                     var celldata =$(${abc}List.mygrid).jqGrid('getRowData',id)["${parentTable.pk.name}"];
                     //清空原来的选项
                    ${abc}List.treeObj.checkAllNodes(false);
                      Ajax.getJSON(PATH+"/${table.name?uncap_first}/listAll.json",{"${table.mapper.parentid}":celldata},function(result){
                           if(result.data){
                                          for(var i=0;i<result.data.length;i++){
                                              var node = ${abc}List.treeObj.getNodeByParam("id",result.data[i].${table.mapper.childid});
                                              if(node!=null)
                                              ${abc}List.treeObj.checkNode(node,true,true);
                                          }
                                      }
                      })
                    },
    },
    saveInfo:function(){
        //首先查出父节点勾选信息
        var selarrrow =$(this.mygrid).jqGrid("getGridParam","selarrrow");
        if(selarrrow==null || selarrrow.length==0){
            selarrrow .push(this.mygrid.jqGrid('getGridParam','selrow'));
        }
        console.log(selarrrow);
        //再查出子节点勾选信息
        var childids=[];
        var treeObj= this.treeObj;
                         var checkedNodes= this.treeObj.getCheckedNodes(true);
                         for(var i=0;i<checkedNodes.length;i++){
                             var obj=checkedNodes[i];
                             if(!checkedNodes[i].isParent){
                                 childids.push(checkedNodes[i].id);
                             }
                         }
        console.log(selarrrow.join(","));
        var parentIdArray=new Array();
        for(var i=0;i<selarrrow.length;i++){
            parentIdArray.push( $(this.mygrid).jqGrid("getRowData",selarrrow[i])["id"]);
        }

        console.log(childids.join(","));
         console.log(parentIdArray.join(","));
        //进行保存

        Ajax.post(PATH+"/${abc}/msave.json",{"${table.mapper.parentid}s":parentIdArray.join(","),"${table.mapper.childid}s":childids.join(",")},function(result){
            if(result.r==AJAX_SUCC){
                dialog.alert("关联成功");
            }else{
                dialog.alert(result.msg);
            }
        })
    },
    searchInfo:function (){
        var jso = changeForm2Jso(".app-search");
        $(this.mygrid).jqGrid("setGridParam", { search: true ,"postData":jso}).trigger("reloadGrid", [{ page: 1}]);  //重载JQGrid
    },
    search:function (){
        var jso= changeForm2Jso(".app-search");
        console.log(jso);
        this.mygrid.search(jso);
    },


};
${abc}List.init()
</script>

