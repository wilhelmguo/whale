/**
 * Created by gsw on 16-8-7.
 */
function backIndex() {
    window.location.href="index.html";
}
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return unescape(r[2]);
    return null;
}

function backPre() {
    history.back(-1);
    location.reload();
}