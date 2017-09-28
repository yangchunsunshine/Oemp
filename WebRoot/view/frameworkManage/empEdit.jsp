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
<title>编辑员工界面</title>
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
		var sex = '${empInfo.sex}';
		if(sex == 0){
			$("#empSexM").prop("checked","checked");
		}
		if(sex == 1){
			$("#empSexW").prop("checked","checked");
		}
		var cancleFlag = '${empInfo.enable}';
		if(cancleFlag == 0){
			$("#empCancleFlag").prop("checked","checked");
		}
		$("#empPart").ckCombox({
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
			},
			onSuccess : function(){
				var partId = '${empInfo.departmentId}';
				$("#empPart").val(partId);
			}
		});
		$("#empQQ").mustNumber();
		getOrgRole();
	})

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
	       		getMemberRole();
	       	},
	       	error : function(XMLHttpRequest, textStatus, errorThrown) {
				parent.$.showMsg("亲!您的网络不给力哦~", 2, null, 5000);
			}
		});
	}

	function getMemberRole(){
		var ACC_ROLER_URL = '<%=url%>';
		var memberId = $("#empId").val();
		var url = ACC_ROLER_URL+"/auth/sysauth/memberRole/get.jspx";
		$.ajax({
			type : "get",
			timeout : 5000,
	        dataType : "jsonp",  
	       	jsonp : "callBack",
	       	data : {"memberid":memberId},
			url  : url,
	       	success : function(data){
	       		var mess = data.results;
	       		for(var i=0;i<mess.length;i++){
	       			$("#editRole"+mess[i].roleid).prop("checked",true);
	       		}
	       	},
	       	error : function(XMLHttpRequest, textStatus, errorThrown) {
				parent.$.showMsg("亲!您的网络不给力哦~", 2, null, 5000);
			}
		});
	}
	
	function saveRole(){
		var ACC_ROLER_URL = '<%=url%>';
		var memberId = $("#empId").val();
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
	       	
	       	},
	       	error : function(XMLHttpRequest, textStatus, errorThrown) {
				parent.$.showMsg("亲!您的网络不给力哦~", 2, null, 5000);
			}
		});
	}
	
	function editEmpInfo(){
		var name = $("#empName").val();
		if($.trim(name) == "" || name == null){
			parent.$.showMsg("请输入员工姓名!", 2);
			return false;
		}
		if(!$("#empEmail").isEmail()){
			parent.$.showMsg("邮箱格式不正确!", 2);
			return false;
		}
		
		var tips = layer.msg("正在保存...", {icon : 16,time : 0, shade: [0.1]});
		saveRole();
		var data = $("#empForm").serialize();
		$.post('<%=path%>/supervisory/asyn/editEmpInfo', data, function(result){
			layer.close(tips);
			if(result.success == true){
				parent.$.showMsg(result.msg, 1);
				parent.searchfb();
				closeWindow();
				return true;
			}
			if(result.success == false){
				parent.$.showMsg(result.msg, 2, null, 5000);
				return false;
			}
		});
	}
	
	function closeWindow(){
		window.parent.layer.close(window.parent.editEmpWindow);
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
        	<input id="empId" name="id" type="hidden" value="${empInfo.id}" />
        	<input id="empName" name="name" maxlength="10" type="text" class="txt140" value="${empInfo.name}" />
        	<span>*</span>
        </td>
        <td width="70">
        	<label >登录账号：</label>
        </td>
        <td>
        	<input id="empNo" name="telphone" maxlength="11" disabled="disabled" placeholder="请输入员工手机账号" type="text" class="txt140" value="${empInfo.telphone}"  />
            <span>*</span>
         </td>
      </tr>
      <tr>
        <td>性别：</td>
        <td>
        	<input type="radio" name="sex" id="empSexM" value="0" />
            <label class="Inline">男</label>
            <input type="radio" name="sex" id="empSexW" value="1" />
            <label class="Inline">女</label></td>
        <td>证件号：</td>
        <td>
        	<input id="empCardNo" name="cardNo" maxlength="18" type="text" class="txt140" value="${empInfo.cardNo}">
        </td>
      </tr>
      <tr>
        <td>E-mail：</td>
        <td>
        	<input id="empEmail" name="email" type="text" class="txt140" value="${empInfo.email}" />
        </td>
        <td>QQ：</td>
        <td>
        	<input id="empQQ" name="qq" maxlength="14" type="text" class="txt140" value="${empInfo.qq}"/>
        </td>
      </tr>
      <tr>
        <td>部门：</td>
        <td>
        	<select class="select_staff" id="empPart" name="departmentId" style="width: 143px"></select>
        </td>
        <td>备注：</td>
        <td>
        	<input id="empDemo" name="demo" maxlength="200" type="text" value="${empInfo.demo}" class="txt140" value="" />
        </td>
      </tr>
      <tr>
        <td>联系地址：</td>
        <td>
        	<input id="empAddr" name="addr" type="text" value="${empInfo.addr}" maxlength="50" class="txt420" value="" style="width:140px" />
        </td>
        <td>停用账号：</td>
        <td>
        	<input id="empCancleFlag" name="cancleFlag" type="checkbox" />
      </tr>
      <tr>
        <td valign="top">角色：</td>
        <td colspan="3">
        <div id="ChooseRole"></div>            
        </td>
      </tr>
    </table>
    <p class="Right_btn" style="padding-top:20px;">
      <input id="saveBtn" style="padding: 0px 0px 0px 0px" type="button" onclick="editEmpInfo()" value="保存" class="bcssbtn"/>
      <input id="closBtn" style="padding: 0px 0px 0px 0px" type="button" onclick="closeWindow()" value="关闭" class="bcssbtn" />
    </p>
</form>
</div>
</body>
</html>