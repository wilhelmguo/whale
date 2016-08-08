/**
 * Created by gsw on 16-8-7.
 */
$(function () {
    getSignTime();
});

function getSignTime() {
    $.ajax({
        type: 'GET',
        url: "/whale/moblie/api/v1/signs/current",
        data: null,
        dataType: 'json',
        success: function (data, textStatus) {
            if (data.res == 1) {
                var result = data.obj;
                var morning = "上班时间:" + notEmpty(result.morning);
                var night = "下班时间:" + notEmpty(result.night);
                $("#morning-time").text(morning);
                $("#nigth-time").text(night);
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