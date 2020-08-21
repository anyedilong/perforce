var proTree;
var BASE_URL = 'js/plugins/webuploader/index.html';
var versionType_info;
var prodVersFileList = [];
var fileCount = 0;

var uploader;
//页面初始化方法
$(function() {
	initVersionType();//加载版本类型
	
	//初始化产品分类树
	initProTree();
	setTimeout('$("#downProTree").downZtree()', 100);
	
	//验证
	
	var id = $.util.getUrlParam('id');
	if (!$.util.isNull(id)) {
		initData(id);//加载数据
		document.getElementById("button_fb").style.display = "none";
	}
	
	initValidate();
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
			bindData('saveForm', data);
			
			//初始化   所属产品分类
            var proId = data.proId;
            if(!$.util.isNull(proId)){
                var treeNode = proTree.getNodesByParam("id", proId, null);
                if(!$.util.isNull(treeNode)){
                	proTree.selectNode(treeNode[0]);
                    $("#saveForm input[name=proId]").val(treeNode[0].id);
					$("#saveForm input[name=proName]").val(treeNode[0].typeName);
                }
            }
			
			var status = data.status;
			if(status==1){
				document.getElementById("button_fb").style.display = "";//如果是未发布状态，显现发布按钮。
			}
			
			var versionType = data.versionType
			if(versionType!=undefined){//版本类型赋值
					$("input[name='versionType'][value="+versionType+"]").attr("checked",true);  
			}
			
			var product = data.product;
			if (!$.util.isNull(product)) {
				$("#saveForm input[name=proName]").val(product.proName); //产品名称赋值。
			}
			
			var fileId = data.fileId
			prodVersFileList = data.prodVersFileList; 
			if(!$.util.isNull(prodVersFileList)){ //附件初始化
				showFileInfo();
			}
		}
	});
}

//初始化数据校验
function initValidate(){
	var e = "<i class='fa fa-times-circle'></i> ";
	
    $("#saveForm").validate({
        rules: {
        	proName: "required",
        	versionType: "required",
        	versionNum: "required"
            },
        messages: {
        	proName: e + "请输入产品名称",
        	versionType: e + "请选择版本类型",
        	versionNum: e + "请输入版本号"
            }
    });
    
}

//保存form表单
function saveForm(status) {
	if(fileCount>0){
		swal("添加的文件未上传！", "请点击 “开始上传”按钮", "warning");
		return;
	}
	
	var param = prodVersFileList;
	$("#saveForm input[name=status]").val(status);
	
	var saveUrl = '/r/vmm/save';
	save(saveUrl, 'saveForm', function() {
		 parent.refreshTable();
		 parent.closeDialog(menuId);
	 },{"prodVersFileList":prodVersFileList});
	
}


//版本类型
function initVersionType(){
	getDictList("versionType",function(data){
		versionType_info = data;
	});
}

//加载版本类型选择框
function setVersionType(versionType){
	
	if (!$.util.isNull(versionType_info)) {
		var innerHTML_vt='';
		
		for (var i = 0; i < versionType_info.length; i++) {
			var item = versionType_info[i];
			var value = item.value;
			if(versionType==value){
				innerHTML_vt = innerHTML_vt+item.text+'<input type="radio" name="versionType" value="'+value+'" checked/> '+'&nbsp;&nbsp;&nbsp;&nbsp;';
			}else{
				innerHTML_vt = innerHTML_vt+item.text+'<input type="radio" name="versionType" value="'+value+'" /> '+'&nbsp;&nbsp;&nbsp;&nbsp;';
			}
		}
		document.getElementById("versionType_span").innerHTML = innerHTML_vt;
	}
}

//初始化产品选择树
function initProTree() {
	var initOrgUrl = $.util.basePath + '/r/vmm/getTree';

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
				typeName : "产品",
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
						if(!treeNode.isParent){
							$("#saveForm input[name=proId]").val(treeNode.id);
							$("#saveForm input[name=proName]").val(treeNode.typeName);
							$("#downProTree .selTree").addClass("hd");
							getOldVersion(treeNode.id);
						}
					}
				}
			};

			proTree = $.fn.zTree.init($("#proTree"), setting, data);
			proTree.expandAll(true);
		}
	});
}

//根据选定的产品proId，设置前置版本号和排序号
function getOldVersion(proId){
	var initUrl = $.util.basePath + '/r/vmm/getPrepVersInfo';

	$.util.ajax({
		url : initUrl,
		data:{"proId":proId},
		async : false, // 是否同步
		success : function(data) {
			var preposeVersionId = data.id;
			var preposeVersionNum = data.versionNum;
			var orderNum = (data.orderNum+1);
			
			if(preposeVersionId!=undefined){ //如果产品有相关前置信息
				$("#saveForm input[name=preposeVersionId]").val(preposeVersionId);
				$("#saveForm input[name=preposeVersionNum]").val(preposeVersionNum);
				$("#saveForm input[name=orderNum]").val(orderNum);
			}else{
				$("#saveForm input[name=orderNum]").val(1);
			}
		}
	});
	
}

