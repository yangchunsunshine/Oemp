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
<title>代理记账订单查询</title>
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
		url : "<%=path%>/supervisory/asyn/getAccountOrderList",
		contentType : 'application/json',
		datatype : 'json',
	   	rownumbers:true,
		mtype: "post",
		rowNum:-1,
		//multiselect:true,
		autowidth:true,
		height:'280',
	   	colNames:['id','订单号','充值企业','充值金额','结算状态','充值类型'],
	   	colModel:[
	   		{name:'id',index:'id',width:120,hidden:true},
	   		{name:'code',index:'code',width:100,align:'center'},
	   		{name:'orgName',index:'orgName',width:120,align:'center'},
	   		{name:'basePackageMoney',index:'basePackageMoney',width:100,align:'center'},
	   		{name:'withStatus',index:'withStatus',width:100,align:'center',formatter:function(cellvalue, options, rowObject){
	   			var temp = "";
	   			if(cellvalue=="0"){
	   				temp = "【可结算】";
	   			}else if(cellvalue=="1"){
	   				temp = "【已结算】";
	   			}
				return temp;
			 }},
	   		{name:'mntPayType',index:'mntPayType',width:100,align:'center',formatter:function(cellvalue, options, rowObject){
	   			var temp = "";
	   			if(cellvalue=="1"){
	   				temp = "【直接充值】";
	   			}else if(cellvalue=="2"){
	   				temp = "【代理充值】";
	   			}
				return temp;
			 }}
	   	],
		viewrecords:true,
        rowNum:10,
        rowList:[10,20,30],
        pager:"#gridPager"
	});
});
</script>
</head>
<body>
<div class="re_top">
	<table cellpadding="0" cellspacing="0" border="0" class="re_table_wi">
    	<tr>
        	<td colspan="2" style="font-size:23px; color:#000;">未结算</td>
        </tr>
    	<tr>
        	<td colspan="2"><font style="font-size:29px;color:#F00">${requestScope.unsettlement}</font>　元</td>
        </tr>
    	<tr>
        	<td width="180">企业已结算<font style=" color:#000">　${requestScope.settlemented}　</font>元，</td>
        	<td width="180">企业总费用<font style=" color:#000">　${requestScope.totalAmount}　</font>元</td>
        </tr>
    </table>
 </div>
 <br>   
<table id="gridTable"></table>
<div id="gridPager"></div>
</body>
</html>
