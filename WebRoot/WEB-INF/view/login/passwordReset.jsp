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
<title>重置密码</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style.css"/>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script>
	function checkForm(){
		var password = $("#password").val();
		var checkPsw = $("#checkPsw").val();
		if(!password){
			$("#tip").html("请填写新密码！");
			return false;
		}
		if(password != checkPsw){
			$("#tip").html("两次输入密码不一致");
			return false;
		}
		$.ajax({
			type:"POST", 
			url : "<%=path%>/supervisory/asyn/pwdModify", 
			data:{password:password}, 
			success : function(data) {
				var result = eval("(" +data +")");
				if(!result.success){
					$("#tip").html(result.message);
					return false;
				}else{
					$.showMsg(result.message, 6,function(){
						location.href='<%=path%>/supervisory/forward/gotoLogin';
					});
				}
			} 
		})
	}
</script>
</head>

<body>
<div class="registration_head">
	<div class="login_box">
        <div class="registration_logo" style="width:275px;">重置密码</div>
	</div>
</div>
<div class="login_content">
    <form id="memberRegisterForm" >
	<h3 align="center"><font color="red" id="tip">${requestScope.message }</font></h3>
	<table width="640" cellpadding="0" cellspacing="0" class="table_registration">
    	<tr>
        	<td width="110" align="right">新密码:</td>
        	<td width="310"><input  name="passward" type="password" id="password" /></td>
        	<td width="200" align="left">
                
            </td>
        </tr>
        <tr>
        	<td width="110" align="right">重复密码:</td>
        	<td width="310"><input  name="checkPsw" type="password" id="checkPsw" /></td>
        	<td width="200" align="left">
            </td>
        </tr>
    	<tr>
        	<td width="110" align="right"></td>
        	<td width="310"><input type="button" value="提交" class="registration_submit" style="padding: 2px 6px 2px 6px" onclick="checkForm()" /></td>
        	<td width="200" align="left"></td>
        </tr>
    </table>
</form>
</div>
</body>
</html>
