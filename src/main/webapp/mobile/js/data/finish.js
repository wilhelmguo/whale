/**
 * Created by gsw on 16-8-6.
 */
$(function () {
    getTolist();
});

function getTolist() {
    $.ajax({
        type: 'GET',
        url: "/whale/moblie/api/v1/workflow/finishes",
        data: null,
        dataType: 'json',
        success: function (data, textStatus) {
            if (data.res == 1) {
                var l = data.obj;
                var html = "";
                if (l) {
                    for (var i = 0; i < l.length; i++) {
                        var clickFunc = "todoTask(&apos;" + l[i].id + "&apos;,&apos;" + l[i].processinstanceid + "&apos;,&apos;" + l[i].pkey + "&apos;,&apos;" + l[i].presentationsubject + "&apos;)";
                        html += '<div class="item-wrap-workflow" onclick="' + clickFunc + '" data="1"> <div class="initiate-item-workflow"> <p class="init-title">' + l[i].presentationsubject + '</p> <p class="init-de">已审批(' + l[i].attr2 + ')</p> <p class="init-time"></p> </div> </div>';
                    }
                    $("#toList").html(html);
                }
            }
        },
        error: function () {
            return;
        },
        beforeSend: function () {
        },
        complete: function () {
        }
    });
}

function todoTask(id, pId, pkey, title) {
    window.location.href = "approvalFinish.html?id=" + id + "&pId=" + pId + "&pkey=" + pkey + "&title=" + title + "&type="+GetQueryString("type");
    // if (name == '请假流程') {
    //     leave(id, pId);
    // } else if (name == '报销流程') {
    //     claim(id, pId);
    // } else if (name == '加班流程') {
    //     overtime(id, pId);
    // } else if (name == '补卡流程') {
    //     patch(id, pId);
    // } else {
    //     usrdef(id, pId)
    // }
}

function waitBack() {
    var type = GetQueryString("type");
    if (type == 0) {
        window.location.href = "workFlow.html";
    } else {
        window.location.href = "setUp.html";
    }
}