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
<title>新增合同</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style_mnt.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/jqgrid/css/ui.jqgrid.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/jqueryui/jquery-ui.css"/>
<script type="text/javascript">
$(function(){//页面初始化,获取跟进信息列表
	$("#saveInfo").show();
	$("#editInfo").hide();
	$("#fileNone").show();
	$("#fileHave").hide();
	$("#monthCost").mustNumber();
	$("#accBookCost").mustNumber();
	$("#commission").mustMoney();
	$("#discount").mustMoney();
	var cusId = $("#cusId").val();
	$("#contractInfoTable").jqGrid({
		url: '<%=path%>/supervisory/asyn/getContractInfo',
		postData:{"cusId":cusId},
		datatype: 'json',
		colNames: ['合同ID','能否修改','签约日期','附件','付款方式','备注','类型','客户名称','期限始','期限末','代账费','折扣率','账本费','会计提成','操作'],
		colModel: [
		{name:'CONTRACTID',index:'CONTRACTID',width:10,sortable:false,hidden:true},
		{name:'canEdit',index:'canEdit',width:10,sortable:false,hidden:true},
		{name:'SIGNTIME',index:'SIGNTIME',width:10,sortable:false,hidden:true,formatter:dateFmatterD},
		{name:'HAVEFILE',index:'HAVEFILE',width:10,sortable:false,hidden:true},
		{name:'PAYTYPE',index:'PAYTYPE',width:10,sortable:false,hidden:true},
		{name:'DEMO',index:'DEMO',width:10,sortable:false,hidden:true},
		{name:'CONTRACTTYPE',index:'CONTRACTTYPE',width:10,sortable:false,hidden:true},
		{name:'CUSNAME',index:'CUSNAME',sortable:true},
		{name:'ACCSTARTTIME',index:'ACCSTARTTIME',width:100,sortable:true,align:"right",formatter:dateFmatterM},
		{name:'ACCENDTIME',index:'ACCENDTIME',width:100,sortable:true,align:"right",formatter:dateFmatterM},
		{name:'MONTHCOST',index:'MONTHCOST',sortable:true,align:"right"},
		{name:'DISCOUNT',index:'DISCOUNT',width:100,sortable:true,align:"right"},
		{name:'ACCBOOKCOST',index:'ACCBOOKCOST',width:100,sortable:true,align:"right"},
		{name:'COMMISSION',index:'COMMISSION',width:100,sortable:true,align:"right"},
		{name:'OPERATE',index:'OPERATE',width:300,sortable:false,align:"center",formatter:currencyFmatter}
		],
		rowNum: 100,
		rowList: [100,150,200],
		pager: '#gridPager',
		mtype: "POST",
		width:$(window).width() - 5,
		height:$(window).height()-350,
		viewrecords: true,
		multiselect: false,
		loadComplete: function (data){},
		gridComplete: function(){},
		hidegrid: false
	});
	jQuery("#contractInfoTable").jqGrid('navGrid',"#gridPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
});

function dateFmatterD(cellvalue, options, rowObject){
	return myDateFmatter(cellvalue,"d");
}

function dateFmatterM(cellvalue, options, rowObject){
	return myDateFmatter(cellvalue,"m");
}

function myDateFmatter(time,flag){
	var myDate = "";
	if(flag=="m"){
		var year = new Date(time).getFullYear();
		var month = new Date(time).getMonth()+1;
		if(month < 10) month = "0" + month;
		myDate = year+"-"+month;
	}else if(flag=="d"){
		var year = new Date(time).getFullYear();
		var month = new Date(time).getMonth()+1;
		if(month < 10) month = "0" + month;
		var date = new Date(time).getDate();
		if(date < 10) date = "0" + date;
		myDate = year+"-"+month+"-"+date;
	}
	return myDate;
}

function currencyFmatter (cellvalue, options, rowObject){//操作字段重新格式化
	var canEdit = rowObject.canEdit;
	if(canEdit==false){
		return "<font color='green'>【已生效】<font>";
	}
	var haveFile = rowObject.HAVEFILE;
	var signTime = myDateFmatter(rowObject.SIGNTIME,"d");
	var accStartTime = myDateFmatter(rowObject.ACCSTARTTIME,"m");
	var accEndTime = myDateFmatter(rowObject.ACCENDTIME,"m");
	var monthCost = rowObject.MONTHCOST;
	var payType = rowObject.PAYTYPE;
	var discount = rowObject.DISCOUNT;
	var accBookCost = rowObject.ACCBOOKCOST;
	var contractType = rowObject.CONTRACTTYPE;
	var demo = rowObject.DEMO;
	var commission = rowObject.COMMISSION;
	demo = $.ckTrim(demo);
	demo =demo.replace("\r\n", "<br/>");
	var operate = "<div>"+
					"<input type='button' value='修改' onclick=\"toEditContractInput('" + cellvalue + "','" + haveFile + "','" + signTime + "','" + accStartTime + "','" + accEndTime + "','" + monthCost + "','" + payType + "','" + discount + "','" + accBookCost + "','" + demo + "','"+contractType+"','"+commission+"');\" class='bcssbtn' style='width:36px;height:22px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;padding-left: 5px;padding-right: 5'/>"+
					"<input type='button' value='删除' onclick=\"deleteContractInfo('" + cellvalue + "');\" class='bcssbtn' style='width:36px;height:22px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;padding-left: 5px;padding-right: 5'/>"+
					"<input type='button' value='添加业务' onclick=\"addProcess('" + cellvalue + "');\" class='bcssbtn' style='width:55px;height:22px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;padding-left: 5px;padding-right: 5'/>"+
					"</div>";
	return operate;
}


var doServiceLayer;
function addProcess(value){ //跳转到业务查询页面
	doServiceLayer = layer.open({
		title : ' 业务查询',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		area : ['550px', '450px'],
		content : '<%=path%>/business/forward/gotoBusinessView?pageName=goToDoService&contractId='+value,
		end : function(){
			queryMessage();
		}
	}); 
	
}

function saveContractInfo(){//保存合同信息
	var signTime = $("#signTime").val();
	var accStartTime = $("#accStartTime").val();
	var accEndTime = $("#accEndTime").val();
	var monthCost = $("#monthCost").val();
	var discount = $("#discount").val();
	var accBookCost = $("#accBookCost").val();
	var demo = $("#demo").val();
	var file = $("#file").val();
	if(signTime==null||signTime==""){
		parent.$.showMsg("请输入签约日期!", 2);
		return;
	}
	if(accStartTime==null||accStartTime==""){
		parent.$.showMsg("请输入服务起始日期!", 2);
		return;
	}
	if(accEndTime==null||accEndTime==""){
		parent.$.showMsg("请输入服务结束日期!", 2);
		return;
	}
	if(new Date(signTime.substr(0,7)).getTime()>new Date(accEndTime).getTime()){
		parent.$.showMsg("签约日期必须小于服务结束日期!", 2);
		return;
	}
	if(new Date(accStartTime).getTime()>new Date(accEndTime).getTime()){
		parent.$.showMsg("结束日期必须大于起始日期!", 2);
		return;
	}
	if(discount==null||discount==""){
		parent.$.showMsg("请填写折扣率!", 2);
		return;
	}
	if(monthCost==null||monthCost==""){
		parent.$.showMsg("请填写代账费(月)!", 2);
		return;
	}
	if(accBookCost==null||accBookCost==""){
		parent.$.showMsg("请填写账本费!", 2);
		return;
	}
	var tips;
	var options = {
		type : 'post',
		dataType : 'json',
		url:'<%=path%>/supervisory/asyn/saveContractInfo',
		beforeSubmit : function() {
			tips = layer.msg("正在保存,请稍后...",{icon : 16,time : 0, shade: [0.1]});
		},
		success : function(result) {
			layer.close(tips);
			if(result.code==0){
				parent.$.showMsg("保存成功!", 1);
				resetContractInfo();
				jQuery("#contractInfoTable").jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getContractInfo',page:1}).trigger("reloadGrid");
			}else if(result.code==1){
				parent.$.showMsg("保存失败,系统出错!", 2);
			}else if(result.code==2){
				parent.$.showMsg("服务期限不能有交集!", 2);
			}
		},
		error : function(result) {
			layer.close(tips);
			parent.$.showMsg("亲!您的网络不给力哦~", 2);
		}
	};
	$("#contractInfoForm").ajaxSubmit(options);
}

function deleteContractInfo(contractId){//删除合同信息
layer.confirm("确认删除?", {icon: 3, title:"提示"}, function(index){
	var tips = layer.msg("正在删除...",{icon : 16,time : 0, shade: [0.1]});
	$.post('<%=path%>/supervisory/asyn/deleteContractInfo',{'contractId' : contractId},function(result){
			layer.close(tips);
			if(result.code){
				parent.$.showMsg("删除成功!", 1);
				resetContractInfo();
				$("#saveInfo").show();
				$("#editInfo").hide();
				$("#fileNone").show();
				$("#fileHave").hide();
				jQuery("#contractInfoTable").jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getContractInfo',page:1}).trigger("reloadGrid");
			}else{
				resetContractInfo();
				$("#saveInfo").show();
				$("#editInfo").hide();
				$("#fileNone").show();
				$("#fileHave").hide();
				parent.$.showMsg("删除失败,系统出错!", 2);
			}
		}
	);
  layer.close(index);
});
}

function deleteContractFile(fileId){//删除合同附件（落地）并局部刷新列表
	var contractId = $("#contractId").val();
	layer.confirm("确认删除?", {icon: 3, title:"提示"}, function(){
		var tips = layer.msg("正在删除...",{icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/deleteContractFile',{'contractId' : contractId},function(result){
			layer.close(tips);
			if(result.code){
				$("#fileNone").show();
				$("#fileHave").hide();
				parent.$.showMsg("删除成功!", 1);
			}else{
				parent.$.showMsg("删除失败,系统出错!", 2);
			}
		});
	});
}

function toEditContractInput(contractId,haveFile,signTime,accStartTime,accEndTime,monthCost,payType,discount,accBookCost,demo,contractType,commission){//编辑合同信息（页面）
	demo =demo .replace("<br/>","\r\n");
	if(haveFile>0){
		$("#fileHave").show();
		$("#fileNone").hide();
	}else{
		$("#fileHave").hide();
		$("#fileNone").show();
	}
	$("#payType option[value='"+payType+"']").attr("selected",true);
	$("#contractType option[value='"+contractType+"']").attr("selected",true);
	$("#editInfo").show();
	$("#saveInfo").hide();
	$("#signTime").attr("value",signTime);
	$("#accStartTime").attr("value",accStartTime);
	$("#accEndTime").attr("value",accEndTime);
	$("#monthCost").attr("value",monthCost);
	$("#discount").attr("value",discount);
	$("#accBookCost").attr("value",accBookCost);
	$("#demo").attr("value",demo);
	$("#contractId").attr("value",contractId);
	$("#commission").attr("value",commission);
}

function editContractInfo(){//编辑合同信息（数据库落地）
	var signTime = $("#signTime").val();
	var accStartTime = $("#accStartTime").val();
	var accEndTime = $("#accEndTime").val();
	var monthCost = $("#monthCost").val();
	var discount = $("#discount").val();
	var accBookCost = $("#accBookCost").val();
	var demo = $("#demo").val();
	var file = $("#file").val();
	if(signTime==null||signTime==""){
		parent.$.showMsg("请输入签约日期!", 2);
		return;
	}
	if(accStartTime==null||accStartTime==""){
		parent.$.showMsg("请输入服务起始日期!", 2);
		return;
	}
	if(accEndTime==null||accEndTime==""){
		parent.$.showMsg("请输入服务结束日期!", 2);
		return;
	}
	if(new Date(signTime.substr(0,7)).getTime()>new Date(accEndTime).getTime()){
		parent.$.showMsg("签约日期必须小于服务结束日期!", 2);
		return;
	}
	if(new Date(accStartTime).getTime()>new Date(accEndTime).getTime()){
		parent.$.showMsg("结束日期必须大于起始日期!", 2);
		return;
	}
	if(discount==null||discount==""){
		parent.$.showMsg("请填写折扣率!", 2);
		return;
	}
	if(monthCost==null||monthCost==""){
		parent.$.showMsg("请填写代账费(月)!", 2);
		return;
	}
	if(accBookCost==null||accBookCost==""){
		parent.$.showMsg("请填写账本费!", 2);
		return;
	}
	var tips;
	var options = {
		type : 'post',
		dataType : 'json',
		url:'<%=path%>/supervisory/asyn/editContractInfo',
		beforeSubmit : function() {
			tips = layer.msg("正在更新,请稍后...",{icon : 16,time : 0, shade: [0.1]});
		},
		success : function(result) {
			layer.close(tips);
			if(result.code==0){
				parent.$.showMsg("更新成功!", 1);
				resetContractInfo();
				$("#saveInfo").show();
				$("#editInfo").hide();
				$("#fileNone").show();
				$("#fileHave").hide();
				jQuery("#contractInfoTable").jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getContractInfo',page:1}).trigger("reloadGrid");
			}else if(result.code==1){
				resetContractInfo();
				$("#saveInfo").show();
				$("#editInfo").hide();
				$("#fileNone").show();
				$("#fileHave").hide();
				parent.$.showMsg("更新失败,系统出错!", 2);
			}else if(result.code==2){
				resetContractInfo();
				$("#saveInfo").show();
				$("#editInfo").hide();
				$("#fileNone").show();
				$("#fileHave").hide();
				parent.$.showMsg("服务期限不能有交集!", 2);
			}
		},
		error : function(result) {
			layer.close(tips);
			parent.$.showMsg("亲!您的网络不给力哦~", 2);
		}
	};
	$("#contractInfoForm").ajaxSubmit(options);
}

function downLoadContractFile(){
	var contractId = $("#contractId").val();
	$("#hrefDownLoad").attr("href","<%=path%>/supervisory/asyn/downloadContractFile?contractId="+contractId);
}

function fileInfoCheck(_this,ele){//验证上传文件格式
	var filepath = $("input[name='file']").val();
	var extStart = filepath.lastIndexOf(".");
	var ext = filepath.substring(extStart, filepath.length).toUpperCase();
	if (ext != ".TXT" && ext != ".XLS" && ext != ".DOC" && ext != ".JPG" && ext != ".GIF"&& ext != ".PDF") {
		parent.$.showMsg("文件限于txt、xls、doc、jpg、gif、pdf格式", 2);
		_this.prop("value","");
		return;
	}
	var fileSize = (ele.files[0].size / 1024).toFixed(2);
	if(fileSize>500){
		parent.$.showMsg("文件大小限于500KB以内", 2);
		_this.prop("value","");
		return;
	}
}

function resetContractInfo(){//重置按钮
		$("#payType option[value='1']").attr("selected",true);
		$("#signTime").attr("value","");
		$("#accStartTime").attr("value","");
		$("#accEndTime").attr("value","");
		$("#monthCost").attr("value","");
		$("#accBookCost").attr("value","");
		$("#discount").attr("value","100");
		$("#file").attr("value","");
		$("#demo").attr("value","");
		$("#commission").attr("value","0");
}

function upLoadConExcel(){
	var tips;
	var options = {
		type : 'post',
		dataType : 'json',
		url:'<%=path%>/supervisory/asyn/upLoadConExcel',
		beforeSubmit : function() {
			tips = layer.msg("正在上传,请稍后...",{icon : 16,time : 0, shade: [0.1]});
		},
		success : function(result) {
			layer.close(tips);
		},
		error : function(result) {
			layer.close(tips);
			parent.$.showMsg("亲!您的网络不给力哦~", 2);
		}
	};
	$("#contractInfoForm").ajaxSubmit(options);
}

</script>
</head>
<body>
	<form id=contractInfoForm enctype="multipart/form-data" method="POST">
		<div>
			<table id="contractInfoEditTable" width="100%">
				<tr>
					<td align="left"><font>客户：</font></td>
					<td align="left"><font>${orgName}</font>
						<input type="hidden" id="cusId" name="cusId" value="${cusId}">
						<input type="hidden" id="contractId" name="contractId">
						<input type="hidden" id="cusName" name="cusName" value="${orgName}">
						<input type="hidden" id="cancleFlag" name="cancleFlag" value="生效">
						<input type="hidden" id="payType" name="payType" value="1">
					</td>
					<td align="left"><font>会计提成：</font></td>
					<td align="left">
						<input type="text" id="commission" name="commission" value="0" maxlength="5" class="txt140"/><font>%</font>
					</td>
				</tr>
				<tr>
					<td align="left"><font>代账费(月)：</font></td>
					<td>
						<input type="text" id="monthCost" maxlength="10" name="monthCost" class="txt140"/><font>元</font><font color="red">*</font>
					</td>
					<td align="left"><font>签约日期：</font></td>
					<td align="left">
						<input type="text" id="signTime" name="signTime" onfocus="WdatePicker()" class="Wdate" style="margin-top:5px;" /><font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td align="left"><font>账本费：</font></td>
					<td>
						<input type="text" id="accBookCost" maxlength="10" name="accBookCost" class="txt140"/><font>元</font><font color="red">*</font>
					</td>
					<td align="left"><font>折扣率：</font></td>
					<td>
						<input type="text" id="discount" maxlength="5" name="discount" value="100" class="txt140"/><font>%</font><font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td align="left"><font>服务期限：</font></td>
					<td align="left">
						<input type="text" id="accStartTime" name="accStartTime" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" class="Wdate" style="margin-top:5px;" />
					</td>
					<td>	
						<font>至</font>
					</td>
					<td>	
						<input type="text" id="accEndTime" name="accEndTime" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" class="Wdate" style="margin-top:5px;" /><font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td align="left"><font>附件：</font></td>
					<td align="left">
						<div id="fileNone" style="display:inline;">
							<input type='file' id='file' name='file' class='FileCss' onchange='fileInfoCheck($(this),this,"+fileCountId+");'/>
							<font color="red">不超过5M</font>
						</div>
						<div id="fileHave" style="display:inline;">
							<a id="hrefDownLoad" onclick="downLoadContractFile();" class="bcssbtn" style="height:17px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px">下载</a>
							<input type="button" value="删除" onclick="deleteContractFile();" class="bcssbtn" style="height:20px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px"/>
						</div>
					</td>
					<td><font>合同类型：</font></td>
					<td>
						<select id="contractType" name="contractType" class="select_staff" style="width: 142px">
							<option value="200101" selected="selected">代理记账</option>
							<option value="200100">工商注册</option>
							<option value="200102">法律咨询</option>
							<option value="200103">人事代理</option>
							<option value="200104">商标专利</option>
							<option value="200105">其他</option>
						</select>
					</td>
				</tr>
				<tr	style="display: none;"><td align="left">导入Excel：</td>
					<td><input type='file' id=excelFile name='excelFile' class='FileCss'/><input type="button" value="导入" onclick="upLoadConExcel();"></td></tr>
				<tr>
					<td align="left"><font>备注：</font></td>
					<td colspan="3" align="left">
						<textarea id="demo" name="demo" class="txt450"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="4" align="right">
						<div id="saveInfo" style="display:inline;">
							<input type="button" value="保存" class="bcssbtn" style="padding: 0px 0px 0px 0px" id="reset" onclick="saveContractInfo();"/>
						</div>
						<div id="editInfo" style="display:inline;">
							<input type="button" value="修改" class="bcssbtn" style="padding: 0px 0px 0px 0px" id="reset" onclick="editContractInfo();"/>
						</div>
						<div style="display:inline;">
							<input type="button" value="重置" class="bcssbtn" style="padding: 0px 0px 0px 0px" id="reset" onclick="resetContractInfo();"/>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
	<h4>合同列表：</h4>
	<table id="contractInfoTable" width="100%"></table>
	<div id="gridPager"></div>
</body>
</html>