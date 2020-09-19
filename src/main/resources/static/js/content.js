var $parentNode = window.parent.document;

$.ajaxSetup({cache:false});
function $childNode(name) {
    return window.frames[name]
}

// tooltips
$('.tooltip-demo').tooltip({
    selector: "[data-toggle=tooltip]",
    container: "body"
});

// 使用animation.css修改Bootstrap Modal
$('.modal').appendTo("body");

$("[data-toggle=popover]").popover();

//折叠ibox
$('.collapse-link').click(function () {
    var ibox = $(this).closest('div.ibox');
    var button = $(this).find('i');
    var content = ibox.find('div.ibox-content');
    content.slideToggle(200);
    button.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
    ibox.toggleClass('').toggleClass('border-bottom');
    setTimeout(function () {
        ibox.resize();
        ibox.find('[id^=map-]').resize();
    }, 50);
});

//关闭ibox
$('.close-link').click(function () {
    var content = $(this).closest('div.ibox');
    content.remove();
});

//判断当前页面是否在iframe中
//if (top == this) {
//    var gohome = '<div class="gohome"><a class="animated bounceInUp" href="index.html?v=4.0" title="返回首页"><i class="fa fa-home"></i></a></div>';
//    $('body').append(gohome);
//}

//animation.css
function animationHover(element, animation) {
    element = $(element);
    element.hover(
        function () {
            element.addClass('animated ' + animation);
        },
        function () {
            //动画完成之前移除class
            window.setTimeout(function () {
                element.removeClass('animated ' + animation);
            }, 2000);
        });
}

//拖动面板
function WinMove() {
    var element = "[class*=col]";
    var handle = ".ibox-title";
    var connect = "[class*=col]";
    $(element).sortable({
            handle: handle,
            connectWith: connect,
            tolerance: 'pointer',
            forcePlaceholderSize: true,
            opacity: 0.8,
        })
        .disableSelection();
};


//编辑器新增的ajax上传图片函数
function sendFile(files, editor, $editable) {
    var size = files[0].size;
    if((size / 1024 / 1024) > 2) {
        alert("图片大小不能超过2M...");
        return false;
    }
    console.log("size="+size);
    var formData = new FormData();
    formData.append("file", files[0]);
    $.ajax({
        data : formData,
        type : "POST",
        url : "/common/sysFile/upload",    // 图片上传出来的url，返回的是图片上传后的路径，http格式
        cache : false,
        contentType : false,
        processData : false,
        dataType : "json",
        success: function(data) {//data是返回的hash,key之类的值，key是定义的文件名
            $('.summernote').summernote('insertImage',data.fileName);
        },
        error:function(){
            alert("上传失败");
        }
    });
}

//转换日期格式(时间戳转换为datetime格式)
function changeDateFormat(cellval) {
    var dateVal = cellval + "";
    if (cellval != null) {
        var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(")/", ""), 10));
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();

        var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();

        return date.getFullYear() + "-" + month + "-" + currentDate + " " + hours + ":" + minutes + ":" + seconds;
    }
}

function getCatName(catId) {
    var name = '';
    $.ajax({
        type : "get",
        url : "/simple/category/getName?id=" + catId,
        cache : true,
        contentType : false,
        processData : false,
        dataType : "json",
        async: false,
        success: function(data) {
            name = data['name'];
        },
        error:function(data){
            console.error(data);
            name = catId;
        }
    });
    return name;
}

function select(idTag, nameTag, idValue, nameValue) {
    $("#"+idTag).val(idValue);
    $("#"+nameTag).val(nameValue);
    this.layer.close(this.layer.index);
}

$.fn.categorySelector = function () {
    this.attr('type', "hidden");
    var idTag = this.attr('id'), nameTag = '_cat_';
    var showInput = $("<input class=\"form-control\" id='"+nameTag+"' type=\"text\">");
    showInput.insertAfter(this);
    var initNameValue = this.attr('data');
    if (initNameValue) {
        showInput.val(initNameValue);
    }
    var html = '<div class="wrapper wrapper-content " style="display: none;" id="categorySelectortDiv">' +
        '<div class="col-sm-12">' +
        '<div class="ibox">' +
        '<div class="ibox-body">' +
        '<div class="fixed-table-toolbar">' +
        '<div class="columns pull-left">' +
        '</div>' +
        // '<div class="columns pull-right">' +
        // '<button class="btn btn-success" onclick="reLoad()">查询</button>' +
        // '</div>' +
        // '<div class="columns pull-right col-md-2 nopadding">' +
        // '<input id="searchName" type="text" class="form-control" placeholder="">' +
        // '</div>' +
        '</div>' +
        '<table id="categorySelectorTable" data-mobile-responsive="true">' +
        '</table>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</div>';
    $(html).insertAfter('body');
    $('#categorySelectorTable')
        .bootstrapTreeTable(
            {
                id: 'id',
                code: 'id',
                parentCode: 'parentId',
                type: "GET", // 请求数据的ajax类型
                url: '/simple/category/list', // 请求数据的ajax的url
                ajaxParams: {sort:'order_no'}, // 请求数据的ajax的data属性
                expandColumn: '1',// 在哪一列上面显示展开按钮
                striped: true, // 是否各行渐变色
                bordered: true, // 是否显示边框
                expandAll: false, // 是否全部展开
                columns : [
                    {
                        field : 'id',
                        title : '类目ID'
                    },
                    {
                        field : 'name',
                        title : '类目名称'
                    },
                    {
                        title : '操作',
                        field : 'id',
                        align : 'center',
                        formatter : function(item, index) {
                            var e = '<a class="btn btn-primary btn-sm" href="javascript:void(0)" title="选择"' +
                                ' onclick="select(\''+idTag+'\', \''+nameTag+'\', \''+item.id+'\', \''+item.name+'\')">选择</a> ';
                            return e;
                        }
                    } ]
            });
    showInput.click(function() {
        layer.open({
            type : 1,
            title : '选择',
            maxmin : true,
            shadeClose : false, // 点击遮罩关闭层
            area : [ '800px', '450px' ],
            content : $("#categorySelectortDiv")
        });
    });
};

