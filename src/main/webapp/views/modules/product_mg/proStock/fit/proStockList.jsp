<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/views/resource.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">

var gridTable;
	refreshTable();
	//初始化数据
	$(function(){
		
		//加载grid
		initGridTable();
		//初始化数据校验
		//initValidate();
		//初始化产品分类树
		initProTypeTree();
		setTimeout('$("#downProTypeTree").downZtree()', 100);
		//初始化下拉框
		initProSlt();
	})
	
	//初始化产品状态下拉框
    function initProHandleStatus(){
		
    	getDictList("dictProductStatus",function(data){
    		var optionHtml = [];
    		optionHtml.push(' <option value="">全部</option> ');
    		//下拉框数据绑定
    		if(!$.util.isNull(data)){
    			for(var i = 0; i < data.length;i++){
    				 var item = data[i];
    				 optionHtml.push(' <option value="'+item.value+'">'+item.text+'</option> ');
    			}
    		}
    		$("#statusSel").html(optionHtml.join(''));
    		$("#statusSel").trigger("chosen:updated"); 
    	});
    }
	

	//初始化产品分类树
	function initProTypeTree() {
		var initOrgUrl = $.util.basePath + '/r/productType/getTree';

		$.util.ajax({
			url : initOrgUrl,
			async : false, // 是否同步
			success : function(data) {
				if ($.util.isNull(data)) {
					data = [];
				}
				data.push({
					id : "0",
					parentId : "-1",
					typeName : "产品分类",
					open : true
				});
				var setting = {
					data : {
						simpleData : {
							enable : true,
							idKey : "id",
							pIdKey : "parentId"
						},
						key : {
							//url: "xUrl"
							name : "typeName"
						}
					},
					callback : {
						onClick : function(event, treeId, treeNode) {
							$("#queryForm input[name=proType]").val(treeNode.id);
							$("#queryForm input[name=proTypeName]").val(treeNode.typeName);
							$("#downProTypeTree .selTree").addClass("hd");
							initProSlt();
						}
					}
				};
				proTypeTree = $.fn.zTree.init($("#proTypeTree"),setting, data);
				proTypeTree.expandAll(true);
			}
		});
	}
	
	 //初始化数据校验
    function initValidate(){
    	var e = "<i class='fa fa-times-circle'></i> ";
        $("#queryForm").validate({
            rules: {
            	proTypeName: "required",
            	productSlt: "required",
            	proNum: "required"
                },
            messages: {
            	proTypeName: e + "请选择产品分类",
            	productSlt: e + "请选择产品",
            	proNum: e + "请输入库存编号"
                }
        });
    }
	
  //初始化产品下拉框
	function initProSlt(editFlg){
		var optionHtml = [];
		//获取产品类型
		var proType = $("#queryForm input[name=proType]").val();
		var proSltUrl = $.util.basePath + '/r/product/getProductSlt';
		if(!$.util.isNull(proType)){
			$.util.ajax({
				url : proSltUrl,
				data :{"proType":proType},
				async : false, // 是否同步
				success : function(data) {
					if (!$.util.isNull(data)) {
						for (var i = 0; i < data.length; i++) {
							var item = data[i];
							optionHtml.push(' <option value="'+item.id+'">' + item.proName+ '</option> ');
						}
					}
				}
			});
		}
		$("#productSlt").html(optionHtml.join(''));
		$("#productSlt").trigger("chosen:updated");
	} 
	
	 //初始化机构树
	 

   	var gridTableUrl = $.util.basePath + '/r/proStock/getFitList';
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
            uniqueId: "id",                    //每一行的唯一标识，一般为主键列
            columns: 
            [
                {field: '', checkbox:true,formatter:function(event,value,row,index){
                }},   
                {field: 'proNum', title: '产品编号',formatter:function(event,value,row,index){
                    var proNum = value.proNum;
                    var html = '<a href="javascript:openShowDialog(\''+value.id+'\');"  >'+proNum+'</a>';
                    return html;
                }}, 
                {field: 'proType', title: '所属产品分类'},
                {field: 'proName', title: '所属产品'},
                {field: 'proStockDevices', title: '设备类型'},
          		{field: 'dictStatus', title: '状态'},
          		{field: 'name', title: '产品装配人'},
          		{field: 'fitTime', title: '产品装配时间'},
           		{field: '', title: '操作',formatter:function(event,value,row,index){
                	var html='<button type="button" class="btn btn-outline btn-primary button-row button-handle-row" onclick="openSaveDialog(\''+value.id+'\');" ><i class="fa">编辑</i></button>';
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
   
    //打开详情界面
    function openShowDialog(id){
        var url = "/r/proStock/forwordFitShow?id="+id;
        var width = 780;
        var height = 640;
        var title = "库存详情";
        
        openDialog(null, url, width, height, title);
    }
    
    //跳转到 登记页面
    function openFitDialog(btnEle,id){
    	var url = "/r/proStock/forwordFitEdit?id="+id;
    	var width = "780";
		var height = "700";
		openDialog(null, url, width, height);
    }
    
    //打开保存界面
	function openSaveDialog(id,type,eve) {
			var url = "/r/proStock/forwordFitEdit?id="+id
			var width = "780";
			var height = "700";
			var title = "编辑设备信息";
			openDialog(null, url, width, height, title);
  	}
    
	 //打开行编辑界面
    function openEditRowDialog(id,btnEle){
    	
    	var url = "/r/proStock/forwordFitEdit?id="+id
    	openDialogByHandle(btnEle,url);
    }
    //删除数据
    function deleteRows(eve){
    	
        var delUrl = $.util.basePath + '/r/proStock/delete';
        del(delUrl,"gridTable",null,function(){
        	refreshTable();
        });
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
					                            <input type="hidden" name="id"/>
			                                    <div class="form-group">
			                                        <label class="control-label">产品编号</label>
			                                        <input type="text" name="proNum"  class="form-control">
			                                    </div>
			                                    
					    
					                            <div class="form-group">
					                                <label class="control-label">所属产品分类：</label>
					                                <div id="downProTypeTree"  class="form-control" style="border:0; padding:0;" >
					                                    <input id="proTypeName" name="proTypeName" class="form-control" data-placeholder="选择所属产品分类..." type="text">
					                                    <input name="proType" class="form-control" type="hidden">
						                                <div class="selTree hd" style="">
						                                    <ul id="proTypeTree" class="ztree"></ul>
						                                </div>
					                                </div>
					                            </div>
								                  
								                <div class="form-group">
					                                <label class="control-label">所属产品：</label>
					                                    <select id="productSlt" name="proId" data-placeholder="选择产品..." style="width:350px;" class="chosen-select-deselect" tabindex="12">
					                                    </select>
					                            </div>
			                                  <!--	 <div class="form-group">
			                                        <label class="control-label">库存编号</label>
			                                        <input type="text" name="deviceType"  class="form-control">
			                                    </div>
			                                 -->
													
			                                </form>
			                            </div>
	                                </td>
									
	                                <!-- <td>
	                                   <div class="col-sm search-where-div" >
			                                <form id="queryForm" role="form" class="form-inline">
			                                    
			                                </form>
			                            </div>
	                                </td> -->
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
						          <button handle-type="fit" class="btn btn-info button-handle " type="button" onclick="openFitDialog(this)"><i class="fa "></i> </button>
						           <button handle-type="delete" class="btn btn-danger button-handle" type="button" onclick="deleteRows(this)" ><i class="fa"></i></button>
						         
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