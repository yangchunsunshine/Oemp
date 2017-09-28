<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.wb.framework.commonUtil.PropertiesReader"%>
<%PropertiesReader reader = PropertiesReader.getInstance();%>
<%String url = reader.getValue("/com/wb/config/application", "ACC_BILL_URL");%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<title>新建客户</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script type="text/javascript" src="<%=path%>/script/powerAuth.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/select2.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/i18n/zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/select2/css/select2.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style_mnt.css"/>
<script type="text/javascript">
var countId = -1;//税务tr计数器
var fileCountId = -1;//文件tr计数器
var isAdmin = $.getCookie("isAdmin");
function gotoTab(tab){
	$("#menuDiv").find("li").removeClass("selected");
	$("#" + tab + "Li").addClass("selected");
	$("#detailDiv").find("table").hide();
	$("#taxTable").show();
	$("#Tax_reminder").show();
	$("#File_List").show();
	$("#" +　tab).show();
}

$(function(){
	$("#orgNum").mustNumber();
	$("#workTel").mustNumber();
	$("#tel").mustNumber();
	$("#mobile").mustNumber();
	$("#days").mustNumber();
	$("#emp").select2({
		language: "zh-CN"
	});
	if(isAdmin == "true" || isAdmin == true){
		$("#empTD").show();
	}else{
		$("#empTD").hide();
	}
});

function saveCusInfo(){//点击保存按钮,保存客户信息
	var saveCreate = $("#saveCreate").is(":checked");
	var orgNum = $("#orgNum").val();
	var orgName = $("#orgName").val();
	var days = $("#days").val();
	var mobile = $("#mobile").val();
	var contact = $("#contact").val();
	
	if(isAdmin && $("#emp").val() == 0){
		parent.$.showMsg("请选择客户主管!", 2, null, 3000);
		return;
	}
	if(orgNum==null||orgNum==""){
		$("#orgNum").focus();
		parent.$.showMsg("请填写客户编号!", 2, null, 3000);
		return;
	}
	if(orgName==null||orgName==""){
		$("#orgName").focus();
		parent.$.showMsg("请填写公司名称!", 2, null, 3000);
		return;	
	}
	//if(!isNaN(orgName)){
	//	$("#orgNum").focus();
	//	parent.$.showMsg("公司名称不可以为纯数字!", 2, null, 3000);
	//	return;	
	//}
	if(mobile==null||mobile==""){
		$("#mobile").focus();
		parent.$.showMsg("请填写手机号码!", 2, null, 4000);
		return;	
	}
	if ($.isMobile(mobile) == false) {
        parent.$.showMsg("手机号码格式有误!", 2, null, 4000);
		return;	
	}
	if(days==null||days==""){
		$("#days").val("0");
		return;
	}
	if(!isEmail()){
		return;
	}
	if(contact!=""){
		if(!isNaN(contact)){
			parent.$.showMsg("联系人不能为数字!", 2, null, 3000);
			return;
		}
	}
	if(fileCountId!=-1){
		var fileNum = fileCountId + 1;
		for(var i=0;i<fileNum;i++){
			var f = $("#file_"+i).val();
			var m = $("#comments_"+i).val();
			if(f==null||f==""||m==null||m==""){
				parent.$.showMsg("请检查所上传的附件信息是否有遗漏（文件说明必填）!", 2, null, 4000);
				return;
			}
		}
	}
	var tips;
	var options = {
		type : 'post',
		dataType : 'json',
		url:'<%=path%>/supervisory/asyn/createCustom',
		beforeSubmit : function() {
			tips = layer.msg("正在保存...", {icon : 16,time : 0, shade: [0.1]});
		},
		success : function(result) {
			if(result.code==1){
				if(saveCreate){
					var bookInfo = result.bookInfo;
					//var bookName = encodeURI(bookInfo[0].name,"UTF-8");
					var bookName = bookInfo[0].name;
					createBook(bookInfo[0].orgId,bookInfo[0].tel,bookName,bookInfo[0].createDate);
					parent.$.showMsg("保存成功!", 1, function(){
						layer.close(tips);
						closeFrame();
					},2000);
				}else{
					parent.$.showMsg("保存成功!", 1, function(){
						layer.close(tips);
						closeFrame();
					});
				}
			}else if(result.code==2){
				layer.close(tips);
				parent.$.showMsg("保存失败,客户编号重复!", 2, null, 3000);
			}else if(result.code==3){
				layer.close(tips);
				parent.$.showMsg("保存失败,公司名称重复!", 2, null, 3000);
			}else if(result.code==4){
				layer.close(tips);
				parent.$.showMsg("保存失败,客户数据生成失败!", 2, null, 3000);
			}else if(result.code==5){
				layer.close(tips);
				parent.$.showMsg("保存失败,税务提醒生成失败!", 2, null, 3000);
			}else if(result.code==6){
				layer.close(tips);
				parent.$.showMsg("保存失败,附件上传失败!", 2, null, 3000);
			}else if(result.code==7){
				layer.close(tips);
				parent.$.showMsg("保存失败,公司数据生成失败!", 2, null, 3000);
			}else if(result.code==8){
				layer.close(tips);
				parent.$.showMsg("保存失败,创建公司客户关系失败!", 2, null, 3000);
			}else{
				layer.close(tips);
				parent.$.showMsg("保存失败,系统出错!", 2, null, 3000);
			}
		},
		error : function(result) {
			layer.close(tips);
			parent.$.showMsg("亲!您的网络不给力哦~", 2);
		}
	};
	$('#cusInfoForm').ajaxSubmit(options);
}

