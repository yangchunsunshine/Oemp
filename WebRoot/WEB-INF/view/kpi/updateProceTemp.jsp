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
<title>添加节点</title>
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
<script type=text/javascript>
var tips;

$(function(){
	$("#roleSelect").select2({
		language: "zh-CN"
	});
	//getOrgRole();
});








function getOrgRole(){
		var ACC_ROLER_URL = '<%=url%>';
		var orgId ="";
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
	       		var roleStr = "<option value=''>选择角色：</option>";
	       		for(var i=0;i<mess.length;i++){
					roleStr = roleStr + "<option value='"+mess[i].rid+"'>"+mess[i].roleName+"</option>";
	       		}
	       		$("#roleSelect").html(roleStr);
	       	},
	       	error : function(XMLHttpRequest, textStatus, errorThrown) {
				parent.$.showMsg("亲!您的网络不给力哦~", 2, null, 5000);
		}
	});
}

function addNodeTr(){
	var addRoleText = $("#roleSelect").find("option:selected").text();
	var addRoleId = $("#roleSelect").val();
	var addNodeName = $("#addNodeName").val();
	var repeat = checkRepeat(addRoleId,addNodeName);
	if(repeat){
		parent.$.showMsg("节点名称重复!", 2);
		return;
	}
	if(!addRoleId){
		parent.$.showMsg("请选择角色!", 2);
		return;
	}
	if(!addNodeName){
		parent.$.showMsg("请填写节点名称!", 2);
		return;
	}
	var taxRow = "<tr>"+
								"<td align='left'>"+addRoleText+
									"<input mes='roldId' type='hidden' value='"+addRoleId+"'>"+
								"</td>"+
								"<td align='left'>"+addNodeName+
									"<input mes='nodeName' type='hidden' value='"+addNodeName+"'>"+
								"</td>"+
								"<td align='center'>"+
									"<input type='button' value='上移' onclick=\"up(this);\" style='height:23px;font-size:12px;text-align: center;padding: 0px 0px 0px 0px' class='bcssbtn'/>"+
									"<input type='button' value='下移' onclick=\"down(this);\" style='height:23px;font-size:12px;text-align: center;padding: 0px 0px 0px 0px' class='bcssbtn'/>"+
									"<input type='button' value='删除' onclick=\"delNodeTr(this);\" style='height:23px;font-size:12px;text-align: center;padding: 0px 0px 0px 0px' class='bcssbtn'/>"+
								"</td>"+
							"</tr>";
	$("#nodeTable tr:last").after(taxRow);
}

function up(obj){
	var objParentTR = $(obj).parent().parent();
	var prevTR = objParentTR.prev();
	var prevTRId = prevTR.attr("id");
	if(prevTRId=="beginTr"){
		return;
	}
	if (prevTR.length > 0) {
		prevTR.insertAfter(objParentTR);
	}
}

function down(obj){
	var objParentTR = $(obj).parent().parent();
	var nextTR = objParentTR.next();
	if (nextTR.length > 0) {
		nextTR.insertBefore(objParentTR);
	}
}

function delNodeTr(obj,id){//点击税务的删除按钮,删除一条税务信息
	//var id = $("input[mes='id']").val();
	//alert(id);
	$.ajax({
        url: "<%=path%>/supervisory/asyn/deleteNodeAfter?id="+id,
        type: "GET",
        success: function(data){
            if(data.code){
            	$(obj).parent().parent().remove();
				layer.close(tips);
				parent.$.showMsg("删除成功!", 1);
				closeFrame();
			}else{
				layer.close(tips);
				parent.$.showMsg("删除失败,系统出错!", 2);
			}
        },
        error: function(res){
            parent.$.showMsg("亲您的网络不给力哦~", 2);
        }
    });
	
	
}

function initData(){
	var nodes =[];
	var id = $("#id").val();
	var processName = $("#processName").val();
	
	// alert(id);
	// alert(processName);
	
	//$.each(nodeNameInputs,function(index,item){
		var nodeObj = new Object();
		nodeObj.id =id;
		nodeObj.processName=processName;
		//nodeObj.orderSeq = index*1000;
		nodes.push(nodeObj);
	//});
	return nodes;
}

