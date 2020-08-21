<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/views/resource.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">
    

    var menuTree;

    $(function(){
        var id = $.util.getUrlParam('id');
        if($.util.isNull(id)){
        	swal("授权操作错误", "", "error");
        	parent.closeDialog(menuId);
        }
    	//初始化菜单
    	initMenuTree();
    });
    
    function initMenuTree(){
        var intiMenuUrl = $.util.basePath + '/r/menu/getUserTree';
        $.util.ajax({
            url:intiMenuUrl,
            async : true, // 是否同步
            success:function(data){
                if($.util.isNull(data)){
                    data = [];
                }
                var setting = {
                	check: {
                        enable: true,
                        chkboxType: { "Y": "s", "N": "s" }
                    },
                    data: {
                        simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "parentId"
                        },
                        key:{
                            url: "xUrl",
                            icon: "icon1"
                        }
                    },
                    callback: {
                    	beforeCheck: function zTreeBeforeCheck(treeId, treeNode) {
                            //取消上级节点选中
                            var bl = true;
                            var childeNode = treeNode;
                            while(bl){
                            	childeNode = childeNode.getParentNode();
                            	if(!$.util.isNull(childeNode)){
                            		childeNode.checked = false;
                            	}else{
                            	    bl = false;
                            	}
                            }
                        }
                    }
                };

                menuTree = $.fn.zTree.init($("#menuTree"), setting, data);
                initData($.util.getUrlParam('id'))
            }
        });
    }
    
    function initData(id){
    	var showAuthUrl = $.util.basePath + '/r/role/showAuthorize';
    	$.util.ajax({
            url:showAuthUrl,
            data:{'id':id},
            async : true, // 是否同步
            success:function(data){
            	if(!$.util.isNull(data)){
            		for(var i = 0 ; i < data.length ; i++){
            			var node = menuTree.getNodesByParam("id", data[i], null);
                        if(!$.util.isNull(node)){
                        	//menuTree.selectNode(node[0]);
                        	node[0].checked = true;
                        	menuTree.updateNode(node[0]); 
                        }
            		}
            	}
            }
        });
    	
    }
    
    function saveForm(){
    	var id = $.util.getUrlParam('id');
        if($.util.isNull(id)){
            swal("授权操作错误", "", "error");
            parent.closeDialog(menuId);
        }
        
    	var menuArray = [];
    	var nodes = menuTree.getCheckedNodes(true);
    	if(!$.util.isNull(nodes)){
    		for(var i=0;i <nodes.length; i++ ){
    			var nodeItem = nodes[i];
    			menuArray.push(nodeItem.id);
    		}
    	}
    	
    	var saveAuthorizeUrl = $.util.basePath + '/r/role/authorize';
    	$.util.ajax({
            url:saveAuthorizeUrl,
            data:{'id':id,'menuIds':menuArray},
            success:function(data){
                parent.$.util.toastr.success('授权成功');
                parent.closeDialog(menuId);
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
                        <h5>${roleName }角色授权</h5>
                    </div>
                    <div class="ibox-content" style="height:413px;overflow: auto;">
                        <ul id="menuTree" class="ztree"></ul>
                    </div>
		            <div class="ibox-content text-center">
		                <button class="btn btn-info" type="button" onclick="saveForm()"><i class="fa fa-save"></i> 授权</button>
		            </div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>