<%@page import="com.wb.component.computer.sendMessageManager.controller.SendMessageAction"%>
<%@page import="com.wb.component.computer.sendMessageManager.service.imp.SendMessageService"%>
<%@page import="java.util.Date"%>
<%@page import="com.wb.model.entity.computer.SendMessage"%>
<%@page import="util.SendMessageUtil"%>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%String path = request.getContextPath();%>
<% String registerFlag=(String)session.getAttribute("registerFlag"); 
	if(null!=registerFlag && "1".equals(registerFlag)){
		String tel=(String)session.getAttribute("tel");
		/* String member_id=(String)session.getAttribute("member_id"); */ 
		String user="王艳福";
		String pass="weibao2004";
		StringBuffer context=new StringBuffer();
		context.append("恭喜您注册成为中国领先的企业在线财务服务对象。快速下载手机端产品，随时");
		context.append("随地掌握企业一手财务金融状况。"+"\r\n");
		context.append("永久免费的企业在线财务管理软件。"+"\r\n");
		context.append("http://www.weibaobeijing.com/Accounting/financeDownload.jspx"+"\r\n");
		context.append("专业便捷的企业金融和理财顾问。"+"\r\n");
		context.append("http://120.25.223.132:8980/Oemp/html/download.html"+"\r\n");
		context.append("服务公司移动办公的不二之选 。"+"\r\n");
		context.append("http://www.weibaobeijing.com/Accounting/agencyDownload.jspx"+"\r\n");
		context.append("。"+"\r\n");
 		System.out.println("user="+user+"pass="+pass+"context="+context.toString()+"tel="+tel);
 		
 		String result=SendMessageUtil.SendMsg(user, pass, context.toString(),tel);
		System.out.println("result="+result);
		
		/* String  codeAndMessage[]=result.split("\\|");
		 if(codeAndMessage[0].equals("0")){// 0是失败的情况
			
		}else if(codeAndMessage[0].equals("1")){ //1是失败的情况
			SendMessageAction sendMessageService=new SendMessageAction();
			sendMessageService.saveTuiSong(context.toString(),tel,Integer.valueOf(member_id));
		}  */
	}

%>


<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<title>代帐公司管理系统</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style.css"/>
<script>
//防止嵌套
$(function(){
	$("#tel_ID").focus();
})

if(window.top!=window.self){
	top.location.href='<%=path%>/supervisory/forward/gotoLogin';
}
if('<%=session.getAttribute("userInfo")%>' != 'null'){
	window.location.href='<%=path%>/supervisory/forward/gotoMainFrame';
}
//登录方法
var ifLogining = false;
function toLogin(){
	if($("#tel_ID").val()==null||$("#tel_ID").val()==""){
		layer.msg("请输入管理员账号!", {icon : 2, time : 1500});
		$("#tel_ID").focus();
		return;
	}
	if($("#pwd_ID").val()==null||$("#pwd_ID").val()==""){
		layer.msg("请输入密码!", {icon : 2, time : 1500});
		$("#pwd_ID").focus();
		return;
	}
	if(ifLogining){
		return;
	}
	var tel = $("#tel_ID").val();
	var pwd = $("#pwd_ID").val();
	ifLogining = true;
	var lloading = layer.msg("正在登陆...", {icon : 16, shade: false, time : 0});
	$.ajax({ 
		type:'POST',
		url : '<%=path%>/supervisory/asyn/loginPlatform',
		data: {telphone:tel,password:pwd}, 
		dataType:'json',
		success : function(result) {
			ifLogining = false;
			layer.close(lloading);
			if(result.success){
				window.location.href='<%=path%>/supervisory/forward/gotoMainFrame';
	        }else{
	        	layer.msg(result.message, {icon : 4, time : 1500});
	        }
		},
		error:function(XMLHttpRequest,textStatus,errorThrown){
			ifLogining = false;
			layer.msg("亲!您的网络不给力哦~ ", {icon : 5, time : 1500});
		}
	});
}

//Enter键登陆
function toSubmit(event){
	if(event.keyCode==13){
		if (navigator.userAgent.indexOf('MSIE')>=0){
			event.keyCode=0;
			event.returnValue=false;
		}else{
			event.preventDefault();
		}
		toLogin();
	}
}

function pwdGetBack(){
	window.location.href = '<%=path%>/supervisory/forward/gotoPwdGetBack?tel=' + $("#tel_ID").val();
}

function checkTel(){
	if($("#tel_ID").val().length == 11){
		$("#pwd_ID").focus();
	}
}
</script>
</head>
<body onkeydown="toSubmit(event);">
	<!--登录头部-->
	<div class="wtdl_top">
    	<div class="wtdl_top_1">
        	<div class="wt_logo">
            	<div class="wt_logo_1"><img src="<%=path%>/style/image/wt_gslogo.png" width="82" height="38" /><span>欢迎登录</span></div>
                <div class="wt_logo_2">中国第一家永久免费移动财务软件</div>
            </div>
          <div class="wt_lxfs">
            	<div class="wt_lxfs_1"><img src="<%=path%>/style/image/wt_phonetb.png" width="22" height="24" /><span>热线电话</span></div>
            <div class="wt_lxfs_1" style="font-weight:bold;">4000-110-543</div>
          </div>
        </div>
    </div>
    <!--内容部分-->
    <div class="wtdl_conter">
    	<div class="wt_dlbox">
       	  <div class="wt_dlbox_1">
            	<div class="wt_dengltop"><img src="<%=path%>/style/image/wt_dlbiaot.jpg" width="260" height="40" /></div>
                <div class="wt_shuju">
                      <div class="wt_shuju_1" style="border-bottom:solid 1px #e3e3e3">
                      		<div class="wt_shuju_1nei">
                                <div class="wt_shuju_img"><img src="<%=path%>/style/image/wt_yhmtb.png" width="15" height="19" /></div>
                                <div class="wt_shuju_tx"><input placeholder="用户名"  name="telphone" id="tel_ID" maxlength="11" type="text" style="border:none;" class="txys" onkeyup="checkTel()"/></div>
                            </div>
                      </div>
                   	  <div class="wt_shuju_1">
                      		<div class="wt_shuju_1nei">
                                <div class="wt_shuju_img"><img src="<%=path%>/style/image/wt_mmtb.png" width="15" height="19" /></div>
                                <div class="wt_shuju_tx"><input placeholder="密码" name="password" id="pwd_ID" type="password"  maxlength="16" style="border:none;" class="txys"/></div>
                            </div>
                      </div>
                </div>
                <!--登录按钮-->
                <div class="wt_denglbtn" style="cursor: pointer;" onclick="toLogin();">登 录</div>
                <p><a href="<%=path%>/supervisory/forward/gotoMemberRegister">免费注册</a> | <a href="#" onclick="pwdGetBack()">忘记密码</a></p>
          </div>
        </div>
    </div>
</body>
</html>