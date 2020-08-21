<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/views/resource.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=basePath%>/static/zTree/js/jquery.ztree.exedit.js"></script>

<script type="text/javascript">

  
	//更新系统缓存
	function updateSysCache() {
		var url = $.util.basePath + '/cache/updateSysCache';
		var title = "更新系统缓存";
		updateCache(url, title);
	}

	//更新用户缓存
	function updateUserCache() {
		var url = $.util.basePath + '/cache/updateUserCache';
		var title = "更新用户缓存";
		updateCache(url, title);
	}
	
	//更新字典缓存
	function updateDictCache() {
		var url = $.util.basePath + '/cache/updateDictCache';
		var title = "更新字典缓存";
		updateCache(url, title);
	}

	//更新产品类型缓存
	function updateProTypeCache() {
		var url = $.util.basePath + '/cache/updateProTypeCache';
		var title = "更新产品类型缓存";
		updateCache(url, title);
	}

	function updateCache(url, title) {
		//删除提示
        swal({   
            title: "确认"+title+"?",
            text: "",
            type: "warning",   
            showCancelButton: true,   
            confirmButtonColor: "#DD6B55",   
            confirmButtonText: "更新",   
            cancelButtonText:"取消",
            closeOnConfirm: false }, 
            function(){   
                $.util.ajax({
                    url:url,
                    async : false, // 是否同步
                    success:function(data){
                        $.util.toastr.success('更新成功');
                        swal({
                            title : "",
                            timer : 0
                        });
                    }
                });
                
            }
        );
	}
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm-5">
				<div class="ibox box-div">
                    <div class="ibox-content">
                        <button role="button" onclick="updateSysCache();" class="btn btn-primary btn-block btn-lg">更新系统全部缓存</button>
                        <button role="button" onclick="updateUserCache();" class="btn btn-primary btn-block btn-lg">更新用户缓存</button>
                        <button role="button" onclick="updateDictCache();" class="btn btn-primary btn-block btn-lg">更新字典缓存</button>
                        <button role="button" onclick="updateProTypeCache();" class="btn btn-primary btn-block btn-lg">更新产品类型缓存</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>