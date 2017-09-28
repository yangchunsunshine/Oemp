<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%String path = request.getContextPath();%>
<%String processId = request.getParameter("proId");%>
<%String nodeId = request.getParameter("nodeId");%>
<%String departmentId = request.getParameter("departmentId");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<title>业务管理-环节指派-编辑执行人姓名</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<link type="text/css" rel="stylesheet" href="<%=path%>/style/css/style.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqueryui/jquery-ui.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqgrid/css/ui.jqgrid.css"/>
<script type="text/javascript">
var processId = <%=processId%>;
var nodeId = <%=nodeId%>;
var departmentId = <%=departmentId%>;
var adminId = ${adminId};
$(function(){
	//给form表单赋缺省值
	$("#processId").val(processId);
	$("#nodeId").val(nodeId);
	$("#departmentId").val(departmentId);
	//加载和departmentId相同的 人员数据
	InitMemberTable();
	
});
function InitMemberTable(){
	$("#MemberTable").jqGrid({
		url: '<%= path%>/business/asyn/getDepartmentUserList?'+$("#nodeInfoForm").serialize(),datatype: 'json',
		colNames: ['id','姓名','电话','QQ','Email','性别','操作'],
		colModel: [
			{name:'id',index:'id',sortable:false,hidden:true},
			{name:'Name',index:'Name',sortable:false,align:"center",hidden:false },
			{name:'Telphone',index:'Telphone',sortable:false,hidden:false},
			{name:'qq',index:'nodeStatus',width:120,align:"center"},
			{name:'Email',index:'Name',width:120,align:"center"},
			{name:'sex',index:'sex',width:120,align:"center",formatter:sexFormat},
			{name:'opera',index:'opera',sortable:true,width:250,align:"center",formatter:operaFormat}
		],
		rowNum: 50,
		rowList: [50,100,200],
		pager: '#gridPager',
		sortname: 'id',
		sortorder: "asc",
		rownumbers: true,
		rownumWidth: 50,
		caption: '人员列表',
		mtype: "GET",
		width:$(window).width()-40,
        height:$(window).height()-195,
		viewrecords: true,
		footerrow:true,
		loadComplete: function (data) {
			
		},
		hidegrid: false
	});
	jQuery("#MemberTable").jqGrid('navGrid',"#gridPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
	jQuery("#jqgh_MemberTable_rn").prepend("序号");
	
}
//操作列
function operaFormat(cellvalue,options,rowObject) {
	var id = rowObject.id;//biz_member.id
	var divH = "<div style='padding-left: 45px;'>";
	var divB = "</div>";
	var button = "<input  type='button' value='确认指派' onclick=\"zhipai('"+id+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;' class='bcssbtn'/>&emsp;";
	return divH + button + divB;
}

//操作列
function sexFormat(cellvalue,options,rowObject) {
	if(cellvalue =="0"){
		return"男";
	}else if(cellvalue =="1"){
		return"男";
	}else{
		return"";
	}
}

//执行人姓名列
function nameFormat(cellvalue,options,rowObject) {
	var name = rowObject.Name;
	var nodeId = rowObject.nodeId;
	var processId = rowObject.processIds;
	var divH = "<div style='padding-left: 45px; font-color:red;'>";
	var divB = "</div>";
	var button = "<a href='#' onclick='editNamePage("+nodeId+");'>"+name+"</a>";
	return divH + button + divB;
}

//进入指派业务-编辑执行人页面
var editUserName;
function editNamePage(nodeId){
	
	editUserName = layer.open({
		title : '重新指派业务-编辑执行人', 
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		maxmin: true,
		area : ['650px', '480px'],
		content : '<%=path%>/business/forward/gotoBusinessView?pageName=editNamePage&proId='+processId+'&nodeId='+nodeId,
		end : function(){// 关闭页面执行的操作 此处先空着
			//searchfb();
		}
	});
}
//启动环节
function qidong(nodeId){
	$.ajax( {   
		type : 'post',
		data : {nodeId:nodeId,processId:processId,nodeStatus:1},
		dataType : 'json',
		url : '<%=path%>/business/asyn/startNode',
		beforeSubmit : function() {
			tips = layer.msg("正在更新...",{icon : 16,time : 0, shade: [0.1]});
		},
		success : function(result) {
			if(result.code==0){
				alert("启动流程成功!");
				closeFrame();
			}else {
				alert("启动失败!请联系管理员");
				closeFrame();
			}
		},
		error : function(result) {
			alert("亲!您的网络不给力哦~");
			closeFrame();
		}
	});
	
}


//确认重新指派人
function zhipai(id){//参数id为biz.member.id
	if(id==adminId ){
		parent.$.showMsg("环节指派失败!不能将环节指派给本人！",2);
		return;
	}
	$.ajax( {   
		type : 'post',
		data : {nodeId:nodeId,processId:processId,id:id},
		dataType : 'json',
		url : '<%=path%>/business/asyn/reAppoint',
		beforeSubmit : function() {
			tips = layer.msg("正在重新指派环节...",{icon : 16,time : 0, shade: [0.1]});
		},
		success : function(result) {
			if(result.code==0){
				parent.$.showMsg("环节指派成功!",1);
				closeFrame();
			}else {
				parent.$.showMsg("环节指派失败!请联系管理员!",1);
				closeFrame();
			}
		},
		error : function(result) {
			alert("亲!您的网络不给力哦~");
			closeFrame();
		}
	});
	
	
}

//查询
function searchfb(){
	
	jQuery("#MemberTable").jqGrid('setGridParam',{url:encodeURI('<%=path%>/business/asyn/getDepartmentUserList?'+$("#nodeInfoForm").serialize()),page:1}).trigger("reloadGrid");
}

function autoSub(obj){
    if(/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/.test(obj.value)){
    	searchfb();
    }
    if(obj.value==""){
    	searchfb();
    }
}



function closeFrame(){ //点击关闭按钮,关闭当前页面,并刷新父级页面
	//window.parent.thisGrid.trigger("reloadGrid");
	window.parent.layer.close(window.parent.editUserName);
}
</script>
</head>
<body style="min-width:1100px;">
	<div id="orgPageRecover" style="width:95%;margin:25px 0 15px 20px;">
		<div style="margin-bottom:15px;">
			<form id="nodeInfoForm" method="post">
			<input id="processId" name="processId" type="text" style = "display:none;" />
			<input id="nodeId" name="nodeId" type="text" style = "display:none;" />
			<input id="departmentId" name="departmentId" type="text" style = "display:none;" />
			
			<span><b>姓名:&nbsp;</b><input maxlength="11" onkeyup="" placeholder="请输入指派人的姓名" name="name" type="text" class="dfinput_fb" value="" style="width:140px;height:24px;"/>
			&emsp;
			
			<span><b>电话:&nbsp;</b><input maxlength="11" onkeyup="" placeholder="请输入指派人的电话" name="tel" type="text" class="dfinput_fb" value="" style="width:140px;height:24px;"/>
			&emsp;
			&emsp;<input class="bcssbtn" type="button" style="padding: 2px 6px 2px 6px" onclick="searchfb()" value="<spring:message code="SERACH_SUBMIT"/>" style="height:30px;margin-left:0px;"/>
			</form>
			<div>&nbsp;</div>
			<table id="MemberTable"></table>
			<div id="gridPager"></div>
		</div>	
	</div>
	
	
</body>   
</html> 