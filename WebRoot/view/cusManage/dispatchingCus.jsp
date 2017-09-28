<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="com.wb.framework.commonUtil.PropertiesReader"%>
<%PropertiesReader reader = PropertiesReader.getInstance();%>
<%String url = reader.getValue("/com/wb/config/application", "ACC_ROLER_URL");%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<title>派工管理</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/zTree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/select2.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/i18n/zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/select2/css/select2.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style_mnt.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/jqueryui/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/jqgrid/css/ui.jqgrid.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/zTree/css/zTreeStyle.css" />
<script type="text/javascript">
var tips;
var updateBookId;

$(function(){
	$("#tel").mustNumber();
	$("#roleSelect").select2({
		language: "zh-CN"
	});
	$("#depSelect").select2({
		language: "zh-CN"
	});
	getOrgRole();
	getDispList();
	getGrantViewList();
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
	       	data : {"roleType":"0001","refCompanyId":orgId},
			url  : url,
	       	success : function(data){
	       		var mess = data.results;
	       		var roleStr = "<option value='0'>选择角色：</option>";
	       		for(var i=0;i<mess.length;i++){
					roleStr = roleStr + "<option value='"+mess[i].rid+"'>"+mess[i].roleName+"</option>";
	       		}
	       		$("#roleSelect").html(roleStr);
	       	},
	       	error : function(XMLHttpRequest, textStatus, errorThrown) {
				$.showMsg("亲!您的网络不给力哦~", 2, null, 5000);
			}
		});
	}

function getDispList(){
	$.post('<%=path%>/supervisory/asyn/getDispList',function(result){
		var dispTr = "<tr>"+
						 "<th width='19%'>公司</th>"+
						 "<th width='17%'>账套</th>"+
						 "<th width='15%'>职员</th>"+
						 "<th width='17%'>角色</th>"+
						 "<th width='22%'>部门</th>"+
						 "<th width='10%'>操作</th>"+
					 "</tr>";
		for(var i=0;i<result.dispList.length;i++){
			dispTr += "<tr>"+
				"<td align='left'>"+result.dispList[i].orgName+"</td>"+
				"<td align='left'>"+result.dispList[i].accBookName+"</td>"+
				"<td align='left'>"+result.dispList[i].memName+"</td>"+
				"<td align='left'>"+result.dispList[i].roleName+"</td>"+
				"<td align='left'>"+result.dispList[i].depName+"</td>"+
				"<td align='center'>"+
				"<span><a href='#' title='转派工' onclick=\"chEmp('"+result.dispList[i].mBookId+"','"+result.dispList[i].memId+"','"+result.dispList[i].Operator+"');\"><img src='<%=path%>/style/image/ico_turn.png'/></a></span>"+
				"<span style='margin-left: 15%;'><a href='#' title='删除' onclick=\"delDisp('"+result.dispList[i].mBookId+"','"+result.dispList[i].memId+"','"+result.dispList[i].Operator+"');\"><img src='<%=path%>/style/image/ico_delete_ro.png'/></a></span>"+
				"</td>"+
				"</tr>";
		}
		$("#dispListTable").html(dispTr);
	});
}

function getGrantViewList(){
	$.post('<%=path%>/supervisory/asyn/getGrantViewList',function(result){
		var grantViewTr = "<tr>"+
							"<th width='36%'>客户</th>"+
							"<th width='24%'>授权用户</th>"+
							"<th width='24%'>角色</th>"+
							"<th width='15%'>操作</th>"+
						  "</tr>";
		for(var i=0;i<result.grantViewList.length;i++){
			grantViewTr += "<tr>"+
								"<td width='36%' align='left'>"+result.grantViewList[i].orgName+"</td>"+
								"<td width='24%' align='left'>"+result.grantViewList[i].memName+"</td>"+
								"<td width='24%' align='left'>"+result.grantViewList[i].roleName+"</td>"+
								"<td width='15%' align='center'>"+
								"<a href='#' title='删除' onclick=\"delDisp('"+result.grantViewList[i].mBookId+"','"+result.grantViewList[i].memId+"','"+result.grantViewList[i].Operator+"');\"><img src='<%=path%>/style/image/ico_delete_ro.png'/></a>"+
								"</td>"+
							"</tr>";
		}
		$("#grantViewTable").html(grantViewTr);
	});
}

