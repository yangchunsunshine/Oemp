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
<title>在线状态_主界面</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script type="text/javascript" src="<%=path%>/script/powerAuth.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/select2.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/i18n/zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/select2/css/select2.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/style/css/style.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqueryui/jquery-ui.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqgrid/css/ui.jqgrid.css"/>
<script type="text/javascript">
var depId = '${userInfo.departmentId}';
$(function(){
	$("#clerkTel").mustNumber();
	$("select").select2({
		language: "zh-CN"
	});
	$("#clerkMonitorTable").jqGrid({
		url: '<%=path%>/supervisory/asyn/getOnlineEmpList?'+$("#clerkMonitorForm").serialize(),
		datatype: 'json',
		colNames: ['员工ID','员工姓名','员工电话','所属部门','员工邮箱','最后登录时间','在线标识','计数标识','在线状态'],
		colModel: [
			{name:'clerkId',index:'clerkId',sortable:false,hidden:true},
			{name:'clerkName',index:'clerkName',width:100,sortable:false,align:"left"},
			{name:'clerkTel',index:'clerkTel',width:150,sortable:false,align:"left"},
			{name:'partName',index:'partName',width:150,sortable:false,align:"left"},
			{name:'clerkEmail',index:'clerkEmail',width:105,sortable:false,align:"center"},
			{name:'lastLogin',index:'lastLogin',width:100,sortable:false,align:"center"},
			{name:'clerkState',index:'clerkState',sortable:false,hidden:true},
			{name:'countState',index:'countState',sortable:false,hidden:true},
			{name:'clerkStateName',index:'clerkStateName',width:60,sortable:false,align:"center",summaryType:'count',summaryTpl:'<b>共{0}人</b>'}
		],
		rowNum: 100,
		rowList: [15,20,50],
		pager: '#gridPager',
		sortname: 'clerkId',
		sortorder: "asc",
		rownumbers: true,
		rownumWidth: 50,
		caption: '员工监控列表 <span style="color : red;">(双击查询员工详细)</span>',
		mtype: "GET",
		width:$(window).width()-40,
        height:$(window).height()-195,
		viewrecords: true,
		hidegrid: false,
		footerrow:true,
		loadComplete: function (data) {
			$(this).footerData("set",{"clerkName":"合计","clerkTel":"在线会计"+data.trendData.summary1+"人/共"+data.trendData.count+"人"});//将合计值显示出来
       	},
		gridComplete: function(){
	        var ids = $("#clerkMonitorTable").jqGrid('getDataIDs');
	        for(var i=0;i<ids.length;i++){
	        	var row=$("#clerkMonitorTable").jqGrid('getRowData',ids[i]);
	        	if(row["clerkState"]=="<spring:message code="CLERK_STATE_OFFLINE"/>"){
	        		$("#clerkMonitorTable #"+ids[i]).css("color","gray");
		        }else if(row["clerkState"]=="<spring:message code="CLERK_STATE_ONLINE"/>"){
		        	$("#clerkMonitorTable #"+ids[i]).css("color","green"); 
			    }
	        }
	   	},
		ondblClickRow: function(id){
	   		var row=$("#clerkMonitorTable").jqGrid('getRowData',id);
	   		parent.clerkDetailDispather(row["clerkTel"],row["clerkName"],2);
		}
	});
	jQuery("#clerkMonitorTable").jqGrid('navGrid',"#gridPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
	jQuery("#jqgh_clerkMonitorTable_rn").prepend("序号");
	$("#clerkName").ckCombox({
		url : '<%=path%>/supervisory/asyn/getEmpByDep',
		requestType : "POST",
		data: {'depSelect' : depId},
		dataType : "json",
		width : "125px",
		height : "20px",
		formatter : {
			root: "empList",
			id : "empName",
			value : "empName"
		},
		onSuccess: function(){
			$("#clerkName").select2({
				language: "zh-CN"
			});
		}
	});
});

//刷新本页
function reloadSelf(){
	$("#clerkMonitorTable").jqGrid().trigger("reloadGrid");
}

//查询
function searchfb(){
	jQuery("#clerkMonitorTable").jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getOnlineEmpList?'+$("#clerkMonitorForm").serialize(),page:1}).trigger("reloadGrid");
}

function autoSub(obj){
    if(/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/.test(obj.value)){
    	searchfb();
    }
    if(obj.value==""){
    	searchfb();
    }
}
</script>
</head>
<body>
	<div id="orgPageRecover" style="width:95%;margin:25px 0 15px 20px;">
		<form id="clerkMonitorForm" method="post">
			<div style="margin-bottom:15px;">
				<span><b>员工姓名:</b><select id="clerkName" name="clerkName" style="width:80px;height:24px;"></select>
				&emsp;<b>员工电话:</b><input id="clerkTel" name="clerkTel" onkeyup="autoSub(this);" maxlength="11" type="text" class="dfinput_fb" style="width:120px;height:24px;"/>
				&emsp;<b>在线状态:</b><select name="clerkState" style="width:80px;height:24px;font-size:14px;border:1px #ccc solid;">
					<option value="<spring:message code="STATE_ALL_INCLUDE"/>"><spring:message code="STATE_ALL_INCLUDE_NAME"/></option>
					<option value="<spring:message code="CLERK_STATE_ONLINE"/>"><spring:message code="CLERK_STATE_ONLINE_NAME"/></option>
					<option value="<spring:message code="CLERK_STATE_OFFLINE"/>"><spring:message code="CLERK_STATE_OFFLINE_NAME"/></option>
				</select>
				&emsp;
				<input class="bbtn" id="selBtn" type="button" onclick="searchfb()" value="查询" />
				<input class="abtn" id="frameWorkBtn" type="button" onclick="parent.addOptionTab('<%=path%>/supervisory/forward/gotoFrameworkManage','组织架构');" value="管理员工"/>
			</span></div>
		</form>
		<table id="clerkMonitorTable"></table>
		<div id="gridPager"></div>
	</div>
</body>  
</html> 