function createBook(orgid,tel,name,createDate){
		var ACC_BILL_URL = '<%=url%>';
		var url = ACC_BILL_URL+"/financeCreateBook.jspx";
		var param = "{orgid:" + orgid + ", tel:" + tel + ", name:'" + name + "', createDate:" + createDate + "}";
		$.ajax({
			type : "get",
			timeout : 5000,
	        dataType : "json",
	       	data : {"param":param},
			url  : url
		});
	}

function closeFrame(){//点击关闭按钮,关闭当前页面,并刷新父级页面
	window.parent.thisGrid.trigger("reloadGrid");
	window.parent.layer.close(window.parent.addCus);
}

function addTaxTable(){//点击税务的添加按钮,添加一条税务信息
	countId ++;
	var taxKindText = $("#taxKind").find("option:selected").text();
	var taxKind = $('#taxKind').val();
	var repeortTypeText = $("#repeortType").find("option:selected").text();
	var repeortType = $('#repeortType').val();
	var days = $('#days').val();
	var taxPeopleText = $("#taxPeople").find("option:selected").text();
	var taxPeople = $('#taxPeople').val();
	var taxRow = "<tr>"+
		"<td>"+taxKindText+
			"<input type='hidden' name='taxList["+countId+"].taxKind' value='"+taxKind+"'>"+
			"<input type='hidden' name='taxList["+countId+"].taxName' value='"+taxKindText+"'>"+
		"</td>"+
		"<td>"+repeortTypeText+
			"<input type='hidden' name='taxList["+countId+"].repeortType' value='"+repeortType+"'>"+
		"</td>"+
		"<td>满期后"+days+"天"+
			"<input type='hidden' name='taxList["+countId+"].days' value='"+days+"'>"+
		"</td>"+
		"<td>"+taxPeopleText+
			"<input type='hidden' name='taxList["+countId+"].taxPeople' value='"+taxPeople+"'>"+
		"</td>"+
		"<td>"+
			"<input type='button' value='删除' name='deleteTr_"+countId+"' onclick=\"deleteTaxTr(this);\" class='bcssbtn' style='padding: 0px 0px 0px 0px'/>"+
		"</td>"+
	"</tr>";
	$("#Tax_reminder tr:last").after(taxRow);
}

function deleteTaxTr(k){//点击税务的删除按钮,删除一条税务信息
	$(k).parent().parent().remove();
}

function addFileTable(){//点击文件的添加按钮,添加一条文件信息
	fileCountId ++;
	var newRow = "<tr>"+
		"<td align='center'>"+(fileCountId+1)+"</td>"+
		"<td align='center'>"+
			"<input type='file' id='file_"+fileCountId+"' name='fileListInfo["+fileCountId+"].file' class='FileCss' onchange='fileInfoCheck($(this),this,"+fileCountId+");'/>"+
		"</td>"+
		"<td align='center'>"+
			"<input type='text' id='comments_"+fileCountId+"' name='fileListInfo["+fileCountId+"].comments' class='FileCss' />"+
		"</td>"+
	"</tr>";
	$("#File_List tr:last").after(newRow);
}

