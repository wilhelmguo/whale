$(function () {
    getbaseList();
    //增加回车事件
    $("#baseForm").keydown(function (e) {
        keycode = e.which || e.keyCode;
        if (keycode == 13) {
            search();
        }
    });

    //新加
    $('#uploadModel').on('click', function (e) {
        //通知浏览器不要执行与事件关联的默认动作		
        e.preventDefault();
        cleanFile();
        JY.Model.edit("uploadDiv", "上传流程模型", function () {
            var upVal = $("#uploadModelFile").val();
            if (JY.Object.notNull(upVal)) {
                var that = $(this);
                $("#uploadForm").ajaxSubmit({
                    type: 'post',
                    url: jypath + '/backstage/workflow/process/uploadModel',
                    success: function (data) {
                        var o = JSON.parse(data);
                        var ores = o.res;
                        var oresMsg = o.resMsg;
                        if (ores == 1) {
                            JY.Model.info(oresMsg, function () {
                                search();
                            });
                            that.dialog("close");
                        } else {
                            JY.Model.error(oresMsg);
                        }
                    }
                });
            } else {
                JY.Model.error("请选择文件");
            }
        });
    });
    //上传
    $('#uploadModelFile').ace_file_input({
        no_file: '请选择模型 ...',
        btn_choose: '选择',
        btn_change: '更改',
        droppable: false,
        onchange: null,
        thumbnail: false, //| true | large
        whitelist: 'zip|bar|bpmn|bpmn20.xml|xml',
        blacklist: 'gif|png|jpg|jpeg|xls'
        //onchange:''
        //
    });
    loadPreOrgTree();
});

