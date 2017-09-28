<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<title>业务模板</title>
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
var thisGrid;
var tips;
$(function(){
	$("#processTable").jqGrid({                   
		url: '<%=path%>/kpi/forward/gotoKpiList',
		contentType: 'application/json',
		datatype: 'json',
		colNames: ['id','指标名称','指标标识','周期','备注'],
		colModel: [
			{name:'id',index:'id',sortable:false,hidden:true},
			{name:'znname',index:'znname',sortable:false,hidden:false},
			{name:'zid',index:'mngId',sortable:false,hidden:false},
			{name:'time',index:'time',sortable:false,hidden:false},
			{name:'remark',index:'remark',sortable:false,hidden:false}
			/* {name:'nodeName',index:'nodeName',sortable:true,width:80,align:"left",formatter:showNumFormart} */
		],
		rowNum: 50,
		rowList: [50,100,200],
		pager: '#gridPager',
		sortname: 'id',
		sortorder: "asc",
		rownumbers: true,
		rownumWidth: 50,
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
	jQuery("#jqgh_processTable_rn").prepend("序号");
});

function showNumFormart(processType){
	if(processType==200101){
		processType='代理记账';
	}else if(processType==200100){
		processType='工商注册';
	}else if(processType==200102){
		processType='法律咨询';
	}else if(processType==200103){
		processType='人事代理';
	}else if(processType==200104){
		processType='商标专利';
	}else if(processType==200105){
		processType='其他';
	}
	return processType;
}





function canUseFormat(cellvalue,options,rowObject){
	if(cellvalue == 0){
		return"<font color='red'>【未启用】</font>";
	}else{
		return"<font color='green'>【已启用】</font>";
	}
}

function openWin(id,obj)
{
    obj.target="_blank";
    obj.href = "/supervisory/forward/gotoAddNode?proId='+proId"+id;
    obj.click();
} 

function operaFormat(cellvalue,options,rowObject) {
	var canUse = 0;
	var proId = rowObject.id;
	//alert(proId);
	var divH = "<div style='padding-left: 45px;'>";
	var divB = "</div>";
	var addNode = "";
	var deleteProcess = "";
	//if(canUse == 0){
		var addNode = "<input id='setNode' type='button' value='查看' onclick=\"gotoUpdateNode('"+proId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;' class='bcssbtn'/>";
		var deleteProcess = "<input id='delBuss' type='button' value='修改' onclick=\"deleteProcessSoft('"+proId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;' class='bcssbtn'/> &emsp;&emsp;&emsp;";
		var chose = "<input id='delBuss' type='button' value='选择' onclick=\"deleteProcessSoft('"+proId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;' class='bcssbtn'/>";
	//}else{
		//var addNode = "<input id='setNode' type='button' value='编辑节点' onclick=\"gotoUpdateNode('"+proId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;display:none;' class='bcssbtn'/>&emsp;";
		//var deleteProcess = "<input id='delBuss' type='button' value='删除业务' onclick=\"deleteProcessSoft('"+proId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;display:none;' class='bcssbtn'/>";
	//}
	return divH + addNode + deleteProcess + chose+ divB;
}

var addNodeLayer;
function gotoAddNode(proId){
	addNodeLayer = layer.open({
		title : ' 添加节点',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		shadeClose: true,
		area : ['600px', '450px'],
		content : '<%=path%>/supervisory/forward/gotoAddNode?proId='+proId,
		end : function(){
			//searchfb();
		}
	});
}

var updateNodeLayer;
function gotoUpdateNode(modelId){
	updateNodeLayer = layer.open({
		title : ' 查看模板节点',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		shadeClose: true,
		area : ['600px', '450px'],
		content : '<%=path%>/business/forward/gotoBusinesslNode?processTmpId='+modelId,
       end : function(){
			//searchfb();
		}
	});
}

var addProcessLayer;
function gotoAddProcess(){
	addProcessLayer= layer.open({
		title : '添加业务',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 1,
		shadeClose: true,
		area : ['300px', '100px'],
		content : $("#addProcessDiv"),
		end : function(){
			$("#addProcessName").val("");
			searchfb();
		}
	});
}

function closeAddProcessLayer(){
	window.layer.close(window.addProcessLayer);
}

function saveProcess(){
	var processName = $("#addProcessName").val();
	if($.ckTrim(processName) == ""){
		parent.$.showMsg("请填写业务名称!", 2);
		return;
	}
	tips = layer.msg("正在保存，请稍后...", {icon : 16,time : 0, shade: [0.1]});
	$.post('<%=path%>/supervisory/asyn/saveProcess',{
		'processName':processName
	},function(result){
		if(result.code==0){
			parent.$.showMsg("保存成功!", 1, function(){
				layer.close(tips);
				closeAddProcessLayer();
			});
		}else if(result.code==1){
			parent.$.showMsg("保存失败,系统出错!", 2,function(){
				layer.close(tips);
			});
		}else if(result.code==2){
			parent.$.showMsg("业务名称已存在!", 2,function(){
				layer.close(tips);
			});
		}
	});
}

function deleteProcess(processId){
	layer.confirm("确认删除!", {icon: 3, title:"提示"}, function(index){
		tips = layer.msg("正在删除,请稍后...",{icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/deleteProcess',
		{
			'id': processId
		},
		function(result){
			if(result.code){
				layer.close(tips);
				searchfb();
				$.showMsg("删除成功!",1);
			}else{
				layer.close(tips);
				$.showMsg("删除失败,系统出错!",2);
			}
		});
		layer.close(index);
	});
}

function deleteProcessSoft(processId){
	layer.confirm("确认删除!", {icon: 3, title:"提示"}, function(index){
		tips = layer.msg("正在删除,请稍后...",{icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/deleteProcessSoft',
		{
			'id': processId
		},
		function(result){
			if(result.code){
				layer.close(tips);
				searchfb();
				$.showMsg("删除成功!",1);
			}else{
				layer.close(tips);
				$.showMsg("删除失败,系统出错!",2);
			}
		});
		layer.close(index);
	});
}

//查询

</script>
</head>
<body>
<div id="mainDiv" style="padding-top: 10px">
	<div style="float: right;padding-right: 20px;" title="审批人员信息">
			<div style="margin-bottom:15px;">
				<span>
					<input maxlength="12"  id="processName" name="processName" type="hidden" class="dfinput_fb" style="width:140px;height:24px;"/>&emsp;
					
					<input id="addBuss" class="abtn" type="button" onclick="gotoAddProcess();" value="添加业务" />
				</span>
			</div>
		<table id="processTable"></table>
		<div id="gridPager"></div>
	</div>
</div>
<input type="hidden" id="processTmpId" name="processTmpId" value="${requestScope.processTmpId}"/>
</body>  
</html>