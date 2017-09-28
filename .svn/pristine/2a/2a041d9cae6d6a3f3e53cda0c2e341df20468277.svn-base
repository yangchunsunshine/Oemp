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
<title>业务设置(主界面)</title>
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
	$("#canUse").select2({
		language: "zh-CN"
	});
	var processName = $("#processName").val();
	var canUse = $("#canUse").val();
	thisGrid = $("#processTable").jqGrid({
		url: '<%=path%>/supervisory/asyn/getProcessList?processName='+processName+'&canUse='+canUse,
		datatype: 'json',
		colNames: ['proId','mngId','idDelete','业务名称','所属公司','业务状态','操作'],
		colModel: [
			{name:'proId',index:'proId',sortable:false,hidden:true},
			{name:'mngId',index:'mngId',sortable:false,hidden:true},
			{name:'idDelete',index:'idDelete',sortable:false,hidden:true},
			{name:'proName',index:'proName',sortable:true,width:50,align:"left"},
			{name:'mngName',index:'mngName',sortable:true,width:80,align:"left"},
			{name:'canUse',index:'canUse',sortable:true,width:30,align:"center",formatter:canUseFormat},
			{name:'opera',index:'opera',sortable:true,width:50,align:"left",formatter:operaFormat}
		],
		rowNum: 50,
		rowList: [50,100,200],
		pager: '#gridPager',
		sortname: 'ownerId',
		sortorder: "asc",
		rownumbers: true,
		rownumWidth: 50,
		caption: '业务列表',
		mtype: "POST",
		width:$(window).width()-40,
        height:$(window).height()-170,
		viewrecords: true,
		loadComplete: function (data){
			authView();
		},
		hidegrid: false
	});
	jQuery("#processTable").jqGrid('navGrid',"#gridPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
	jQuery("#jqgh_processTable_rn").prepend("序号");
});

function canUseFormat(cellvalue,options,rowObject){
	if(cellvalue == 0){
		return"<font color='red'>【未启用】</font>";
	}else{
		return"<font color='green'>【已启用】</font>";
	}
}

function operaFormat(cellvalue,options,rowObject) {
	var canUse = rowObject.canUse;
	var proId = rowObject.proId;
	var divH = "<div style='padding-left: 45px;'>";
	var divB = "</div>";
	var addNode = "";
	var deleteProcess = "";
	if(canUse == 0){
		var addNode = "<input id='addNode' type='button' value='添加节点' onclick=\"gotoAddNode('"+proId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;display:none;' class='bcssbtn'/>&emsp;";
		var deleteProcess = "<input id='delBuss' type='button' value='删除业务' onclick=\"deleteProcess('"+proId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;display:none;' class='bcssbtn'/>";
	}else{
		var addNode = "<input id='setNode' type='button' value='编辑节点' onclick=\"gotoUpdateNode('"+proId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;display:none;' class='bcssbtn'/>&emsp;";
		var deleteProcess = "<input id='delBuss' type='button' value='删除业务' onclick=\"deleteProcessSoft('"+proId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;display:none;' class='bcssbtn'/>";
	}
	return divH + addNode + deleteProcess + divB;
}

var addNodeLayer;
function gotoAddNode(proId){
	addNodeLayer = layer.open({
		title : ' 添加节点',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		shadeClose: true,
		area : ['600px', '450px'],
		content : '<%=path%>/supervisory/forward/gotoAddNode?proId='+proId,
		end : function(){
			searchfb();
		}
	});
}

var updateNodeLayer;
function gotoUpdateNode(proId){
	updateNodeLayer = layer.open({
		title : ' 添加节点',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		shadeClose: true,
		area : ['600px', '450px'],
		content : '<%=path%>/supervisory/forward/gotoUpdateNode?proId='+proId,
		end : function(){
			searchfb();
		}
	});
}

var addProcessLayer;
function gotoAddProcess(){
	addProcessLayer= layer.open({
		title : '添加业务',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 1,
		shadeClose: true,
		area : ['300px', '100px'],
		content : $("#addProcessDiv"),
		end : function(){
			$("#addProcessName").val("");
			searchfb();
		}
	});
}

function closeAddProcessLayer(){
	window.layer.close(window.addProcessLayer);
}

function saveProcess(){
	var processName = $("#addProcessName").val();
	if($.ckTrim(processName) == ""){
		parent.$.showMsg("请填写业务名称!", 2);
		return;
	}
	tips = layer.msg("正在保存，请稍后...", {icon : 16,time : 0, shade: [0.1]});
	$.post('<%=path%>/supervisory/asyn/saveProcess',{
		'processName':processName
	},function(result){
		if(result.code==0){
			parent.$.showMsg("保存成功!", 1, function(){
				layer.close(tips);
				closeAddProcessLayer();
			});
		}else if(result.code==1){
			parent.$.showMsg("保存失败,系统出错!", 2,function(){
				layer.close(tips);
			});
		}else if(result.code==2){
			parent.$.showMsg("业务名称已存在!", 2,function(){
				layer.close(tips);
			});
		}
	});
}

function deleteProcess(processId){
	layer.confirm("确认删除!", {icon: 3, title:"提示"}, function(index){
		tips = layer.msg("正在删除,请稍后...",{icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/deleteProcess',
		{
			'id': processId
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

function deleteProcessSoft(processId){
	layer.confirm("确认删除!", {icon: 3, title:"提示"}, function(index){
		tips = layer.msg("正在删除,请稍后...",{icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/deleteProcessSoft',
		{
			'id': processId
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
	var processName = $("#processName").val();
	var canUse = $("#canUse").val();
	thisGrid.jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getProcessList?processName='+processName+'&canUse='+canUse, page : 1}).trigger("reloadGrid");
}
</script>
</head>
<body>
<div id="mainDiv" style="padding-top: 10px">
	<div style="float: right;padding-right: 20px;" title="审批人员信息">
			<div style="margin-bottom:15px;">
				<span>
					<b style="font-size: 15px">业务名称:</b>
					<input maxlength="12"  id="processName" name="processName" type="text" class="dfinput_fb" style="width:140px;height:24px;"/>&emsp;
					<b style="font-size: 15px">业务状态:</b>
					<select id="canUse" name="canUse" class="select_staff" style="width:125px;">
						<option value="" selected="selected">全部</option>
						<option value="0">未启用</option>
						<option value="1">已启用</option>
					</select>&emsp;
					<input class="bcssbtn" type="button" onclick="searchfb();" value="查询" style="padding: 2px 6px 2px 6px"/>
					<input id="addBuss" class="abtn" type="button" onclick="gotoAddProcess();" value="添加业务" />
				</span>
			</div>
		<table id="processTable"></table>
		<div id="gridPager"></div>
	</div>
</div>

<div id="addProcessDiv" style="display: none;">
	<table class="table_query" border="0" cellpadding="0" cellspacing="0" rules="all" style="margin-top: 15px">
		<tr>
			<td align="left" style="border:0;"><h4>业务名称:</h4></td>
			<td align="left" style="border:0;">
				<input maxlength="12"  id="addProcessName" name="addProcessName" type="text" class="dfinput_fb" style="width:140px;height:24px;"/>
			</td>
			<td align="left" style="border:0;">
				<input class="acssbtn" type="button" style="padding: 2px 6px 2px 6px;" onclick="saveProcess();" value="提交" />
			</td>
		</tr>
	</table>
</div>

</body>  
</html>