<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.wb.framework.commonUtil.PropertiesReader"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<title>权限管理(主界面)</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script type="text/javascript" src="<%=path%>/script/powerAuth.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/zTree/js/jquery.ztree.core-3.5.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style_mnt.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqueryui/jquery-ui.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqgrid/css/ui.jqgrid.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/zTree/css/zTreeStyle.css" />
<style type="text/css">
	.table-head{padding-right:17px;background-color:#f5f3f3;color:#f5f3f3;}
	.table-body{width:100%; height:300px;overflow-y:scroll;}
	.table-head table,.table-body table{width:100%;}
	.table-body table tr:nth-child(2n+1){background-color:#f2f2f2;}
</style>
<script type="text/javascript">
<%PropertiesReader reader = PropertiesReader.getInstance();%>
<%String url = reader.getValue("/com/wb/config/application", "ACC_ROLER_URL");%>
var ACC_ROLER_URL = '<%=url%>';
var treeObj;
var refCompanyId = '${sessionScope.userInfo.orgId}';
function getCookie(name){ 
	var arr = document.cookie.split("; "); 
	for(var i=0; i<arr.length; i++){ 
	var arr2 = arr[i].split("="); 
	if(arr2[0] == name){ 
	return arr2[1]; 
	} 
	} 
	return ""; 
} 
$(function(){
	initTreeData();
	initMainFramData();
	$("#powerSel").change( function(){
		var roleId;
		var selNodes = treeObj.getSelectedNodes();
		if(selNodes.length > 0){
			roleId = selNodes[0].id;
		}
		initMainFramData(roleId);
	});
})

/**
	加载树数据
*/
function initTreeData(){
	var refCompanyId=getCookie("memberId");
	var isAdmin=getCookie("isAdmin");
	var isAdminFlag="";
	if(isAdmin!='true'){
		isAdminFlag='1';
	}
	$.ajax({
		type : "get",
		timeout : 5000,
        dataType : "jsonp",  
       	jsonp : "callBack",
       	data : {refCompanyId:refCompanyId,isAdmin:isAdmin,isAdminFlag:isAdminFlag},
		url  : ACC_ROLER_URL + "/auth/sysauth/role/get.jspx",
       	success : function(data){
       		if(data.returnCode == 0){
       			//var companyRoler = {"id":"0000", "rid":"0000", "pid":"0000", "open":true, "icon":"/Oemp/plugins/zTree/css/img/diy/1_open.png","roleName":"管理角色","roleCode":"com","refCompanyId":00000};
       			var accountRoler = {"id":"0001", "rid":"0001", "pid":"0001", "open":true, "icon":"/Oemp/plugins/zTree/css/img/diy/1_open.png","roleName":"业务角色","roleCode":"acc","refCompanyId":00000};
       			var result = data.results;
       			//result.push(companyRoler);
       			result.push(accountRoler);
       			initTree(result);
       			treeObj.refresh();
       		}else{
       			$.showMsg(data.returnMSG, 2, null, 5000);
       		}
       	},
       	error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.showMsg("亲!您的网络不给力哦~", 2, null, 5000);
		}
	});
}

/**
	加载所有权限
*/
function initMainFramData(rolerId){
	var powerType = $("#powerSel").val();
	$.ajax({
		type : "get",
		timeout : 5000,
        dataType : "jsonp",  
       	jsonp : "callBack",
       	data : {sysType:powerType},
		url  : ACC_ROLER_URL + "/auth/sysauth/menusLeftJoinfuns/get.jspx",
       	success : function(data){
       		if(data.returnCode == 0){
       			var result = data.results;
       			$("tr[trFlag='addTr']").remove();
       			var parentAttr = [];
       			$.each(result, function(index,item){
       				if(item.menuParentId == 0 && item.functionSeq == null){
       					var tempId = item.menuId;
       					var tempName = item.menuName;
       					var tempObj = new Object();
       					tempObj.id = tempId;
       					tempObj.name = tempName;
       					parentAttr.push(tempObj);
       				}
       			});
       			$.each(result, function(index,item){
       				if(item.menuParentId != 0 || item.functionSeq != null){
	       				addTd(parentAttr, item, item.menuParentId, item.menuId, item.menuName, item.functionId, item.btnName, item.describes);
       				}
       			});
       			if(rolerId){
       				showRolerRole(rolerId);
       			}
       		}else{
       			$.showMsg(data.returnMSG, 2, null, 5000);
       		}
       	},
       	error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.showMsg("亲!您的网络不给力哦~", 2, null, 5000);
		}
	});
}

