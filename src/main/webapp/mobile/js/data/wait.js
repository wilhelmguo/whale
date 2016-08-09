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
                    html += '<div class="item-wrap-workflow" data="1"> <div class="initiate-item-workflow"> <p class="init-title">' + l[i].presentationSubject + '</p> <p class="init-de">待审批</p> <p class="init-time"></p> </div> </div>';
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