function checkRepeat(addRoleId,addNodeName){
	var repeat = false;
	var nodeNameInputs = $(" input[mes='nodeName']");
	var roldIdInputs = $(" input[mes='roldId']");
	$.each(nodeNameInputs,function(index,item){
		var nodeName = $(item).val();
		var roleId = $(roldIdInputs[index]).val();
		if(addRoleId==roleId&&addNodeName==nodeName){
			repeat = true;
		}
	});
	return repeat;
}
var close;
function saveNode(){
	//var nodes = initData();
	tips = layer.msg("正在保存...", {icon : 16,time : 0, shade: [0.1]});
	var id = $("#id").val();
	var processName = $("#processName").val();
	close=$.ajax({
                url: "<%=path%>/kpi/asyn/saveProcessTemp",
                type: "POST",
                //contentType : 'application/json;charset=utf-8',
                dataType:"json",
                data:{"id":id,"name":processName},
                success: function(data){
                    if(data.code==3){
						layer.close(tips);
						parent.$.showMsg("保存成功!", 1);
						window.parent.layer.close(window.parent.update);
						closeFrame1();
						
					}else if(data.code==2){
						layer.close(tips);
						parent.$.showMsg("保存失败,系统出错!", 2);
					
					}else if(data.code==1){
						layer.close(tips);
						parent.$.showMsg("已有相同的模板名称!", 2);
					}
                },
                error: function(res){
                    parent.$.showMsg("亲您的网络不给力哦~", 2);
                }
            });
}

function closeFrame(){
	window.parent.layer.close(window.parent.updateNodeLayer);
}
function closeFrame1(){
	window.parent.layer.close(window.parent.close);
}

