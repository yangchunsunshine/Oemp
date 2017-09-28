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
<title>业务模板选择-含合同</title>
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
var contractId = ${contractId};
var processId = ${processId};
var orgId = ${orgId};
var thisGrid;
var tips;
$(function(){
	$("#canUse").select2({
		language: "zh-CN"
	});
	var processName = $("#processName").val();
	var canUse = $("#canUse").val();
	thisGrid = $("#processTable").jqGrid({
		url: '<%=path%>/supervisory/asyn/getAllBusinessModel?canUse='+canUse,
		datatype: 'json',
		colNames: ['选择','id','proId','mngId','idDelete','业务名称','业务类型','业务状态','操作'],
		colModel: [
			{name:'checkName',index:'checkName',sortable:true,width:10,align:"center",formatter:addCheckBox},
			{name:'id',index:'id',sortable:false,hidden:true},
			{name:'proId',index:'proId',sortable:false,hidden:true},
			{name:'mngId',index:'mngId',sortable:false,hidden:true},
			{name:'idDelete',index:'idDelete',sortable:false,hidden:true},
			{name:'processName',index:'processName',sortable:true,width:50,align:"left"},
			{name:'processType',index:'processType',sortable:true,width:80,align:"left",formatter:showNumFormart},
			{name:'canUse',index:'canUse',sortable:true,width:30,align:"center",formatter:canUseFormat},
			{name:'opera',index:'opera',sortable:true,width:60,align:"left",formatter:operaFormat}
		],
		rowNum: 50,
		rowList: [50,100,200],
		pager: '#gridPager',
		sortname: 'ownerId',
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



// 添加复选框
function addCheckBox(cellvalue,options,rowObject){
	var id=rowObject.id;
	var divH = "<div>";
	var divB = "</div>";
	var c="<input id='checkNAme' type='checkbox' name='checkName' value=\""+id+"\"/>";
	return divH+c+divB;
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
	var divH = "<div style='padding-left: 45px;'>";
	var divB = "</div>";
	var addNode = "";
	var deleteProcess = "";
	//if(canUse == 0){
		var addNode = "<input id='setNode' type='button' value='查看' onclick=\"gotoUpdateNode('"+proId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;' class='bcssbtn'/>";
		//var deleteProcess = "<input id='delBuss' type='button' value='修改' onclick=\"deleteProcessSoft('"+proId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;' class='bcssbtn'/>";
		//var deleteTrueProcess = "<input id='delBuss' type='button' value='删除' onclick=\"deleteProcess('"+proId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;' class='bcssbtn'/>";
		var chose = "<input id='delBuss' type='button' value='选择' onclick=\"deleteProcessSoft('"+proId+"');\" style='height:23px;font-size:10px;text-align: center;padding-top:0px; padding-bottom:0px;' class='bcssbtn'/>";
	//}else{
		//var addNode = "<input id='setNode' type='button' value='编辑节点' onclick=\"gotoUpdateNode('"+proId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;display:none;' class='bcssbtn'/>&emsp;";
		//var deleteProcess = "<input id='delBuss' type='button' value='删除业务' onclick=\"deleteProcessSoft('"+proId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;display:none;' class='bcssbtn'/>";
	//}
	return divH + addNode + chose+ divB;
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
			searchfb();
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
			searchfb();
		}
	});
}

var updateNodeLayer1;
function gotoUpdateNode1(){
	var flag = false;
	var checkName = document.getElementsByName("checkName");
	var ary = new Array();
	var val;
	for(var i=0;i<checkName.length;i++){
		 if(checkName[i].checked == true)
		  {
			 flag = true;
			 val=checkName[i].value;
		  }
	
	}
	 if(!flag){
		  //alert("请至少选择一项业务模板");
		  parent.$.showMsg("请选择一项业务模板", 2);
		  return;
		 }
	
	updateNodeLayer1 = layer.open({
		title : ' 添加节点',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		shadeClose: true,
		area : ['600px', '450px'],
		content : '<%=path%>/supervisory/forward/gotoUpdateNodeAfter?processTmpId='+val,
		end : function(){						 
			searchfb();
		}
	});
}

var updateNodeLayer2;
function gotoUpdateNode2(){
    var flag = false;
	var checkName = document.getElementsByName("checkName");
	var ary = new Array();
	var val;
	var str;
	for(var i=0;i<checkName.length;i++){
		 if(checkName[i].checked == true)
		  {
			 flag = true;
			 val=checkName[i].value;
			 ary[i]=val;
			 //alert(checkName[i].value);
		  }
	
	}
	 $.post('<%=path%>/supervisory/asyn/addUserTempAndNode',
			 {
			'array':ary.join(",")
		    },function(result){
			if(result.code==true){
				parent.$.showMsg("保存成功!", 1, function(){
					layer.close(tips);
					closeAddProcessLayer();
					searchfb();
				});
			}else if(result.code==false){
				parent.$.showMsg("保存失败,系统出错!", 2,function(){
					layer.close(tips);
				});
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
		parent.$.showMsg("请填写节点名称!", 2);
		return;
	}
	var checkName = document.getElementsByName("checkName");
	var val;
	for(var i=0;i<checkName.length;i++){
		 if(checkName[i].checked == true){
			 val= checkName[i].value;
			 break;
		 }
	}
	
	tips = layer.msg("正在保存，请稍后...", {icon : 16,time : 0, shade: [0.1]});
	$.post('<%=path%>/supervisory/asyn/SaveNode',{
		'nodeName':processName,
		'processTmpId':val
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
		$.post('<%=path%>/supervisory/asyn/deleteProcessTemp',
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
var update;
function deleteProcessSoft(processIdTemp){ //选择业务需要将模板业务数据添加给人
	//将业务模板指定给人员
	//alert(processId);
	if("-1"==processId){ //新增业务信息
		layer.confirm("确认选择!", {icon: 3, title:"提示"}, function(index){
			
			tips = layer.msg("正在处理,请稍后...",{icon : 16,time : 0, shade: [0.1]});
			$.post('<%=path%>/business/asyn/addProcessInfo',
			{
				'processIdTemp': processIdTemp ,
				'contractId':contractId ,
				'orgId': orgId
			},
			function(result){
				if(result.code=="1"){
					$.showMsg("添加业务和环节失败,系统出错!",2);
				}else{
					window.parent.location.href="<%=path%>/business/forward/gotoBusinessView?pageName=addProcessByContract&contractId="+contractId+"&processId="+result.processId;
				}
			});
			layer.close(index);
		});
		
	}else{
		layer.confirm("确认选择!", {icon: 3, title:"提示"}, function(index){
			
			tips = layer.msg("正在处理,请稍后...",{icon : 16,time : 0, shade: [0.1]});
			$.post('<%=path%>/business/asyn/updateProcessInfo',
			{
				'processIdTemp': processIdTemp ,
				'processId':processId,
				'contractId':contractId ,
				'orgId': orgId
			},
			function(result){
				//alert(result.processId);
				if(result.code=="1"){
					$.showMsg("添加业务和环节失败,系统出错!",2);
				}else{
					window.parent.location.href="<%=path%>/business/forward/gotoBusinessView?pageName=addProcessByContract&contractId="+contractId+"&processId="+result.processId;
				}
				
			});
			layer.close(index);
		});
		
	}
	
	
	
	 

	
}

//查询
function searchfb(){
	var processName = $("#processName").val();
	var canUse = $("#canUse").val();
	thisGrid.jqGrid('setGridParam',{url:'<%=path%>/supervisory/asyn/getAllBusinessModel?canUse='+canUse, page : 1}).trigger("reloadGrid");
}

// 提交节点
var addProcessLayer;
function addNode(){
	var flag = false;
	var checkName = document.getElementsByName("checkName");
	var ary = new Array();
	var val;
	for(var i=0;i<checkName.length;i++){
		 if(checkName[i].checked == true)
		  {
			 flag = true;
			 val=checkName[i].value;
			 ary.pop(checkName[i].value);
			 
			 addProcessLayer= layer.open({
					title : '添加节点',
					shade : [ 0.1, '#000' ],
					fix : true,
					type : 1,
					shadeClose: true,
					area : ['300px', '100px'],
					content :$("#addProcessDiv"),
					end : function(){
						$("#addProcessName").val("");
						searchfb();
					}
				});
		 
		  }

	}
	
	 if(!flag){
		  //alert("请至少选择一项业务模板");
		  parent.$.showMsg("请选择一项业务模板", 2);
		  return;
		 }
	
}
	
	

</script>
</head>
<body>
<div id="mainDiv" style="padding-top: 10px">
	<div style="float: right;padding-right: 20px;" title="">
			<div style="margin-bottom:15px;">
				<span>
					<input maxlength="12"  id="processName" name="processName" type="hidden" class="dfinput_fb" style="width:140px;height:24px;"/>&emsp;
					<b style="font-size: 15px">业务状态:</b>
					<select id="canUse" name="canUse" class="select_staff" style="width:125px;">
						    <option value="0" selected="selected">全部</option>
						    <option value="200101">代理记账</option>
							<option value="200100">工商注册</option>
							<option value="200102">法律咨询</option>
							<option value="200103">人事代理</option>
							<option value="200104">商标专利</option>
							<option value="200105">其他</option>
					</select>&emsp;
					<input class="bcssbtn" type="button" onclick="searchfb();" value="查询" style="padding: 2px 6px 2px 6px"/>
					
					
				</span>
			</div>
		<table id="processTable"></table>
		<div id="gridPager"></div>
	</div>
</div>



<div id="addProcessDiv" style="display: none;">
	<table class="table_query" border="0" cellpadding="0" cellspacing="0" rules="all" style="margin-top: 15px">
		<tr>
			<td align="left" style="border:0;"><h4>节点名称:</h4></td>
			<td align="left" style="border:0;">
				<input maxlength="12"  id="addProcessName" name="nodeName" type="text" class="dfinput_fb" style="width:140px;height:24px;"/>
			</td>
			<td>
				<input name="getCheckBox" type="hidden" value=""/>
			</td>
			<td align="left" style="border:0;">
				<input class="acssbtn" type="button" style="padding: 2px 6px 2px 6px;" onclick="saveProcess();" value="提交" />
			</td>
			
		</tr>
	   
	</table>
</div>

</body>  
</html>