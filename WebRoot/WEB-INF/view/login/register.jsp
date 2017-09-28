<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%String path = request.getContextPath();%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<title>代账公司管理员注册</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style.css"/>
<script>
$(function(){
	$("#getBtn").bind("click",checkCode);
});

function checkpwd(){
	var pwd = document.getElementById("pwd").value;
	var repwd = document.getElementById("repwd").value;
	if(repwd != pwd){
		document.getElementById("pwdInfo").style.display = "none";
		document.getElementById("errorpwd").style.display = "block";
		$("#repwd").val("");
		return false;
	}
}

var seconds = 60;
var task1 = '';
function disableBtn(){
	if(seconds>0){
		seconds--;
		$("#getBtn").html("<font color='#c3c3c3'>重新发送<font>" + seconds)
		$("#getBtn").unbind("click",checkCode);
	}
	else{
		$("#getBtn").html("重新获取")
		seconds = 60;
		$("#getBtn").bind("click",checkCode);
		window.clearInterval(task1); 
	}
}

function recheck(){
	document.getElementById("pwdInfo").style.display = "block";
	document.getElementById("errorpwd").style.display = "none";	
}

function recheckCert(){
	document.getElementById("errorCert").style.display = "none";	
}

function recheckTel(){
	document.getElementById("errorTel").style.display = "none";
	document.getElementById("errorTelInput").style.display = "none";	
	document.getElementById("cueTel").style.display = "block";
}

function checkNull(){
	var orgName=document.getElementById("orgName").value;
	if(!orgName){
		$.showMsg("公司名称不能为空,请填写！", 2);
		return false;
	}
	var userName=document.getElementById("userName").value;
	if(!userName){
		$.showMsg("用户名不能为空,请填写！", 2);
		return false;
	}
	var pwd = document.getElementById("pwd").value;
	var repwd = document.getElementById("repwd").value;
	var checkCode = document.getElementById("checkCode").value;
	var verifyCode = document.getElementById("verifyCode").value;
	if(!pwd){
		$.showMsg("请输入密码！", 2);
		return false;
	}
	if(!checkCode){
		$.showMsg("请输入验证码！", 2);
		return false;
	}
	if(!repwd){
		$.showMsg("请输入确认密码！", 2);
		return false;
	}
	if(checkTel()){
		return true;
	}else{
		$.showMsg("请填写正确的手机号！", 2);
		return false;
	}
}

function checkTel(){
	var telphone=document.getElementById("telphone").value;
	var re = /^1\d{10}$/;
	if(!re.test(telphone)){
		document.getElementById("errorTelInput").style.display = "block";
		document.getElementById("cueTel").style.display = "none";
		return false;
	}else{
		$.ajax({ 
			type:"POST", 
			url : "<%=path%>/supervisory/asyn/checkTelphone", 
			data:{tel:telphone}, 
			success : function(data) {
				var result = eval("(" +data +")");
				if(!result.success){
					document.getElementById("errorTel").style.display = "none";
					document.getElementById("cueTel").style.display = "none";
					return false;
				}else{
					return true;
				}
			} 
		 });
	}
}

var checkCode = function (){
	var tips = layer.msg("验证码发送中...", {icon : 16,time : 0, shade: [0.1]}); 
	var telphone=document.getElementById("telphone").value;
	var re = /^1\d{10}$/;
	if(!re.test(telphone)){
		document.getElementById("errorTelInput").style.display = "block";
		document.getElementById("cueTel").style.display = "none";
		layer.close(tips);
		return false;
	}else{
		$.ajax({ 
				type:"POST", 
				url : "<%=path%>/supervisory/asyn/checkTelphone", 
				data:{tel:telphone}, 
				success : function(data) {
					var result = eval("(" +data +")");
					if(!result.success){
						document.getElementById("errorTel").style.display = "block";
						document.getElementById("cueTel").style.display = "none";
						document.getElementById("errorTelInput").style.display = "none";
						layer.close(tips);
						return false;
					}else{
						$.ajax({ 
							type:"POST", 
							url : "<%=path%>/supervisory/asyn/getCheckCode", 
							data:{tel:telphone,type:1}, 
							success : function(data) {
								var result = eval("("+data+")");
								layer.close(tips);
								if(result.success){
									task1 = setInterval(disableBtn, 1000)
									$.showMsg(result.message, 6);
								}else{
									$.showMsg(result.message, 6);
								}
							} 
					    });
						return true;
					}
				} 
		 });
	}
}

