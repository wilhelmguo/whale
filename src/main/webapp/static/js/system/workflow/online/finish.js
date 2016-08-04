$(function () {
    getbaseList();
    //增加回车事件
    $("#baseForm").keydown(function (e) {
        keycode = e.which || e.keyCode;
        if (keycode == 13) {
            search();
        }
    });
});
function search() {
    $("#searchBtn").trigger("click");
}
function getbaseList(init) {
    if (init == 1)$("#baseForm .pageNum").val(1);
    JY.Model.loading();
    JY.Ajax.doRequest("baseForm", jypath + '/backstage/workflow/online/taskInfo/findComplateByPage', null, function (data) {
        $("#baseTable tbody").empty();
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
                html += "<td hidden class='center hidden-480'>" + JY.Object.notEmpty(l.id) + "</td>";
                html += "<td class='center'>" + JY.Object.notEmpty(l.presentationsubject) + "</td>";
                html += "<td class='center hidden-480'>" + JY.Date.Default(l.completetime) + "</td>";
                html += "<td class='center hidden-480'>" + JY.Object.notEmpty(l.attr1) + "</td>";
                html += "<td class='center'>" + JY.Object.notEmpty(l.attr2) + "</td>";
                html += "<td class='center'>" +
                    "<a title='办理详情' class='aBtnNoTD' " +
                    "onclick='todoTask(&apos;" + l.processinstanceid + "&apos;,&apos;" + l.attr1 + "&apos;)' >" +
                    "<i class='icon-edit color-blue bigger-140' ></i></a>" +
                    "<a title='流程详情' class='aBtnNoTD' " +
                    "onclick='currentNode(&apos;" + l.processdefinitionid + "&apos;,&apos;" + l.processinstanceid + "&apos;)' >" +
                    "<i class='icon-zoom-in color-purple bigger-140'></i></a>" +
                    // "<a title='我的' class='aBtnNoTD' " +
                    // "onclick='test(&apos;" + l.processdefinitionid + "&apos;,&apos;" + l.processinstanceid + "&apos;)' >" +
                    // "<i class='icon-anchor color-purple bigger-140'></i></a>" +
                    "</td>";
                html += "</tr>";
            }
            $("#baseTable tbody").append(html);
            JY.Page.setPage("baseForm", "pageing", pageSize, pageNum, totalRecord, "getbaseList");
        } else {
            html += "<tr><td colspan='7' class='center'>没有相关数据</td></tr>";
            $("#baseTable tbody").append(html);
            $("#pageing ul").empty();//清空分页
        }
        JY.Model.loadingClose();
    });
}

function test(pdId, pIId) {
    JY.Ajax.doRequest(null, jypath + '/backstage/workflow/online/myTask/test', {pId: pdId}, function (data) {
        var obj = data.obj;
        setTodoForm(obj);
        JY.Model.check("auDiv");
    });
}

function currentNode(pdId, pIId) {
    var width = document.documentElement.clientWidth * 0.85 + "px";
    var height = document.documentElement.clientHeight * 0.85 + "px";
    layer.open({
        type: 2,
        title: '当前节点',
        shadeClose: true,
        maxmin: true,
        area: [width, height],
        content: jypath + "/act-process-editor/diagram-viewer/index.html?processDefinitionId=" + pdId + "&processInstanceId=" + pIId //iframe的url
    });
}

function cleanTodoForm() {
    JY.Tags.cleanForm("auForm");
}

function cleanOvertimeTodoForm() {
    JY.Tags.cleanForm("auFormOvertime");
}

function cleanPatchTodoForm() {
    JY.Tags.cleanForm("auFormPatch");
}

function cleanClaimTodoForm() {
    JY.Tags.cleanForm("auFormClaim");
}

function setTodoForm(l) {
    if (JY.Object.notNull(l)) {
        $("#auForm input[name$='id']").val(l.id);
        $("#auForm input[name$='org']").val(JY.Object.notEmpty(l.org));
        $("#auForm input[name$='account_id']").val(JY.Object.notEmpty(l.name));
        $("#auForm input[name$='leaveDay']").val(JY.Object.notEmpty(l.leaveDay));
        $("#auForm input[name$='typeName']").val(JY.Object.notEmpty(l.typeName));
        $("#auForm input[name$='beginTime']").val(JY.Date.Default(l.beginTime));
        $("#auForm input[name$='endTime']").val(JY.Date.Default(l.endTime));
        $("#auForm textarea[name$='description']").val(JY.Object.notEmpty(l.description));
    }
}

