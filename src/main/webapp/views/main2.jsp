<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/views/resource.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统首页</title>

<style type="text/css">
div {
	overflow: hidden;
	padding: 0px;
	margin: 0px;
	border: opx;
}

body {
	overflow: hidden;
	padding: 0px;
	margin: 0px;
	border: opx;
}

.topbar-nvl {
	height: 50px;
	width: 100%;
}

.topbar-nvl .topbar-nvl-info {
	border-bottom: 1px solid #eee;
	width: 100%;
	height: 100%;
}

.footer-nvl {
	height: 20px;
	/* position: absolute; */
	z-index: 3;
	/* bottom: 5px; */
}

.footer-nvl .footer-nvl-info {
	border-top: 1px solid #eee;
	width: 100%;
	height: 100%;
}

.body-menu-nvl {
	float: left;
	width: 200px;
}

.body-menu-nvl .body-menu-nvl-info {
	border-right: 1px solid #eee;
}

.body-content-nvl-info {
	
}

.body-content {
	margin: 10px;
	padding: 1px solid #eee;
}
</style>
<script type="text/javascript">
	$(function() {
		//设置窗口界面布局大小
		setWin();
		//当窗口大小改变的时候设置界面
		$(window).resize(function() {
			setWin();
		});
		initContent();
	});

	//设置窗口界面布局大小
	function setWin() {

		var widthWin = $("html").width();//获取当前窗口的宽度
		var heightWin = $("html").height();//获取当前窗口的高度
		if (widthWin <= 960) {
			$("body").eq(0).css("overflow-x", "auto");
			widthWin = 960;
		} else {
			$("body").eq(0).css("overflow-x", "hidden");
		}
		$(".body").width(widthWin)

		if (heightWin <= 600) {
			$("body").eq(0).css("overflow-y", "auto");
			heightWin = 600;
		} else {
			$("body").eq(0).css("overflow-y", "hidden");
		}
		$(".body").height(heightWin)

		//获取头高度
		var topH = $(".topbar-nvl").height();
		//获取底部高度
		var footerH = $(".footer-nvl").height();
		//设置内容高度
		var bodyH = heightWin - (topH + footerH + 5);
		$(".body-nvl").height(bodyH);
		$(".body-menu-nvl").height(bodyH);
		$(".body-content-nvl").height(bodyH);
		$(".body-menu-nvl-info").height("100%");

		//设置内容宽度
		var menuW = $(".body-menu-nvl").width();
		$(".body-content-nvl").width(widthWin - menuW);

	}
	
	//初始化菜单
	function initContent(){
		var contentTag = $("#openMenuBar").closableTag({
			contentEle:'openContent',
			tabItem:[{
				id:'1',//标签唯一标志ID
		        name:'百度aaaaaaaaaaaaaaaaaaaaaa',//标签名称
		        content:"你好百度",
		        //url:'http://www.baidu.com',//地址
		        closable:false//是否可以关闭
			 },
			 {
                id:'2',//标签唯一标志ID
                name:'购物111111111111111111',//标签名称
                children:[{
                	id:'3',//标签唯一标志ID
                    name:'淘宝aaaaaaaaaaaaaaaaaa',//标签名称
                    content:"你好淘宝",
                    //url:'http://www.taobao.com',//地址
                    closable:true//是否可以关闭
                }]
             },
             {
                 id:'4',//标签唯一标志ID
                 name:'京东aaaaaaaaaaaaa',//标签名称
                 content:"你好京东",
                 //url:'http://www.taobao.com',//地址
                 closable:false//是否可以关闭
              }
			]
		})
	}
	
</script>
</head>
<body>
	<div class="body">
		<!-- 头 -->
		<div class="topbar-nvl">
			<div class="topbar-nvl-info">top</div>
		</div>
		<!-- 内容 -->
		<div class="body-nvl">
			<!-- 左侧菜单 -->
			<div class="body-menu-nvl">
				<div class="body-menu-nvl-info">左侧菜单</div>
			</div>
			<!-- 右侧 -->
			<div class="body-content-nvl">
				<div class="body-content-nvl-info">
					<div class="body-content">
					
						<!-- 此处是相关代码 -->
					    <div class="content-bar" style="width: 300px;" >
					       <div style="width: 500px;">
							<ul  id="openMenuBar" style="width: 500px;" class="nav nav-tabs" role="tablist">
							</ul>
							</div>
						</div>
						<!-- 相关代码结束 -->
						<div class="content-main" style="height: 500px;">
                            <div id="openContent" class="tab-content" style="width:100%;">
                            </div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 底部 -->
		<div class="footer-nvl">
			<div class="footer-nvl-info">footer</div>
		</div>
	</div>
</body>
</body>
</html>