<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<title>客户缴费查看</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script type="text/javascript" src="<%=path%>/script/powerAuth.js"></script>
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

<script type="text/javascript">
var thisGrid;
var treeObj;
var tips;

$(function(){//初始化函数,刷新公司信息列表
	$("#custom").select2({ 
		language: "zh-CN"
	});
	$("#payYear").val(getThisYear());
	var payYear = $("#payYear").val();
	thisGrid = $("#cusPayInfoTable").jqGrid({
          url: '<%=path%>/supervisory/asyn/getCusPayInfo?init=true&cmd=BY_DEP&queryId=0&payYear='+payYear,
          datatype: 'json',
          colNames: ['公司ID','缴费状态','公司编号','公司名称','合同编号','合同起始月份','合同终止月份','应收金额','实收金额','月数','已收月数隐藏','账本费隐藏','是否欠费隐藏','是否即将到期隐藏','是否有合同隐藏','实际合同ID'],
          colModel: [
           {name:'ORGID',index:'ORGID',sortable:false,hidden:true},
           {name:'PAYFLAG',index:'PAYFLAG',sortable:true,width:8,align:"center",formatter:payFlagFmatter},
           {name:'ORGNUM',index:'ORGNUM',sortable:true,width:9,align:"left"},
           {name:'ORGNAME',index:'ORGNAME',sortable:true,width:16,align:"left"},
           {name:'ACCNO',index:'ACCNO',sortable:false,hidden:true},
           {name:'BMONTHS',index:'BMONTHS',sortable:true,width:7,align:"center"},
           {name:'EMONTHS',index:'EMONTHS',sortable:true,width:7,align:"center"},
           {name:'PAYAMOUNT',index:'PAYAMOUNT',sortable:true,width:5,align:"right",formatter:payAmountFmatter},
           {name:'REALAMOUNT',index:'REALAMOUNT',sortable:true,width:5,align:"right",formatter:realAmountFmatter},
           {name:'SHOWMONTH',index:'SHOWMONTH',sortable:false,width:24,align:"right",formatter:monthFmatter},
           {name:'PAYMONTHS',index:'PAYMONTHS',sortable:false,hidden:true},
           {name:'BOOKFEE',index:'BOOKFEE',sortable:false,hidden:true},
           {name:'ARR',index:'ARR',sortable:false,hidden:true},
           {name:'ISOVER',index:'ISOVER',sortable:false,hidden:true},
           {name:'CON',index:'CON',sortable:false,hidden:true},
           {name:'CONID',index:'CONID',sortable:false,hidden:true}
          ],
	   rowNum: 100,
	   rowList: [100,150,200],
       pager: '#payInfoPager',
	   sortname: 'ORGID',
	   sortorder: "asc",
	   caption: '<label style="font-size:12px;color:black;">客户缴费信息</label>',
	   mtype: "POST",
	   width: $(window).width()-270,
   	   height: $(window).height()-150,
       viewrecords: true,
       multiselect: true,
       onSelectAll: function(aRowids,status) {
        	if (status) {
        		$("input:checkbox:checked:disabled").parent().parent().removeClass("ui-state-highlight");
				var cbs = $("input:checkbox:checked:disabled");
				cbs.removeAttr("checked");
			}
		},
		beforeSelectRow: function (rowid, e) {
			var $myGrid = $(this),
			i = $.jgrid.getCellIndex($(e.target).closest('td')[0]),  
			cm = $myGrid.jqGrid('getGridParam', 'colModel');  
			return (cm[i].name === 'cb');
    	},
    	onSelectRow:function(rowid,status){
    		$("input:checkbox:checked:disabled").parent().parent().css("background-image","url(images/ui-bg_highlight-soft_100_eeeeee_1x100.png)");
    		var cbs = $("input:checkbox:checked:disabled");
			cbs.removeAttr("checked");
    	},
       	gridComplete: function(){
			var ids = $("#cusPayInfoTable").jqGrid('getDataIDs');
            for(var i=0;i<ids.length;i++){
        		var row=$("#cusPayInfoTable").jqGrid('getRowData',ids[i]);
        		if(row["ARR"]=="true"){
	        		$("#cusPayInfoTable #"+ids[i]).css("color","red");
                }else{
   		            if(row["CON"]=="false"){
		   				$("#cusPayInfoTable #"+ids[i]).css("color","grey");
			   			$("#jqg_cusPayInfoTable_"+ids[i]).attr("disabled",true);
			   		}else{
			   		 	$("#cusPayInfoTable #"+ids[i]).css("color","green");
			        }
				}
			}
			$("#cusPayInfoTable tr").mouseover(function(e) {
				$(this).find("div[mess='monthButtonDiv']").show();
				$(this).find("div[mess='monthDiv']").hide();
			});
			$("#cusPayInfoTable tr").mouseout(function(e) {
				$(this).find("div[mess='monthButtonDiv']").hide();
				$(this).find("div[mess='monthDiv']").show();
			});
		},
		hidegrid: false
	});
	
	treeObj = $.fn.zTree.init($("#cusTree"), {
		data: {
			key: {
				name: "partName"
            },
            simpleData :{
				enable: true,
				idKey: "id",
				pIdKey: "pId",
				rootPId: 0
	        }
        },
        async: {
	        enable: true,
	        type: "get",
	       	url:"<%=path%>/supervisory/asyn/findMntFrameWork?ifHasEmp=yes"
        },
	   	callback: {
			onClick: function(event, treeId, treeNode) {
	   			queryMessage();
	   			getCusSelect();
			},
	       	onAsyncSuccess: function(event, treeId, treeNode, msg) {
				var node =treeObj.getNodes();
			    treeObj.selectNode(node[0],false);
			    getCusSelect();
			    jQuery("#cusPayInfoTable").jqGrid('navGrid',"#payInfoPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
			}
		}
	});
});

function getThisYear(){
	var year = new Date().getFullYear();
	return year;
}

//过后台打开发送短信界面--开始
function gotoSMSTemplate(orgId,payMonths,orgName,bookFee,accNo,conId,bMonths,eMonths,type){
			openSMSTemplateWindow(orgId,payMonths,orgName,bookFee,accNo,conId,bMonths,eMonths,type);
}
//过后台打开发送短信界面--结束


function payFlagFmatter(cellvalue, options, rowObject){

	var orgId = rowObject.ORGID;
	var payMonths = rowObject.PAYMONTHS;
	var orgName = rowObject.ORGNAME;
	var bookFee = rowObject.BOOKFEE;
	var months = rowObject.PAYMONTHS;
	var accNo = rowObject.ACCNO;
	var conId = rowObject.CONID;
	var bMonths = rowObject.BMONTHS;
	var eMonths = rowObject.EMONTHS;
	var arr = rowObject.ARR;
	var isOver = rowObject.ISOVER;//即将到期
	var con = rowObject.CON;
	var ReminderFeeButtonHtml = "<div mess='ReminderFeeButtonDiv' align='center'><input   type='button' onclick=\"gotoSMSTemplate('"+orgId+"','"+payMonths+"','"+orgName+"','"+bookFee+"','"+accNo+"','"+conId+"','"+bMonths+"','"+eMonths+"','1')\" value='催费' style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px; ' class='bcssbtn'/></div>";
	var isOverButtonHtml="<div mess='isOverButtonDiv' align='center'><input  type='button' onclick=\"gotoSMSTemplate('"+orgId+"','"+payMonths+"','"+orgName+"','"+bookFee+"','"+accNo+"','"+conId+"','"+bMonths+"','"+eMonths+"','2')\" value='即将到期' style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px; ' class='bcssbtn'/></div>";
	if(arr){
		return ReminderFeeButtonHtml;
	}else if(isOver){
		return isOverButtonHtml
	}else{
		if(con=="false"){
			return "无合同";
   		}else{
			return "未欠费";
		}
	}
}

function payAmountFmatter(cellvalue, options, rowObject){
	if(cellvalue==0){
		return"";
	}
	return cellvalue;
}

function realAmountFmatter(cellvalue, options, rowObject){
	if(cellvalue==0){
		return"";
	}
	return cellvalue;
}

function monthFmatter(cellvalue, options, rowObject){
	var orgId = rowObject.ORGID;
	var payMonths = rowObject.PAYMONTHS;
	var orgName = rowObject.ORGNAME;
	var bookFee = rowObject.BOOKFEE;
	var months = rowObject.PAYMONTHS;
	var accNo = rowObject.ACCNO;
	var conId = rowObject.CONID;
	var bMonths = rowObject.BMONTHS;
	var eMonths = rowObject.EMONTHS;
	var monthButtonHtml = "<div mess='monthButtonDiv' style='display: none;' align='center'><input type='button' onclick=\"gotoCustomPay('"+orgId+"','"+payMonths+"','"+orgName+"','"+bookFee+"','"+accNo+"','"+conId+"','"+bMonths+"','"+eMonths+"');\" value='缴费' style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;' class='bcssbtn'/><div>";
	var divH = "<div mess='monthDiv' class='check_show_div'>";
	var divB = "</div>";
	var monthHtml = "";
	//if(months==null||months==""){
		//monthHtml = divH+monthHtml+divB;
		//return monthHtml+monthButtonHtml;
	//}
	var month = months.split(",");
	month = month.sort(function(a,b){return a-b});

	bMonths = formatMonth(bMonths);
	eMonths = formatMonth(eMonths);
	for(var j = parseInt(eMonths);j>parseInt(bMonths)-1;j--){
		var temp=1;//该条是否显示默认显示
		for(var i = month.length-1;i>=0;i--){
			if($.ckTrim(month[i]) == ""){
				continue;
			}
			month[i] = formatMonth(month[i]);
			if(j==parseInt(month[i])){
				monthHtml = monthHtml + '<label for="month'+month[i]+'" class="label_mon checkon">'+month[i]+'</label>';
				month.splice(i,1);
				temp=0;
			}
		}
		if(temp==1){
			monthHtml = monthHtml + '<label for="month'+j+'" class="label_mon">'+j+'</label>';
		}
	}
	monthHtml = divH+monthHtml+divB;
	return monthHtml+monthButtonHtml;
}
//去掉01-09月份的0
function formatMonth(month){
	if(month.length>1){//去掉01-09月份的0
		if(parseInt(month.substring(0,1))==0)
			month = parseInt(month.substring(1,2));
	}
	return month;
}
function getCusSelect(){
	var selectNodes = treeObj.getSelectedNodes();
	var cmd = selectNodes[0].cmd;
	var queryId = selectNodes[0].id;
	$.post('<%=path%>/supervisory/asyn/getCusSelect',{'cmd':cmd,'queryId':queryId},function(result){
		var cusSelect = "<option selected='selected' value=''>全部</option>";
		for(var i=0;i<result.cusSelectList.length;i++){
			cusSelect = cusSelect + "<option value='"+result.cusSelectList[i].OID+"'>"+result.cusSelectList[i].ORGNAME+"</option>";
		}
		$("#custom").html(cusSelect);
	});
}

function queryMessage(){//输入编号或客户名称,查询公司信息列表
	var selectNodes = treeObj.getSelectedNodes();
	var cmd = selectNodes[0].cmd;
	var queryId = selectNodes[0].id;
	var orgId = $("#custom").val();
	var payYear = $("#payYear").val();
	thisGrid.jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getCusPayInfo?init=false&cmd='+cmd+'&queryId=' + queryId+'&orgId='+orgId+'&payYear='+payYear, page: 1}).trigger("reloadGrid");
}

var pay;
function  gotoCustomPay(orgId,payMonths,orgName,bookFee,accNo,conId,bMonths,eMonths){//进入缴费页面
	var url = '<%=path%>/supervisory/asyn/ifHasContract?orgId='+orgId+'&payMonths='+payMonths;
	$.ajax({
	type : "get",
	dataType : "json",
	url : url, 
	success :function(data){
		var result = eval('(' + data + ')')
		if(result.success == true){
			openPayWindow(orgId,payMonths,orgName,bookFee,accNo,conId,bMonths,eMonths);
		}else{
			parent.$.showMsg(result.message, 2);
		}
	}
});
}
//打开发送短信界面--开始
function openSMSTemplateWindow(orgId,payMonths,orgName,bookFee,accNo,conId,bMonths,eMonths,type){
	var payYear = $("#payYear").val();
	var url = '<%=path%>/supervisory/asyn/gotoSMSTemplate?orgId='+orgId+'&orgName='+encodeURIComponent(encodeURIComponent(orgName))+'&payMonths='+payMonths+'&payYear='+payYear+'&bookFee='+bookFee+'&accNo='+accNo+'&conId='+conId+'&bMonths='+bMonths+'&eMonths='+eMonths+'&type='+type;
	pay = layer.open({
		title : '催费短信提醒', 
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		maxmin: true,
		shadeClose: true,
		area : ['580px', '460px'],
		content : url,
		success : function(){
			//layer.full(addCus);
		}
	});
}
//打开发送短信界面--结束

function openPayWindow(orgId,payMonths,orgName,bookFee,accNo,conId,bMonths,eMonths){
	var payYear = $("#payYear").val();
	var url = '<%=path%>/supervisory/forward/gotoCustomPay?orgId='+orgId+'&orgName='+orgName+'&payMonths='+payMonths+'&payYear='+payYear+'&bookFee='+bookFee+'&accNo='+accNo+'&conId='+conId+'&bMonths='+bMonths+'&eMonths='+eMonths;
	pay = layer.open({
		title : '缴费', 
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		maxmin: true,
		shadeClose: true,
		area : ['580px', '460px'],
		content : url,
		success : function(){
			//layer.full(addCus);
		}
	});
}

function sendfeeNotice(){
	var selectData = jQuery("#cusPayInfoTable").jqGrid('getGridParam', 'selarrrow');
	var dataStr = "";
	if(selectData.length>0){
		for(var i=0;i<selectData.length;i++){
			var con = jQuery('#cusPayInfoTable').jqGrid('getCell',selectData[i],'CON');
			if(con=="false"){
				continue;
			}
			if(i==selectData.length-1){
				var payFlag = jQuery('#cusPayInfoTable').jqGrid('getCell',selectData[i],'PAYFLAG');
				if(payFlag=='未欠费'){
					parent.$.showMsg("请选择需要催费的客户!", 2);
					return;
				}else{
					var data = jQuery('#cusPayInfoTable').jqGrid('getCell',selectData[i],'ORGID');
					dataStr = dataStr + data;
				}
			}else{
				var payFlag = jQuery('#cusPayInfoTable').jqGrid('getCell',selectData[i],'PAYFLAG');
				if(payFlag=='未欠费'){
					parent.$.showMsg("请选择需要催费的客户!", 2);
					return;
				}else{
					var data = jQuery('#cusPayInfoTable').jqGrid('getCell',selectData[i],'ORGID');
					dataStr = dataStr + data + ",";
				}
			}
		}
	}else{
		parent.$.showMsg("请选择需要催费的客户!", 2);
		return;
	}
	tips = layer.msg("正在发送，请稍后...", {icon : 16,time : 0, shade: [0.1]});
	$.post('<%=path%>/supervisory/asyn/sendfeeNotice',{'orgIdArray':dataStr},function(result){
			layer.close(tips);
			var head = "<tr><td colspan='3' align='center'><span>催费结果明细列表</span></td></tr>"+
								"<tr><td align='center' width='30%'><span>公司名称</span></td>"+
								"<td align='center' width='30%'><span>通讯地址</span></td>"+
								"<td align='center' width='40%'><span>催费结果</span></td></tr>";
			var body = "";
			for(var i=0;i<result.length;i++){
				body+=	"<tr>"+
								"<td align='left'>"+result[i].cusName+"</td>"+
								"<td align='center'>"+result[i].tel+"</td>"+
								"<td align='left''>"+result[i].mess+"</td>"+
								"</tr>";
			}
			var bottom = "<tr><td colspan='3' align='center'><input type='button' onclick=\"closePushMessLayer();\" value='关闭'></td></tr>";
			var table = head+body+bottom;
			$("#pushMessDetailTable").html(table);
			openPushMessDetail();
	});
}

var openPushMessLayer;
function openPushMessDetail(){
	openPushMessLayer = layer.open({
		type : 1,
		title : '催费结果明细',
		shade : [ 0.1, '#000' ],
		fix : true,
		maxmin: true,
		shadeClose: true,
		area : ['490px', '450px'],
		content : $("#pushMessDetail")
	});
}

function closePushMessLayer(){
	window.layer.close(window.openPushMessLayer);
}

</script>
</head>
<body>
	<div id="mainDiv" style="padding-top: 10px">
		<div class="customer_left">
			<h2 style="color: black;">组织架构</h2>
			<div class="div_tree">
			<div id="cusTree" class="ztree" style="height: 240px;margin-bottom: 5px;margin-left: 15px;"></div>
			</div>
		</div>
		<div style="float: right;padding-right: 20px;" title="缴费信息">
			<form id="cusPayInfoForm" method="post">
				<div style="margin-bottom:15px;" align="left">
					<span><font size="2" style="font-weight:bold;">客户信息：</font>
						<select name="custom" id="custom" class="select_staff"></select>&nbsp;&nbsp;
						<font size="2" style="font-weight:bold;"> 缴费年份：</font>
						<input type="text" id="payYear" name="payYear" onfocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'});" onchange="queryMessage();" value="" class="Wdate" style="margin-top:5px;height: 25px" />&nbsp;&nbsp;&nbsp;
						<input name="searchButton" type="button" style="padding: 2px 6px 2px 6px;display:none;" id="searchButton"   value="查询" onclick="queryMessage();" class="bcssbtn" /> 
						<!-- <input name="feeNotice" class="abtn" style="padding: 2px 6px 2px 6px" type="button" id="feeNotice" onclick="sendfeeNotice()" value="催费" />
						<label for="notice" class="label_mon checkon" style="width:60px">已缴费</label> -->
					</span>
				</div>
			</form>
			<table id="cusPayInfoTable"></table>
			<div id="payInfoPager"></div>
		</div>
	</div>
	<div id="pushMessDetail" style="display: none;">
	   		<table id="pushMessDetailTable" class="table_query"  cellpadding="0" cellspacing="0" rules="all"></table>
	</div>
</body>  
</html> 