/**
*	展示角色权限
*/
function showRolerRole(rolerId){
	var powerType = $("#powerSel").val();
	var refCompanyId=getCookie("memberId");
	var tips = layer.msg("请稍后...",{icon : 16,time : 0, shade: [0.1]});
	$.ajax({
		type : "get",
		timeout : 5000,
        dataType : "jsonp",  
       	jsonp : "callBack",
       	data : {roleId : rolerId, sysType : powerType,refCompanyId:refCompanyId},
		url  : ACC_ROLER_URL + "/auth/sysauth/menuRoleCustomer/get.jspx",
       	success : function(data){
       		layer.close(tips);
       		if(data.returnCode == 0){
       			var result = data.results;
       			$.each(result, function(index,item){
       				var funId = item.functionId;
       				$(" input[funId='" + funId + "'][menuId='" + item.menuId + "']").prop("checked", true);
       			})
       		}else{
       			$.showMsg(data.returnMSG, 2, null, 5000);
       		}
       	},
       	error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.showMsg("亲!您的网络不给力哦~", 2, null, 5000);
		}
	});
}

/**
	添加TD
*/
var tempGNId = -1;
var tempMkId = -1;
var tempMenuName = '';
function addTd(parentAttr, item, parentId, menuId, menuName, funId, funName, cz){
	var tr = '<tr trFlag="addTr">'
	if(tempGNId != parentId){
		var parentName = menuName;
		for(var i = 0; i < parentAttr.length; i = i + 1){
			var obj = parentAttr[i];
			if(obj.id == parentId){
				parentName = obj.name;
			}
		}
		tr = tr + '<td width="25%" align="left">' + parentName + '</td>'
	}else if(tempGNId == parentId && tempMenuName != menuName && parentId == 0){
		tr = tr + '<td width="25%" align="left">' + menuName + '</td>';
	}else{
		tr = tr + '<td width="25%" align="left"></td>'
	}
	if(tempMkId != menuId){
		tr = tr + '<td width="25%" align="left"><input type="checkbox" mes="rawBox" menuId="' + menuId + '" onclick="selMenuAll(this,' + menuId + ')" class="checkbox"/>' + $.ckTrim(menuName) + '</td>';
	}else{
		tr = tr + '<td width="25%" align="left"></td>';
	}
	tr = tr + '<td width="30%" align="left">' + $.ckTrim(funName) + '</td>'
	tr = tr + '<td width="20%" align="left"><input type="checkbox" mas="funBox" mes="rawBox" menuId="' + menuId + '" funId="' + funId + '" class="checkbox"/></td>'
   	tr = tr + '</tr>';
	tempMkId = menuId;
   	tempGNId = parentId;
	tempMenuName = menuName;
	$("#mainTable").append(tr);
}

/**
	选择菜单权限
*/
function selMenuAll(_this,menuId){
	if($(_this).prop("checked")){
		$(" input[menuId='" + menuId + "']").prop("checked", true);
	}else{
		$(" input[menuId='" + menuId + "']").prop("checked", false);
	}
	
}

/**
	选择所有权限
*/
function checkAll(_this){
	if($(_this).prop("checked")){
		$(" input[mes='rawBox']").prop("checked", true);
	}else{
		$(" input[mes='rawBox']").prop("checked", false);
	}
}

