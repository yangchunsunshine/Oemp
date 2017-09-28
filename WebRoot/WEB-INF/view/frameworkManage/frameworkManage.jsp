<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="com.wb.framework.commonUtil.PropertiesReader"%>
<%PropertiesReader reader = PropertiesReader.getInstance();%>
<%String url = reader.getValue("/com/wb/config/application", "ACC_ROLER_URL");%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<title>组织架构管理(主界面)</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script type="text/javascript" src="<%=path%>/script/powerAuth.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/zTree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/select2.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/i18n/zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/select2/css/select2.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/style/css/style.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/style/css/style_mnt.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqueryui/jquery-ui.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqgrid/css/ui.jqgrid.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/zTree/css/zTreeStyle.css" />
<script type="text/javascript">
var treeObj;
var thisGrid;
var depId = '${userInfo.departmentId}';
var memberId =  '${sessionScope.userInfo.empInfo.id}';
var isAdmin = $.getCookie("isAdmin");
$(function(){
	$("#partNum").mustNumber();
	$("#updatePartNum").mustNumber();
	$("#clerkTel").mustNumber();
	$("select").select2({
		language: "zh-CN"
	});
	thisGrid = $("#clerkManagementTable").jqGrid({
		url: '<%=path%>/supervisory/asyn/getClerkMngList?'+$("#clerkManagementForm").serialize()+ '&partId=',
		datatype: 'json',
		colNames: ['id','关联ID','员工姓名','员工电话','密码修改','所属部门','申请时间','申请状态','操作'],
		colModel: [
            {name:'id',index:'associateId',sortable:false,hidden:true,align:"right"},
			{name:'associateId',index:'associateId',sortable:false,hidden:true,align:"right"},
			{name:'clerkName',index:'clerkName',width:90,sortable:false,align:"left"},
			{name:'clerkTel',index:'clerkTel',width:90,sortable:false,align:"left"},
			{name:'queryState',index:'queryState',formatter:formatPwd,width:120,sortable:false,align:"center"},
			{name:'partName',index:'partName',width:100,sortable:false,align:"left"},
			{name:'queryTime',index:'queryTime',width:100,sortable:false,align:"center"},
			{name:'queryStateName',index:'queryStateName',width:60,sortable:false,align:"center"},
			{name:'queryState',index:'queryState',formatter:formatOpts,width:60,sortable:false,align:"center"}
		],
		rowNum: 50,
		rowList: [50,100,200],
		pager: '#gridPager',
		sortname: 'associateId',
		sortorder: "asc",
		rownumbers: true,
		rownumWidth: 50,
		caption: '员工管理列表',
		mtype: "GET",
		width:$(window).width()-270,
        height:$(window).height()-168,
		viewrecords: true,
		footerrow:true,
		loadComplete: function (data) {
	   	    $(this).footerData("set",{"clerkName":"合计","clerkTel":"共"+(data.records==null?0:data.records)+"条人员监控信息"});
       		authView();
       	},
		hidegrid: false
	});
	thisGrid.jqGrid('navGrid',"#gridPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
	$("#jqgh_clerkManagementTable_rn").prepend("序号");
	treeObj = $.fn.zTree.init($("#frameworkTree"), {
		data: {
			key: {
				name: "partName"
            },
            simpleData :{
				enable: true,
				idKey: "id",
				pIdKey: "pId",
				rootPId: 0
	        }
        },
        async: {
	        enable: true,
	        type: "get",
	       	url:"<%=path%>/supervisory/asyn/findMntFrameWork"
        },
	   	callback: {
	       	onClick: function(event, treeId, treeNode) {
				var partId = treeNode.id;
				thisGrid.jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getClerkMngList?'+$("#clerkManagementForm").serialize() + '&partId=' + partId, page : 1}).trigger("reloadGrid");
			},
			onAsyncSuccess: function(event, treeId, treeNode, msg) {
				var node =treeObj.getNodes();
			    treeObj.selectNode(node[0],false);
			    //searchfb();
			}
		}
	});
	
	$("#clerkName").ckCombox({
		url : '<%=path%>/supervisory/asyn/getEmpByDep',
		requestType : "POST",
		data: {'depSelect' : depId},
		dataType : "json",
		width : "125px",
		height : "20px",
		formatter : {
			root: "empList",
			id : "empName",
			value : "empName"
		},
		onSuccess: function(){
			$("#clerkName").select2({
				language: "zh-CN"
			});
		}
	});
});

//格式化密码修改
function formatPwd(cellvalue,options,rowObject) {
	var val="";
	if(cellvalue==<spring:message code="CLERK_MANAGE_STATE_ACCEPT"/>){
		if(isAdmin=='true'){
			val='<input id="pwd_'+rowObject.associateId+'" maxlength="16" type="text" class="dfinput_fb" value="" style="width:100px;height:10px;line-height:10px;"/><input class="acssbtn" id="editEmpPwd" onclick="submitPwd('+rowObject.associateId+');" type="button" style="text-align:center;padding:0px;margin-left:9px;display: none;" value="提交"/>';
			
		}else{
			if(memberId==rowObject.id){
				val='<input id="pwd_'+rowObject.associateId+'" maxlength="16" type="text" class="dfinput_fb" value="" style="width:100px;height:10px;line-height:10px;"/><input class="acssbtn" id="editEmpPwd" onclick="submitPwd('+rowObject.associateId+');" type="button" style="text-align:center;padding:0px;margin-left:9px;display: none;" value="提交"/>';
			}
		}
	}
	return val;
}

//格式化操作项
function formatOpts(cellvalue,options,rowObject) {
	if(cellvalue==<spring:message code="CLERK_MANAGE_STATE_ACCEPT"/>){
		var edit = '';
		if(isAdmin=='true'){
			edit = '<a class="button_recover" style="display: none;" id="editEmpInfo" title="编辑员工信息" type="button" href="javascript:void(0);" onclick="editEmp(' +rowObject.associateId+ ')">编辑</a>&nbsp;&nbsp;<a class="button_recover" style="display: none;" id="editEmpInfo" title="删除员工" type="button" href="javascript:void(0);" onclick="delEmp(' +rowObject.id+ ')">删除</a>';
		}else{
			if(memberId==rowObject.id){
				edit = '<a class="button_recover" style="display: none;" id="editEmpInfo" title="编辑员工信息" type="button" href="javascript:void(0);" onclick="editEmp(' +rowObject.associateId+ ')">编辑</a>&nbsp;&nbsp;<a class="button_recover" style="display: none;" id="editEmpInfo" title="删除员工" type="button" href="javascript:void(0);" onclick="delEmp(' +rowObject.id+ ')">删除</a>';
			}
		}
		return edit;
		/* return '<a class="button_recover" title="删除员工" type="button" href="javascript:void(0);" onclick="queryDealing('+rowObject.associateId+",'"+'<spring:message code="CLERK_MANAGE_STATE_ACCEPT"/>'+"'"+",'"+'确定删除员工:[ '+rowObject.clerkName+' ]吗？'+"'"+');">删除员工</a>'; */
	}
	if(cellvalue==<spring:message code="CLERK_MANAGE_STATE_DEALING"/>){
		var btn ='';
		if(isAdmin=='true'){
			btn = '<a class="button_recover" title="取消申请" type="button" href="javascript:void(0);" onclick="queryDealing('+rowObject.associateId+",'"+'<spring:message code="CLERK_MANAGE_STATE_DEALING"/>'+"'"+",'"+'确定取消对员工:[ '+rowObject.clerkName+' ]的监控请求吗？'+"'"+');">取消申请</a>';
		}else{
			if(memberId==rowObject.id){
				btn = '<a class="button_recover" title="取消申请" type="button" href="javascript:void(0);" onclick="queryDealing('+rowObject.associateId+",'"+'<spring:message code="CLERK_MANAGE_STATE_DEALING"/>'+"'"+",'"+'确定取消对员工:[ '+rowObject.clerkName+' ]的监控请求吗？'+"'"+');">取消申请</a>';
			}
		}
		return btn ;
	}
	if(cellvalue==<spring:message code="CLERK_MANAGE_STATE_REFUSED"/>){
		var btn = '';
		if(isAdmin=='true'){
			btn = '<a class="button_recover" title="删除申请" type="button" href="javascript:void(0);" onclick="queryDealing('+rowObject.associateId+",'"+'<spring:message code="CLERK_MANAGE_STATE_REFUSED"/>'+"'"+",'"+'确定删除被拒绝的对员工:[ '+rowObject.clerkName+' ]的监控请求吗？'+"'"+');">删除申请</a>';
		}else{
			if(memberId==rowObject.id){
				btn = '<a class="button_recover" title="删除申请" type="button" href="javascript:void(0);" onclick="queryDealing('+rowObject.associateId+",'"+'<spring:message code="CLERK_MANAGE_STATE_REFUSED"/>'+"'"+",'"+'确定删除被拒绝的对员工:[ '+rowObject.clerkName+' ]的监控请求吗？'+"'"+');">删除申请</a>';
			}
		}
		return btn;
	} 
}

var editEmpWindow;
function editEmp(mmuId){
    editEmpWindow = layer.open({
		type : 2,
		title : '编辑员工信息',
		shade : [ 0.1, '#000' ],
		fix : true,
		maxmin: true,
		shadeClose: true,
		area : ['510px', '375px'],
		content : '<%=path%>/supervisory/forward/gotoEditEmp?mmuId=' + mmuId,
		success : function(){
			
		}
	});
}

//修改密码
function submitPwd(pwdId){
	if($("#pwd_"+pwdId).val() == null || $.trim($("#pwd_"+pwdId).val()) == ""){
		$.showMsg("密码不能为空!",2);
		return;
	}
	var reg=/^[a-zA-Z0-9]\w{0,16}$/;
    if(!reg.test($("#pwd_"+pwdId).val())){    
        $.showMsg("密码格式不正确!",2);
        return;
    }
	if($("#pwd_"+pwdId).val().length>16){
		$.showMsg("密码长度不能大于16个字符!",2);
		return;
	}
	layer.confirm("确定修改密码为:[ " + $("#pwd_"+pwdId).val() + " ]吗?", {icon: 3, title:'提示信息'}, function(){
		var tips = layer.msg("正在修改...", {icon : 16,time : 0, shade: [0.1]});
		$.ajax({
			type:'post',
			url:'<%=path%>/supervisory/asyn/clerkPwdModify',
			data:{pwdId : pwdId,pwd : $("#pwd_"+pwdId).val()},
			dataType: "json",
			success: function(result) {
				layer.closeAll();
				if(result.success){
					$.showMsg(result.message, 6);
				}else{
					$.showMsg(result.message, 5);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$.showMsg("亲!您的网络不给力哦~", 2);
			}
		});
	});
}

//处理加入员工状态
function queryDealing(id,state,stateTip){
	layer.confirm(stateTip,{icon: 3, title:'提示信息'},function(){
		var tips = layer.msg("正在处理...",{icon : 16,time : 0, shade: [0.1]});
		$.ajax({
			type:'post',
			url:'<%=path%>/supervisory/asyn/queryDealingForClerk',
			data:{id:id,state:parseInt(state)},
			dataType: "json",
			success: function(result) {
				layer.closeAll();
				if(result.success){
					$.showMsg(result.message, 6);
					$("#clerkManagementTable").jqGrid().trigger("reloadGrid");
				}else{
					$.showMsg(result.message, 6);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$.showMsg('<spring:message code="tip_text_fail"/>', 5);
			}
		});
	});
}

//引入已注册员工
function addExployer(){
	var pHtml= '<div style="text-align:left;width:270px;height:130px;"><div>';
		pHtml+='<div style="margin:20px 0 10px 30px;"><span><label>员工姓名:&nbsp;&nbsp;</label><input id="eName" maxlength="10" style="margin-left:0px;width:156px;padding-left:3px;height:24px;line-height:30px;" class="vchInput" type="text"/></span></div>';
		pHtml+='<div style="margin: 0px 0 10px 30px;"><span><label>员工电话:&nbsp;&nbsp;</label><input id="eTelp" maxlength="11" style="margin-left:0px;width:156px;padding-left:3px;height:24px;line-height:30px;" class="vchInput" type="text"/></span></div>';
		pHtml+='<div style="margin: 0px 0 10px 30px;"><span><label>部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门:&nbsp;&nbsp;</label><select id="ePartId" class="select_staff"></select></span></div>';
		pHtml+='</div><div style="margin-right:10px;margin-top:20px;text-align:right;"><input id="addExploer" class="employer bcssbtn" style="padding: 4px 8px 4px 8px" onclick="searchEmployer();" type="button" style="text-align:center;" value="引入员工"/></div></div>';
	layer.open({
		type: 1,
		shade : [ 0.1, '#000' ],
		fix : true,
		maxmin: true,
		shadeClose: true,
		title: '引入已注册员工',
		area: ['280px', '235px'],
		content :pHtml,
		success : function(){
			$("#eTelp").mustNumber();
			$("#ePartId").ckCombox({
				url : '<%=path%>/supervisory/asyn/findMntFrameWork',
				requestType : "GET",
				data: {'ifHasEmp' : 'no'},
				dataType : "json",
				width : "162px",
				height : "24px",
				defaultSel : false,
				formatter : {
					id : "id",
					value : "partName"
				}
			});
		}
	});
}

//单击检索
function searchEmployer(){
	if($("#eName").val()==null||$("#eName").val()==""){
		$.showMsg("员工姓名不能为空!",2);
		return;
	}
	if($("#eTelp").val()==null||$("#eTelp").val()==""){
		$.showMsg("员工电话不能为空!",2);
		return;
	}
	var tips = layer.msg("正在处理...",{icon : 16,time : 0, shade: [0.1]});
	$.ajax({
		type:'post',
		url:'<%=path%>/supervisory/asyn/addClerkFormonitor',
		data:{qname:$("#eName").val(),qtel:$("#eTelp").val(),qPartName:$("#ePartId").find("option:selected").text(),qPartId:$("#ePartId").val()},
		success: function(data) {
			layer.close(tips);
			var result=eval("("+data+")");
			if(result.success){
				$.showMsg(result.message, 6);
				$("#errorTipeForSearch").html("");
				$("#clerkManagementTable").jqGrid().trigger("reloadGrid");
				$("#eName").val("");
				$("#eTelp").val("");
			}else{
				$.showMsg(result.message, 5);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.showMsg("亲!您的网络不给力哦~", 5);
		}
	});
}

//查询
function searchfb(){
	var selectNodes = treeObj.getSelectedNodes();
	var partId;
	if(selectNodes.length > 0){
		partId= selectNodes[0].id;
	}else{
		partId = 0;
	}
	thisGrid.jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getClerkMngList?'+$("#clerkManagementForm").serialize() + '&partId=' + partId, page : 1}).trigger("reloadGrid");
}

var addPartLayer;
function addPartInfo_openWindow(){
	$("#partNum").val("");
	$("#partName").val("");
	var notes = treeObj.getSelectedNodes();
	if(notes.length == 0){
		$.showMsg("请选择一个部门节点!",2);
		return;
	}
	addPartLayer = layer.open({
		type : 1,
		title : '添加部门信息',
		shade : [ 0.1, '#000' ],
		fix : true,
		maxmin: true,
		shadeClose: true,
		area : ['280px', '207px'],
		content : $("#partInfoDiv")
	});
}

var updatePartLayer;
function updatePartInfo_openWindow(){
	$("#updatePartNum").val("");
	$("#updatePartName").val("");
	var notes = treeObj.getSelectedNodes();
	if(notes.length == 0){
		$.showMsg("请选择一个部门节点!",2);
		return;
	}
	var id = notes[0].id;
	var partNum = notes[0].partNum;
	var partName = notes[0].partName;
	if(id == 0){
		$.showMsg("公司节点不允许修改!",2);
		return;
	}
	$("#updatePartNum").val(partNum);
	$("#updatePartName").val(partName);
	updatePartLayer = layer.open({
		type : 1,
		title : '修改部门信息',
		shade : [ 0.1, '#000' ],
		fix : true,
		maxmin: true,
		shadeClose: true,
		area : ['280px', '207px'],
		content : $("#updatePartInfoDiv")
	});
}

function addPartInfo(){
	var notes = treeObj.getSelectedNodes();
	var id = notes[0].id;
	var idPath = notes[0].idPath;
	var level = notes[0].level;
	var rootId = notes[0].rootId;
	var partNum = $("#partNum").val();
	var partName = $("#partName").val();
	if(partNum==null||partNum==""){
		$.showMsg("请输入部门编号!",2);
		return;
	}
	if(partName==null||partName==""){
		$.showMsg("请输入部门名称!",2);
		return;
	}
	if(level > 5){
		$.showMsg("部门级次太多啦!",2);
		return;
	}
	layer.msg("正在创建,请稍后...",{icon : 16,time : 0, shade: [0.1]});
	$.post('<%=path%>/supervisory/asyn/addPartInfo',
	{
		'id': id,
		'idPath': idPath,
		'level': level,
		'rootId': rootId,
		'partNum': partNum,
		'partName': partName
	},
	function(result){
		if(result.code==1){
			$.showMsg("创建成功!",1);
			treeObj.reAsyncChildNodes(null, "refresh");
			$("#partNum").val("");
			$("#partName").val("");
			window.layer.close(window.addPartLayer);
		}else if(result.code==2){
			$.showMsg("创建失败,系统出错!",2);
		}else if(result.code==3){
			$.showMsg("部门编号已存在!",2);
		}else if(result.code==4){
			$.showMsg("部门名称已存在!",2);
		}
	});
}
	
function updatePartInfo(){
	var notes = treeObj.getSelectedNodes();
	var id = notes[0].id;
	var rootId = notes[0].rootId;
	var partNum = $("#updatePartNum").val();
	var partName = $("#updatePartName").val();
	if(partNum==null||partNum==""){
		$.showMsg("请输入部门编号!", 2);
		return;
	}
	if(partName==null||partName==""){
		$.showMsg("请输入部门名称!", 2);
		return;
	}
	layer.msg("正在修改,请稍后...",{icon : 16,time : 0, shade: [0.1]});
	$.post('<%=path%>/supervisory/asyn/updatePartInfo',
	{
		'id': id,
		'rootId':rootId,
		'partNum': partNum,
		'partName': partName
	},
	function(result){
		if(result.code==1){
			$.showMsg("修改成功!",1);
			treeObj.reAsyncChildNodes(null, "refresh");
			$("#updatePartNum").val("");
			$("#updatePartName").val("");
			window.layer.close(window.updatePartLayer);
		}else if(result.code==2){
			$.showMsg("修改失败,系统出错!",2);
		}else if(result.code==3){
			$.showMsg("部门编号已存在!",2);
		}else if(result.code==4){
			$.showMsg("部门名称已存在!",2);
		}
	});
}

function deletePartInfo(){
	var notes = treeObj.getSelectedNodes();
	if(notes.length == 0){
		$.showMsg("请选择一个部门节点!",2);
		return;
	}
	var id = notes[0].id;
	var idPath = notes[0].idPath;
	if(id==0){
		$.showMsg("公司节点不允许删除!",2);
		return;
	}
	layer.confirm("确认删除?", {icon: 3, title:'提示'}, function(index){
	layer.msg("正在删除,请稍后...",{icon : 16,time : 0, shade: [0.1]});
	$.post('<%=path%>/supervisory/asyn/deletePartInfo',
		{
			'id': id,
			'idPath': idPath
		},
		function(result){
			if(result.code==1){
				$.showMsg("删除成功!",1);
				treeObj.reAsyncChildNodes(null, "refresh");
			}else if(result.code==2){
				$.showMsg("删除失败,系统出错!",2);
			}else if(result.code==3){
				$.showMsg("该部门下有子部门,不能删除!",2);
			}else if(result.code==4){
				$.showMsg("该部门下有员工,不能删除!",2);
			}
		});
	layer.close(index);
	});
}

var addEmp
function addEmpFuc(){
    addEmp = layer.open({
		type : 2,
		title : '新增员工   (<strong>*</strong>为必填项 初始密码：123456)',
		shade : [ 0.1, '#000' ],
		fix : true,
		maxmin: true,
		shadeClose: true,
		area : ['510px', '375px'],
		content : '<%=path%>/supervisory/forward/gotoAddEmp'
	});
}

function autoSub(obj){
    if(/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/.test(obj.value)){
    	searchfb();
    }
    if(obj.value==""){
    	searchfb();
    }
}

function showEmp(){
	var ifCheck = $("#showEmp").attr("checked");
}
function delEmp(empId){
	layer.confirm("删除后该员工将失去所有权限!",{icon: 3},function(){
		var tips = layer.msg("正在删除...", {icon : 16,time : 0, shade: [0.1]});
		var ACC_ROLER_URL = '<%=url%>';
		var memberId = empId;
		var url = ACC_ROLER_URL+"/auth/sysauth/memberRoleMulti/put.jspx";
		var roleList = [];
		var roleObj = new Object();
		roleObj.memberid = memberId;
		roleObj.roleid ="";
		roleList.push(roleObj);
		var dataList = JSON.stringify(roleList);
		$.ajax({
			type : "get",
			timeout : 5000,
	        dataType : "jsonp",  
	       	jsonp : "callBack",
	       	data : {"dataList":dataList},
			url  : url,
	       	success : function(data){
	       		$.post('<%=path%>/supervisory/asyn/delEmpInfo', {empId: empId}, function(result){
					layer.close(tips);
					if(result.success == true){
						$.showMsg(result.msg,1);
						searchfb();
						return true;
					}
					if(result.success == false){
						$.showMsg(result.msg, 2, null, 5000);
						return false;
					}
				});
	       	},
	       	error : function(XMLHttpRequest, textStatus, errorThrown) {
				$.showMsg("亲!您的网络不给力哦~", 2, null, 5000);
			}
		});
	});
}
</script>
</head>
<body>
<div id="mainDiv" style="padding-top: 10px">
	<div class="customer_left">
        <h2 style="color: black;">组织架构</h2>
        <h3>
        	<a class="a_add_01"    id="frameWork_add" style="display: none;" onclick="addPartInfo_openWindow()" >新增</a>
        	<a class="a_delete_01" id="frameWork_del" style="display: none;" onclick="deletePartInfo()" >删除</a>
        	<a class="a_edit_01"   id="frameWork_up"  style="display: none;" onclick="updatePartInfo_openWindow()">修改</a>
        </h3>
        <div class="div_tree">
        	<div id="frameworkTree" class="ztree" style="height: 240px;margin-bottom: 5px;margin-left: 15px;"></div>
        </div>
    </div>
	<div id="orgPageRecover" style="float: right;padding-right: 20px;" title="员工信息">
		<form id="clerkManagementForm" method="post">
			<div style="margin-bottom:15px;">
				<span>
					<b style="font-size: 15px">员工姓名:&nbsp;</b><select id="clerkName" name="clerkName" style="width:80px;height:24px;"></select>&emsp;
					<b style="font-size: 15px">员工电话:&nbsp;</b><input  id="clerkTel" name="clerkTel" onkeyup="autoSub(this);" maxlength="11" type="text" class="dfinput_fb" value="${requestScope.condition}" style="width:100px;height:24px;"/>&emsp;
					<b style="font-size: 15px">请求状态:&nbsp;</b>
					<select name="clerkState" style="width:80px;height:24px;font-size:14px;border:1px #ccc solid;">
						<option value="<spring:message code="STATE_ALL_INCLUDE"/>"><spring:message code="STATE_ALL_INCLUDE_NAME"/></option>
						<option value="<spring:message code="CLERK_MANAGE_STATE_DEALING"/>"><spring:message code="CLERK_MANAGE_STATE_DEALING_NAME"/></option>
						<option value="<spring:message code="CLERK_MANAGE_STATE_ACCEPT"/>"><spring:message code="CLERK_MANAGE_STATE_ACCEPT_NAME"/></option>
						<option value="<spring:message code="CLERK_MANAGE_STATE_REFUSED"/>"><spring:message code="CLERK_MANAGE_STATE_REFUSED_NAME"/></option>
					</select>&emsp;
					<input class="bcssbtn" type="button" onclick="searchfb()" value="查询" style="padding: 2px 6px 4px 6px"/>&nbsp;&nbsp;
					<input class="abtn" id="addNewEmp" type="button" onclick="addEmpFuc()" value="添加员工" style="padding: 2px 6px 4px 6px"/>&nbsp;&nbsp;
					<input class="abtn" id="addHasEmp" type="button" onclick="addExployer()" value="引入已注册员工" style="padding: 2px 6px 4px 6px"/>
					<!-- <input id="showEmp" align="right" type="checkbox" onclick="showEmp()"/>显示下级 -->
				</span>
			</div>
		</form>
		<table id="clerkManagementTable"></table>
		<div id="gridPager"></div>
	</div>
</div>

<div id="partInfoDiv" style="display: none;">
	<div style="margin:20px 0 10px 0px;text-align:center;">
		<h3 style="display: inline-block;">部门编号:</h3>
		<input id="partNum" name="partNum" maxlength="10" type="text" style="margin-left:0px;width:156px;padding-left:3px;height:24px;line-height:30px;" class="vchInput"/><br/>
	</div>
	<div style="margin:20px 0 10px 0px;text-align:center;">
		<h3 style="display: inline-block;">部门名称:</h3>
		<input id="partName" name="partName"  type="text" style="margin-left:0px;width:156px;padding-left:3px;height:24px;line-height:30px;" class="vchInput"/><br/>
	</div>
	<div style="margin-right:15px;margin-top:20px; text-align:right;"><input class="bcssbtn" type="button" style="padding: 4px 8px 4px 8px" onclick="addPartInfo();" value="添加"  /></div>
</div>

<div id="updatePartInfoDiv" style="display: none;">
	<div style="margin:20px 0 10px 0px;text-align:center;">
		<h3 style="display: inline-block;">部门编号:</h3>
		<input id="updatePartNum" name="updatePartNum" maxlength="10" type="text" style="margin-left:0px;width:156px;padding-left:3px;height:24px;line-height:30px;" class="vchInput"/><br/>
	</div>
	<div style="margin:20px 0 10px 0px;text-align:center;">
		<h3 style="display: inline-block;">部门名称:</h3>
		<input id="updatePartName" name="updatePartName"  type="text" style="margin-left:0px;width:156px;padding-left:3px;height:24px;line-height:30px;" class="vchInput"/><br/>
	</div>
	<div style="margin-right:15px;margin-top:20px;text-align:right;"><input class="bcssbtn" type="button" style="padding: 4px 8px 4px 8px" onclick="updatePartInfo();" value="提交"  /></div>
</div>
</body>  
</html>