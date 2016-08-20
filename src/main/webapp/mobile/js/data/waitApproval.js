/**
 * Created by gsw on 16-8-10.
 */
$(function () {
    var id = GetQueryString("id");
    var pId = GetQueryString("pId");
    var pkey = GetChineseQueryString("pkey");
    var title = GetChineseQueryString("title");
    todoTask(id, pId, pkey, title);
});

function back() {
    window.location.href = "wait.html?type="+GetQueryString("type");
}

function backFinish() {
    window.location.href = "finish.html?type="+GetQueryString("type");
}

function backMystart() {
    window.location.href = "mystart.html?type="+GetQueryString("type");
}

function approvalPass() {
    var id = GetQueryString("id");
    var pId = GetQueryString("pId");
    var vars = [{key: 'approval', value: true, type: 'B'},
        {key: 'processInstanceId', value: pId, type: 'S'}];
    doPostRequest("",  '/whale/backstage/workflow/online/myTask/complete/' + id, comVar(vars), function (data) {
        alert(data.resMsg);
        window.location.href="wait.html";

    });
}

function approvalReject() {
    var id = GetQueryString("id");
    var pId = GetQueryString("pId");
    var vars = [{key: 'approval', value: false, type: 'B'}];
    var ovar = comVar(vars);
    // ovar.rejectReason = rejectReason;
    ovar.pId = pId;
    doPostRequest("", '/whale/backstage/workflow/online/myTask/reject/' + id, ovar, function (data) {
        alert(data.resMsg);
        window.location.href="wait.html";
    });
}

