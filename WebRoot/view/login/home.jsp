<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js" ></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script type="text/javascript" src="<%=path%>/script/powerAuth.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style.css"/>
<title>首页</title>
<script type="text/javascript">
var tips;
var lookObj;
var moreRouter = 0;

$(function(){
	menuContro();
	periodChange();
	getPushMessage();
	getMainFollowInfo();
	getMainServiceInfo();
	getMainPayAuditInfo();
	$("#replaceShow").css("height",($(window).height()-282)+"px");
	if($.browser.msie){
		$("#replaceShow").html('<div style="padding:100px 100px 100px 100px;"><div style="float:left;margin:5px;"><img src="<%=path%>/style/image/sign_01.png"/></div><div style="float:left;"><a href="http://rj.baidu.com/soft/detail/14744.html" style="text-decoration:underline;color:#af1515;font-size:12px;font-weight:bold;line-height:18px;" target="_blank">非常抱歉,IE浏览器不支持走势图！请下载谷歌浏览器</a></div></div>');
	}else{
		$("#replaceShow").html('<iframe id="showChart" width="100%" height="'+($(window).height()-282)+'px" frameborder="0" scrolling="no" src="<%=path%>/supervisory/forward/gotoOrgTrend" marginwidth="0" marginheight="0"></iframe>');
	}
	$("#newDiv").css("height","79%");
	lookObj = window.setInterval("checkShow()",300);
	window.setTimeout("clearObj(" + lookObj + ")",10000);
	
	var lis = $("#tab a");
    for (var i = 0; i < lis.length; i++) {
        lis[i].index = i;
        lis[i].onclick = function() {
            for (var j = 0; j < lis.length; j++) {
	        	lis[j].className = "";
		    }
		    lis[this.index].className = "cur";
			if(lis[this.index].index == "0"){
				$("#followUl").css('display','block');
				$("#serviceUl").css('display','none');
				$("#payAuditUl").css('display','none');
				moreRouter = 0;
			}else if(lis[this.index].index == "1"){
				$("#followUl").css('display','none');
				$("#serviceUl").css('display','block');
				$("#payAuditUl").css('display','none');
				moreRouter = 1;
			}else if(lis[this.index].index == "2"){
				$("#followUl").css('display','none');
				$("#serviceUl").css('display','none');
				$("#payAuditUl").css('display','block');
				moreRouter = 2;
			}
        }
    }
});

function checkShow(){
	if($("#pushNews").css("display") == "block"){
		var liLength = $("#messageListUl li").length;
		if(liLength == 6){
			$("#messageListUl li").eq(-1).remove();
			$("#messageListUl li").eq(-1).remove();
		}else if(liLength == 5){
			$("#messageListUl li").eq(-1).remove();
		}
		$("#newDiv").css("height","56%");
		clearObj(lookObj);
	}
}

function clearObj(obj){
	window.clearTimeout(obj);
}

//期间变化获取统计信息
function periodChange(){
	var period=$("#lisence_ID").val();
	$("#lisence_ID").parent("td").append('<label id="searching" style="font-size:12px;margin-left:20px;">查询中...</label>');
	$.ajax({
		url:'<%=path%>/supervisory/asyn/getHomeShowData',
		data:{period:period},
		type:'post',
		dataType:'json',
		success:function(showData){
			$("#searching").remove();
			/*在线人数*/
			$("#onlineClerks").html(showData.onlineClerks);/* +"/"+showData.allClerks */
			/*结账数量*/
			$("#settleState").html(showData.settleNum+"/"+showData.orgNum);
			/*结帐率*/
			$("#settleRate").html(Number((isNaN(showData.settleNum/showData.orgNum)?0:(showData.settleNum/showData.orgNum))*100).toFixed(2)+"%");
			/*报税数量*/
			$("#taxState").html(showData.taxState+"/"+showData.orgNum);
			/*报税率*/
			$("#taxRate").html(Number((isNaN(showData.taxState/showData.orgNum)?0:(showData.taxState/showData.orgNum))*100).toFixed(2)+"%");
			/*欠费公司数量*/
			$("#arrearsCom").html(showData.orgNum2);
		},
		error:function(jqXHR, textStatus, errorThrown ){
			parent.$.showMsg("休息一下哦~亲~",{icon : 5, time : 1500})
		}
	});
}

