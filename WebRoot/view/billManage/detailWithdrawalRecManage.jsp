<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.wb.framework.commonUtil.PropertiesReader"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<title>提现明细</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style_mnt.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqueryui/jquery-ui.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqgrid/css/ui.jqgrid.css"/>
<style type="text/css">
	.table-head{padding-right:17px;background-color:#f5f3f3;color:#f5f3f3;}
	.table-body{width:100%; height:300px;overflow-y:scroll;}
	.table-head table,.table-body table{width:100%;}
	.table-body table tr:nth-child(2n+1){background-color:#f2f2f2;}
</style>
<script type="text/javascript">
$(function() {
	$("#gridTable").jqGrid({
		url : "<%=path%>/supervisory/asyn/detailWithdrawalRec?id="+$("#id").val(),
		contentType : 'application/json',
		datatype : 'json',
	   	rownumbers:true,
		mtype: "post",
		rowNum:-1,
		//multiselect:true,
		autowidth:true,
		width:'500',
		height:'280',
	   	colNames:['id','订单号','充值企业','充值金额'],
	   	colModel:[
	   		{name:'id',index:'id',width:120,hidden:true},
	   		{name:'code',index:'code',width:100,align:'center'},
	   		{name:'companyName',index:'companyName',width:120,align:'center'},
	   		{name:'basePackageMoney',index:'basePackageMoney',width:100,align:'center'}
	   	]
	   	//,
		//viewrecords:true,
        //rowNum:10,
        //rowList:[10,20,30],
        //pager:"#gridPager"
	});
});
</script>
</head>
<body>
	<input type="hidden" id="id" name="id" value="${requestScope.id}"/>
    <table id="gridTable"></table>
	<div id="gridPager"></div>
</body>
</html>
