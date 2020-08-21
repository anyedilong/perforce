<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/views/resource.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript" src="<%=basePath%>/views/modules/product_mg/product/js/productEdit.js"></script>

</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>保存产品</h5>
                    </div>
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="saveForm" novalidate="novalidate">
                            <input type="hidden" name="id"/>
                            <div class="form-group ">
                                <label class="col-sm-3 control-label">产品名称：</label>
                                <div class="col-sm-8">
                                    <input id="proName" name="proName" class="form-control" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">产品编码：</label>
                                <div class="col-sm-8">
                                    <input id="proCode" name="proCode" class="form-control" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">所属产品分类：</label>
                                <div id="downProTypeTree" class="col-sm-8">
                                    <input id="proTypeName" name="proTypeName" class="form-control" data-placeholder="选择所属产品分类..." type="text">
                                    <input  name="proType" class="form-control" type="hidden">
	                                <div class="selTree hd" style="">
	                                    <ul id="proTypeTree" class="ztree"></ul>
	                                </div>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-3 control-label">产品立项时间：</label>
                                <div class="col-sm-8">
                                    <input id="proApprovalTime" name="proApprovalTime" class="form-control picker-date" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">产品定型时间：</label>
                                <div class="col-sm-8">
                                    <input id="proFinalTime" name="proFinalTime" class="form-control picker-date" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">产品投产时间：</label>
                                <div class="col-sm-8">
                                    <input id="proPutTime" name="proPutTime" class="form-control picker-date" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">产品软件质保时间（月）：</label>
                                <div class="col-sm-8">
                                    <input id="proSoftwareWarranty" name="proSoftwareWarranty" class="form-control" type="number" min="0">
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-3 control-label">产品说明：</label>
                                <div class="col-sm-8">
                                    <textarea name="proDescription" class="form-control" ></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">产品功能：</label>
                                <div class="col-sm-8">
                                    <textarea name="proFunction" class="form-control" ></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">产品简介：</label>
                                <div class="col-sm-8">
                                    <textarea name="proIntroducte" class="form-control" ></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">备注：</label>
                                <div class="col-sm-8">
                                    <textarea name="remarks" class="form-control" ></textarea>
                                </div>
                            </div>
                        </form>
                        <div class="well" style="padding-bottom: 0;">
                             <div class="row">
					            <div class="col-sm">
					                <div class="ibox float-e-margins">
					                    <div class="ibox-title">
					                        <h5>附属设备</h5>
					                        <button class="btn btn-info" style="float: right;padding: 3px 10px;" type="button" onclick="addSubDevice()"><i class="fa fa-plus"></i> 添加附属设备</button>
					                    </div>
					                    <div class="ibox-content">
                                            <table id="proDevice" class="table table-hover table-striped" style="border: 1px solid #e4eaec;">
                                                 <thead>
                                                     <tr>
                                                         <th style="width: 140px;"><div class="th-inner">设备类型</div></th>
                                                         <th><div class="th-inner">设备型号</div></th>
                                                         <th style="width: 100px;"><div class="th-inner">数量</div></th>
                                                         <th style="width: 85px;"><div class="th-inner">附属设备</div></th>
                                                         <th style="width: 85px;"><div class="th-inner">必要设备</div></th>
                                                         <th style="width: 50px;"><div class="th-inner">操作</div></th>
                                                     </tr>
                                                 </thead>
                                                <tbody id="proDeviceBody">
                                                       	                                                    
                                                </tbody>
                                             </table>
					                    </div>
				                    </div>
			                    </div>
		                    </div>             
                         </div>
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