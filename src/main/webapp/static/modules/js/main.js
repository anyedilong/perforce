$(function(){
	//swal("服务器内部错误", "1213123", "error");
	initMenu();
}) ;

//打开登录窗口
var loginDialog;
function openLoginDialog(){
	var loginModalUrl = $.util.basePath+'/loginModal';
	loginDialog = $.dialog({width:450,height:300,title: '登录',lock: true,content: 'url:'+loginModalUrl});
};
function loginDialogSuccess(){
	loginDialog.close();
	swal({
		title : "登录成功",
		text : "1秒后自动关闭",
		timer : 1000,
		showConfirmButton : false
	});
	//$.dialog.tips('登录成功',1.5,"success.gif");
};

//初始化菜单
function initMenu(){
	//
	var initMenuUrl = $.util.basePath+'/r/menu/getUserMenu';
	
	$.util.ajax({
		url:initMenuUrl,
		async : false, // 是否同步
		success:function(data){
			//拼接菜单
			var menuHtml = [];
			var level = 1;
			getMenuHtml(menuHtml,data,level);
			$("#side-menu").append(menuHtml.join(''));
		}
	});
}

//获取菜单html
function getMenuHtml(menuHtml,menuData,level){
	if(!$.util.isNull(menuData)){
		//遍历
		$.each(menuData, function (i, menuItem) {
		    //菜单ID
		    var menuId = menuItem.id;
		    //菜单类型   1菜单 2 操作
		    var menuType = menuItem.type;
		    //菜单名称
		    var menuName = menuItem.name;
		    //菜单等级
		    //var menuLevel = menuItem.menuLevel;
		    
		    //菜单路径
		    var menuUrl = "#";
		    if(!$.util.isNull(menuItem.url)){
		    	menuUrl = $.util.basePath+menuItem.url;
		    	if(menuUrl.indexOf("?") >= 0){
		    		menuUrl += "&menuId="+menuId ;
		    	}else{
		    		menuUrl += "?menuId="+menuId ;
		    	}
		    }
		    //菜单图标
		    var menuIcon = $.util.basePath+'/static/image/menuIcon/'+menuItem.icon;
		    
		    if("1" == menuType){
    			//验证是否有子集
    			var childrenItems = menuItem.children;
    			var childrenMenuSize = menuItem.chilerenMenuSize;
    			if(!$.util.isNull(childrenItems) && childrenItems.length > 0 && childrenMenuSize > 0){
    				menuHtml.push(' <li> ');
                    menuHtml.push('     <a class="" href="'+menuUrl+'"> ');
                    if(!$.util.isNull(menuItem.icon)){
                    	menuHtml.push('         <i class="fa"> ');
                    	menuHtml.push('             <img style="height:14px;" src="'+menuIcon+'"> ');
                    	menuHtml.push('         </i> ');
                    }
                    menuHtml.push('         <span class="nav-label"> ');
                    menuHtml.push(menuName);
                    menuHtml.push('         </span> ');
                    menuHtml.push('         <span class="fa arrow"></span> ');
                    menuHtml.push('     </a> ');

                    if(level == 1){
                        menuHtml.push('     <ul class="nav nav-second-level">  ');
                    	
                    }else if(level == 2){
                        menuHtml.push('     <ul class="nav nav-third-level">  ');
                    }
                    
                    getMenuHtml(menuHtml,childrenItems,level+1)
                    menuHtml.push('     </ul> ');
                    menuHtml.push(' </li> ');
    			}else{
    				menuHtml.push(' <li> ');
    				menuHtml.push('     <a class="J_menuItem" href="'+menuUrl+'"> ');
    				if(!$.util.isNull(menuItem.icon)){
                    	menuHtml.push('         <i class="fa"> ');
                    	menuHtml.push('             <img style="height:14px;" src="'+menuIcon+'"> ');
                    	menuHtml.push('         </i> ');
                    }
    				menuHtml.push('         <span class="nav-label"> ');
    				menuHtml.push(menuName);
    				menuHtml.push('         </span> ');
    				menuHtml.push('     </a> ');
    				menuHtml.push(' </li> ');
    			}
		    }
		});
	}
}