//跳转到详细页面
function gotoDetail(url,name,flag){
	if(flag){
		url+='?telphone='+flag+'&period='+$("#lisence_ID").val();
	}else{
		url+='?telphone=&period='+$("#lisence_ID").val();
	}
	parent.addOptionTab(url,name);
	window.layer.close(window.openMoreInfoLayer);
}

var loadPushMessage;
function pushMessage(){
	loadPushMessage = layer.open({
		title : '发布公告',
		shade : [ 0.1, '#000' ],
		fix : true,
		maxmin: true,
		shadeClose: true,
		type : 2,
		area : ['560px', '260px'],
		content : '<%=path%>/supervisory/forward/gotoPushMessage'
	});
}

function getPushMessage(){
	$.post('<%=path%>/supervisory/asyn/getPushMessage',function(result){
		$("#messageListUl li").remove();
		var messList = result.messageList;
		for(var i = 0; i < messList.length; i ++){
			var newMess = messList[i].title;
			if(newMess.length>16){
			  newMess=newMess.substring(0,16)+"...";
			}
			var messHead = '<li>';
			var messBody = '<span style="cursor:pointer;" onclick="showNotice('+messList[i].id+')">'+myDateFmatter(messList[i].createTime)+'</span><span style="cursor:pointer;" onclick="showNotice('+messList[i].id+')">'+newMess+'</span>';
			messBody = messBody + '<div style="display:inline-block;margin-left:140px"><div id="delNotice" style="display: none;"><a href="#" title="删除" onclick="delNotice('+messList[i].id+')"><img src="<%=path%>/style/image/error_fuck.png" /></a></div></div>';
			messBottom = '</li>'
			var messLi = messHead + messBody + messBottom;
			$("#messageListUl").append(messLi);
			checkShow();
			authView();
		}
	});
}

function delNotice(noticeId){
	layer.confirm("确认删除?", {icon: 3, title:"提示"}, function(index){
		tips = layer.msg("正在删除...", {icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/delNotice',{'noticeId':noticeId},function(result){
			if(result.code){
				layer.close(tips);
				parent.$.showMsg("删除成功!", 1);
				getPushMessage();
			}else{
				layer.close(tips);
				parent.$.showMsg("删除失败!", 2);
			}
		})
	  	layer.close(index);
	});
}

var showNoticeWindow;
function showNotice(msgId){
	showNoticeWindow = layer.open({
		title : '公告信息',
		shade : [ 0.1, '#000' ],
		fix : true,
		maxmin: true,
		shadeClose: true,
		type : 2,
		area : ['560px', '490px'],
		content : '<%=path%>/supervisory/forward/gotoLookMessage?msgId=' + msgId
	});
}

function myDateFmatter(time){
	var myDate = "";
	var year = new Date(time).getFullYear();
	var month = new Date(time).getMonth()+1;
	if(month < 10) month = "0" + month;
	var date = new Date(time).getDate();
	if(date < 10) date = "0" + date;
	myDate = year+"-"+month+"-"+date;
	return myDate;
}

