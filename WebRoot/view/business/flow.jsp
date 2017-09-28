<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%String path = request.getContextPath();%>
<%String processId = request.getParameter("proId");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<title>业务管理-环节管理</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<link type="text/css" rel="stylesheet" href="<%=path%>/style/css/style.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqueryui/jquery-ui.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqgrid/css/ui.jqgrid.css"/>
<style type="text/css">

</style>
<script type="text/javascript">

var processId = <%=processId%>;
$(function(){
	//给form表单赋缺省值
	$("#processId").val(processId);
	queryFlow();
	//查询业务下的环节
	//InitNodeTable();
	
});


//查询流程环节
function queryFlow(){
	$.ajax( {   
		type : 'post',
		data : {processId:processId},
		dataType : 'json',
		url : '<%=path%>/business/asyn/getflowDate',
		beforeSubmit : function() {
			tips = layer.msg("正在加载数据...",{icon : 16,time : 0, shade: [0.1]});
		},
		success : function(result) {
			var flowDate =result.flowDate;
			var level = 1;
			//$("#flowDiv").append("<span>-->开始</span>");
			for(var i = 0 ;i <flowDate.length;i++){
				var nodeInfo = flowDate[i];
				var nodeInfoNext = null ;
				if(i+2<=flowDate.length){ //防止数组越界
					nodeInfoNext = flowDate[i+1];
				}else{
					nodeInfoNext = null ;
				}
				var nodeInfo = flowDate[i];
				//获取afterNodeId
				var afterNodeId = nodeInfo.afterNodeId;
				var arr = afterNodeId.split(',');
				
				//判断状态颜色
				var backColor = "background-image:url(<%=path%>/style/image/flowImg/nodeStatus"+nodeInfo.nodeStatus+".png);background-size:100%;no-repeat";
				var nextIcon= "background-image:url(<%=path%>/style/image/flowImg/nextIcon.png);";
				if(nodeInfo.orHelp==0){ //无关联
					if(nodeInfoNext == null ){
						$("#flowDiv").append("<div id='flow"+nodeInfo.nodeId+"' style='height:39px;width:150px; float:left;margin-top:-15px;text-align:center;line-height: 39px; white-space: nowrap;overflow: hidden;text-overflow: ellipsis;"+backColor+"'>"+nodeInfo.nodeName+"</div>");
					}else{
						$("#flowDiv").append("<div id='flow"+nodeInfo.nodeId+"' style='height:39px;width:150px; float:left;margin-top:-15px;text-align:center;line-height: 39px; white-space: nowrap;overflow: hidden;text-overflow: ellipsis;"+backColor+"'>"+nodeInfo.nodeName+"</div><div style = 'float:left; height:17px;margin-top:-7px;"+nextIcon+"'>&nbsp;</div>");
					}
					
				}else if(nodeInfo.orHelp==1){ //有关联
					if(i==flowDate.length-1){
						$("#flowDiv").append("<div id='flow"+nodeInfo.nodeId+"' style='height:39px;width:150px; float:left; margin-top:-15px;text-align:center; line-height: 39px; white-space: nowrap;overflow: hidden;text-overflow: ellipsis;"+backColor+"'>"+nodeInfo.nodeName+"</div>");
					}else{
						if(nodeInfo.afterNodeId == nodeInfoNext.afterNodeId){
							//alert("nodeInfo.afterNodeId:"+nodeInfo.afterNodeId);
							//alert("nodeInfoNext.afterNodeId:"+nodeInfoNext.afterNodeId);
							//$("#flowDiv").append("<div id='flow"+nodeInfo.nodeId+"' style='height:16px; float:left; margin-top:-15px;"+backColor+"'>"+nodeInfo.nodeName+"</div></br>");
							$("#flowDiv").append("<div id='flow"+nodeInfo.nodeId+"' style='height:39px;width:150px; float:left; margin-top:-15px;text-align:center;line-height: 39px; white-space: nowrap;overflow: hidden;text-overflow: ellipsis;"+backColor+"'>"+nodeInfo.nodeName+"</div>");
						}else{
							$("#flowDiv").append("<div id='flow"+nodeInfo.nodeId+"' onmouseover='display();' style='height:39px;width:150px; float:left;margin-top:-15px;text-align:center;line-height: 39px; white-space: nowrap;overflow: hidden;text-overflow: ellipsis;"+backColor+"'>"+nodeInfo.nodeName+"</div><div style = 'float:left;height:17px;margin-top:-7px;"+nextIcon+"'>&nbsp;</div>");
						}
					}
					
					
					
				}else{
					alert("环节关联有问题"+nodeInfo.id);
				}
				
			}
			//$("#flowDiv").append("<span>-->结束</span>");
			
		},
		error : function(result) {
			alert("亲!您的网络不给力哦~");
			closeFrame();
		}
	});
	
}

function display(){
	parent.$.showMsg("环节名称",1);
}





function autoSub(obj){
    if(/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/.test(obj.value)){
    	searchfb();
    }
    if(obj.value==""){
    	searchfb();
    }
}



function closeFrame(){ //点击关闭按钮,关闭当前页面,并刷新父级页面
	//window.parent.thisGrid.trigger("reloadGrid");
	window.parent.layer.close(window.parent.queryBusi);
}
</script>
</head>
<body style="min-width:1100px;overflow-y: hidden;" >
	<div id="orgPageRecover" style="width:95%;margin:25px 0 15px 20px;">
		<form id="nodeInfoForm" method="post">
		<input id="processId" name="processId" type="text" style = "display:none;" />
		</form>
	</div>
	<div style="margin-bottom:50px;">
		<table>
			<tr><td><div><img alt="环节未启动" src="<%=path%>/style/image/flowImg/smallblue.png"></div></td><td>未启动</td></tr>
			<tr><td><div><img alt="环节已启动" src="<%=path%>/style/image/flowImg/smallyellow.png"></div></td><td>已启动</td></tr>
			<tr><td><div><img alt="环节已完结" src="<%=path%>/style/image/flowImg/smallgrey.png"></div></td><td>已完结</td></tr>
		</table>
	</div>
	<div id="flowDiv" style="background-color:white;height:400px;"></div>
</body>   
</html> 