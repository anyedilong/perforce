<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/views/resource.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">
    
    var iconDialog;
    var orgTree;
    
    $(function(){
    	initValidate();
    	//初始化机构树
    	initOrgTree();
    	setTimeout('$("#downOrgTree").downZtree()', 100);
    	
    	//初始化数据
        var id = $.util.getUrlParam('id');
        if(!$.util.isNull(id)){
            initData(id);
        }
    	
        //是否为查看状态
        var type =  $.util.getUrlParam('type');
        if(type=='show'){
            
        }
    	
    });
    
    //初始化机构树
    function initOrgTree(){
        var initOrgUrl =  $.util.basePath + '/r/org/getOrgTree?type=1';
        $.util.ajax({
            url:initOrgUrl,
            async : false, // 是否同步
            data:{'type':'1'},
            success:function(data){
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
                            $("#saveForm input[name=parentId]").val(treeNode.id);
                            $("#saveForm input[name=parentName]").val(treeNode.name);
                            
                            $("#downOrgTree .selTree").addClass("hd");
                        }
                    }
                };

                orgTree = $.fn.zTree.init($("#orgTree"), setting, data);
                orgTree.expandAll(true); 
                var orgId = $.util.getUrlParam('orgId');
                if(!$.util.isNull(orgId)){
                	var node = orgTree.getNodesByParam("id", orgId, null);
                	if(!$.util.isNull(node)){
                		orgTree.selectNode(node[0]);
	                	$("#saveForm input[name=parentId]").val(node[0].id);
                        $("#saveForm input[name=parentName]").val(node[0].name);
                	}
                }
            }
        });
    }
    
    //初始化数据校验
    function initValidate(){
    	var e = "<i class='fa fa-times-circle'></i> ";
        $("#saveForm").validate({
            rules: {
            	name: "required"
                },
            messages: {
            	name: e + "请输入机构名称"
                }
        });
    }
    
    //保存form表单
    function saveForm(){
    	var saveUrl = '/r/org/saveOrg';
    	save(saveUrl,'saveForm',function(){
            parent.refreshTableAndTree();
            parent.closeDialog(menuId);
        });
    }
    
    //初始化数据
    function initData(id){
        //查询数据，绑定到元素
        var showUrl = $.util.basePath + '/r/org/show';
        var json = {'id':id}
        $.util.ajax({
            url:showUrl,
            data:json,
            success:function(data){
                bindData('saveForm',data);
                $("#saveForm .show-hidden").addClass("hd");
                
                //
                var parentId = data.parentId;
                if(!$.util.isNull(parentId)){
                    var node = orgTree.getNodesByParam("id", parentId, null);
                    if(!$.util.isNull(node)){
                        orgTree.selectNode(node[0]);
                        $("#saveForm input[name=parentId]").val(node[0].id);
                        $("#saveForm input[name=parentName]").val(node[0].name);
                    }
                }
                $("#downOrgTree .selTree").remove();
            }
        });
        
    }
    
</script>
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>保存机构</h5>
                    </div>
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="saveForm" novalidate="novalidate">
                            <input type="hidden" name="id"/>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">机构名称：</label>
                                <div class="col-sm-8">
                                    <input id="username" name="name" class="form-control" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">所属机构：</label>
                                <div class="col-sm-8">
                                    <input id="parentId" name="parentId" class="form-control" type="hidden">
                                    <div id="downOrgTree">
                                        <input name="parentName" class="form-control" type="text" readonly="readonly">
	                                    <div class="selTree hd" style="">
	                                        <ul id="orgTree" class="ztree"></ul>
	                                    </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">机构简称：</label>
                                <div class="col-sm-8">
                                    <input id="sortName" name="sortName" class="form-control" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">机构编码：</label>
                                <div class="col-sm-8">
                                    <input id="orgCode" name="orgCode" class="form-control" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">备注：</label>
                                <div class="col-sm-8">
                                    <textarea name="remarks" class="form-control" ></textarea>
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
		                <button class="btn btn-info" type="button" onclick="saveForm()"><i class="fa fa-save"></i> 保存</button>
		            </div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>