function showFileInfo(){
	var  o = jQuery;
	var r = o("#uploader");
	var u = r.find(".placeholder");
	d = r.find(".statusBar");
	
	var filelist = $(".filelist");
	
	for(var fl in prodVersFileList){
		
		var id =  prodVersFileList[fl].id;
		var versionId = prodVersFileList[fl].versionId;
		
		var prodVersFile = prodVersFileList[fl];
		var fileid = prodVersFile.fileDomain.id;
		var fileName = prodVersFile.fileDomain.fileName;
		var filepath = prodVersFile.fileDomain.serverUrl+prodVersFile.fileDomain.filePath;
		var a = o('<li id="' + fileid + '"><p class="title">' + fileName + '</p><p class="imgWrap"></p><p class="progress"><span></span></p></li>');
		//var s = o('<div class="file-panel"><span class="cancel">删除</span></div>').appendTo(a);
		
		u.addClass("element-invisible"),
		o("#filePicker2").removeClass("element-invisible");
		d.removeClass("element-invisible");
		d.show();
		a.appendTo(filelist);
		a.append('<span class="success"></span>');//增加对号样式
		var s1 = o('<div class="file-panel"><span class="cancel" onclick="delFile(\'' + id + '\',\'' + fileid + '\',\'' + versionId + '\')">删除</span></div>').appendTo(a); //增加删除按钮
		s1.stop().animate({
			height: 30
		})
		tt = a.find("p.imgWrap");
		tt.text("点击下载！");
		tt[0].style.cursor = 'pointer'; 
		tt. click(function(){ //下载
			
			var url = filepath;
			window.open(url);  
		});
	}
	
	uploader.refresh();
	
	
}

//附件删除
function delFile(id,fileId,versionId){
	var initUrl = $.util.basePath + '/r/vmm/delFileById';
	swal({   
        title:"确认删除?",
        text: "删除后的数据将无法查看",
        type: "warning",   
        showCancelButton: true,   
        confirmButtonColor: "#DD6B55",   
        confirmButtonText: "删除",   
        cancelButtonText:"取消",
        closeOnConfirm: false }, 
        function(){
			$.util.ajax({
				url : initUrl,
				data:{"id":id,"fileId":fileId,"versionId":versionId},
				async : false, // 是否同步
				success : function(data) {
					
					var  o = jQuery;
					var r = o("#uploader");
					var u = r.find(".placeholder");
					d = r.find(".statusBar");
					var filelist = $(".filelist");
					var fileCount =  filelist[0].childElementCount;
					var li = $("#"+fileId+"");
					if(fileCount==1){//如果只有一个附件，样式恢复
						 u.removeClass("element-invisible");
						 d.addClass("element-invisible");
					}
					li.remove();//样式上移除
					uploader.refresh();
					
					swal({
                        title : "",
                        timer : 0
                    });
					
					
				}
			})}
      );
}


function addfile(fileid){
	var itemParam = {"fileid":fileid};
	prodVersFileList.push(itemParam);
}


//根据选定的产品proId，获取版本信息
//function getOldVersion(){
//	var gridTableUrl = $.util.basePath + '/r/vmm/getList';
//	gridTable = $("#gridTable")
//			.bootstrapTable(
//					{
//						url : gridTableUrl,
//						method : 'post', // 请求方式（*）
//						striped : true, // 是否显示行间隔色
//						cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
//						pagination : false, // 是否显示分页（*）
//						queryParams : {
//							'includeSubFlg' : '1'
//						},
//						clickToSelect : false, // 是否启用点击选中行
//						uniqueId : "id", // 每一行的唯一标识，一般为主键列
//						columns : [
//									{field : '', checkbox : true, formatter : function(event, value, row, index) { }},
//									
//									{field: 'versionNum', title: '版本编号',formatter:function(event,value,row,index){
//						                var versionNum = value.versionNum;
//						                var html = '<a href="javascript:openShowDialog(\''+value.id+'\');"  >'+versionNum+'</a>';
//						                return html;
//						            }},   
//									
//									{field : 'proName',title : '产品'},
//									{field : 'proCode',title : '产品编码'},
//									{field : 'versionType',title : '版本类型',formatter : function(event, value, row,index) {var versionType = value.versionType;if(versionType=='1'){return '全量包'}else if(versionType=='2')  {return "增量包"}}},
//									{field : 'preposeVersionId',title : '前置版本号'},
//									{field : 'releaseTime',title : '发布时间'},
//									{field : 'releaseUserName',title : '发布人'},
//									{field : 'status',title : '状态',formatter : function(event, value, row,index) {var statusValue = value.status;if(statusValue=='1'){return '待发布'}else if(statusValue=='2')  {return "已发布"}}},
//									{field : '',title : '操作',
//										formatter : function(event, value, row,
//												index) {
//											var status = value.status;
//												
//							                	html = '<button handle-type="edit_row"  type="button" class="btn btn-outline btn-primary button-row button-handle-row" onclick="openSaveDialog(this,\''+value.id+'\');" ><i class="fa"></i></button>';
//							                	
//							                	//删除 
//							                    if(status=='1') {
//							                        html += '<button handle-type="del_row" type="button" class="btn btn-outline btn-primary button-row button-handle-row" onclick="deleteRows(\''+value.id+'\');" ><i class="fa"></i></button>';
//							                    }
//							                	return html;
//										}} ],
//						onLoadSuccess : function() {
//							initRowHandle("gridTable");
//						}
//					});
//
//}



