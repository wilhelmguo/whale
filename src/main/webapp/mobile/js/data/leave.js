/**
 * Created by gsw on 16-8-9.
 */
$(function () {
    Zepto('#picktime1-1,#picktime1-2').mdatetimer({
        mode: 4, //时间选择器模式：1：年月日，2：年月日时分（24小时），3：年月日时分（12小时），4：年月日时分秒。默认：1
        format: 2, //时间格式化方式：1：2015年06月10日 17时30分46秒，2：2015-05-10  17:30:46。默认：2
        years: [2015, 2016, 2017, 2018], //年份数组
        nowbtn: false, //是否显示现在按钮
        onOk: function () {
            console.log(this);
        },
        onCancel: function () {

        },
    });
    loadDefaultApprover();
});

function loadAllApprover() {
    doGetRequest(null, '/whale/backstage/workflow/approver/findAllApprover', {"key": "leave"}, function (data) {
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
            JY.Model.error(date.resMsg);
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
function submitClick() {

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
    doPostRequest("auForm", "/whale/backstage/workflow/online/apply/start", {approver: approver}, function (data) {
        alert(data.resMsg);
    });
}

