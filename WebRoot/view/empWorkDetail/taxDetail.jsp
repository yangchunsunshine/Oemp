<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<title>员工绩效_报税信息页面</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
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
	$("#taxOrgDetailGrid").jqGrid({
           url: '<%=path%>/supervisory/asyn/getOrgTaxList?'+$("#taxOrgDetailGridForm").serialize(),
           datatype: 'json',
           colNames: ['公司ID','税号ID','公司编号','公司名称','报税期间','报税金额','税费详细','报税状态ID','报税状态','短信通知ID','短信通知状态','短信通知时间','记账会计','会计电话'],
           colModel: [
	           {name:'orgId',index:'orgId',sortable:false,hidden:true},
	           {name:'motId',index:'motId',sortable:false,hidden:true},
	           {name:'orgCode',index:'orgCode',width:70,sortable:true,align:"left"},
	           {name:'orgName',index:'orgName',width:280,sortable:false,align:"left"},
	           {name:'orgTaxPeriod',index:'orgTaxPeriod',width:85,sortable:false,align:"center"},
	           {name:'orgTaxAmount',index:'orgTaxAmount',width:100,sortable:true,align:"right"},
	           {name:'orgTaxDetail',index:'orgTaxDetail',width:100,sortable:false,align:"left"},
	           {name:'orgTaxState',index:'orgTaxState',sortable:true,hidden:true},
	           {name:'orgTaxStateName',index:'orgTaxStateName',width:100,sortable:false,align:"center"},
	           {name:'isMsgState',index:'isMsgState',sortable:true,hidden:true},
	           {name:'isMsgStateName',index:'isMsgStateName',width:100,sortable:false,align:"center"},
	           {name:'sendMsgDate',index:'sendMsgDate',width:120,sortable:true,align:"center"},
	           {name:'clerkName',index:'clerkName',width:60,sortable:true,align:"center"},
	           {name:'orgTelphone',index:'orgTelphone',width:100,sortable:true,align:"center"}
	          ],
		   rowNum: 150,
		   rowList: [150,200,500],
	       pager: '#gridPager',
		   sortname: 'orgTaxState',
		   sortorder: "desc",
		   caption: "公司报税信息详细信息",
		   mtype: "GET",
		   width:$(window).width()-40,
           height:$(window).height()-195,
           viewrecords: true,
           footerrow:true,
           loadComplete: function (data) {
        	   $(this).footerData("set",{"orgCode":"合计","orgName":"公司数量："+data.trendData.count+"家","orgTaxAmount":"金额："+data.trendData.summary1});//将合计值显示出来
	       },
		   gridComplete: function(){
	           var ids = $("#taxOrgDetailGrid").jqGrid('getDataIDs');
	           for(var i=0;i<ids.length;i++){
	        	   var row=$("#taxOrgDetailGrid").jqGrid('getRowData',ids[i]);
	        	   if(row["orgTaxState"]=="<spring:message code="ORG_TAX_STATE_PAYTAX"/>"){
	        		   if(row["isMsgState"]=="<spring:message code="ORG_MSG_STATE_NTSEND"/>"){
			               $("#taxOrgDetailGrid #"+ids[i]).css("color","red");
	                   }else if(row["isMsgState"]=="<spring:message code="ORG_MSG_STATE_IDSEND"/>"){
				           $("#taxOrgDetailGrid #"+ids[i]).css("color","green"); 
					   }
                   }else{
    		           $("#taxOrgDetailGrid #"+ids[i]).css("color","gray");
				   }
		       }
		   },
		   hidegrid: false
	});
	jQuery("#taxOrgDetailGrid").jqGrid('navGrid',"#gridPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
});

//查询
function searchfb(){
	jQuery("#taxOrgDetailGrid").jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getOrgTaxList?'+$("#taxOrgDetailGridForm").serialize(),page:1}).trigger("reloadGrid");
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
		<form id="taxOrgDetailGridForm" method="post">
			<div style="margin-bottom:15px;"><span><b>公司检索:&nbsp;</b><input name="orgName" maxlength="15" placeholder="请输入客户公司名称" type="text" class="dfinput_fb" style="width:140px;height:24px;"/>
				&emsp;<b>会计检索:&nbsp;</b><input maxlength="11" placeholder="请输入姓名或电话号" onkeyup="autoSub(this);" name="fbUserName" type="text" class="dfinput_fb" value="${taxDetailTelphone}" style="width:140px;height:24px;"/>
				&emsp;<b>报税期间:&nbsp;</b><input name="lisence" maxlength="10" type="text" readonly="readonly" class="Wdate dfinput_fb" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM'});" value="${taxDetailDate}" style="width:80px;height:24px;"/>
				&emsp;<b>报税状态:&nbsp;</b><select name="authState" style="width:80px;height:24px;font-size:14px;border:1px #ccc solid;">
					<option value="<spring:message code="STATE_ALL_INCLUDE"/>"><spring:message code="STATE_ALL_INCLUDE_NAME"/></option>
					<option value="<spring:message code="ORG_TAX_STATE_PAYTAX"/>"><spring:message code="ORG_TAX_STATE_PAYTAX_NAME"/></option>
					<option value="<spring:message code="ORG_TAX_STATE_DISTAX"/>"><spring:message code="ORG_TAX_STATE_DISTAX_NAME"/></option>
				</select>&emsp;<b>通知状态:&nbsp;</b><select name="isDeleted" style="width:100px;height:24px;font-size:14px;border:1px #ccc solid;">
					<option value="<spring:message code="STATE_ALL_INCLUDE"/>"><spring:message code="STATE_ALL_INCLUDE_NAME"/></option>
					<option value="<spring:message code="ORG_MSG_STATE_IDSEND"/>"><spring:message code="ORG_MSG_STATE_IDSEND_NAME"/></option>
					<option value="<spring:message code="ORG_MSG_STATE_NTSEND"/>"><spring:message code="ORG_MSG_STATE_NTSEND_NAME"/></option>
				</select>&emsp;&emsp;<input class="bcssbtn" style="padding: 2px 6px 2px 6px" type="button" onclick="searchfb()" value="<spring:message code="SERACH_SUBMIT"/>" style="height:30px;margin-left:0px;"/>
			</span></div>
		</form>
		<table id="taxOrgDetailGrid"></table>
		<div id="gridPager"></div>
	</div>
	<div id="amountDiv" style="display: none;">
		<div style="width:350px;height:303px; overflow-x:hidden ;">
			<table id="amountTable">
				
			</table>
		</div>
	</div>
</body>  
</html> 
