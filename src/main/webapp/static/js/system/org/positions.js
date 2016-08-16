$(function () {
    JY.Dict.setSelect("selectisValid", "isValid", 2, '全部');
    getbaseList();
    // loadOrgTree();
    //增加回车事件
    $("#baseForm").keydown(function (e) {
        keycode = e.which || e.keyCode;
        if (keycode == 13) {
            search();
        }
    });
    //form显示选择
    $("#authorityDiv button").bind('click', function () {
        $("#authorityDiv button").removeClass("btn-success");
        $(this).addClass("btn-success");
        var layer = $(this).val();
        $("#authorityDiv input[name$='layer']").val(layer);
        //重新加载资源
        var auth = $("#authorityDiv input[name$='auth']").val();
        if (auth == 'org') {
            var orgId = $("#baseForm input[name$='orgId']").val();
            JY.Ajax.doRequest("", jypath + '/backstage/org/role/orglistCompanyAuthorized', {
                id: orgId,
                layer: layer
            }, function (data) {
                $.fn.zTree.init($("#authorityTree"), {
                    check: {
                        enable: true,
                        chkDisabledInherit: true,
                        chkboxType: {"Y": "ps", "N": "s"}
                    }, data: {simpleData: {enable: true}}
                }, data.obj);
            });
        } else if (auth == 'role') {
            var roleId = $("#authorityDiv input[name$='roleId']").val();
            JY.Ajax.doRequest(null, jypath + '/backstage/org/role/listCompanyAuthorized', {
                id: roleId,
                layer: layer
            }, function (data) {
                $.fn.zTree.init($("#authorityTree"), {
                    check: {
                        enable: true,
                        chkDisabledInherit: true,
                        chkboxType: {"Y": "ps", "N": "s"}
                    }, data: {simpleData: {enable: true}}
                }, data.obj);
            });
        } else {
            JY.Model.error("选择显示层级有误！");
        }
    });
    //授权机构组织
    $('#authOrgBtn').on('click', function (e) {
        cleanAuthForm();
        $("#authorityDiv input[name$='auth']").val("org");
        //通知浏览器不要执行与事件关联的默认动作
        e.preventDefault();
        var orgId = $("#baseForm input[name$='orgId']").val();
        var layer = $("#authorityDiv input[name$='layer']").val();
        if (JY.Object.notNull(orgId)) {
            JY.Ajax.doRequest("", jypath + '/backstage/org/role/orglistCompanyAuthorized', {
                id: orgId,
                layer: layer
            }, function (data) {
                $.fn.zTree.init($("#authorityTree"), {
                    check: {
                        enable: true,
                        chkDisabledInherit: true,
                        chkboxType: {"Y": "ps", "N": "s"}
                    }, data: {simpleData: {enable: true}}
                }, data.obj);
                JY.Model.edit("authorityDiv", "机构组织授权", function () {
                    var zTree = $.fn.zTree.getZTreeObj("authorityTree"),
                        nodes = zTree.getCheckedNodes(), aus = "";
                    for (var i = 0, l = nodes.length; i < l; i++) {
                        aus += nodes[i].id + ",";
                    }
                    if (aus.length > 0) aus = aus.substring(0, aus.length - 1);
                    var that = $(this);
                    layer = $("#authorityDiv input[name$='layer']").val();
                    JY.Ajax.doRequest(null, jypath + '/backstage/org/role/saveOrgAuthorized', {
                        id: orgId,
                        aus: aus,
                        layer: layer
                    }, function (data) {
                        that.dialog("close");
                        JY.Model.info(data.resMsg, function () {
                            search();
                        });
                    });
                });
            });
        } else {
            JY.Model.error("请选择机构组织");
        }
    });
    //新加机构组织
    $('#addOrgBtn').on('click', function (e) {
        //通知浏览器不要执行与事件关联的默认动作
        e.preventDefault();
        cleanOrgForm();
        loadPreOrgTree();
        JY.Model.edit("auOrgDiv", "新增机构组织", function () {
            if (JY.Validate.form("auOrgForm")) {
                var that = $(this);
                JY.Ajax.doRequest("auOrgForm", jypath + '/backstage/org/role/addOrg', null, function (data) {
                    that.dialog("close");
                    JY.Model.info(data.resMsg, function () {
                        refreshOrgTree();
                    });
                });
            }
        });
    });
    //修改机构组织
    $('#editOrgBtn').on('click', function (e) {
        //通知浏览器不要执行与事件关联的默认动作
        e.preventDefault();
        cleanOrgForm();
        $("#preOrgTR").addClass('hide');
        loadPreOrgTree();
        var orgId = $("#baseForm input[name$='orgId']").val();
        JY.Ajax.doRequest(null, jypath + '/backstage/org/role/findOrg', {id: orgId}, function (data) {
            if (data.res == 1) {
                setOrgForm(data);
                JY.Model.edit("auOrgDiv", "修改机构组织", function () {
                    if (JY.Validate.form("auOrgForm")) {
                        var that = $(this);
                        JY.Ajax.doRequest("auOrgForm", jypath + '/backstage/org/role/updateOrg', null, function (data) {
                            that.dialog("close");
                            JY.Model.info(data.resMsg, function () {
                                refreshOrgTree();
                            });
                        });
                    }
                });
            } else {
                JY.Model.info(data.resMsg);
            }
        });
    });

    //删除机构组织
    $('#delOrgBtn').on('click', function (e) {
        //通知浏览器不要执行与事件关联的默认动作
        e.preventDefault();
        var orgId = $("#baseForm input[name$='orgId']").val();
        var treeObj = $.fn.zTree.getZTreeObj("orgTree");
        var ptreeNode = treeObj.getNodeByParam("id", orgId, null);
        JY.Model.confirm("确认要删除【" + ptreeNode.name + "】吗？", function () {
            JY.Ajax.doRequest(null, jypath + '/backstage/org/role/delOrg', {id: orgId}, function (data) {
                JY.Model.info(data.resMsg, function () {
                    loadOrgTree();
                });
            });
        });
    });

    //新增职务
    $('#addBtn').on('click', function (e) {
        //通知浏览器不要执行与事件关联的默认动作
        e.preventDefault();
        cleanForm();
        loadPreOrgTree();
        loadPreOrgTree1();
        var orgId = $("#baseForm input[name$='orgId']").val();
        // if(JY.Object.notNull(orgId)){
        /* var treeObj = $.fn.zTree.getZTreeObj("orgTree");
         var ptreeNode=treeObj.getNodeByParam("id",orgId,null);*/
        JY.Model.resizeedit("350","450","auDiv", "新增职务", function () {
            if (JY.Validate.form("auForm")) {
                var that = $(this);
                JY.Ajax.doRequest("auForm", jypath + '/backstage/org/positions/add', null, function (data) {
                    that.dialog("close");
                    JY.Model.info(data.resMsg, function () {
                        search();
                        // refreshOrgTree();
                    });
                });
            }
        });
        // }else{
        //  JY.Model.error("请先选择机构组织");
        // }
    });
    //批量删除
    $('#delBatchBtn').on('click', function (e) {
        //通知浏览器不要执行与事件关联的默认动作
        e.preventDefault();
        var chks = [];
        $('#baseTable input[name="ids"]:checked').each(function () {
            chks.push($(this).val());
        });
        if (chks.length == 0) {
            JY.Model.info("您没有选择任何内容!");
        } else {
            JY.Model.confirm("确认要删除选中的职务吗?", function () {
                JY.Ajax.doRequest(null, jypath + '/backstage/org/role/delBatch', {chks: chks.toString()}, function (data) {
                    JY.Model.info(data.resMsg, function () {
                        search();
                    });
                });
            });
        }
    });

});
function search() {
    $("#searchBtn").trigger("click");
}
function loadOrgTree() {
    JY.Ajax.doRequest(null, jypath + '/backstage/org/role/getOrgTree', null, function (data) {
        //设置
        $.fn.zTree.init($("#orgTree"), {
            view: {selectedMulti: false, fontCss: {color: "#393939"}},
            data: {simpleData: {enable: true}},
            callback: {onClick: clickOrg}
        }, data.obj);
        var treeObj = $.fn.zTree.getZTreeObj("orgTree");
        var nodes = treeObj.getNodes();
        if (nodes.length > 0) {
            //默认选中第一个
            treeObj.selectNode(nodes[0]);
            clickOrg(null, null, nodes[0]);
        }
    });
}
function refreshOrgTree() {
    JY.Model.loading();
    JY.Ajax.doRequest(null, jypath + '/backstage/org/role/getOrgTree', null, function (data) {
        //设置
        $.fn.zTree.init($("#orgTree"), {
            view: {selectedMulti: false, fontCss: {color: "#393939"}},
            data: {simpleData: {enable: true}},
            callback: {onClick: clickOrg}
        }, data.obj);
        JY.Model.loadingClose();
    });
}
function loadPreOrgTree1() {
    JY.Ajax.doRequest(null, jypath + '/backstage/org/role/getRoleTree', null, function (data) {
        //设置
        $.fn.zTree.init($("#preOrgTree1"), {
            view: {dblClickExpand: false, selectedMulti: false, nameIsHTML: true},
            data: {simpleData: {enable: true}},
            callback: {onClick: clickPreOrg1}
        }, data.obj);
    });
}
function loadPreOrgTree() {
    JY.Ajax.doRequest(null, jypath + '/backstage/org/role/getCompanyPreOrgTree', null, function (data) {
        //设置
        $.fn.zTree.init($("#preOrgTree"), {
            view: {dblClickExpand: false, selectedMulti: false, nameIsHTML: true},
            data: {simpleData: {enable: true}},
            callback: {onClick: clickPreOrg}
        }, data.obj);
    });
}
function emptyPreOrg() {
    $("#preOrg").prop("value", "");
    $("#auOrgForm input[name$='orgId']").prop("value", "0");
}
function emptyPreOrg1() {
    $("#preOrg1").prop("value", "");
    $("#auOrgForm input[name$='pid']").prop("value", "0");
}
var preisShow = false;//窗口是否显示
function showPreOrg() {
    if (preisShow) {
        hidePreOrg();
    } else {
        var obj = $("#preOrg");
        var offpos = $("#preOrg").position();
        $("#preOrgContent").css({left: offpos.left + "px", top: offpos.top + obj.heith + "px"}).slideDown("fast");
        preisShow = true;
    }
}
var preisShow1 = false;//窗口是否显示
function showPreOrg1() {
    if (preisShow1) {
        hidePreOrg1();
    } else {
        var obj = $("#preOrg1");
        var offpos = $("#preOrg1").position();
        $("#preOrgContent1").css({left: offpos.left + "px", top: offpos.top + obj.heith + "px"}).slideDown("fast");
        preisShow1 = true;
    }
}
function clickPreOrg1(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("preOrgTree1"),
        nodes = zTree.getSelectedNodes(), v = "", n = "";
    for (var i = 0, l = nodes.length; i < l; i++) {
        v += nodes[i].name + ",";//获取name值
        n += nodes[i].id + ",";	//获取id值
    }
    if (v.length > 0) v = v.substring(0, v.length - 1);
    if (n.length > 0) n = n.substring(0, n.length - 1);
    $("#preOrg1").prop("value", v);
    $("#auForm input[name$='pid']").val(n);
    //因为单选选择后直接关闭，如果多选请另外写关闭方法
    hidePreOrg1();
}
function clickPreOrg(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("preOrgTree"),
        nodes = zTree.getSelectedNodes(), v = "", n = "";
    for (var i = 0, l = nodes.length; i < l; i++) {
        v += nodes[i].name + ",";//获取name值
        n += nodes[i].id + ",";	//获取id值
    }
    if (v.length > 0) v = v.substring(0, v.length - 1);
    if (n.length > 0) n = n.substring(0, n.length - 1);
    $("#preOrg").prop("value", v);
    $("#auForm input[name$='orgId']").prop("value", n);
    //因为单选选择后直接关闭，如果多选请另外写关闭方法
    hidePreOrg();
}
function hidePreOrg() {
    $("#preOrgContent").fadeOut("fast");
    preisShow = false;
}
function hidePreOrg1() {
    $("#preOrgContent1").fadeOut("fast");
    preisShow1 = false;
}
function clickOrg(event, treeId, treeNode) {
    $("#baseForm input[name$='orgId']").val(treeNode.id);
    // $("#orgName1").html("");
    // $("#orgName2").html("");
    if (JY.Object.notNull(treeNode.pId)) {
        var treeObj = $.fn.zTree.getZTreeObj("orgTree");
        var ptreeNode = treeObj.getNodeByParam("id", treeNode.pId, null);
        $("#orgName1").html(ptreeNode.name);
        $("#orgName2").html(treeNode.name);
    } else {
        $("#orgName1").html(treeNode.name);
    }
    getbaseList();
}
function getbaseList(init) {
    if (init == 1)$("#baseForm .pageNum").val(1);
    JY.Model.loading();
    JY.Ajax.doRequest("baseForm", jypath + '/backstage/org/positions/findByPage', null, function (data) {
        $("#baseTable tbody").empty();
        var obj = data.obj;
        var list = obj.list;
        var results = list.results;
        var pageNum = list.pageNum, pageSize = list.pageSize, totalRecord = list.totalRecord;
        var permitBtn = obj.permitBtn;
        var html = "";
        if (results != null && results.length > 0) {
            var leng = (pageNum - 1) * pageSize;//计算序号
            for (var i = 0; i < results.length; i++) {
                var l = results[i];
                html += "<tr>";
                html += "<td class='center'><label> <input type='checkbox' name='ids' value='" + l.id + "' class='ace' /> <span class='lbl'></span></label></td>";
                html += "<td class='center hidden-480'>" + (i + leng + 1) + "</td>";
                html += "<td class='center'>" + JY.Object.notEmpty(l.name) + "</td>";
                if (l.isValid == 1) {
                    html += "<td class='center hidden-480'><span class='label label-sm label-success'>有效</span></td>";
                } else {
                    html += "<td class='center hidden-480'><span class='label label-sm arrowed-in'>无效</span></td>";
                }
                html += "<td class='center'>" + JY.Object.notEmpty(l.orgName) + "</td>";
                html += "<td class='center'>" + JY.Object.notEmpty(l.pName) + "</td>";
                html += "<td class='center hidden-480'>" + JY.Object.notEmpty(l.description) + "</td>";
                html += JY.Tags.setFunction(l.id, permitBtn);
                html += "</tr>";
            }
            $("#baseTable tbody").append(html);
            JY.Page.setPage("baseForm", "pageing", pageSize, pageNum, totalRecord, "getbaseList");
        } else {
            html += "<tr><td colspan='6' class='center'>没有相关数据</td></tr>";
            $("#baseTable tbody").append(html);
            $("#pageing ul").empty();//清空分页
        }
        JY.Model.loadingClose();
    });
}
function authorized(id) {
    cleanAuthForm();
    $("#authorityDiv input[name$='roleId']").val(id);
    $("#authorityDiv input[name$='auth']").val("role");
    var layer = $("#authorityDiv input[name$='layer']").val();
    JY.Ajax.doRequest(null, jypath + '/backstage/org/positions/listPositionAuthorized', {
        id: id,
        layer: layer
    }, function (data) {
        //获取数据
        var zNodes = data.obj;
        //设置
        var setting = {
            check: {enable: true, chkDisabledInherit: true, chkboxType: {"Y": "ps", "N": "s"}},
            data: {simpleData: {enable: true}}
        };
        $.fn.zTree.init($("#authorityTree"), setting, zNodes);
        JY.Model.edit("authorityDiv", "角色授权", function () {
            var zTree = $.fn.zTree.getZTreeObj("authorityTree"),
                nodes = zTree.getCheckedNodes(), aus = "";
            for (var i = 0, l = nodes.length; i < l; i++) {
                aus += nodes[i].id + ",";
            }
            if (aus.length > 0) aus = aus.substring(0, aus.length - 1);
            var that = $(this);
            layer = $("#authorityDiv input[name$='layer']").val();
            JY.Ajax.doRequest(null, jypath + '/backstage/org/positions/saveAuthorized', {
                id: id,
                aus: aus,
                layer: layer
            }, function (data) {
                that.dialog("close");
                JY.Model.info(data.resMsg, function () {
                    search();
                });
            });
        });
    });
}
function check(id) {
    //清空表单
    cleanForm();
    loadPreOrgTree();
    loadPreOrgTree1();
    JY.Ajax.doRequest(null, jypath + '/backstage/org/positions/find', {id: id}, function (data) {
        setForm(data);
        JY.Model.check("auDiv");
    });
}
function edit(id) {
    //清空表单
    cleanForm();
    JY.Ajax.doRequest(null, jypath + '/backstage/org/positions/find', {id: id}, function (data) {
        setForm(data);
        loadPreOrgTree();
        loadPreOrgTree1();
        JY.Model.resizeedit("350","450","auDiv", "修改职务", function () {
            if (JY.Validate.form("auForm")) {
                var that = $(this);
                JY.Ajax.doRequest("auForm", jypath + '/backstage/org/positions/update', null, function (data) {
                    that.dialog("close");
                    JY.Model.info(data.resMsg, function () {
                        search();
                    });
                });
            }
        });
    });
}
function del(id) {
    JY.Model.confirm("确认删除角色吗？", function () {
        JY.Ajax.doRequest(null, jypath + '/backstage/org/role/del', {id: id}, function (data) {
            JY.Model.info(data.resMsg, function () {
                search();
            });
        });
    });
}
function cleanOrgForm() {
    JY.Tags.cleanForm("auForm");
    $("#auForm input[name$='pid']").val('0');//上级资源
    $("#preOrgTR").removeClass('hide');
    hidePreOrg();
}
function cleanForm() {
    JY.Tags.cleanForm("auForm");
    JY.Tags.isValid("auForm", "1");
    hidePreOrg();
    hidePreOrg1();
}

