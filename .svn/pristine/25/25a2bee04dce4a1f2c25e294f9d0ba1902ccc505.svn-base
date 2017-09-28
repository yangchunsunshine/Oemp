var tabpanel; //tab页对象
$(function(){
	//tabpanel动态宽高
	var main_right_height = $(window).height()-55;
	var main_rigth_width = $(window).width()-80;
	//声明tabpanel并设置首页
	tabpanel = new TabPanel({  
		renderTo:'main_right',  
		width:main_rigth_width,  
		height:main_right_height,   
		active : 0,
		border:'none',
		items : [
			{id:'index',title:'首页',html:'<iframe id="iframIndex" src="'+getRootPath()+'/supervisory/forward/gotoHome" width="100%" height="100%" frameborder="0"></iframe>',closable: false}
		]
	}); 
	
	//注册菜单选项单击事件，隐藏菜单、添加tab页
	$(".menu_list").live("click",function(){
		$(this).parents(".float_menu").hide();
		var url = $(this).attr("url");
		var data_option = $(this).attr("data_option")
		if(data_option){
			data_option =  eval("("+ data_option +")");
		}else{
			data_option = new Object();
		}
		var name = $(this).text();
		if($('#frame'+name).length>0) {
			var srcUrl = $('#frame' + name).attr("src");
			if(url != srcUrl) {
				$('#frame'+name).attr("src",url);
			}
		}
		tabpanel.addTab({
			id:name,
			title:name ,
			beforeClose:data_option.beforeClose,
			html:'<iframe id="frame'+name+'" src="'+url+'" width="100%" height="100%" frameborder="0"></iframe>',
			closable: true
		 });
	})

	//鼠标滑过左侧菜单事件
	$("#leftmenu li").hover(function(){
			var sub_ul = $(this).find("ul");
			var ul_overflow =$(window).height()- ($(this).offset().top + sub_ul.height())
			if(ul_overflow<0){
				sub_ul.css("top",ul_overflow);
			}
			$(this).find("ul").css("display","block")
		},
		function(){
			$(this).find("ul").css("display","none")
		})

	//伸缩菜单
	$(".rightx .img_in").live("click",function(){
		$(".ri").removeClass("rightx").addClass("rightn");
		$(".main_right").addClass("rightm_m");
		$("#leftmenu").addClass("content_left_m");
		$(".tabpanel_tab_content").width($(window).width())
		$(".tabpanel_content").width($(window).width())
	});
	$(".rightn .img_in").live("click",function(){
		$(".ri").removeClass("rightn").addClass("rightx");
		$(".main_right").removeClass("rightm_m");
		$("#leftmenu").removeClass("content_left_m");
		$(".tabpanel_tab_content").width($(window).width()-80)
		$(".tabpanel_content").width($(window).width()-80)
	});
	layout_init();	
});
$(window).resize( function() {	
	layout_init();
});
function layout_init(){
	/********** 自适应高度  ***********/
	$("#leftmenu").css("height",$(window).height()-55);
	$(".ri").css("height",$(window).height()-110);
}  
function getRootPath(){
	var curWwwPath=window.document.location.href;
	var pathName=window.document.location.pathname;
	var pos=curWwwPath.indexOf(pathName);
	var localhostPaht=curWwwPath.substring(0,pos);
	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	return(localhostPaht+projectName);
}