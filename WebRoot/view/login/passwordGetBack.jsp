<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%String path = request.getContextPath();%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<title>找回密码</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style.css"/>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script>
	$(function(){
		$("#getBtn").bind("click",checkCode);
	})
	function checkForm(){
		var telphone=document.getElementById("telphone").value;
		var checkCode=document.getElementById("checkCode").value;
		var re = /^1\d{10}$/;
		if(!re.test(telphone)){
			document.getElementById("errorTelInput").style.display = "block";
			document.getElementById("cueTel").style.display = "none";
			return false;
		}else{
			document.getElementById("errorTelInput").style.display = "none";
			document.getElementById("cueTel").style.display = "none";
		}
		if(!checkCode){
			return false;
		}
	}

	var seconds = 60;
	function disableBtn(){
		if(seconds>0){
			seconds--;
			$("#getBtn").html("<font color='#c3c3c3'>重新发送<font>" + seconds)
			$("#getBtn").unbind("click",checkCode);
		}
		else{
			$("#getBtn").html("重新获取")
			$("#getBtn").bind("click",checkCode);
			window.clearInterval(task1); 
		}
	}
	
	var task1 = '';
	var checkCode = function (){
		seconds =60;
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
						$.ajax({ 
							type:"POST", 
							url : "<%=path%>/supervisory/asyn/getCheckCode", 
							data:{tel:telphone,type:2}, 
							success : function(data) {
								var result = eval("("+data+")");
								layer.close(tips);
								if(result.success){
									task1 = setInterval(disableBtn, 1000)
									$.showMsg(result.message, 6);
								}else{
									$.showMsg(result.message, 5);
								}
							} 
					    });
					}else{
						layer.close(tips);
						$.showMsg("手机未注册!", 4);
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
</head>

<body>
<div class="registration_head">
	<div class="login_box">
        <div class="registration_logo" style="width:275px;">找回密码</div>
        <div class="registration_login">
        	已有账号 <a href="<%=path%>/supervisory/forward/gotoLogin" class="bcssbtn" style="padding: 2px 6px 2px 6px">登录</a>
        </div>
	</div>
</div>
<div class="login_content">
    <form id="memberRegisterForm" method="post" action="<%=path%>/supervisory/forward/gotoPwdReset" onsubmit="return checkForm()" >
	<table width="640" cellpadding="0" cellspacing="0" class="table_registration">
    	<tr>
        	<td width="110" align="right">手机号:</td>
        	<td width="310"><input  name="telphone" maxlength="11" type="text" id="telphone" value="${tel}" /></td>
        	<td width="200" align="left">
                <span class="labels" id="cueTel" >
                    <font style="color:#EC0000">* </font>注册的手机号码
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
        	<a href="javascript:void(0)" id="getBtn" class="bcssbtn">获取验证码</a>
        	</td>
        	<td width="200" align="left">
            </td>
        </tr>
    	<tr>
        	<td width="110" align="right"></td>
        	<td width="310"><input type="submit" value="提交" class="registration_submit"/></td>
        	<td width="200" align="left"></td>
        </tr>
    </table>
</form>
</div>
</body>
</html>