function getMainFollowInfo(){
	$.post('<%=path%>/supervisory/asyn/getMainFollowInfo',function(result){
		var folTotal = result.folTotal;
		var folList = result.folList;
		var li = "";
		for(var i=0;i<folList.length;i++){
			var orgid = folList[i].orgid;
			var cusId = folList[i].cusId;
			var orgNum = folList[i].orgNum;
			var orgName = folList[i].orgName;
			var folDate = folList[i].folDate;
			if(orgName.length>16){
			  orgName=orgName.substring(0,16)+"...";
			}
			li = li + "<li><span>"+folDate+"</span><span onclick=\"createFollow("+cusId+")\" style='cursor:pointer;'>"+orgName+"</span>"+
					"<div style='display:inline-block;margin-left:137px'><a href='#' title='完成' onclick=\"doneFollow("+cusId+")\"><img width='16px' src='<%=path%>/style/image/doneFollow.png'/></a></div></li>";
		}
		$("#followUl").html(li);
		$("#followLiTotal").html("("+folTotal+")");
		$("#followTotal").html(folTotal);
	});
}

function getMainServiceInfo(){
	$.post('<%=path%>/supervisory/asyn/getMainServiceInfo',function(result){
		var serTotal = result.serTotal;
		var serList = result.serList;
		var li = "";
		var mun = 0;
		if(serList.length<4){
			mun = serList.length;
		}else{
			mun = 4;
		}
		for(var i=0;i<mun;i++){
			var stamp = serList[i].stamp;
			var orgName = serList[i].orgName;
			var nodeName = serList[i].nodeName;
			var ophId = serList[i].ophId;
			if(orgName.length>5){
			  orgName=orgName.substring(0,5)+"...";
			}
			if(nodeName.length>5){
			  nodeName=nodeName.substring(0,5)+"...";
			}
			li = li + "<li><span>"+stamp+"</span><span>"+orgName+"</span><span>"+nodeName+"</span>"+
					"<div style='display:inline-block;margin-left:15px'><a href='#' title='指派' onclick=\"openChMemDiv("+ophId+")\"><img width='16px' src='<%=path%>/style/image/button_bg_user.png'/></a>&nbsp&nbsp&nbsp"+
					"<a href='#' title='完成' onclick=\"doneService("+ophId+")\"><img width='16px' src='<%=path%>/style/image/doneFollow.png'/></a></div></li>";
		}
		$("#serviceUl").html(li);
		$("#serviceLiTotal").html("("+serTotal+")");
	});
}

var chMemDivLayer;
function openChMemDiv(ophId){
	chMemDivLayer = layer.open({
		type : 1,
		title : '指派审批人员',
		shade : [ 0.1, '#000' ],
		fix : true,
		maxmin: true,
		shadeClose: true,
		area : ['260px', '120px'],
		end : function(){
			getMainServiceInfo();
		},
		content : $("#chMemDiv"),
		success: function(){
			$.post('<%=path%>/supervisory/asyn/getChMemSel',function(result){
				var option = "";
				for(var i=0;i<result.length;i++){
					if(i==0){
						option = option + "<option value='"+result[i].memId+"' selected='selected'>"+result[i].memName+"</option>";
					}else{
						option = option + "<option value='"+result[i].memId+"'>"+result[i].memName+"</option>";
					}
				}
				$("#memSel").html(option);
				$("#ophIdH").val(ophId);
			});
		}
	});
}

function chMem(){
	var ophId = $("#ophIdH").val();
	var memberId = $("#memSel").val();
	if(memberId==null||memberId==""){
		parent.$.showMsg("请指派人员!", 2);
		return;
	}
	layer.confirm("确认指派该人员?", {icon: 3, title:"提示"}, function(index){
		tips = layer.msg("正在指派,请稍后...", {icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/updateServiceMember',{'ophId':ophId,'memberId':memberId},function(result){
			if(result.code){
				layer.close(tips);
				parent.$.showMsg("处理成功!", 1);
				window.layer.close(window.chMemDivLayer);
			}else{
				layer.close(tips);
				parent.$.showMsg("处理失败!", 2);
			}
		});
		layer.close(index);
	});
}

