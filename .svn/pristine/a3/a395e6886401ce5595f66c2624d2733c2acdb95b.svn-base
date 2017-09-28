<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<title>评价查询(主界面)</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script type="text/javascript" src="<%=path%>/script/powerAuth.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/zTree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/select2.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/i18n/zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/select2/css/select2.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/style/css/style.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/style/css/style_mnt.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqueryui/jquery-ui.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqgrid/css/ui.jqgrid.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/zTree/css/zTreeStyle.css" />
<script type="text/javascript">
var thisGrid;
var tips;
$(function(){
	$("#queryScore").select2({
		language: "zh-CN"
	});
	thisGrid = $("#feedBackTable").jqGrid({
		url: '<%=path%>/supervisory/asyn/getFeedBackList',
		datatype: 'json',
		colNames: ['fbId','orgId','客户名称','业务名称','分数','评论内容','处理人员'],
		colModel: [
			{name:'fbId',index:'fbId',sortable:false,hidden:true},
			{name:'orgId',index:'orgId',sortable:false,hidden:true},
			{name:'orgName',index:'orgName',sortable:true,width:50,align:"left"},
			{name:'nodeName',index:'nodeName',sortable:true,width:50,align:"left"},
			{name:'score',index:'score',sortable:true,width:20,align:"center"},
			{name:'detail',index:'detail',sortable:true,width:160,align:"left"},
			{name:'creatorName',creatorName:'detail',sortable:true,width:30,align:"center"}
		],
		rowNum: 50,
		rowList: [50,100,200],
		pager: '#gridPager',
		sortname: 'ownerId',
		sortorder: "asc",
		rownumbers: true,
		rownumWidth: 50,
		caption: '评价列表',
		mtype: "POST",
		width:$(window).width()-40,
        height:$(window).height()-170,
		viewrecords: true,
		loadComplete: function (data){
			authView();
		},
		hidegrid: false
	});
	jQuery("#feedBackTable").jqGrid('navGrid',"#gridPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
	jQuery("#jqgh_feedBackTable_rn").prepend("序号");
});

function searchfb(){
	var queryName = $("#queryName").val();
	var queryCreatorName = $("#queryCreatorName").val();
	var queryScore = $("#queryScore").val();
	thisGrid.jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getFeedBackList?queryName='+queryName+'&queryCreatorName='+queryCreatorName+'&queryScore='+queryScore, page : 1}).trigger("reloadGrid");
}
</script>
</head>
<body>
<div id="mainDiv" style="padding-top: 10px">
	<div style="float: right;padding-right: 20px;" title="审批人员信息">
			<div style="margin-bottom:15px;">
				<span>
					<b style="font-size: 15px">名称:</b>
					<input maxlength="12"  id="queryName" name="queryName" placeholder="请输入业务名称或客户名称" type="text" class="dfinput_fb" style="width:180px;height:24px;"/>&emsp;
					<b style="font-size: 15px">处理人员:</b>
					<input maxlength="12"  id="queryCreatorName" name="queryCreatorName" placeholder="请输入处理人员名称" type="text" class="dfinput_fb" style="width:140px;height:24px;"/>&emsp;
					<b style="font-size: 15px"> 分数:</b>
					<select id="queryScore" name="queryScore" style="width:60px;">
						<option value="0" selected="selected">全部</option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
					</select>&emsp;
					<input class="bcssbtn" type="button" onclick="searchfb();" value="查询" style="padding: 2px 6px 2px 6px"/>
				</span>
			</div>
		<table id="feedBackTable"></table>
		<div id="gridPager"></div>
	</div>
</div>
</body>  
</html>