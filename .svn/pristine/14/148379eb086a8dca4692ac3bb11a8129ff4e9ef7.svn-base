;(function(){
	//window.onload=function(){
		var download_mobil = document.getElementById("download_mobil");
		var actualTop = download_mobil.offsetTop+download_mobil.offsetHeight; //弹出层上边距
		var actualLeft = download_mobil.offsetLeft; //弹出层左边距
		
		var pp = document.getElementById("div_mobil");
		download_mobil.onmouseover = function(e){// 弹出图层
		   pp.style.display="block";
		   pp.style.top =actualTop-2+"px";
		   pp.style.left =actualLeft-200+"px";
		};
		download_mobil.onmouseout = function(e){// 隐藏图层
		   pp.style.display="none";
		};
		pp.onmouseover = function(e){// 指向弹出层自身时也显示
		   this.style.display="block";
		};
		pp.onmouseout = function(e){// 离开弹出层自身时也隐藏
		   this.style.display="none";
		};
	//}

})();

