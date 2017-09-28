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
<script type="text/javascript">
var tips;





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
	    var $tr = $(obj).parents("tr"); 
	    if ($tr.index() != 1) { 
	      $tr.fadeOut().fadeIn(); 
	      $tr.prev().before($tr); 
	    } 
}


function down(obj){
	 var $down = $(".2"); 
	var len = $down.length; 
	 var $tr = $(obj).parents("tr"); 
	    if ($tr.index() != len - 1) { 
	      $tr.fadeOut().fadeIn(); 
	      $tr.next().after($tr); 
	    } 
}

 function delNodeTr(obj,id){//点击税务的删除按钮,删除一条税务信息
	//var id = $("input[mes='id']").val();
	//alert(id);
	
	layer.confirm("确认删除!", {icon: 3, title:"提示"}, function(index){
		tips = layer.msg("正在删除,请稍后...",{icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/kpi/del/kpi',
		{
			'id': id
		},
		function(result){
			if(result.result=='success'){
				layer.close(tips);
				$.showMsg("删除成功!",1);
				//init();
			    window.location.href="<%=path%>/kpi/show/kpisbyid?id="+$("#zhibiaoji_id").val();
			}else{
				layer.close(tips);
				$.showMsg("删除失败,系统出错!",2);
			}
		});
		layer.close(index);
	});
	
	
	
	<%-- $.ajax({
        url: "<%=path%>/supervisory/asyn/deleteNodeAfter?id="+id,
        type: "GET",
        success: function(data){
            if(data.code==3){
            	$(obj).parent().parent().remove();
				layer.close(tips);
				parent.$.showMsg("删除成功!", 1);
				closeFrame();
			}else if(data.code==2){
				layer.close(tips);
				parent.$.showMsg("删除失败,系统出错!", 2);
			}else if(data.code==1){
				layer.close(tips);
				parent.$.showMsg("已存在协同关系，请重建关系后再删除", 2);
			}
        },
        error: function(res){
            parent.$.showMsg("亲您的网络不给力哦~", 2);
        }
    }); --%>
	
	
} 
 
 
 function changeznname(obj,id){
	// alert($(obj).val());
	 $.post('<%=path%>/kpi/updatezzname',{
			'znname':$(obj).val(),"id":id
		},function(result){
			 if(result.result=='success'){
				parent.$.showMsg("修改成功!", 1, function(){
					layer.close(tips);
					closeAddProcessLayer();
				});
			}else if(result.result=='error'){
				parent.$.showMsg("修改失败,系统出错!", 2,function(){
					layer.close(tips);
				});
			}else if(result.result=='repeat'){
				//$("#hh1").val("");
				searchfb();
				parent.$.showMsg("修改失败,指标名称已存在!", 2,function(){
					layer.close(tips);
				});
			} 
		});
 }
 
 
 function changezid(obj,id){
		// alert($(obj).val());
		 $.post('<%=path%>/kpi/updatezid',{
				'zid':$(obj).val(),"id":id
			},function(result){
				 if(result.result=='success'){
					parent.$.showMsg("保存成功!", 1, function(){
						layer.close(tips);
						closeAddProcessLayer();
					});
				}else if(result.result=='error'){
					parent.$.showMsg("保存失败,系统出错!", 2,function(){
						layer.close(tips);
					});
				}else if(result.result=='repeat'){
					parent.$.showMsg("保存失败,指标标识已存在!", 2,function(){
						//$("#hh2").val("");
						searchfb();
						layer.close(tips);
					});
				} 
			});
	 }
 
 
  function searchfb(){
		<%--  $.post('<%=path%>/kpi/searchResult',{
				'znname':$("#SearchName").val()
			},function(result){
				
			}); --%>
		 //alert($("#SearchName").val());
		 window.location.href="<%=path%>/kpi/searchResult?znname="+encodeURIComponent(encodeURIComponent($("#SearchName").val()))+"&zhibiaoji_id="+encodeURIComponent(encodeURIComponent($("#zhibiaoji_id").val()));
	 }
 
 
 
 function changeremark(obj,id){
		// alert($(obj).val());
		 $.post('<%=path%>/kpi/updateremark',{
				'remark':$(obj).val(),"id":id
			},function(result){
				 if(result.result=='success'){
					parent.$.showMsg("保存成功!", 1, function(){
						layer.close(tips);
						closeAddProcessLayer();
					});
				}else if(result.result=='error'){
					parent.$.showMsg("保存失败,系统出错!", 2,function(){
						layer.close(tips);
					});
				}else if(result.result=='repeat'){
					parent.$.showMsg("业务名称已存在!", 2,function(){
						layer.close(tips);
					});
				} 
			});
	 }

 
function init(){
	$.ajax({
        url: "<%=path%>/kpi/show/kpisbyid?id="+$("#zhibiaoji_id").val(),
        type: "GET"
    }); 
	
}

function initData(){
	var nodes =[];
	var nodeNameInputs = $(" input[mes='nodeName']");
	var roldIdInputs = $(" input[mes='roldId']");
	var processId = $("#processTmpId").val();
	var id = $("input[mes='id']");
	$.each(nodeNameInputs,function(index,item){
		var nodeObj = new Object();
		nodeObj.nodeName = $(item).val();
		nodeObj.roleId = $(roldIdInputs[index]).val();
		nodeObj.processTmpId = processId;
		nodeObj.id = $(id[index]).val();
		nodeObj.orderSeq = index*1000;
		nodes.push(nodeObj);
	});
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

function saveNode(){
	var nodes = initData();
	if(nodes.length==0){
		parent.$.showMsg("至少存在一个节点!", 2);
		return;
	}
	tips = layer.msg("正在保存...", {icon : 16,time : 0, shade: [0.1]});
	$.ajax({
                url: "<%=path%>/supervisory/asyn/saveNodeAfter",
                type: "POST",
                contentType : 'application/json;charset=utf-8',
                dataType:"json",
                data: JSON.stringify(nodes),
                success: function(data){
                    if(data.code){
						layer.close(tips);
						parent.$.showMsg("保存成功!", 1);
						 location.reload();
						closeFrame();
					}else{
						layer.close(tips);
						parent.$.showMsg("保存失败,系统出错!", 2);
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

function closeFrame11(){
	window.parent.layer.close(window.parent.update11);
}



var update11;
function orhelp(processId,nodeId){
	update11 = layer.open({
		title : ' 节点',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		shadeClose: true,
		area : ['400px', '400px'],
		content : '<%=path%>/business/forward/gotoHelpTogether?processTmpId='+processId+'&nodeId='+nodeId,
		end : function(){	
			location.reload();
			//closeFrame11();
			//$("#hcy").colorbox.close();
		}
	});
	
}

var addProcessLayer1;
function gotoAddProcess1(){
	addProcessLayer1= layer.open({
		title : '添加指标',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 1,
		shadeClose: true,
		area : ['400px', '200px'],
		content : $("#addProcessDiv1"),
		end : function(){
			$("#zhibiaoname").val("");
			$("#zhibiaoid").val("");
			//$("#canUse1").val("产品类型");
			//searchfb();
			//changedate();
			
			window.location.href="<%=path%>/kpi/show/kpisbyid?id="+$("#zhibiaoji_id").val();
		}
	});
}


function savebusiness(){
	var zhibiaoid=$("#zhibiaoid").val();// 产品类型
	var zhibiaoname=$("#zhibiaoname").val();//分类类型
	var zhibiaoji_id=$("#zhibiaoji_id").val();
	//alert(zhibiaoji_id);
	if(zhibiaoname.trim()==""){
		parent.$.showMsg("请填写指标名称", 2);
		return;
	}
	if(zhibiaoid.trim()==""){
		parent.$.showMsg("请填写指标标识", 2);
		return;
	}
	tips = layer.msg("正在保存，请稍后...", {icon : 16,time : 0, shade: [0.1]});
	$.post('<%=path%>/kpi/kpi/saveZhiboji_hcy',{
		'zid':zhibiaoid,
		'znname':zhibiaoname,
		'zhibiaoji_id':zhibiaoji_id
	},function(result){
		if(result.result=='success'){
			parent.$.showMsg("保存成功!", 1, function(){
				layer.close(tips);
				closeAddProcessLayer1();
			});
		}else if(result.result=='error'){
			parent.$.showMsg("保存失败,系统出错!", 2,function(){
				layer.close(tips);
			});
		}else if(result.result=='repeat'){
			parent.$.showMsg("指标名称或指标标识重复!", 2,function(){
				layer.close(tips);
			});
		}
	});
}
function closeAddProcessLayer1(){
	window.layer.close(window.addProcessLayer1);
}

</script>
</head>
<body>
   	<div class="nodeB"><h4><img src="<%=path%>/style/image/ico_table_01.png"/>业务列表：</h4>
   	<div></div>
   	
   <span style="font-size: 15px">${requestScope.name}:&nbsp;&nbsp;</span><input placeholder="请输入想输入的指标" id="SearchName" name="companySearchName" maxlength="20" type="text" class="dfinput_fb" style="width:180px;height:24px;"/>&nbsp;&nbsp;
   	<input class="bcssbtn" type="button" style="padding: 2px 6px 2px 6px" onclick="searchfb();" value="查询" style="height:30px;margin-left:100px;" />
   	<input class="bcssbtn" type="button" style="padding: 2px 6px 2px 6px" onclick="gotoAddProcess1();" value="添加指标" style="height:30px;margin-left:100px;" />
   	
   	<input class="bcssbtn" type="button" style="padding: 2px 6px 2px 6px" onclick="" value="调整排序" style="height:30px;margin-left:100px;" />
   		
   		<table id="nodeTable" class="table_query" border="0" cellpadding="0" cellspacing="0" rules="all">
  				<tr id="beginTr">
   				<td align="center">序号</td>
				<td align="center" >指标名称</td>
				<td align="center" >指标标识</td>
				<td align="center" >周期</td>
				<td align="center" >备注</td>
				<td align="center" >操作</td>
			</tr>
			<c:forEach items="${kpis}" var="upNode" varStatus="status">
				<tr>
						
						<td align="left">${status.index+1}
							<input id="id"  value="${status.index+1}" type="hidden" />
						</td>
						<td align="left">
							<input class="2" id="hh1" mes="znname" type="text" value="${upNode.znname}" onblur="changeznname(this,${upNode.id});"/>
						</td>
						
						<td align="left">
							<input class="2" id="hh2" mes="zid" type="text" value="${upNode.zid}" onblur="changezid(this,${upNode.id})"/>
						</td>
						
						<td align="left">
							<input class="2" mes="time" type="text" value="${upNode.time}"/>
						</td>
						
						<td align="left">
							<input class="2" mes="remark" type="text" value="${upNode.remark}" onblur="changeremark(this,${upNode.id});"/>
						</td>
						
						<td align="center">
							<input type="button" value="上移" onclick="up(this);" style="height:23px;font-size:12px;text-align: center;padding: 0px 0px 0px 0px" class="bcssbtn"/>
							<input type="button" value="下移" onclick="down(this);" style="height:23px;font-size:12px;text-align: center;padding: 0px 0px 0px 0px" class="bcssbtn"/>
							<input type="button" value="删除" onclick="delNodeTr(this,${upNode.id});" style="height:23px;font-size:12px;text-align: center;padding: 0px 0px 0px 0px" class="bcssbtn"/>
						<%-- <c:if test="${upNode.orhelp!=1}">
							<input type="button" id="hcy" value="协同" onclick="orhelp(${upNode.processTmpId},${upNode.id});" style="height:23px;font-size:12px;text-align: center;padding: 0px 0px 0px 0px" class="bcssbtn"/>
						</c:if> --%>
						</td>
					</tr>
			</c:forEach>
   		</table>
   	</div>
   	
   	<!-- <div class="nodeA">
		<table class="table_query"  cellpadding="0" cellspacing="0" rules="all">
			<tr>
				<td style="border:0;">
					<input type="button" value="重建关系"  onclick="saveNode();" align="right" class="acssbtn" style="padding: 2px 6px 2px 6px" style="height:30px;font-size:15px;text-align: center;padding-top:0px; padding-bottom:0px"/>
				</td>
			</tr>
		</table>
	</div> -->
	
	<div id="addProcessDiv1" style="display:none;width: 320px;height: 100px;">
	<table class="table_query" border="0" cellpadding="0" cellspacing="0" rules="all" style="margin-top: 15px">
		<tr>
			<!-- <td align="left" style="border:0;"><h4>业务类型:</h4></td>
			<td align="left" style="border:0;">
				<select id="canUse1" name="canUse1" class="select_staff" onchange="loadInfo1();" >
						    <option value="0" selected="selected">产品类型</option> 
						    <option value="1">DCM</option>
							<option value="2">SDR</option>
				</select>
			</td> -->
			<%-- <td>
			   <input name="zhibiaoji_id" name="zhibiaojiji" value="${requestScope.zhibiaoji_id}" />
			</td> --%>
		</tr>
		
		<tr>
			<!-- <td align="left" style="border:0;"><h4>模板类型:</h4></td>
			<td align="left" style="border:0;">
				<select id="canUse2" name="canUse2" class="select_staff"  onchange="changedate();">
				</select>&emsp;
			</td> -->
		</tr>
		
		<tr>
			<td align="left" style="border:0;"><h4>指标名称:</h4></td>
			<td align="left" style="border:0;">
				<input  id="zhibiaoid" name="businessName" type="text" class="dfinput_fb" style="width:170px;height:22px;"/>
			</td>
		</tr>
		
		<tr>
			<td align="left" style="border:0;"><h4>指标标识:</h4></td>
			<td align="left" style="border:0;">
				<input  id="zhibiaoname" name="businessName" type="text" class="dfinput_fb" style="width:170px;height:22px;"/>
			</td>
		</tr>
	
	<tr>
		<td align="left" style="border:0;">
				<input class="acssbtn" type="button" style="padding: 2px 6px 2px 6px;" onclick="savebusiness();" value="提交" />
			</td>
	</tr>
	</table>
</div>
	<div>
	<input id="zhibiaoji_id" name="zhibiaojiji" type="hidden" value="${zhibiaoji_id}" />
	</div>
	
</body>
</html>
