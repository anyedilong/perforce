<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/views/resource.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript" src="<%=basePath%>/views/modules/product_mg/product/js/productList.js"></script>

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
			                                        <label class="control-label">产品编码</label>
			                                        <input type="text" name="proCode"  class="form-control">
			                                    </div>
			                                    <div class="form-group">
					                                <label class="control-label">所属产品分类：</label>
				                                    <div id="downProTypeTree" class="form-control" style="border:0; padding:0;" >
				                                        <input name="proTypeName" class="form-control" data-placeholder="选择所属产品分类..." type="text" readonly="readonly">
				                                        <input id="proType" name="proType" class="form-control" type="hidden">
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
					                            <div class="form-group">
					                                <label class="control-label">状态：</label>
				                                    <select id="statusSlt" name="status" data-placeholder="选择状态..." style="width:150px;" class="chosen-select-deselect" tabindex="12">
				                                        <option value="" selected="selected">全部</option>
				                                        <option value="1" >待发布</option>
				                                        <option value="2" >已发布</option>
				                                        <option value="3" >撤销发布</option>
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
						          <button handle-type="add" class="btn btn-info button-handle " type="button" onclick="openSaveDialog(this)"><i class="fa "></i> </button>
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