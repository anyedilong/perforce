var gridTable;
    
$(function(){
	//初始化列表
	initGridTable();
	//初始化产品分类树
	initProTypeTree();
	setTimeout('$("#downProTypeTree").downZtree()', 100);
	//初始化下拉框
	initProSlt();
});
//初始化设备类型下拉框
function initDeviceType(){
	getDictList("dictDeviceType",function(data){
		var optionHtml = [];
		optionHtml.push(' <option value="">全部</option> ');
		//下拉框数据绑定
		if(!$.util.isNull(data)){
			for(var i = 0; i < data.length;i++){
				 var item = data[i];
				 optionHtml.push(' <option value="'+item.value+'">'+item.text+'</option> ');
			}
		}
		$("#statusSel").html(optionHtml.join(''));
		$("#statusSel").trigger("chosen:updated"); 
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
                         $("#queryForm input[name=proType]").val(treeNode.id);
                         $("#queryForm input[name=proTypeName]").val(treeNode.typeName);
                         $("#downProTypeTree .selTree").addClass("hd");
                         initProSlt();
                    }
                }
            };

            proTypeTree = $.fn.zTree.init($("#proTypeTree"), setting, data);
            proTypeTree.expandAll(true); 
            
        }
    });
}

//初始化数据校验
function initValidate(){
	var e = "<i class='fa fa-times-circle'></i> ";
   $("#queryForm").validate({
       rules: {
       	proTypeName: "required",
       	productSlt: "required",
       	proNum: "required"
           },
       messages: {
       	proTypeName: e + "请选择产品分类",
       	productSlt: e + "请选择产品",
       	proNum: e + "请输入库存编号"
           }
   });
}
//初始化产品下拉框
function initProSlt(){
	var optionHtml = [];
	//获取产品类型
	var proType = $("#queryForm input[name=proType]").val();
	var proSltUrl = $.util.basePath + '/r/product/getProductSlt';
	
	if(!$.util.isNull(proType)){
		$.util.ajax({
			url : proSltUrl,
			data :{"proType":proType},
			async : false, // 是否同步
			success : function(data) {
				if (!$.util.isNull(data)) {
					for (var i = 0; i < data.length; i++) {
						var item = data[i];
						optionHtml.push(' <option value="'+item.id+'">' + item.proName+ '</option> ');
					}
				}
			}
		});
	}
	$("#productSlt").html(optionHtml.join(''));
	$("#productSlt").trigger("chosen:updated");
} 
//初始化列表
var gridTableUrl = $.util.basePath + '/r/product/getList';
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
            {field: 'proName', title: '产品名称',formatter:function(event,value,row,index){
                var proName = value.proName;
                var html = '<a href="javascript:openShowDialog(\''+value.id+'\');"  >'+proName+'</a>';
                return html;
            }},         
            {field: 'proCode', title: '产品编码'},
            {field: 'proTypeName', title: '产品分类'},
            //{field: 'proApprovalTime', title: '立项时间'},
            //{field: 'proFinalTime', title: '定型时间'},
            //{field: 'proPutTime', title: '投产时间'},
            {field: 'proSoftwareWarranty', title: '质保时间（月）'},
            {field: 'proStork', title: '产品库存',formatter:function(event,value,row,index){
                var proStork = value.proStork;
                var html = '<a href="#" data-name="proStork" data-id="'+value.id+'">'+proStork+'</a>';
                return html;
            }}, 
            {field: 'releaseTime', title: '发布时间'},
            {field: 'releaseUserName', title: '发布人'},
            {field: 'createTime', title: '创建时间'},
            {field: 'createUserName', title: '创建人'},
            {field: 'updateTime', title: '更新时间'},
            {field: 'updateUserName', title: '更新人'},
            {field: 'status', title: '状态',formatter:function(event,value,row,index){
                var status = value.status;
                if(status=='1') return '待发布';
                else  if(status=='2')  return "已发布"
                else  if(status=='3')  return "撤销发布";
            }}, 
            {field: '', title: '操作',formatter:function(event,value,row,index){
                var html = '';
                var status = value.status;
                //编辑
                if(status=='1' || status=='3' ) {
                	html += '<button handle-type="edit_row" type="button" class="btn btn-outline btn-primary button-row button-handle-row" onclick="openSaveDialog(this,\''+value.id+'\');" ><i class="fa"></i></button>';
                }
                //发布
                if(status=='1' || status=='3'){
                	html += '<button handle-type="release_row" type="button" class="btn btn-outline btn-primary button-row button-handle-row" onclick="release(\''+value.id+'\');" ><i class="fa"></i></button>';
                }
                //撤销发布
                if(status=='2'){
                    html += '<button handle-type="revoke_release_row" type="button" class="btn btn-outline btn-primary button-row button-handle-row" onclick="revokeRelease(\''+value.id+'\');" ><i class="fa"></i></button>';
                }
                //删除 
                if(status=='1') {
                    html += '<button handle-type="del_row" type="button" class="btn btn-outline btn-primary button-row button-handle-row" onclick="deleteRows(\''+value.id+'\');" ><i class="fa"></i></button>';
                }
                
                return html;
            }}
        ],
        onLoadSuccess:function(){
            initRowHandle("gridTable");
            //库存修改
            $('#gridTable a[data-name=proStork]').editable({
                type: 'number',
                title: '修改库存',
                url: function (params) {
                	var d = new $.Deferred;
                	var rowId = $(this).attr("data-id");
                	var storkNew = params.value;
                	var updateStorkUrl = $.util.basePath+'/r/product/updateStork';
                	//修改库存
                    $.util.ajax({
                        url:updateStorkUrl,
                        data:{"id":rowId,"proStork":storkNew},
                        async : false, // 是否同步
                        success:function(data){
                            $.util.toastr.success('修改成功');
                            d.resolve();
                        },
                        error:function(){
                        	return d.reject('修改失败');
                        }
                    });
                }
            
            });
        }
    });
    

}

