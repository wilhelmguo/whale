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
                    "onclick='todoTask(&apos;" + l.id + "&apos;,&apos;" + l.processInstanceId + "&apos;)' >" +
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

function currentNode(pdId,pIId){
    var width=document.documentElement.clientWidth * 0.85+"px";
    var height=document.documentElement.clientHeight * 0.85+"px";
    layer.open({
        type: 2,
        title: '当前节点',
        shadeClose: true,
        maxmin: true,
        area: [width, height],
        content: jypath+"/act-process-editor/diagram-viewer/index.html?processDefinitionId="+pdId+"&processInstanceId="+pIId //iframe的url
    });
}

function adjustTask(id, pId) {
    cleanAdjustForm();
    JY.Ajax.doRequest(null, jypath + '/backstage/workflow/online/myTask/findTask', {pId: pId}, function (data) {
        var obj = data.obj;
        setAdjustForm(obj);
        JY.Model.adjust("adjustDiv", "调整申请"
            , function () {
                //重新提交申请
                var that = $(this);
                var vars = [{key: 'reApply', value: true, type: 'B'}];
                var description = $("#adjustForm textarea[name$='description']").val();
                if (JY.Object.notNull(description)) {
                    if (JY.Validate.form("adjustForm")) {
                        JY.Model.confirm("确认提交申请吗?", function () {
                            var ovar = JY.Object.comVar(vars);
                            ovar.description = description;
                            ovar.pId = pId;
                            JY.Ajax.doRequest("", jypath + '/backstage/workflow/online/myTask/adjust/' + id, ovar, function (data) {
                                JY.Model.info(data.resMsg, function () {
                                    search();
                                    that.dialog("close");
                                });
                            });
                        });
                    }
                } else {
                    $("#adjustForm textarea[name$='description']").tips({
                        side: 1,
                        msg: "请填写事由！",
                        bg: '#FF2D2D',
                        time: 1
                    });
                }
            }, function () {
                //放弃申请
                var that = $(this);
                var vars = [{key: 'reApply', value: false, type: 'B'}];
                JY.Model.confirm("确认放弃吗？", function () {
                    JY.Ajax.doRequest("", jypath + '/backstage/workflow/online/myTask/complete/' + id, JY.Object.comVar(vars), function (data) {
                        JY.Model.info(data.resMsg, function () {
                            search();
                            that.dialog("close");
                        });
                    });
                });
            });
    });
}
function todoTask(id, pId) {
    cleanTodoForm();
    JY.Ajax.doRequest(null, jypath + '/backstage/workflow/online/myTask/findTask', {pId: pId}, function (data) {
        var obj = data.obj;
        setTodoForm(obj);
        JY.Model.audit("auDiv", "审核"
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

    });
}
function setTodoForm(l) {
    if (JY.Object.notNull(l)) {
        $("#auForm input[name$='id']").val(l.id);
        $("#auForm input[name$='org']").val(JY.Object.notEmpty(l.org));
        $("#auForm input[name$='account_id']").val(JY.Object.notEmpty(l.account_id));
        $("#auForm input[name$='leaveDay']").val(JY.Object.notEmpty(l.leaveDay));
        $("#auForm input[name$='typeName']").val(JY.Object.notEmpty(l.typeName));
        $("#auForm input[name$='beginTime']").val(JY.Date.Default(l.beginTime));
        $("#auForm input[name$='endTime']").val(JY.Date.Default(l.endTime));
        $("#auForm textarea[name$='description']").val(JY.Object.notEmpty(l.description));
    }
}
function setAdjustForm(l) {
    if (JY.Object.notNull(l)) {
        $("#adjustForm input[name$='id']").val(l.id);
        $("#adjustForm input[name$='org']").val(JY.Object.notEmpty(l.org));
        $("#adjustForm input[name$='name']").val(JY.Object.notEmpty(l.name));
        $("#adjustForm input[name$='approver']").val(JY.Object.notEmpty(l.approver));
        $("#adjustForm input[name$='typeName']").val(JY.Object.notEmpty(l.typeName));
        $("#adjustForm input[name$='type']").val(JY.Object.notEmpty(l.type));
        $("#adjustForm input[name$='beginTime']").val(JY.Date.Default(l.beginTime));
        $("#adjustForm input[name$='endTime']").val(JY.Date.Default(l.endTime));
        $("#adjustForm textarea[name$='description']").val(JY.Object.notEmpty(l.description));
        $("#adjustForm textarea[name$='rejectReason']").val(JY.Object.notEmpty(l.rejectReason));
    }
}

function cleanTodoForm() {
    JY.Tags.cleanForm("auForm");
}
function cleanAdjustForm() {
    JY.Tags.cleanForm("adjustForm");
}