var thisGrid;
var tips;
$(function(){
	$("#fuck").attr("checked","checked");
	//loadInfo();
	
	
	var productId = $("input[name='name_']:checked").val();// 产品id
	 
	
	$("#canUse").select2({
		language: "zh-CN"
	});
	
	thisGrid = $("#processTable").jqGrid({
		url: '<%=path%>/kpi/kpi/getAllZhibiaoByMoban?id='+$("#id").val(),
		datatype: 'json',
		colNames: ['指标集名称','id','修改时间','操作'],
		colModel: [
			{name:'name',index:'name',sortable:true,width:50,align:"left"},
			{name:'id',index:'id',sortable:false,hidden:true},
			{name:'time',index:'time',sortable:false,width:40,align:"center",formatter:getTime},
			{name:'type',index:'type',sortable:true,width:80,align:"center",formatter:operaFormat},
		],
		rowNum: 50,
		rowList: [50,100,200],
		pager: '#gridPager',
		sortname: 'ownerId',
		sortorder: "asc",
		rownumbers: true,
		rownumWidth: 50,
		caption: '指标集列表',
		mtype: "POST",
		width:$(window).width()-40,
        height:$(window).height()-170,
		viewrecords: true,
		loadComplete: function (data){
			//authView();
		},
		hidegrid: false
	});
	jQuery("#processTable").jqGrid('navGrid',"#gridPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
	jQuery("#jqgh_processTable_rn").prepend("序号");
});

function getTime(){
	
	
	
}


function getTime (ucellvalue,options,rowObject) {
	 var unixTime=rowObject.time;
	 return new Date(parseInt(unixTime)).toLocaleString().replace(/:\d{1,2}$/,' '); 
  }




function col_clear() {
	 var selOpt = $("#canUse option");
	 selOpt.remove();
	}

function loadInfo() {
	var productId = $("input[name='name_']:checked").val();// 产品id
	col_clear();
	//$("#canUse").append('<option> ——请选择———</option>');
	$("#canUse").append('<option value="0" selected="selected">全部</option>');
    $.getJSON("<%=path%>/kpi/fenlei?id="+productId, function(data) {
        //解析数组
        $.each(data.result, function(i, item) {
        	/* alert(item.name);
           $("#canUse").val(item.name);
           $("#canUse").html(2); */
           
        	 $("#canUse").append('<option value="' + item.id + '">' + item.name + '</option>');
        });
        });
}


function loadInfo1() {
	// var productId = $("input[name='name_']:checked").val();// 产品id  radio
	var value=$("#canUse1").val();
	var selOpt = $("#canUse2 option");
	var selOpt1 = $("#canUse5 option");
	 selOpt.remove();
	 selOpt1.remove();
    $.getJSON("<%=path%>/kpi/fenlei?id="+value, function(data) {
        //解析数组
        $.each(data.result, function(i, item) {
        	/* alert(item.name);
           $("#canUse").val(item.name);
           $("#canUse").html(2); */
           
        	 $("#canUse2").append('<option value="' + item.id + '">' + item.name + '</option>');
        });
        });
}


function changedate5() {
	var value=$("#canUse2").val();
	//alert(value);
	var selOpt = $("#canUse5 option");
	 selOpt.remove();
    $.getJSON("<%=path%>/kpi/kpi/showMoban?type="+value, function(data) {
        //解析数组
        $.each(data.result, function(i, item) {
        	/* alert(item.name);
           $("#canUse").val(item.name);
           $("#canUse").html(2); */
           
        	 $("#canUse5").append('<option value="' + item.id + '">' + item.name + '</option>');
        });
        });
}




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
 function getAllTemp(){
	 var rowId = $("#processTable").getGridParam('selrow');
	 var rowData = $("#processTable").getRowData(rowId);
	 var array=[];
	 for(var i=0;i<rowData.length;i++){
		 var value=rowData[i].processType;
		 var processType;
		 if(value=='代理记账'){
			    processType='200101';
			}else if(value=='工商注册'){
				processType='200100';
			}else if(value=='法律咨询'){
				processType='200102';
			}else if(value=='人事代理'){
				processType='200103';
			}else if(value=='商标专利'){
				processType='200104';
			}else if(value=='其他'){
				processType='200105';
			}
		 array.push(processType);
	 }
	 return array;
 }
//下拉框变动的时候 改变一下 导入模板的属性
/* function modifyValue(){
	var canUse = $("#canUse").val();
	if(canUse!=0){
		$("#importModel").css('display','none'); 
	}else{
		$("#importModel").css('display','block'); 
	}
} */




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
		// var addNode = "<input id='setNode' type='button' value='校验数据' onclick=\"gotoUpdateNode('"+proId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;' class='bcssbtn'/>";
		var deleteProcess = "<input id='editProcess' type='button' value='编辑指标' onclick=\"deleteProcessSoft('"+proId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;' class='bcssbtn'/>";
		var deleteTrueProcess = "<input id='delProcess' type='button' value='删除' onclick=\"deleteProcess('"+proId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;' class='bcssbtn'/>";
		var chose = "<input id='selectProcess' type='button' value='验证数据' onclick=\"gotoUpdateNode('"+proId+"');\" style='height:23px;font-size:10px;text-align: center;padding-top:0px; padding-bottom:0px;' class='bcssbtn'/>";
	//}else{
	   var addNode = "<input id='setNode' type='button' value='编辑公式' onclick=\"bianjigongshi('"+proId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;' class='bcssbtn'/>&emsp;";
		//var deleteProcess = "<input id='delBuss' type='button' value='删除业务' onclick=\"deleteProcessSoft('"+proId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;display:none;' class='bcssbtn'/>";
	//}
	return divH +  deleteProcess +addNode + deleteTrueProcess + chose+ divB;
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
			 ary.push(val);
		  }
	
	}
	 if(!flag){
		  //alert("请至少选择一项业务模板");
		  parent.$.showMsg("请选择一项业务模板", 2);
		  return;
		 }
	 
	 if(ary.length>1){
		 parent.$.showMsg("只能选择一项业务模板", 2);
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
	var checkName = document.getElementsByName("checkName");
	var ary = new Array();
	var value=getAllTemp();
	 $.post('<%=path%>/supervisory/asyn/addUserTempAndNode',
			 {
			'array':value.join(",")
		    },function(result){
			if(result.code==2){
				parent.$.showMsg("保存成功!", 1, function(){
					layer.close(tips);
					closeAddProcessLayer();
					searchfb();
				});
			}else if(result.code==3){
				parent.$.showMsg("保存失败,系统出错!", 2,function(){
					layer.close(tips);
				});
			}else if(result.code==1){
				parent.$.showMsg("模板业务已经存在!", 2,function(){
					layer.close(tips);
					closeAddProcessLayer();
					searchfb();
				});
			}
		});
 
}