function deleteFileTable(){//点击文件的删除按钮,删除一条文件信息
	if(fileCountId>-1){
		fileCountId --;
		$("#File_List tr:not(:eq(0)):last").remove();
	}
}

function fileInfoCheck(_this,ele,fileCountId){//验证文件格式
	var filepath = $("input[name='fileListInfo["+fileCountId+"].file']").val();
	var extStart = filepath.lastIndexOf(".");
	var ext = filepath.substring(extStart, filepath.length).toUpperCase();
	if (ext != ".TXT" && ext != ".XLS" && ext != ".DOC" && ext != ".JPG" && ext != ".GIF"&& ext != ".PDF") {
		parent.$.showMsg("文件限于txt、xls、doc、jpg、gif、pdf格式", 2);
		_this.prop("value","");
		return;
	}
	var fileSize = (ele.files[0].size / 1024).toFixed(2);
	if(fileSize>500){
		parent.$.showMsg("文件大小限于500KB以内", 2);
		_this.prop("value","");
		return;
	}
}

//正则校验email合法性
function isEmail(){
	var email = $("#email").val();
	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
	if(!reg.test(email) && isEmpty(email) != ""){
		parent.$.showMsg("e-mail格式不合法,请校验!", 2);
		return false;
	}
	return true;
}

