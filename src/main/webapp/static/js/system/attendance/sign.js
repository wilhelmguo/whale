$(function () {
    getWorkRule();
});

function getWorkRule() {
    JY.Ajax.doRequest(null, jypath + '/backstage/workTime/find', null, function (data) {
        var l = data.obj;
        $("#morning").html(l.morning);
        if (JY.Object.notNull(l.beforenoon)) {
            $("#beforenoon").html(l.beforenoon);
        } else {
            $("#beforenoondiv").hide();
        }
        if (JY.Object.notNull(l.afternoon)) {
            $("#afternoon").html(l.afternoon);
        } else {
            $("#afternoondiv").hide();
        }
        $("#night").html(l.night);
        JY.Ajax.doRequest(null, jypath + '/backstage/workRecord/find', null, function (data) {
            var l = data.obj;
            if (JY.Object.notNull(l.morning)) {
                $("#morningtime").html(l.morning);
                $("#click1").hide();
            }
            if (JY.Object.notNull(l.beforenoon)) {
                $("#beforenoontime").html(l.beforenoon);
                $("#click2").hide();
            }
            if (JY.Object.notNull(l.afternoon)) {
                $("#afternoontime").html(l.afternoon);
                $("#click3").hide();
            }
            if (JY.Object.notNull(l.night)) {
                $("#nighttime").html(l.night);
                $("#click4").hide();
            }
        });
    });
}

function insertOrUpdate(type) {
    // e.preventDefault();
    JY.Model.confirm("确认打卡?", function () {
        JY.Ajax.doRequest(null, jypath + '/backstage/sign/insertOrUpdate', {type: type.toString()}, function (data) {
            JY.Model.info(data.resMsg, function () {
                location.reload();
            });
        });
    });
}