$.fn.shopSelector = function () {
    this.attr('type', "hidden");
    var idTag = this.attr('id'), nameTag = '_shop_';
    var showInput = $("<input class=\"form-control\" id='"+nameTag+"' type=\"text\">");
    showInput.insertAfter(this);
    var initNameValue = this.attr('data');
    if (initNameValue) {
        showInput.val(initNameValue);
    }
    var html = '<div class="wrapper wrapper-content " style="display: none;" id="shopSelectortDiv">' +
        '<div class="col-sm-12">' +
        '<div class="ibox">' +
        '<div class="ibox-body">' +
        '<div class="fixed-table-toolbar">' +
        '<div class="columns pull-left">' +
        '</div>' +
        // '<div class="columns pull-right">' +
        // '<button class="btn btn-success" onclick="reLoad()">查询</button>' +
        // '</div>' +
        // '<div class="columns pull-right col-md-2 nopadding">' +
        // '<input id="searchName" type="text" class="form-control" placeholder="">' +
        // '</div>' +
        '</div>' +
        '<table id="shopSelectorTable" data-mobile-responsive="true">' +
        '</table>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</div>';
    $(html).insertAfter('body');
    $('#shopSelectorTable')
        .bootstrapTable(
            {
                method : 'get', // 服务器数据的请求方式 get or post
                url : "/simple/shop/list", // 服务器数据的加载地址
                iconSize : 'outline',
                striped : true, // 设置为true会有隔行变色效果
                dataType : "json", // 服务器返回的数据类型
                pagination : true, // 设置为true会在底部显示分页条
                singleSelect : false, // 设置为true将禁止多选
                pageSize : 10, // 如果设置了分页，每页数据条数
                pageNumber : 1, // 如果设置了分布，首页页码
                showColumns : false, // 是否显示内容下拉框（选择显示的列）
                sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
                queryParams : function(params) {
                    return {
                        limit: params.limit,
                        offset:params.offset
                    };
                },
                columns : [
                    {
                        field : 'id',
                        title : '主键'
                    },
                    {
                        field : 'name',
                        title : '店铺名称'
                    },
                    {
                        title : '操作',
                        field : 'id',
                        align : 'center',
                        formatter : function(value, item, index) {
                            var e = '<a class="btn btn-primary btn-sm" href="javascript:void(0)" title="选择"' +
                                ' onclick="select(\''+idTag+'\', \''+nameTag+'\', \''+item.id+'\', \''+item.name+'\')">选择</a> ';
                            return e;
                        }
                    } ]
            });
    showInput.click(function() {
        layer.open({
            type : 1,
            title : '选择',
            maxmin : true,
            shadeClose : false, // 点击遮罩关闭层
            area : [ '800px', '450px' ],
            content : $("#shopSelectortDiv")
        });
    });
};

//保留2位小数，如：2，还会保留2 不会补0
function toDecimal2NoZero(x) {
    var f = Math.round(x * 100) / 100;
    var s = f.toString();
    return s;
}

