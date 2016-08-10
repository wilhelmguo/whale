/**
 * Created by gsw on 16-8-9.
 */
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
    });
}

