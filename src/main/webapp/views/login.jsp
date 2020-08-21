<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="<%=basePath%>/static/hplus/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
<link href="<%=basePath%>/static/hplus/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
<link href="<%=basePath%>/static/hplus/css/plugins/iCheck/custom.css" rel="stylesheet">
<link href="<%=basePath%>/static/hplus/css/animate.min.css" rel="stylesheet">
<link href="<%=basePath%>/static/hplus/css/style.min862f.css?v=4.1.0" rel="stylesheet">

<script>if(window.top !== window.self){ window.top.location = window.location;}</script>

<script type="text/javascript" src="<%=basePath%>/static/jquery.min.js?v=2.1.4"></script>
<!-- bootstrap -->
<script type="text/javascript"src="<%=basePath%>/static/hplus/js/bootstrap.min.js?v=3.3.6"></script>
<script type="text/javascript" src="<%=basePath%>/static/cookie/jquery.cookie.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/commons/jquery.serializejson.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/hplus/js/plugins/iCheck/icheck.min.js"></script>
<script src="<%=basePath%>/static/hplus/js/plugins/validate/jquery.validate.min.js"></script>
<script src="<%=basePath%>/static/hplus/js/plugins/validate/messages_zh.min.js"></script>
<script type="text/javascript">

	$.validator.setDefaults({
	    highlight: function(e) {
	        $(e).closest(".form-group").removeClass("has-success").addClass("has-error")
	    },
	    success: function(e) {
	        e.closest(".form-group").removeClass("has-error").addClass("has-success")
	    },
	    errorElement: "span",
	    errorPlacement: function(e, r) {
	        e.appendTo(r.is(":radio") || r.is(":checkbox") ? r.parent().parent().parent() : r.parent())
	    },
	    errorClass: "help-block m-b-none tl",
	    validClass: "help-block m-b-none tl"
	});
	
    $(function(){
    	$("input[name=username]").val($.cookie('userunick'));
    	$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})
    	initValidate();
    });
    
	function login(){
		if($("#formlogin").valid()){
			var json = $('#formlogin').serializeJSON(); 
			var loginUrl = '<%=basePath %>/login/check';
			var mainUrl = '<%=basePath %>/main';
			var e = "<i class='fa fa-times-circle'></i> ";
			$.ajax({    
			     type:'post',    
			     url:loginUrl,    
			     data:json,    
			     cache:false,  
			     async: false,  
			     dataType:"json",
			     success:function(data){
			    	 
			     	 if(data.result.retCode == 0){
			     		 location.href = mainUrl;
			     	 }else{
			     		 var errorMsg = data.result.retMsg;
			     		 $("#msg-error").html(e+errorMsg);
			     	 }
			     	 
			     },    
			     error:function(){}    
			}); 
		}
	}
	
	function initValidate(){
		var e = "<i class='fa fa-times-circle'></i> ";
		$("#formlogin").validate({
			rules: {
				username: "required",
				password: "required"
	            },
            messages: {
            	username: e + "请输入用户名",
            	password: e + "请输入密码"
                }
		});
	}
	
</script>
<title>系统登录</title>
</head>
<body class="gray-bg" style="overflow: hidden;">
	<h1>登录界面  </h1>
	
	 <div class="middle-box text-center loginscreen  animated fadeInDown">
	       <form class="m-t" role="form" id="formlogin"  action="" method="post" onsubmit="return false;">
	            <span  id="msg-error" style="display: block;text-align:right;color: #a94442;"></span>
                <div class="form-group">
                    <input type="text" name="username" class="form-control" placeholder="用户名" required="">
                </div>
                <div class="form-group">
                    <input type="password" name="password" class="form-control" placeholder="密码" required="">
                </div>
                <div class="form-group">
                    <div style="float: right;padding:0 5px 10px 5px;">
                                       <label>
                                            <input name="autologin" type="checkbox" class="i-checks">自动登录</label>
                    </div>
                </div>
                <button type="button" onclick="login()"  class="btn btn-primary block full-width m-b">登 录</button>


            </form>
	</div>
</body>
</html>