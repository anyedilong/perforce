<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/views/resource.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">

    var orgTree;
    var gridTable;
    
    $(function(){
    	//初始化 列表
        initGridTable();
    });
    
    //初始化 列表
   	var gridTableUrl = $.util.basePath + '/r/role/getList';
    function initGridTable(){
    	gridTable = $("#gridTable").bootstrapTable({
            url:  gridTableUrl,
            method: 'post',                      //请求方式（*）
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            queryParams:{name:$("#name").val()},
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            clickToSelect: false,                //是否启用点击选中行
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            columns: 
            [
                {field: '', checkbox:true,formatter:function(event,value,row,index){
                }},          
                {field: 'name', title: '角色名称'},
                {field: 'remarks', title: '备注'},          
                {field: 'status', title: '状态',formatter:function(event,value,row,index){
                    var status = value.status;
                    if(status=='1') return '正常';
                    else  if(status=='2')  return "冻结"
                    else  if(status=='3')  return "删除";
                    
                }}, 
                {field: '', title: '操作',formatter:function(event,value,row,index){
                    var html = '<button handle-type="edit_row" type="button" class="btn btn-outline btn-primary button-row button-handle-row" onclick="openEditRowDialog(\''+value.id+'\',this);" ><i class="fa"></i></button>';
                    var status = value.status;
                    if(status=='1'){
                    	html += '<button handle-type="authorize_row" type="button" class="btn btn-outline btn-primary button-row button-handle-row" onclick="openAuthorizeDialog(\''+value.id+'\',this);" ><i class="fa"></i></button>';
                    }
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
    //刷新列表
    function refreshTable() {
        var paramJson = $("#queryForm").serializeJSON();
        $("#gridTable").bootstrapTable('refresh', {
            url : gridTableUrl,
            query : paramJson
        });
    }
    
    //打开行编辑界面
    function openEditRowDialog(id,btnEle){
    	var url = "/r/role/forwordEdit?id="+id
    	openDialogByHandle(btnEle,url);
    }
    
    //打开添加界面
    function openEditDialog(btnEle){
    	openEditRowDialog(null,btnEle);
    }
    
    //删除数据
    function deleteRows(eve){
        var delUrl = $.util.basePath + '/r/role/delete';
        del(delUrl,"gridTable",null,function(){
        	refreshTable();
        });
    }
    
    //打开角色授权界面
    function openAuthorizeDialog(id,btnEle){
    	var url = "/r/role/forwordAuthorize?id="+id
    	openDialogByHandle(btnEle,url);
    }
    
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm">
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
			                                        <label class="control-label">角色名称</label>
			                                        <input type="text" name="name"  class="form-control">
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