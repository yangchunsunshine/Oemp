<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<title>站内信</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/bootstrap/css/bootstrap.min.css"/>
<script type="text/javascript">
$(function(){
	getMessageList(1);
	//点击链接后改变链接
	$("#messageTable tbody").on('click','a',function(event){
		$(this).css("color","#551a8b");
	});
});

//刷新
//获取消息列表
function getMessageList(page){
	layer.msg("读取中...", {icon : 16,time : 0, shade: [0.1]});
	$.ajax({ 
		type:"POST", 
		url : "<%=path%>/supervisory/asyn/getMessageListForMng",
		data : {page:page},
		dataType : "json",
		success : function(rslt) {
			layer.closeAll();
			$("#messageTable tbody").empty();
			if(rslt.rows.length>0){
				$.each(rslt.rows,function(index,item){
					var pHtml='<tr><td class="msgData" data_id="'+item.id+'">'+(index+1)+'</td>';
					if(item.isread==0){
						pHtml+='<td><a href="javascript:void(0);" onclick="parent.MessageDispather('+item.id+",'"+item.path+"','"+item.tabname+"','"+(item.conditions==null?"":item.conditions)+"'"+');">'+item.message+'</a></td>';
					}else if(item.isread==1){
						pHtml+='<td><a href="javascript:void(0);" onclick="parent.MessageDispather('+item.id+",'"+item.path+"','"+item.tabname+"','"+(item.conditions==null?"":item.conditions)+"'"+');" style="color:#aaa">'+item.message+'</a></td>';
					}
					pHtml+='<td>'+item.stamp+'</td></tr>';
					$("#messageTable tbody").append(pHtml);
				});
				var pagiHtml = '<li><a href="javascript:void(0);" onclick="getMessageList(1);">&laquo;</a></li>';
				for(var i=0;i<rslt.total;i++){
					if(i==(page-1)){
						pagiHtml+='<li class="active"><a href="javascript:void(0);" onclick="getMessageList('+(i+1)+');">'+(i+1)+'</a></li>';
					}else{
						pagiHtml+='<li><a href="javascript:void(0);" onclick="getMessageList('+(i+1)+');">'+(i+1)+'</a></li>';
					}
				}
				pagiHtml+='<li><a href="javascript:void(0);" onclick="getMessageList('+rslt.total+');">&raquo;</a></li>';
				$(".pagination").html(pagiHtml);
			}else{
				$("#messageTable tbody").html('<tr><td colspan="3" style="color:red;">没有消息记录！</td></tr>');
			}
		} 
	});
}

//全部标记为已读
function setAllRead(){
	var tips = layer.msg('请求处理中...',{icon : 16,time : 0, shade: [0.1]});
	$.ajax({
		url:'<%=path%>/supervisory/asyn/setAllReadForMng',
		type:'post',
		success:function(data){
			layer.close(tips);
			var result=eval("("+data+")");
			parent.$.showMsg(result.message, 6);
			getMessageList(1);
		},
		error:function(jqXHR, textStatus, errorThrown ){
			parent.$.showMsg("亲!您的网络不给力哦~", 5)
		}
	});
}

//本页标记为已读
function setcurRead(){
	var ids="";
	$.each($(".msgData"),function(index,item){
		ids+=","+$(item).attr("data_id");
	});
	ids=ids.substring(1,ids.length);
	var curpage = parseInt($(".pagination .active a").html());
	var tips = layer.msg('请求处理中...', {icon : 16,time : 0, shade: [0.1]});
	$.ajax({
		url:'<%=path%>/supervisory/asyn/setcurReadForMng',
		data:{ids:ids},
		type:'post',
		success:function(data){
			layer.close(tips);
			var result=eval("("+data+")");
			parent.$.showMsg(result.message, 6);
			getMessageList(curpage);
		},
		error:function(jqXHR, textStatus, errorThrown ){
			parent.$.showMsg("亲!您的网络不给力哦~", 5)
		}
	});
}

//刷新本页
function refrashcurPage(){
	var curpage = parseInt($(".pagination .active a").html());
	getMessageList(curpage);
}
</script>
</head>
<body>
	<div class="page-header" contenteditable="true" style="margin: 30px 50px 0px 20px;padding-bottom:0px;border-bottom: 1px solid #ccc;">
		<h4 style="font-weight:bold;margin-bottom:5px;">消息通知列表</h4>
	</div>
	<div class="well" width="80%" style="margin:20px 50px 20px 20px;">
		<table id="messageTable" class="table table-bordered table-hover" style="align-bottom:0px;">
			<thead><tr>
				<th>序号</th>
				<th>标题</th>
				<th>时间</th>
			</tr></thead>
			<tbody></tbody>
		</table>
		<div width="100%">
			<div class="btn-toolbar" role="toolbar" style="float:left;margin-top: 5px;">
				<div class="btn-group">
					<button type="button" onclick="refrashcurPage();" class="btn btn-default">刷新</button>
					<button type="button" onclick="setAllRead();" class="btn btn-default">全部标记为已读</button>
			  		<button type="button" onclick="setcurRead();" class="btn btn-default">本页标记为已读</button>
			 	</div>
			</div>
			<ul class="pagination" style="float:right;"></ul>
		</div>
	</div>
</body>  
</html> 
