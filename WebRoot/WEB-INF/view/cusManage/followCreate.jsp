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
<title>新增跟进记录</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style_mnt.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/jqgrid/css/ui.jqgrid.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/jqueryui/jquery-ui.css"/>
<script type="text/javascript">
$(function(){//页面初始化,获取跟进信息列表
	$("#tel").mustNumber();
	$("#saveInfo").show();
	$("#editInfo").hide();
	var cusId = $("#cusId").val();
	$("#followInfoTable").jqGrid({
	url: '<%=path%>/supervisory/asyn/getFollowInfo',
	postData:{"cusId":cusId},
	datatype: 'json',
	colNames: ['跟进ID','跟进时间','联系人','跟进内容','操作'],
	colModel: [
	{name:'FOLLOWID',index:'FOLLOWID',sortable:false,hidden:true},
	{name:'FOLLOWTIME',index:'FOLLOWTIME',sortable:true,align:"center"},
	{name:'CONTACTS',index:'CONTACTS',sortable:true,align:"left"},
	{name:'CONTENT',index:'CONTENT',sortable:true,align:"left"},
	{name:'OPERATE',index:'OPERATE',width:240,sortable:false,align:"center",formatter:currencyFmatter}
	],
	rowNum: 100,
	rowList: [100,150,200],
	pager: '#gridPager',
	mtype: "POST",
	width:$(window).width()-5,
	height:$(window).height()-270,
	viewrecords: true,
	multiselect: false,
	ondblClickRow: function(id){},
	loadComplete: function (data){},
	gridComplete: function(){},
	hidegrid: false
	});
	jQuery("#followInfoTable").jqGrid('navGrid',"#gridPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
});

function currencyFmatter (cellvalue, options, rowObject){//操作字段重新格式化
	var contacts = rowObject.CONTACTS;
	var tel = rowObject.TEL;
	var followTime = rowObject.FOLLOWTIME;
	var content = rowObject.CONTENT;
	content =content .replace("\r\n", "<br/>");
	var operate = "<div>"+
					"<input type='button' value='修改' onclick=\"toEditFollowInput('" + cellvalue + "','" + contacts + "','" + tel + "','" + followTime + "','" + content + "');\" class='bcssbtn' style='height:25px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px'/>"+
					"<input type='button' value='删除' onclick=\"deleteFollowInfo('" + cellvalue + "');\" class='bcssbtn' style='height:25px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px'/>"+
				  "</div>";
	return operate;
}

function saveFollowInfo(){//保存跟进信息
	var contacts = $("#contacts").val();
	var tel = $("#tel").val();
	var followTime = $("#followTime").val();
	var content = $("#content").val();
	if(contacts==null||contacts==""){
		parent.$.showMsg("请填写联系人!", 2);
		return;
	}
	if(tel==null||tel==""){
		parent.$.showMsg("请填写联系方式!", 2);
		return;
	}
	if(followTime==null||followTime==""){
		parent.$.showMsg("请选择跟进时间!", 2);
		return;
	}
	if(content==null||content==""){
		parent.$.showMsg("请填写跟进内容!", 2);
		return;
	}
	var tips;
	var options = {
		type : 'post',
		dataType : 'json',
		url:'<%=path%>/supervisory/asyn/saveFollowInfo',
		beforeSubmit : function() {
			tips = layer.msg("正在保存,请稍后...",{icon : 16,time : 0, shade: [0.1]});
		},
		success : function(result) {
			layer.close(tips);
			if(result.code){
				parent.$.showMsg("保存成功!", 1);
				resetFollowInfo();
				jQuery("#followInfoTable").jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getFollowInfo',page:1}).trigger("reloadGrid");
			}else{
				resetFollowInfo();
				parent.$.showMsg("保存失败,系统出错!", 2);
			}
		},
		error : function(result) {
			parent.$.showMsg("亲!您的网络不给力哦~", 2);
		}
	};
	$('#followInfoForm').ajaxSubmit(options);
}

function deleteFollowInfo(followId){//删除跟进信息
layer.confirm("确认删除?", {icon: 3, title:"提示"}, function(index){
	var tips = layer.msg("正在删除...",{icon : 16,time : 0, shade: [0.1]});
	$.post('<%=path%>/supervisory/asyn/deleteFollowInfo',{'followId' : followId},function(result){
			layer.close(tips);
			if(result.code){
				parent.$.showMsg("删除成功!", 1);
				resetFollowInfo();
				$("#saveInfo").show();
				$("#editInfo").hide();
				jQuery("#followInfoTable").jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getFollowInfo',page:1}).trigger("reloadGrid");
			}else{
				resetFollowInfo();
				$("#saveInfo").show();
				$("#editInfo").hide();
				parent.$.showMsg("删除失败,系统出错!", 2);
			}
		}
	);
  layer.close(index);
});
}

function toEditFollowInput(followId,contacts,tel,followTime,content){//编辑跟进信息（页面）
	content =content .replace("<br/>","\r\n");
	$("#editInfo").show();
	$("#saveInfo").hide();
	$("#contacts").attr("value",contacts);
	$("#tel").attr("value",tel);
	$("#followTime").attr("value",followTime);
	$("#content").attr("value",content);
	$("#followIdTemp").attr("value",followId);
}

function editFollowInfo(){//编辑跟进信息（数据库落地）
	var followId = $("#followIdTemp").val();
	var contacts = $("#contacts").val();
	var tel = $("#tel").val();
	var followTime = $("#followTime").val();
	var content = $("#content").val();
	if(contacts==null||contacts==""){
		parent.$.showMsg("请填写联系人!", 2);
		return;
	}
	if(tel==null||tel==""){
		parent.$.showMsg("请填写联系方式!", 2);
		return;
	}
	if(followTime==null||followTime==""){
		parent.$.showMsg("请选择跟进时间!", 2);
		return;
	}
	if(content==null||content==""){
		parent.$.showMsg("请填写跟进内容!", 2);
		return;
	}
	layer.confirm("确认修改?", {icon: 3, title:"提示"}, function(index){
	var tips = layer.msg("正在修改...",{icon : 16,time : 0, shade: [0.1]});
	$.post('<%=path%>/supervisory/asyn/editFollowInfo',{'followId':followId,'contacts':contacts,'tel':tel,'followTime':followTime,'content':content},function(result){
			layer.close(tips);
			if(result.code){
				parent.$.showMsg("修改成功!", 1);
				resetFollowInfo();
				$("#saveInfo").show();
				$("#editInfo").hide();
				jQuery("#followInfoTable").jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getFollowInfo',page:1}).trigger("reloadGrid");
			}else{
				resetFollowInfo();
				$("#saveInfo").show();
				$("#editInfo").hide();
				parent.$.showMsg("修改失败,系统出错!", 1);
			}
		}
	);
  layer.close(index);
});
}

function resetFollowInfo(){//重置按钮
		$("#contacts").attr("value","");
		$("#tel").attr("value","");
		$("#followTime").attr("value","");
		$("#content").attr("value","");
}

</script>
</head>

<body>
	<form id="followInfoForm" enctype="multipart/form-data" method="POST">
		<div>
			<table id="followInfoEditTable" width="100%">
				<tr>
					<td align="left"><font>公司名称：</font></td>
					<td align="left"><font>${orgName}</font>
						<input type="hidden" id="cusId" name="cusId" value="${cusId}">
						<input type="hidden" id="followIdTemp" name="followIdTemp">
					</td>
					<td align="left"><font>跟进人：</font></td>
					<td align="left"><font>${userName}</font>
					</td>
				</tr>
				<tr>
					<td align="left"><font>联系人：</font>
					</td>
					<td align="left">
						<input type="text" id="contacts" name="contacts" class="txt140" maxlength="10"/>
					</td>
					<td align="left"><font>联系方式：</font>
					</td>
					<td align="left">
						<input name="tel" type="text" id="tel" class="txt140" maxlength="11"/>
					</td>
				</tr>
				<tr>
					<td align="left"><font>跟进时间：</font>
					</td>
					<td align="left" colspan="3">
						<input type="text" id="followTime" name="followTime" onfocus="WdatePicker()" class="Wdate"/>
					</td>
				</tr>
				<tr>
					<td align="left"><font>跟进内容：</font>
					</td>
					<td colspan="3" align="left">
						<textarea id="content" name="content" class="txt450"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="4" align="right">
						<div id="saveInfo" style="display:inline;">
							<input type="button" value="保存" class="bcssbtn" style="padding: 0px 0px 0px 0px" id="reset" onclick="saveFollowInfo();"/>
						</div>
						<div id="editInfo" style="display:inline;">
							<input type="button" value="修改" class="bcssbtn" style="padding: 0px 0px 0px 0px" id="reset" onclick="editFollowInfo();"/>
						</div>
						<div style="display:inline;">
							<input type="button" value="重置" class="bcssbtn" style="padding: 0px 0px 0px 0px" id="reset" onclick="resetFollowInfo();"/>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
	<h4>跟进列表：</h3>
	<table id="followInfoTable" width="100%"></table>
	<div id="gridPager" width="100%"></div>
</body>
</html>