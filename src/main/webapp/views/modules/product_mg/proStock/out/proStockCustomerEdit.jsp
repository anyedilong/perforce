<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/views/resource.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">

	var ids ;
	var proId;
	$(function(){
		//初始化客户下拉列表
		initCustomer();
		//初始化数据校验
	
		setTimeout('$("#downProTypeTree").downZtree()', 100);
		ids = $.util.getUrlParam('ids');
		proId = $.util.getUrlParam('proId');
		$("#productSlt").chosen().change(function(){
			initProDevice();
		});

		$("input[name=Out]").click(function(){
			
		});
	})
	
	//初始化客户下拉列表
	function initCustomer(){
		var showUrl = $.util.basePath + '/r/customer/getCustomerDropDownList';
        $.util.ajax({
            url:showUrl,
            data:null,
            success:function(data){
            	var optionHtml = [];
            	
            	optionHtml.push(' <option value="">请选择</option> ');
        		//下拉框数据绑定
        		if(!$.util.isNull(data)){
        			
        			for(var i = 0; i < data.length;i++){
        				 var item = data[i];
        				 optionHtml.push(' <option value="'+item.id+'">'+item.name+'</option> ');
        			}
        		}
        		$("#customerIdSel").html(optionHtml.join(''));
        		$("#customerIdSel").trigger("chosen:updated"); 
            }
        });
  	}
	 

	//发送出库请求
	var testCount;
	var proSltUrl = $.util.basePath + '/r/proStock/outProStock';
	 function saveForm(){ 
		 testCount=ids.split(",").length;
		 var outCustomerUser = $("#customerIdSel").val();
		 var salesManager = $("#proNum").val();
	    $.util.ajax({
			url : proSltUrl,
			data :{"idList":ids,"outCustomerUser":outCustomerUser,"salesManager":salesManager},
			async : false, // 是否同步
			success : function(data) {
				var testSuccessCount = data;
				var testErrorCount = testCount-testSuccessCount;
				
				swal({
					"title" : "操作成功",
					"text" : "操作成功" + testSuccessCount + "件;" + "操作失败"
							+ testErrorCount + "件<br/>",
					"type" : "success"},
					function() {
						parent.closeDialog(menuId);
					}
				);  
			}
		});
	    parent.refreshTable();
	} 
	   
	
</script>

</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>出库登记</h5>
                        
						
                    </div>
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="saveForm" novalidate="novalidate">
                            <input type="hidden" name="id"/>
                           
			                <div class="form-group">
                                <label class="col-sm-3 control-label">客户：</label>
                                <div class="col-sm-8">
                                    <select id="customerIdSel" name="outCustomerUser"  data-placeholder="选择客户..."  style="width:350px;" class="chosen-select-deselect" tabindex="12">
                                    </select>
                                </div>
                            </div>           
			                        
			               <div class="form-group">
			                    <label class="col-sm-3 control-label">负责销售经理：</label>
			                    <div class="col-sm-8">
			                    <input id="proNum" type="text" name="salesManager" class="form-control" ></textarea>
			                </div>
			              </div>
			                          
			             
                          </div>
                        </form>
                        
                    </div>
		            <div class="ibox-content text-center">
		                <button class="btn btn-info" type="button" onclick="saveForm()"><i class="fa fa-save"></i> 保存</button>
		            </div>
                </div>
            </div>
        </div>
    </div>
    
	</body>
</html>
			                        