function loadPreOrgTree() {
    JY.Ajax.doRequest(null, jypath + '/backstage/org/role/getRoleUserPreOrgTree', null, function (data) {
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
    $("#auForm input[name$='users']").prop("value", "0");
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

function clickPreOrg(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("preOrgTree"),
        nodes = zTree.getSelectedNodes(), v = "", n = "", o = "";
    for (var i = 0, l = nodes.length; i < l; i++) {
        v += nodes[i].name + ",";//获取name值
        n += nodes[i].id + ",";	//获取id值
        o += nodes[i].other + ",";
    }
    if (v.length > 0) v = v.substring(0, v.length - 1);
    if (n.length > 0) n = n.substring(0, n.length - 1);
    if (o.length > 0) o = o.substring(0, o.length - 1);
    $("#preOrg").prop("value", v);
    $("#auForm input[name$='users']").prop("value", n);
    var type = 0;
    if (o != "o") {
        type=1
    }
    $("#auForm input[name$='usertype']").prop("value", type);
    //因为单选选择后直接关闭，如果多选请另外写关闭方法
    hidePreOrg();
}

function hidePreOrg() {
    $("#preOrgContent").fadeOut("fast");
    preisShow = false;
}

function search() {
    $("#searchBtn").trigger("click");
}
function getbaseList(init) {
    if (init == 1)$("#baseForm .pageNum").val(1);
    JY.Model.loading();
    JY.Ajax.doRequest("baseForm", jypath + '/backstage/workflow/process/findByPage', null, function (data) {
        $("#baseTable tbody").empty();
        var obj = data.obj;
        var list = obj.list;
        var results = list.results;
        var permitBtn = obj.permitBtn;
        var pageNum = list.pageNum, pageSize = list.pageSize, totalRecord = list.totalRecord;
        var html = "";
        if (results != null && results.length > 0) {
            var leng = (pageNum - 1) * pageSize;//计算序号
            for (var i = 0; i < results.length; i++) {
                var l = results[i];
                html += "<tr>";
                html += "<td class='center'><label> <input type='checkbox' name='ids' value='" + l.id + "' class='ace' /> <span class='lbl'></span></label></td>";
                html += "<td class='center hidden-480'>" + (i + leng + 1) + "</td>";
                html += "<td class='center hidden-480'>" + JY.Object.notEmpty(l.id) + "</td>";
                html += "<td class='center hidden-480'>" + JY.Object.notEmpty(l.deploymentId) + "</td>";
                html += "<td class='center hidden-480'>" + JY.Object.notEmpty(l.name) + "</td>";
                html += "<td class='center '>" + JY.Object.notEmpty(l.key) + "</td>";
                html += "<td class='center '>" + JY.Object.notEmpty(l.version) + "</td>";
                html += "<td class='center hidden-480'><a href='javascritp:void(0);' onclick='currentType(&apos;xml&apos;,&apos;" + l.id + "&apos;);return false' >" + l.resourceName + "</a></td>";
                html += "<td class='center hidden-480'><a href='javascritp:void(0);' onclick='currentType(&apos;image&apos;,&apos;" + l.id + "&apos;);return false' >" + l.resourceName + "</a></td>";
                html += "<td class='center hidden-480'>" + JY.Date.Default(l.deploymentTime) + "</td>";
                html += JY.Tags.setFunction(l.id, permitBtn);
                html += "</tr>";
            }
            $("#baseTable tbody").append(html);
            JY.Page.setPage("baseForm", "pageing", pageSize, pageNum, totalRecord, "getbaseList");
        } else {
            html += "<tr><td colspan='10' class='center'>没有相关数据</td></tr>";
            $("#baseTable tbody").append(html);
            $("#pageing ul").empty();//清空分页
        }
        JY.Model.loadingClose();
    });
}
function del(processDefinitionId) {
    JY.Model.confirm("确认要删除流程定义吗？", function () {
        JY.Ajax.doRequest(null, jypath + '/backstage/workflow/process/del', {processDefinitionId: processDefinitionId}, function (data) {
            JY.Model.info(data.resMsg, function () {
                search();
            });
        });
    });
}
function convertToModel(processDefinitionId) {
    JY.Model.confirm("确认要转换成模型吗？", function () {
        JY.Ajax.doRequest(null, jypath + '/backstage/workflow/process/convertToModel', {processDefinitionId: processDefinitionId}, function (data) {
            JY.Model.info(data.resMsg, function () {
                search();
            });
        });
    });
}

function configWorkflow(processDefinitionId) {
    JY.Ajax.doRequest(null, jypath + '/backstage/workflow/bpmconf/findByPage', {"pid": processDefinitionId}, function (data) {
        getbaseList1(data);
    });
    $("#processDefinitionId").val(processDefinitionId);
    $("#baseTable0").hide();
    $("#baseTable1").show();
}

function getbaseList1(data) {
    JY.Model.loading();
    $("#baseTable1 tbody").empty();
    var obj = data.obj;
    var list = obj.list;
    var results = list.results;
    var pageNum = list.pageNum, pageSize = list.pageSize, totalRecord = list.totalRecord;
    var html = "";
    if (results != null && results.length > 0) {
        var leng = (pageNum - 1) * pageSize;//计算序号
        for (var i = 0; i < results.length; i++) {
            var l = results[i];
            html += "<tr>";
            html += "<td class='center'><label> <input type='checkbox' name='ids' value='" + l.id + "' class='ace' /> <span class='lbl'></span></label></td>";
            html += "<td class='center hidden-480'>" + (i + leng + 1) + "</td>";
            html += "<td class='center hidden-480' hidden>" + JY.Object.notEmpty(l.id) + "</td>";
            html += "<td class='center hidden-480'>" + JY.Object.notEmpty(l.dname) + "</td>";
            html += "<td class='center hidden-480'>" + JY.Object.notEmpty(l.pname) + "</td>";
            html += "<td class='center '>" + JY.Object.notEmpty(l.username) + "</td>";
            html += "<td class='center'>" +
                "<a title='分配审批人' class='aBtnNoTD' " +
                "onclick='currentNode(&apos;" + l.id + "&apos;" + ")' >" +
                "<i class='icon-edit color-purple bigger-140'></i></a>" +
                "</td>";
            html += "</tr>";
        }
        $("#baseTable1 tbody").append(html);
    } else {
        html += "<tr><td colspan='10' class='center'>没有相关数据</td></tr>";
        $("#baseTable1 tbody").append(html);
        $("#pageing ul").empty();//清空分页
    }
    JY.Model.loadingClose();
}

function currentNode(id) {
    // e.preventDefault();
    cleanForm();
    JY.Model.resizeedit("300", "350", "auDiv", "设置审批人", function () {
        if (JY.Validate.form("auForm")) {
            $("#auForm input[name$='id']").val(id);
            var that = $(this);
            JY.Ajax.doRequest("auForm", jypath + '/backstage/workflow/bpmconf/update', null, function (data) {
                that.dialog("close");
                JY.Model.info(data.resMsg, function () {
                    var processDefinitionId = $("#processDefinitionId").val();
                    JY.Ajax.doRequest(null, jypath + '/backstage/workflow/bpmconf/findByPage', {"pid": processDefinitionId}, function (data) {
                        getbaseList1(data);
                    });
                });
            });
        }
    });
}

function cleanForm() {
    JY.Tags.cleanForm("auForm");
    $("#preOrg").val("");//上级资源
    $("#auForm select[name$='users']").prop("value", "");
    hidePreOrg();
}

function insertClose() {
    $("#baseTable0").show();
    $("#baseTable1").hide();
}
function currentType(type, pdId) {
    var width = document.documentElement.clientWidth * 0.85 + "px";
    var height = document.documentElement.clientHeight * 0.85 + "px";
    layer.open({
        type: 2,
        title: "流程【" + type + "】",
        shadeClose: true,
        maxmin: true,
        area: [width, height],
        content: jypath + "/backstage/workflow/process/resource/read?resourceType=" + type + "&processDefinitionId=" + pdId
    });
}
function cleanFile() {
    $(".ace-file-input .remove").trigger("click");
}