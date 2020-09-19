$(function () {
    //一级导航点击事件
    $('.bg_nav').off().on('click', function(){
        var module = $(this).attr('data-value');
        if(module != null && module != ''){
            $(".J_iframe").attr('src', module);
        }

        if(!$(this).hasClass('selt')){
            //删除所有的selt
            $('.selt').removeClass('selt');

            $(this).addClass('selt');
            $(this).siblings('a').removeClass('selt');
            //关闭所有二级菜单
            closeAllposDiv();

            //展开下级菜单
            var length = this.children.length;
            for(var i = 0;i < length; i++){
                if(this.children[i].className .indexOf("pos_2") > 0){
                    this.children[i].style.display = 'block'
                }
            }
        }else{
            $('.selt').removeClass('selt');
            //关闭所有二级菜单
            closeAllposDiv();
        }
    });
    //关闭所有二级菜单
    function closeAllposDiv(){
        var length = $('.pos_2').length
        for(var i = 0;i < length; i++){
            if($('.pos_2')[i].className .indexOf("pos_2") > 0){
                $('.pos_2')[i].style.display = 'none'
                //如果选中则 取消选中
                if($('.pos_2')[i].className.indexOf('selt') > 0){
                    $('.pos_2')[i].className.replace(' selt','');
                }
            }
        }
    }

    //二级导航点击事件
    $('.pos_2>div').die().live('click', function(){
        //父节点不选中
        if($(this.parentNode.parentNode).hasClass('selt')){
            $(this.parentNode.parentNode).removeClass('selt');
        }
        //展开当前节点
        this.parentNode.style.display = 'block'
        var that = $(this);
        var e = arguments.callee.caller.arguments[0] || window.event;
        if(e.target.tagName != 'A' && e.target.tagName != 'DD'){
            that.addClass('selt');
        }
        var module = $(this).attr('data-value');
        if(module != null && module != ''){
            $(".J_iframe").attr('src', module);
        }

    });
});