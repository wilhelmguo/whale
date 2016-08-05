var mapWorkflow = new Map();
$(function () {
    setSelect("workflowkey");
    loadPreOrgTree();
});

function setSelect(ids) {
    $.ajax({
        type: 'POST',
        url: jypath + '/backstage/workflow/process/findUserDefWorkflow',
        data: null,
        dataType: 'json',
        success: function (data) {

            if (data.res == 1) {
                var map = data.obj;
                var opts = "";
                for (var i = 0; i < map.length; i++) {
                    var value = map[i];
                    if (i == 0) {
                        $("#leaveFrom").find("input[name$='wname']").val(value.name);
                    }
                    mapWorkflow[value.key] = value.name;
                    opts += "<option value='" + value.key + "'>" + value.key + "</option>";
                }
                $("#" + ids).append(opts);

            }

        }
    });
}

function submitApply() {
    if (JY.Validate.form("leaveFrom")) {
        JY.Model.confirm("确认提交申请吗?", function () {
            var ueContent = UE.getEditor('editor').getContent();
            $("#leaveFrom").find("input[name$='attach']").val(ueContent);
            JY.Ajax.doRequest("leaveFrom", jypath + '/backstage/workflow/online/userdef/start', "", function (data) {
                JY.Model.info(data.resMsg);
            });
        });
    }
}

function keyChange() {
    var key = $("#workflowkey").val();
    $("#leaveFrom").find("input[name$='wname']").val(mapWorkflow[key]);
}

function loadPreOrgTree() {
    JY.Ajax.doRequest(null, jypath + '/backstage/org/role/getCompanyUserPreOrgTree', null, function (data) {
        //设置
        $.fn.zTree.init($("#preOrgTree"), {
            view: {dblClickExpand: false, selectedMulti: false, nameIsHTML: true},
            data: {simpleData: {enable: true}},
            callback: {onClick: clickPreOrg}
        }, data.obj);
        //设置1
        $.fn.zTree.init($("#preOrgTree1"), {
            view: {dblClickExpand: false, selectedMulti: false, nameIsHTML: true},
            data: {simpleData: {enable: true}},
            callback: {onClick: clickPreOrg}
        }, data.obj);
    });
}

function clickPreOrg(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("preOrgTree"),
        nodes = zTree.getSelectedNodes(), v = "", n = "", o = "";
    for (var i = 0, l = nodes.length; i < l; i++) {
        v += nodes[i].name + ",";//获取name值
        n += nodes[i].id + ",";	//获取id值
        o += nodes[i].other + ",";//获取自定义值
    }
    if (v.length > 0) v = v.substring(0, v.length - 1);
    if (n.length > 0) n = n.substring(0, n.length - 1);
    if (o.length > 0) o = o.substring(0, o.length - 1);
    if (o != 'r') {
        return false;
    }
    var approveName = $("#preOrgName").val();
    var approveID = $("#preOrg").val();
    if (JY.Object.notNull(approveName)) {
        if (approveName.indexOf(v) != -1) {
            return false;
        }
        // if (approveName.split(',').length >= 2) {
        //     return false;
        // }
        approveName = approveName + "," + v;

    } else {
        approveName = v;
    }
    if (JY.Object.notNull(approveID)) {
        if (approveID.indexOf(n) != -1) {
            return false;
        }
        approveID = approveID + "," + n;
    } else {
        approveID = n;
    }
    $("#preOrgName").val(approveName);
    $("#preOrg").val(approveID);
    $("#auOrgForm input[name$='pId']").prop("value", n);
    //因为单选选择后直接关闭，如果多选请另外写关闭方法
    hidePreOrg();
}

var preisShow = false;//窗口是否显示
function showPreOrg() {
    if (preisShow) {
        hidePreOrg();
    } else {
        var obj = $("#preOrgName");
        var offpos = $("#preOrgName").position();
        $("#preOrgContent").css({left: offpos.left + "px", top: offpos.top + obj.heith + "px"}).slideDown("fast");
        preisShow = true;
    }
}

var preisShow1 = false;//窗口是否显示
function showPreOrg1() {
    if (preisShow1) {
        hidePreOrg1();
    } else {
        var obj = $("#preOrgName1");
        var offpos = $("#preOrgName1").position();
        $("#preOrgContent1").css({left: offpos.left + "px", top: offpos.top + obj.heith + "px"}).slideDown("fast");
        preisShow1 = true;
    }
}

function hidePreOrg1() {
    $("#preOrgContent1").fadeOut("fast");
    preisShow1 = false;
}

function hidePreOrg() {
    $("#preOrgContent").fadeOut("fast");
    preisShow = false;
}

function emptyPreOrg1() {
    $("#preOrg1").prop("value", "");
    $("#preOrgName1").prop("value", "");
    $("#leaveFrom input[name$='pId1']").prop("value", "0");
}

function emptyPreOrg() {
    $("#preOrg").prop("value", "");
    $("#preOrgName").prop("value", "");
    $("#leaveFrom input[name$='pId']").prop("value", "0");
}