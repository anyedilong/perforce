<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/views/resource.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">
    
    var iconDialog;
    
    $(function(){
    	$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})
    	//初始化菜单tree
    	initMenuTree($.util.getUrlParam('parentId'));
    	//初始化数据校验
    	initValidate();
    	
    	$("#parentMenuSel").on("change",function(){
    		//alert($("#parentMenuSel").val())
    	});
    	
    	//初始化数据
    	var id = $.util.getUrlParam('id');
    	if(!$.util.isNull(id)){
    		initData(id);
    		//移除菜单树选择
    		$("#parentMenuSel_chosen .chosen-drop").remove();
    	}
    	
    	//是否为查看状态
    	var type =  $.util.getUrlParam('type');
    	if(type=='show'){
    		
    	}
    	
    	
    	//打开图标弹出框
    	$("#chooseIcon").on("click",function(){
    		var iconUrl = $.util.basePath+'/r/menu/handleIcon';
    		iconDialog = $.dialog({
    			width:600,
    			height:400,
    			min:false,
    			max:false,
    			drag:false,
    			lock:true,
    			title: '菜单图标选择',
    			lock: true,
    			content: 'url:'+iconUrl});
    	});
    	
    });
    
    //初始化菜单tree
   function initMenuTree(parentId){
        var intiMenuUrl = $.util.basePath + '/r/menu/getMenuTree';
        $.util.ajax({
            url:intiMenuUrl,
            async : false, // 是否同步
            success:function(data){
            	if(!$.util.isNull(data)){
           		    var optionHtml = [];
           		    optionHtml.push(' <option value="">请选择</option> ');
           		    for(var i = 0; i < data.length;i++){
           		    	var item = data[i];
            			var value = item.id;
            		    var text = item.name;
            		    var level = item.menuLevel-1;
            		    while (level>0){
            		    	text = "&nbsp;&nbsp;&nbsp;"+text;
            		    	level--; 
            		    }
            			if(parentId == value){
            			    optionHtml.push(' <option selected value="'+value+'">'+text+'</option> ');
            			}else{
            			    optionHtml.push(' <option value="'+value+'">'+text+'</option> ');
            			}
           		    }
            		$("#parentMenuSel").html(optionHtml.join(''));
            		
            		$("#parentMenuSel").trigger("chosen:updated"); 
            		$("#parentMenuSel").chosen({allow_single_deselect:true});
            	}
            }
        });
    }
    
    //初始化数据校验
    function initValidate(){
    	var e = "<i class='fa fa-times-circle'></i> ";
        $("#menuForm").validate({
            rules: {
            	name: "required",
            	handleType: "required"
                },
            messages: {
            	name: e + "请输入操作名称",
            	handleType: e + "请输入操作类型"
                }
        });
    }
    
    //初始化数据
    function initData(id){
    	//查询数据，绑定到元素
    	var showUrl = $.util.basePath + '/r/menu/show';
    	var json = {'id':id}
    	$.util.ajax({
            url:showUrl,
            data:json,
            success:function(data){
                bindData('menuForm',data);
                var iconPath = data.icon;
                if(!$.util.isNull(iconPath)){
                    iconChoose(iconPath)
                }
                $("#menuForm select").trigger("chosen:updated"); 
            }
        });
    }
    
    //保存
    function saveMenu(){
    	var saveMenuUrl = '/r/menu/saveHandle';
    	save(saveMenuUrl,'menuForm',function(){
    		parent.refreshTableAndTree();
    		parent.closeDialog(menuId);
    	});
    }
    
    //关闭图标选择
    function iconDialogClose(iconPath){
    	iconChoose(iconPath);
    	iconDialog.close();
    }
    
    //菜单图标选择
    function iconChoose(iconPath){
        $("#icon").val(iconPath);
        $("#iconFa").removeClass().addClass("fa").addClass(iconPath);
    }
    
    //清除图标
    function clearIcon(){
    	$("#icon").val("");
    	$("#iconFa").removeClass().addClass("fa");
    }
    
</script>
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>保存操作</h5>
                    </div>
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="menuForm" novalidate="novalidate">
                            <input type="hidden" name="id"/>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">操作名称：</label>
                                <div class="col-sm-8">
                                    <input id="name" name="name" class="form-control" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">操作类型：</label>
                                <div class="col-sm-8">
                                    <input id="handleType" name="handleType" class="form-control" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">上级菜单：</label>
                                <div class="col-sm-8">
                                    <select id="parentMenuSel" name="parentId" data-placeholder="选择上级菜单..." style="width:350px;" class="chosen-select-deselect" tabindex="12">
							        </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">操作图标：</label>
                                <div class="col-sm-8">
                                    <i class="fa" id="iconFa" style="font-size: 40px;"></i>
                                    <button class="btn btn-info" type="button" id="chooseIcon">选择图标</button>
                                    <button class="btn btn-danger" type="button" onclick="clearIcon();">清除</button>
                                    <input id="icon" name="icon" class="form-control" readonly="readonly" type="hidden">
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-3 control-label">弹窗：</label>
                                <div class="col-sm-4" style="padding-top:7px;">
                                    <input type="checkbox" name="dialogFlg"  value="1" class="i-checks">
                                </div>
                                <label class="col-sm-2 control-label">行操作：</label>
                                <div class="col-sm-3" style="padding-top:7px;">
                                    <input type="checkbox" name="rowHandleFlg" value="1" class="i-checks">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">弹窗标题：</label>
                                <div class="col-sm-8">
                                    <input id="title" name="title" class="form-control" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">宽度：</label>
                                <div class="col-sm-3">
                                    <input id="width" name="width" class="form-control" type="text">
                                </div>
                                <label class="col-sm-2 control-label">高度：</label>
                                <div class="col-sm-3">
                                    <input id="height" name="height" class="form-control" type="text">
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-3 control-label">排序号：</label>
                                <div class="col-sm-8">
                                    <input id="orderNum" name="orderNum" class="form-control" type="number">
                                </div>
                            </div>
                            
                        </form>
                    </div>
		            <div class="ibox-content text-center">
		                <button class="btn btn-info" type="button" onclick="saveMenu()"><i class="fa fa-save"></i> 保存</button>
		            </div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>