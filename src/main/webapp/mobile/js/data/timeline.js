/**
 * Created by gsw on 16-8-8.
 */

function timelineBackUp() {
    var type = GetQueryString("type");
    if (type == 1) {
        window.location.href = 'loc.html';
    } else {
        window.location.href = 'officeSign.html';
    }

}