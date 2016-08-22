$(function () {
    //下拉框
    // JY.Dict.setSelect("selectisValid", "isPublished", 2, "全部");
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
    JY.Ajax.doRequest("baseForm", jypath + '/backstage/msg/findByPage', null, function (data) {
        // $("#baseTable tbody").empty();
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
                html += '<div class="message-item message-unread">'
                // html += '<span class="sender" title="Alex John Red Smith">'+JY.Object.notEmpty(l.publisher)+'</span>'
                html += '<span class="summary">'
                html += '<span class="text" onclick="titleClick(&quot;' + l.taskinfoid + '&quot;)">'
                html += JY.Object.notEmpty(l.content)
                html += '</span>'
                html += '</span>'
                html += ' <span class="time" style="width: 150px;">' + JY.Date.Default(l.createtime) + '</span>'
                html += '</div>'
            }
            $("#message-list").html("");
            $("#message-list").append(html);
            JY.Page.setPage("baseForm", "pageing", pageSize, pageNum, totalRecord, "getbaseList");
        } else {
            // JY.Page.setPage("baseForm", "pageing", pageSize, pageNum, totalRecord, "getbaseList");
            html += "<tr><td colspan='10' class='center'>没有相关数据</td></tr>";
            $("#baseTable tbody").append(html);
            $("#pageing ul").empty();//清空分页
        }

        JY.Model.loadingClose();
    });
}

function titleClick(id) {
    JY.Ajax.doRequest(null, jypath + '/backstage/workflow/online/taskInfo/find', {"id": id}, function (data) {
        if (data.res == 1) {
            var l = data.obj;
            var width = document.documentElement.clientWidth * 0.85 + "px";
            var height = document.documentElement.clientHeight * 0.85 + "px";
            layer.open({
                type: 2,
                title: '当前节点',
                shadeClose: true,
                maxmin: true,
                area: [width, height],
                content: jypath + "/act-process-editor/diagram-viewer/index.html?processDefinitionId=" + l.processdefinitionid + "&processInstanceId=" + l.processinstanceid //iframe的url
            });
        }

    });

}

function check(id) {
    cleanForm();

    JY.Ajax.doRequest(null, jypath + '/backstage/cms/find', {id: id}, function (data) {
        $("#row-fluid").hide();
        $("#writeDiv").show();
        setDetailForm(data);
        // JY.Model.widthcheck("90%","auDiv");
    });
}

function setDetailForm(data) {
    var l = data.obj;
    $("#cmstitle").text(l.title);
    // JY.Tags.isValid("auForm",(JY.Object.notNull(l.isValid)?l.isValid:"0"));
    $("#cmsaddtime").text("发布时间:" + JY.Date.Default(l.addtime));
    $("#cmspublisher").text("发布人:" + JY.Object.notEmpty(l.publisher));
    $("#cmscontent").html(JY.Object.notEmpty(l.content));
    var covers = "封面图片:<img style='max-width: 300px' src='" + jypath + JY.Object.notEmpty(l.cover) + "'/>"
    if (JY.Object.notNull(l.cover)) {
        $("#cmscover").html(covers);
    } else {
        $("#cmscover").html("封面图片:无");
    }

}

function detailClose() {
    $("#row-fluid").show();
    $("#writeDiv").hide();
}

function insertClose() {
    $("#row-fluid").show();
    $("#auDiv").hide();
}


function cleanForm() {
    $(".photo-clip-rotateLayer").html("");
    $("#file").val("");
    $('#view').html("");
    $('#view').attr("style", "");
    JY.Tags.isValid("auForm", "1");
    JY.Tags.cleanForm("auForm");

}
function setForm(data) {
    var l = data.obj;
    $("#auForm input[name$='id']").val(l.id);
    // JY.Tags.isValid("auForm",(JY.Object.notNull(l.isValid)?l.isValid:"0"));
    $("#auForm input[name$='title']").val(JY.Object.notEmpty(l.title));
    $("#auForm input[name$='content']").val(JY.Object.notEmpty(l.content));
    $("#auForm input[name$='cover']").val(JY.Object.notEmpty(l.cover));
    ue.setContent(l.content);
    $("#auForm select[name$='status']").val(JY.Object.notEmpty(l.status));//状态

    var img = '<img width="210" src="/whale' + l.cover + '">';
    $('#view').html(img);
    // 创建缩略图

}
