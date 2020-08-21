<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/views/resource.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">

    var orgTree;
    var orgDownTree;
    var gridTable;
    
    $(function(){
    	//初始化机构树
    	initOrgTree();
    	//初始化 列表
        initGridTable();
    });
    
    //初始化机构树
    function initOrgTree(){
    	var initOrgUrl =  $.util.basePath + '/r/org/getOrgTree';
    	
    	$.util.ajax({
            url:initOrgUrl,
            async : false, // 是否同步
            success:function(data){
            	if($.util.isNull(data)){
                    data = [];
                }
            	data.push({id:"0",parentId:"-1",name:"组织机构",open:true});
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
                            
                            var orgType = treeNode.orgType;
                            if(orgType == '2'){
                                $("#handle-div button[handle-type=add-dept]").removeClass("hd");
                            	$("#handle-div button[handle-type=add-job]").removeClass("hd");
                            	$("#handle-div button[handle-type=add-org]").addClass("hd");
                            }else if(orgType == '3'){
                            	$("#handle-div button[handle-type=add-org]").addClass("hd");
                            	$("#handle-div button[handle-type=add-dept]").addClass("hd");
                            	$("#handle-div button[handle-type=add-job]").addClass("hd");
                            }else{
                            	$("#handle-div button[handle-type=add-org]").removeClass("hd");
                            	$("#handle-div button[handle-type=add-dept]").removeClass("hd");
                            	$("#handle-div button[handle-type=add-job]").removeClass("hd");
                            }
                            query();
                        }
                    }
                };

                orgTree = $.fn.zTree.init($("#orgTree"), setting, data);
                orgTree.expandAll(true); 
            }
        });
    }
    
    //初始化 列表
   	var gridTableUrl = $.util.basePath + '/r/org/getList';
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
                {field: 'name', title: '名称'},          
                {field: 'orgCode', title: '编码'},
                {field: 'sortName', title: '简称'},
                {field: 'orgLevel', title: '等级'},
                {field: 'orgType', title: '类型',formatter:function(event,value,row,index){
                    var orgType = value.orgType;
                    if(orgType=='1') return '机构';
                    else  if(orgType=='2')  return "部门"
                    else  if(orgType=='3')  return "岗位";
                    
                }}, 
                {field: 'status', title: '状态',formatter:function(event,value,row,index){
                    var status = value.status;
                    if(status=='1') return '正常';
                    else  if(status=='2')  return "冻结"
                    else  if(status=='3')  return "注销";
                    
                }}, 
                {field: 'orderNum', title: '排序号'},
                {field: '', title: '操作',formatter:function(event,value,row,index){
                    var html = '<button handle-type="edit_row" type="button" class="btn btn-outline btn-primary button-row button-handle-row" onclick="openEditRowDialog(\''+value.id+'\',\''+value.orgType+'\',this);" ><i class="fa"></i></button>';
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
    //刷新机构树
    //刷新列表和tree
    function refreshTableAndTree() {
    	initOrgTree();
        refreshTable();
    }
    
    //打开行编辑界面
    function openEditRowDialog(id,type,btnEle){
    	//类型 1机构 2 部门 3 岗位
    	var dialogId = getDialogId(btnEle);
    	var url ;
    	var width = 780;
    	var height =   640;
    	var title = "编辑";
    	if (type == '1') {
    		url = "/r/org/forwordOrgEdit?id="+id;
    	}else if (type == '2') {
    		url = "/r/org/forwordDeptEdit?id="+id;
    	}else if (type == '3') {
    		url = "/r/org/forwordJobEdit?id="+id;
    	}
    	
    	openDialog(dialogId, url, width, height, title);
    }

    
    //打开添加界面
    function openAddOrgDialog(btnEle){
    	var orgId = $("#queryForm input[name=orgId]").val();
        var url = "/r/org/forwordOrgEdit?orgId="+orgId;
        openDialogByHandle(btnEle,url);
    }
    function openAddDeptDialog(btnEle){
    	var orgId = $("#queryForm input[name=orgId]").val();
        var url = "/r/org/forwordDeptEdit?orgId="+orgId;
        openDialogByHandle(btnEle,url);
    }
    function openAddJobDialog(btnEle){
    	var orgId = $("#queryForm input[name=orgId]").val();
        var url = "/r/org/forwordJobEdit?orgId="+orgId;
        openDialogByHandle(btnEle,url);
    }
    
    //删除数据
    function deleteRows(eve){
        var delUrl = $.util.basePath + '/r/org/delete';
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
			                                        <input id='qy_orgId' type="hidden" name="orgId"  class="form-control">
			                                        <label class="control-label">机构名称</label>
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
						          <button handle-type="add-org" class="btn btn-info button-handle " type="button" onclick="openAddOrgDialog(this)"><i class="fa "></i> </button>
						          <button handle-type="add-dept" class="btn btn-info button-handle " type="button" onclick="openAddDeptDialog(this)"><i class="fa "></i> </button>
						          <!-- <button handle-type="add-job" class="btn btn-info button-handle " type="button" onclick="openAddJobDialog(this)"><i class="fa "></i> </button> -->
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