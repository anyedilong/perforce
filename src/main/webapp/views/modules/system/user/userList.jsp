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
    	//初始化机构树
    	initOrgTree();
    	//初始化 列表
        initGridTable();
    });
    
    //初始化机构树
    function initOrgTree(){
        var initOrgUrl =  $.util.basePath + '/r/org/getUserOrgTree';
        
        $.util.ajax({
            url:initOrgUrl,
            data:{'type':'2'},
            async : false, // 是否同步
            success:function(data){
                if($.util.isNull(data)){
                    data = [];
                }
                var setting = {
                    data: {
                        simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "parentId"
                        },
                        key:{
                            url: "xUrl"
                        }
                    },
                    callback : {
                        onClick:function(event, treeId, treeNode){
                            $("#queryForm input[name=orgId]").val(treeNode.id);
                            query();
                        }
                    }
                };

                orgTree = $.fn.zTree.init($("#orgTree"), setting, data);
                orgTree.expandAll(true); 
            }
        });
    }
    
    //初始化机构树
   	var gridTableUrl = $.util.basePath + '/r/user/getList';
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
                {field: 'username', title: '登录名'},          
                {field: 'name', title: '姓名'},
                {field: 'roleNames', title: '所属角色'},
                {field: 'status', title: '状态',formatter:function(event,value,row,index){
                    var status = value.status;
                    if(status=='1') return '正常';
                    else  if(status=='2')  return "冻结"
                    else  if(status=='3')  return "注销";
                    
                }}, 
                {field: '', title: '操作',formatter:function(event,value,row,index){
                    var html = '<button handle-type="edit_row" type="button" class="btn btn-outline btn-primary button-row button-handle-row" onclick="openEditRowDialog(\''+value.id+'\',this);" ><i class="fa"></i></button>';
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
    	var orgId = $("#queryForm input[name=orgId]").val();
    	var url = "/r/user/forwordEdit?orgId="+orgId+"&id="+id
    	openDialogByHandle(btnEle,url);
    }
    
    //打开添加界面
    function openEditDialog(btnEle){
    	openEditRowDialog(null,btnEle);
    }
    
    //删除数据
    function deleteRows(eve){
        var delUrl = $.util.basePath + '/r/user/delete';
        del(delUrl,"gridTable",null,function(){
        	refreshTable();
        });
    }
    
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm-3" >
				<div class="ibox box-div">
					<div class="ibox-content box-content-div">
						<ul id="orgTree" class="ztree"></ul>
					</div>
				</div>
			</div>
			<div class="col-sm-9">
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
			                                        <label class="control-label">登录名</label>
			                                        <input id='qy_orgId' type="hidden" name="orgId"  class="form-control">
			                                        <input type="text" name="username"  class="form-control">
			                                    </div>
			                                    <div class="form-group">
			                                        <label class="control-label">姓名</label>
			                                        <input type="text" name="name"  class="form-control">
			                                    </div>
			                                    <div class="form-group">
			                                        <label>
				                                     <input type="checkbox" name="includeSubFlg" checked="checked"  value="1" class="i-checks">
				                                                                 包含子集</label>
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