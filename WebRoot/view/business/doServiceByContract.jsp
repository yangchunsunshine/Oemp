<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="com.wb.framework.commonUtil.PropertiesReader"%>
<%PropertiesReader reader = PropertiesReader.getInstance();%>
<%String url = reader.getValue("/com/wb/config/application", "ACC_ROLER_URL");%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<title>查询业务页面-合同</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/zTree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/select2.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/i18n/zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/select2/css/select2.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style_mnt.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/jqueryui/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/jqgrid/css/ui.jqgrid.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/zTree/css/zTreeStyle.css" />
<script type=text/javascript>
var tips;
var thisGrid;
var contractId = "${contractId}";
//var contractId =267;


$(function(){
	getServiceList();
});

var tally;
function createService(){ //添加业务跳转页面
	parent.parent.parent.addOptionTab('<%=path%>/business/forward/gotoBusinessView?pageName=addProcessByContract&contractId='+contractId+'&processId=-1','添加业务-带合同');
}


function getServiceList(){
	thisGrid = $("#serviceTable").jqGrid({
		url: '<%=path%>/business/asyn/getProcessListByContractId?'+$("#processInfoForm").serialize(),
		datatype: 'json',
		colNames: ['业务id','业务名称','业务状态','操作'],
		colModel: [
			{name:'proId',index:'proId',sortable:false,hidden:true},
			{name:'processName',index:'processName',sortable:false,width:100,align:"center"},
			{name:'canUse',index:'orgId',sortable:false,width:90,hidden:false ,align:"center",formatter:flagFmatter},
			{name:'opera',index:'opera',sortable:true,width:150,align:"center",formatter:operaFmatter}
			
		],
		rowNum: 100,
		rowList: [100,150,200],
		pager: '#gridPager',
		mtype: "POST",
		width:$(window).width()-4,
		height:$(window).height()-100,
		viewrecords: true,
		multiselect: false,
		hidegrid: false
	});
	jQuery("#serviceTable").jqGrid('navGrid',"#gridPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
}




function flagFmatter(cellvalue, options, rowObject){
	if(cellvalue=="close"||cellvalue=="0"){
		return "<font color='red'>【未开启】</font>";
	}else{
		return "<font color='green'>【已开启】</font>";
	}
}

function operaFmatter(cellvalue, options, rowObject){
	var flag = rowObject.canUse;
	if(flag=="close"||flag=="0"){
		var processId = rowObject.id;
		return "<input type='button' value='开启' onclick=\"openService('"+processId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px' class='bcssbtn'/>"
		+"<input type='button' value='修改' onclick=\"updateService('"+processId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px' class='bcssbtn'/>";
	}else{
		var processId = rowObject.id;
		return "<input type='button' value='删除' onclick=\"deleteService('"+processId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px' class='bcssbtn'/>";
		
	}
}

function openService(processId){
	layer.confirm("业务流启动后无法更改!", {icon: 3, title:"提示"}, function(index){
		tips = layer.msg("正在启动,请稍后...",{icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/openServiceByContract',
		{
			'contractId': contractId,
			'processId': processId
		},
		function(result){
			if(result.code){
				layer.close(tips);
				parent.$.showMsg("启动成功!",1);
				searchfb();
			}else{
				layer.close(tips);
				parent.$.showMsg("启动失败!", 2);
				searchfb();
			}
		});
		layer.close(index);
	});
}

function deleteService(orgProcessId){
	layer.confirm("确认关闭该业务流?", {icon: 3, title:"提示"}, function(index){
		tips = layer.msg("正在关闭,请稍后...",{icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/deleteService',
		{
			'orgProcessId': orgProcessId
		},
		function(result){
			if(result.code){
				layer.close(tips);
				parent.$.showMsg("成功关闭!",1);
				searchfb();
			}else{
				layer.close(tips);
				parent.$.showMsg("关闭失败!", 2);
				searchfb();
			}
		});
		layer.close(index);
	});
}





function updateService(orgProcessId){
	//点击修改按钮直接进入业务遍及页面createservice.jsp中（开启的业务不能修改） // 和添加业务的页面相同 唯一不同的是添加业务不传processId 修改业务需要传
	parent.parent.parent.addOptionTab('<%=path%>/business/forward/gotoBusinessView?pageName=addProcessByContract&contractId='+contractId+'&processId='+orgProcessId,'修改业务-带合同');

}

function searchfb(){
	thisGrid.jqGrid('setGridParam',{url:'<%=path%>/business/asyn/getProcessListByContractId?'+$("#processInfoForm").serialize(), page : 1}).trigger("reloadGrid");
}



</script>
</head>
<body>
	   	<div class="nodeB">
	   		<h4><img src="<%=path%>/style/image/ico_table_01.png"/>业务列表：</h4>
	   		<div style = "text-align:right;" >
	   		<input class="acssbtn" id="cus_create" onclick="createService();" type="button" value="添加业务" ></input>
	   		</div>
	   		<table id="serviceTable" class="table_query" border="0" cellpadding="0" cellspacing="0" rules="all"></table>
	   		<div id="gridPager"></div>
	   	</div>
	   	<form id="processInfoForm" method="post">
	   		<input type = "text" name= "contractId" value="${contractId}" style="display:none;"/>
	   	</form>
</body>
</html>