//打开产品保存界面
function openSaveDialog(btnEle,id){
    var url = "/r/product/forwordEdit?id="+id;
    openDialogByHandle(btnEle,url);
}
//发布
function release(id){
	var url = $.util.basePath + "/r/product/release";
	//发布提示
    swal({   
        title: "确认发布?",
        text: "发布之后不能进行修改",
        type: "warning",   
        showCancelButton: true,   
        confirmButtonColor: "#DD6B55",   
        confirmButtonText: "发布",   
        cancelButtonText:"取消",
        closeOnConfirm: false }, 
        function(){   
            $.util.ajax({
                url:url,
                data:{"id":id},
                async : false, // 是否同步
                success:function(data){
                    $.util.toastr.success('发布成功');
                    query();
                    swal({
                        title : "",
                        timer : 0
                    });
                }
            });
            
        }
    );
}

//撤销发布
function revokeRelease(id){
	var url = $.util.basePath + "/r/product/revokeRelease";
	//发布提示
    swal({   
        title: "确认撤销发布?",
        text: "撤销发布之后不能进行出入库操作",
        type: "warning",   
        showCancelButton: true,   
        confirmButtonColor: "#DD6B55",   
        confirmButtonText: "撤销发布",   
        cancelButtonText:"取消",
        closeOnConfirm: false }, 
        function(){   
            $.util.ajax({
                url:url,
                data:{"id":id},
                async : false, // 是否同步
                success:function(data){
                    $.util.toastr.success('撤销成功');
                    query();
                    swal({
                        title : "",
                        timer : 0
                    });
                }
            });
            
        }
    );
}

//删除数据
function deleteRows(id){
    var url = $.util.basePath + "/r/product/delete";
    //发布提示
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
                url:url,
                data:{"id":id},
                async : false, // 是否同步
                success:function(data){
                    $.util.toastr.success('删除成功');
                    query();
                    swal({
                        title : "",
                        timer : 0
                    });
                }
            });
            
        }
    );
    
}

function query(){
    refreshTable();
}
//刷新列表
function refreshTable() {
    var paramJson = $("#queryForm").serializeJSON();
    if($.util.isNull(paramJson.includeSubFlg)){
        paramJson.includeSubFlg='';
    }
    $("#gridTable").bootstrapTable('refresh', {
        url : gridTableUrl,
        query : paramJson
    });
}

//打开详情界面
function openShowDialog(id){
	 var url = "/r/product/forwordShow?id="+id;
     var width = 1000;
     var height =   750;
     var title = "产品详情";
     
     openDialog(null, url, width, height, title);
}