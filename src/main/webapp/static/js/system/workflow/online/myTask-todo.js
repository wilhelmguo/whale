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
    JY.Ajax.doRequest("baseForm", jypath + '/backstage/workflow/online/myTask/findTodoByPage', null, function (data) {
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
                html += "<td class='center'>" + JY.Object.notEmpty(l.presentationSubject) + "</td>";
                html += "<td class='center'>" + JY.Date.Default(l.createTime) + "</td>";
                html += "<td class='center hidden-480'>" + JY.Object.notEmpty(l.processName) + "</td>";
                /*html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.priority)+"</td>";*/
                html += "<td class='center hidden-480'>" + JY.Object.notEmpty(l.name) + "</td>";
                // html+="<td class='center hidden-480'>"+JY.Date.Default(l.dueDate)+"</td>";
                // html+="<td class='left hidden-480'>"+JY.Object.notEmpty(l.description)+"</td>";
                // html+="<td class='left hidden-480'>"+JY.Object.notEmpty(l.owner)+"</td>";
                // if('deptAudit'==taskDKey||'hrAudit'==taskDKey){
                html += "<td class='center'>" +
                    "<a title='办理' class='aBtnNoTD' " +
                    "onclick='todoTask(&apos;" + l.id + "&apos;,&apos;" + l.processInstanceId + "&apos;,&apos;" + l.pkey + "&apos;)' >" +
                    "<i class='icon-edit color-blue bigger-140' ></i></a>" +
                    "<a title='详情' class='aBtnNoTD' " +
                    "onclick='currentNode(&apos;" + l.processDefinitionId + "&apos;,&apos;" + l.processInstanceId + "&apos;)' >" +
                    "<i class='icon-zoom-in color-purple bigger-140'></i></a>" +
                    "</td>";
                // }else{
                //  html+="<td class='left'><button class='btn btn-xs btn-grey' onclick='adjustTask(&apos;"+l.id+"&apos;,&apos;"+l.processInstanceId+"&apos;)' ><i class='icon-pencil align-top bigger-125'></i>调整申请</button></td>";
                // }
                html += "</tr>";
            }
            $("#baseTable tbody").append(html);
            JY.Page.setPage("baseForm", "pageing", pageSize, pageNum, totalRecord, "getbaseList");
        } else {
            html += "<tr><td colspan='12' class='center'>没有相关数据</td></tr>";
            $("#baseTable tbody").append(html);
            $("#pageing ul").empty();//清空分页
        }
        JY.Model.loadingClose();
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


function todoTask(id, pId, name) {
    if (name == 'leave') {
        leave(id, pId);
    } else if (name == 'claim') {
        claim(id, pId);
    } else if (name == 'overtime') {
        overtime(id, pId);
    } else if (name == 'patch') {
        patch(id, pId);
    } else {
        usrdef(id, pId)
    }
}

function usrdef(id, pId) {
    cleanUserdefForm();
    JY.Ajax.doRequest(null, jypath + '/backstage/workflow/online/myTask/findTaskByName', {
        pId: pId,
        name: '自定义'
    }, function (data) {
        var obj = data.obj;
        setUserdefForm(obj);
        CallbackForm(id, pId, "auDivUserdef");
    });
}

function leave(id, pId) {
    cleanTodoForm();
    JY.Ajax.doRequest(null, jypath + '/backstage/workflow/online/myTask/findTaskByName', {
        pId: pId,
        name: '请假流程'
    }, function (data) {
        var obj = data.obj;
        setTodoForm(obj);
        CallbackForm(id, pId, "auDiv");
    });
}


function claim(id, pId) {
    cleanClaimForm();
    JY.Ajax.doRequest(null, jypath + '/backstage/workflow/online/myTask/findTaskByName', {
        pId: pId,
        name: '报销流程'
    }, function (data) {
        var obj = data.obj;
        setClaimForm(obj);
        CallbackForm(id, pId, "auDivClaim");

    });
}

function patch(id, pId) {
    cleanPatchForm();
    JY.Ajax.doRequest(null, jypath + '/backstage/workflow/online/myTask/findTaskByName', {
        pId: pId,
        name: '补卡流程'
    }, function (data) {
        var obj = data.obj;
        setPatchForm(obj);
        CallbackForm(id, pId, "auDivPatch");

    });
}

function overtime(id, pId) {
    cleanOvertimeForm();
    JY.Ajax.doRequest(null, jypath + '/backstage/workflow/online/myTask/findTaskByName', {
        pId: pId,
        name: '加班流程'
    }, function (data) {
        var obj = data.obj;
        setOvertimeForm(obj);
        CallbackForm(id, pId, "auDivOvertime");

    });
}

function CallbackForm(id, pId, formId) {
    JY.Model.audit(formId, "审核"
        , function () {
            //同意申请
            var that = $(this);
            JY.Model.confirm("确认同意吗？", function () {
                var vars = [{key: 'approval', value: true, type: 'B'},
                    {key: 'processInstanceId', value: pId, type: 'S'}];
                JY.Ajax.doRequest("", jypath + '/backstage/workflow/online/myTask/complete/' + id, JY.Object.comVar(vars), function (data) {
                    JY.Model.info(data.resMsg, function () {
                        search();
                        that.dialog("close");
                    });
                });
            });
        }, function () {
            //驳回申请
            $(this).dialog("close");
            JY.Tags.cleanForm("rejectDiv");
            JY.Model.edit("rejectDiv", "驳回", function () {
                var rejectReason = $("#rejectForm textarea[name$='rejectReason']").val();
                if (JY.Object.notNull(rejectReason)) {
                    var that = $(this);
                    JY.Model.confirm("确认驳回吗？", function () {
                        var vars = [{key: 'approval', value: false, type: 'B'}];
                        var ovar = JY.Object.comVar(vars);
                        ovar.rejectReason = rejectReason;
                        ovar.pId = pId;
                        JY.Ajax.doRequest("", jypath + '/backstage/workflow/online/myTask/reject/' + id, ovar, function (data) {
                            JY.Model.info(data.resMsg, function () {
                                search();
                                that.dialog("close");
                            });
                        });
                    });
                } else {
                    $("#rejectForm textarea[name$='rejectReason']").tips({
                        side: 1,
                        msg: "请填写理由！",
                        bg: '#FF2D2D',
                        time: 1
                    });
                }
            });
        }
    );
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

function setUserdefForm(l) {
    if (JY.Object.notNull(l)) {
        $("#auFormUserdef input[name$='id']").val(l.id);
        // $("#auForm input[name$='org']").val(JY.Object.notEmpty(l.org));
        $("#auFormUserdef input[name$='account_id']").val(JY.Object.notEmpty(l.name));

        $("#attachUserdef").html(JY.Object.notEmpty(l.attach));
    }
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

function cleanClaimForm() {
    JY.Tags.cleanForm("auFormClaim");
}

function cleanOvertimeForm() {
    JY.Tags.cleanForm("auFormOvertime");
}

function cleanPatchForm() {
    JY.Tags.cleanForm("auFormPatch");
}

function cleanTodoForm() {
    JY.Tags.cleanForm("auForm");
}

function cleanUserdefForm() {
    JY.Tags.cleanForm("auFormUserdef");
}