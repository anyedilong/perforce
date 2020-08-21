<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/views/resource.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">

    var gridTable;
    var proTypeTree;
    
    $(function(){
    	//初始化产品分类树
    	initProTypeTree();;
    	//初始化列表
    	initGridTable();
    	
    });
    
    //初始化产品分类树
    function initProTypeTree(){
        var initProTypeUrl =  $.util.basePath + '/r/productType/getTree';
        
        $.util.ajax({
            url:initProTypeUrl ,
            async : false, // 是否同步
            success:function(data){
                if($.util.isNull(data)){
                    data = [];
                }
                data.push({id:"0",parentId:"-1",typeName:"产品分类",open:true});
                var setting = {
                    data: {
                        simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "parentId"
                        },
                        key:{
                            //url: "xUrl"
                        	name: "typeName"
                        }
                    },
                    callback : {
                        onClick:function(event, treeId, treeNode){
                            $("#queryForm input[name=parentId]").val(treeNode.id);
                            query();
                        }
                    }
                };

                proTypeTree = $.fn.zTree.init($("#proTypeTree"), setting, data);
                proTypeTree.expandAll(true); 
            }
        });
    }
    
    //初始化列表
    var gridTableUrl = $.util.basePath + '/r/productType/getList';
    function initGridTable(){
        gridTable = $("#gridTable").bootstrapTable({
            url:  gridTableUrl,
            method: 'post',                      //请求方式（*）
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            queryParams:{},
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            clickToSelect: false,                //是否启用点击选中行
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            columns: 
            [
                {field: '', checkbox:true,formatter:function(event,value,row,index){
                }},          
                {field: 'typeName', title: '产品分类名称'},          
                {field: 'code', title: '产品分类code'},
                {field: 'status', title: '状态',formatter:function(event,value,row,index){
                    var status = value.status;
                    if(status=='1') return '正常';
                    else  if(status=='2')  return "冻结";
                    
                }}, 
                {field: 'orderNum', title: '排序号'},
                {field: 'remarks', title: '备注'},
                {field: '', title: '操作',formatter:function(event,value,row,index){
                    var html = '<button handle-type="edit_row" type="button" class="btn btn-outline btn-primary button-row button-handle-row" onclick="openEditDialog(this,\''+value.id+'\');" ><i class="fa"></i></button>';
                    
                    return html;
                }}
            ],
            onLoadSuccess:function(){
                initRowHandle("gridTable");
            }
        });
    }
    
    function query(){
        refreshTable();
    }
    
    //刷新列表和tree
    function refreshTableAndTree() {
    	initProTypeTree();
        refreshTable();
    }
    
    //刷新列表
    function refreshTable() {
        var paramJson = $("#queryForm").serializeJSON();
        if($.util.isNull(paramJson.includeSubFlg)){
            paramJson.includeSubFlg='';
        }
        $("#gridTable").bootstrapTable('refresh', {
            url : gridTableUrl,
            query : paramJson
        });
    }
    
    //打开添加界面
    function openEditDialog(btnEle,id){
    	var parentId = $("#queryForm input[name=parentId]").val();
    	var url = "/r/productType/forwordEdit?parentId="+parentId+"&id="+id;
        openDialogByHandle(btnEle,url);
    }
    
    //删除数据
    function deleteRows(eve){
        var delUrl = $.util.basePath + '/r/productType/delete';
        del(delUrl,"gridTable",null,function(){
        	refreshTableAndTree();
        });
    }
    
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
		  <div class="col-sm-4" >
                <div class="ibox box-div">
                    <div class="ibox-content box-content-div">
                        <ul id="proTypeTree" class="ztree"></ul>
                    </div>
                </div>
            </div>
			<div class="col-sm-8">
				<div class="ibox box-div">
                    <div class="ibox-content">
						<div class="row search-div">
	                        <table style="width:100%;">
	                            <tr>
									<!-- 查询条件 -->
	                                <td>
	                                   <div class="col-sm search-where-div" >
			                                <form id="queryForm" role="form" class="form-inline">
			                                    <div class="form-group">
			                                        <label class="control-label">产品分类名称</label>
			                                        <input id='qy_parentId' type="hidden" name="parentId"  class="form-control">
			                                        <input type="text" name="typeName" class="form-control">
			                                    </div>
			                                    <div class="form-group">
			                                        <label class="control-label">产品分类code</label>
			                                        <input type="text" name="code"  class="form-control">
			                                    </div>
			                                </form>
			                            </div>
	                                </td>
	                                <td style="width:90px;">
	                                    <!-- 查询按钮 -->
			                            <div class="col-sm search-button-div">
			                               <button class="btn btn-primary btn-lg btn-search" onclick="query();" type="button"><i class="fa fa-search"></i>&nbsp;查询</button>
			                            </div>
	                                </td>
	                            </tr>
	                        </table>
                        </div>
						
						<!-- 列表 -->
						<div class="row-fluid grid-div">
						    <!-- 操作按钮 -->
						    <div class="columns  handle-button-div" id="handle-div">
                                  <button handle-type="add" class="btn btn-info button-handle " type="button" onclick="openEditDialog(this)"><i class="fa "></i> </button>
                                  <button handle-type="delete" class="btn btn-danger button-handle" type="button" onclick="deleteRows(this)" ><i class="fa"></i></button>
						    
						          <!-- 
						          <button class="btn btn-info button-handle" type="button"><i class="fa fa-paste"></i> 编辑</button>
						          <button class="btn btn-danger button-handle" type="button"><i class="fa fa-trash"></i> 删除</button>
						          <button class="btn btn-info button-handle" type="button"><i class="fa fa-plus"></i> 新增</button>
						          <button class="btn btn-info button-handle" type="button"><i class="fa fa-file-excel-o"></i> 导出</button>
						          <button class="btn btn-info button-handle" type="button"><i class="fa fa-print"></i> 打印</button>
						           -->
						    </div>
                               <table id="gridTable" ></table>
                        </div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>