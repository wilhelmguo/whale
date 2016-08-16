/**
 * Created by gsw on 16-8-6.
 */
$(function () {
    getUser();
});

function getUser() {
    $.ajax({
        type: 'GET',
        url: "/whale/moblie/api/v1/users/current",
        data: null,
        dataType: 'json',
        success: function (data, textStatus) {
            if (data.res == 1) {
                var fullname=data.obj.name;
                var shortName=fullname.substring(fullname.length-1,fullname.length);
                $("#fullname").text(fullname);
                $("#shortname").text(fullname);
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

function logout(){
    window.location.href="/whale/system_logout";
}

