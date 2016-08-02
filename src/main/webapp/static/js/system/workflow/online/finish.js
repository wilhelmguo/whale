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

function todoTask(pId, name) {
    if (name == '请假流程') {
        leave(pId);
    } else if (name == '报销流程') {
        claim(pId);
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