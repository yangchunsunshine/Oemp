<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<title>提成明细</title>
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
var tips;
var sdId = "${sdId}";
var edId = "${edId}";
var memberId = "${memberId}";
$(function(){
	tips = layer.msg("正在获取客户列表，请稍后...", {icon : 16,time : 0, shade: [0.1]});
	$.post('<%=path%>/supervisory/asyn/getCommissionDetail',{'sdId':sdId,'edId':edId,'memberId':memberId},function(result){
		layer.close(tips);
		var total = 0.00;
		var comTr = "<tr><td align='center' width='80%'><span>公司名称</span></td><td align='center' width='20%'><span>公司提成</span></td></tr>";
		for(var i=0;i<result.length;i++){
			comTr = comTr + "<tr><td align='left'><span>"+result[i].orgName+"</span></td>"+
											"<td align='right'><span>"+result[i].commission+"</span></td></tr>";
			total = total + parseFloat(result[i].commission);
		}
		comTr = comTr + "<tr><td align='center'><span>合计</span></td><td align='right'><span>"+total+"</span></td></tr>";
		$("#comTable").html(comTr);
	});
});
</script>
</head>
<body>
   	<div class="nodeB"><h4><img src="<%=path%>/style/image/ico_table_01.png"/>提成明细：</h4>
		<table id="comTable" class="table_query"  cellpadding="0" cellspacing="0" rules="all">
		</table>
	</div>
</body>
</html>
