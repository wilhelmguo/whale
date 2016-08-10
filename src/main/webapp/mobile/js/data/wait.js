/**
 * Created by gsw on 16-8-6.
 */
$(function () {
    getTolist();
});

function getTolist() {
    $.ajax({
        type: 'GET',
        url: "/whale/moblie/api/v1/workflow/todos",
        data: null,
        dataType: 'json',
        success: function (data, textStatus) {
            if (data.res == 1) {
                var l = data.obj;
                var html = "";
                for (var i = 0; i < l.length; i++) {
                    var clickFunc = "todoTask(&apos;" + l[i].id + "&apos;,&apos;" + l[i].processInstanceId + "&apos;,&apos;" + l[i].processName + "&apos;,&apos;" + l[i].presentationSubject + "&apos;)";
                    html += '<div class="item-wrap-workflow" data="1" onclick="' + clickFunc + '"> <div class="initiate-item-workflow"> <p class="init-title">' + l[i].presentationSubject + '</p> <p class="init-de">待审批</p> <p class="init-time"></p> </div> </div>';
                }
                $("#toList").html(html);
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

function todoTask(id, pId, name, title) {
    window.location.href = "approval.html?id=" + id + "&pId=" + pId + "&name=" + name + "&title=" + title;
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
