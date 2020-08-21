<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/views/resource.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
    .ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
</style>

<script type="text/javascript" src="<%=basePath%>/static/zTree/js/jquery.ztree.exedit.js"></script>

<script type="text/javascript">

    var gridTable;
    var dictTree;
    
    $(function(){
    	//初始化字典树
        initDictTree();
    	//初始化列表
    	initGridTable();
    });
    
    //初始化字典树
    function initDictTree(){
    	var initDictUrl = $.util.basePath + '/r/dict/getDictTree';
    	
    	$.util.ajax({
            url:initDictUrl,
            async : false, // 是否同步
            success:function(data){
                if($.util.isNull(data)){
                    data = [];
                }
                
                data.push({id:"0",parentId:"-1",dictName:"字典",name:"字典",open:true});

                var setting = {
            		view: {
                        addHoverDom: addHoverDom,//需要显示自定义控件的节点
                        removeHoverDom: removeHoverDom
                    },
                    data: {
                        simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "parentId"
                        },
                        key:{
                        	name: "dictName"
                        },
                        
                    },
                    edit: {
                        enable: true,
                        showRenameBtn: function(treeId, treeNode){
                        	return treeNode.id!='0';
                        },
                        showRemoveBtn: function(treeId, treeNode){
                        	return !treeNode.isParent || treeNode.id!='0';
                        },
                        drag: {
                            isCopy: false,
                            isMove: false
                        }
                    },
                    callback : {
                        onClick:function(event, treeId, treeNode){
                        	//如果有下级 ，则不做操作
                            $("#queryForm input[name=dictId]").val(treeNode.id);
                            query();
                        },
                        beforeEditName:function(treeId, treeNode){
                        	openDictDialog(treeNode.id);
                        	return false;
                        },
                        beforeRemove:function(treeId, treeNode){
                            return removeDict(treeNode.id);
                        },
                    }
                };

                dictTree = $.fn.zTree.init($("#dictTree"), setting, data);
                dictTree.expandAll(true); 
            }
        });
    }
    
    //zTree显示添加按钮
    function addHoverDom(treeId, treeNode) {
    	var sObj = $("#" + treeNode.tId + "_span");
        if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
            + "' title='添加' onfocus='this.blur();'></span>";
        sObj.after(addStr);
        var btn = $("#addBtn_"+treeNode.tId);
        if (btn) btn.bind("click", function(){
        	openDictDialog(null,treeNode.id,treeNode.name);
        	//dictTree.addNodes(treeNode, {id:(100 + 'aaa'), pId:treeNode.id, name:"new nodeaaaa "});
            return true;
        });
    };
    
    function removeHoverDom(treeId, treeNode) {
        $("#addBtn_"+treeNode.tId).unbind().remove();
    };
    
    //初始化列表
    var gridTableUrl = $.util.basePath + '/r/dict/getSubList';
    function initGridTable(){
        gridTable = $("#gridTable").bootstrapTable({
            url:  gridTableUrl,
            method: 'post',                      //请求方式（*）
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            queryParams:{},
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            clickToSelect: false,                //是否启用点击选中行
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            columns: 
            [
                {field: '', checkbox:true,formatter:function(event,value,row,index){
                }},          
                {field: 'text', title: '字典名称'},          
                {field: 'value', title: '字典值'},
                {field: 'status', title: '状态',formatter:function(event,value,row,index){
                    var status = value.status;
                    if(status=='1') return '正常';
                    else  if(status=='2')  return "冻结";
                    
                }},
                {field: 'orderNum', title: '排序号'},
                {field: 'remarks', title: '备注'},
                {field: '', title: '操作',formatter:function(event,value,row,index){
                    var html = '<button type="button" class="btn btn-outline btn-primary button-row button-handle-row" onclick="openDictSubDialog(\''+value.id+'\');" ><i class="fa"></i>编辑</button>';
                    return html;
                }}
            ],
            onLoadSuccess:function(){
                initRowHandle("gridTable");
            }
        });
    }
    
    //打开编辑界面
    function openDictDialog(id,parentId,parentName){
    	var url = "/r/dict/forwordEdit?id="+id+"&parentId="+parentId+"&parentName="+parentName;
    	
    	var width = 780;
        var height =   510;
        var title = "更新字典";
        openDialog(null, url, width, height, title);
    }
    
    function openDictSubDialog(id){
    	var dictId = $("#qy_DictId").val();
    	if($.util.isNull(dictId)){
    		swal("请选择所属字典项之后进行操作", "", "warning");
    		return;
    	}
    	var url = "/r/dict/forwordSubEdit?id="+id+"&dictId="+dictId;
    	
    	
    	var width = 780;
        var height =   510;
        var title = "更新字典明细";
        openDialog(null, url, width, height, title);
    }
    
    //添加字典之后调用
	function saveDictAfter(id, parentId, dictName) {
		//根据ID  获取nodeID
		var nodes = dictTree.getNodesByParam("id", id);
		//node为空，新增，存在修改
		if (!$.util.isNull(nodes) && nodes.length > 0) {
			nodes[0].dictName = dictName;
			dictTree.updateNode(nodes[0]);
		} else {
			var treeNodes = dictTree.getNodesByParam("id", parentId);
			dictTree.addNodes(treeNodes[0], {
				'id' : id,
				'pId' : parentId,
				'dictName' : dictName
			});
		}
	}

	function query() {
		refreshTable();
	}

	//刷新列表
	function refreshTable() {
		var paramJson = $("#queryForm").serializeJSON();
		if ($.util.isNull(paramJson.includeSubFlg)) {
			paramJson.includeSubFlg = '';
		}
		$("#gridTable").bootstrapTable('refresh', {
			url : gridTableUrl,
			query : paramJson
		});
	}
	
	//删除字典
	function removeDict(id){
		var url = $.util.basePath + '/r/dict/delete';
		//删除提示
        swal({   
            title: "确认是否删除?",
            text: "删除后的数据将无法查看",
            type: "warning",   
            showCancelButton: true,   
            confirmButtonColor: "#DD6B55",   
            confirmButtonText: "删除",   
            cancelButtonText:"取消",
            closeOnConfirm: false }, 
            function(){   
                $.util.ajax({
                    url:url,
                    data:{'id':id},
                    async : false, // 是否同步
                    success:function(data){
                        $.util.toastr.success('删除成功');
                        //删除
                        var nodes = dictTree.getNodesByParam("id", id);
                        dictTree.removeNode(nodes[0]);
                        
                        swal({
                            title : "",
                            timer : 0
                        });
                        return true;
                    }
                });
                
            }
        );
        return false;
	}
	
	//删除字典明细
	function deleteSubRows(){
		var delUrl = $.util.basePath + '/r/dict/deleteSub';
        del(delUrl,"gridTable",null,function(){
            refreshTable();
        });
	}
	
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
		    <div class="col-sm-4" >
                <div class="ibox box-div">
                    <div class="ibox-content box-content-div">
                        <ul id="dictTree" class="ztree"></ul>
                    </div>
                </div>
            </div>
			<div class="col-sm-8">
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
			                                        <label class="control-label">字典名称</label>
			                                        <input id='qy_DictId' type="hidden" name="dictId"  class="form-control">
			                                        <input type="text" name="text"  class="form-control">
			                                    </div>
			                                    <div class="form-group">
			                                        <label class="control-label">字典值</label>
			                                        <input type="text" name="value"  class="form-control">
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
						          <button class="btn btn-info button-handle" type="button" onclick="openDictSubDialog();"><i class="fa fa-plus"></i> 新增</button>
						          <button class="btn btn-danger button-handle" type="button" onclick="deleteSubRows()"><i class="fa fa-trash"></i> 删除</button>
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