function setOrgForm(data) {
    var l = data.obj;
    $("#auForm input[name$='id']").val(JY.Object.notEmpty(l.id));
    $("#auForm input[name$='name']").val(JY.Object.notEmpty(l.name));
    $("#auForm textarea[name$='description']").val(JY.Object.notEmpty(l.description));//描述
    $("#preOrg").val(JY.Object.notEmpty(l.pName));
    $("#auForm input[name$='pid']").val(JY.Object.notEmpty(l.pid));//上级资源
};
function setForm(data) {
    var l = data.obj;
    $("#auForm input[name$='id']").val(JY.Object.notEmpty(l.id));
    $("#auForm input[name$='pid']").val(JY.Object.notEmpty(l.pid));
    $("#auForm input[name$='orgId']").val(JY.Object.notEmpty(l.orgId));
    JY.Tags.isValid("auForm", (JY.Object.notNull(l.isValid) ? l.isValid : "0"));
    $("#auForm input[name$='name']").val(JY.Object.notEmpty(l.name));
    $("#preOrg").val(JY.Object.notEmpty(l.orgName));
    $("#preOrg1").val(JY.Object.notEmpty(l.pName));
    $("#auForm textarea[name$='description']").val(JY.Object.notEmpty(l.description));//描述
}
function cleanAuthForm() {
    $("#authorityDiv input[name$='auth']").val("org");
    $("#authorityDiv button").removeClass("btn-success");
    $("#authorityDiv input[name$='layer']").val("1");
    $("#authorityDiv button[value$='1']").addClass("btn-success");
    $("#authorityDiv input[name$='roleId']").val("");
}
