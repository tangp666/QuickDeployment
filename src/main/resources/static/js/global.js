/**
 * @descript
 * 		初始化 
 */
$(function() {


	
	$(document).off("click.radio").on("click.radio",".radio",function(){
		$(this).addClass("slt").siblings().removeClass("slt");
	})
	
	//导航展开收起按钮
	$('.slideBtn').off().on('click',function(){
		$(this).parents('.bodyer').stop().toggleClass('sm');
		$(".rightManager").removeAttr("style");
		initlayout();

		if($(this).parents('.bodyer').hasClass('sm')){
			$('.bg_nav.selt').next('.twoLevelNav').hide();
		}else{
			$('.bg_nav.selt').next('.twoLevelNav').show();
		}
	});
	
	//缩小后的左侧导航鼠标经过效果
	$(document).off("mouseover.leftNav").on("mouseover.leftNav",".sm .leftNav",function(){
		$('.bodyer').addClass("positionMenu");
		$('.bg_nav.selt').next('.twoLevelNav').show();
	});
	$(document).off("mouseout.leftNav").on("mouseout.leftNav",".sm .leftNav",function(){
		$('.bodyer').removeClass("positionMenu");
		if($(this).parents('.bodyer').hasClass('sm')){
			$('.bg_nav.selt').next('.twoLevelNav').hide();
		}
	});
	
});

/**
 * 初始框架布局
 * 
 */
 function initlayout () {
	$('[placeholder]').placeholder&&$('[placeholder]').placeholder();
	var H_HEADER = 57;
	var	h_window = $(window).height();
	var	w_window = $(window).width();
	var	h_footer = $('.footer').height();
	var	h_body = $('body').height();
	var	w_body = $('body').width();
	h_body = h_body<h_window?h_window:h_body;
	w_body = w_body<w_window?w_body:w_window;
	$('.normal').css("height",h_window-H_HEADER-1);
	$('.bodyer.normal').css("min-height",h_window-H_HEADER-1);
	$('.leftNav').css("height",h_window-H_HEADER-100);
	$('#rightManager').css("height",h_window-H_HEADER-1-h_footer);
	$('.scrollH').css("height",h_window-H_HEADER-1);
	$('.J_waterfullWrap').css("height",h_window-H_HEADER-61-37-110);
	$('.J_collectBox').css("height",h_window-58-20);
	$('.J_collectBox').css("width",'100%');
	$('.topShadow').css("height",h_window);
	$('.J_collectBox').css("min-width",1366);
	$('.allNavscroll').css("height",h_window-H_HEADER-1-20-90);
	$('.allNavfull').css("width",w_window-10);
	$('.wp62').css("width",$('.cartTop').width()-368);
	//头部导航自适应
	//var topNav = w_window-645;
	var topNav = w_window-754;
	if(topNav<initW){
		$('.topNav .topNavText a').width(((topNav-36)/$('.topNavText').length)-42);
		$('.topNav .topNavText a').css("min-width",30);
		$('.topNav').css("width",topNav);
	}else{
		$('.topNav .topNavText a').css("width",'auto');
		$('.topNav').css("width",topNav);
	}
	$('.topNav').css("min-width",736);
    var rightManagerWidth = $('.rightManager').width();
    var leftNavLeftWidth = $('.leftTree').outerWidth();
    var paddingRightWidth = 55;
    var rightManagerHeight = $('.rightManager').height();
	if($('.rightManager').width()>=1850){
        paddingRightWidth = 35;
		$('.bottomActive').removeClass().addClass('box bottomActive width_1');
	}else if($('.rightManager').width()<1850 && $('.rightManager').width()>=1691){
        paddingRightWidth = 55;
		$('.bottomActive').removeClass().addClass('box bottomActive width_2');
	}else if($('.rightManager').width()<1691 && $('.rightManager').width()>=1530){
		$('.bottomActive').removeClass().addClass('box bottomActive width_3');
	}else if($('.rightManager').width()<1530 && $('.rightManager').width()>=1388){
		$('.bottomActive').removeClass().addClass('box bottomActive width_4');
	}else if($('.rightManager').width()<1388 && $('.rightManager').width()>=1370){
		$('.bottomActive').removeClass().addClass('box bottomActive width_5');
	}else if($('.rightManager').width()<1370 && $('.rightManager').width()>=1296){
		$('.bottomActive').removeClass().addClass('box bottomActive width_6');
	}else if($('.rightManager').width()<1296 && $('.rightManager').width()>=1228){
		$('.bottomActive').removeClass().addClass('box bottomActive width_7');
	}else if($('.rightManager').width()<1228 && $('.rightManager').width()>=1154){
		$('.bottomActive').removeClass().addClass('box bottomActive width_8');
	}else if($('.rightManager').width()<1154){
		$('.bottomActive').removeClass().addClass('box bottomActive width_8');
	}
    $(".gridBar").width(rightManagerWidth - leftNavLeftWidth - paddingRightWidth);
	$(".leftTree").height(rightManagerHeight - 62);
    $(".innerBox").height(rightManagerHeight - 82);
}