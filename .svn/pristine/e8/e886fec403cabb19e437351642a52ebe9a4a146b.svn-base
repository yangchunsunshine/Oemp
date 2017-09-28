<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.wb.framework.commonUtil.PropertiesReader"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<title>确认提现</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqueryui/jquery-ui.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqgrid/css/ui.jqgrid.css"/>
<script type="text/javascript">
$(function() {
	$("#gridTable").jqGrid({
		url : "<%=path%>/supervisory/asyn/getOrderList",
		contentType : 'application/json',
		datatype : 'json',
	   	rownumbers:true,
		mtype: "post",
		rowNum:-1,
		multiselect:true,
		autowidth:true,
		height:'260',
	   	colNames:['id','订单号','充值企业','充值金额'],
	   	colModel:[
	   		{name:'id',index:'id',width:120,hidden:true},
	   		{name:'code',index:'code',width:100,align:'center'},
	   		{name:'companyName',index:'companyName',width:120,align:'center'},
	   		{name:'basePackageMoney',index:'basePackageMoney',width:100,align:'center'}
	   	],
		viewrecords:true,
        rowNum:10,
        rowList:[10,20,30],
        pager:"#gridPager"
	});
});	
	//postData:{"startTime":$("#startTime").val(),"endTime":$("#endTime").val()},	

function launchWithdrawal(){
	var selectedIds = $("#gridTable").jqGrid("getGridParam", "selarrrow");
	if(selectedIds==""){
		parent.$.showMsg("请选择要提现的订单!", 2);
		return ;
	}else{
		var orderIds = [];
		for(var i=0; i<selectedIds.length; i++){
			var rowData = $("#gridTable").jqGrid("getRowData",selectedIds[i]);
			orderIds[i]= rowData.id;
		}
		var loading=layer.load(1, {
			shade: [0.1,'#808080']
		});
		$.ajax({
			async: true,
			type: "POST",
			contentType:"application/json;charset=UTF-8",
			url: "<%=path%>/supervisory/asyn/launchWithdrawal",
			data: JSON.stringify(orderIds),
			dataType: "json",
			success: function(data){
				layer.close(loading);
				if(data.status=="no"){
					parent.$.showMsg("申请提现失败，提现金额大于企业余额!", 2);
				}else if(data.status=="false"){
				 	parent.$.showMsg("申请提现失败!", 2);
				}else if(data.status=="ok"){
					parent.$.showMsg("申请提现成功!", 1);
					window.location.reload(true);
					//$("#gridTable").jqGrid().trigger("reloadGrid");
				}
			},error:function(XMLResponse){
				layer.close(loading);
				//alert(XMLResponse.responseText)
			}
		});		
	}
}
</script>
</head>
<body>
<div class="re_top">
	<table cellpadding="0" cellspacing="0" border="0" class="re_table_wi">
    	<tr>
        	<td colspan="2" style="font-size:23px; color:#000;">可用余额</td>
        </tr>
    	<tr>
        	<td colspan="2"><font style="font-size:29px;color:#F00">${requestScope.balance}</font>　元</td>
        </tr>
    	<tr>
        	<td width="180">企业已提现<font style=" color:#000">　${requestScope.withdrawal}　</font>元，</td>
        	<td width="180">企业总金额<font style=" color:#000">　${requestScope.amount}　</font>元</td>
    	<tr>
        	<td colspan="2"><input type="button" value="   提现   " class="button_mo" onclick="launchWithdrawal()"></td>
        </tr>
    </table>
 </div>   
<table id="gridTable"></table>
<div id="gridPager"></div>
</body>
</html>
