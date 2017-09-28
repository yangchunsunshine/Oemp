<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="com.wb.framework.commonUtil.PropertiesReader"%>
<%PropertiesReader reader = PropertiesReader.getInstance();%>
<%String url = reader.getValue("/com/wb/config/application", "ACC_ROLER_URL");%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<title>报税</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/zTree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/select2.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/i18n/zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/select2/css/select2.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style_mnt.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/jqueryui/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/jqgrid/css/ui.jqgrid.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/zTree/css/zTreeStyle.css" />
<script type=text/javascript>
var thisGrid;
var tips;
var ifSave = false;
$(function(){
	$("#orgNum").mustNumber();
	$("#taxDate").val(getDate());
	getTallyList();
	getTaxDetailList();
});

function getDate(){
	var myDate;
	var year = new Date().getFullYear();
	var month = new Date().getMonth()+1;
	if(month < 10) month = "0" + month;
	myDate = year+"-"+month;
	return myDate;
}

function getTallyList(){
	var orgId = $("#orgId").val();
	thisGrid = $("#tallyOrgList").jqGrid({
		url: '<%=path%>/supervisory/asyn/getTallyList',
		postData:{"orgId":orgId},
		datatype: 'json',
		colNames: ['公司ID','税务ID','公司编号','公司名称','联系电话','报税期间','报税总额','税金详情','短信通知状态','短信通知时间'],
		colModel: [
			{name:'orgId',index:'orgId',sortable:false,hidden:true},
			{name:'taxId',index:'taxId',sortable:false,hidden:true},
			{name:'orgNum',index:'orgNum',sortable:false},
			{name:'orgName',index:'orgName',sortable:true},
			{name:'tel',index:'tel',sortable:true,align:"right",formatter:showTele},
			{name:'taxDate',index:'taxDate',sortable:true,align:"center"},
			{name:'taxAmount',index:'taxAmount',sortable:true,align:"right",formatter:taxAmountFmatter},
			{name:'taxDetail',index:'taxDetail',sortable:false,hidden:true},
			{name:'isMsg',index:'isMsg',sortable:true,align:"center",formatter:isMsgFmatter},
			{name:'msgTime',index:'msgTime',sortable:true}
		],
		rowNum: 100,
		rowList: [100,150,200],
		pager: '#gridPager',
		mtype: "POST",
		width:$(window).width()-5,
		height:$(window).height()-208,
		viewrecords: true,
		multiselect: false,
		ondblClickRow: function(rowid){
			var taxDetail = $('#tallyOrgList').jqGrid('getRowData',rowid).taxDetail;
			checkTaxDetail(taxDetail);
		},
		loadComplete: function (data){},
		gridComplete: function(){},
		hidegrid: false
	});
	jQuery("#tallyOrgList").jqGrid('navGrid',"#gridPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
}

function taxDetailFmatter(cellvalue, options, rowObject){//暂时不使用
	var taxDetail = "<input type='button' value='查看' onclick=\"checkTaxDetail('" + cellvalue + "');\" style='height:25px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px' class='bcssbtn'/>";
	return taxDetail;
}

var showCheckTaxDetail;
function checkTaxDetail(taxDetail){
	if(taxDetail==null||taxDetail==""){
		taxDetail = "[无报税明细]";
	}
	var checkTaxDetailTableTr = "<tr><td align='center'>"+
									"报税明细："+
									"</td><td align='center' rowspan='2'>"+
									taxDetail+
									"</td></tr>"+
									"<tr><td align='center'>"+
									"<input type='button' value='关闭' onclick=\"closeCheckTaxDetail();\" align='right' class='acssbtn' style='padding: 2px 6px 2px 6px' style='height:30px;font-size:15px;text-align: center;padding-top:0px; padding-bottom:0px'/>"+
									"</td></tr>";
	$("#checkTaxDetailTable").html(checkTaxDetailTableTr);
	showCheckTaxDetail = layer.open({
		type : 1,
		title : '查看报税明细',
		shade : [ 0.1, '#000' ],
		fix : true,
		maxmin: true,
		shadeClose: true,
		area : ['800px', '110px'],
		content : $("#checkTaxDetail")
	});
}

function taxAmountFmatter(cellvalue, options, rowObject){
	var isMsg = rowObject.isMsg;
	var taxId = rowObject.taxId;
	var taxDetail = rowObject.taxDetail;
	var taxAmount = "";
	if(isMsg==1){
		taxAmount = cellvalue;
	}else{
		taxAmount = "<a title='编辑' onclick=\"getEditTaxDetailList('"+taxId+"','" + taxDetail + "');\"><span>"+cellvalue+"</span></a>";
	}
	return taxAmount;
}

var updateTaxDetail;
function getEditTaxDetailList(taxId,taxDetail){
			var editTaxTr = "";
			taxDetail = taxDetail.replace(/\:/g,"\":");
			taxDetail = taxDetail.replace(/\[/g,"{\"");
			taxDetail = taxDetail.replace(/\]/g,"}");
			taxDetail = taxDetail.replace(/\}{/g,"},{");
			taxDetail = "[" + taxDetail + "]";
			var jsonObj =  eval("(" + taxDetail + ")");
			for(var i in jsonObj){
				var obj = jsonObj[i];
				for(var key in obj){
					editTaxTr = editTaxTr+"<tr>"+
												"<td align='left'><input type='hidden' mes='tEditName' value='"+key+"'>"+key+"</td>"+
												"<td><input type='text' mes='editTax' onkeyup=\"addEditTax(	);\" onkeypress=\"return IsNum(event,$(this));\" value='"+obj[key]+"' maxlength='8' size='6'  style='text-align: right;width:84%'>元</td>"+
												"<td align='center'><input type='button' value='删除' onclick=\"deleteEditTaxTr(this);\" class='bcssbtn' style='padding: 0px 0px 0px 0px'/></td>"+
											"</tr>";
				}
			}
			var taxDetailTableTr = "<tr>"+
												"<td width='200px'><span>税种 ：</span>"+
												"<span><select name='taxEditKind' id='taxEditKind'>"+
												"<option value='1'>增值税（一般纳税人）</option>"+
												"<option value='2'>增值税（小规模）</option>"+
												"<option value='3'>消费税</option>"+
												"<option value='4'>营业税</option>"+
												"<option value='5'>企业所得税</option>"+
												"<option value='6'>城市维护建设税</option>"+
												"<option value='7'>教育费附加税</option>"+
												"<option value='8'>个人所得税</option>"+
												"<option value='9'>房产税</option>"+
												"<option value='10'>资源税</option>"+
												"<option value='11'>社保费</option>"+
												"<option value='12'>印花税</option>"+
												"</select></span>"+
												"</td>"+
												"<td width='100px'>"+
												"<span>金额 ：</span>"+
												"<span><input id='taxEditMoney' name='taxEditMoney'  maxlength='9' onkeypress=\"return IsNum(event,$(this));\" type='text' value='0' style='width:50px;' class='Deadline'/></span>"+
												"</td>"+
												"<td align='center' width='100px'>"+
												"<span><input type='button' value='添加' onclick=\"addEditTaxMoney();\" class='bcssbtn' style='padding: 0px 0px 0px 0px'/></span>"+
												"</td>"+
											"</tr>"+
											"<tr>"+
												"<td align='center'>报税种类</td>"+
												"<td align='center'>报税金额</td>"+
												"<td align='center'>操作</td>"+
											"</tr>"+
											editTaxTr+
											"<tr>"+
												"<td align='center'>合计：</td>"+
												"<td align='center' id='taxEditTotal' align='right' colspan='2'>0元<input id='finallyEditTax' type='hidden' value='0'/></td>"+
												"</tr>"+
												"<tr>"+
												"<td colspan='3' align='right'>"+
												"<span><input type='button' value='保存' onclick=\"editTaxAmount('"+taxId+"');\" align='center' class='acssbtn' style='padding: 2px 6px 2px 6px' style='height:30px;font-size:15px;text-align: center;padding-top:0px; padding-bottom:0px'/></span>"+
												"</td>"+
											"</tr>";
		$("#updateTaxDetailTable").html(taxDetailTableTr);
		addEditTax();
		updateTaxDetail = layer.open({
			type : 1,
			title : '编辑报税明细',
			shade : [ 0.1, '#000' ],
			fix : true,
			maxmin: true,
			area : ['500px', '300px'],
			content : $("#updateTaxDetail")
		});
}

function addEditTaxMoney(){
	var taxKind = $("#taxEditKind").val();
	var taxKindText = $("#taxEditKind").find("option:selected").text();
	var taxMoney = $("#taxEditMoney").val();
	taxMoney = Number(taxMoney);
	if(isNaN(taxMoney)){
		parent.$.showMsg("请输入有效金额!", 2);
		return;
	}
	var taxDetailTableTr = "<tr>"+
												"<td align='left'><input type='hidden' mes='tEditName' value='"+taxKindText+"'>"+taxKindText+"</td>"+
												"<td><input type='text' mes='editTax' onkeyup=\"addEditTax(	);\" readonly='readonly' value='"+taxMoney+"' maxlength='8' size='6'  style='text-align: right;width:84%'>元</td>"+
												"<td align='center'><input type='button' value='删除' onclick=\"deleteEditTaxTr(this);\" class='bcssbtn' style='padding: 0px 0px 0px 0px'/></td>"+
											"</tr>";
	$("#updateTaxDetailTable tr:eq(-3)").after(taxDetailTableTr);
	addEditTax();
}

function addEditTax(){
	var taxTotal = 0;
	var taxMoneyList = $("input[mes='editTax']");
	for(var i=0;i<taxMoneyList.length;i++){
		taxTotal += Number(taxMoneyList[i].value);
	}
	$("#taxEditTotal").html(taxTotal+"元"+"<input id='finallyEditTax' type='hidden' value='"+taxTotal+"'/>");
}

function deleteEditTaxTr(obj){
	$(obj).parent().parent().remove();
	addEditTax();
}

function editTaxAmount(taxId){
	var amount = $("#finallyEditTax").val();
	var taxMoneyList = $("input[mes='editTax']");
	var taxNameList = $("input[mes='tEditName']");
	var taxDetailValue = "";
	for(var i=0;i<taxMoneyList.length;i++){
			taxDetailValue = taxDetailValue + "[" + taxNameList[i].value + ":" + taxMoneyList[i].value + "]";
	}
	tips = layer.msg("正在更新...", {icon : 16,time : 0, shade: [0.1]});
	$.post('<%=path%>/supervisory/asyn/updateTally',{
		'taxId':taxId,
		'amount':amount,
		'taxDetail':taxDetailValue
	},function(result){
		if(result.code){
			layer.close(tips);
			parent.$.showMsg("更新成功!", 1, function(){
				window.layer.close(window.updateTaxDetail);
				thisGrid.jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getTallyList', page : 1}).trigger("reloadGrid");
			});
		}else{
			layer.close(tips);
			parent.$.showMsg("更新失败,系统出错!", 2);
		}
	});
}

function isMsgFmatter(cellvalue, options, rowObject){
	var taxId = rowObject.taxId;
	var operate = "";
	if(cellvalue==0){
		operate = "<a href='#' title='发送短信' onclick=\"sendMess('" + taxId + "');\"><img width='36px' src='<%=path%>/style/image/pushMess.png'/></a>";
	}else if(cellvalue==1){
		operate = "<img width='26px' src='<%=path%>/style/image/pushed.png'/>";
	}
	return operate;
}

var taxList;
function getTaxDetailList(){
	var taxDetailTableTr = "<tr>"+
												"<td width='200px'><span>税种 ：</span>"+
												"<span><select name='taxKind' id='taxKind'>"+
												"<option value='1'>增值税（一般纳税人）</option>"+
												"<option value='2'>增值税（小规模）</option>"+
												"<option value='3'>消费税</option>"+
												"<option value='4'>营业税</option>"+
												"<option value='5'>企业所得税</option>"+
												"<option value='6'>城市维护建设税</option>"+
												"<option value='7'>教育费附加税</option>"+
												"<option value='8'>个人所得税</option>"+
												"<option value='9'>房产税</option>"+
												"<option value='10'>资源税</option>"+
												"<option value='11'>社保费</option>"+
												"<option value='12'>印花税</option>"+
												"</select></span>"+
												"</td>"+
												"<td width='100px'>"+
												"<span>金额 ：</span>"+
												"<span><input id='taxMoney' name='taxMoney' maxlength='9' type='text' value='0' style='width:50px;' class='Deadline'/></span>"+
												"</td>"+
												"<td align='center' width='100px'>"+
												"<span><input type='button' value='添加' onclick=\"addTaxMoney();\" class='bcssbtn' style='padding: 0px 0px 0px 0px'/></span>"+
												"</td>"+
											"</tr>"+
											"<tr>"+
												"<td align='center'>报税种类</td>"+
												"<td align='center'>报税金额</td>"+
												"<td align='center'>操作</td>"+
											"</tr>"+
											"<tr>"+
												"<td align='center'>合计：</td>"+
												"<td align='center' id='taxTotal' align='right' colspan='2'>0元<input id='finallyTax' type='hidden' value='0'/></td>"+
												"</tr>"+
												"<tr>"+
												"<td colspan='3' align='right'>"+
												"<span><input type='button' value='保存' onclick=\"saveTaxDetail();\" align='center' class='acssbtn' style='padding: 2px 6px 2px 6px' style='height:30px;font-size:15px;text-align: center;padding-top:0px; padding-bottom:0px'/></span>"+
												"<span style='margin-left: 6%;'><input type='button' value='清空' onclick=\"clearTaxDetail();\" align='right' class='acssbtn' style='padding: 2px 6px 2px 6px' style='height:30px;font-size:15px;text-align: center;padding-top:0px; padding-bottom:0px'/></span>"+
												"</td>"+
											"</tr>";
		$("#taxDetailTable").html(taxDetailTableTr);
		$("#taxMoney").mustMoney();
}

function addTaxMoney(){
	var taxKind = $("#taxKind").val();
	var taxKindText = $("#taxKind").find("option:selected").text();
	var taxMoney = $("#taxMoney").val();
	taxMoney = Number(taxMoney);
	if(isNaN(taxMoney)){
		parent.$.showMsg("请输入有效金额!", 2);
		return;
	}
	var taxDetailTableTr = "<tr>"+
												"<td align='left'><input type='hidden' mes='tName' value='"+taxKindText+"'>"+taxKindText+"</td>"+
												"<td><input type='text' mes='tax' onkeyup=\"addTax(	);\" readonly='readonly' value='"+taxMoney+"' maxlength='8' size='6'  style='text-align: right;width:84%'>元</td>"+
												"<td align='center'><input type='button' value='删除' onclick=\"deleteTaxTr(this);\" class='bcssbtn' style='padding: 0px 0px 0px 0px'/></td>"+
											"</tr>";
	$("#taxDetailTable tr:eq(-3)").after(taxDetailTableTr);
	$("input[mes='tax']").mustMoney();
	addTax();
}

function deleteTaxTr(obj){
	$(obj).parent().parent().remove();
	addTax();
}

function addTax(){
	var taxTotal = 0;
	var taxMoneyList = $("input[mes='tax']");
	for(var i=0;i<taxMoneyList.length;i++){
		taxTotal += Number(taxMoneyList[i].value);
	}
	taxTotal = taxTotal.toFixed(2);
	$("#taxTotal").html(taxTotal+"元"+"<input id='finallyTax' type='hidden' value='"+taxTotal+"'/>");
}

function saveTaxDetail(){
	ifSave = true;
	$("#amount").val($("#finallyTax").val());
	var taxMoneyList = $("input[mes='tax']");
	var taxNameList = $("input[mes='tName']");
	var taxDetailValue = "";
	for(var i=0;i<taxMoneyList.length;i++){
			taxDetailValue = taxDetailValue + "[" + taxNameList[i].value + ":" + taxMoneyList[i].value + "]";
	}
	$("#taxDetail").val(taxDetailValue);
	closeTaxDetail();
}

function clearTaxDetail(){
	$("#taxMoney").val("0");
	$("#amount").val("0");
	$("#finallyTax").val("0");
	$("input[mes='tax']").parent().parent().remove();
	$("#taxTotal").html("0元<input id='finallyTax' type='hidden' value='0'/>");
	$("#taxDetail").val("");
	//closeTaxDetail();
}

var editTaxDetail;
function openEditTaxDetail(){
	editTaxDetail = layer.open({
		type : 1,
		title : '编辑报税明细',
		shade : [ 0.1, '#000' ],
		fix : true,
		maxmin: true,
		area : ['500px', '300px'],
		content : $("#editTaxDetail"),
		end : function(){
			if(!ifSave){
				clearTaxDetail();
			}
			ifSave = false;
		}
	});
}

function closeCheckTaxDetail(){
	window.layer.close(window.showCheckTaxDetail);
}

function closeTaxDetail(){
	window.layer.close(window.editTaxDetail);
}

function sendMess(taxId){
	var tel = $("#tel").val();
	if(tel==""||tel==null){
		parent.$.showMsg("联系电话不能为空，请在客户编辑页面修改手机号码!", 2);
		return;
	}
	tips = layer.msg("正在发送，请稍后...", {icon : 16,time : 0, shade: [0.1]});
	$.post('<%=path%>/supervisory/asyn/sendTaxNotice',{
		'taxId':taxId
	},function(result){
		if(result.code){
			parent.$.showMsg("发送成功!", 1, function(){
				layer.close(tips);
				thisGrid.jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getTallyList', page : 1}).trigger("reloadGrid");
			});
		}else{
			parent.$.showMsg(result.mess, 2,function(){
				layer.close(tips);
				thisGrid.jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getTallyList', page : 1}).trigger("reloadGrid");
			});
		}
	});
}

function saveTally(){
	var orgId = $("#orgId").val();
	var amount = $("#amount").val();
	var taxDate = $("#taxDate").val();
	var taxDetail = $("#taxDetail").val();
	var tel = $("#tel").val();
	if(tel==""||tel==null){
		parent.$.showMsg("手机号码不能为空，请在客户编辑页面修改手机号码!", 2);
		return;
	}
	if(taxDate==""||taxDate==null){
		parent.$.showMsg("请输入报税期间!", 2);
		return;
	}
	tips = layer.msg("正在保存...", {icon : 16,time : 0, shade: [0.1]});
	$.post('<%=path%>/supervisory/asyn/saveTally',{
		'orgId':orgId,
		'amount':amount,
		'taxDate':taxDate,
		'taxDetail':taxDetail
	},function(result){
		if(result.code==0){
			layer.close(tips);
			parent.$.showMsg("保存成功!", 1);
			thisGrid.jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getTallyList', page : 1}).trigger("reloadGrid");
			$("#flag").html("已报税");
		}else if(result.code==1){
			layer.close(tips);
			parent.$.showMsg("保存失败,系统出错!", 2);
		}else if(result.code==2){
			layer.close(tips);
			parent.$.showMsg("保存失败,该报税期间已报税!", 2);
		}
	});
}

function closeFrame(){//点击关闭按钮,关闭当前页面,并刷新父级页面
	window.parent.thisGrid.trigger("reloadGrid");
	window.parent.layer.close(window.parent.tally);
}
// 加载进来 显示手机是否隐藏
$(function(){
	var tel=$("#tel").val();
	var str=$("#tel").val().substring(4,8);
	$("#tel2").val(tel.replace(str,"****"));	
	//alert($("#tel2").val());
	//showTele();
	
});
// Jqgrid 显示电话
function showTele(tel){
	var phone=$("#phone").val();
	if(phone==1){
		tel=$("#tel2").val();
	}
	return tel;
}


</script>
</head>
<body>
	<input value="${tallyInfo[0].hiddenValue}" id="phone" type="hidden"/>
   	<div class="tallyA"><h4><img src="<%=path%>/style/image/ico_table_01.png"/>报税信息：</h4>
		<table class="table_query"  cellpadding="0" cellspacing="0" rules="all">
			<tr>
				<td>
					<span>公司名称：</span>
					<span>${tallyInfo[0].orgName}</span>
					<input type="hidden" id="orgId" name="orgId" value="${tallyInfo[0].orgId}"/>
				</td>
				<td>
					<span>报税期间：</span>
					<span><input type="text" id="taxDate" name="taxDate" onfocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM'});" value="" class="Wdate" style="margin-top:5px;" /></span>
				</td>
				<td>
					<span>联系电话：</span>
					<input type="hidden" id="tel" name="tel" value="${tallyInfo[0].tel}" readonly="readonly"/>
				<c:if test="${tallyInfo[0].hiddenValue!=1}">
					<span><input type="text" id="tel1" name="tel1" value="${tallyInfo[0].tel}" readonly="readonly"/></span>
				</c:if>
				<c:if test="${tallyInfo[0].hiddenValue==1}">
					<span><input type="text" id="tel2" name="tel2"  readonly="readonly"/></span>
				</c:if>
				</td>
			</tr>
			<tr>
				<td>
					<span>本月报税状态：</span>
					<span id="flag" style="color: red;">${flag}</span>
				</td>
				<td>
					<span>报税总额：</span>
					<span><input type="text" id="amount" onclick="openEditTaxDetail()" name="amount" value="0" readonly="readonly" />元</span>
					<input type="hidden" id="taxDetail" name="taxDetail"/>
				</td>
				<td>
					<input type="button" value="保存" onclick="saveTally();" align="right" class="acssbtn" style="padding: 2px 6px 2px 6px" style="height:30px;font-size:15px;text-align: center;padding-top:0px; padding-bottom:0px"/>&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="关闭" onclick="closeFrame();" align="right" class="acssbtn" style="padding: 2px 6px 2px 6px" style="height:30px;font-size:15px;text-align: center;padding-top:0px; padding-bottom:0px"/>
				</td>
			</tr>
		</table>
	</div>
   	<div class="tallyB"><h4><img src="<%=path%>/style/image/ico_table_01.png"/>报税列表<span style="color: red;">(双击查看税金详情)</span>：</h4>
   		<table id="tallyOrgList" class="table_query"  cellpadding="0" cellspacing="0" rules="all"></table>
   		<div id="gridPager" width="100%"></div>
   	</div>
   	<div id="editTaxDetail" style="display: none;">
   		<table id="taxDetailTable" class="table_query"  cellpadding="0" cellspacing="0" rules="all"></table>
	</div>
	<div id="checkTaxDetail" style="display: none;">
   		<table id="checkTaxDetailTable" class="table_query"  cellpadding="0" cellspacing="0" rules="all"></table>
	</div>
	<div id="updateTaxDetail" style="display: none;">
   		<table id="updateTaxDetailTable" class="table_query"  cellpadding="0" cellspacing="0" rules="all"></table>
	</div>
</body>
</html>
