<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/views/resource.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">
    
    var menuTree;
    
    var menuTable;

    $(function(){
    	initMenuTree();
    	initMenuTable();
    });
    
    function initMenuTree(){
    	var intiMenuUrl = $.util.basePath + '/r/menu/getMenuTree';
    	$.util.ajax({
    		url:intiMenuUrl,
    		async : false, // 是否同步
    		success:function(data){
    			if($.util.isNull(data)){
    				data = [];
    			}
    			data.push({id:"0",parentId:"-1",name:"菜单",open:true});
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
   		            		$("#queryForm input[name=parentId]").val(treeNode.id);
   		            		query();
   		            	}
   		            }
   		        };

    			menuTree = $.fn.zTree.init($("#menuTree"), setting, data);
    			menuTree.expandAll(true); 
    		}
    	});
    }
    
    
    
   	var menuTableUrl = $.util.basePath + '/r/menu/getMenuList';
    function initMenuTable(){
    	
       	menuTable = $("#menuTable").bootstrapTable({
       		url:  menuTableUrl,
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
                {field: 'name', title: '菜单名称'},          
                {field: 'type', title: '菜单类型',formatter:function(event,value,row,index){
                	var type = value.type;
                	if(type=='1') return '菜单';
                	else return "操作";
                	
                }},       
                {field: 'handleType', title: '操作类型'},          
                {field: 'width', title: '宽度'},          
                {field: 'height', title: '高度'},          
                {field: '', title: '操作',formatter:function(event,value,row,index){
                	var html = '<button handle-type="edit_row" type="button" class="btn btn-outline btn-primary button-row button-handle-row" onclick="openSaveDialog(\''+value.id+'\',\''+value.type+'\',this);" ><i class="fa"></i></button>';
                	return html;
                }}
            ],
            onLoadSuccess:function(){
		       	initRowHandle("menuTable");
            }
       	});
    }

    //
    function openMenuDialog(eve){
    	var parentId = $("#queryForm input[name=parentId]").val();
    	var url = "/r/menu/editMenu?parentId="+parentId;
    	openDialogByHandle(eve,url);
    }
    function openHandleDialog(eve){
    	var parentId = $("#queryForm input[name=parentId]").val();
    	var url = "/r/menu/editHandle?parentId="+parentId;
        openDialogByHandle(eve,url);
    }
    
    //打开保存界面
	function openSaveDialog(id,type,eve) {
		var dialogId = getDialogId(eve);
		var parentId = $("#queryForm input[name=parentId]").val();
		if (type == '1') {
			//菜单界面
			var url = "/r/menu/editMenu?parentId="+parentId+"&id="+id
			var width = "780";
			var height = "490";
			var title = "保存菜单";
			openDialog(dialogId, url, width, height, title);
		}else{
			//操作界面
			var url = "/r/menu/editHandle?parentId="+parentId+"&id="+id
            var width = "780";
            var height = "640";
            var title = "保存操作";
            openDialog(dialogId, url, width, height, title);
		}

	}

	//刷新列表和tree
	function refreshTableAndTree() {
		initMenuTree();
		query();
	}

	function query() {
		var paramJson = $("#queryForm").serializeJSON();
		$("#menuTable").bootstrapTable('refresh', {
			url : menuTableUrl,
			query : paramJson
		});
	}
	
	//删除数据
	function deleteRows(eve){
		var delUrl = $.util.basePath + '/r/menu/delete';
		del(delUrl,"menuTable",null,function(){
			refreshTableAndTree();
		});
		//alert(JSON.stringify(selectionRows))
	}
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm-3" >
				<div class="ibox box-div">
					<div class="ibox-content box-content-div">
						<ul id="menuTree" class="ztree"></ul>
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
			                                        <label class="control-label">菜单名称</label>
			                                        <input id='qy_parentId' type="hidden" name="parentId"  class="form-control">
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
						          <button handle-type="add_menu" class="btn btn-info button-handle " type="button" onclick="openMenuDialog(this)"><i class="fa "></i> </button>
						          <button handle-type="add_handle" class="btn btn-info button-handle " type="button" onclick="openHandleDialog(this)"><i class="fa "></i> </button>
						          <button handle-type="delete" class="btn btn-danger button-handle" type="button" onclick="deleteRows(this)" ><i class="fa"></i></button>
						          <!-- 
						          <button class="btn btn-info button-handle" type="button"><i class="fa fa-paste"></i> 编辑</button>
						          <button class="btn btn-danger button-handle" type="button"><i class="fa fa-trash"></i> 删除</button>
						          <button class="btn btn-info button-handle" type="button"><i class="fa fa-plus"></i> 新增</button>
						          <button class="btn btn-info button-handle" type="button"><i class="fa fa-file-excel-o"></i> 导出</button>
						          <button class="btn btn-info button-handle" type="button"><i class="fa fa-print"></i> 打印</button>
						           -->
						    </div>
                               <table id="menuTable" ></table>
                        </div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>