function getMainPayAuditInfo(){
	$.post('<%=path%>/supervisory/asyn/getAuditList?page=1&rows=4',function(result){
		var audTotal = result.mainTotal;
		var audList = result.rows;
		var auditorLevel = result.auditorLevel;
		var li = "";
		for(var i=0;i<audList.length;i++){
			var routeId = audList[i].routeId;
			var auditFlag = audList[i].auditFlag;
			var payDate = audList[i].payDate;
			var orgName = audList[i].orgName;
			if(orgName.length>5){
			  orgName=orgName.substring(0,5)+"...";
			}
			if(auditorLevel==88&&auditFlag!=0){
				li = li + "<li><span>"+payDate+"</span><span>"+orgName+"</span><div style='display:inline-block;margin-left:137px'>"+
				"<a href='#' onclick=\"passAudit('"+routeId+"','"+auditFlag+"','"+auditorLevel+"');\" title='完成'>"+
				"<img width='16px' src='<%=path%>/style/image/doneFollow.png'/></a></div></li>";
			}else{
				if(auditFlag==auditorLevel){
					li = li + "<li><span>"+payDate+"</span><span>"+orgName+"</span><div style='display:inline-block;margin-left:137px'>"+
					"<a href='#' onclick=\"passAudit('"+routeId+"','"+auditFlag+"','"+auditorLevel+"');\" title='完成'>"+
					"<img width='16px' src='<%=path%>/style/image/doneFollow.png'/></a></div></li>";
				}
			}
		}
		$("#payAuditUl").html(li);
		$("#payAuditTotal").html("("+audTotal+")");
	});
}

function doneService(ophId){
	layer.confirm("确认完成?", {icon: 3, title:"提示"}, function(index){
		tips = layer.msg("正在完成待办事项...", {icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/doneService',{'ophId':ophId},function(result){
			if(result.code){
				layer.close(tips);
				parent.$.showMsg("处理成功!", 1);
				getMainServiceInfo();
			}else{
				layer.close(tips);
				parent.$.showMsg("处理失败!", 2);
				getMainServiceInfo();
			}
		});
		layer.close(index);
	});
}

function createFollow(cusId){//进入跟进页面
	addFollow = layer.open({
		title : '待办事项',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		maxmin: true,
		shadeClose: true,
		area : ['550px', '480px'],
		content : '<%=path%>/supervisory/forward/gotoFollowCreate?cusId='+cusId,
		success : function(){
			//layer.full(addFollow);
		}
	});
}

function doneFollow(cusId){
	layer.confirm("确认完成?", {icon: 3, title:"提示"}, function(index){
		tips = layer.msg("正在完成待办事项...", {icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/doneFollow',{'cusId':cusId},function(result){
			if(result.code){
				layer.close(tips);
				parent.$.showMsg("处理成功!", 1);
				getMainFollowInfo();
			}else{
				layer.close(tips);
				parent.$.showMsg("处理失败!", 2);
				getMainFollowInfo();
			}
		});
		layer.close(index);
	});
}

var moreNewsDell;
function openMoreNews(cusId){
	moreNewsDell = layer.open({
		title : '公告详细列表',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		shadeClose: true,
		area : ['550px', '480px'],
		content : '<%=path%>/supervisory/forward/gotoMoreNews',
		success : function(){
		},
		end : function(){
			getPushMessage();
		}
	});
	layer.full(moreNewsDell);
}

function openMore(){
	if(moreRouter == 0){
		openMoreFollow();
	}else if(moreRouter == 1){
		openMoreService();
	}else if(moreRouter == 2){
		parent.addOptionTab('<%=path%>/supervisory/forward/gotoPayAudit','缴费审批列表');
	}
}

var moreFollowsDell;
function openMoreFollow(){
	moreFollowsDell = layer.open({
		title : '跟进详细列表',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		shadeClose: true,
		area : ['550px', '480px'],
		content : '<%=path%>/supervisory/forward/gotoMoreFollows',
		success : function(){
		},
		end : function(){
			getMainFollowInfo();
		}
	});
	layer.full(moreFollowsDell);
}

