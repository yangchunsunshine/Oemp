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
<title>员工下代账公司费用统计</title>
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
	$("#clerkWorkDetailTable").jqGrid({
		url: '<%=path%>/supervisory/asyn/getClerkFeeListForReport?'+$("#clerkWorkDetailForm").serialize(),datatype: 'json',
		colNames: ['会计ID','公司ID','会计名称','会计电话','公司数量','收费总额','提成'],
		colModel: [
			{name:'ownerId',index:'ownerId',sortable:false,hidden:true},
			{name:'orgId',index:'orgId',sortable:false,hidden:true},
			{name:'legalperson',index:'legalperson',sortable:false,width:100,align:"left"},
			{name:'lptelphone',index:'lptelphone',width:120,align:"right"},
			{name:'orgNum',index:'orgNum',width:120,align:"right"},
			{name:'realAmount',index:'realAmount',width:120,align:"right"},
			{name:'commission',index:'commission',width:38,align:"center",formatter:commissionFmatter}
		],
		rowNum: 50,
		rowList: [50,100,200],
		pager: '#gridPager',
		sortname: 'ownerId',
		sortorder: "asc",
		rownumbers: true,
		rownumWidth: 50,
		caption: '会计维度统计收费总额',
		mtype: "GET",
		width:$(window).width()-40,
        height:$(window).height()-195,
		viewrecords: true,
		footerrow:true,
		loadComplete: function (data) {
			$(this).footerData("set",{
	            "legalperson":"合计"+data.trendData.count+"人",
	            "realAmount":"共"+data.trendData.summary2+"元",
	            "orgNum":"共"+data.trendData.summary4+"家"
	        });
		},
		hidegrid: false
	});
	jQuery("#clerkWorkDetailTable").jqGrid('navGrid',"#gridPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
	jQuery("#jqgh_clerkWorkDetailTable_rn").prepend("序号");
});

function commissionFmatter(cellvalue, options, rowObject){
	var memberId = rowObject.ownerId;
	if(memberId!=null){
		return "<a href='#' onclick=\"getCommission('"+memberId+"');\" style='color:blue;text-decoration:underline;'>查看</a>";
	}else{
		return "";
	}
}

function getCommission(memberId){
	var sdId = $("#sdId").val();
	var edId = $("#edId").val();
	layer.open({
		title : '提成明细', 
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		maxmin: true,
		shadeClose: true,
		area : ['550px', '480px'],
		content : '<%=path%>/supervisory/forward/gotoCommissionDetail?memberId='+memberId+'&sdId='+sdId+'&edId='+edId
	});
}

//查询
function searchfb(){
	if($("#sdId").val()>$("#edId").val()){
		parent.$.showMsg("起始日期不能大于结束日期!", 2);
		return;
	}
	jQuery("#clerkWorkDetailTable").jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getClerkFeeListForReport?'+$("#clerkWorkDetailForm").serialize(),page:1}).trigger("reloadGrid");
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
			<div style="margin-bottom:15px;">
				<span><b>会计检索:&nbsp;</b><input maxlength="11" onkeyup="autoSub(this);" placeholder="请输入姓名或电话号" name="userTelphone" type="text" class="dfinput_fb" value="" style="width:140px;height:24px;"/>
				&emsp;<b>收费日期:&nbsp;</b><input id="sdId" name="onDate" maxlength="7" type="text" readonly="readonly" class="Wdate dfinput_fb" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM'});" value="${requestScope.startDate}" style="width:80px;height:24px;"/>
				&nbsp;<b>至</b>&nbsp;&nbsp;<input id="edId" name="offDate" maxlength="7" type="text" readonly="readonly" class="Wdate dfinput_fb" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM'});" value="${requestScope.endDate}" style="width:80px;height:24px;"/>
				&emsp;<input class="bcssbtn" type="button" style="padding: 2px 6px 2px 6px" onclick="searchfb()" value="<spring:message code="SERACH_SUBMIT"/>" style="height:30px;margin-left:0px;"/>
				<input class="acssbtn" type="button" style="padding: 2px 6px 2px 6px" value="按公司统计" onclick="parent.addOptionTab('<%=path%>/supervisory/forward/gotoOrgFeeList','代账费统计');" style="height:30px;margin-left:0px;">
				</span>
			</div>
		</form>
		<table id="clerkWorkDetailTable"></table>
		<div id="gridPager"></div>
	</div>
</body>   
</html> 