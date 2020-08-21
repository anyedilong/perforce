var versionType_info;
var nameStr = "";
$(function() {
    getUserInfo();
	// 初始化 列表
	initVersionType();
	initGridTable();
});

function query() {
	refreshTable();
}
// 刷新列表
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

function getUserInfo() {
    var gridTableUrl = $.util.basePath + '/r/user/getUserInfo';
    $.ajax({
        type: "post",
        dataType: "json",
        url: gridTableUrl,
        async : true, // 是否同步
        success: function (data) {
            nameStr =  data.data.name;
        }
    });
}

var gridTableUrl = $.util.basePath + '/r/vmm/getList';
function initGridTable() {
	gridTable = $("#gridTable")
			.bootstrapTable(
					{
						url : gridTableUrl,
						method : 'post', // 请求方式（*）
						striped : true, // 是否显示行间隔色
						cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
						pagination : true, // 是否显示分页（*）
						queryParams : {
							'includeSubFlg' : '1'
						},
						pageNumber : 1, // 初始化加载第一页，默认第一页
						pageSize : 10, // 每页的记录行数（*）
						clickToSelect : false, // 是否启用点击选中行
						uniqueId : "id", // 每一行的唯一标识，一般为主键列
						columns : [
									{field : '', checkbox : true, formatter : function(event, value, row, index) { }},

									{field: 'versionNum', title: '版本编号',formatter:function(event,value,row,index){
						                var versionNum = value.versionNum;
						                var html = '<a href="javascript:openShowDialog(\''+value.id+'\');"  >'+versionNum+'</a>';
						                return html;
						            }},

									{field : 'proName',title : '产品'},
									{field : 'proCode',title : '产品编码'},
									{field : 'versionType',title : '版本类型',formatter : function(event, value, row,index) {var versionType = value.versionType;if(versionType=='1'){return '全量包'}else if(versionType=='2')  {return "增量包"}}},
									{field : 'preposeVersionNum',title : '前置版本号'},
									{field : 'releaseTime',title : '发布时间'},
									{field : 'releaseUserName',title : '发布人'},
									{field : 'status',title : '状态',formatter : function(event, value, row,index) {var statusValue = value.status;if(statusValue=='1'){return '待发布'}else if(statusValue=='2')  {return "已发布"}}},
									{field : 'cz',title : '操作',
										formatter : function(event, value, row,
												index) {
											var status = value.status;
											var html = "";
                                            if("普通管理员" === nameStr){
                                                $('#gridTable').bootstrapTable('hideColumn', 'cz');
                                                $('#addBut').hide();
                                                return "";
                                            }
											//删除
                                            if(status=='1') {
                                                html = '<button handle-type="edit_row"  type="button" class="btn btn-outline btn-primary button-row button-handle-row" onclick="openSaveDialog(this,\''+value.id+'\');" ><i class="fa"></i></button>';
                                                html += '<button handle-type="del_row" type="button" class="btn btn-outline btn-primary button-row button-handle-row" onclick="deleteRows(\''+value.id+'\');" ><i class="fa"></i></button>';
                                            }
                                            return html;

										}} ],
						onLoadSuccess : function() {
							initRowHandle("gridTable");
						}
					});
}


//打开添加界面
function openSaveDialog(btnEle,id){

	var url = "/r/vmm/forwordEdit?&id="+id;
    openDialogByHandle(btnEle,url);
}

//删除数据
function deleteRows(id){
    var url = $.util.basePath + "/r/vmm/delete";
    //删除提醒
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

//版本类型
function initVersionType(){
	getDictList("versionType",function(data){
		versionType_info = data;
	});
}

//根据字典值获取text
function getVersionType(versionType){
	if($.util.isNull(versionType_info)){ //如果没有数据字典，加载数据字典
		initVersionType();
	}

	for (var i = 0; i < versionType_info.length; i++) {
		var item = versionType_info[i];
		var value = item.value;
		if(versionType==value){
			return item.text;
		}

	}

}


//打开详情界面
function openShowDialog(id){
	 var url = "/r/vmm/forwordShow?id="+id;
     var width = 1000;
     var height =   800;
     var title = "产品详情";

     openDialog(null, url, width, height, title);
}

