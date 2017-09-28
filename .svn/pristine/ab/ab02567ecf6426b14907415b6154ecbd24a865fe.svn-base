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
<title>短信详情</title>
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
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/zTree/css/zTreeStyle.css" /><script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script type="text/javascript" src="<%=path%>/script/powerAuth.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/zTree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/select2.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/i18n/zh-CN.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/select2/css/select2.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/style/css/style.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/style/css/style_mnt.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqueryui/jquery-ui.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqgrid/css/ui.jqgrid.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/zTree/css/zTreeStyle.css" />
<script type="text/javascript">
var thisGrid;
var tips;
$(function(){
	thisGrid = $("#processTable").jqGrid({
		url: '<%=path%>/sendmessage/forward/gotoMessageFenye',
		datatype: 'json',
		colModel: [
			{name:'content',index:'content',sortable:true,width:10,align:"left"},
			{name:'id',index:'id',sortable:false,hidden:true}
		],
		rowNum: 15,
		rowList: [15,30,45],
		pager: '#gridPager',
		sortname: 'createtime',
		height : "100%",
		sortorder: "desc",
		rownumbers: true,
		rownumWidth: 15,
		caption: '业务列表',
		mtype: "POST",
		width:$(window).width()-40,
        height:$(window).height()-170,
		viewrecords: true,
		loadComplete: function (data){
			authView();
		},
		hidegrid: false
	});
	jQuery("#processTable").jqGrid('navGrid',"#gridPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
});


function addDanYuanGe(cellvalue, options, rowObject){
	var id=rowObject.id;
	var divH = "<label>";
	var str="将于"+id+"发送233个公司";
	var divB = "</label>";
	
	return divH+str+divB;
}
</script>
</head>
<body>
	<table id="processTable"></table>
    <div id="gridPager"></div>

	
   	<div class="nodeB">
   		<table id="nodeTable" class="table_query" border="0" cellpadding="0" cellspacing="0" rules="all" style="display: none">
			<c:forEach items="${listMessages}" var="upNode" varStatus="status">
				<div>
						<tr>
						<td align="left">
							<input class="2" name="content" id="content" mes="content" type="hidden" value="${upNode.content}"/>
							<label>
							<c:if test="${upNode.status!=2}">
								将于${upNode.sendtime} 发送233个公司
							</c:if>
							<c:if test="${upNode.status==2}">
							  	${upNode.sendtime}
						  	</c:if>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<font>
							<c:if test="${upNode.status==2}">
								已发送
							</c:if>
							<c:if test="${upNode.status!=2}">
							  	未发送
						  	</c:if>
						  	</font>
						  	</label>
						</td>
						</tr>
						<tr>
						<td align="left" style="height: auto;">
							<textarea rows="3" name="textAre" cols="100" id="textAre" readonly="readonly"></textarea>
							<c:if test="${upNode.status!=2}">
							  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							  	<a id="hrefDownLoad" onclick="downLoadExcel();" class="acssbtn" style="padding: 3px 4px 2px 4px;">编辑</a>
						  	</c:if>
						</td>
						</tr>
						<tr>
						<td></td>
						</tr>
				</div>
			</c:forEach>
   		</table>
   	</div>
   	
</body> 
</html>