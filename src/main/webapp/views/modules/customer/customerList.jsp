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
    	$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})
    	//初始化 列表
        initGridTable();
    });
    
    //打开详情界面
    function openShowDialog(id){
        var url = "/r/customer/forwordShow?id="+id;
        var width = 780;
        var height = 640;
        var title = "客户详情";
        
        openDialog(null, url, width, height, title);
    }
  //打开保存界面
	function openSaveDialog(id,type,eve) {
			var url = "/r/customer/forwordEdit?id="+id
			var width = "780";
			var height = "490";
			var title = "编辑客户信息";
			openDialog(null, url, width, height, title);
  	}
    //初始化列表
   	var gridTableUrl = $.util.basePath + '/r/customer/getList';
    function initGridTable(){
    	gridTable = $("#gridTable").bootstrapTable({
            url:  gridTableUrl,
            method: 'post',                      //请求方式（*）
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            queryParams:{'includeSubFlg':'1'},
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            clickToSelect: false,                //是否启用点击选中行
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            columns: 
            [
                {field: '', checkbox:true,formatter:function(event,value,row,index){
                }},          
                {field: 'name', title: '客户姓名'},          
                {field: 'phoneNum', title: '客户手机号'},
                {field: 'source', title: '数据来源',formatter:function(event,value,row,index){
                    var status = value.source;
                    if(status=='1') return '代理商客户';
                    else  if(status=='2')  return "直接客户"
                }},
                {field: 'status', title: '状态',formatter:function(event,value,row,index){
                    var status = value.status;
                    if(status=='1') return '正常';
                    else  if(status=='2')  return "冻结"
                    else  if(status=='3')  return "注销";
                    
                }}, 
                {field: '', title: '操作',formatter:function(event,value,row,index){
                	var html = '<button type="button" class="btn btn-outline btn-primary button-row button-handle-row" onclick="openShowDialog(\''+value.id+'\');" ><i class="fa">查看</i></button>';
                	html+='<button type="button" class="btn btn-outline btn-primary button-row button-handle-row" onclick="openSaveDialog(\''+value.id+'\');" ><i class="fa">编辑</i></button>';
                	return html;
                }},
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
        if($.util.isNull(paramJson.includeSubFlg)){
            paramJson.includeSubFlg='';
        }
        $("#gridTable").bootstrapTable('refresh', {
            url : gridTableUrl,
            query : paramJson
        });
    }
    
    //打开行编辑界面
    function openEditRowDialog(id,btnEle){
    	/* var orgId = $("#queryForm input[name=orgId]").val(); */
    	var url = "/r/customer/forwordEdit?id="+id
    	openDialogByHandle(btnEle,url);
    }
    
    //打开添加界面
    function openEditDialog(btnEle){
    	openEditRowDialog(null,btnEle);
    }
    
    //删除数据
    function deleteRows(eve){
        var delUrl = $.util.basePath + '/r/customer/delete';
        del(delUrl,"gridTable",null,function(){
        	refreshTable();
        });
    }
    
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
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
			                                        <label class="control-label">客户姓名</label>
			                                        <input type="text" name="name"  class="form-control">
			                                    </div>
			                                    <div class="form-group">
			                                        <label class="control-label">客户手机号</label>
			                                        <input type="text" name="phoneNum"  class="form-control">
			                                    </div>
			                                    <div class="form-group">
			                                        <label class="control-label">数据来源</label>
                                						<select id="source" name="source" style="width:350px;" class="chosen-select-deselect" tabindex="12">
                                							<option id="op" value=''>全部</option>
                                    						<option id="op" value='1'>代理商客户</option> 
    														<option id="ep" value='2'>直接客户</option>
                                    					</select>
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
						          
								<!--<button class="btn btn-info button-handle" type="button"><i class="fa fa-paste"></i> 编辑</button>
						          <button class="btn btn-danger button-handle" type="button"><i class="fa fa-trash"></i> 删除</button>
						          <button class="btn btn-info button-handle" type="button"><i class="fa fa-plus"></i> 新增</button>
						          <button class="btn btn-info button-handle" type="button"><i class="fa fa-file-excel-o"></i> 导出</button>
						          <button class="btn btn-info button-handle" type="button"><i class="fa fa-print"></i> 打印</button> -->
						          
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