/**
	初始化科目树
*/
function initTree(data){
	treeObj= $.fn.zTree.init($("#frameworkTree"), {
		data: {
			key: {
				name: "roleName"
            },
            simpleData :{
				enable: true,
				idKey : "rid",
				pIdKey: "pid"
        	}
        },
        callback: {
			onClick: function (event, treeId, treeNode){
				var rolerId = treeNode.id;
				if(rolerId == 0000 || rolerId == 0001){
					return false;
				}
				initMainFramData(rolerId);
			}
		}
	}, data);
}

/**
	保存角色权限
*/
function saveRolerRoles(){
	var notes = treeObj.getSelectedNodes();
	if(notes.length == 0){
		$.showMsg("请选择一个角色节点!",2);
		return;
	}
	var rolerId = notes[0].id;
	var refCompanyId = notes[0].refCompanyId;
	if(rolerId == 0000 || rolerId == 0001){
		$.showMsg("请选择一个有效的角色!",2);
		return;
	}
	var data;
	var checkFun = $(" input[mas='funBox']:checked");
	var tempData = [];
	if(checkFun.length == 0){
		var temp = new Object();
		temp.roleId = rolerId;
		temp.menuId = "";
		temp.functionId = "";
		temp.refCompanyId = refCompanyId;
		tempData.push(temp);
	}else{
		$.each(checkFun, function(index,item){
			var temp = new Object();
			temp.roleId = rolerId;
			temp.menuId = $(item).attr("menuId");
			temp.functionId = $(item).attr("funId");
			temp.refCompanyId = refCompanyId;
			tempData.push(temp);
		});
	}
	data = JSON.stringify(tempData);
	layer.msg("正在保存,请稍后...",{icon : 16, shade: [0.1]}, function(){
		$.showMsg("保存成功!", 1);
	}, 2000);
	var powerType = $("#powerSel").val();

	$.ajax({
		type : "get",
		timeout : 5000,
        dataType : "jsonp",  
       	jsonp : "callBack",
       	data : {dataList : data, sysType : powerType},
       	url  : ACC_ROLER_URL + "/auth/sysauth/menuRoleCustomerMulti/put.jspx",
       	success : function(data){
       		if(data.returnCode == 0){
       			
       		}else{
       			$.showMsg(data.returnMSG, 2, null, 5000);
       		}
       	},
       	error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.showMsg("亲!您的网络不给力哦~", 2, null, 5000);
		}
	});
}


/**
	新增角色窗口打开
*/
var addRolerInfoWindow;
function addRolerInfo_openWindow(){
	$("#addRolerBtn").show();
	$("#updateRolerBtn").hide();
	$("#rolerBtn").val("添加");
	$("#rolerId").val("");
	$("#rolerType").val("");
	$("#rolerName").val("");
	addRolerInfoWindow = layer.open({
		type : 1,
		title : '添加角色信息',
		shade : [ 0.1, '#000' ],
		fix : true,
		maxmin: true,
		shadeClose: true,
		area : ['280px', '207px'],
		content : $("#rolerInfoDiv")
	});
}

/**
	修改角色窗口打开
*/
var updateRolerInfoWindow;
function updateRolerInfo_openWindow(){
	$("#addRolerBtn").hide();
	$("#updateRolerBtn").show();
	$("#rolerId").val("");
	$("#rolerType").val("");
	$("#rolerName").val("");
	var notes = treeObj.getSelectedNodes();
	if(notes.length == 0){
		$.showMsg("请选择一个角色节点!",2);
		return;
	}
	var rolerId   = notes[0].id;
	var rolerType = notes[0].roleType;
	var rolerName = notes[0].roleName;
	if(rolerId == 0000 || rolerId == 0001){
		$.showMsg("该节点不允许修改!",2);
		return;
	}
	$("#rolerId").val(rolerId);
	$("#rolerType").val(rolerType);
	$("#rolerName").val(rolerName);
	updateRolerInfoWindow = layer.open({
		type : 1,
		title : '修改部门信息',
		shade : [ 0.1, '#000' ],
		fix : true,
		maxmin: true,
		shadeClose: true,
		area : ['280px', '207px'],
		content : $("#rolerInfoDiv")
	});
}

