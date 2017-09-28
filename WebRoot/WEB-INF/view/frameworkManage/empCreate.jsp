<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.wb.framework.commonUtil.PropertiesReader"%>
<%PropertiesReader reader = PropertiesReader.getInstance();%>
<%String url = reader.getValue("/com/wb/config/application", "ACC_ROLER_URL");%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" /> 
<title>添加员工页面</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style_mnt.css"/>
<script type="text/javascript">
	$(function(){
		$("#empNo").mustNumber();
		$("#empQQ").mustNumber();
		$("#empPartId").ckCombox({
			url : '<%=path%>/supervisory/asyn/findMntFrameWork',
			requestType : "GET",
			data: {'ifHasEmp' : 'no'},
			dataType : "json",
			width : "145px",
			height : "22px",
			defaultSel : false,
			formatter : {
				id : "id",
				value : "partName"
			}
		});
		getOrgRole();
	});
	
	function getOrgRole(){
		var ACC_ROLER_URL = '<%=url%>';
		var orgId = ${userInfo.orgId};
		var url = ACC_ROLER_URL+"/auth/sysauth/role/get.jspx";
		$.ajax({
			type : "get",
			timeout : 5000,
	        dataType : "jsonp",  
	       	jsonp : "callBack",
	       	data : {"refCompanyId":orgId},
			url  : url,
	       	success : function(data){
	       		var mess = data.results;
	       		var roleStr = "";
	       		for(var i=0;i<mess.length;i++){
	       			if(mess[i].roleName=='查看者'){
	       				roleStr = roleStr + "<a><input name='editRole' type='checkbox' checked='checked' disabled='disabled' id='editRole"+mess[i].rid+"' value='"+mess[i].rid+"' />"+mess[i].roleName+"</a>";
	       			}else{
	       				roleStr = roleStr + "<a><input name='editRole' type='checkbox' id='editRole"+mess[i].rid+"' value='"+mess[i].rid+"' />"+mess[i].roleName+"</a>";
	       			}
	       			
	       		}
	       		$("#ChooseRole").html(roleStr);
	       	},
	       	error : function(XMLHttpRequest, textStatus, errorThrown) {
				parent.$.showMsg("亲!您的网络不给力哦~", 2, null, 5000);
			}
		});
	}
	
	function saveRole(memberId){
		var ACC_ROLER_URL = '<%=url%>';
		var url = ACC_ROLER_URL+"/auth/sysauth/memberRoleMulti/put.jspx";
		var roleList = [];
		var editRole = $("[name='editRole']:checked");
		if(editRole.length==0){
			var roleObj = new Object();
			roleObj.memberid = memberId;
			roleObj.roleid ="";
			roleList.push(roleObj);
		}else{
			for(var i=0;i<editRole.length;i++){
				var roleObj = new Object();
				roleObj.memberid = memberId;
				roleObj.roleid = editRole[i].value;
				roleList.push(roleObj);
			}
		}
		var dataList = JSON.stringify(roleList);
		$.ajax({
			type : "get",
			timeout : 5000,
	        dataType : "jsonp",  
	       	jsonp : "callBack",
	       	data : {"dataList":dataList},
			url  : url,
	       	success : function(data){
				parent.searchfb();
				closeWindow();
	       	},
	       	error : function(XMLHttpRequest, textStatus, errorThrown) {
				parent.$.showMsg("亲!您的网络不给力哦~", 2, null, 5000);
				parent.searchfb();
				closeWindow();
			}
		});
	}

	function sendCheckCode(){

		var tel  = $("#empNo").val();
		var name = $("#empName").val();
		if($.trim(name) == "" || name == null){
			parent.$.showMsg("请输入员工姓名!", 2);
			return false;
		}
		var re = /^1\d{10}$/;
		if(!re.test(tel)){
			parent.$.showMsg("员工手机号码格式不正确!", 2);
			return false;
		}
		if(!$("#empEmail").isEmail()){
			parent.$.showMsg("邮箱格式不正确!", 2);
			return false;
		}
		

		
		
		$.get('<%=path%>/supervisory/asyn/checkTelphone', {tel:tel}, function(data){
			var result = eval("(" +data +")");
			if(!result.success){
				parent.$.showMsg(result.message, 2);
			}else{
				var tips = layer.msg("验证码发送中...", {icon : 16,time : 0, shade: [0.1]});
				$.ajax({ 
					type:"POST", 
					url : "<%=path%>/supervisory/asyn/getCheckCode", 
					data:{tel:tel, type:1},
					dataType:'json',
					success : function(result) {
						layer.close(tips);
						if(result.success){
							parent.$.showMsg(result.message, 6);
							showSureCodeWindow();
						}else{
							parent.$.showMsg(result.message, 6, null, 5000);
							showSureCodeWindow();
						}
					} 
				});
			}
		});
	}
	
	var codeWindow;
	function showSureCodeWindow(){
		var pHtml= '<div style="text-align:left;width:215px;height:70px;">';
		pHtml+='<div style="margin:20px 0 10px 30px;"><span><label>请输入4位验证码:&nbsp;&nbsp;</label><input onkeyup="sureCheckCode(this)" id="code" maxlength="4" style="font-size:22px;margin-left:0px;width:156px;padding-left:3px;height:24px;line-height:30px;" class="vchInput" type="text"/></span></div></div>';
		codeWindow = layer.open({
			title:'验证码确认',
			shade : [ 0.1, '#000' ],
			type:1,
			content:pHtml,
			onSuccess:function(){
				$("#code").mustNumber();
			}
		})
		$("#code").focus();		
	}
	
	function sureCheckCode(_this){
		var code = $(_this).val();
		var tel  = $("#empNo").val();
		if(code.length == 4){
			$.ajax({ 
				type:"GET", 
				url : "<%=path%>/supervisory/asyn/checkAddEmpCode", 
				data:{checkCode: code,telphone: tel},
				dataType:'json',
				success : function(result) {
					if(result.success){
						layer.close(codeWindow);
						saveEmpInfo();
					}else{
						if(result.type == 0000){
							layer.close(codeWindow);
						}
						parent.$.showMsg(result.message, 2);
					}
				} 
	    	});
		}
	}
	
	function saveEmpInfo(){
		var tel  = $("#empNo").val();
		var name = $("#empName").val();
		if($.trim(name) == "" || name == null){
			parent.$.showMsg("请输入员工姓名!", 2);
			return false;
		}
		var re = /^1\d{10}$/;
		if(!re.test(tel)){
			parent.$.showMsg("员工手机号码格式不正确!", 2);
			return false;
		}
		if(!$("#empEmail").isEmail()){
			parent.$.showMsg("邮箱格式不正确!", 2);
			return false;
		}
		var tips = layer.msg("正在创建...", {icon : 16,time : 0, shade: [0.1]});
		var data = $("#empForm").serialize();
		$.post('<%=path%>/supervisory/asyn/addEmpInfo', data, function(result){
			layer.close(tips);
			if(result.success == true){
				var memberId = result.memberId;
				saveRole(memberId);
				parent.$.showMsg(result.msg, 1);
				return true;
			}
			if(result.success == false){
				parent.$.showMsg(result.msg, 2, null, 5000);
				return false;
			}
		});
	}
	
	function closeWindow(){
		window.parent.layer.close(window.parent.addEmp);
	}
