/**
 * Created by gsw on 16-8-9.
 */
function submitClick() {
    var that = $(this);
    that.tips({side: 1, msg: "必要字段！", bg: '#FF2D2D', time: 1});
    // doPostRequest("auForm", "/whale/backstage/workflow/online/apply/start", null, function (data) {
    //     alert("dd");
    // });
}

