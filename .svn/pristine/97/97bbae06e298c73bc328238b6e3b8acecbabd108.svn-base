<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/bootstrap/js/Chart.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/bootstrap/css/bootstrap.min.css"/>
<script type="text/javascript">
var myNewChart = null;
window.onload = function(){
	var ctx = document.getElementById("mngOrgCanvas").getContext("2d");
	window.myLine = myNewChart;
}

$(function(){
	refrashChart();
});

//刷新走势图
function refrashChart(){
	$("#searchRunning").val("查询中...");
	$.ajax({
		url:'<%=path%>/supervisory/asyn/getOrgRunningInfoList',
		data:$("#rInfoForm").serialize(),
		type:'post',
		dataType:'json',
		success:function(showData){
			$("#searchRunning").val("统计报表");
			if(myNewChart!=null){
				myNewChart.destroy();
			}
			$("#totalCount").html(showData.totalCount);
			var lineChartData = {
				labels : showData.strArray,
				datasets : [{
						label: "税金或凭证量走势图",
						fillColor    : "rgba(151,187,205,0.2)",
						strokeColor  : "rgba(151,187,205,1)",
						pointColor   : "rgba(151,187,205,1)",
						pointStrokeColor     : "#fff",
						pointHighlightFill   : "#fff",
						pointHighlightStroke : "rgba(151,187,205,1)",
						data : showData.intArray
					}
				]
			}
			var ctx = document.getElementById("mngOrgCanvas").getContext("2d");
			myNewChart = new Chart(ctx).Line(lineChartData,{responsive: true});
		},
		error:function(jqXHR, textStatus, errorThrown ){
			parent.$.showMsg("休息一下哦~亲~",  5)
		}
	});
}
</script>
</head>
<body >
	<div style="padding:8px 0px 20px 0px;width:100%;height:99%;">
	  	<div id="replaceShow" style="width:100%;height:99%;">
			<div style="width:73%;height:108%;float:left;"><canvas id="mngOrgCanvas" style="width:100%;height:100%;border: 1px solid #CCC;"></canvas></div>
			<div style="width:27%;height:108%;float:right;padding-left:10px;padding-right:2px;">
			<form id="rInfoForm">
			<table style="width:100%;height:100%;border: 1px solid #CCC;">
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td align="right"><span style="font-size: 15px">起始月份:</span></td><td>
						<input name="sidx" maxlength="10" type="text" readonly="readonly" class="Wdate dfinput_fb" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM'});" value="${sidxDate}" style="width:100px;height:25px;"/>
					</td>
				</tr>
				<tr>
					<td align="right"><span style="font-size: 15px">结束月份:</span></td><td>
						<input name="sord" maxlength="10" type="text" readonly="readonly" class="Wdate dfinput_fb" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM'});" value="${sordDate}" style="width:100px;height:25px;"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<span style="font-size: 15px">统计选项:</span>
					</td>
					<td>
						<select name="authState" style="width:100px;height:25px;font-size:14px;border:1px #ccc solid;">
							<option value="2" selected="selected">缴费金额</option>
							<option value="0">税金总额</option>
							<option value="1" >凭证数量</option>
						</select>
					</td>
				</tr>
				<tr style="height:50px;">
					<td colspan="2" align="center">
						<input id="searchRunning" type="button" onclick="refrashChart();" style="width:80px;" class="btn-sm btn-primary" value="查询报表"/>
					</td>
				</tr>
				<tr style="height:30px;"><td colspan="2" align="center" style="color:red;font-size:12px;"><b>合计(总额):</b><label id="totalCount"></label></td></tr>
			</table>
			</form>
			</div>
		</div>
	</div>
</body>
</html>