/**
	新增角色
*/
function addRolerInfo(){
	var rolerName = $("#rolerName").val();
	var rolerType = $("#rolerType").val();
	if(rolerType == null || rolerType == ""){
		$.showMsg("请选择角色类型!", 2);
		return;
	}
	if(rolerName == null || rolerName == ""){
		$.showMsg("请输入角色名称!", 2);
		return;
	}
	layer.msg("正在保存,请稍后...",{icon : 16,time : 0, shade: [0.1]});
	$.ajax({
		type : "get",
        dataType : "jsonp",  
       	jsonp : "callBack",
       	data : {
			refCompanyId : refCompanyId,
			roleName : encodeURIComponent(encodeURIComponent(rolerName)),
			roleType : rolerType
		},
		url  : ACC_ROLER_URL + "/auth/sysauth/role/put.jspx",
       	success : function(data){
       		if(data.returnCode == 0){
       			initTreeData();
       			layer.close(addRolerInfoWindow);
       			$.showMsg("保存成功!", 1);
       		}else{
       			$.showMsg(data.returnMSG, 2, null, 5000);
       		}
       	}
    });
}

/**
	修改角色
*/
function updateRolerInfo(){
	var notes = treeObj.getSelectedNodes();
	var id      = notes[0].id;
	var rolerType = $("#rolerType").val();
	var rolerName = $("#rolerName").val();
	if(rolerType == null || rolerType == ""){
		$.showMsg("请选择角色类型!", 2);
		return;
	}
	if(rolerName == null || rolerName == ""){
		$.showMsg("请输入角色名称!", 2);
		return;
	}
	layer.msg("正在修改,请稍后...",{icon : 16,time : 0, shade: [0.1]});
	$.ajax({
		type : "get",
        dataType : "jsonp",  
       	jsonp : "callBack",
       	data : {
       		id : id,
			refCompanyId : refCompanyId,
			roleName : rolerName,
			roleType : rolerType
		},
		url  : ACC_ROLER_URL + "/auth/sysauth/role/put.jspx",
       	success : function(data){
       		if(data.returnCode == 0){
       			initTreeData();
       			layer.close(updateRolerInfoWindow);
       			$.showMsg("修改成功!", 1);
       		}else{
       			$.showMsg(data.returnMSG, 2, null, 5000);
       		}
       	}
    });
}

