$(function () {
    getSysNews();
});

function getSysNews() {
    $.ajax({
        type: 'GET',
        url: "/whale/moblie/api/v1/news",
        data: null,
        dataType: 'json',
        success: function (data, textStatus) {
            if (data.res == 1) {
                var news = data.obj;
                var html = "";
                for (var key in news) {
                    html += '<div style="text-align: center;margin-bottom: 20px"><i class="timestyle" >' + key + '</i></div>';
                    for (var i = 0; i < news[key].length; i++) {
                        var newDetail = news[key][i];
                        var click = 'onclick="getSysNewsDetail(\'' + newDetail.id + '\')"';
                        if (i == 0) {
                            html += ' <div ' + click + ' class="tip-img-wrap tip-img-div"> <img class="tip-img tip-img-k" src="img/1.jpeg"/> <p class="tip-title tip-title-p">' + newDetail.title + '</p></div>';
                        } else {
                            html += '<div ' + click + 'class="tip-img-wrap tip-img-div tip-two"> <div class="tip-right" style="background-image:url(img/1.jpeg)"> </div> <p class="tip-title tip-title-t">' + newDetail.title + '</p> </div>';
                        }
                    }

                }
                $("#newDetails").html(html);
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

function getSysNewsDetail(id) {
    window.location.href = 'noticeDetail.html?index=' + id + '&type=2';
}