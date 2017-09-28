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
<title>员工绩效_工作概览</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/select2.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/i18n/zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/select2/css/select2.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqueryui/jquery-ui.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqgrid/css/ui.jqgrid.css"/>
<script type="text/javascript">
$(function(){
	$("select").select2({
		language: "zh-CN"
	});
	$("#clerkWorkDetailTable").jqGrid({
		url: '<%=path%>/supervisory/asyn/getClerkWorkDetailList?'+$("#clerkWorkDetailForm").serialize(),
		datatype: 'json',
		colNames: ['员工ID','员工名称','员工电话','结账数量','报税数量','公司数量','结账率','报税率','报税金额','短信数量','凭证数量','最后录入','记账期间'],
		colModel: [
			{name:'ownerId',index:'ownerId',sortable:false,hidden:true},
			{name:'clerkName',index:'clerkName',width:90,align:"left"},
			{name:'clerkTelphone',index:'clerkTelphone',width:100,align:"left"},
			{name:'settleNum',formatter:formatSO,index:'settleNum',width:85,align:"center"},
			{name:'taxState',formatter:formatTS,index:'taxState',width:85,align:"center"},
			{name:'orgNum',index:'orgNum',width:75,align:"right"},
			{name:'settleRate',formatter:formatSTR,index:'settleRate',width:75,align:"right"},
			{name:'taxRate',formatter:formatSTR,index:'taxRate',width:75,align:"right"},
			{name:'taxAmount',index:'taxAmount',width:85,align:"right"},
			{name:'msgState',formatter:formatMG,index:'msgState',width:95,align:"right"},
			{name:'vchNum',index:'vchNum',width:95,align:"right"},
			{name:'lastVch',index:'lastVch',width:105,align:"right"},
			{name:'period',index:'period',width:80,sortable:false,align:"right",hidden:true}
		],
		rowNum: 50,
		rowList: [50,100,200],
		pager: '#gridPager',
		sortname: 'ownerId',
		sortorder: "asc",
		rownumbers: true,
		rownumWidth: 50,
		caption: '公司负责人管理列表',
		mtype: "GET",
		width:$(window).width()-40,
        height:$(window).height()-195,
		viewrecords: true,
		footerrow:true,
		loadComplete: function (data) {
			$(this).footerData("set",{
	            "clerkName":"合计"+data.trendData.count+"人",
	            "orgNum"   :"共"+data.trendData.summary1+"家",
	            "settleNum":"共"+data.trendData.summary2+"家",
	            "vchNum"   :"共"+data.trendData.summary3+"张",
	            "taxState" :"共"+data.trendData.summary4+"家",
	            "taxAmount":"总金额"+data.trendData.summary5,
	            "msgState" :"共"+data.trendData.summary6+"条"
	        });
		},
		hidegrid: false
	});
	jQuery("#clerkWorkDetailTable").jqGrid('navGrid',"#gridPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
	jQuery("#jqgh_clerkWorkDetailTable_rn").prepend("序号");
});

//去往结账详情
function formatSO(cellvalue, options, rowObject){
	cellvalue+='<span><label style="font-size:12px;">(</label><a href="javascript:void(0);" onclick="parent.clerkDetailDispather('+rowObject.clerkTelphone+",'"+rowObject.period+"'"+',0);" style="color:#34bcfb;"><label style="font-size:12px;cursor:pointer;"><spring:message code="COMMON_VIEW_TIP"/><label></a><label style="font-size:12px;">)</label></span>';
	return cellvalue;
}

//去往报税详情
function formatTS(cellvalue, options, rowObject){
	cellvalue+='<span><label style="font-size:12px;">(</label><a href="javascript:void(0);" onclick="parent.clerkDetailDispather('+rowObject.clerkTelphone+",'"+rowObject.period+"'"+',1);" style="color:#34bcfb;"><label style="font-size:12px;cursor:pointer;"><spring:message code="COMMON_VIEW_TIP"/><label></a><label style="font-size:12px;">)</label></span>';
	return cellvalue;
}

//短信比列
function formatMG(cellvalue, options, rowObject){
	cellvalue+='/'+rowObject.orgNum;
	return cellvalue;
}

//格式化百分比
function formatSTR(cellvalue, options, rowObject){
	return Number(cellvalue*100).toFixed(2)+"%";
}

//查询
function searchfb(){
	jQuery("#clerkWorkDetailTable").jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getClerkWorkDetailList?'+$("#clerkWorkDetailForm").serialize(),page:1}).trigger("reloadGrid");
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
<body style="min-width:1100px;">
	<div id="orgPageRecover" style="width:95%;margin:25px 0 15px 20px;">
		<form id="clerkWorkDetailForm" method="post">
			<div style="margin-bottom:15px;"><span>
			          <b>员工检索:&nbsp;</b><input maxlength="11" onkeyup="autoSub(this);" placeholder="请输入姓名或电话号" id="fbUserName" name="fbUserName" type="text" class="dfinput_fb" value="${clerkDetailTelphone}" style="width:140px;height:24px;"/>
				&emsp;<b>记账期间:&nbsp;</b><input name="lisence" maxlength="10" type="text" readonly="readonly" class="Wdate dfinput_fb" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM'});" value="${clerkDetailDate}" style="width:80px;height:24px;"/>
				&emsp;<b>结账进度:&nbsp;</b><select name="settleProcess" style="width:100px;height:24px;font-size:14px;border:1px #ccc solid;">
					<option value="0.0" selected="selected">全部</option>
					<option value="0.25">25%以上</option>
					<option value="0.5">50%以上</option>
					<option value="0.75">75%以上</option>
					<option value="1.0">100%完成</option>
				</select>&emsp;<b>报税进度:&nbsp;</b><select name="taxProcess" style="width:100px;height:24px;font-size:14px;border:1px #ccc solid;">
					<option value="0.0" selected="selected">全部</option>
					<option value="0.25">25%以上</option>
					<option value="0.5">50%以上</option>
					<option value="0.75">75%以上</option>
					<option value="1.0">100%完成</option>
				</select>&emsp;&emsp;<input class="bcssbtn" style="padding: 2px 6px 2px 6px" type="button" onclick="searchfb()" value="<spring:message code="SERACH_SUBMIT"/>" style="height:30px;margin-left:0px;"/>
			</span></div>
		</form>
		<table id="clerkWorkDetailTable"></table>
		<div id="gridPager"></div>
	</div>
</body>  
</html> 