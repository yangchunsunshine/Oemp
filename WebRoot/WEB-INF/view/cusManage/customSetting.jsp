<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="com.wb.framework.commonUtil.PropertiesReader"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">  
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<title>客户信息查看</title>
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
<%PropertiesReader reader = PropertiesReader.getInstance();%>
<%String url = reader.getValue("/com/wb/config/application", "ACC_BILL_URL");%>
var ACC_BILL_URL = '<%=url%>';
$(function(){//初始化函数,刷新公司信息列表
	if(document.attachEvent){  
	    document.attachEvent("onkeydown",function(e){  
	    if(e.keyCode == 13)  
	        queryMessage();
	    });  
	}  
	else {  
	    document.addEventListener("keydown",function(e){  
	    if(e.keyCode == 13)  
	        queryMessage();
	    });  
	}
	var conType = $("#conType").val();
	var disFlag = $("#disFlag").val();
	thisGrid = $("#paymentGridTable").jqGrid({
           url: '<%=path%>/supervisory/asyn/getCompanyInfo?init=true&cmd=BY_DEP&queryId=0&auditType=0&auditorName=&conType='+conType+'&disFlag='+disFlag,
           datatype: 'json',
           colNames: ['对应账簿ID','业务类型','公司ID','公司编号','公司名称','所属主管','操作'],
           colModel: [
			   {name:'OID',index:'OID',sortable:false,hidden:true},
			   {name:'CONTYPE',index:'CONTYPE',sortable:false,hidden:true},
	           {name:'ORGID',index:'ORGID',sortable:false,hidden:true},
	           {name:'ORGSEQ',index:'ORGSEQ',sortable:false,width:60,align:"left"},
	           //{name:'OID',index:'OID',sortable:false,width:60,align:"left"},
	           {name:'ORGNAME',index:'ORGNAME',sortable:false,width:160,align:"left",formatter:nameFmatter},
	           {name:'MEMBERNAME',index:'MEMBERNAME',sortable:false,width:60,align:"left"},
	           {name:'OPERATE',index:'OPERATE',width:180,sortable:false,align:"center",formatter:operateFmatter}
           ],
		   rowNum: 50,
		   rowList: [50,100,500],
	       pager: '#gridPager',
		   sortname: 'ORGID',
		   sortorder: "asc",
		   caption: '公司信息',
		   mtype: "POST",
		   width:$(window).width()-270,
       	   height:$(window).height()-155,
           viewrecords: true,
           multiselect: true,
           ondblClickRow: function(rowid){},
	       gridComplete: function(){
				$('#paymentGridTable tr').mouseover(function(e) {
					$(this).find("div[mes='operateTrDiv']").show();
				});
				$('#paymentGridTable tr').mouseout(function(e) {
					$(this).find("div[mes='operateTrDiv']").hide();
				});
				authView();
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
			},
			onAsyncSuccess: function(event, treeId, treeNode, msg) {
				var node =treeObj.getNodes();
				treeObj.selectNode(node[0],false);
			    jQuery("#paymentGridTable").jqGrid('navGrid',"#gridPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
			}
		}
	});
});

function nameFmatter(cellvalue, options, rowObject){
	var div = '<div><div/><div></div>';
	return "<div align='left' style='display: inline;float: left;'><span>" + cellvalue + "</span></div><div align='right' style='display: inline;float: right;'><span id='cus_edit' style='display: none;'><a href='#' title='修改' onclick=\"editCusInfo('"+rowObject.ORGID+"','"+rowObject.MEMBERNAME+"')\"><img title='修改' src='<%=path%>/style/image/ico_turn.png'/></a></span><div>";
}

function queryMessage(){//输入编号或客户名称,查询公司信息列表
	var selectNodes = treeObj.getSelectedNodes();
	var cmd = selectNodes[0].cmd;
	var companySearchName = $("#companySearchName").val();
	var queryId = selectNodes[0].id;
	var conType = $("#conType").val();
	var disFlag = $("#disFlag").val();
	thisGrid.jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getCompanyInfo?init=false&cmd='+cmd+'&queryId=' + queryId+'&companySearchName=' + companySearchName + '&conType=' + conType+'&disFlag='+disFlag, page : 1}).trigger("reloadGrid");
}

