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
                var html = '';
                for (var key in news) {
                    html += '<div style="text-align: center;margin-bottom: 20px"><i class="timestyle" >' + key + '</i></div>';
                    html += '<div class="tip-item tip-style-wx">';
                    for (var i = 0; i < news[key].length; i++) {
                        var newDetail = news[key][i];
                        var click = 'onclick="getSysNewsDetail(\'' + newDetail.id + '\')"';
                        if (i == 0) {
                            html += '<div ' + click + ' class="tip-img-wrap tip-img-div"> <img class="tip-img tip-img-k" src="/whale' + newDetail.cover + '"/> <p class="tip-title tip-title-p"> ' + newDetail.title + ' </p></div>';
                        } else {
                            var c = "/whale" + newDetail.cover;
                            html += '<div ' + click + ' class="tip-img-wrap tip-img-div tip-two"> <div class="tip-right" style="background-image:url(' + c + ')"> </div> <p class="tip-title tip-title-t"> ' + newDetail.title + ' </p> </div>';
                        }

                    }
                    html += '</div>';
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