/**
	删除角色
*/
function delRolerInfo(){
	var notes = treeObj.getSelectedNodes();
	if(notes.length == 0){
		$.showMsg("请选择一个角色节点!",2);
		return;
	}
	var rolerId   = notes[0].id;
	if(rolerId == 0000 || rolerId == 0001){
		$.showMsg("该节点不允许删除!",2);
		return;
	}
	layer.confirm("确认删除?", {icon: 3, title:'提示'}, function(index){
		layer.msg("正在删除,请稍后...",{icon : 16,time : 0, shade: [0.1]});
		$.ajax({
			type : "get",
	        dataType : "jsonp",  
	       	jsonp : "callBack",
	       	data : {
				id : rolerId
			},
			url  : ACC_ROLER_URL + "/auth/sysauth/role/delete.jspx",
	       	success : function(data){
	       		if(data.returnCode == 0){
	       			initTreeData();
	       			$.showMsg("删除成功!", 1);
	       		}else{
       				$.showMsg(data.returnMSG, 2, null, 5000);
       			}
	       	}
	    });
    });
}
</script>
</head>
<body>
<div class="box_cust">
    <div class="customer_left" id="customer_left">
        <h2 style="color: black;">角色定义</h2>
        <h3>
        	<a class="a_add_01"    style="display: none;" id="power_add" onclick="addRolerInfo_openWindow()" >新增</a>
        	<a class="a_delete_01" style="display: none;" id="power_del" onclick="delRolerInfo()" >删除</a>
        	<a class="a_edit_01"   style="display: none;" id="power_up"  onclick="updateRolerInfo_openWindow()">修改</a>
        </h3>
        <div class="div_tree">
        	<div id="frameworkTree" class="ztree" style="height: 230px;margin-bottom: 5px;margin-left: 15px;"></div>
        </div>
    </div>
    <div class="customer_right">
        <div style="overflow-y:auto;height:440px;">
        	<div class="table-head">
	            <table id="tableThread" class="table_workbench" width="100%" height="100%" cellpadding="0" cellspacing="0"  rules="all">
	                <thead>
		                <tr>			
		                    <th align="left" colspan="4" style="color: black;">
		                    	权限项:
		                    	<select id="powerSel" style="border: 1px solid #C3C3C3;">
		                    		<option value="cwphone">手机端</option>
		                    		<option value="cwjxc">进销存</option>
		                    		<option value="cwjk" selected="selected">管理平台</option>
		                    		<option value="cwzz">财务总账</option>
		                    	</select>
		                    </th>
		                </tr>	
		                <tr>
		                    <td width="25%" align="left" style="background-color:#FAFAFA;">模块</td>
		                    <td width="25%" align="left" style="background-color:#FAFAFA;">功能列表</td>
		                    <td width="30%" align="left" style="background-color:#FAFAFA;">操作</td>
		                    <td width="20%" align="left" style="background-color:#FAFAFA;"><input type="checkbox" onclick="checkAll(this)" class="checkbox"/>授权</td>
		                </tr>
	                </thead>
	            </table>
            </div>
            <div class="table-body" style="height:84%">
	            <table id="mainTable" class="table_workbench" width="100%" height="100%" cellpadding="0" cellspacing="0"  rules="all">
	            </table>
            </div>
        </div>
        <div align="center" style="padding-top: 10px"><input id="power_save" type="button" class="bcssbtn" class="bcssbtn" style="padding: 4px 8px 4px 8px;display: none;" onclick="saveRolerRoles()" value=" 保存 "/></div>
    </div>
	<div id="rolerInfoDiv" style="display: none;">
		<div style="margin:20px 0 10px 0px;text-align:center;">
			<h3 style="display: inline-block;">角色类型:</h3>
			<select id="rolerType" name="rolerType"  style="margin-left:0px;width:160px;padding-left:3px;height:24px;line-height:30px;" class="vchInput">
				<!-- <option value="0000">管理角色</option> -->
				<option value="0001">业务角色</option>
			</select><br/>
		</div>
		<div style="margin:20px 0 10px 0px;text-align:center;">
			<h3 style="display: inline-block;">角色名称:</h3>
			<input id="rolerName" name="rolerName"  type="text" style="margin-left:0px;width:156px;padding-left:3px;height:24px;line-height:30px;" class="vchInput"/><br/>
		</div>
		<div style="margin-right:15px;margin-top:20px; text-align:right;"><input id="addRolerBtn" class="bcssbtn" style="padding: 4px 8px 4px 8px" type="button" onclick="addRolerInfo();" value="添加"  /><input id="updateRolerBtn" class="bcssbtn" style="padding: 4px 8px 4px 8px" type="button" onclick="updateRolerInfo()" value="修改"  /></div>
	</div>
</div>
<script type="text/javascript">
	function myfun()
	{
		var isAdmin=getCookie("isAdmin");
		if(isAdmin=='false'){
			$("#power_add").hide();
			$("#power_del").hide();
			$("#power_up").hide();
			$("#power_save").hide();
		}else{
			$("#power_add").show();
			$("#power_del").show();
			$("#power_up").show();
			$("#power_save").show();
		}
	}
	/*用window.onload调用myfun()*/
	window.onload=myfun;//不要括号
</script>
</body>
</html>
