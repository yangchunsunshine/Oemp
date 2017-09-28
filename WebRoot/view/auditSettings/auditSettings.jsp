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
<title>审批设置(主界面)</title>
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
var treeObj;
var thisGrid;
var tips;
var depId = '${userInfo.departmentId}';
$(function(){
	$("#auditLevel").select2({
		language: "zh-CN"
	});
	$("#auditorName").select2({
		language: "zh-CN"
	});
	$("#auditType").select2({
		language: "zh-CN"
	});
	$("#auditLevelAdd").numLimit(1,10);
	$("#auditLevelUp").numLimit(1,10);
	thisGrid = $("#auditSettingsTable").jqGrid({
		url: '<%=path%>/supervisory/asyn/getAuditSettingsList?init=true&cmd=BY_DEP&queryId=0&companySearchName=-1&auditType=-1&auditLevel=-1',
		datatype: 'json',
		colNames: ['审批id','审批人员id','人员姓名','所属部门','流程类别','审批级别','操作'],
		colModel: [
			{name:'auditId',index:'auditId',sortable:false,hidden:true},
			{name:'auditorId',index:'auditorId',sortable:false,hidden:true},
			{name:'auditorName',index:'auditorName',width:90,sortable:false,align:"left"},
			{name:'partName',index:'partName',width:100,sortable:false,align:"left"},
			{name:'auditType',index:'auditType',width:60,sortable:false,align:"left",formatter:auditTypeFormat},
			{name:'auditLevel',auditLevel:'auditType',width:60,sortable:false,align:"center",formatter:auditLevelFormat},
			{name:'opera',index:'opera',width:60,sortable:false,align:"center",formatter:operaFormat}
		],
		rowNum: 50,
		rowList: [50,100,200],
		pager: '#gridPager',
		sortname: 'associateId',
		sortorder: "asc",
		rownumbers: true,
		rownumWidth: 50,
		caption: '审批人员列表',
		mtype: "POST",
		width:$(window).width()-270,
        height:$(window).height()-150,
		viewrecords: true,
		gridComplete: function (data) {
			authView();
       	},
		hidegrid: false
	});
	thisGrid.jqGrid('navGrid',"#gridPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
	$("#jqgh_auditSettingsTable_rn").prepend("序号");
	
	treeObj = $.fn.zTree.init($("#auditSettingsTree"), {
		data: {
			key: {
				name: "partName"
            },
            simpleData :{
				enable: true,
				idKey: "id",
				pIdKey: "pId",
				rootPId: 0
	        }
        },
        async: {
	        enable: true,
	        type: "get",
	       	url:"<%=path%>/supervisory/asyn/findMntFrameWork?ifHasEmp=yes"
        },
	   	callback: {
	       	onClick: function(event, treeId, treeNode) {
	   			searchfb();
			},
			onAsyncSuccess: function(event, treeId, treeNode, msg) {
				var node =treeObj.getNodes();
				treeObj.selectNode(node[0],false);
			    jQuery("#auditSettingsTable").jqGrid('navGrid',"#gridPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
			}
		}
	});
	
	$("#auditorName").ckCombox({
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
			$("#auditorName").select2({
				language: "zh-CN"
			});
		}
	});
});

function auditTypeFormat(cellvalue,options,rowObject){
	if(cellvalue==1){
		return "缴费审批流程";
	}
	return "其他";
}

function auditLevelFormat(cellvalue,options,rowObject){
	return "【"+cellvalue+"级审批】";
}

function operaFormat(cellvalue,options,rowObject) {
	var auditId = rowObject.auditId;
	var auditorId = rowObject.auditorId;
	var auditorName = rowObject.auditorName;
	var auditType = rowObject.auditType;
	var auditLevel = rowObject.auditLevel;
	var up = "<span><a href='#' title='修改' id='updateAuditor' style='display: none;' onclick=\"updateAuditSettings('"+auditId+"','"+auditorId+"','"+auditorName+"','"+auditType+"','"+auditLevel+"');\"><img src='<%=path%>/style/image/ico_turn.png'/></a></span>";
	var del = "<span style='margin-left: 8%;'><a href='#' title='删除' id='delAuditor' style='display: none;' onclick=\"deleteAuditSettings('"+cellvalue+"');\"><img src='<%=path%>/style/image/ico_delete_ro.png'/></a></span>";
	return up + del;
}

function deleteAuditSettings(auditId){
	layer.confirm("删除节点可能造成当前存在的审批流程失效!", {icon: 3, title:"提示"}, function(index){
		tips = layer.msg("正在删除,请稍后...",{icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/deleteAuditor',
		{
			'auditId': auditId
		},
		function(result){
			if(result.code){
				layer.close(tips);
				searchfb();
				$.showMsg("删除成功!",1);
			}else{
				layer.close(tips);
				$.showMsg("删除失败,系统出错!",2);
			}
		});
		layer.close(index);
	});
}

//查询
function searchfb(){
	var selectNodes = treeObj.getSelectedNodes();
	var queryId = selectNodes[0].id;
	var cmd = selectNodes[0].cmd;
	var auditType = $("#auditType").val();
	var auditorName = $("#auditorName").val();
	var auditLevel = $("#auditLevel").val();
	thisGrid.jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getAuditSettingsList?init=false&cmd='+cmd+'&queryId=' + queryId+'&auditType='+auditType+'&auditorName=' + auditorName+'&auditLevel='+auditLevel, page : 1}).trigger("reloadGrid");
}


var addAuditorLayer;
function addAuditor(){
	addAuditorLayer = layer.open({
		type : 1,
		title : '添加审批权限',
		shade : [ 0.1, '#000' ],
		fix : true,
		maxmin: true,
		shadeClose: true,
		area : ['260px', '180px'],
		end : function(){
			searchfb();
			resetAddValue();
		},
		content : $("#addAuditorDiv"),
		success: function(){
			$("#auditorIdAdd").ckCombox({
				url : '<%=path%>/supervisory/asyn/getEmpByDep',
				requestType : "POST",
				data: {'depSelect' : depId},
				dataType : "json",
				width : "125px",
				height : "20px",
				defaultSel : false,
				formatter : {
					root: "empList",
					id : "empId",
					value : "empName"
				}
			});
		}
	});
}

function saveAuditor(){
	var auditorId = $("#auditorIdAdd").val();
	var auditType = $("#auditTypeAdd").val();
	var auditLevel = $("#auditLevelAdd").val();
	if(auditLevel==""||auditLevel==null){
		$.showMsg("请填写审批级别!",2);
		return;
	}
	tips = layer.msg("正在保存,请稍后...",{icon : 16,time : 0, shade: [0.1]});
	$.post('<%=path%>/supervisory/asyn/saveAuditor',
	{
		'auditorId': auditorId,
		'auditType': auditType,
		'auditLevel': auditLevel
	},
	function(result){
		if(result.code==0){
			layer.close(tips);
			$.showMsg("创建成功!",1);
			window.layer.close(window.addAuditorLayer);
		}else if(result.code==1){
			layer.close(tips);
			$.showMsg("创建失败,系统出错!",2);
		}else if(result.code==2){
			layer.close(tips);
			$.showMsg("重复的审批权限!",2);
		}
	});
}

var updateAuditorLayer;
var tempLevel = 0;
function updateAuditSettings(auditId,auditorId,auditorName,auditType,auditLevel){
	$("#auditIdUp").val(auditId);
	$("#auditTypeUp").val(auditType);
	$("#auditLevelUp").val(auditLevel);
	tempLevel = auditLevel;
	updateAuditorLayer = layer.open({
		type : 1,
		title : '更新审批权限',
		shade : [ 0.1, '#000' ],
		fix : true,
		maxmin: true,
		shadeClose: true,
		area : ['260px', '180px'],
		end : function(){
			searchfb();
			resetUpValue();
		},
		content : $("#upAuditorDiv"),
		success: function(){
			$("#auditorIdUp").ckCombox({
				url : '<%=path%>/supervisory/asyn/getEmpByDep',
				requestType : "POST",
				data: {'depSelect' : depId},
				dataType : "json",
				width : "125px",
				height : "20px",
				defaultSel : false,
				formatter : {
					root: "empList",
					id : "empId",
					value : "empName"
				},
				onSuccess: function(){
					$("#auditorIdUp").val(auditorId);
				}
			});
		}
	});
}

function upAuditor(){
	var auditId = $("#auditIdUp").val();
	var auditorId = $("#auditorIdUp").val();
	var auditType = $("#auditTypeUp").val();
	var auditLevel = $("#auditLevelUp").val();
	if(auditLevel==""||auditLevel==null){
		$.showMsg("请填写审批级别!",2);
		return;
	}
	if(tempLevel != auditLevel){
		layer.confirm("级别变更可能造成当前存在的审批流程失效!", {icon: 3, title:"提示"}, 
		function(index){
			tips = layer.msg("正在更新,请稍后...",{icon : 16,time : 0, shade: [0.1]});
			$.post('<%=path%>/supervisory/asyn/updateAuditor',
			{
				'auditId': auditId,
				'auditorId': auditorId,
				'auditType': auditType,
				'auditLevel': auditLevel
			},
			function(result){
				if(result.code==0){
					layer.close(tips);
					$.showMsg("更新成功!",1);
					window.layer.close(window.updateAuditorLayer);
				}else if(result.code==1){
					layer.close(tips);
					$.showMsg("更新失败,系统出错!",2);
				}else if(result.code==2){
					layer.close(tips);
					$.showMsg("重复的审批权限!",2);
				}
			});
		});
	}else{
		tips = layer.msg("正在更新,请稍后...",{icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/updateAuditor',
		{
			'auditId': auditId,
			'auditorId': auditorId,
			'auditType': auditType,
			'auditLevel': auditLevel
		},
		function(result){
			if(result.code==0){
				layer.close(tips);
				$.showMsg("更新成功!",1);
				window.layer.close(window.updateAuditorLayer);
			}else if(result.code==1){
				layer.close(tips);
				$.showMsg("更新失败,系统出错!",2);
			}else if(result.code==2){
				layer.close(tips);
				$.showMsg("重复的审批权限!",2);
			}
		});
	}
	
}

function resetAddValue(){
	$("#auditorNameAdd").html("");
	$("#auditorIdAdd").val("");
	$("#auditLevelAdd").val("");
	$("#auditTypeAdd").val("1");
}

function resetUpValue(){
	$("#auditorNameUp").html("");
	$("#auditIdUp").val("");
	$("#auditorIdUp").val("");
	$("#auditLevelUp").val("");
	$("#auditTypeUp").val("1");
}

</script>
</head>
<body>
<div id="mainDiv" style="padding-top: 10px">
	<div class="customer_left">
        <h2 style="color: black;">组织架构</h2>
        <div class="div_tree">
        	<div id="auditSettingsTree" class="ztree" style="height: 240px;margin-bottom: 5px;margin-left: 15px;"></div>
        </div>
    </div>
	<div style="float: right;padding-right: 20px;" title="审批人员信息">
			<div style="margin-bottom:15px;">
				<span>
					<b style="font-size: 15px">员工姓名:</b>
					<select id="auditorName" name="auditorName" style="width:100px;height:24px;"></select>&emsp;
					<b style="font-size: 15px">流程类别:</b>
					<select id="auditType" name="auditType" style="width:120px;">
						<option value="-1" selected="selected">全部</option>
						<option value="1">缴费审批流程</option>
					</select>&emsp;
					<b style="font-size: 15px">审批级别:</b>
					<select id="auditLevel" class="select_staff" name="auditLeve" style="width:125px;">
						<option value="-1" selected="selected">全部</option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">6</option>
						<option value="7">7</option>
						<option value="8">8</option>
						<option value="9">9</option>
					</select>
					&emsp;<input class="bcssbtn" type="button" onclick="searchfb()" value="查询" style="padding: 2px 6px 3px 6px"/>
					<input class="acssbtn" id="addAuditor" type="button" onclick="addAuditor()" value="添加审批权限" style="padding: 2px 6px 3px 6px;display: none;"/>
				</span>
			</div>
		<table id="auditSettingsTable"></table>
		<div id="gridPager"></div>
	</div>
</div>

<div id="addAuditorDiv" style="display: none;">
	<table class="table_query" border="0"  cellpadding="0" cellspacing="0" rules="all">
		<tr>
			<td align="left" style="border:0;"><h4 style="display: inline-block;">审批人:</h4></td>
			<td align="left" style="border:0;">
				<select class="select_staff" id="auditorIdAdd" name="auditorIdAdd"></select>
			</td>
		</tr>
		<tr>
			<td align="left" style="border:0;"><h4 style="display: inline-block;">流程类别:</h4></td>
			<td align="left" style="border:0;">
				<select id="auditTypeAdd" class="select_staff" name="auditTypeAdd" style="width:125px;">
					<option value="1" selected="selected">缴费审批流程</option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="left" style="border:0;"><h4 style="display: inline-block;">审批级别:</h4></td>
			<td align="left" style="border:0;">
				<select id="auditLevelAdd" class="select_staff" name="auditLevelAdd" style="width:125px;">
					<option value="1" selected="selected">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
					<option value="9">9</option>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center" style="border:0;padding-top: 10px"><input class="bcssbtn" type="button" style="padding: 4px 8px 4px 8px" onclick="saveAuditor();" value="提交"  /></td>
		</tr>
	</table>
</div>

<div id="upAuditorDiv" style="display: none;">
	<table class="table_query" border="0" cellpadding="0" cellspacing="0" rules="all">
		<tr>
			<td align="left" style="border:0;"><h4 style="display: inline-block;">审批人:</h4></td>
			<td align="left" style="border:0;">
				<select class="select_staff" id="auditorIdUp" name="auditorIdUp"></select>
				<input type="hidden" id="auditIdUp" name="auditIdUp">
			</td>
		</tr>
		<tr>
			<td align="left" style="border:0;"><h4 style="display: inline-block;">流程类别:</h4></td>
			<td align="left" style="border:0;">
				<select id="auditTypeUp" class="select_staff" name="auditTypeUp" style="width:125px;" disabled="disabled">
					<option value="1" selected="selected">缴费审批流程</option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="left" style="border:0;"><h4 style="display: inline-block;">审批级别:</h4></td>
			<td align="left" style="border:0;">
				<select id="auditLevelUp" class="select_staff" name="auditLevelUp" style="width:125px;">
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
					<option value="9">9</option>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center" style="border:0;padding-top: 10px"><input class="bcssbtn" type="button" style="padding: 4px 8px 4px 8px" onclick="upAuditor();" value="提交"  /></td>
		</tr>
	</table>
</div>

</body>  
</html>