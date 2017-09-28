<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%String path = request.getContextPath();%>
<%String processId = request.getParameter("proId");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<title>业务管理-环节指派</title>
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

var memberId = '${sessionScope.userInfo.empInfo.id}';
var isAdmin = $.getCookie("isAdmin");

$(function(){
	//给form表单赋缺省值
	$("#processId").val(processId);
	//查询业务下的环节
	InitNodeTable();
	
});
function InitNodeTable(){
	$("#NodeTable").jqGrid({
		url: '<%= path%>/business/asyn/getDepartmentNodeList?'+$("#nodeInfoForm").serialize(),datatype: 'json',
		colNames: ['环节id','业务id','环节名称','环节状态','执行人姓名','执行人部门id','执行人id','操作'],
		colModel: [
			{name:'nodeId',index:'nodeId',sortable:false,hidden:true},
			{name:'processIds',index:'processIds',sortable:false,align:"center",hidden:true },
			{name:'nodeName',index:'nodeName',sortable:false,hidden:false},
			{name:'nodeStatus',index:'nodeStatus',width:120,align:"center",formatter:statusFormat},
			{name:'Name',index:'Name',width:120,align:"center", formatter:nameFormat},
			{name:'departmentId',index:'departmentId',width:12,align:"center" ,hidden:true},
			{name:'adminId',index:'adminId',width:12,align:"center" ,hidden:true},
			{name:'opera',index:'opera',sortable:true,width:150,align:"center",formatter:operaFormat}
		],
		rowNum: 50,
		rowList: [50,100,200],
		pager: '#gridPager',
		sortname: 'nodeId',
		sortorder: "asc",
		rownumbers: true,
		rownumWidth: 50,
		caption: '环节列表',
		mtype: "GET",
		width:$(window).width()-40,
        height:$(window).height()-195,
		viewrecords: true,
		footerrow:true,
		loadComplete: function (data) {
			
		},
		hidegrid: false
	});
	jQuery("#NodeTable").jqGrid('navGrid',"#gridPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
	jQuery("#jqgh_NodeTable_rn").prepend("序号");
	
}
//操作列
function operaFormat(cellvalue,options,rowObject) {
	var nodeStatus = rowObject.nodeStatus;
	var nodeId = rowObject.nodeId;
	var departmentId = rowObject.departmentId;//该环节执行人的部门id
	var adminId = rowObject.adminId;//该环节执行人id
	var divH = "<div style='padding-left: 45px;'>";
	var divB = "</div>";
	var button = "";
	if(nodeStatus=="0"){ //未启动
		//button = "<input id='addNode' type='button' value='启动' onclick=\"qidong('"+nodeId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;' class='bcssbtn'/>&emsp;";
		button = button+"<input id='addNode' type='button' value='指派' onclick=\"editNamePage('"+nodeId+"','"+departmentId+"','"+adminId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;' class='bcssbtn'/>&emsp;";
	}else if(nodeStatus=="1"){
		//button = "<input id='addNode' type='button' value='交接' onclick=\"commitNode('"+nodeId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;' class='bcssbtn'/>&emsp;";
	}
	
	return divH + button + divB;
}

function statusFormat(cellvalue,options,rowObject) {
	var nodeStatus = rowObject.nodeStatus;
	var nodeId = rowObject.nodeId;
	var divH = "<div style='padding-left: 45px;'>";
	var divB = "</div>";
	var button = "";
	if(nodeStatus=="0"){ //未启动
		return"<font color='blue'>未启动</font>";
	}else if(nodeStatus=="1"){
		return"<font color='orange'>已启动</font>";
	}else if(nodeStatus=="2"){
		return"<font color='grey'>已完结</font>";
	}
}


//执行人姓名列
function nameFormat(cellvalue,options,rowObject) {
	var name = rowObject.Name;
	var nodeId = rowObject.nodeId;
	var departmentId = rowObject.departmentId;//该环节执行人的部门id
	var adminId = rowObject.adminId;//该环节执行人id
	var divH = "<div style='padding-left: 45px; font-color:red;'>";
	var divB = "</div>";
	var button = "<a href='#' onclick='editNamePage("+nodeId+","+departmentId+","+adminId+");'>"+name+"</a>";
	return divH + button + divB;
}

//进入指派业务-编辑执行页面
var editUserName;
function editNamePage(nodeId,departmentId,adminId){
	var url='<%=path%>/business/forward/gotoBusinessView?pageName=editNamePage&proId='+processId+'&nodeId='+nodeId+'&departmentId='+departmentId+'&isAdmin='+isAdmin+'&adminId='+adminId;
	editUserName = layer.open({
		title : '重新指派业务-重新选择执行人', 
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		maxmin: true,
		area : ['850px', '480px'],
		content : url,
		end : function(){// 关闭页面执行的操作 此处先空着
			searchfb();
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
				//alert("启动流程成功!");
				searchfb();
			}else {
				//alert("启动失败!请联系管理员");
				//closeFrame();
				searchfb();
			}
		},
		error : function(result) {
			alert("亲!您的网络不给力哦~");
			closeFrame();
		}
	});
	
}
//jiaojie环节 有协同的环节发送通知 并且通知下一个环节的审批人
function commitNode(nodeId){
	$.ajax( {   
		type : 'post',
		data : {nodeId:nodeId,processId:processId,nodeStatus:2},
		dataType : 'json',
		url : '<%=path%>/business/asyn/commitNode',
		beforeSubmit : function() {
			tips = layer.msg("正在交接环节...",{icon : 16,time : 0, shade: [0.1]});
		},
		success : function(result) {
			if(result.code==0){
				//alert("环节交接成功!");
				//closeFrame();
				searchfb();
			}else {
				//alert("环节交接失败!请联系管理员");
				//closeFrame();
				searchfb();
			}
		},
		error : function(result) {
			alert("亲!您的网络不给力哦~");
			//closeFrame();
			searchfb();
		}
	});
	
}



function zhipai(nodeId){
	
}

//查询
function searchfb(){
	
	jQuery("#NodeTable").jqGrid('setGridParam',{url:'<%=path%>/business/asyn/getDepartmentNodeList?'+$("#nodeInfoForm").serialize(),page:1}).trigger("reloadGrid");
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
	window.parent.layer.close(window.parent.queryBusi);
}
</script>
</head>
<body style="min-width:1100px;">
	<div id="orgPageRecover" style="width:95%;margin:25px 0 15px 20px;">
		<form id="nodeInfoForm" method="post">
		<input id="processId" name="processId" type="text" style = "display:none;" />
		</form>
		<table id="NodeTable"></table>
		<div id="gridPager"></div>
		
	</div>
	
	
</body>   
</html> 