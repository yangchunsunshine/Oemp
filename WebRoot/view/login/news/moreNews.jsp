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
<title>报税</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script type="text/javascript" src="<%=path%>/script/powerAuth.js"></script>
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
<script type=text/javascript>
var thisGrid;
var tips;

$(function(){
	getNewList();
});

function getNewList(){
	thisGrid = $("#newListTable").jqGrid({
		url: '<%=path%>/supervisory/asyn/getNewList',
		datatype: 'json',
		colNames: ['公告ID','客户ID','创建者','标题','内容','创建时间','操作'],
		colModel: [
			{name:'newId',index:'newId',sortable:false,hidden:true},
			{name:'orgId',index:'orgId',sortable:false,hidden:true},
			{name:'createrName',index:'createrName',sortable:false,width:"60px",align:"left"},
			{name:'title',index:'title',sortable:true,width:"100px",align:"left"},
			{name:'context',index:'context',sortable:true,align:"left",width:"160px",formatter:contextFmatter},
			{name:'createTime',index:'createTime',sortable:true,width:"60px",align:"center"},
			{name:'operate',index:'operate',sortable:true,align:"center",width:"60px",formatter:operatorFmatter}
		],
		rowNum: 100,
		rowList: [100,150,200],
		pager: '#gridPager',
		mtype: "POST",
		width:$(window).width()-5,
		height:$(window).height()-170,
		rownumbers: true,
		viewrecords: true,
		multiselect: false,
		ondblClickRow: function(rowid){},
		gridComplete: function (data) {
			authView();
       	},
		hidegrid: false
	});
	jQuery("#newListTable").jqGrid('navGrid',"#gridPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
	jQuery("#jqgh_newListTable_rn").prepend("序号");
}

function contextFmatter(cellvalue, options, rowObject){
	if(cellvalue.length>16){
			  cellvalue=cellvalue.substring(0,16)+"...";
	}
	return cellvalue;
}

function operatorFmatter(cellvalue, options, rowObject){
	var upd = "<span id='updNotice' style='display: none;'><a href='#' title='修改' onclick=\"editNews("+cellvalue+")\"><img src='<%=path%>/style/image/ico_turn.png'/></a></span>";
	var del = "<span id='delNotice' style='display: none;margin-left: 6px;'><a href='#' title='删除' onclick=\"delSingleNotice("+cellvalue+")\"><img src='<%=path%>/style/image/ico_delete_ro.png'/></a></span>";
	return upd+del;
}

var loadEditMessage;
function editNews(newId){
	loadEditMessage = layer.open({
		title : '修改公告',
		shade : [ 0.1, '#000' ],
		fix : true,
		maxmin: true,
		shadeClose: true,
		type : 2,
		area : ['560px', '260px'],
		end : function(){
			queryMessage();
		},
		content : '<%=path%>/supervisory/forward/gotoEditNews?newId='+newId
	});
}

function delSingleNotice(noticeId){
	layer.confirm("确认删除?", {icon: 3, title:"提示"}, function(index){
		tips = layer.msg("正在删除...", {icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/delNotice',{'noticeId':noticeId},function(result){
			if(result.code){
				layer.close(tips);
				parent.$.showMsg("删除成功!", 1);
				queryMessage();
			}else{
				layer.close(tips);
				parent.$.showMsg("删除失败!", 2);
				queryMessage();
			}
		})
	  	layer.close(index);
	});
}

function queryMessage(){
	var title = $("#title").val();
	var createDate = $("#createDate").val();
	thisGrid.jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/queryNewList?title='+title+'&createDate=' + createDate, page : 1}).trigger("reloadGrid");
}
</script>
</head>
<body>
   	<div class="tallyC"><h4><img src="<%=path%>/style/image/ico_table_01.png"/>查询条件：</h4>
		<table class="table_query"  cellpadding="0" cellspacing="0" rules="all">
			<tr>
				<td width="30%" align="center">
					<span>标题：</span>
					<span><input type="text" class="txt140" maxlength="30" id="title" name="title" value=""/></span>
				</td>
				<td width="30%" align="center">
					<span>创建时间：</span>
					<span><input name="createDate" type="text" id="createDate" onfocus="WdatePicker()" class="Wdate" readonly="readonly"/></span>
				</td>
				<td width="40%" align="center">
					<span><input onclick="queryMessage();" value="查询" class="bcssbtn" class="acssbtn" type="button" style="padding: 2px 6px 2px 6px" style="height:30px;font-size:15px;text-align: center;padding-top:0px; padding-bottom:0px"/></span>
				</td>
			</tr>
		</table>
	</div>
   	<div class="tallyA"><h4><img src="<%=path%>/style/image/ico_table_01.png"/>公告列表：</h4>
   		<table id="newListTable" class="table_query"  cellpadding="0" cellspacing="0" rules="all"></table>
   		<div id="gridPager" width="100%"></div>
   	</div>
</body>
</html>
