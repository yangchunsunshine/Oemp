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
<title>提现记录</title>
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
	$("#grid").jqGrid({
		url : '<%=path%>/supervisory/asyn/getWithdrawalRec',
		postData:{"startTime":$("#startTime").val(),"endTime":$("#endTime").val()},
		contentType : 'application/json',
		datatype : 'json',
	   	rownumbers:true,
		mtype: "post",
		rowNum:-1,
		autowidth:true,
		height:400,
	   	colNames:['id','提现日期','提现金额','提现状态','操作'],
	   	colModel:[
	   		{name:'id',index:'id',width:120,hidden:true},
	   		{name:'withdrawalTime',index:'withdrawalTime',width:100,align:'center'},
	   		{name:'withdrawalAmount',index:'withdrawalAmount',width:100,align:'center'},
	   		{name:'state',index:'state',width:100,align:'center',formatter:function(cellvalue){
	   			if(cellvalue==0){
	   				return "提现申请已提交";
	   			}else if(cellvalue==1){
	   				return "提现申请完成";
	   			}
	   		}},
	   		{name:'id',index:'id',width:60,align:'center',formatter:function(cellvalue, options, rowObject){
             	var id = options.rowId;
				var divH = "<div  style='padding-right: 40px;'>";
				var divB = "</div>";
				//var detail="<span id='detail' style='padding-left:16px;display:none;'><a href='#' onclick=\"showPayAuditDetail('"+id+"');\" style='color:blue;text-decoration:underline;'>提现明细</a></span>";
				var temp = "<a onclick=\"showPayAuditDetail('"+id+"');\">【提现明细】</a>";
				return temp;
			 }},
	   	],
		viewrecords:true,
        rowNum:10,
        rowList:[10,20,30],
        pager:"#gridPager"
	});
});

function search(){
	var loading=layer.load(1, {
		shade: [0.1,'#808080']
	});	  
	$("#grid").jqGrid('setGridParam', {
		url : '<%=path%>/supervisory/asyn/getWithdrawalRec?startTime='+$("#startTime").val()+'&endTime='+$("#endTime").val(),
		page : 1
	}).trigger("reloadGrid");
	layer.close(loading);
}

var payAuditDetailLayer;
function showPayAuditDetail(id){//进入跟进页面
	payAuditDetailLayer = layer.open({
		title : '历史记录:',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		maxmin: true,
		shadeClose: true,
		area : ['800px', '400px'],
		content : '<%=path%>/supervisory/forward/gotoDetailWithdrawalRec?id='+id,
		end : function(){
			//searchfb();
		},
		success : function(){
			//layer.full(addFollow);
		}
	});
}
</script>
</head>
<body>
<div class="re_top">
	提现日期：<input type="text" id="startTime" name="startTime" onfocus="WdatePicker()" class="Wdate" style="width:100px;"/> 至 <input type="text" id="endTime" name="endTime" onfocus="WdatePicker()" class="Wdate" style="width:100px;"/> &nbsp; &nbsp;
    <input type="button" value="查询" onclick="search()" style="padding: 2px 6px 2px 6px;height:28px;" class="bcssbtn">
</div>
<table id="grid"></table>
<div id="gridPager"></div>
</body>
</html>
