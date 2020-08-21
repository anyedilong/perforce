<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	String path = request.getContextPath();
%>

<!-- 公共资源CSS,JS  -->
<!--Css -->
<%-- <link type="text/css" href="<%=basePath%>/static/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet"> --%>
<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
<![endif]-->

<link href="<%=basePath%>/static/chosen/chosen.min.css" rel="stylesheet">
<link href="<%=basePath%>/static/hplus/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
<link href="<%=basePath%>/static/hplus/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
<link href="<%=basePath%>/static/hplus/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
<link href="<%=basePath%>/static/hplus/css/plugins/iCheck/custom.css" rel="stylesheet">
<link href="<%=basePath%>/static/hplus/css/animate.min.css" rel="stylesheet">
<link href="<%=basePath%>/static/hplus/css/style.min862f.css?v=4.1.0" rel="stylesheet">
<!-- Sweet Alert -->
<link href="<%=basePath%>/static/hplus/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<link href="<%=basePath%>/static/hplus/css/plugins/toastr/toastr.min.css" rel="stylesheet">
<!-- Ztree -->
<link rel="stylesheet" href="<%=basePath%>/static/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/static/commons/css/style.main.css" type="text/css">
<!-- 时间插件 -->
<link href="<%=basePath%>/static/hplus/js/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<link href="<%=basePath%>/static/hplus/js/plugins/bootstrap-table/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet">

 <!-- ** Javascript ** -->
<script type="text/javascript" src="<%=basePath%>/static/jquery.min.js?v=2.1.4"></script>
<script type="text/javascript" src="<%=basePath%>/static/chosen/chosen.jquery.min.js"></script>
<!-- bootstrap -->
<script type="text/javascript"src="<%=basePath%>/static/hplus/js/bootstrap.min.js?v=3.3.6"></script>
<script type="text/javascript" src="<%=basePath%>/static/hplus/js/plugins/iCheck/icheck.min.js"></script>

<!-- H+
<script type="text/javascript" src="<%=basePath%>/static/hplus/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/hplus/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/hplus/js/plugins/layer/layer.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/hplus/js/hplus.min.js?v=4.1.0"></script>
<script type="text/javascript" src="<%=basePath%>/static/hplus/js/contabs.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/hplus/js/plugins/pace/pace.min.js"></script>
 -->
<script src="<%=basePath%>/static/hplus/js/plugins/validate/jquery.validate.min.js"></script>
<script src="<%=basePath%>/static/hplus/js/plugins/validate/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/zTree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/zTree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/hplus/js/plugins/toastr/toastr.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/hplus/js/plugins/sweetalert/sweetalert.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/lhgdialog/lhgdialog/lhgcore.lhgdialog.min.js?self=true"></script>
<script type="text/javascript" src="<%=basePath%>/static/cookie/jquery.cookie.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/commons/jquery.serializejson.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/commons/jquery.util.js"></script>
<script type="text/javascript">
    $.util.basePath = '<%=basePath %>';
</script>
<script type="text/javascript" src="<%=basePath%>/static/commons/base.js"></script>
<script src="<%=basePath%>/static/hplus/js/plugins/bootstrap-table/bootstrap-table.js"></script>
<script src="<%=basePath%>/static/hplus/js/plugins/bootstrap-table/bootstrap3-editable/js/bootstrap-editable.js"></script>
<!-- 时间插件 -->
<script src="<%=basePath%>/static/hplus/js/plugins/bootstrap-datetimepicker/moment.min.js"></script>
<script src="<%=basePath%>/static/hplus/js/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath%>/static/hplus/js/plugins/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
