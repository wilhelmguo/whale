/**
 * Created by gsw on 16-8-9.
 */
$(function () {
    Zepto('#picktime1-1,#picktime1-2').mdatetimer({
        mode: 4, //时间选择器模式：1：年月日，2：年月日时分（24小时），3：年月日时分（12小时），4：年月日时分秒。默认：1
        format: 2, //时间格式化方式：1：2015年06月10日 17时30分46秒，2：2015-05-10  17:30:46。默认：2
        years: [2016, 2017,2018], //年份数组
        nowbtn: false, //是否显示现在按钮
        onOk: function () {
            console.log(this);
        },
        onCancel: function () {

        },
    });
    loadDefaultApprover();
});
function loadDefaultApprover() {
    doGetRequest(null, '/whale/backstage/workflow/approver/find', "", function (data) {
        var r = data.obj;
        $(".list-manage").html("");
        $(".list-manage").append(
            "<div class='peo-manage' data-value='false'>" +
            "<p>" + r.name + "</p>" +
            "<p class='name-manage hide'>" + r.accountId + "</p>" +
            "</div>");

    });
}

function loadAllApprover() {
    doGetRequest(null, '/whale/backstage/workflow/approver/findAllApprover', {"key": "overtime"}, function (data) {
        if (data.res) {
            $(".list-manage").html("");
            var r = data.obj;
            for (var i = 0; i < r.length; i++) {
                $(".list-manage").append(
                    "<div class='peo-manage' data-value='false'>" +
                    "<p>" + r[i].name + "</p>" +
                    "<p class='name-manage hide'>" + r[i].accountId + "</p>" +
                    "</div>");
            }
        } else {
            alert(date.resMsg);
        }

    });

}
function changeEvent(obj) {
    var day = $(obj).val();
    if (isNaN(day)) {
        alert("请假天数必须为数字");
    } else {
        if (day > 3) {
            loadAllApprover();
        } else {
            loadDefaultApprover();
        }
    }
}

function submitClick() {
    var starttime = $("#auForm input[name$='starttime']").val();
    var endtime = $("#auForm input[name$='endtime']").val();
    if (starttime > endtime) {
        alert("开始时间不能大于结束时间!");
        return false;
    }
    var timeSub = (new  Date(endtime)).getTime() - (new  Date(starttime)).getTime();
    var duration = $("#auForm input[name$='duration']").val();
    var iDays = parseInt(Math.floor(timeSub) / 1000 / 60 / 60 / 24);
    if (iDays != duration) {
        alert("时间区间与天数不符,请检查!");
        return false;
    }


    var approver = "";
    $("p.name-manage").each(function () {
        approver += $(this).text() + ",";
    });
    var lastIndex = approver.lastIndexOf(',');
    if (lastIndex > -1) {
        approver = approver.substring(0, lastIndex) + approver.substring(lastIndex + 1, approver.length);
    }
    if (approver == "") {
        alert("审批人不能为空!");
        return false;
    }
    doPostRequest("auForm", "/whale/backstage/workflow/online/overtime/start", {approver: approver}, function (data) {
        alert(data.resMsg);
        window.location.href="workFlow.html";
    });
}

