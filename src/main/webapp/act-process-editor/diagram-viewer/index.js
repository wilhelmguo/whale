/**
 * Created by gsw on 16-8-20.
 */
$(function () {
    getbaseList();
});

function getbaseList() {
    doPostRequest("baseForm", '/whale/backstage/workflow/online/taskInfo/findAll', {"processinstanceid": GetQueryString("processInstanceId")}, function (data) {
        $("#baseTable tbody").empty();
        var results = data.obj;
        var html = "";
        if (results != null && results.length > 0) {
            for (var i = 0; i < results.length; i++) {
                var l = results[i];
                html += "<tr>";
                html += "<td class='center hidden-480'>" + (i + 1) + "</td>";
                html += "<td class='center'>" + notEmpty(l.name) + "</td>";

                html += "<td class='center hidden-480' >" + formatDate(l.createtime) + "</td>";
                html += "<td class='center hidden-480' >" + notEmpty(l.description) + "</td>";

                html += "<td class='center'>" + notEmpty(l.attr2) + "</td>";
                // html += JY.Tags.setFunction(l.id, permitBtn);
                html += "</tr>";
            }
            $("#baseTable tbody").append(html);
        } else {
            html += "<tr><td colspan='9' class='center'>没有相关数据</td></tr>";
            $("#baseTable tbody").append(html);
            $("#pageing ul").empty();//清空分页
        }
        // JY.Model.loadingClose();
    });
}