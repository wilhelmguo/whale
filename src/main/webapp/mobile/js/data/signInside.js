/**
 * Created by gsw on 16-8-8.
 */

$(function () {
    var today = new Date();
    $("#today").text("内勤打卡  " + formatDateWithPatten(today, "yyyy-MM-dd"));
    getSignTime();
});

function insertOrUpdate(type) {
    // e.preventDefault();
    var r = confirm("确认打卡?");
    if (r == true) {
        $.ajax({
            type: 'GET',
            url: "/whale/moblie/api/v1/signs",
            data: {location: "", bz: "", pic: "",type:type,signtype:"0"},
            dataType: 'json',
            success: function (data, textStatus) {
                if (data.res == 1) {
                    // var result = data.obj;
                    alert("打卡成功!");
                    location.reload();
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
    } else {

    }

}

function getSignTime() {
    $.ajax({
        type: 'GET',
        url: "/whale/moblie/api/v1/signs/current",
        data: null,
        dataType: 'json',
        success: function (data, textStatus) {
            if (data.res == 1) {
                var result = data.obj;
                if (!isEmpty(result.morning)) {
                    $("#morning-time").text(result.morning);
                    // $("#click1").hide();
                }
                if (!isEmpty(result.night)) {
                    $("#night-time").text(result.night);
                    // $("#click2").hide();
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

function patch(type) {
    window.location.href="patch.html?type=0";
}

function timelineBackUp() {
    window.location.href = 'index.html';
}