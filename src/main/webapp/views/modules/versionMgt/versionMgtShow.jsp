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
<script type="text/javascript" src="<%=basePath%>/views/modules/versionMgt/js/fileUpload.js"></script>
<script type="text/javascript"> 
	var BASE_URL = '<%=basePath%>'+'/static/hplus/js/plugins/webuploader/index.html';



	$(function(){
        var id = $.util.getUrlParam('id');
        if(!$.util.isNull(id)){
            initData(id);
        }
    });

	//初始化数据
	function initData(id) {
		
		//查询数据，绑定到元素
		var showUrl = $.util.basePath + '/r/vmm/show';
		var json = {
			'id' : id
		};
		$.util.ajax({
			url : showUrl,
			data : json,
			
			success : function(data) {
				bindShowData(data);
				var versionType = data.versionType;
				var versionDescription = data.versionDescription;
				if(versionType!=undefined){

						$("input[name='versionType'][value="+versionType+"]").attr("checked",true);  
				}
				if(versionDescription!=undefined){
					$("#versionDescription").val(versionDescription); 
				}
				var product = data.product;
				if (!$.util.isNull(product)) {
					$("#proName").text(product.proName); //产品名称赋值。
				}

				var prodVersFileList = data.prodVersFileList; 
				showFileInfo(prodVersFileList);//附件初始化
			}
		});
	}


	function showFileInfo(prodVersFileList){
		var  o = jQuery;
		var r = o("#uploader");
		var u = r.find(".placeholder");
		d = r.find(".statusBar");
		
		var filelist = $(".filelist");
		
		if($.util.isNull(prodVersFileList)){
			u.addClass("element-invisible");
		}
		for(var fl in prodVersFileList){
			
			var id =  prodVersFileList[fl].id;
			var versionId = prodVersFileList[fl].versionId;
			
			var prodVersFile = prodVersFileList[fl];
			var fileid = prodVersFile.fileDomain.id;
			var fileName = prodVersFile.fileDomain.fileName;
			var filepath = prodVersFile.fileDomain.serverUrl+prodVersFile.fileDomain.filePath;
			
			u.addClass("element-invisible");
			o("#filePicker2").removeClass("element-invisible");
			var a = o('<li id="' + fileid + '"><p class="title">' + fileName + '</p><p class="imgWrap"></p><p class="progress"><span></span></p></li>');
			a.append('<span class="success"></span>');//增加对号样式
			
			tt = a.find("p.imgWrap");
			tt.text("点击下载！");
			tt[0].style.cursor = 'pointer'; 
			tt. click(function(){ //下载
				
				var url = filepath;
				window.open(url);  
			});
			a.appendTo(filelist);
		}
		
		
	}
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
                            <div class="row show-row show-text">
                                <label class="col-sm-3 control-label font-l">产品：</label>
                                <div class="col-sm-8" id="downProTree">
                                    <lable class="form-control-static show-data" id="proName" data-name="proName">asdasdasdasdasd</lable>
                                </div>
                            </div>
                            <div class="row show-row">
                                <label class="col-sm-3 control-label font-l">前置版本号：</label>
                                <div class="col-sm-8">
                                    <lable class="form-control-static show-data" data-name="preposeVersionNum"></lable>
                                </div>
                            </div>
                            <div class="row show-row">
                                <label class="col-sm-3 control-label font-l">版本类型：</label>
								<div class="col-sm-5" id="versionType_span">
									<div class="radio i-checks">
										<label><input type="radio" value="1" name="versionType"> <i></i> 全量包</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<label><input type="radio" value="2" name="versionType"> <i></i> 增量包</label>
									</div>
								</div>
							</div>
                            <div class="row show-row">
                                <label class="col-sm-3 control-label font-l">版本号：</label>
                                <div class="col-sm-8">
									   <lable class="form-control-static show-data" id="versionNum" data-name="versionNum"></lable>
                                </div>
                            </div>
                            <div class="row show-row">
                                <label class="col-sm-3 control-label font-l">排序号：</label>
                                <div class="col-sm-8">
                                       <lable class="form-control-static show-data" id="orderNum" data-name="orderNum"></lable>
                                </div>
                            </div>
                            <div class="row show-row">
                                <label class="col-sm-3 control-label font-l">版本描述：</label>
                                <div class="col-sm-8">
                                	<textarea id="versionDescription"  class="form-control" rows="6"></textarea>
                                </div>
                            </div>
							<div class="row show-row">
                                <label class="col-sm-3 control-label font-l">发布人：</label>
                                <div class="col-sm-8">
                                	<lable class="form-control-static show-data" data-name="releaseUserName"></lable>
                                </div>
                            </div>
							<div class="row show-row">
								<label class="col-sm-3 control-label font-l">发布时间：</label>
								<div class="col-sm-8">
                                    <lable class="form-control-static show-data" data-name="releaseTime"></lable>
								</div>
							</div>
							<div class="row show-row">
								<label class="col-sm-3 control-label font-l">创建人：</label>
								<div class="col-sm-8 show-text">
									<lable class="form-control-static show-data"
										data-name="createUserName"></lable>
								</div>
							</div>
							<div class="row show-row">
								<label class="col-sm-3 control-label font-l">创建时间：</label>
								<div class="col-sm-8 show-text">
									<lable class="form-control-static show-data"
										data-name="createTime"></lable>
								</div>
							</div>
							<div class="row show-row">
								<label class="col-sm-3 control-label font-l">更新人：</label>
								<div class="col-sm-8 show-text">
									<lable class="form-control-static show-data"
										data-name="updateUserName"></lable>
								</div>
							</div>
							<div class="row show-row">
								<label class="col-sm-3 control-label font-l">更新时间：</label>
								<div class="col-sm-8 show-text">
									<lable class="form-control-static show-data"
										data-name="updateTime"></lable>
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

        		</div>
        	</div>
    	</div>
	</div>
</body>
</html>