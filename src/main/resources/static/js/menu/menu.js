//页面加载执行
$(function() {
    load();

    $('#exampleTable th div').each(function () {
        $(this).attr("title", $(this).text());
        $(this).css("cursor", 'pointer');
    });
});


function load() {
    var columns = [
        {
            title: '序号',
            field: 'id',
            visible: false,
            align: 'center',
            valign: 'center',
            width: '5%'
        },
        {
            title: '名称',
            valign: 'center',
            field: 'name',
            width: '20%'
        },
        {
            title: '图标',
            field: 'icon',
            align: 'center',
            valign: 'center',
            width : '5%',
            formatter: function (value, item, index) {
                return item.icon == null ? '' : '<i class="' + item.icon + ' fa-lg"></i>';
            }
        },
        {
            title: '类型',
            field: 'type',
            align: 'center',
            valign: 'center',
            width : '10%',
            formatter: function (value, item, index) {
                if (item.type === 0) {
                    return '<span class="label label-primary">目录</span>';
                }
                if (item.type === 1) {
                    return '<span class="label label-success">菜单</span>';
                }
                if (item.type === 2) {
                    return '<span class="label label-warning">按钮</span>';
                }
            }
        },
        {
            title: '地址',
            align: 'center',
            valign: 'center',
            width : '20%',
            field: 'url'
        },
        {
            title: '权限标识',
            align: 'center',
            valign: 'center',
            width : '20%',
            field: 'perms'
        },
        {
            title: '操作',
            field: 'id',
            align: 'center',
            valign: 'center',
            formatter: function (value, item, index) {
                var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
                    + item.menuId + '\')">编辑</a> ';
                var p = '<a class="btn btn-primary btn-sm '+ s_add_h + '" href="#" mce_href="#" title="添加下级" onclick="add(\''
                    + item.menuId + '\')">添加下级</a> ';
                var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
                    + item.menuId + '\')">删除</a> ';
                return e + d + p;
            }
        }];
    getMenuTree('/menu/menuList', {
        sort:'order_num'
    }, columns);
}

// 刷新
function reLoad() {
    load();
}

function getMenuTree(url, params, columns, ele) {
    if(!ele){
        ele = "exampleTable";
    }
    $("#"+ele).bootstrapTable("destroy");

    columns[0] = {
        title: '序号',
        field: 'id',
        align: 'center',
        valign: 'center',
        width: '5%',
        formatter: function (value, row, index) {
            return row.indexNum;
        }
    };
    $.ajax({
        url: url,
        type: "get",
        data: params,
        success: function (data) {
            //查询结果不正确时
            if(data.code != 0){
                layer.error(data.message);
                return;
            }
            var indexNum = 0;
            var arr = getLevelOneNodes(data.data.rows);
            var tableData={
                RetValue:arr,
                Tag:1
            };
            var options={
                sidePagination:"server",
                pagination:false,
                height:$(".bootstrap-table-box").height(),
                totalField:'Tag',
                dataField:"RetValue",
                undefinedText: '',
                clickToSelect:true,
                singleSelect:true,
                checkboxHeader:false,
                columns:columns,
                treeView: true,		// 开启表格树功能
                treeId: "menuId",	// 每个节点的独立编号
                parentId: "parentId",	// 父节点的treeId
                treeField: "name",	// icon显示在哪一列
                getChild:function(index, treeId){
                    var children=[];
                    $.each(data.data.rows,function(i,v){
                        if(v.parentId == tableData.RetValue[index][treeId]){
                            var name = v.name;
                            var nodeType = 2;
                            if(v.nodeType){
                                nodeType = v.nodeType;
                            }
                            if(hasChild(data.data.rows, v)){
                                nodeType = 1;
                            }else{
                                nodeType = 2;
                            }

                            children.unshift({
                                name: name,
                                menuId: v.id,
                                nodeType: nodeType,
                                url: v.url,
                                perms: v.perms,
                                icon: v.icon,
                                type: v.type,
                                parentId: v.parentId
                            });
                        }
                    });
                    return children;
                },
                onPageChange:function(number, size){
                }
            };

            $("#"+ele).bootstrapTable(options);
            $("#"+ele).bootstrapTable('load',tableData);
            function hasChild(datas, item) {
                var res = false;
                if(datas){
                    for(var i = 0; i < datas.length; i++){
                        if(item.id == datas[i].parentId){
                            res = true;
                            break;
                        }
                    }
                }
                return res;
            }

            function getLevelOneNodes(datas) {
                var res = [];
                if(datas){
                    for(var i = 0; i < datas.length; i++){
                        if(datas[i].parentId == 0){
                            var nodeType;
                            if(hasChild(datas, datas[i])){
                                nodeType = 1;
                            }else{
                                nodeType = 2;
                            }

                            indexNum++;
                            res.push({
                                indexNum: indexNum,
                                name: datas[i].name,
                                nodeType: nodeType,
                                menuId: datas[i].id,
                                parentId: datas[i].parentId,
                                url: datas[i].url,
                                perms: datas[i].perms,
                                icon: datas[i].icon,
                                type: datas[i].type,
                            });
                        }
                    }
                }
                return res;
            }
            $('#exampleTable th div').each(function () {
                $(this).attr("title", $(this).text());
                $(this).css("cursor", 'pointer');
            });
        }
    });
}

//新增
function add(id) {
    layer.open({
        type : 2,
        title : '增加',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : '/menu/add/'+id // iframe的url
    });
}
//修改
function edit(id) {
    layer.open({
        type : 2,
        title : '编辑',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : '/menu/edit/' + id // iframe的url
    });
}
//删除
function remove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn : [ '确定', '取消' ]
    }, function() {
        $.ajax({
            url : "/menu/delete",
            type : "post",
            data : {
                'id' : id
            },
            success : function(r) {
                if (r.code==3) {
                    layer.msg(r.message);
                    reLoad();
                }else{
                    layer.msg(r.message);
                }
            }
        });
    })
}

//批量删除
function batchRemove() {
    var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要删除的数据");
        return;
    }
    layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
        btn : [ '确定', '取消' ]
        // 按钮
    }, function() {
        var ids = new Array();
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.each(rows, function(i, row) {
            ids.push(row['id'])
        });
        $.ajax({
            type : 'POST',
            data : {
                "ids" : ids
            },
            url : 'menu/batchDelete',
            success : function(r) {
                if (r.code == 3) {
                    layer.msg(r.message);
                    reLoad();
                } else {
                    layer.msg(r.message);
                }
            }
        });
    }, function() {

    });
}

