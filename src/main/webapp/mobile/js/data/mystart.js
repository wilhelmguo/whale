/**
 * Created by gsw on 16-8-6.
 */
$(function () {
    getTolist();
});

function getTolist() {
    $.ajax({
        type: 'GET',
        url: "/whale/moblie/api/v1/workflow/mystarts",
        data: null,
        dataType: 'json',
        success: function (data, textStatus) {
            if (data.res == 1) {
                var l = data.obj;
                var html = "";
                for (var i = 0; i < l.length; i++) {
                    var clickFunc = "todoTask(&apos;" + l[i].id + "&apos;,&apos;" + l[i].processinstanceid + "&apos;,&apos;" + l[i].attr1 + "&apos;,&apos;" + l[i].presentationsubject + "&apos;)";
                    html += '<div class="item-wrap-workflow" onclick="' + clickFunc + '" data="1"> <div class="initiate-item-workflow"> <p class="init-title">' + l[i].presentationsubject + '</p> <p class="init-de">(' + l[i].attr3 + ')</p> <p class="init-time"></p> </div> </div>';
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
    window.location.href = "approvalMystart.html?id=" + id + "&pId=" + pId + "&name=" + name + "&title=" + title;

}