var moreServiceDell;
function openMoreService(){
	moreServiceDell = layer.open({
		title : '业务详细列表',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		shadeClose: true,
		area : ['550px', '480px'],
		content : '<%=path%>/supervisory/forward/gotoMoreService',
		success : function(){
		},
		end : function(){
			getMainServiceInfo();
		}
	});
	layer.full(moreServiceDell);
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
				$.showMsg("审批成功!",1);
				getMainPayAuditInfo();
			}else{
				layer.close(tips);
				$.showMsg("审批失败,系统出错!",2);
				getMainPayAuditInfo();
			}
		});
		layer.close(index);
	});
}

var openMoreInfoLayer;
function openMoreInfo(){
	openMoreInfoLayer = layer.open({
		type : 1,
		title : '更多信息',
		shade : [ 0.1, '#000' ],
		fix : true,
		maxmin: true,
		shadeClose: true,
		area : ['600px', '128px'],
		content : $("#moreInfo")
	});
}

</script>
</head>
<body style="min-width:1100px;">
<div class="content_left">
    <div class="headline" style=" margin-bottom:0px;">
       <img src="<%=path%>/style/image/lh_01.png"/>客户缴费/报税/工作量(图表)
	</div>
    <div id="replaceShow" style="width:100%;"></div>
	<div class="content_right">
		<div class="headline">
	            <img src="<%=path%>/style/image/lh_02.png"/>公告栏
	            <span><a href="#" onclick="openMoreNews();">更多 >></a></span>
	    </div>
	    <div id="newDiv" class="div_ul" style="height: 79%">
	        <ul class="notice-list" id="messageListUl">
	        </ul>
	    </div>
	     <div class="notice_button" id="pushNews" style="margin-top: 8px;display: none;"><a href="#" onclick="pushMessage();"><img src="<%=path%>/style/image/lh_fabu.png"/>发布公告</a></div>	
	</div>
 </div>

<div class="content_left_02">
   <div class="headline" style="margin-top: 5px">
        <img src="<%=path%>/style/image/lh_02.png"/>工作情况概览
       <span>记账期间: <input id="lisence_ID" name="lisence" maxlength="10" type="text" readonly="readonly" class="Wdate dfinput_fb" onFocus="WdatePicker({isShowClear:false,readOnly:true,onpicked:periodChange,dateFmt:'yyyy-MM'});" value="${homeDate}" style="width:80px;height:24px;"/></span>
	</div>
    <table width="100%" cellpadding="0" cellspacing="0" class="table_monitor">
    	<tr>
        	<th></th>
        	<th></th>
        	<th></th>
        	<th></th>
        	<th></th>
        	<th></th>
        </tr>
    	<tr> 
        	<td id="/supervisory/forward/gotoCustomPayLook" width="16%" style="display: none;" mes="menu">
                <a href="#"><img src="<%=path%>/style/image/lh_gn01.png" onclick="gotoDetail('<%=path%>/supervisory/forward/gotoCustomPayLook','缴费管理',2);"/><span id="arrearsCom">0</span></a>
                <h6>欠费公司</h6>
            </td>
        	<td id="onLineState" width="16%" style="display: none;" mes="menu">
                <a href="#"><img src="<%=path%>/style/image/lh_gn02.png" onclick="parent.addOptionTab('<%=path%>/supervisory/forward/gotoOnlineState','在线状态');"/><span id="onlineClerks">0</span> </a>
                <h6>在线人数</h6>
            </td>
        	<td width="16%">
                <a href="#" onclick="openMoreFollow();"><img src="<%=path%>/style/image/lh_gn03.png"/><span id="followTotal">0</span></a>
                <h6>待办事项</h6>
            </td>
        	<td id="/supervisory/forward/gotoCustomSetting" width="16%" style="display: none;" mes="menu">
                <a href="#"><img src="<%=path%>/style/image/lh_gn04.png" onclick="parent.addOptionTab('<%=path%>/supervisory/forward/gotoCustomSetting','客户信息');"/></a>
                <h6>客户信息</h6>
            </td>
        	<td id="/supervisory/forward/gotoFrameworkManage" width="16%" style="display: none;" mes="menu">
                <a href="#"><img src="<%=path%>/style/image/lh_gn05.png" onclick="parent.addOptionTab('<%=path%>/supervisory/forward/gotoFrameworkManage','组织架构');"/></a>
                <h6>组织架构</h6>
            </td>
        	<td width="16%">
                <a href="#" onclick="openMoreInfo();"><img src="<%=path%>/style/image/lh_gn06.png"/></a>
                <h6>更多</h6>
            </td>
        </tr>
    	<tr>
        	<th></th>
        	<th></th>
        	<th></th>
        	<th></th>
        	<th></th>
        	<th></th>
        </tr>
    </table>
	<div class="content_right_02">
		<div class="headline" style="margin-top: 4px">
	            <img src="<%=path%>/style/image/lh_02.png"/>待办事项
	            <span><a href="#" onclick="openMore();">更多 >></a></span>
	    </div>
	    <div class="div_ul" style="height: 152px" id="tab">
	    	<div class="paging">
            	<a class="cur">跟进<span id="followLiTotal"></span></a>
            	<a>业务<span id="serviceLiTotal"></span></a>
            	<a>审批<span id="payAuditTotal"></span></a>
            	<a>消息</a>
            </div>
	        <ul class="notice-list" id="followUl">
	        </ul>
	        <ul class="notice-list" id="serviceUl" style="display: none;">
	        </ul>	
	        <ul class="notice-list" id="payAuditUl" style="display: none;">
	        </ul>	
	        <ul class="notice-list" id="to_do" style="display: none;">
	        </ul>	
	    </div>
	</div>