</script>
</head>
<body>
<div>
	<div id="menuDiv" class="tab_menu" >
		<ul class="TabClass">
			<li id="cusInfoLi" class="selected">
				<span onclick="gotoTab('cusInfo')">基本资料</span>
			</li>
			<li id="contactLi" >
				<span onclick="gotoTab('contact')">联系方式</span>
			</li>
			<li id="taxInfoLi" >
				<span onclick="gotoTab('taxInfo')">税务资料</span>
			</li>
			<li id="attachmentLi">
				<span onclick="gotoTab('attachment')">附件</span>	
			</li>
		</ul>
	</div>
	<form id="cusInfoForm" enctype="multipart/form-data" method="POST">
	<div id="detailDiv" class="tab_box">
		<table id="cusInfo" width="100%" border="0">
			<tr>
				<td width="80">公司编号：</td>
				<td colspan="3">
				<input name="orgNum" type="text" id="orgNum" maxlength="9" autocomplete="off" class="txt450" /> <span style="color: red">*</span>
				</td>
			</tr>
			<tr>
				<td>公司名称：</td>
				<td colspan="3">
				<input name="orgName" maxlength="20" type="text" id="orgName" autocomplete="off" class="txt450" /> <span style="color: red">*</span>
				</td>
			</tr>
			<tr>
				<td>公司简称：</td>
				<td>
				<input name="orgShortName" maxlength="20" type="text" id="orgShortName" class="txt170" autocomplete="off" />
				</td>	
				<td>行业性质：</td>
				<td>
				<input name="bussType" maxlength="20" type="text" id="bussType" class="txt170" autocomplete="off" />
				</td>
			</tr>
			<tr>
				<td>法人代表：</td>
				<td>
				<input name="rawPeople" maxlength="20" type="text" id="rawPeople" class="txt170" autocomplete="off" />
				</td>
				<td>手机号码：</td>
				<td>
				<input name="mobile" type="text" id="mobile" placeholder="填写准确的联系人电话" class="txt170" autocomplete="off" maxlength="11"/>
				</td>
			</tr>
			<tr>
				<td>营业执照号：</td>
				<td>
				<input name="bussLicence" maxlength="20" type="text" id="bussLicence" class="txt170" autocomplete="off" />
				</td>
				<td>组织机构代码：</td>
				<td>
				<input name="institutionNo" maxlength="20" type="text" id="institutionNo" class="txt170" autocomplete="off" />
				</td>
			</tr>
			<tr>
				<td>成立日期：</td>
				<td>
				<input name="createDate" type="text" id="createDate" onfocus="WdatePicker()" class="Wdate" />
				</td>
				<td>开始代账日期：</td>
				<td>
				<input name="startAcc" type="text" id="startAcc" onfocus="WdatePicker()" class="Wdate" />
				</td>
			</tr>
			<tr>
				<td>备注：</td>
				<td colspan="3">
				<textarea name="demo" id="demo"	width="420" class="txt450"></textarea>
				</td>
			</tr>
			<tr>
			<tr>
				<td colspan="2" id="empTD">
					<div><span style="margin-right: 23.6px">客户主管:</span>
						<select name="emp" id="emp" style="width: 100px;">
							<option value="0">请选择员工</option>
							<c:forEach items='${empList}' var='emp'>
								<option value="${emp.id}">${emp.name}</option>
							</c:forEach>
						</select>
					</div>
				</td>
				<td colspan="2">
				<input style="margin: 7px;" name="saveCreate" type="checkbox" id="saveCreate" checked="checked" />
				保存时同步创建会计账套
				</td>
			</tr>
		</table>
		<table id="contact" class="hide">
			<tr>
				<td width="70">联系人：</td>
				<td width="180">
				<input name="contact" type="text" id="contact" class="txt180" autocomplete="off" maxlength="10"/>
				</td>
				<td width="70">办公电话：</td>
				<td width="180">
				<input name="workTel" type="text" id="workTel" class="txt180" autocomplete="off" maxlength="12"/>
				</td>
			</tr>
			<tr>
				<td>座机号码：</td>
				<td>
				<input name="tel" type="text" id="tel" class="txt180"	autocomplete="off" maxlength="12"/>
				</td>
				<td>身份证号：</td>
				<td>
				<input name="cardNo" maxlength="18" type="text" id="cardNo" class="txt180" autocomplete="off" />
				</td>
			</tr>
			<tr>
				<td>传真号码：</td>
				<td>
				<input name="fax" type="text" maxlength="20" id="fax" class="txt180" autocomplete="off" />
				</td>
				<td>QQ:</td>
				<td>
				<input name="qq" type="text" id="qq" maxlength="20" class="txt180" autocomplete="off" />
				</td>
			</tr>
			<tr>
				<td>E-mail:</td>
				<td colspan="3">
				<input name="email" type="text" id="email" maxlength="30" class="txt450" autocomplete="off" onblur="isEmail();"/>
				</td>
			</tr>
			<tr>
				<td>其他：</td>
				<td colspan="3">
				<input name="orther" type="text" maxlength="20" id="orther" class="txt450" autocomplete="off" />
				</td>
			</tr>
			<tr>
				<td valign="top" style="padding:10px 0 0 0;">详细地址：</td>
				<td colspan="3">
				<textarea name="addr" id="addr" class="txt450"></textarea>
				</td>
			</tr>
		</table>
		<table id="taxInfo" class="hide">
			<tr>
				<td>纳税人类别：</td>
				<td>
				<select name="taxType" id="taxType" class="Taxes" style="width:163px">
					<option value="1">一般纳税人</option>
					<option value="2">小规模纳税人</option>
				</select>
				</td>
				<td>税率：</td>
				<td>
				<input name="taxRates" type="text" id="taxRates" autocomplete="off" class="txt160" style="width: 125px" maxlength="3" onkeypress="return IsNum(event,$(this));"/>%
				</td>
			</tr>
			<tr>
				<td>国税税务登记号：</td>
				<td>
				<input name="nationalTaxNo" maxlength="20" type="text" id="nationalTaxNo" autocomplete="off" class="txt160" />
				</td>
				<td>电脑编号：</td>
				<td>
				<input name="nationalTaxCpu" maxlength="20" type="text" id="nationalTaxCpu" autocomplete="off" class="txt140" />
				</td>
			</tr>
			<tr>
				<td>国税主管税务分局(科)：</td>
				<td>
				<input name="nationalTaxAddr" maxlength="20" type="text" id="nationalTaxAddr" autocomplete="off" class="txt160" />
				</td>
				<td>国税备注：</td>
				<td>
				<input name="nationalTaxDemo" maxlength="50" type="text" id="nationalTaxDemo" autocomplete="off" class="txt140" />
				</td>
			</tr>
			<tr>
				<td>地税税务登记号：</td>
				<td>
				<input name="landTaxNo" type="text" maxlength="20" id="landTaxNo" class="txt160" autocomplete="off" />
				</td>
				<td>电脑编号：</td>
				<td>
				<input name="landTaxNoCpu" type="text" maxlength="20" id="landTaxNoCpu" class="txt140" autocomplete="off" />
				</td>
			</tr>
			<tr>
				<td width="136" nowrap="nowrap">地税主管税务分局(科)：</td>
				<td>
				<input name="landTaxNoAddr" maxlength="20" type="text" id="landTaxNoAddr" class="txt160" autocomplete="off" />
				</td>
				<td width="70" nowrap="nowrap">地税备注：</td>
				<td>
				<input name="landTaxNoDemo" maxlength="50" type="text" id="landTaxNoDemo" class="txt140" autocomplete="off" />
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<p>
					<b>税务提醒：</b>
					</p>
					<table id="taxTable" width="97%" class="TaxReminder">
						<tr>
							<td width="200">税种</td>
							<td width="100">申报周期</td>
							<td width="150">到期日</td>
							<td width="140">报税人</td>
							<td width="50"></td>
						</tr>
						<tr>
							<td class="Td_Taxes">
							<select name="taxKind" id="taxKind" class="Taxes">
								<option value="1">增值税（一般纳税人）</option>
								<option value="2">增值税（小规模）</option>
								<option value="3">消费税</option>
								<option value="4">营业税</option>
								<option value="5">企业所得税</option>
								<option value="6">城市维护建设税</option>
								<option value="7">教育费附加税</option>
								<option value="8">个人所得税</option>
								<option value="9">房产税</option>
								<option value="10">资源税</option>
								<option value="11">社保费</option>
								<option value="12">印花税</option>
							</select>
							</td>
							<td class="Td_Period">
							<select name="repeortType" id="repeortType" class="Period">
								<option value="1">月报</option>
								<option value="2">季报</option>
								<option value="3">半年报</option>
								<option value="4">年报</option>
							</select>
							</td>
							<td class="Td_Deadline">
							期满后<input name="days" type="text" maxlength="3" id="days" value="0" class="Deadline"/>天
							</td>
							<td class="Td_Taxpeople">
							<select name="taxPeople" id="taxPeople" class="Period" style="width: 100px;">
								<c:forEach items='${empList}' var='emp'>
									<option value="${emp.id}" selected="selected">${emp.name}</option>
								</c:forEach>
							</select>
							</td>
							<td>
								<input id="editCus_addTax" type="button" value="添加" class="bcssbtn" style="padding: 0px 0px 0px 0px;display: none;" id="reset" onclick="addTaxTable();"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="5">
					<h3 class="H3_class">已添加税务提醒：</h3>
					<div class="wrapTaxTable">
						<table id="Tax_reminder" width="98%">
							<tr>
								<td>税种</td>
								<td>申报周期</td>
								<td>到期日</td>
								<td>报税人</td>
								<td>操作</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
		<table id="attachment" class="hide" width="100%" border="2">
			<tr>
				<td colspan="6">
					<span>支持txt、xls、doc、jpg、gif、pdf等文件格式,大小不超过500K</span>
					<input id="editCus_addAtta" type="button" value="添加" class="bcssbtn" style="padding: 0px 0px 0px 0px;display: none;" id="reset" onclick="addFileTable();"/>
					<input type="button" value="删除" class="bcssbtn" style="padding: 0px 0px 0px 0px" id="reset" onclick="deleteFileTable();"/>
				</td>
			</tr>
			<tr>
				<td colspan="7">
					<h3 class="H3_class">附件列表：</h3>
					<div class="wrapTaxTable" style="height: 280px">
						<table id="File_List" width="100%">
							<tr>
								<td width="10%" align="center">序号</td>
								<td width="40%" align="center">附件</td>
								<td width="50%" align="center">文件说明</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
	</div>
	</form>
	<div align="right" style="padding-right: 5px;margin-top: 30px">
		<p>
			<input name="txtTaxInfo" type="hidden" id="txtTaxInfo" /> 
			<input name="txtRecorder" type="hidden" id="txtRecorder" /> 
			<input name="saveCus"  type="submit" id="saveCus" class="bbtn" value="保存" style="display: inline;" onclick="saveCusInfo()" /> 
			<input name="closeTab" type="button" id="closeTab" class="abtn" style="display: inline;margin-right: 8px" value="关闭" onclick="closeFrame()"/>
		</p>
	</div>
</div>
</body>
</html>