var addProcessLayer1;
function gotoAddProcess1(){
	addProcessLayer1= layer.open({
		title : '添加业务',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 1,
		shadeClose: true,
		area : ['400px', '200px'],
		content : $("#addProcessDiv1"),
		end : function(){
			$("#addProcessName1").val("");
			$("#canUse2").val("");
			$("#canUse1").val("产品类型");
			//searchfb();
			changedate();
		}
	});
}

function closeAddProcessLayer(){
	window.layer.close(window.addProcessLayer);
}

function closeAddProcessLayer1(){
	window.layer.close(window.addProcessLayer1);
}


function saveProcess(){
	var processName = $("#addProcessName").val();
	if($.ckTrim(processName) == ""){
		parent.$.showMsg("请填写节点名称!", 2);
		return;
	}
	 var reg=/[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im;  
	 if(reg.test(processName)){  
		 parent.$.showMsg("您输入的节点名称含有非法字符！", 2);
		 $("#addProcessName").val('');
		 $("#addProcessName").focus(); 
		 return false;  
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
		}else if(result.code==10){
			parent.$.showMsg("已经存在协同关系,请重建关系再添加!", 2,function(){
				layer.close(tips);
			});
		}else if(result.code==11){
			parent.$.showMsg("节点名称含有特殊字符！", 2,function(){
				layer.close(tips);
			});
		}
	});
}

