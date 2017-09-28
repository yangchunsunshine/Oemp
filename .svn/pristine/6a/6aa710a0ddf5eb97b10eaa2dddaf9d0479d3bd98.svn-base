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
<title>员工绩效_结账信息页面</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/select2.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/i18n/zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/select2/css/select2.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/style/css/style.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqueryui/jquery-ui.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqgrid/css/ui.jqgrid.css"/>
<script type="text/javascript">
$(function(){
	$("select").select2({
		language: "zh-CN"
	});
	$("#settleDetailTable").jqGrid({
		url: '<%=path%>/supervisory/asyn/getSetttledOrgList?'+$("#settleDetailTableForm").serialize(),
		datatype: 'json',
		colNames: ['公司ID','公司编码','公司名称','账套名称','记账会计','会计电话','是否结账标识','是否结账','最后结账期间','凭证数量','最后录入时间'],
		colModel: [
			{name:'orgId',index:'orgId',sortable:false,hidden:true,cellattr:$.insertAttr},
			{name:'seqcode',index:'seqcode',width:70,sortable:true,align:"left",cellattr:$.insertAttr},
			{name:'orgName',index:'orgName',width:150,sortable:false,align:"left",cellattr:$.insertAttr},
			{name:'bookName',index:'bookName',width:150,sortable:false,align:"left"},
			{name:'userName',index:'userName',width:100,sortable:false,align:"left"},
			{name:'userTelphone',index:'userTelphone',width:100,sortable:true,align:"right"},
			{name:'isSettled',index:'isSettled',width:60,sortable:true,align:"right",hidden:true},
			{name:'isSettledName',index:'isSettledName',width:60,sortable:false,align:"right"},
			{name:'settleMonth',index:'settleMonth',width:85,sortable:false,align:"right"},
			{name:'vchNum',index:'vchNum',width:60,sortable:true,align:"right"},
			{name:'lastVch',index:'lastVch',width:120,sortable:true,align:"right",summaryType:'count',summaryTpl:'<b>共{0}家公司</b>'},
		],
		judgeId : "orgId",
		mergeCellFild : ["seqcode","orgName"],
		rowNum: 150,
		rowList: [150,200,250],
		pager: '#gridPager',
		sortname: 'orgId',
		sortorder: "asc",
		caption: '代帐公司记账情况一览',
		mtype: "GET",
		width:$(window).width()-40,
        height:$(window).height()-195,
		viewrecords: true,
		footerrow:true,
		loadComplete: function (data) {
			$(this).footerData("set",{"seqcode":"合计","orgName":"公司数量："+data.trendData.count+"家","vchNum":"数量："+data.trendData.summary1});//将合计值显示出来
		},
		gridComplete: function(){
			$(this).jqCellMerge();
	        var ids = $("#settleDetailTable").jqGrid('getDataIDs');
	        for(var i=0;i<ids.length;i++){
	        	var row=$("#settleDetailTable").jqGrid('getRowData',ids[i]);
	        	if(row["isSettled"]=="<spring:message code="ORG_IS_SETTLED_NTSETTLED"/>"){
	        		$("#settleDetailTable #"+ids[i]).css("color","gray");
		        }else if(row["isSettled"]=="<spring:message code="ORG_IS_SETTLED_ISSETTLED"/>"){
		        	$("#settleDetailTable #"+ids[i]).css("color","green"); 
			    }
	        }
	   	},
		hidegrid: false
	});
	jQuery("#settleDetailTable").jqGrid('navGrid',"#gridPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
});

//查询
function searchfb(){
	jQuery("#settleDetailTable").jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getSetttledOrgList?'+$("#settleDetailTableForm").serialize(),page:1}).trigger("reloadGrid");
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
		<form id="settleDetailTableForm" method="post">
			<div style="margin-bottom:15px;"><span><b>公司检索:&nbsp;</b><input name="orgName" placeholder="请输入客户公司名称" maxlength="5" type="text" class="dfinput_fb" style="width:140px;height:24px;"/>
				&emsp;<b>会计检索:&nbsp;</b><input name="fbUserName" onkeyup="autoSub(this);" maxlength="11" placeholder="请输入姓名或电话号" type="text" class="dfinput_fb" value="${settleTelphone}" style="width:140px;height:24px;"/>
				&emsp;<b>记账期间:&nbsp;</b><input name="lisence" maxlength="10" type="text" readonly="readonly" class="Wdate dfinput_fb" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM'});" value="${settleDate}" style="width:80px;height:24px;"/>
				&emsp;<b>结账状态:&nbsp;</b><select name="authState" style="width:80px;height:24px;font-size:14px;border:1px #ccc solid;">
					<option value="<spring:message code="STATE_ALL_INCLUDE"/>"><spring:message code="STATE_ALL_INCLUDE_NAME"/></option>
					<option value="<spring:message code="ORG_IS_SETTLED_NTSETTLED"/>"><spring:message code="ORG_IS_SETTLED_NTSETTLED_NAME"/></option>
					<option value="<spring:message code="ORG_IS_SETTLED_ISSETTLED"/>"><spring:message code="ORG_IS_SETTLED_ISSETTLED_NAME"/></option>
				</select>&emsp;&emsp;<input class="bcssbtn" style="padding: 2px 6px 2px 6px" type="button" onclick="searchfb()" value="<spring:message code="SERACH_SUBMIT"/>" style="height:30px;margin-left:0px;"/>
			</span></div>
		</form>
		<table id="settleDetailTable"></table>
		<div id="gridPager"></div>
	</div>
</body>  
</html> 