function selectTag(showContent,selfObj){
	// 操作标签
	var tag = document.getElementById("tags").getElementsByTagName("li");
	var taglength = tag.length;
	for(i=0; i<taglength; i++){
		tag[i].className = "";
	}
	selfObj.parentNode.className = "selectTag";
	// 操作内容
	for(i=0; j=document.getElementById("tagContent"+i); i++){
		j.style.display = "none";
	}
	document.getElementById(showContent).style.display = "block";	
}

function queryEmpByDep(){
	var depSelect = $("#depSelect").val();
	if(depSelect=="x"){
		return;
	}
	$.post('<%=path%>/supervisory/asyn/getEmpByDep',{'depSelect' : depSelect},function(result){
		var empLi = "";
		for(var i=0;i<result.empList.length;i++){
			empLi = empLi + "<li id='"+result.empList[i].empId+"' ondblclick='copyThisMessToTable(this);''>"+result.empList[i].empName+"</li>";
		}
		$("#empUl").html(empLi);
	});
}

function queryUpdateEmpByDep(){
	var depSelect = $("#updateDepSelect").val();
	if(depSelect=="x"){
		$("#updateEmpSelect").html("");
		return;
	}
	$.post('<%=path%>/supervisory/asyn/getEmpByDep',{'depSelect' : depSelect},function(result){
		var empSelect = "";
		for(var i=0;i<result.empList.length;i++){
			empSelect = empSelect + "<option value='"+result.empList[i].empId+"'>"+result.empList[i].empName+"</option>";
		}
		$("#updateEmpSelect").html(empSelect);
	});
}

function copyThisMessToTable(obj){
	var roleId = $("#roleSelect").val();
	var roleName = $("#roleSelect").find("option:selected").text();
	var depId = $("#depSelect").val();
	var depName = $("#depSelect").find("option:selected").text();
	var empId = obj.id;
	var empName=obj.innerText;
	if(roleId == "0"){
		parent.$.showMsg("请选择角色!", 2);
		return;
	}
	if(depId == "x"){
		parent.$.showMsg("请选择部门!", 2);
		return;
	}
	$("#"+empId).remove();
	var selEmpRow = "<tr>"+
						"<td width='26%'>"+roleName+"</td>"+
						"<td width='33%'>"+depName+"</td>"+
						"<td width='25%'>"+empName+"<input type='hidden' name='memberRoleId' depId='"+depId+"' memberId='"+empId+"' roleId='"+roleId+"'/></td>"+
						"<td width='14%'><a href='#' title='移除' onclick=\"delEmpTr(this);\"><img src='<%=path%>/style/image/error_fuck.png'/></a></td>"+
					"</tr>";
	$("#selectedEmpTable tr:last").after(selEmpRow);
};

function delEmpTr(k){//点击税务的删除按钮,删除一条税务信息
	$(k).parent().parent().remove();
}

function saveDispatching(){
	var memberRoleId = $("input[name='memberRoleId']");
	var bookOrgId = $("input[name='bookOrgId']");
	if(!memberRoleId.length>0){
		parent.$.showMsg("请选择公司职员!", 2);
		return;
	}
	if(!bookOrgId.length>0){
		parent.$.showMsg("请选择需要派工的账套!", 2);
		return;
	}
	var dispMess =[];
	for(var i=0;i<memberRoleId.length;i++){
		for(var j=0;j<bookOrgId.length;j++){
			var dispObj = new Object();
			dispObj.depId = $(memberRoleId[i]).attr("depId");
			dispObj.memberId = $(memberRoleId[i]).attr("memberId");
			dispObj.roleId = $(memberRoleId[i]).attr("roleId");
			dispObj.bookId = $(bookOrgId[j]).attr("bookId");
			dispObj.orgId = $(bookOrgId[j]).attr("orgId");
			dispMess.push(dispObj);
		}
	}
	var data = JSON.stringify(dispMess);
	tips = layer.msg("正在保存...", {icon : 16,time : 0, shade: [0.1]});
	$.post('<%=path%>/supervisory/asyn/saveDispatching',{dispMess:data}, function(result){
		if(result.code){
			layer.close(tips);
			parent.$.showMsg("保存成功!", 1);
			getDispList();
			getGrantViewList();
		}else{
			layer.close(tips);
			parent.$.showMsg("保存失败!", 2);
		}
	});
}