function initArea() {
    var provinceId = $("#provinceId").val();
    $.ajax({
        type : "GET",
        url : "/simple/areaUsed/select",
        data : {parentId: 0, level: 1},
        success : function(data) {
            var str="<option value=''>--请选择省--</option>";
            for(var i=0;i<data.length; i++){
                if(provinceId && provinceId == data[i].id){
                    str += '<option value="'+data[i].id+'" selected="selected">'+data[i].name+'</option>';
                }else{
                    str += '<option value="'+data[i].id+'">'+data[i].name+'</option>';
                }

            }
            if(document.getElementById("province") != null){
                document.getElementById("province").innerHTML=str;
            }
            provinceChange($("#provinceId").val(), $("#cityId").val());
            cityChange($("#cityId").val(), $("#areaId").val())
        }
    });

    $("#province").change(function(){
        var ck = $("#province").val();
        provinceChange(ck);
    });
    function provinceChange(ck, cityId){
        if (ck==""){
            if(document.getElementById("city") != null){
                document.getElementById("city").innerHTML="<option value='' selected>--请选择市--</option>";
            }
        }else {
            $.ajax({
                type : "GET",
                url : "/simple/areaUsed/select",
                data : {parentId: ck, level: 2},
                success : function(data) {
                    var str="<option value=''>--请选择市--</option>";
                    for(var i=0;i<data.length; i++){
                        if(cityId && cityId == data[i].id){
                            str += '<option value="'+data[i].id+'" selected="selected">'+data[i].name+'</option>';
                        }else{
                            str += '<option value="'+data[i].id+'">'+data[i].name+'</option>';
                        }
                    }
                    if(document.getElementById("city") != null){
                        document.getElementById("city").innerHTML=str;
                    }
                }
            });
        }
        /* 改变省,县必为空*/
        if(document.getElementById("area") != null){
            document.getElementById("area").innerHTML="<option value='' selected>--请选择县--</option>";
        }
    }
    $("#city").change(function(){
        var ck = $("#city").val();
        cityChange(ck)
    });

    function cityChange(ck, areaId){
        if (ck==""){
            if(document.getElementById("area") != null){
                document.getElementById("area").innerHTML="<option value='' selected>--请选择县--</option>";
            }
        }else {
            $.ajax({
                type : "GET",
                url : "/simple/areaUsed/select",
                data : {parentId: ck, level: 3},
                success : function(data) {
                    var str="<option value=''>--请选择县--</option>";
                    for(var i=0;i<data.length; i++){
                        if(areaId && areaId == data[i].id){
                            str += '<option value="'+data[i].id+'" selected="selected">'+data[i].name+'</option>';
                        }else{
                            str += '<option value="'+data[i].id+'">'+data[i].name+'</option>';
                        }
                    }
                    if(document.getElementById("area") != null){
                        document.getElementById("area").innerHTML=str;
                    }
                }
            });
        }
    }
}

function initArea2() {
    var provinceId = $("#provinceId").val();
    function provinceChange(ck, cityId){
        if (ck==""){
            if(document.getElementById("city") != null){
                document.getElementById("city").innerHTML="<option value='' selected>--请选择市--</option>";
            }
        }else {
            $.ajax({
                type : "GET",
                url : "/simple/area/select",
                data : {parentId: ck, level: 2},
                success : function(data) {
                    var str="<option value=''>--请选择市--</option>";
                    for(var i=0;i<data.length; i++){
                        if(cityId && cityId == data[i].id){
                            str += '<option value="'+data[i].id+'" selected="selected">'+data[i].areaname+'</option>';
                        }else{
                            str += '<option value="'+data[i].id+'">'+data[i].areaname+'</option>';
                        }
                    }
                    if(document.getElementById("city") != null){
                        document.getElementById("city").innerHTML=str;
                    }
                }
            });
        }
        /* 改变省,县必为空*/
        if(document.getElementById("area") != null){
            document.getElementById("area").innerHTML="<option value='' selected>--请选择县--</option>";
        }
    }

    $.ajax({
        type : "GET",
        url : "/simple/area/select",
        data : {parentId: 0, level: 1},
        success : function(data) {
            var str="<option value=''>--请选择省--</option>";
            for(var i=0;i<data.length; i++){
                if(provinceId && provinceId == data[i].id){
                    str += '<option value="'+data[i].id+'" selected="selected">'+data[i].areaname+'</option>';
                }else{
                    str += '<option value="'+data[i].id+'">'+data[i].areaname+'</option>';
                }

            }
            if(document.getElementById("province") != null){
                document.getElementById("province").innerHTML=str;
            }
            provinceChange($("#provinceId").val(), $("#cityId").val());
            cityChange($("#cityId").val(), $("#areaId").val())
        }
    });
    $("#province").change(function(){
        var ck = $("#province").val();
        provinceChange(ck);
    });
    $("#city").change(function(){
        var ck = $("#city").val();
        cityChange(ck)
    });

    function cityChange(ck, areaId){
        if (ck==""){
            if(document.getElementById("area") != null){
                document.getElementById("area").innerHTML="<option value='' selected>--请选择县--</option>";
            }
        }else {
            $.ajax({
                type : "GET",
                url : "/simple/area/select",
                data : {parentId: ck, level: 3},
                success : function(data) {
                    var str="<option value=''>--请选择县--</option>";
                    for(var i=0;i<data.length; i++){
                        if(areaId && areaId == data[i].id){
                            str += '<option value="'+data[i].id+'" selected="selected">'+data[i].areaname+'</option>';
                        }else{
                            str += '<option value="'+data[i].id+'">'+data[i].areaname+'</option>';
                        }
                    }
                    if(document.getElementById("area") != null){
                        document.getElementById("area").innerHTML=str;
                    }
                }
            });
        }
    }
}

Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};