function operateFmatter (cellvalue, options, rowObject) {//操作字段重新格式化
	var orgId = rowObject.OID;
	var CONTYPE=rowObject.CONTYPE;
	var conType = $("#conType").val();
	if(conType == "200101"){
		var operate = "<div mes='operateTrDiv'  style='display:none;'>"+
							"<input type='button' value='合同' id='cus_Contract' onclick=\"createContract('" + cellvalue + "');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;display: none;' class='bcssbtn'/>"+
							"<input type='button' value='记账' id='cus_toAcc' onclick=\"toAccounting('" + orgId + "');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;display: none;' class='bcssbtn'/>"+
							"<input type='button' value='跟进' id='cus_follow' onclick=\"createFollow('" + cellvalue + "');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;display: none;' class='bcssbtn'/>"+
							"<input type='button' value='报税' id='cus_tax' onclick=\"goToTally('" + orgId + "');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;display: none;' class='bcssbtn'/>"+
							"<input type='button' value='业务办理' id='cus_buss' onclick=\"goToDoService('" + orgId +"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;display: none;' class='bcssbtn'/>"+
						"</div>";
	}else{
		if(CONTYPE == ""){
			if(CONTYPE == "200101"){
				var operate = "<div mes='operateTrDiv'  style='display:none;'>"+
				"<input type='button' value='合同' id='cus_Contract' onclick=\"createContract('" + cellvalue + "');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;display: none;' class='bcssbtn'/>"+
				"<input type='button' value='记账' id='cus_toAcc' onclick=\"toAccounting('" + orgId + "');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;display: none;' class='bcssbtn'/>"+
				"<input type='button' value='跟进' id='cus_follow' onclick=\"createFollow('" + cellvalue + "');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;display: none;' class='bcssbtn'/>"+
				"<input type='button' value='报税' id='cus_tax' onclick=\"goToTally('" + orgId + "');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;display: none;' class='bcssbtn'/>"+
				"<input type='button' value='业务办理' id='cus_buss' onclick=\"goToDoService('" + orgId +"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;display: none;' class='bcssbtn'/>"+
			"</div>";
			}
			else{
				var operate = "<div mes='operateTrDiv'  style='display:none;'>"+
				"<input type='button' value='合同' id='cus_Contract' onclick=\"createContract('" + cellvalue + "');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;display: none;' class='bcssbtn'/>"+
				"<input type='button' value='跟进' id='cus_follow' onclick=\"createFollow('" + cellvalue + "');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;display: none;' class='bcssbtn'/>"+
				"<input type='button' value='业务办理' id='cus_buss' onclick=\"goToDoService('" + orgId + "');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;display: none;' class='bcssbtn'/>"+
			"</div>";
			}
			
		}else{
			var operate = "<div mes='operateTrDiv'  style='display:none;'>"+
			"<input type='button' value='合同' id='cus_Contract' onclick=\"createContract('" + cellvalue + "');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;display: none;' class='bcssbtn'/>"+
			"<input type='button' value='跟进' id='cus_follow' onclick=\"createFollow('" + cellvalue + "');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;display: none;' class='bcssbtn'/>"+
			"<input type='button' value='业务办理' id='cus_buss' onclick=\"goToDoService('" + orgId + "');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;display: none;' class='bcssbtn'/>"+
		"</div>";
		}
	
		
	}
	return operate;
}

var addCus;
function createCus(){//进入添加客户页面
	addCus = layer.open({
		title : '创建客户', 
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		maxmin: true,
		area : ['550px', '480px'],
		content : '<%=path%>/supervisory/forward/gotoCustomCreate',
		end : function(){
			queryMessage();
		}
	});
}

var editCus;
function editCusInfo(cusId,memberName){//进入编辑客户页面
	editCus = layer.open({
		title : '编辑客户信息',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		maxmin: true,
		area : ['550px', '480px'],
		content : '<%=path%>/supervisory/forward/gotoCustomEdit?cusId='+cusId+'&memberName='+encodeURIComponent(encodeURIComponent(memberName)),
		end : function(){
			queryMessage();
		}
	});
}

var addCon;
function createContract(cusId){//进入创建合同页面
	addCon = layer.open({
		title : '创建合同',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		maxmin: true,
		area : ['550px', '480px'],
		content : '<%=path%>/supervisory/forward/gotoContractCreate?cusId='+cusId,
		success : function(){
			//layer.full(addCon);
		},
		end : function(){
			queryMessage();
		}
	});
	layer.full(addCon);
}

var addFollow;
function createFollow(cusId){//进入跟进页面
	addFollow = layer.open({
		title : '新增跟进记录',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		maxmin: true,
		area : ['550px', '480px'],
		content : '<%=path%>/supervisory/forward/gotoFollowCreate?cusId='+cusId,
		end : function(){
			queryMessage();
		}
	});
}

/**
	跳转缴费接口
*/
function gotoPayMent(){
	window.location.href = '<%=path%>/supervisory/forward/gotoPayment';
}