function closeFrame(){//点击关闭按钮,关闭当前页面,并刷新父级页面
	window.parent.thisGrid.trigger("reloadGrid");
	window.parent.layer.close(window.parent.dispatching);
}

function chEmp(mBookId,memId,operator){
	if(memId == operator){
		parent.$.showMsg("账薄创建者不能转派工!", 2);
		return;
	}
	updateBookId = mBookId;
	updateDispLayer_openWindow();
}

function delDisp(mBookId,memId,operator){
	if(memId == operator){
		parent.$.showMsg("账薄创建者不能被删除!", 2);
		return;
	}
	layer.confirm("确认删除?", {icon: 3, title:"提示"}, function(index){
		tips = layer.msg("正在删除...", {icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/delDisp',{'mBookId':mBookId,'memId':memId,'operator':operator}, function(result){
			if(result.code){
				layer.close(tips);
				parent.$.showMsg("删除成功!", 1);
				getDispList();
				getGrantViewList();
			}else{
				layer.close(tips);
				parent.$.showMsg("删除失败!", 2);
			}
		});
	    layer.close(index);
	});
}

var updateDispLayer;
function updateDispLayer_openWindow(){
	updateDispLayer = layer.open({
		type : 1,
		title : '转派工',
		shade : [ 0.1, '#000' ],
		fix : true,
		maxmin: true,
		shadeClose: true,
		area : ['280px', '220px'],
		content : $("#updateDispDiv")
	});
}

function updateDisp(){
	var depId = $("#updateDepSelect").val();
	var empId = $("#updateEmpSelect").val();
	if(depId=="x"){
		parent.$.showMsg("请选择部门!", 2);
		return;
	}
	if(empId==""||empId==null){
		parent.$.showMsg("请选择员工!", 2);
		return;
	}
	tips = layer.msg("正在保存...", {icon : 16,time : 0, shade: [0.1]});
	$.post('<%=path%>/supervisory/asyn/updateDisp',{'mBookId':updateBookId,'depId':depId,'empId':empId}, function(result){
		if(result.code==0){
			layer.close(tips);
			parent.$.showMsg("保存成功!", 1);
			closeUpdateFrame();
			getDispList();
			getGrantViewList();
		}else if(result.code==1){
			layer.close(tips);
			parent.$.showMsg("保存失败!", 2);
		}else if(result.code==2){
			layer.close(tips);
			parent.$.showMsg("保存失败,转入人员已经存在该公司账套!", 2);
		}
	});
}

function closeUpdateFrame(){//点击关闭按钮,关闭当前页面,并刷新父级页面
	window.layer.close(window.updateDispLayer);
}

