/**
 * Created by gsw on 16-8-8.
 */

$(function () {
    getMyWorkRecord();

});

function getMyWorkRecord() {
    $.ajax({
        type: 'GET',
        url: "/whale/moblie/api/v1/signs/list",
        data: null,
        dataType: 'json',
        success: function (data, textStatus) {
            if (data.res == 1) {
                var l = data.obj
                var html = "";
                for (var i = 0; i < l.length; i++) {
                    var date = formatDateWithPatten(l[i].date, "yyyy-MM-dd");
                    var dateSimple = formatDateWithPatten(l[i].date, "MM.dd");
                    var workTime = "上班时间:" + notEmpty(l[i].morning) + "&nbsp;&nbsp;下班时间:" + notEmpty(l[i].night);
                    html += '<div class="timeline-item clearfix"><div class="timeline-info"><img alt="考勤"src="../static/images/system/user/hpic0.jpg"/> <span class="label label-info label-sm">' + dateSimple + '</span> </div> <div class="widget-box transparent"> <div class="widget-header widget-header-small"> <h5 class="smaller"> <span class="grey">' + date + '</span> </h5> <span class="widget-toolbar"> <a href="#" data-action="collapse"> <i class="icon-chevron-up"></i> </a> </span> </div> <div class="widget-body"> <div class="widget-main">' + workTime + '</div> </div> </div> </div>';
                }
                $("#timeline-items").html(html)
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

function timelineBackUp() {
    var type = GetQueryString("type");
    if (type == 1) {
        window.location.href = 'loc.html';
    } else {
        window.location.href = 'signInside.html';
    }

}