function todoTask(id, pId, pkey, title) {
    var html = "";
    html += '<div style="padding-top: 8px"> <label style="padding-left: 20px;font-size: 0.9em;color: #00a2d4">审批标题:</label><span>' + title + '</span></div>';
    if (pkey == 'leave') {
        doPostRequest(null, '/whale/backstage/workflow/online/myTask/findTaskByName', {
            pId: pId,
            name: '请假流程'
        }, function (data) {
            var l = data.obj;
            var fullname = l.name;
            var shortName = fullname.substring(fullname.length - 1, fullname.length);
            $("#short-name").text(shortName);
            $("#long-name").text(fullname);
            html += '<div style="padding-top: 8px"> <label style="padding-left: 20px;font-size: 0.9em;color: #00a2d4">申请人:</label><span>' + notEmpty(l.name) + '</span></div>';
            html += '<div style="padding-top: 8px"> <label style="padding-left: 20px;font-size: 0.9em;color: #00a2d4">请假天数:</label><span>' + notEmpty(l.leaveDay)  + '</span></div>';
            html += '<div style="padding-top: 8px"> <label style="padding-left: 20px;font-size: 0.9em;color: #00a2d4">请假类型:</label><span>' + notEmpty(l.typeName)  + '</span></div>';
            html += '<div style="padding-top: 8px"> <label style="padding-left: 20px;font-size: 0.9em;color: #00a2d4">开始时间:</label><span>' + formatDate(l.beginTime) + '</span></div>';
            html += '<div style="padding-top: 8px"> <label style="padding-left: 20px;font-size: 0.9em;color: #00a2d4">结束时间:</label><span>' + formatDate(l.endTime) + '</span></div>';
            html += '<div style="padding-top: 8px"> <label style="padding-left: 20px;font-size: 0.9em;color: #00a2d4">请假事由:</label><span>' + notEmpty(l.description)  + '</span></div>';
            $("#approval-detail").html(html);
        });
    } else if (pkey == 'claim') {
        doPostRequest(null, '/whale/backstage/workflow/online/myTask/findTaskByName', {
            pId: pId,
            name: '报销流程'
        }, function (data) {
            var l = data.obj;
            var fullname = l.name;
            var shortName = fullname.substring(fullname.length - 1, fullname.length);
            $("#short-name").text(shortName);
            $("#long-name").text(fullname);
            html += '<div style="padding-top: 8px"> <label style="padding-left: 20px;font-size: 0.9em;color: #00a2d4">申请人:</label><span>' + notEmpty(l.name) + '</span></div>';
            html += '<div style="padding-top: 8px"> <label style="padding-left: 20px;font-size: 0.9em;color: #00a2d4">报销金额:</label><span>' + notEmpty(l.amount) + '</span></div>';
            html += '<div style="padding-top: 8px"> <label style="padding-left: 20px;font-size: 0.9em;color: #00a2d4">报销类别:</label><span>' + notEmpty(l.type) + '</span></div>';
            html += '<div style="padding-top: 8px"> <label style="padding-left: 20px;font-size: 0.9em;color: #00a2d4">费用明细:</label><span>' + notEmpty(l.detail) + '</span></div>';
            $("#approval-detail").html(html);
        });
    } else if (pkey == 'overtime') {
        doPostRequest(null, '/whale/backstage/workflow/online/myTask/findTaskByName', {
            pId: pId,
            name: '加班流程'
        }, function (data) {
            var l = data.obj;
            var fullname = l.name;
            var shortName = fullname.substring(fullname.length - 1, fullname.length);
            $("#short-name").text(shortName);
            $("#long-name").text(fullname);
            html += '<div style="padding-top: 8px"> <label style="padding-left: 20px;font-size: 0.9em;color: #00a2d4">申请人:</label><span>' + notEmpty(l.name) + '</span></div>';
            html += '<div style="padding-top: 8px"> <label style="padding-left: 20px;font-size: 0.9em;color: #00a2d4">加班天数:</label><span>' + notEmpty(l.duration) + '</span></div>';
            html += '<div style="padding-top: 8px"> <label style="padding-left: 20px;font-size: 0.9em;color: #00a2d4">开始时间:</label><span>' + formatDate(l.starttime) + '</span></div>';
            html += '<div style="padding-top: 8px"> <label style="padding-left: 20px;font-size: 0.9em;color: #00a2d4">结束时间:</label><span>' + formatDate(l.endtime) + '</span></div>';
            html += '<div style="padding-top: 8px"> <label style="padding-left: 20px;font-size: 0.9em;color: #00a2d4">加班原因:</label><span>' + notEmpty(l.reason) + '</span></div>';
            $("#approval-detail").html(html);
        });
    } else if (pkey == 'patch') {
        doPostRequest(null, '/whale/backstage/workflow/online/myTask/findTaskByName', {
            pId: pId,
            name: '补卡流程'
        }, function (data) {
            var l = data.obj;
            var fullname = l.name;
            var shortName = fullname.substring(fullname.length - 1, fullname.length);
            $("#short-name").text(shortName);
            $("#long-name").text(fullname);
            html += '<div style="padding-top: 8px"> <label style="padding-left: 20px;font-size: 0.9em;color: #00a2d4">申请人:</label><span>' + l.name + '</span></div>';
            html += '<div style="padding-top: 8px"> <label style="padding-left: 20px;font-size: 0.9em;color: #00a2d4">补卡日期:</label><span>' + formatDateWithPatten(l.date, "yyyy-MM-dd") + '</span></div>';
            html += '<div style="padding-top: 8px"> <label style="padding-left: 20px;font-size: 0.9em;color: #00a2d4">上班时间:</label><span>' + l.morning + '</span></div>';
            html += '<div style="padding-top: 8px"> <label style="padding-left: 20px;font-size: 0.9em;color: #00a2d4">下班时间:</label><span>' + l.night + '</span></div>';
            $("#approval-detail").html(html);
        });
    } else {
        doPostRequest(null, '/whale/backstage/workflow/online/myTask/findTaskByName', {
            pId: pId,
            name: '自定义'
        }, function (data) {
            var l = data.obj;
            var fullname = l.name;
            var shortName = fullname.substring(fullname.length - 1, fullname.length);
            $("#short-name").text(shortName);
            $("#long-name").text(fullname);
            html += '<div style="padding-top: 8px"> <label style="padding-left: 20px;font-size: 0.9em;color: #00a2d4">申请人:</label><span>' + l.name + '</span></div>';
            html += '<div style="padding-top: 8px"> <label style="padding-left: 20px;font-size: 0.9em;color: #00a2d4">流程名称:</label><span>' + l.wname + '</span></div>';
            html += '<div style="padding-top: 8px"> <label style="padding-left: 20px;font-size: 0.9em;color: #00a2d4">申请明细:</label><span>' + l.attach + '</span></div>';
            $("#approval-detail").html(html);
        });
    }
}