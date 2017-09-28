<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>管理员密码修改</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/bootstrap/css/bootstrap.min.css"/>
<script type="text/javascript">
$(function(){
	//提交事件绑定
	$("#submit-id").click(function(event){
		var originPwd = $("#originPassword").val();
		var firstPwd = $("#firstPassword").val();
		var confirmPwd = $("#confirmPassword").val();
		if(!isHaveValue(originPwd)){
			parent.$.showMsg('原始密码不能为空!', 2);
			return false;
		}
		if(!isHaveValue(firstPwd)){
			parent.$.showMsg('新密码不能为空!', 2);
			return false;
		}
		if(!isHaveValue(confirmPwd)){
			parent.$.showMsg('确认密码不能为空!', 2);
			return false;
		}
		if(firstPwd!=confirmPwd){
			parent.$.showMsg('新密码与确认密码要相同!', 2);
			return false;
		}
		var tips = layer.msg("密码保存中...",{icon : 16,time : 0, shade: [0.1]});
		$.ajax({
			url:'<%=path%>/supervisory/asyn/modifyPwdForMng',
			data:{pwd0:originPwd,pwd1:firstPwd},
			type:'post',
			dataType : 'json',
			success:function(result){
				layer.close(tips);
				if(result.success){
					parent.$.showMsg(result.message, 6, function(){
						parent.deleteTab("密码修改");
					});
				}else{
					parent.$.showMsg(result.message, 5);
				}
			},
			error:function(jqXHR, textStatus, errorThrown ){
				parent.$.showMsg("亲!您的网络不给力哦~", 5)
			}
		});
	});
});

//是否有值
function isHaveValue(value){
	if(value!=null&&value!=""){
		return true;
	}else{
		return false;
	}
}
</script>
</head>
<body>
	<div class="well" width="80%" style="margin:20px 50px 20px 20px;">
	  	<form id="form-PWModify" class="form-horizontal" >
	  		<div class="control-group"><label class="control-label" for="inputPassword0">当前密码:</label>
				<div class="controls"><input id="originPassword" name="pwd0" maxlength="16" placeholder="当前密码" type="password" /></div>
			</div>
			<div class="control-group"><label class="control-label" for="inputPassword1">新密码: &nbsp;</label>
				<div class="controls"><input id="firstPassword" name="pwd1" maxlength="16" placeholder="新密码" type="password" /></div>
			</div>
			<div class="control-group"><label class="control-label" for="inputPassword2">确认密码:</label>
				<div class="controls"><input id="confirmPassword" name="pwd2" maxlength="16" placeholder="确认密码" type="password"/></div>
			</div>
			<div class="control-group">
				<div class="controls"><input id="submit-id" type="button" style="margin-top:15px;width:70px;padding: 2px 6px 2px 6px" class="btn-sm btn-primary" value="提交"/></div>
			</div>
		</form>
	</div>
</body>
</html>
