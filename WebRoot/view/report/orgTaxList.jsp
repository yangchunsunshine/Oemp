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
<title>公司报税金额统计页面(主页面)</title>
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
	$("#settleDetailTable").jqGrid({
		url: '<%=path%>/supervisory/asyn/getOrgTaxListForReport?'+$("#settleDetailTableForm").serialize(),datatype: 'json',
		colNames: ['会计ID','公司ID','关联ID','公司编码','公司名称','报税总额','报税次数','发送短信数量','记账会计','会计电话'],
		colModel: [
			{name:'userId',index:'userId',sortable:false,hidden:true},
			{name:'orgId',index:'orgId',sortable:false,hidden:true},
			{name:'motId',index:'motId',sortable:false,hidden:true},
			{name:'seqCode',index:'seqCode',width:70,sortable:true,align:"left"},
			{name:'orgName',index:'orgName',width:180,sortable:true,align:"left"},
			{name:'orgTaxAmount',index:'orgTaxAmount',width:120,sortable:true,align:"right"},
			{name:'orgTaxState',index:'orgTaxState',width:120,sortable:true,align:"right"},
			{name:'isMsgState',index:'isMsgState',width:120,sortable:true,align:"right"},
			{name:'clerkName',index:'clerkName',width:100,sortable:true,align:"left"},
			{name:'orgTelphone',index:'orgTelphone',width:100,sortable:true,align:"left"}
		],
		rowNum: 150,
		rowList: [150,200,250],
		pager: '#gridPager',
		sortname: 'orgTaxState',
		sortorder: "desc",
		caption: '公司维度统计报税总额',
		mtype: "GET",
		width:$(window).width()-40,
        height:$(window).height()-195,
		viewrecords: true,
		footerrow:true,
		loadComplete: function (data) {
			$(this).footerData("set",{"seqcode":"合计","orgName":"公司数量："+data.trendData.count+"家","orgTaxAmount":"报税总额："+data.trendData.summary1,"orgTaxState":"报税次数总计："+data.trendData.summary2,"isMsgState":"短信通知数量："+data.trendData.summary3});//将合计值显示出来
		},
		hidegrid: false
	});
	jQuery("#settleDetailTable").jqGrid('navGrid',"#gridPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
});
//查询
function searchfb(){
	if($("#sdId").val()>$("#edId").val()){
		parent.$.showMsg("起始日期不能大于结束日期!", 2);
		return;
	}
	jQuery("#settleDetailTable").jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getOrgTaxListForReport?'+$("#settleDetailTableForm").serialize(),page:1}).trigger("reloadGrid");
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
			<div style="margin-bottom:15px;">
				<span><b>公司检索:&nbsp;</b><input name="orgName" placeholder="请输入客户公司名称" maxlength="5" type="text" class="dfinput_fb" style="width:140px;height:24px;"/>
				&emsp;<b>会计检索:&nbsp;</b><input maxlength="11" onkeyup="autoSub(this);" placeholder="请输入姓名或电话号" name="userTelphone" type="text" class="dfinput_fb" value="${settleTelphone}" style="width:140px;height:24px;"/>
				&emsp;<b>记账期间:&nbsp;</b><input id="sdId" name="onDate" maxlength="7" type="text" readonly="readonly" class="Wdate dfinput_fb" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM'});" value="${requestScope.startDate}" style="width:80px;height:24px;"/>
				&nbsp;<b>至</b>&nbsp;&nbsp;<input id="edId" name="offDate" maxlength="7" type="text" readonly="readonly" class="Wdate dfinput_fb" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM'});" value="${requestScope.endDate}" style="width:80px;height:24px;"/>
				&emsp;<input class="bcssbtn" type="button" style="padding: 2px 6px 2px 6px" onclick="searchfb()" value="<spring:message code="SERACH_SUBMIT"/>" style="height:30px;margin-left:0px;"/>
				<input class="acssbtn" type="button" style="padding: 2px 6px 2px 6px" value="按会计统计" onclick="parent.addOptionTab('<%=path%>/supervisory/forward/gotoClerkTaxList','报税金统计');" style="height:30px;margin-left:0px;">
			</span></div>
		</form>
		<table id="settleDetailTable"></table>
		<div id="gridPager"></div>
	</div>
</body>   
</html> 