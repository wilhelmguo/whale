/**
 * Created by gsw on 16-8-7.
 */
$(function () {
    var location = GetChineseQueryString("location");
    initSign(location);

});

function signBackClick() {
    window.location.href = "loc.html";
}


function initSign(location) {
    var now = new Date();
    var nowFormat = now.Format("yyyy-MM-dd  hh:mm:ss");
    $("#signtime").text(nowFormat);
    $("#signlocation").text(location);
}

function signClick() {
    var location=$("#signlocation").text();
    var bz=$("#loc-desc").val();
    var pic="";
    var signtype=$("#signtype").val();
    $.ajax({
        type: 'GET',
        url: "/whale/moblie/api/v1/signs",
        data: {location: location, bz: bz, pic: pic,type:signtype},
        dataType: 'json',
        success: function (data, textStatus) {
            if (data.res == 1) {
                // var result = data.obj;
                alert("打卡成功!");
                window.location.href="loc.html";
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
