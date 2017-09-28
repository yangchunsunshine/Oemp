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
<title>缴费管理-消息发送编辑</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/util/validateSms.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<link type="text/css" rel="stylesheet" href="<%=path%>/style/css/style.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqueryui/jquery-ui.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqgrid/css/ui.jqgrid.css"/>
<script type="text/javascript">

var isAdmins = "${isAdmin}"; //标识此业务是admin数据还是员工数据

var empInfo= '${sessionScope.userInfo.empInfo}';
var memberId =  '${sessionScope.userInfo.empInfo.id}';
var isAdmin = $.getCookie("isAdmin");
var orgName = "${orgName}";
var daiLiOrgName = "${daiLiOrgName}";
var date = "${date}";
var checkDate = "${checkDate}";
var custMobile = "${custMobile}";
var flag = "1"; //标识是否已经提示过字数超过70
var type = "${type}";
var orgId = "${orgId}";
$(function(){
	
	//设置发送内容默认值
	setSendContent();
	//设置发送时间的默认值
	$("#sendTime").val(date);
	
});
//初始化textarea的事件
$(document).bind('propertychange input', function () {
	var counter = $('#sendContent').val().length;
	if(flag!="2"){
		if(counter>=71){
			flag = "2";
			parent.$.showMsg("温馨提示:短信发送内容分为2条发送",1);
		}
	}
	if(counter>300){
		var text = $('#sendContent').val().substring(0,300);
		$('#sendContent').val(text);
		counter = $('#sendContent').val().length;
	}
	$("#num").text(counter);
	
});


var sendContent;
function setSendContent(){
	if("1"==type){
		sendContent = "尊敬的"+orgName+"，您在"+daiLiOrgName+"代理公司代理记账服务已经到期，请您提前续费，感谢您的支持和理解。";
	}else{
		sendContent = "尊敬的"+orgName+"，您在"+daiLiOrgName+"代理公司代理记账服务即将到期，请您提前续费，感谢您的支持和理解。";
	}
	
	
	$("#sendContent").val(sendContent);//设置文本域的值
	//获取文本域的字数
	var le = sendContent.length;
	$("#num").text(le);
}



function closeFrame(){ //点击关闭按钮,关闭当前页面,并刷新父级页面
	//window.parent.thisGrid.trigger("reloadGrid");
	//window.parent.layer.close(window.parent.queryBusi);
}


function sendMsg(type){
	//var validateSms = "";
	var b =validateSms.split("|");
	var senContent = $("#sendContent").val();
	for(var i=0;i<b.length;i++){
		var substr =b[i];
		if(senContent.indexOf(substr)>=0){
			parent.$.showMsg("短信内容包含非法内容:"+substr,2); 
			return ;
		}
	}
	if(senContent.length>300){
		parent.$.showMsg("短信发送内容不可超过300字",2); 
		return ;
	}
	
	if(custMobile==null||custMobile==""){
		parent.$.showMsg("该客户手机号为空无法发送,请完善客户的手机号！",2);
		return ;
	}
	
	
	var sendTime = $("#sendTime").val();
	if(sendTime<checkDate){
		parent.$.showMsg("发送时间必须大于"+checkDate,3);
		return ;
	}
	
	
	$.ajax( {   
		type : 'post',
		data : {type:type,senContent:senContent,custMobile:custMobile,sendTime:sendTime,orgId:orgId},
		dataType : 'json',
		url : '<%=path%>/supervisory/asyn/sendMsg',
		beforeSubmit : function() {
			tips = layer.msg("正在执行...",{icon : 16,time : 0, shade: [0.1]});
		},
		success : function(result) {
			if(1==type){
				if(result.code=="success"){
					parent.$.showMsg("执行成功",1);
				}else {
					parent.$.showMsg(result.code,2);
				}
			}else{
				if(result.code==1){
					parent.$.showMsg("执行成功",1);
				}else {
					parent.$.showMsg("执行失败",2);
				}
			}
			
		},
		error : function(result) {
			alert("亲!您的网络不给力哦~");
			searchfb();
		}
	});
	
	
	
}
</script>
</head>
<body style="min-width:1100px;overflow-X: hidden;">
	<div id="orgPageRecover" style="width:95%;margin:25px 0 15px 20px;">
		<div style="width:80px;float:left;">
			<span>发送内容:</span>
		</div>
		<div style="">
			<textarea id="sendContent" name="sendContent"  style="width:300px;height:200px;border:grey solid 1px;"></textarea>
		</div>
		<div style="margin-left:340px;font-size:9px;" id="checkCount"><span id="num">1</span><span >/300字</span></div>
		<div>
			<span>发送时间:</span>
			<input type="text" id="sendTime" name="sendTime" onfocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'});" value="" class="Wdate" style="margin-top:5px;height: 25px" />
		</div>
		
		<div style="margin-top:50px;">
			<input type="button" id="" class="bcssbtn" style="padding: 2px 6px 2px 6px;margin-left:150px;" onclick="sendMsg(1);" value="立即发送" />
			<input type="button" id="" class="bcssbtn" style="padding: 2px 6px 2px 6px" onclick="sendMsg(2);" value="定时发送" />
		</div>
	</div>

</body>   
</html> 