<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/views/resource.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">
    
    var proTypeTree;
    
    $(function(){
    	initValidate();
    	//初始化产品分类树
    	initProTypeTree();
    	setTimeout('$("#downProTypeTree").downZtree()', 100);
    	
    	//初始化数据
        var id = $.util.getUrlParam('id');
        if(!$.util.isNull(id)){
            initData(id);
        }
        
    });
    
    //初始化数据校验
    function initValidate(){
    	var e = "<i class='fa fa-times-circle'></i> ";
        $("#saveForm").validate({
            rules: {
            	typeName: "required",
            	code: "required"
                },
            messages: {
            	typeName: e + "请输入产品分类名称",
            	code: e + "请输入产品分类code"
                }
        });
    }
    
    //初始化产品分类树
    function initProTypeTree(){
        var initOrgUrl =  $.util.basePath + '/r/productType/getTree';
        
        $.util.ajax({
            url:initOrgUrl,
            async : false, // 是否同步
            success:function(data){
                if($.util.isNull(data)){
                    data = [];
                }
                data.push({id:"0",parentId:"-1",typeName:"产品分类",open:true});
                var setting = {
                    data: {
                        simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "parentId"
                        },
                        key:{
                            //url: "xUrl"
                        	name: "typeName"
                        }
                    },
                    callback : {
                        onClick:function(event, treeId, treeNode){
                        	 $("#saveForm input[name=parentId]").val(treeNode.id);
                             $("#saveForm input[name=parentName]").val(treeNode.typeName);
                             $("#downProTypeTree .selTree").addClass("hd");
                        }
                    }
                };

                proTypeTree = $.fn.zTree.init($("#proTypeTree"), setting, data);
                proTypeTree.expandAll(true); 
                
                //初始化   所属产品分类
                var parentId = $.util.getUrlParam('parentId');
                if(!$.util.isNull(parentId)){
                    var node = proTypeTree.getNodesByParam("id", parentId, null);
                    if(!$.util.isNull(node)){
                    	proTypeTree.selectNode(node[0]);
                        $("#saveForm input[name=parentId]").val(node[0].id);
                        $("#saveForm input[name=parentName]").val(node[0].typeName);
                    }
                }
            }
        });
    }
   
    
    //初始化数据
    function initData(id){
        //查询数据，绑定到元素
        var showUrl = $.util.basePath + '/r/productType/show';
        var json = {'id':id}
        $.util.ajax({
            url:showUrl,
            data:json,
            success:function(data){
                bindData('saveForm',data);
                $("#saveForm .show-hidden").addClass("hd");
                
                //初始化   所属产品分类
                var parentId = $.util.getUrlParam('parentId');
                if(!$.util.isNull(parentId)){
                    var node = proTypeTree.getNodesByParam("id", parentId, null);
                    if(!$.util.isNull(node)){
                        proTypeTree.selectNode(node[0]);
                        $("#saveForm input[name=parentId]").val(node[0].id);
                        $("#saveForm input[name=parentName]").val(node[0].typeName);
                    }
                }
                $("#downProTypeTree .selTree").remove();
            }
        });
        
    }
    
    //保存form表单
    function saveForm(){
        var saveUrl = '/r/productType/save';
        save(saveUrl,'saveForm',function(){
            parent.refreshTableAndTree();
            parent.closeDialog(menuId);
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
                        <h5>保存产品分类</h5>
                    </div>
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="saveForm" novalidate="novalidate">
                            <input type="hidden" name="id"/>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">产品分类名称：</label>
                                <div class="col-sm-8">
                                    <input id="typeName" name="typeName" class="form-control" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">产品分类code：</label>
                                <div class="col-sm-8">
                                    <input id="code" name="code" class="form-control" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">所属产品分类：</label>
                                <div class="col-sm-8">
                                    <div id="downProTypeTree">
                                        <input name="parentName" class="form-control" data-placeholder="选择所属产品分类..." type="text" readonly="readonly">
                                        <input id="parentId" name="parentId" class="form-control" type="hidden">
	                                    <div class="selTree hd" style="">
	                                        <ul id="proTypeTree" class="ztree"></ul>
	                                    </div>
                                    </div>
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
                                    <input id="orderNum" name="orderNum" class="form-control" type="number" min="0">
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