function deleteProcess(processId){
	//var processId=$("#processId").val();
	//alert(processId);
	layer.confirm("确认删除!", {icon: 3, title:"提示"}, function(index){
		tips = layer.msg("正在删除,请稍后...",{icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/kpi/kpi/deleteZhiBiaoji',
		{
			'id': processId
		},
		function(result){
			if(result.result=='success'){
				searchfbA();
				//clickradio();
				layer.close(tips);
				
				$.showMsg("删除成功!",1);
				//searchfb();
				
			}else{
				layer.close(tips);
				$.showMsg("删除失败,系统出错!",2);
			}
		});
		layer.close(index);
	});
	//clickradio();
}
var update;
function deleteProcessSoft(processId){
	
	update = layer.open({
		title : ' 修改指标',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		shadeClose: true,
		area : ['1000px', '400px'],
		content : '<%=path%>/kpi/show/kpisbyid?id='+processId,
		end : function(){
			//window.layer.close(window.update);
			window.layer.close(window.update);
			//searchfb();
			changedate();
			//关闭页面
		}
	});
	
}

var update12;
function bianjigongshi(processId){
	
	update = layer.open({
		title : ' 编辑公式',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		shadeClose: true,
		area : ['1000px', '400px'],
		content : '<%=path%>/kpi/show/formulabyZhibiaoji?id='+processId,
		end : function(){
			//window.layer.close(window.update);
			window.layer.close(window.update12);
			//searchfb();
			changedate();
			//关闭页面
		}
	});
	
}

//查询
function searchfb(){
	thisGrid.jqGrid('setGridParam',{url:'<%=path%>/kpi/kpi/getAllBusinessModel',page : 1}).trigger("reloadGrid");
}

function searchfbA(){
	thisGrid.jqGrid('setGridParam',{url:'<%=path%>/kpi/kpi/getAllZhibiaoByMoban?id='+$("#id").val(),page : 1}).trigger("reloadGrid");
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
			 ary.push(checkName[i].value);
		  }

	}
	
	 if(!flag){
		  //alert("请至少选择一项业务模板");
		  parent.$.showMsg("请选择一项业务模板", 2);
		  return;
		 }
	if(ary.length>1){
		 parent.$.showMsg("只能选择一项模板", 2);
		 return;
	}else{
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

function clickradio(){
	//col_clear();
	loadInfo();
	
	changedate();
	
	
}

function clickradio1(){
	loadInfo1();
}

/***
 * 保存指标集
 */
function savebusiness(){
	var canUse1=$("#canUse1").val();// 产品类型
	var canUse2=$("#canUse2").val();//分类类型
	var canUse5=$("#canUse5").val();//模板名称
	var addProcessName1=$("#addProcessName1").val();
	
	/* canUse3 = canUse1.replace(/^\s+|\s+$/g,"");
	if(canUse3.trim()==""){
		parent.$.showMsg("请选择产品名称!", 2);
		return;
	} */
	tips = layer.msg("正在保存，请稍后...", {icon : 16,time : 0, shade: [0.1]});
	$.post('<%=path%>/kpi/kpi/savezhibiaoJI',{
		'fenleiId':canUse5,
		'name':addProcessName1
	},function(result){
		if(result.result=='success'){
			parent.$.showMsg("保存成功!", 1, function(){
				layer.close(tips);
				closeAddProcessLayer1();
				searchfbA();
			});
		}else if(result.result=='error'){
			parent.$.showMsg("保存失败,系统出错!", 2,function(){
				layer.close(tips);
			});
		}else if(result.result=='repeat'){
			parent.$.showMsg("指标集名称重复!", 2,function(){
				layer.close(tips);
			});
		}
	});
}




function savehcy(){
	var id=$("#id").val();
	// alert(id);
	var addProcessName1=$("#addProcessName1").val();
	tips = layer.msg("正在保存，请稍后...", {icon : 16,time : 0, shade: [0.1]});
	$.post('<%=path%>/kpi/kpi/savezhibiaoJI_de',{
		'id':id,
		'name':addProcessName1
	},function(result){
		if(result.result=='success'){
			parent.$.showMsg("保存成功!", 1, function(){
				layer.close(tips);
				closeAddProcessLayer1();
				searchfbA();
			});
		}else if(result.result=='error'){
			parent.$.showMsg("保存失败,系统出错!", 2,function(){
				layer.close(tips);
			});
		}else if(result.result=='repeat'){
			parent.$.showMsg("指标集名称重复!", 2,function(){
				layer.close(tips);
			});
		}
	});
}


function changedate(){
	//alert(1);
	var productId = $("input[name='name_']:checked").val();// 产品id
	var selectData=$("#canUse").val();
	// alert(selectData);
<%-- 	thisGrid.jqGrid('setGridParam',{url:'<%=path%>/kpi/kpi/getAllBusinessModel_c?productId='+encodeURIComponent(encodeURIComponent(productId))+'&selectData='+encodeURIComponent(encodeURIComponent(selectData)),page : 1}).trigger("reloadGrid");
 --%><%-- thisGrid.jqGrid('setGridParam',{url:'<%=path%>/sendmessage/forward/showorganizationByChose?orgName='+encodeURIComponent(encodeURIComponent(orgname)), page : 1}).trigger("reloadGrid");
 --%>
}
	





</script>
</head>
<body>
   	<div class="nodeB"><h4><img src="<%=path%>/style/image/ico_table_01.png"/>模板：</h4>
   		<table id="nodeTable" class="table_query" border="0" cellpadding="0" cellspacing="0" rules="all">
  				<tr id="beginTr">
   					<td align="center" width="90%">模板名称</td>
			    </tr>
			 <c:forEach items="${processInfoTmp}" var="upNode" varStatus="status">
				<tr>
						<td align="left">
							<input id="id" name="id" type="hidden" value="${upNode.id}"/>
							<input id="processName" name="name" type=text" value="${upNode.name}" style="width:400px"/>
						</td>
				</tr>
			</c:forEach>
		
   		</table>
   	</div>
   	
   	<div class="nodeA">
		<table class="table_query"  cellpadding="0" cellspacing="0" rules="all">
			<tr>
				<td style="border:0;">
					<input type="button" value="保存"  onclick="saveNode();" align="right" class="acssbtn"/>
				</td>
			</tr>
		</table>
	</div>
	
	<input type="hidden" id="processTmpId" name="processTmpId" value="${requestScope.processTmpId}"/>
	
	
	<div id="mainDiv" style="padding-top: 10px">
	<div style="float: right;padding-right: 20px;">
			<div style="margin-bottom:15px;">
					<!-- <label><input id="fuck" name="name_" type="radio" class="bcssbtn" value="1" onclick="clickradio();"/>DCM</label>
					<label><input name="name_" type="radio" class="bcssbtn" value="2" onclick="clickradio();"/>SDR</label>  -->
				
				
				<span>
					<!-- <input maxlength="12"  id="processName" name="processName" type="hidden" class="dfinput_fb" style="width:140px;height:24px;"/>&emsp;<br/>
				
					
					<b style="font-size: 15px">业务类型:</b>
					<select id="canUse" name="canUse" class="select_staff" style="width:125px;" onchange="changedate();">
						    <option value="">财务报告</option>
							<option value="200100">重大信息</option>
							<option value="200102">信息变更</option>
							<option value="200103">会议信息</option>
							<option value="200104">增进机构</option>
							<option value="200105">其他</option>
					</select>&emsp;
					 
					<input class="bcssbtn" type="button" onclick="searchfb();" value="查询" style="padding: 2px 6px 2px 6px"/>
					
					<input class="bcssbtn" id="addNode" type="button" value="添加节点" onclick="addNode();" style="padding: 2px 6px 2px 6px"/>
					
					<input class="bcssbtn" id="editNode" type="button" onclick="gotoUpdateNode1();" value="修改节点" style="padding: 2px 6px 2px 6px"/>
					
					<input class="bcssbtn" id="importModel" type="button" onclick="gotoUpdateNode2();" value="导入模板" style="padding: 2px 6px 2px 6px;"/> -->
					
					<input id="addProcess" class="bcssbtn" type="button" onclick="gotoAddProcess1();" value="添加指标集" style="padding: 2px 6px 2px 6px;"/> 
				</span>
			</div>
		<table id="processTable"></table>
		<div id="gridPager"></div>
	</div>
</div>



<div id="addProcessDiv" style="display:none;">
	<table class="table_query" border="0" cellpadding="0" cellspacing="0" rules="all" style="margin-top: 15px">
		<tr>
			<td align="left" style="border:0;"><h4>节点名称:</h4></td>
			<td align="left" style="border:0;">
				<input maxlength="12"  id="addProcessName" name="nodeName" type="text" class="dfinput_fb" style="width:140px;height:24px;"/>
			</td>
			
			<td align="left" style="border:0;">
				<input class="acssbtn" type="button" style="padding: 2px 6px 2px 6px;" onclick="savehcy();" value="提交" />
			</td>
			
		</tr>
	   
	</table>
</div>

<div id="addProcessDiv1" style="display:none;width: 320px;height: 100px;">
	<table class="table_query" border="0" cellpadding="0" cellspacing="0" rules="all" style="margin-top: 15px">
		<!-- <tr>
			<td align="left" style="border:0;"><h4>产品类型:</h4></td>
			<td align="left" style="border:0;">
				<select id="canUse1" name="canUse1" class="select_staff" onchange="loadInfo1();" >
						    <option value="0" selected="selected">产品类型</option> 
						    <option value="1">DCM</option>
							<option value="2">SDR</option>
				</select>
			</td>
		</tr>
		
		<tr>
			<td align="left" style="border:0;"><h4>模板类型:</h4></td>
			<td align="left" style="border:0;">
				<select id="canUse2" name="canUse2" class="select_staff"  onchange="changedate5();">
				</select>&emsp;
			</td>
		</tr>
		
		<tr>
			<td align="left" style="border:0;"><h4>模板名称:</h4></td>
			<td align="left" style="border:0;">
				<select id="canUse5" name="canUse5" class="select_staff">
				</select>&emsp;
			</td>
		</tr> -->
		
		
		<tr>
			<td align="left" style="border:0;"><h4>指标集名称:</h4></td>
			<td align="left" style="border:0;">
				<input  id="addProcessName1" name="businessName" type="text" class="dfinput_fb" style="width:170px;height:22px;"/>
			</td>
		</tr>
	
	<tr>
		<td align="left" style="border:0;">
				<input class="acssbtn" type="button" style="padding: 2px 6px 2px 6px;" onclick="savehcy();" value="提交" />
			</td>
	</tr>
	</table>
</div>
	
	
	
	
</body>
</html>
