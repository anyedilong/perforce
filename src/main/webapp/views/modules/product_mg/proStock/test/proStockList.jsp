<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/views/resource.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">

	var proId;	
	$(function(){
		
		//加载grid
		initGridTable();
		//初始化数据校验
		//initValidate();
		//初始化产品分类树
		initProTypeTree();
		setTimeout('$("#downProTypeTree").downZtree()', 100);
		//初始化产品下拉框
		initProSlt()
		$("#productSlt").chosen().change(function(){
			initProDevice();
		});
	})
		//初始化设备类型下拉框
    function initDeviceType(){
    	getDictList("dictDeviceType",function(data){
    		var optionHtml = [];
    		optionHtml.push(' <option value="">全部</option> ');
    		//下拉框数据绑定
    		if(!$.util.isNull(data)){
    			for(var i = 0; i < data.length;i++){
    				 var item = data[i];
    				 optionHtml.push(' <option value="'+item.value+'">'+item.text+'</option> ');
    			}
    		}
    		$("#deviceTypeSel").html(optionHtml.join(''));
    		$("#deviceTypeSel").trigger("chosen:updated"); 
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
	function initProSlt(){
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
	
	 

   	var gridTableUrl = $.util.basePath + '/r/proStock/getTestList';

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
            	{field: '',checkbox:true,formatter:function(event,value,row,index){
            		proId=value.proId;
            		var status = value.status;
                		if(status==10){
                			return ;
                		}else{
                			return {disabled:true};
                		} 
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
    //打开详情界面
    function openShowDialog(id){
        var url = "/r/proStock/forwordStockShow?id="+id;
        var width = 780;
        var height = 640;
        var title = "出库详情";
        openDialog(null, url, width, height, title);
    }
    
    
    var testCount;
    //跳转到 测试页面
    function openFitDialog(btnEle,id){

    	var selectionRows = $('#gridTable').bootstrapTable('getSelections');
   		var ids = [];
   		testCount = selectionRows.length;
    	if(selectionRows.length <= 0){
    		swal("请选择需要测试产品", "", "warning");
    		return;
    	}else{
    		for(var i = 0 ; i < selectionRows.length ; i++){
    			var row = selectionRows[i];
    			ids.push(row['id']);
    		}
    	}
    	var  posturl =$.util.basePath +'/r/proStock/testProStock';
    	//debugger;
    	 $.util.ajax({
        	url : posturl,
			data :{"ids":ids,"proId":proId},
			success : function(data) {
				var testSuccessCount = data;
				var testErrorCount = testCount-testSuccessCount;
				if(testErrorCount==0){
					swal("操作成功","操作成功"+testSuccessCount+"件"+"   ,  "+"操作失败"+testErrorCount+"件","success");
				 }else{
					swal("操作失败","失败了"+testSuccessCount+"件"+"   ,  "+"测试了"+testErrorCount+"件","success");
				} 
				
				
				refreshTable()
			}
        });
    }
    //测试失败
    function openErrorDialog(btnEle,id){

    	var selectionRows = $('#gridTable').bootstrapTable('getSelections');
   		var ids = [];
   		testCount = selectionRows.length;
    	if(selectionRows.length <= 0){
    		swal("请选择需要失败的产品", "", "warning");
    		return;
    	}else{
    		for(var i = 0 ; i < selectionRows.length ; i++){
    			var row = selectionRows[i];
    			ids.push(row['id']);
    		}
    	}
    	var  posturl =$.util.basePath +'/r/proStock/errorProStock';
    	debugger;
    	 $.util.ajax({
        	url : posturl,
			data :{"ids":ids},
			success : function(data) {
				var testSuccessCount = data;
				var testErrorCount = testCount-testSuccessCount;
				swal("操作成功","失败了"+testSuccessCount+"件"+"   ,  "+"测试了"+testErrorCount+"件","success");
				refreshTable();
			}
        })
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
			                                    <!--  
			                                        <label class="control-label">设备编号</label>
			                                        <input type="text" name="deviceCode"  class="form-control">
			                                    -->
			                                    	<label class="control-label">产品编号</label>
			                                        <input type="text" name="proNum"  class="form-control">
			                                    </div>
			                                     <!--  
			                                    <div class="form-group">
			                                        <label class="control-label">设备型号</label>
			                                        <input type="text" name="deviceModel"  class="form-control">
			                                    </div>
			                                     -->
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
						          <button handle-type="test" class="btn btn-info button-handle " type="button" onclick="openFitDialog(this)"><i class="fa "></i> </button>
						           <button handle-type="error" class="btn btn-info button-handle " type="button" onclick="openErrorDialog(this)"><i class="fa "></i> </button>
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