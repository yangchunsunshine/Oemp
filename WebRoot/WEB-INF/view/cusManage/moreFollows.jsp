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
<title>报税</title>
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
var thisGrid;
var tips;

$(function(){
	$("#followTimeBegin").val(getDate());
	$("#followTimeEnd").val(getDate());
	getFollowList();
	$("#isRead").select2({
		language: "zh-CN"
	});
});

function getDate(){
	var myDate;
	var year = new Date().getFullYear();
	var month = new Date().getMonth()+1;
	var day = new Date().getDate();
	if(month < 10) month = "0" + month;
	if(day < 10) day = "0" + day;
	myDate = year+"-"+month+"-"+day;
	return myDate;
}

function getFollowList(){
	var followTimeBegin = getDate();
	var followTimeEnd = getDate();
	thisGrid = $("#followListTable").jqGrid({
		url: '<%=path%>/supervisory/asyn/getMoreFollowList?followTimeBegin='+followTimeBegin+'&followTimeEnd='+followTimeBegin,
		datatype: 'json',
		colNames: ['orgId','cusId','followId','客户名称','跟进时间','跟进备注','联系人','联系方式','是否完成','操作'],
		colModel: [
			{name:'orgId',index:'orgId',sortable:false,hidden:true},
			{name:'cusId',index:'cusId',sortable:false,hidden:true},
			{name:'followId',index:'followId',sortable:false,hidden:true},
			{name:'orgName',index:'orgName',sortable:true,align:"left"},
			{name:'followTime',index:'followTime',sortable:true,align:"center"},
			{name:'content',index:'content',sortable:true,align:"left",formatter:contentFmatter},
			{name:'contacts',index:'contacts',sortable:true,align:"left"},
			{name:'tel',index:'tel',sortable:true,align:"left"},
			{name:'isRead',index:'isRead',sortable:true,align:"left",formatter:isReadFmatter},
			{name:'isRead',index:'isRead',sortable:true,align:"center",formatter:operatorFmatter}
		],
		rowNum: 100,
		rowList: [100,150,200],
		pager: '#gridPager',
		mtype: "POST",
		width:$(window).width()-5,
		height:$(window).height()-170,
		viewrecords: true,
		multiselect: false,
		rownumbers: true,
		ondblClickRow: function(rowid){
			var cusId = $('#followListTable').jqGrid('getRowData',rowid).cusId;
			createFollow(cusId);
		},
		loadComplete: function (data){},
		gridComplete: function(){},
		hidegrid: false
	});
	jQuery("#followListTable").jqGrid('navGrid',"#gridPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
	jQuery("#jqgh_followListTable_rn").prepend("序号");
}

function createFollow(cusId){//进入跟进页面
	addFollow = layer.open({
		title : '待办事项',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		maxmin: true,
		shadeClose: true,
		area : ['550px', '480px'],
		content : '<%=path%>/supervisory/forward/gotoFollowCreate?cusId='+cusId,
		success : function(){
			//layer.full(addFollow);
		}
	});
}

function contentFmatter(cellvalue, options, rowObject){
	if(cellvalue.length>12){
			  cellvalue=cellvalue.substring(0,12)+"...";
	}
	return cellvalue;
}

function isReadFmatter(cellvalue, options, rowObject){
	if(cellvalue==0){
		return "未完成";
	}else{
		return "已完成";
	}
}

function operatorFmatter(cellvalue, options, rowObject){
	var followId = rowObject.followId;
	if(cellvalue==0){
		return "<a href='#' title='完成' onclick=\"doneSingleFollow("+followId+")\"><img width='22px' src='<%=path%>/style/image/doneFollow.png'/></a>";
	}else{
		return "<a href='#' title='重启' onclick=\"reloadSingleFollow("+followId+")\"><img width='22px' src='<%=path%>/style/image/reloadFol.png'/></a>";
	}
}

function doneSingleFollow(followId){
	layer.confirm("确认完成?", {icon: 3, title:"提示"}, function(index){
		tips = layer.msg("正在完成待办事项...", {icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/doneSingleFollow',{'followId':followId},function(result){
			if(result.code){
					layer.close(tips);
					parent.$.showMsg("处理成功!", 1);
					queryMessage();
				}else{
					layer.close(tips);
					parent.$.showMsg("处理失败!", 2);
					queryMessage();
				}
			layer.close(index);
		});
	});
}

function reloadSingleFollow(followId){
	layer.confirm("确认重新开启?", {icon: 3, title:"提示"}, function(index){
		tips = layer.msg("正在处理,请稍后...", {icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/reloadSingleFollow',{'followId':followId},function(result){
			if(result.code){
					layer.close(tips);
					parent.$.showMsg("处理成功!", 1);
					queryMessage();
				}else{
					layer.close(tips);
					parent.$.showMsg("处理失败!", 2);
					queryMessage();
				}
			layer.close(index);
		});
	});
}

function queryMessage(){
	var orgName = $("#orgName").val();
	var followTimeBegin = $("#followTimeBegin").val();
	var followTimeEnd = $("#followTimeEnd").val();
	var isRead = $("#isRead").val();
	thisGrid.jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getMoreFollowList?orgName='+orgName+'&followTimeBegin=' + followTimeBegin+'&followTimeEnd='+followTimeEnd+'&isRead='+isRead, page : 1}).trigger("reloadGrid");
}
</script>
</head>
<body>
   	<div class="tallyC"><h4><img src="<%=path%>/style/image/ico_table_01.png"/>查询条件：</h4>
		<table class="table_query"  cellpadding="0" cellspacing="0" rules="all">
			<tr>
				<td width="20%" align="center">
					<span>客户名称：</span>
					<span><input type="text" class="txt140" maxlength="30" id="orgName" name="orgName" value=""/></span>
				</td>
				<td width="20%" align="center">
					<span>跟进始：</span>
					<span><input name="followTimeBegin" type="text" id="followTimeBegin" onfocus="WdatePicker()" class="Wdate" readonly="readonly"/></span>
				</td>
				<td width="20%" align="center">
					<span>跟进末：</span>
					<span><input name="followTimeEnd" type="text" id="followTimeEnd" onfocus="WdatePicker()" class="Wdate" readonly="readonly"/></span>
				</td>
				<td width="20%" align="center">
					<span>完成度：</span>
					<select id="isRead" style="width: 50%">
						<option value="" selected="selected">全部</option>
						<option value="0">未完成</option>
						<option value="1">已完成</option>
					</select>
				</td>
				<td width="20%" align="center">
					<span><input onclick="queryMessage();" value="查询" class="bcssbtn" class="acssbtn" type="button" style="padding: 2px 6px 2px 6px" style="height:30px;font-size:15px;text-align: center;padding-top:0px; padding-bottom:0px"/></span>
				</td>
			</tr>
		</table>
	</div>
   	<div class="tallyD"><h4><img src="<%=path%>/style/image/ico_table_01.png"/>跟进列表：</h4>
   		<table id="followListTable" class="table_query"  cellpadding="0" cellspacing="0" rules="all"></table>
   		<div id="gridPager" width="100%"></div>
   	</div>
</body>
</html>
