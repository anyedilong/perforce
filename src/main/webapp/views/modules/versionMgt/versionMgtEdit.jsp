<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/views/resource.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增版本</title>
<link href="<%=basePath%>/static/hplus/css/animate.min.css" rel="stylesheet">
<link href="<%=basePath%>/static/hplus/css/plugins/webuploader/webuploader.css" rel="stylesheet">
<link href="<%=basePath%>/static/hplus/css/demo/webuploader-demo.min.css" rel="stylesheet">


<script type="text/javascript" src="<%=basePath%>/static/hplus/js/content.min.js?v=1.0.0"></script>
<script type="text/javascript" src="<%=basePath%>/static/hplus/js/plugins/webuploader/webuploader.min.js"></script>

<script type="text/javascript" src="<%=basePath%>/views/modules/versionMgt/js/versionMgtEdit.js"></script>
<script type="text/javascript" src="<%=basePath%>/views/modules/versionMgt/js/fileUpload.js"></script>
<script type="text/javascript"> 
	var BASE_URL = '<%=basePath%>'+'/static/hplus/js/plugins/webuploader/index.html';
</script>


</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>版本信息</h5>
                    </div>
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="saveForm" novalidate="novalidate"  enctype=”multipart/form-data”>
                            <input type="hidden" id="versionId" name="id"/>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">产品：</label>
                                <div class="col-sm-8" id="downProTree">
                                    <input id="proName" name="proName" class="form-control" type="text"  data-placeholder="选择所属产品..." readonly="readonly"/>
                                    <input id="proId" name="proId" class="form-control" type="hidden"/>
                                    <input id="fileId" name="fileId" class="form-control" type="hidden"/>
                                    <div class="selTree hd" style="">
	                                    <ul id="proTree" class="ztree"></ul>
	                                </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">前置版本号：</label>
                                <div class="col-sm-8">
                                    <input id="preposeVersionNum" name="preposeVersionNum" class="form-control" type="text" readonly="readonly">
                                     <input id="preposeVersionId" name="preposeVersionId" class="form-control" type="hidden">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">版本类型：</label>
								<div class="col-sm-5" id="versionType_span">
									<div class="radio i-checks">
										<label><input type="radio" value="1" name="versionType"> <i></i> 全量包</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<label><input type="radio" value="2" name="versionType"> <i></i> 增量包</label>
									</div>
								</div>
							</div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">版本号：</label>
                                <div class="col-sm-8">
                                       <input id="versionNum" name="versionNum" class="form-control" type="text">
									   <input id="status" name="status" class="form-control" type="hidden">
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="col-sm-3 control-label">排序号：</label>
                                <div class="col-sm-8">
                                       <input id="orderNum" name="orderNum" class="form-control" type="text" readonly="readonly">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">版本描述：</label>
                                <div class="col-sm-8">
                                	<textarea name="versionDescription" class="form-control" rows="6"></textarea>
                                </div>
                            </div>
                        </form>
					</div>
					<div class="wrapper wrapper-content animated fadeIn">
						<div class="row">
							<div class="col-sm-12">
								<div class="ibox float-e-margins">
									<div class="ibox-title">
										<h5>版本发布包</h5>
									</div>
									<div class="ibox-content">
										<div class="page-container">
											<div id="uploader" class="wu-example">
												<div class="queueList">
													<div id="dndArea" class="placeholder">
														<div id="filePicker"></div>
														<p>支持拖拽上传</p>
													</div>
												</div>
												<div class="statusBar" style="display: none;">
													<div class="progress">
														<span class="text">0%</span> <span class="percentage"></span>
													</div>
													<div class="info"></div>
													<div class="btns">
														<div id="filePicker2"></div>
														<div class="uploadBtn">开始上传</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="ibox-content text-center">
						<button class="btn btn-info" type="button" onclick="saveForm(1)"> <i class="fa fa-save"></i> 保存 </button>
						<button class="btn btn-info" type="button" onclick="saveForm(2)" id="button_fb"> <i class="fa fa-save"></i> 发布 </button>
					</div>
        		</div>
        	</div>
    	</div>
	</div>
	<div id="modal-form" class="modal fade" aria-hidden="true">
        <div class="modal-dialog" style="width: 900;height: 700;margin: 50 50;">
            <div class="modal-content" style="height: 700;">
                <div class="modal-body" >
					<form id="queryForm" role="form" class="form-inline">
						<input type="text" name="status" type="hidden">
						<input type="text" name="proId" type="hidden">
					</form>
					<div class="row-fluid grid-div">
                               <table id="gridTable" ></table>
                   </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>