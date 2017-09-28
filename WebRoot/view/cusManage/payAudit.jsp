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
<title>收费审批</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script type="text/javascript" src="<%=path%>/script/powerAuth.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<link type="text/css" rel="stylesheet" href="<%=path%>/style/css/style.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqueryui/jquery-ui.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqgrid/css/ui.jqgrid.css"/>
<script type="text/javascript">
var auditorLevel = '${auditorLevel}';
$(function(){
	var cusName = $("#cusName").val();
	var bDate = $("#bDate").val();
	var eDate = $("#eDate").val();
	$("#payAuditTable").jqGrid({
		url: '<%=path%>/supervisory/asyn/getAuditList?cusName='+cusName+'&bDate='+bDate+'&eDate='+eDate,
		datatype: 'json',
		colNames: ['orgId','expId','routeId','缴费日期','客户名称','收费时段','账本费','实收金额','状态','操作'],
		colModel: [
			{name:'orgId',index:'orgId',sortable:false,hidden:true},
			{name:'expId',index:'expId',sortable:false,hidden:true},
			{name:'routeId',index:'routeId',sortable:false,hidden:true},
			{name:'payDate',index:'payDate',sortable:true,width:38,align:"center"},
			{name:'orgName',index:'orgName',sortable:true,width:100,align:"left"},
			{name:'paySection',index:'paySection',sortable:true,width:80,align:"left"},
			{name:'bookFee',index:'bookFee',sortable:true,width:30,align:"center",formatter:bookFeeFmatter},
			{name:'realAmount',index:'realAmount',sortable:true,width:50,align:"right"},
			{name:'auditFlag',index:'auditFlag',sortable:true,width:60,align:"center",formatter:auditFlagFmatter},
			{name:'opera',index:'opera',width:100,sortable:true,align:"right",formatter:operaFmatter}
		],
		rowNum: 50,
		rowList: [50,100,200],
		pager: '#gridPager',
		sortname: 'ownerId',
		sortorder: "asc",
		rownumbers: true,
		rownumWidth: 50,
		caption: '收费审批列表',
		mtype: "POST",
		width:$(window).width()-40,
        height:$(window).height()-170,
		viewrecords: true,
		gridComplete: function (data) {
			authView()
		},
		hidegrid: false
	});
	jQuery("#payAuditTable").jqGrid('navGrid',"#gridPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
	jQuery("#jqgh_payAuditTable_rn").prepend("序号");
});

function bookFeeFmatter(cellvalue, options, rowObject){
	if(cellvalue==1){
		return"<font color='green'>【已交】</font>";
	}else{
		return"<font color='red'>【未交】</font>";
	}
}

function auditFlagFmatter(cellvalue, options, rowObject){
	var auditFlag = cellvalue;
	if(auditFlag==0){
			return "【结束】";
		}
	if(auditorLevel==88){
		return "【当前第"+auditFlag+"级审批】";
	}else{
		if(auditFlag==auditorLevel){
			return "【未审批】";
		}else{
			return "【已审批】";
		}
	}
}

function operaFmatter(cellvalue, options, rowObject){
	var divH = "<div  style='padding-right: 40px;'>";
	var divB = "</div>";
	var routeId = rowObject.routeId;
	var auditFlag = rowObject.auditFlag;
	var expId = rowObject.expId;
	var orgId = rowObject.orgId;
	var orgName = rowObject.orgName;
	var pass="<span id='Approval' style='padding-left:16px;display:none;'><a href='#'    onclick=\"passAudit('"+routeId+"','"+auditFlag+"','"+auditorLevel+"');\" style='color:blue;text-decoration:underline;'>审批</a></span>";
	var back="<span id='cancleApproval' style='padding-left:16px;display:none;'><a href='#' onclick=\"backAudit('"+routeId+"');\" style='color:blue;text-decoration:underline;'>驳回</a></span>";
	var del="<span id='canclePay' style='padding-left:16px;display:none;'><a href='#' onclick=\"delExp('"+expId+"');\" style='color:blue;text-decoration:underline;'>取消收费</a></span>";
	var detail="<span id='detail' style='padding-left:16px;display:none;'><a href='#' onclick=\"showPayAuditDetail('"+orgId+"','"+orgName+"');\" style='color:blue;text-decoration:underline;'>记录</a></span>";
	if(auditFlag==0){
			return divH+detail+divB;
	}
	if(auditorLevel==88){
		return divH+pass+back+del+detail+divB;
	}else{
		if(auditFlag==auditorLevel){
			return divH+pass+back+del+detail+divB;
		}else{
			return divH+detail+divB;
		}
	}
}

function passAudit(routeId,auditFlag,auditorLevel){
	layer.confirm("确认审批!", {icon: 3, title:"提示"}, function(index){
		tips = layer.msg("正在审批,请稍后...",{icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/passAudit',
		{
			'auditType':1,
			'routeId': routeId,
			'auditFlag': auditFlag,
			'auditorLevel': auditorLevel
		},
		function(result){
			if(result.code){
				layer.close(tips);
				searchfb();
				$.showMsg("审批成功!",1);
			}else{
				layer.close(tips);
				$.showMsg("审批失败,系统出错!",2);
			}
		});
		layer.close(index);
	});
}

function backAudit(routeId){
	layer.confirm("取消审批!", {icon: 3, title:"提示"}, function(index){
		tips = layer.msg("正在取消,请稍后...",{icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/backAudit',
		{
			'routeId': routeId
		},
		function(result){
			if(result.code){
				layer.close(tips);
				searchfb();
				$.showMsg("取消成功!",1);
			}else{
				layer.close(tips);
				$.showMsg("取消失败,系统出错!",2);
			}
		});
		layer.close(index);
	});
}

function delExp(expId){
	layer.confirm("取消收费!", {icon: 3, title:"提示"}, function(index){
		tips = layer.msg("正在取消,请稍后...",{icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/delExp',
		{
			'expId': expId
		},
		function(result){
			if(result.code){
				layer.close(tips);
				searchfb();
				$.showMsg("取消成功!",1);
			}else{
				layer.close(tips);
				$.showMsg("取消失败,系统出错!",2);
			}
		});
		layer.close(index);
	});
}

var payAuditDetailLayer;
function showPayAuditDetail(orgId,orgName){//进入跟进页面
	payAuditDetailLayer = layer.open({
		title : '历史记录:'+orgName,
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		maxmin: true,
		shadeClose: true,
		area : ['800px', '400px'],
		content : '<%=path%>/supervisory/forward/gotoPayAuditDetail?orgId='+orgId,
		end : function(){
			searchfb();
		},
		success : function(){
			//layer.full(addFollow);
		}
	});
}

function searchfb(){
	var cusName = $("#cusName").val();
	var bDate = $("#bDate").val();
	var eDate = $("#eDate").val();
	jQuery("#payAuditTable").jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getAuditList?cusName='+cusName+'&bDate='+bDate+'&eDate='+eDate,page:1}).trigger("reloadGrid");
}

</script>
</head>
<body style="min-width:1100px;">
	<div style="width:95%;margin:25px 0 15px 20px;">
		<div style="margin-bottom:15px;">
			<b>客户名称:</b>
			<input id="cusName" name="cusName" type="text" class="dfinput_fb" style="width:140px;height:24px;"/>&emsp;
			<b>收费日期:</b>
			<input id="bDate" name="bDate" type="text" readonly="readonly" class="Wdate dfinput_fb" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'});" style="width:100px;height:24px;"/>&nbsp;<b>至</b>&nbsp;
			<input id="eDate" name="eDate" type="text" readonly="readonly" class="Wdate dfinput_fb" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'});" style="width:100px;height:24px;"/>&emsp;
			<input class="bcssbtn" type="button" onclick="searchfb();" value="查询" style="padding: 2px 6px 2px 6px" style="height:30px;margin-left:0px;"/>
		</div>
		<table id="payAuditTable"></table>
		<div id="gridPager"></div>
	</div>
</body>   
</html> 