function setPatchForm(l) {
    if (JY.Object.notNull(l)) {
        $("#auFormPatch input[name$='id']").val(l.id);
        // $("#auForm input[name$='org']").val(JY.Object.notEmpty(l.org));
        $("#auFormPatch input[name$='account_id']").val(JY.Object.notEmpty(l.name));

        $("#auFormPatch input[name$='date']").val(JY.Date.Default(l.date));
        $("#auFormPatch input[name$='morning']").val(JY.Object.notEmpty(l.morning));
        $("#auFormPatch input[name$='beforenoon']").val(JY.Object.notEmpty(l.beforenoon));
        $("#auFormPatch input[name$='afternoon']").val(JY.Object.notEmpty(l.afternoon));
        $("#auFormPatch input[name$='night']").val(JY.Object.notEmpty(l.night));
    }
}

function setOvertimeForm(l) {
    if (JY.Object.notNull(l)) {
        $("#auFormOvertime input[name$='id']").val(l.id);
        // $("#auForm input[name$='org']").val(JY.Object.notEmpty(l.org));
        $("#auFormOvertime input[name$='account_id']").val(JY.Object.notEmpty(l.name));
        var sc = l.duration;
        if (l.unit == 0) {
            sc = sc + '小时';
        } else {
            sc = sc + '天';
        }
        $("#auFormOvertime input[name$='duration']").val(JY.Object.notEmpty(sc));
        $("#auFormOvertime input[name$='starttime']").val(JY.Date.Default(l.starttime));
        $("#auFormOvertime input[name$='endtime']").val(JY.Date.Default(l.endtime));
        $("#auFormOvertime textarea[name$='reason']").val(JY.Object.notEmpty(l.reason));
    }
}

function todoTask(pId, name) {
    if (name == '请假流程') {
        leave(pId);
    } else if (name == '报销流程') {
        claim(pId);
    }else if (name == '加班流程') {
        overtime(pId);
    }else if (name == '补卡流程') {
        patch(pId);
    }

}

function claim(pId) {
    cleanClaimTodoForm();
    JY.Ajax.doRequest(null, jypath + '/backstage/workflow/online/myTask/findTaskByName', {
        pId: pId,
        name: '报销流程'
    }, function (data) {
        var obj = data.obj;
        setClaimForm(obj);
        JY.Model.check("auDivClaim");
    });
}

function leave(pId) {
    cleanTodoForm();
    JY.Ajax.doRequest(null, jypath + '/backstage/workflow/online/myTask/findTaskByName', {
        pId: pId,
        name: '请假流程'
    }, function (data) {
        var obj = data.obj;
        setTodoForm(obj);
        JY.Model.check("auDiv");
    });
}

function patch(pId) {
    cleanPatchTodoForm();
    JY.Ajax.doRequest(null, jypath + '/backstage/workflow/online/myTask/findTaskByName', {
        pId: pId,
        name: '补卡流程'
    }, function (data) {
        var obj = data.obj;
        setPatchForm(obj);
        JY.Model.check("auDivPatch");
    });
}

function overtime(pId) {
    cleanOvertimeTodoForm();
    JY.Ajax.doRequest(null, jypath + '/backstage/workflow/online/myTask/findTaskByName', {
        pId: pId,
        name: '加班流程'
    }, function (data) {
        var obj = data.obj;
        setOvertimeForm(obj);
        JY.Model.check("auDivOvertime");
    });
}

function setClaimForm(l) {
    if (JY.Object.notNull(l)) {
        $("#auFormClaim input[name$='id']").val(l.id);
        // $("#auForm input[name$='org']").val(JY.Object.notEmpty(l.org));
        $("#auFormClaim input[name$='account_id']").val(JY.Object.notEmpty(l.name));
        $("#auFormClaim input[name$='amount']").val(JY.Object.notEmpty(l.amount));
        $("#auFormClaim input[name$='type']").val(JY.Object.notEmpty(l.type));
        $("#attach").html(JY.Object.notEmpty(l.attach));
    }
}