$(function () {
    load();
    $('#exampleTable th div').each(function () {
        $(this).attr("title", $(this).text());
        $(this).css("cursor", 'pointer');
    });
});

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method : 'get', // 服务器数据的请求方式 get or post
                url: "/server/serverList", // 服务器数据的加载地址
                striped : true, // 设置为true会有隔行变色效果
                dataType : "json", // 服务器返回的数据类型
                pagination : true, // 设置为true会在底部显示分页条
                // queryParamsType : "limit",
                // //设置为limit则会发送符合RESTFull格式的参数
                singleSelect : false, // 设置为true将禁止多选
                iconSize : 'outline',
                toolbar : '#exampleToolbar',
                // contentType : "application/x-www-form-urlencoded",
                // //发送到服务器的数据编码类型
                pageSize : 10, // 如果设置了分页，每页数据条数
                pageNumber : 1, // 如果设置了分布，首页页码
                search : false, // 是否显示搜索框
                showColumns : false, // 是否显示内容下拉框（选择显示的列）
                showRefresh : false, // 是否显示内容下拉框（选择显示的列）
                sidePagination : "client", // 设置在哪里进行分页，可选值为"client" 或者 "server"
                queryParams : function(params) {
                    return {
                        //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                        limit: params.limit,
                        offset:params.offset,
                        serverName: $('#serverName').val(),
                        serverAddress: $("#serverAddress").val()
                    };
                },
                responseHandler: function (res) {
                    if(res.code == 0){
                        return {
                            data: res.data.data,
                            total: res.data.total
                        }
                    }else {
                        return {
                            data: [],
                            total: 0
                        }
                    }
                },
                columns: [
                    {
                        field : 'id', // 列字段名
                        title : '序号', // 列标题
                        width: '5%',
                        align: 'center',
                        valign: 'center',
                        formatter: function (value, row, index) {
                            //获取每页显示的数量
                            var pageSize=$('#exampleTable').bootstrapTable('getOptions').pageSize;
                            //获取当前是第几页
                            var pageNumber=$('#exampleTable').bootstrapTable('getOptions').pageNumber;
                            return pageSize * (pageNumber - 1)+ index+1;
                        }
                    },
                    {
                        field: 'serverName',
                        title: '服务器名称',
                        align: 'center',
                        valign: 'center',
                        width: '20%'
                    },
                    {
                        field: 'serverAddress',
                        title: '服务器地址',
                        align: 'center',
                        valign: 'center',
                        width: '15%'
                    },
                    {
                        field: 'serverProt',
                        title: '服务器端口',
                        align: 'center',
                        align: 'center',
                        valign: 'center',
                        width: '5%'
                    },
                    {
                        field: 'serverAccount',
                        title: '服务器账号',
                        align: 'center',
                        align: 'center',
                        valign: 'center',
                        width: '15%'
                    },
                    {
                        field: 'serverDesc',
                        title: '服务器描述',
                        align: 'center',
                        align: 'center',
                        valign: 'center',
                        width: '30%',
                        cellStyle: formatTableUnit
                    },
                    {
                        title : '操作',
                        field : 'id',
                        align : 'center',
                        valign: 'center',
                        width: '10%',
                        formatter : function(value, row, index) {
                            var e = '<a  class="btn btn-primary btn-sm" href="#" mce_href="#" title="编辑" onclick="edit(\''
                                + row.id
                                + '\')">编辑</a> ';
                            var d = '<a class="btn btn-warning btn-sm" href="#" title="删除"  mce_href="#" onclick="remove(\''
                                + row.id
                                + '\')">删除</a> ';
                            return e + d;
                        }
                    }]
            });
}

//控制项目介绍样式
function formatTableUnit(value, row, index) {
    return {
        css: {
            "white-space": 'nowrap',
            "text-overflow": 'ellipsis',/*当文本溢出时，溢出的部分隐藏，显示省略号*/
            "overflow": 'hidden',
            "max-width": "200px"
        }
    }
}

function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}

function add() {
    layer.open({
        type: 2,
        title: '增加',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '700px'],
        content: '/server/add' // iframe的url
    });
}
function edit(id) {
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '700px'],
        content: '/server/edit/' + id // iframe的url
    });
}
function remove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: "/server/remove",
            type: "post",
            data: {
                'id': id
            },
            success: function (r) {
                if (r.code == 3) {
                    layer.msg(r.message);
                    reLoad();
                } else {
                    layer.msg(r.message);
                }
            }
        });
    })
}