function grantView(){
	var tel = $("#tel").val()
	if(tel==null||tel==""){
		parent.$.showMsg("请输入授权用户手机号!", 2);
		return;
	}
	var bookOrgId = $("input[name='bookOrgId']");
	var grantMess =[];
	for(var j=0;j<bookOrgId.length;j++){
		var dispObj = new Object();
		dispObj.bookId = $(bookOrgId[j]).attr("bookId");
		dispObj.orgId = $(bookOrgId[j]).attr("orgId");
		grantMess.push(dispObj);
	}
	var data = JSON.stringify(grantMess);
	tips = layer.msg("正在授权...", {icon : 16,time : 0, shade: [0.1]});
	$.post('<%=path%>/supervisory/asyn/grantView',{grantMess:data,'tel':tel}, function(result){
		if(result.code==0){
			layer.close(tips);
			parent.$.showMsg("授权成功!", 1);
			getGrantViewList();
			getDispList();
		}else if(result.code==1){
			layer.close(tips);
			parent.$.showMsg("授权失败!", 2);
		}else if(result.code==2){
			layer.close(tips);
			parent.$.showMsg("授权失败,没有此用户!", 2);
		}
	});
}
</script>
</head>
<body style="overflow-x:hidden;">
<div id=con>
    <ul id=tags>
        <li class="selectTag"><a onclick="selectTag('tagContent0',this)" href="javascript:void(0)">派工</a> </li>
        <li><a onclick="selectTag('tagContent1',this)" href="javascript:void(0)">派工查询</a> </li>
        <li><a onclick="selectTag('tagContent2',this)" href="javascript:void(0)">授权客户查看</a> </li>
    </ul>
    <div id=tagContent>
    <!--派工显示内容-->
        <div class="tagContent selectTag" id=tagContent0>
        	<div class="roles" style="display: inline;">
            	派工角色：
                <select id="roleSelect"></select>
            </div>
            <div class="roles" style="display: inline;text-align:right;margin-left: 830px;">
                <!--  <input align="right" type="checkbox"/><font color="red"> 发送消息通知</font> -->
                <input align="right" type="button" style="padding: 2px 6px 2px 6px" value="保存" onclick="saveDispatching();" class="acssbtn" style="height:30px;font-size:15px;text-align: center;padding-top:0px; padding-bottom:0px"/>&nbsp;&nbsp;&nbsp;
                <input align="right" type="button" style="padding: 2px 6px 2px 6px" value="关闭" onclick="closeFrame();" class="acssbtn" style="height:30px;font-size:15px;text-align: center;padding-top:0px; padding-bottom:0px"/>
             </div>
            <div class="roles_02">
            	<div class="member">
                	<h4><img src="<%=path%>/style/image/user_01.png"/>公司职员</h4>
                    <span>
                        <select id="depSelect" onchange="queryEmpByDep();">
                            <option value="x">请选择部门：</option>
                            <c:forEach items='${depList}' var='dep'>
								<option value="${dep.id}">${dep.partName}</option>
							</c:forEach>
                        </select>
                    </span>
                    <ul id="empUl" class="roles_all">
                    </ul>
                </div>
                <div class="arrow_r"><img src="<%=path%>/style/image/arrow_right.png"/></div>
            	<div class="member_list">
                	<h4><img src="<%=path%>/style/image/ico_choiced.png"/>已选择的操作员</h4>
                    <div class="operators">
                    	<table id="selectedEmpTable" class="table_operators" border="0" cellpadding="0" cellspacing="0" width="100%"><tr></tr></table>
                    </div>
                </div>
            </div>
            <div class="roles_03">
            	<div class="member_books">
                	<h4><img src="<%=path%>/style/image/ico_table_01.png"/>需要派工的账套</h4>
                    <span>共${fn:length(bookOrgList)}个帐套</span>
                    <ul class="books_all">
                    	<c:forEach items='${bookOrgList}' var='bookOrg'>
								<li>${bookOrg.bookName}-${bookOrg.orgName}<input type="hidden" name="bookOrgId" orgId="${bookOrg.orgId}" bookId="${bookOrg.bookId}"/></li>
						</c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    	<!--派工查询显示内容-->
        <div class="tagContent" id=tagContent1>
			<table id="dispListTable" class="table_query"  cellpadding="0" cellspacing="0" rules="all"></table>
        </div>
    	<!--授权客户查看显示内容-->
        <div class="tagContent" id=tagContent2>
        	<div class="roles" style="padding-bottom:8px;">
            	授权用户手机号 ： <input id="tel" type="text" maxlength="11"/>
                <a href="#" onclick="grantView();" class="bcssbtn">授权</a>
            </div>
			<table id="grantViewTable" class="table_query"  cellpadding="0" cellspacing="0" rules="all"></table>
        </div>
    </div>
</div>
<div id="updateDispDiv" style="display: none;">
	<div style="margin:20px 0 10px 0px;text-align:center;">
		<h3 style="display: inline-block;">选择部门:</h3>
		<select id="updateDepSelect" class="Taxes" onchange="queryUpdateEmpByDep();">
			<option value="x">请选择部门：</option>
			<c:forEach  items="${depList}" var="dep">
				<option value="${dep.id}">${dep.partName}</option>
			</c:forEach>
		</select>
	</div>
	<div style="margin:20px 0 10px 0px;text-align:center;">
		<h3 style="display: inline-block;">转派人员:</h3>
		<select id="updateEmpSelect" class="Taxes"></select>
	</div>
	<div style="margin-right:20px; margin-top:35px; text-align:right;">
		<input class="bcssbtn" type="button" style="padding: 3px 7px 3px 7px" onclick="updateDisp();" value="保存" style="width:60px;height:30px;font-size:15px;text-align: center;padding-top:0px; padding-bottom:0px"/>
		<input class="bcssbtn" type="button" style="padding: 3px 7px 3px 7px" onclick="closeUpdateFrame();" value="关闭" style="width:60px;height:30px;font-size:15px;text-align: center;padding-top:0px; padding-bottom:0px"/>
	</div>
</div>
</body>
</html>

