/**
 * Created by gsw on 16-8-9.
 */
$(function () {
    loadDefaultApprover();
});
function loadDefaultApprover() {
    doGetRequest(null, '/whale/backstage/workflow/approver/find', "", function (data) {
        var r = data.obj;
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
    doPostRequest("auForm", "/whale/backstage/workflow/online/claim/start", {approver: approver}, function (data) {
        alert(data.resMsg);
        window.location.href="workFlow.html";
    });
}

