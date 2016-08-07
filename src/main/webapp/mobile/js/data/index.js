/**
 * Created by gsw on 16-8-6.
 */
$(function () {

    getAds();

});

function getAds() {
    $.ajax({
        type: 'GET',
        url: "/whale/moblie/api/v1/ads",
        data: null,
        dataType: 'json',
        success: function (data, textStatus) {
            if (data.res == 1) {
                var results = data.obj.results;
                var html = "";
                for (var i = 0; i < results.length; i++) {
                    var clickFunc = "onclick=clickFunction('" + results[i].id + "')";
                    html += '<div ' + clickFunc + ' class="swiper-slide s-ad"><img class="swiper-slide" src="/whale' + results[i].cover + '" /></div>';
                }

                $("#roolingPic").html(html);
                //滑动框
                var swiper = new Swiper('.swiper-container', {
                    pagination: '.swiper-pagination',
                    paginationClickable: true,
                    autoplay: 2000,
                    loop: true,
                    autoplayDisableOnInteraction: false
                });
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

function clickFunction(id) {
    window.location.href = 'noticeDetail.html?index=' + id + '&type=1';
}