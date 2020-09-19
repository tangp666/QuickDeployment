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
                var e = '<a class="btn btn-primary btn-sm '
                    + s_edit_h
                    + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
                    + item.id
                    + '\')">编辑</a> ';
                var p = '<a class="btn btn-primary btn-sm '
                    + s_add_h
                    + '" href="#" mce_href="#" title="添加下级" onclick="add(\''
                    + item.id
                    + '\')">添加下级</a> ';
                var d = '<a class="btn btn-warning btn-sm '
                    + s_remove_h
                    + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
                    + item.id
                    + '\')">删除</a> ';
                return e + d + p;
            }
        }];
    getMenuTree('menu/menuList', {
        sort:'order_num'
    }, columns);
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
            debugger
            var indexNum = 0;
            var arr = getLevelOneNodes(data.rows);
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
                treeId: "id",	// 每个节点的独立编号
                parentId: "parentId",	// 父节点的treeId
                treeField: "name",	// icon显示在哪一列
                treeLevelField: "nodeLevel",	// 设置级别对应的字段
                treeParentLevelField: "nodeLevel",	// 父节点的级别对应的字段
                // treeMaxLevel: 3,	// 最深级别设定，不设定则视为无穷
                getChild:function(index, treeId){
                    var pId=tableData.RetValue[index][treeId];
                    var children=[];
                    $.each(data,function(i,v){
                        if(v.parentId == tableData.RetValue[index][treeId]){
                            var type = "budget";
                            var nodeLevel = v.nodeLevel;
                            if(!v.nodeLevel){
                                var pNode = getNodeById(data, v.parentId);
                                nodeLevel = pNode.nodeLevel + 1;
                                var nodeIndex = getNodeIndexById(data, v.id);
                                data[nodeIndex].nodeLevel = nodeLevel;
                                type = "dept";
                            }

                            nodeLevel = nodeLevel -1;

                            var name = v.name;
                            if(!name){
                                name = v.deptName;
                            }
                            var nodeType = 2;
                            if(v.nodeType){
                                nodeType = v.nodeType;
                            }

                            if(hasChild(data, v)){
                                nodeType = 1;
                            }else{
                                nodeType = 2;
                            }

                            children.unshift({
                                name: name,
                                nodeLevel: nodeLevel,
                                menuId: v.id,
                                code: v.code,
                                delFlag: v.delFlag,
                                nodeType: nodeType,
                                url: v.url,
                                perms: v.perms,
                                icon: v.icon,
                                type: v.type,
                                remainMoney: v.remainMoney,
                                freezeMoney: v.freezeMoney,
                                executedMoney: v.executedMoney,
                                budgeExecutedMoney: v.budgeExecutedMoney,
                                parentId: v.parentId,
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
                            var nodeLevel = 0;
                            if(datas[i].nodeLevel){
                                nodeLevel = datas[i].nodeLevel;
                            }else{
                                datas[i].nodeLevel = nodeLevel;
                            }

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
                                nodeLevel: nodeLevel,
                                nodeType: nodeType,
                                menuId: datas[i].menuId,
                                delFlag: datas[i].delFlag,
                                code: datas[i].code,
                                parentId: datas[i].parentId,
                                url: datas[i].url,
                                perms: datas[i].perms,
                                icon: datas[i].icon,
                                type: datas[i].type,
                                remainMoney: datas[i].remainMoney,
                                freezeMoney: datas[i].freezeMoney,
                                executedMoney: datas[i].executedMoney,
                                budgeExecutedMoney: datas[i].budgeExecutedMoney,
                            });
                        }
                    }
                }
                return res;
            }

            function getNodeById(datas, menuId) {
                var res = {};
                if(datas){
                    for(var i = 0; i < datas.length; i++){
                        if(menuId == datas[i].menuId){
                            res = datas[i];
                            break;
                        }
                    }
                }
                return res;
            }

            function getNodeIndexById(datas, menuId) {
                var res = 0;
                if(datas){
                    for(var i = 0; i < datas.length; i++){
                        if(menuId == datas[i].menuId){
                            res = i;
                            break;
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

//刷新
function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}

//新增
function add() {
    layer.open({
        type : 2,
        title : '增加',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : prefix + '/add' // iframe的url
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
        content : prefix + '/edit/' + id // iframe的url
    });
}
//删除
function remove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn : [ '确定', '取消' ]
    }, function() {
        $.ajax({
            url : prefix+"/remove",
            type : "post",
            data : {
                'id' : id
            },
            success : function(r) {
                if (r.code==0) {
                    layer.msg(r.msg);
                    reLoad();
                }else{
                    layer.msg(r.msg);
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
            ids[i] = row['id'];
        });
        $.ajax({
            type : 'POST',
            data : {
                "ids" : ids
            },
            url : prefix + '/batchRemove',
            success : function(r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function() {

    });
}

//查看
function view(id) {
    layer.open({
        type: 2,
        title: '查看',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: [ '800px', '520px' ],
        content: prefix + '/view/' + id // iframe的url
    });
}

