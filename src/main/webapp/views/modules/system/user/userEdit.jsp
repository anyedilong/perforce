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
    	initValidate();
    	//初始化机构树
    	initOrgTree();
    	setTimeout('$("#downOrgTree").downZtree()', 100);
    	
    	//初始化角色下拉
    	initRole();
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
        var initOrgUrl =  $.util.basePath + '/r/org/getUserOrgTree';
        $.util.ajax({
            url:initOrgUrl,
            data:{'type':'2'},
            async : false, // 是否同步
            success:function(data){
                var setting = {
                    data: {
                        simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "parentId"
                        },
                        key:{
                            //url: "xUrl"
                        }
                    },
                    callback : {
                        onClick:function(event, treeId, treeNode){
                            $("#saveForm input[name=orgId]").val(treeNode.id);
                            $("#saveForm input[name=orgName]").val(treeNode.name);
                            
                            $("#downOrgTree .selTree").addClass("hd");
                            initMrgOrg(treeNode);
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
                        $("#saveForm input[name=orgId]").val(node[0].id);
                        $("#saveForm input[name=orgName]").val(node[0].name);
                        
                        initMrgOrg(node[0]);
                    }
                }
            }
        });
    }
    
    //初始化管辖机构
    function initMrgOrg(mrgOrgData){
    	var initLevel = mrgOrgData.orgLevel;
    	
    	var margOrgChild = mrgOrgData.children;
    	var optionHtml = [];
    	
    	optionHtml.push(' <option value="'+mrgOrgData.id+'">'+mrgOrgData.name+'</option> ');
    	
    	getMrgSelHtml(margOrgChild,optionHtml,initLevel);
    	
    	$("#mrgOrgSel").html(optionHtml.join(''));
    	
    	$("#mrgOrgSel").trigger("chosen:updated");
  	}
    
    function getMrgSelHtml(margOrgChild,optionHtml,orgLevel){
    	
    	if(!$.util.isNull(margOrgChild) && margOrgChild.length > 0){
    		for(var i = 0 ; i < margOrgChild.length ; i++){
    			var margOrg = margOrgChild[i];
    			var value = margOrg.id;
    			var text = margOrg.name;
    			var level = margOrg.orgLevel - orgLevel;
    			
    			while (level>0){
                    text = "&nbsp;&nbsp;&nbsp;"+text;
                    level--; 
                }
    			optionHtml.push(' <option value="'+value+'">'+text+'</option> ');
    			getMrgSelHtml(margOrg.children,optionHtml,orgLevel);
    			
    		}
    	}
    	
    }
    
    //初始化数据校验
    function initValidate(){
    	var e = "<i class='fa fa-times-circle'></i> ";
        $("#saveForm").validate({
            rules: {
            	username: "required",
            	name: "required"
                },
            messages: {
            	username: e + "请输入用户名",
            	name: e + "请输入用户姓名"
                }
        });
    }
    
    //保存form表单
    function saveForm(){
        $("#saveForm input[name=roleIds]").val($("#parentRoleSel").val())
        $("#saveForm input[name=mrgOrgIds]").val($("#mrgOrgSel").val())
    	var saveUrl = '/r/user/save';
    	save(saveUrl,'saveForm',function(){
            parent.refreshTable();
            parent.closeDialog(menuId);
        });
    }
    
    //初始化数据
    function initData(id){
        //查询数据，绑定到元素
        var showUrl = $.util.basePath + '/r/user/show';
        var json = {'id':id}
        $.util.ajax({
            url:showUrl,
            data:json,
            success:function(data){
                bindData('saveForm',data);
                $("#username").attr('readonly',true);
                $("#saveForm .show-hidden").addClass("hd");
                
                var orgId = data.orgId;
                if(!$.util.isNull(orgId)){
                    var node = orgTree.getNodesByParam("id", orgId, null);
                    if(!$.util.isNull(node)){
                        orgTree.selectNode(node[0]);
                        $("#saveForm input[name=orgId]").val(node[0].id);
                        $("#saveForm input[name=orgName]").val(node[0].name);
                        initMrgOrg(node[0]);
                    }
                }
                $("#downOrgTree .selTree").remove();
                
                //初始化角色
                var userRoleList = data.userRoleList;
                if(!$.util.isNull(userRoleList)){
                	for(var i = 0 ; i < userRoleList.length ; i++){
                		var userRole = userRoleList[i];
                		 $("#parentRoleSel option[value='" + userRole.roleId + "']").attr('selected', 'selected');
                	}
                	//$("#parentMenuSel").trigger("chosen:updated"); 
                	$("#parentRoleSel").trigger("chosen:updated");
                }
                
                //初始化管辖机构
                var mrgOrgList = data.mrgOrgList;
                if(!$.util.isNull(mrgOrgList)){
                    for(var i = 0 ; i < mrgOrgList.length ; i++){
                        var mrgOrg = mrgOrgList[i];
                         $("#mrgOrgSel option[value='" + mrgOrg.orgId + "']").attr('selected', 'selected');
                    }
                    //$("#parentMenuSel").trigger("chosen:updated"); 
                    $("#mrgOrgSel").trigger("chosen:updated");
                }
                
                
            }
        });
        
    }
    
    //初始化角色下拉
    function initRole(){
    	var initRoleUrl = $.util.basePath + '/r/role/getAllList';
    	
    	 $.util.ajax({
             url:initRoleUrl,
             async : false, // 是否同步
             success:function(data){
                 if(!$.util.isNull(data)){
                     var optionHtml = [];
                     for(var i = 0; i < data.length;i++){
                         var item = data[i];
                         var value = item.id;
                         var text = item.name;
                         optionHtml.push(' <option value="'+value+'">'+text+'</option> ');
                     }
                     $("#parentRoleSel").html(optionHtml.join(''));
                     
                     $("#parentRoleSel").trigger("chosen:updated"); 
                 }
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
                        <h5>保存用户</h5>
                    </div>
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="saveForm" novalidate="novalidate">
                            <input type="hidden" name="id"/>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">用户名：</label>
                                <div class="col-sm-8">
                                    <input id="username" name="username" class="form-control" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">姓名：</label>
                                <div class="col-sm-8">
                                    <input id="name" name="name" class="form-control" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">所属机构：</label>
                                <div class="col-sm-8">
                                    <div id="downOrgTree">
                                        <input name="orgName" class="form-control" data-placeholder="选择所属机构..." type="text" readonly="readonly">
                                        <input id="orgId" name="orgId" class="form-control" type="hidden">
	                                    <div class="selTree hd" style="">
	                                        <ul id="orgTree" class="ztree"></ul>
	                                    </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">管辖机构：</label>
                                <div class="col-sm-8">
                                    <select id="mrgOrgSel"  data-placeholder="选择管辖机构..." style="width:350px;" class="chosen-select-deselect" multiple  tabindex="12">
                                    </select>
                                    <input name="mrgOrgIds" class="form-control" type="hidden">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">用户角色：</label>
                                <div class="col-sm-8">
                                    <input name="roleIds" class="form-control" type="hidden">
                                    <select id="parentRoleSel"  data-placeholder="选择上级菜单..." style="width:350px;" class="chosen-select-deselect" multiple  tabindex="12">
                                    </select>
                                </div>
                            </div>
                            <div class="form-group show-hidden">
                                <label class="col-sm-3 control-label">密码：</label>
                                <div class="col-sm-8">
                                    <input id="password" name="password" class="form-control" type="text" placeholder="不填，默认密码“111111”">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">备注：</label>
                                <div class="col-sm-8">
                                    <textarea name="remarks" class="form-control" ></textarea>
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