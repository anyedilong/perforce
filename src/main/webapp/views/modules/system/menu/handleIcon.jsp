<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="/views/resource.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">
    
    $(function(){
    	//
    	
    });
    
    function clickIcon(eve){
    	var icon = $(eve).attr("data-icon");
    	$("#iconDiv .fa-check-circle").addClass("hd");
    	$(eve).find(".fa-check-circle").removeClass("hd");
    	
    	$("#iconPath").val(icon);
    }
    
    function checkIcon(){
    	var icon = $("#iconPath").val();
    	parent.iconDialogClose(icon);
    }
    
</script>
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
    <input type="hidden" id="iconPath">
        <div class="row">
            <div class="col-sm">
                <div class="ibox float-e-margins">
                    <div id="iconDiv" class="ibox-content">
                          <a data-icon="fa-plus" class="icon-a" onclick="clickIcon(this);">
                              <img alt="" src="">
                              <i class="fa fa-plus faIcon" style="right: 22px; bottom: -25px; font-size: 60px;"></i>
                              <i class="fa fa-check-circle faCheck hd"></i>
                          </a>  
                          <a data-icon="fa-paste" class="icon-a" onclick="clickIcon(this);">
                              <img alt="" src="">
                              <i class="fa fa-paste faIcon" style="right: 15px; bottom: -25px; font-size: 60px;"></i>
                              <i class="fa fa-check-circle faCheck hd"></i>
                          </a>  
                          <a data-icon="fa-trash" class="icon-a" onclick="clickIcon(this);">
                              <img alt="" src="">
                              <i class="fa fa-trash faIcon" style="right: 22px; bottom: -25px; font-size: 60px;"></i>
                              <i class="fa fa-check-circle faCheck hd"></i>
                          </a>  
                          <a data-icon="fa-file-excel-o" class="icon-a" onclick="clickIcon(this);">
                              <img alt="" src="">
                              <i class="fa fa-file-excel-o faIcon" style="right: 20px; bottom: -25px; font-size: 60px;"></i>
                              <i class="fa fa-check-circle faCheck hd"></i>
                          </a>  
                          <a data-icon="fa-print" class="icon-a" onclick="clickIcon(this);">
                              <img alt="" src="">
                              <i class="fa fa-print faIcon" style="right: 15px; bottom: -25px; font-size: 60px;"></i>
                              <i class="fa fa-check-circle faCheck hd"></i>
                          </a>  
                         
                    </div>
		            <div class="ibox-content text-center" style="padding:10px 0 0 0;">
		                <button class="btn btn-info" type="button" onclick="checkIcon()"><i class="fa fa-check"></i> 确定</button>
		            </div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>