function changeCode(){
	var src= $("#codeImg").attr("src");
	$("#codeImg").attr("src",src+"?_r="+Math.random());
}
</script>
<style>
.table_registration td {height:48px;line-height:48px;}
</style>
</head>
<body>
<div class="registration_head">
	<div class="login_box">
		<div class="registration_logo" style="width:540px;font-size:26px;">代账公司管理系统</div>
        <div class="registration_login">
        	已有账号 <a href="<%=path%>/supervisory/forward/gotoLogin" class="bcssbtn" style="padding: 2px 6px 2px 6px">登录</a>
        </div>
	</div>
</div>
<div class="login_content">
    <form id="memberRegisterForm" method="post" action="<%=path%>/supervisory/asyn/register" onsubmit="return checkNull()" >
	<h3 align="center"><font color="red">${requestScope.message}</font></h3>
	<table width="640" cellpadding="0" cellspacing="0" class="table_registration">
    	<tr>
    		<td width="110" align="right">公司名称:</td>
        	<td width="310"><input name="orgName" type="text" id="orgName" maxlength="36"/></td>
        	<td width="220" align="left">
                <span class="labels">
                    <font style="color:#EC0000">* </font>必填
                </span>
                <span class="sign" style="display:none" >
                    <img src="<%=path%>/style/image/sign.png"/>公司名称不能为空
                </span>
            </td>
        </tr>
        <tr>
        	<td width="110" align="right">用户名:</td>
        	<td width="310"><input name="userName" type="text" id="userName" maxlength="10"/></td>
        	<td width="220" align="left">
                <span class="labels">
                    <font style="color:#EC0000">* </font>必填
                </span>
                <span class="sign" style="display:none" >
                    <img src="<%=path%>/style/image/sign.png"/>用户名不能为空
                </span>
            </td>
        </tr>
    	<tr>
        	<td width="110" align="right">手机号:</td>
        	<td width="310"><input  name="telphone" type="text" id="telphone" maxlength="11" /></td>
        	<td width="200" align="left">
                <span class="labels" id="cueTel" >
                    <font style="color:#EC0000">* </font>请输入真实号码(登陆账号)
                </span>
                <span class="sign" style="display:none" id="errorTel" >
                    <img src="<%=path%>/style/image/sign.png"/>此号码已被注册
                </span>
                <span class="sign" style="display:none" id="errorTelInput" >
                    <img src="<%=path%>/style/image/sign.png"/>请输入正确的手机号
                </span>
            </td>
        </tr>
        <tr>
        	<td width="110" align="right">验证码:</td>
        	<td width="310" style="line-height: 22px">
        	<input  name="checkCode" type="text" id="checkCode" style="width: 150px" maxlength="4" />
        	<a href="javascript:void(0)" id="getBtn"  class="bcssbtn">获取验证码</a>
        	</td>
        	<td width="200" align="left">
            </td>
        </tr>
    	<tr>
        	<td width="110" align="right">密码:</td>
        	<td width="310"><input name="password" type="password" id="pwd" maxlength="16" /></td>
        	<td width="200" align="left">
                <span class="labels">
                    <font style="color:#EC0000">* </font>必填
                </span>
                <span class="sign" style="display:none">
                    <img src="<%=path%>/style/image/sign.png"/>密码不能为空
                </span>
            </td>
        </tr>
    	<tr>
        	<td width="110" align="right">重复密码:</td>
        	<td width="310"><input name="repwd" type="password" id="repwd" onblur="return checkpwd()" onfocus="recheck()" maxlength="16" /></td>
        	<td width="200" align="left">
                <span class="labels" id="pwdInfo" >
                    <font style="color:#EC0000">* </font>必填
                </span>
                <span class="sign" style="display:none" id="errorpwd" >
                    <img src="<%=path%>/style/image/sign.png"/>两次输入密码必须一致
                </span>
            </td>
        </tr>
    	<tr>
        	<td width="110" align="right"></td>
        	<td width="310"><input type="submit" value="提交" class="registration_submit" style="padding: 2px 6px 2px 6px"/></td>
        	<td width="200" align="left"></td>
        </tr>
    </table>
</form>
</div>
</body>
</html>