</div>
<div id="moreInfo" style="display: none;">
  	<table id="moreInfoTable" class="table_query"  cellpadding="0" cellspacing="0" rules="all">
  		<tr> 
      	<td width="15" align="left" style="padding-left:5px"><img src="<%=path%>/style/image/point02.png"/></td>	
      	<td width="310" align="left">该记账期间内,共有<font id="settleState" style="color:#F00"></font>家公司已完成结账,完成率<font id="settleRate" style="color:#F00"></font></td>	
      	<td width="95" align="right"><a id="newAdd" class="bcssbtn" style="margin-right: 4px" href="javascript:void(0);" onclick="gotoDetail('<%=path%>/supervisory/forward/gotoSettleDetail','结账信息');">查看详情</a></td>	
      </tr>
      <tr> 
      	<td width="15" align="left" style="padding-left:5px"><img src="<%=path%>/style/image/point02.png"/></td>	
      	<td width="310" align="left">该记账期间内,共有<font id="taxState" style="color:#F00"></font>家公司已完成报税,完成率<font id="taxRate" style="color:#F00"></font></td>	
      	<td width="95" align="right"><a id="newAdd" class="bcssbtn" style="margin-right: 4px" href="javascript:void(0);" onclick="gotoDetail('<%=path%>/supervisory/forward/gotoTaxDetail','报税信息');">查看详情</a></td>	
      </tr>
  </table>
</div>

<div id="chMemDiv" style="display: none;">
	<table class="table_query" border="0" cellpadding="0" cellspacing="0" rules="all">
		<tr>
			<td align="left" style="border:0;"><h4 style="display: inline-block;">指派人员:</h4></td>
			<td align="left" style="border:0;">
				<select id="memSel" name="memSel" style="border:1px solid #ccc;width: 120px;"></select>
				<input type="hidden" id="ophIdH" name="ophIdH"/>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center" style="border:0;padding-top: 10px"><input class="bcssbtn" type="button" style="padding: 4px 8px 4px 8px" onclick="chMem();" value="提交"  /></td>
		</tr>
	</table>
</div>

</body>
</html>


