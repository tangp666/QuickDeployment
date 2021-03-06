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
                url: "/user/userList", // 服务器数据的加载地址
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
                        username: $('#searchName').val(),
                        roleId: $('#searchRoleId option select').val()
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
                // "server"
                // queryParams : queryParams,
                // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
                // queryParamsType = 'limit' ,返回参数必须包含
                // limit, offset, search, sort, order 否则, 需要包含:
                // pageSize, pageNumber, searchText, sortName,
                // sortOrder.
                // 返回false将会终止请求
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
                        field: 'username',
                        title: '姓名',
                        align: 'center',
                        valign: 'center'
                    },
                    {
                        field: 'status',
                        title: '状态',
                        align: 'center',
                        align: 'center',
                        valign: 'center',
                        formatter: function (value, row, index) {
                            if (value == '0') {
                                return '<span class="label label-danger">禁用</span>';
                            } else if (value == '1') {
                                return '<span class="label label-primary">正常</span>';
                            }
                        }
                    },
                    {
                        title : '操作',
                        field : 'id',
                        align : 'center',
                        valign: 'center',
                        width: '25%',
                        formatter : function(value, row, index) {
                            var e = '<a  class="btn btn-primary btn-sm " href="#" mce_href="#" title="编辑" onclick="edit(\''
                                + row.id
                                + '\')">编辑</a> ';
                            var d = '<a class="btn btn-warning btn-sm" href="#" title="删除"  mce_href="#" onclick="remove(\''
                                + row.id
                                + '\')">删除</a> ';
                            var f = '<a class="btn btn-success btn-sm" href="#" title="重置密码"  mce_href="#" onclick="resetPwd(\''
                                + row.id
                                + '\')">重置密码</a> ';
                            return e + d + f;
                        }
                    }]
            });
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
        area: ['800px', '520px'],
        content: '/user/add' // iframe的url
    });
}
function edit(id) {
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: '/user/edit/' + id // iframe的url
    });
}
function remove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: "/user/remove",
            type: "post",
            data: {
                'id': id
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    })
}

function resetPwd(id) {
    //判定当前用户是否是系统管理员角色
    $.ajax({
        type : "POST",
        url : "/role/checkRoles/"+ id,
        async : false,
        success : function(data) {
            if (data.code == 0) {
                layer.open({
                    type : 2,
                    title : '修改密码',
                    maxmin : true,
                    shadeClose : false, // 点击遮罩关闭层
                    area : [ '400px', '260px' ],
                    content : '/user/resetPwd/' + id // iframe的url
                });
            } else {
                layer.msg("您不是系统管理员,只能修改重置自己的密码")
            }
        }
    });
}
function batchRemove() {
    var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要删除的数据");
        return;
    }
    layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        var ids = new Array();
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.each(rows, function (i, row) {
            ids[i] = row['id'];
        });
        $.ajax({
            type: 'POST',
            data: {
                "ids": ids
            },
            url: prefix + '/batchRemove',
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function () {

    });
}
