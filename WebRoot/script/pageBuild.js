var padding = '';
var def_css = '';
var nextPage = '';
var gotoPage = '';
var maxShowNum  = 0;
var beforePage = '';
var pageNavigat = ''; 
function pageBuild(pageIndex,totalPage){
	pageInit();
	if(pageIndex > totalPage) return;
	pageCheck(pageIndex,totalPage);
	if(maxShowNum > totalPage) maxShowNum = totalPage;
	if(pageIndex < maxShowNum || (pageIndex == maxShowNum && maxShowNum == totalPage)){
		for(var i = 1;i <= maxShowNum;i ++){
			pageNavigat = pageDetail(i,pageIndex,pageNavigat);
			if(i == maxShowNum && maxShowNum != totalPage){
				pageNavigat += '<span ' + padding + '>...</span>'
			}
		}
	}
	if(pageIndex > maxShowNum || (pageIndex == maxShowNum && maxShowNum != totalPage)){
		for(var i = 1;i <= 2;i ++){
			pageNavigat += '<a onclick="pageChange(' + i + ')"' + def_css + '>' + i + '</a>'
		}
		pageNavigat += '<span ' + padding + '>...</span>'
		if(pageIndex < totalPage - 2){
			for(var i = (pageIndex - 2);i <= (pageIndex + 2);i ++){
				pageNavigat = pageDetail(i,pageIndex,pageNavigat);
			}
			pageNavigat += '<span ' + padding + '>...</span>'
		}
		if(pageIndex >= totalPage - 2){
			for(var i = (pageIndex - 4 + (totalPage -  pageIndex));i <= totalPage;i ++){
				pageNavigat = pageDetail(i,pageIndex,pageNavigat);
			}
		}
	}
	//拼装分页条
	$("#pageInfo").html('<div>' + beforePage + pageNavigat + nextPage + '<span style="padding: 5px 15px 5px 15px;">共<span id="countPage">' + totalPage+'</span>页</span>' + gotoPage+ "</div>");
}

function pageInit(){
	pageNavigat = '';
	maxShowNum  = 7;
	padding     = 'style="height: 19px;margin:2px 2px 2px 2px;padding: 5px 15px 5px 15px;font-size: 14px;"';
	def_css     = 'style="height: 19px;margin:2px 2px 2px 2px;padding: 5px 15px 5px 15px;cursor: pointer;background-color: #F5F3F3;border-radius: 3px;font-size: 14px;"';
	beforePage  = '<a id="beforePage" onclick="pageChange(\'before\')"' + def_css + '>上一页</a>';
	nextPage    = '<a id="nextPage" onclick="pageChange(\'after\')"' + def_css + '>下一页</a>';
	gotoPage    = '<span>到第<input id="gotoPage" style="width:50px" type="text"></input>页<a onclick="pageChange(\'goto\')"' + def_css + '>确定</a></span>'
}

function pageCheck(pageIndex,totalPage){
	var re = /^[0-9]*[1-9][0-9]*$/;
	if(!re.test(pageIndex)) pageIndex = 0;
	if(!re.test(totalPage)) totalPage = 0;
	if(pageIndex <= 1) beforePage = '<a id="beforePage" onclick="pageWarn(\'before\')"' + def_css + '>上一页</a>';
	if(pageIndex >= totalPage) nextPage = '<a id="nextPage" onclick="pageWarn(\'after\')"' + def_css + '>下一页</a>';
}

function pageDetail(i,pageIndex,pageNavigat){
	if(i == pageIndex){
		pageNavigat += '<span id="nowPage"' + padding + '>' + i + '</span>'
	}else{
		pageNavigat += '<a onclick="pageChange(' + i + ')"' + def_css + '>' + i + '</a>'
	}
	return pageNavigat;
}

function pageWarn(where){
	if("before" == where) alert("已经是第一页了!");
	if("after"  == where) alert("已经是最后一页了!");
}

function pageChange(where){
	var page = -1;
	var countPage = $("#countPage").html();
	if("before" == where) page = parseInt($("#nowPage").html()) - 1;
	if("after"  == where) page = parseInt($("#nowPage").html()) + 1;
	if("goto"   == where) page = parseInt($("#gotoPage").val());
	if(page == -1) page = where;
	if(page > countPage) page = countPage;
	//pageBuild(page,countPage);
	pageImp(page,countPage);
}