var dispatching;
function dispatchingCus(){
	var selectData = jQuery("#paymentGridTable").jqGrid('getGridParam', 'selarrrow');
	var dataStr = "";
	if(selectData.length>0){
		for(var i=0;i<selectData.length;i++){
			var data = jQuery('#paymentGridTable').jqGrid('getCell',selectData[i],'OID');
			dataStr = dataStr + data + "-";
		}
	}else{
		parent.$.showMsg("请选择需要派工的公司!", 2);
		return;
	}
	dispatching = layer.open({
		title : '派工',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		area : ['550px', '480px'],
		content : '<%=path%>/supervisory/forward/gotoDispatchingCus?dataStr='+dataStr,
		success : function(){
		
		}
	});
	layer.full(dispatching);
}

function toAccounting(orgId){
	$.post('<%=path%>/supervisory/asyn/toAccounting',{'orgId':orgId},function(result){
		var memberId = result.memberId;
		if(result.code == true || result.code == 'true'){
			var url = ACC_BILL_URL + "/forward/toConsolePage.jspx?memberId="+memberId;
			window.open(url, "_blank");
		}else{
			parent.$.showMsg("无法获取相应的管理者！", 2);
			return;
		}
	});
}

var tally;
function goToTally(orgId){
	tally = layer.open({
		title : ' 报税',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		area : ['550px', '480px'],
		content : '<%=path%>/supervisory/forward/goToTally?orgId='+orgId,
		success : function(){
		
		}
	});
	layer.full(tally);
}

var doServiceLayer;
function goToDoService(orgId){
	doServiceLayer = layer.open({
		title : ' 业务查询',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		area : ['550px', '450px'],
		content : '<%=path%>/supervisory/forward/goToDoService?orgId='+orgId,
		end : function(){
			queryMessage();
		}
	}); 
	
}

function downLoadExcel(){
	var orgIds = "";
	var selectData = jQuery("#paymentGridTable").jqGrid('getGridParam', 'selarrrow');
	if(selectData.length==0){
		parent.$.showMsg("请选择需要导出的公司!", 2);
		return;
	}
	for(var i=0;i<selectData.length;i++){
		var data = jQuery('#paymentGridTable').jqGrid('getCell',selectData[i],'OID');
		if(i!=(selectData.length-1)){
			orgIds = orgIds+data+",";
		}else{
			orgIds = orgIds+data;
		}
	}
	
	$("#hrefDownLoad").attr("href","<%=path%>/supervisory/asyn/downLoadExcel?orgIdStr="+orgIds);
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
		<div style="float: right;padding-right: 20px;" title="公司信息">
			<div style="margin-bottom:15px;height: 30px">
				<span>
					<span style="font-size: 15px">客户:&nbsp;&nbsp;</span><input placeholder="请输入编号或客户名称" id="companySearchName" name="companySearchName" maxlength="20" type="text" class="dfinput_fb" style="width:180px;height:24px;"/>&nbsp;&nbsp;
					<span style="font-size: 15px">合同类型:&nbsp;&nbsp;</span><select id="conType" name="conType" onchange="queryMessage();" style="border:1px solid #ccc;width:90px;height:26px;">
						<option selected="selected" value="1">全部</option>
						<option value="">无合同</option>
						<option value="200101">代理记账</option>
						<option value="200100">工商注册</option>
						<option value="200102">法律咨询</option>
						<option value="200103">人事代理</option>
						<option value="200104">商标专利</option>
						<option value="200105">其他</option>
					</select>&nbsp;&nbsp;
					<span style="font-size: 15px">派工状态:&nbsp;&nbsp;</span><select id="disFlag" name="disFlag" onchange="queryMessage();" style="border:1px solid #ccc;width:90px;height:26px;">
						<option selected="selected" value="">全部</option>
						<option value="0">未派工</option>
						<option value="1">已派工</option>
					</select>&nbsp;&nbsp;
					<input name="searchName"  type="button" id="searchName" style="padding: 3px 4px 2px 4px" value="查询" onclick="queryMessage();" class="bcssbtn" style="height:30px;font-size:15px;text-align: center;padding-top:0px; padding-bottom:0px"/> 
				</span>
				<input class="acssbtn" type="button" id="cus_create" onclick="createCus();"      value="创建客户" style="padding: 3px 4px 2px 4px;display: none"/>&nbsp;&nbsp;
				<input class="acssbtn" type="button" id="cus_dispath" onclick="dispatchingCus();" value="派工" style="padding: 3px 4px 2px 4px;display: none"/>&nbsp;&nbsp;
				<a id="hrefDownLoad" onclick="downLoadExcel();" class="acssbtn" style="padding: 3px 4px 2px 4px;">导出</a>
			</div>
			<table id="paymentGridTable"></table>
			<div id="gridPager"></div>
		</div>
	</div>
</body>  
</html> 