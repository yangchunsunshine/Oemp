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
<title>业务管理-进度查询</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<link type="text/css" rel="stylesheet" href="<%=path%>/style/css/style.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqueryui/jquery-ui.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqgrid/css/ui.jqgrid.css"/>
<script type="text/javascript">
$(function(){
	initProcessInfo();
	
});
function initProcessInfo(){
	$("#processInfoTable").jqGrid({
		url: '<%=path%>/business/asyn/getProcessList?'+$("#processInfoForm").serialize(),datatype: 'json',
		colNames: ['业务编号','公司编号','业务名称','客户名称','业务类型','操作'],
		colModel: [
			{name:'proId',index:'proId',sortable:false,hidden:true},
			{name:'orgId',index:'orgId',sortable:false,hidden:true ,align:"center"},
			{name:'processName',index:'processName',sortable:false,width:100,align:"center"},
			{name:'orgName',index:'orgId',sortable:false,width:90,hidden:false ,align:"center"},
			{name:'contractType',index:'contractType',width:50,align:"center",formatter:contractFormat},
			{name:'opera',index:'opera',sortable:true,width:150,align:"center",formatter:operaFormat}
		],
		rowNum: 50,
		rowList: [50,100,200],
		pager: '#gridPager',
		sortname: 'proId',
		sortorder: "asc",
		rownumbers: true,
		rownumWidth: 50,
		caption: '业务列表',
		mtype: "POST",
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		width:$(window).width()-40,
        height:$(window).height()-195,
		viewrecords: true,
		footerrow:true,
		loadComplete: function (data) {
			
		},
		hidegrid: false
	});
	jQuery("#processInfoTable").jqGrid('navGrid',"#gridPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
	jQuery("#jqgh_processInfoTable_rn").prepend("序号");
	
}

function contractFormat(cellvalue,options,rowObject){
	
	
	if(cellvalue =="200101"){
		return"<font color='green'>代理记账</font>";
	}else if(cellvalue =="200100"){
		return"<font color='green'>工商注册</font>";
	}else if(cellvalue =="200102"){
		return"<font color='green'>法律咨询</font>";
	}else if(cellvalue =="200103"){
		return"<font color='green'>人事代理</font>";
	}else if(cellvalue =="200104"){
		return"<font color='green'>商标专利</font>";
	}else if(cellvalue =="200105"){
		return"<font color='green'>其他</font>";
	}else{
		return"<font color='green'>未定义</font>";
	}
}


function operaFormat(cellvalue,options,rowObject) {
	var proId = rowObject.proId;
	var isAdmin = rowObject.isAdmin;
	var divH = "<div style='padding-left: 45px;'>";
	var divB = "</div>";
	var addNode = "<input id='selectNode' type='button' value='查看' onclick=\"goNodePage('"+proId+"','"+isAdmin+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;' class='bcssbtn'/>&emsp;";
	var btn2 = "<input id='assign' type='button' value='指派' onclick=\"zhiPaiNodePage('"+proId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;' class='bcssbtn'/>&emsp;";
	var flowbtn = "<input id='viewFlowChart' type='button' value='查看流程图' onclick=\"gotoFlowPage('"+proId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;' class='bcssbtn'/>&emsp;";
	return divH + addNode + btn2 + flowbtn + divB;
}




//查询
function searchfb(){
	
	jQuery("#processInfoTable").jqGrid('setGridParam',{url:encodeURI('<%=path%>/business/asyn/getProcessList?'+$("#processInfoForm").serialize()),page:1}).trigger("reloadGrid");
}

function autoSub(obj){
    if(/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/.test(obj.value)){
    	searchfb();
    }
    if(obj.value==""){
    	searchfb();
    }
}


//查看业务进度
var queryBusi;
function goNodePage(proId,isAdmin){//进入查看业务信息页面
	queryBusi = layer.open({
		title : '查看业务进度', 
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		maxmin: true,
		area : ['850px', '580px'],
		content : '<%=path%>/business/forward/gotoBusinessView?pageName=queryNode&proId='+proId+'&isAdmin='+isAdmin,
		end : function(){// 关闭页面执行的操作 此处先空着
			searchfb();
		}
	});
}




//进入指派业务页面
var queryBusi2;
function zhiPaiNodePage(proId){
	queryBusi2 = layer.open({
		title : '重新指派业务', 
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		maxmin: true,
		area : ['950px', '580px'],
		content : '<%=path%>/business/forward/gotoBusinessView?pageName=zhiPaiNodePage&proId='+proId,
		end : function(){// 关闭页面执行的操作 此处先空着
			searchfb();
		}
	});
}



var flowPage;
function gotoFlowPage(processId){
	flowPage = layer.open({
		title : '流程图', 
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		maxmin: true,
		area : ['650px', '350px'],
		content : '<%=path%>/business/forward/gotoBusinessView?pageName=flowPage&proId='+processId,
		end : function(){// 关闭页面执行的操作 此处先空着
			searchfb();
		}
	});
}


</script>
</head>
<body style="min-width:1100px;">
	<div id="orgPageRecover" style="width:95%;margin:25px 0 15px 20px;">
		<form id="processInfoForm" method="post">
			<div style="margin-bottom:15px;">
				<span><b>客户:&nbsp;</b><input maxlength="11" onkeyup="" placeholder="请输入客户名称" name="cusName" type="text" class="dfinput_fb" value="" style="width:140px;height:24px;"/>
				&emsp;
				<span><b>业务名称:&nbsp;</b><input maxlength="11" onkeyup="" placeholder="请输入业务名称" name="processName" type="text" class="dfinput_fb" value="" style="width:140px;height:24px;"/>
				&emsp;
				<span style="font-size: 15px">业务类型:&nbsp;&nbsp;</span><select id="conType" name="conType"  style="border:1px solid #ccc;width:90px;height:26px;">
						<option value="-1" selected="selected">全部</option>
						<option value="200101" >代理记账</option>
							<option value="200100">工商注册</option>
							<option value="200102">法律咨询</option>
							<option value="200103">人事代理</option>
							<option value="200104">商标专利</option>
							<option value="200105">其他</option>
				</select>
				
				&emsp;
				<span style="font-size: 15px">合同类型:&nbsp;&nbsp;</span><select id="contractType" name="contractType"  style="border:1px solid #ccc;width:90px;height:26px;">
						<option value="-1" selected="selected">全部</option>
						<option value="0" >无合同</option>
						<option value="1">有合同</option>
				</select>
				
				<input class="bcssbtn" type="button" style="padding: 2px 6px 2px 6px" onclick="searchfb();" value="查询" style="height:30px;margin-left:100px;" />
				
				</span>
			</div>
		</form>
		<table id="processInfoTable"></table>
		<div id="gridPager"></div>
	</div>
</body>   
</html> 