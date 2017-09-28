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
<title>添加节点</title>
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
<script type="text/javascript">
var tips;
$(function(){
	$("#roleSelect").select2({
		language: "zh-CN"
	});
	getOrgRole();
});

function getOrgRole(){
		var ACC_ROLER_URL = '<%=url%>';
		var orgId = ${userInfo.orgId};
		var url = ACC_ROLER_URL+"/auth/sysauth/role/get.jspx";
		$.ajax({
			type : "get",
			timeout : 5000,
	        dataType : "jsonp",  
	       	jsonp : "callBack",
	       	data : {"roleType":"0001","refCompanyId":orgId},
			url  : url,
	       	success : function(data){
	       		var mess = data.results;
	       		var roleStr = "<option value=''>选择角色：</option>";
	       		for(var i=0;i<mess.length;i++){
					roleStr = roleStr + "<option value='"+mess[i].rid+"'>"+mess[i].roleName+"</option>";
	       		}
	       		$("#roleSelect").html(roleStr);
	       	},
	       	error : function(XMLHttpRequest, textStatus, errorThrown) {
				parent.$.showMsg("亲!您的网络不给力哦~", 2, null, 5000);
		}
	});
}

function addNodeTr(){
	var addRoleText = $("#roleSelect").find("option:selected").text();
	var addRoleId = $("#roleSelect").val();
	var addNodeName = $("#addNodeName").val();
	var repeat = checkRepeat(addRoleId,addNodeName);
	if(repeat){
		parent.$.showMsg("节点名称重复!", 2);
		return;
	}
	if(!addRoleId){
		parent.$.showMsg("请选择执行角色!", 2);
		return;
	}
	if(!addNodeName){
		parent.$.showMsg("请填写节点名称!", 2);
		return;
	}
	var taxRow = "<tr>"+
					"<td align='left'>"+addRoleText+
						"<input mes='roleId' type='hidden' value='"+addRoleId+"'>"+
					"</td>"+
					"<td align='left'>"+addNodeName+
						"<input mes='nodeName' type='hidden' value='"+addNodeName+"'>"+
					"</td>"+
					"<td align='center'>"+
						"<input type='button' value='上移' onclick=\"up(this);\" style='height:23px;font-size:12px;text-align: center;padding: 0px 0px 0px 0px' class='bcssbtn'/>"+
						"<input type='button' value='下移' onclick=\"down(this);\" style='height:23px;font-size:12px;text-align: center;padding: 0px 0px 0px 0px' class='bcssbtn'/>"+
						"<input type='button' value='删除' onclick=\"delNodeTr(this);\" style='height:23px;font-size:12px;text-align: center;padding: 0px 0px 0px 0px' class='bcssbtn'/>"+
					"</td>"+
				 "</tr>";
	$("#nodeTable tr:last").after(taxRow);
}

function up(obj){
	var objParentTR = $(obj).parent().parent();
	var prevTR = objParentTR.prev();
	var prevTRId = prevTR.attr("id");
	if(prevTRId=="beginTr"){
		return;
	}
	if (prevTR.length > 0) {
		prevTR.insertAfter(objParentTR);
	}
}

function down(obj){
	var objParentTR = $(obj).parent().parent();
	var nextTR = objParentTR.next();
	if (nextTR.length > 0) {
		nextTR.insertBefore(objParentTR);
	}
}

function delNodeTr(obj){//点击税务的删除按钮,删除一条税务信息
	$(obj).parent().parent().remove();
}

function initData(){
	var nodes =[];
	var nodeNameInputs = $(" input[mes='nodeName']");
	var roleIdInputs = $(" input[mes='roleId']");
	var processId = $("#proId").val();
	$.each(nodeNameInputs,function(index,item){
		var nodeObj = new Object();
		nodeObj.nodeName = $(item).val();
		nodeObj.roleId = $(roleIdInputs[index]).val();
		nodeObj.processId = processId;
		nodeObj.orderSeq = index*1000;
		nodes.push(nodeObj);
	});
	return nodes;
}

function checkRepeat(addRoleId,addNodeName){
	var repeat = false;
	var nodeNameInputs = $(" input[mes='nodeName']");
	var roleIdInputs = $(" input[mes='roleId']");
	$.each(nodeNameInputs,function(index,item){
		var nodeName = $(item).val();
		var roleId = $(roleIdInputs[index]).val();
		if(addRoleId==roleId&&addNodeName==nodeName){
			repeat = true;
		}
	});
	return repeat;
}

function saveNode(){
	var nodes = initData();
	if(nodes.length==0){
		parent.$.showMsg("至少存在一个节点!", 2);
		return;
	}
	tips = layer.msg("正在保存...", {icon : 16,time : 0, shade: [0.1]});
	$.ajax({
		url: "<%=path%>/supervisory/asyn/saveNode",
        type: "POST",
        contentType : 'application/json;charset=utf-8',
        dataType:"json",
        data: JSON.stringify(nodes),
        success: function(data){
            if(data.code){
				layer.close(tips);
				parent.$.showMsg("保存成功!", 1);
				closeFrame();
			}else{
				layer.close(tips);
				parent.$.showMsg("保存失败,系统出错!", 2);
			}
		},
        error: function(res){
            parent.$.showMsg("亲您的网络不给力哦~", 2);
		}
	});
}

function closeFrame(){
	window.parent.layer.close(window.parent.addNodeLayer);
}
</script>
</head>
<body>
   	<div class="nodeA"><h4><img src="<%=path%>/style/image/ico_table_01.png"/>节点信息：</h4>
		<table class="table_query"  cellpadding="0" cellspacing="0" rules="all">
			<tr>
				<td style="border:0;">
					<span>执行角色：</span>
					<span><select id="roleSelect" style="width: 150px"></select></span>
					<input type="hidden" id="proId" name="proId" value="${proId}"/>
				</td>
				<td style="border:0;">
					<span>节点名称：</span>
					<span><input maxlength="12"  id="addNodeName" name="addNodeName" type="text" class="dfinput_fb" style="width:140px;height:24px;"/></span>
				</td>
				<td style="border:0;">
					<input type="button" value="添加"  onclick="addNodeTr();" align="right" class="acssbtn" style="padding: 2px 6px 2px 6px" style="height:30px;font-size:15px;text-align: center;padding-top:0px; padding-bottom:0px"/>
					<input type="button" value="保存"  onclick="saveNode();" align="right" class="acssbtn" style="padding: 2px 6px 2px 6px" style="height:30px;font-size:15px;text-align: center;padding-top:0px; padding-bottom:0px"/>
				</td>
			</tr>
		</table>
	</div>
   	<div class="nodeB"><h4><img src="<%=path%>/style/image/ico_table_01.png"/>节点列表：</h4>
   		<table id="nodeTable" class="table_query" border="0" cellpadding="0" cellspacing="0" rules="all">
  				<tr id="beginTr">
   				<td align="center" width="30%">执行角色</td>
				<td align="center" width="40%">节点名称</td>
				<td align="center" width="30%">操作</td>
			</tr>
   		</table>
   	</div>
</body>
</html>