</script>
</head>
<body>
<div class="AddcusPop">
<form id="empForm">
<div>
</div>
    <table width="100%" border="0">
      <tr>
        <td width="70">
        	<label >姓名：</label>
        </td>
        <td width="200">
        	<input id="empName" name="name" maxlength="10" type="text" class="txt140" />
        	<span>*</span>
        </td>
        <td width="70">
        	<label >登录账号：</label>
        </td>
        <td>
        	<input id="empNo" name="telphone" maxlength="11" placeholder="请输入员工手机账号" type="text" class="txt140" />
            <span>*</span>
         </td>
      </tr>
      <tr>
        <td>性别：</td>
        <td>
        	<input type="radio" name="sex" id="empSex" value="0" />
            <label class="Inline">男</label>
            <input type="radio" name="sex" id="empSex" value="1" checked="checked" />
            <label class="Inline">女</label></td>
        <td>证件号：</td>
        <td>
        	<input id="empCardNo" name="cardNo" maxlength="18" type="text" class="txt140">
        </td>
      </tr>
      <tr>
        <td>E-mail：</td>
        <td>
        	<input id="empEmail" name="email" type="text" class="txt140" />
        </td>
        <td>QQ：</td>
        <td>
        	<input id="empQQ" name="qq" maxlength="14" type="text" class="txt140" />
        </td>
      </tr>
      <tr>
        <td>部门：</td>
        <td>
        	<select id="empPartId" name="departmentId" class="select_staff"></select>
        </td>
        <td>备注：</td>
        <td>
        	<input id="empDemo" name="demo" maxlength="200" type="text" class="txt140" value="" />
        </td>
      </tr>
      <tr>
        <td>联系地址：</td>
        <td colspan="3">
        	<input id="empAddr" name="addr" type="text" maxlength="50" class="txt420" value="" style="width:415px" />
        </td>
      </tr>
      <tr>
        <td valign="top">角色：</td>
        <td colspan="3">
        <div id="ChooseRole"></div>            
        </td>
      </tr>
    </table>
    <p class="Right_btn" style="padding-top:20px;">
      <input id="saveBtn" style="padding: 0px 0px 0px 0px" type="button" onclick="sendCheckCode()" value="保存" class="bcssbtn"/>
      <input id="closBtn" style="padding: 0px 0px 0px 0px" type="button" onclick="closeWindow()" value="关闭" class="bcssbtn" />
    </p>
</form>
</div>
</body>
</html>