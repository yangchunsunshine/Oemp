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
	getMoreServiceInfo();
	$("#flag").select2({
		language: "zh-CN"
	});
});

function getMoreServiceInfo(){
	thisGrid = $("#serviceListTable").jqGrid({
		url: '<%=path%>/supervisory/asyn/getMoreServiceInfo',
		datatype: 'json',
		colNames: ['ophId','办理时间','客户名称','业务名称','节点名称','完成状态','操作'],
		colModel: [
			{name:'ophId',index:'ophId',sortable:false,hidden:true},
			{name:'stamp',index:'stamp',sortable:true,align:"center"},
			{name:'orgName',index:'orgName',sortable:true,align:"left"},
			{name:'proName',index:'proName',sortable:true,align:"left"},
			{name:'nodeName',index:'nodeName',sortable:true,align:"left"},
			{name:'flag',index:'flag',sortable:true,align:"center",formatter:flagFmatter},
			{name:'opera',index:'opera',sortable:true,align:"center",formatter:operaFmatter}
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
		ondblClickRow: function(rowid){},
		loadComplete: function (data){},
		gridComplete: function(){},
		hidegrid: false
	});
	jQuery("#serviceListTable").jqGrid('navGrid',"#gridPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
	jQuery("#jqgh_serviceListTable_rn").prepend("序号");
}

function flagFmatter(cellvalue, options, rowObject){
	if(cellvalue == 0){
		return "【进行中】";
	}else{
		return "【结束】";
	}
}

function operaFmatter(cellvalue, options, rowObject){
	var flag = rowObject.flag;
	var ophId = rowObject.ophId;
	if(flag == 0){
		return 	"<a href='#' title='指派' onclick=\"openChMemDiv("+ophId+")\"><img width='16px' src='<%=path%>/style/image/button_bg_user.png'/></a>"+
						"<a href='#' title='完成' onclick=\"doneService("+ophId+")\"><img width='16px' src='<%=path%>/style/image/doneFollow.png'/></a>";
	}else{
		return "";
	}
}

var chMemDivLayer;
function openChMemDiv(ophId){
	chMemDivLayer = layer.open({
		type : 1,
		title : '指派审批人员',
		shade : [ 0.1, '#000' ],
		fix : true,
		maxmin: true,
		shadeClose: true,
		area : ['260px', '120px'],
		end : function(){
			queryMessage();
		},
		content : $("#chMemDiv"),
		success: function(){
			$.post('<%=path%>/supervisory/asyn/getChMemSel',function(result){
				var option = "";
				for(var i=0;i<result.length;i++){
					if(i==0){
						option = option + "<option value='"+result[i].memId+"' selected='selected'>"+result[i].memName+"</option>";
					}else{
						option = option + "<option value='"+result[i].memId+"'>"+result[i].memName+"</option>";
					}
				}
				$("#memSel").html(option);
				$("#ophIdH").val(ophId);
			});
		}
	});
}

function chMem(){
	var ophId = $("#ophIdH").val();
	var memberId = $("#memSel").val();
	if(memberId==null||memberId==""){
		parent.$.showMsg("请指派人员!", 2);
		return;
	}
	layer.confirm("确认指派该人员?", {icon: 3, title:"提示"}, function(index){
		tips = layer.msg("正在指派,请稍后...", {icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/updateServiceMember',{'ophId':ophId,'memberId':memberId},function(result){
			if(result.code){
				layer.close(tips);
				parent.$.showMsg("处理成功!", 1);
				window.layer.close(window.chMemDivLayer);
			}else{
				layer.close(tips);
				parent.$.showMsg("处理失败!", 2);
			}
		});
		layer.close(index);
	});
}

function doneService(ophId){
	layer.confirm("确认完成?", {icon: 3, title:"提示"}, function(index){
		tips = layer.msg("正在处理...", {icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/doneService',{'ophId':ophId},function(result){
			if(result.code){
				layer.close(tips);
				parent.$.showMsg("处理成功!", 1);
				queryMessage();
			}else{
				layer.close(tips);
				parent.$.showMsg("处理失败!", 2);
				queryMessage();
			}
		});
		layer.close(index);
	});
}

function queryMessage(){
	var orgName = $("#orgName").val();
	var stamp = $("#stamp").val();
	var flag = $("#flag").val();
	thisGrid.jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getMoreServiceInfo?orgName='+orgName+'&stamp=' + stamp+'&flag='+flag, page : 1}).trigger("reloadGrid");
}
</script>
</head>
<body>
    	<div class="tallyC"><h4><img src="<%=path%>/style/image/ico_table_01.png"/>查询条件：</h4>
			<table class="table_query"  cellpadding="0" cellspacing="0" rules="all">
				<tr>
					<td width="25%" align="center">
						<span>客户名称：</span>
						<span><input type="text" class="txt140" maxlength="30" id="orgName" name="orgName" value=""/></span>
					</td>
					<td width="25%" align="center">
						<span>办理时间：</span>
						<span><input type="text" id="stamp" name="stamp" onfocus="WdatePicker()" class="Wdate" readonly="readonly"/></span>
					</td>
					<td width="25%" align="center">
						<span>完成状态：</span>
						<select id="flag" style="width: 50%">
							<option value="" selected="selected">全部</option>
							<option value="0">未完成</option>
							<option value="1">已完成</option>
						</select>
					</td>
					<td width="25%" align="center">
						<span><input onclick="queryMessage();" value="查询" class="bcssbtn" class="acssbtn" type="button" style="padding: 2px 6px 2px 6px" style="height:30px;font-size:15px;text-align: center;padding-top:0px; padding-bottom:0px"/></span>
					</td>
				</tr>
			</table>
		</div>
	   	<div class="tallyD"><h4><img src="<%=path%>/style/image/ico_table_01.png"/>业务列表：</h4>
	   		<table id="serviceListTable" class="table_query"  cellpadding="0" cellspacing="0" rules="all"></table>
	   		<div id="gridPager" width="100%"></div>
	   	</div>
	   	
	   	<div id="chMemDiv" style="display: none;">
			<table class="table_query" border="0" cellpadding="0" cellspacing="0" rules="all">
				<tr>
					<td align="left" style="border:0;"><h4 style="display: inline-block;">指派人员:</h4></td>
					<td align="left" style="border:0;">
						<select id="memSel" name="memSel" style="border:1px solid #ccc;width: 120px;"></select>
						<input type="hidden" id="ophIdH" name="ophIdH"/>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center" style="border:0;padding-top: 10px"><input class="bcssbtn" type="button" style="padding: 4px 8px 4px 8px" onclick="chMem();" value="提交"  /></td>
				</tr>
			</table>
		</div>

</body>
</html>
