/**
 * Created by gsw on 16-8-7.
 */
$(function () {
    var index = GetQueryString("index");
    var type = GetQueryString("type");
    if (type == 1) {
        getOneAd(index);
    } else {
        getOneNew(index);
    }
});

function backPres() {
    var type = GetQueryString("type");
    if (type == 1) {
        window.location.href = "index.html";
    } else {
        window.location.href = "notice.html";
    }

}

function getOneNew(index) {
    $.ajax({
        type: 'GET',
        url: "/whale/moblie/api/v1/news/find",
        data: {id: index},
        dataType: 'json',
        success: function (data, textStatus) {
            if (data.res == 1) {
                var l = data.obj;
                $("#title").text(l.title);
                var bref = "发布人:" + l.publisher + "发布日期:" + l.addtime;
                $("#publishinfo").val(l.bref);
                $("#content").html(l.content);

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

function getOneAd(index) {
    $.ajax({
        type: 'GET',
        url: "/whale/moblie/api/v1/ads/find",
        data: {id: index},
        dataType: 'json',
        success: function (data, textStatus) {
            if (data.res == 1) {
                var l = data.obj;
                $("#title").text(l.title);
                var bref = "发布人:" + l.publisher + "发布日期:" + l.addtime;
                $("#publishinfo").val(l.bref);
                $("#content").html(l.content);

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