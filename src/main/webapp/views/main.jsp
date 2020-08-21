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
<title>系统首页</title>

<link href="<%=basePath%>/static/hplus/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
<link href="<%=basePath%>/static/hplus/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
<link href="<%=basePath%>/static/hplus/css/animate.min.css" rel="stylesheet">
<link href="<%=basePath%>/static/hplus/css/style.min862f.css?v=4.1.0" rel="stylesheet">
<!-- Sweet Alert -->
<link href="<%=basePath%>/static/hplus/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">


<!-- ** Javascript ** -->
<script type="text/javascript" src="<%=basePath%>/static/jquery.min.js?v=2.1.4"></script>
<!-- bootstrap -->
<script type="text/javascript"src="<%=basePath%>/static/hplus/js/bootstrap.min.js?v=3.3.6"></script>
<script type="text/javascript" src="<%=basePath%>/static/hplus/js/plugins/sweetalert/sweetalert.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/lhgdialog/lhgdialog/lhgcore.lhgdialog.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/cookie/jquery.cookie.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/commons/jquery.serializejson.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/commons/jquery.util.js"></script>
<script type="text/javascript">
    $.util.basePath = '<%=basePath %>';
</script>
<script type="text/javascript" src="<%=basePath%>/static/modules/js/main.js"></script>
<!-- H+ -->
<script type="text/javascript" src="<%=basePath%>/static/hplus/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/hplus/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/hplus/js/plugins/layer/layer.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/hplus/js/hplus.min.js?v=4.1.0"></script>
<script type="text/javascript" src="<%=basePath%>/static/hplus/js/contabs.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/hplus/js/plugins/pace/pace.min.js"></script>

<script type="text/javascript">
  // var userName;
   //var name;
    var str;
   	var gridTableUrl = $.util.basePath + '/r/user/getUserDto';
   	$.ajax({
        type: "post",
        dataType: "json",
        url: gridTableUrl,
        async : true, // 是否同步
        success: function (data) {
        	var userName=data.data.userName;
        	$("#userName").html(userName);
        	 $("#name").html(data.data.name);
        }
    });
   	
    </script>
</head>


<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
    <div id="wrapper">
        <!--左侧导航开始-->
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="nav-close"><i class="fa fa-times-circle"></i>
            </div>
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="nav-header">
                        <div class="dropdown profile-element">
                            <span><img alt="image" class="img-circle" style="height:64px;position:relative;left : 20%;" src="<%=basePath %>/static/image/bltLogo.gif" /></span>
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="clear">
                               <span id="userName" class="block m-t-xs" style="position:relative;left :30%;"><strong class="font-bold" ></strong></span>
                                <span id="name" class="text-muted text-xs block" style="position:relative;left :20%;"><b class="caret"></b></span>
                                </span>
                            </a>
                            <ul class="dropdown-menu animated fadeInRight m-t-xs">
                                <li><a href="exit">安全退出</a>
                                </li>
                            </ul>
                        </div>
                        <div class="logo-element">B
                        </div>
                    </li>
                    
                </ul>
            </div>
        </nav>
        <!--左侧导航结束-->
        <!--右侧部分开始-->
        <div id="page-wrapper" class="gray-bg dashbard-1">
            <div class="row border-bottom">
                <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                    <div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                        <form role="search" class="navbar-form-custom" method="post" action="http://www.zi-han.net/theme/hplus/search_results.html">
                            <div class="form-group">
                                <input type="text" placeholder="请输入您需要查找的内容 …" class="form-control" name="top-search" id="top-search">
                            </div>
                        </form>
                    </div>
                    <ul class="nav navbar-top-links navbar-right">
                        
                    </ul>
                </nav>
            </div>
            <div class="row content-tabs">
                <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
                </button>
                <nav class="page-tabs J_menuTabs">
                    <div class="page-tabs-content">
                        <a href="javascript:;" class="active J_menuTab" data-id="index_v1.html">首页</a>
                    </div>
                </nav>
                <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
                </button>
                <div class="btn-group roll-nav roll-right">
                    <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span>

                    </button>
                    <ul role="menu" class="dropdown-menu dropdown-menu-right">
                        <li class="J_tabShowActive"><a>定位当前选项卡</a>
                        </li>
                        <li class="divider"></li>
                        <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                        </li>
                        <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                        </li>
                    </ul>
                </div>
                <a href="exit" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
            </div>
            <div class="row J_mainContent" id="content-main">
                <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="" frameborder="0" data-id="index_v1.html" seamless></iframe>
            </div>
            <div class="footer">
                <div class="pull-right">&copy; 2016 博乐通
                </div>
            </div>
        </div>
        <!--右侧部分结束-->
        <!--右侧边栏开始-->
      
        <!--右侧边栏结束-->
       
    </div>
</body>


</html>
