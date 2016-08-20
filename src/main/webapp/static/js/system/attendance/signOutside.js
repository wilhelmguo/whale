$(function () {
    getWorkRule();
});

function cleanForm() {
    JY.Tags.isValid("leaveFrom", "1");
    JY.Tags.cleanForm("leaveFrom");
}

function insertClose() {
    $("#patchForm").show();
    $("#applyForm").hide();
}

function patch(type) {
    parent.TabControlAppend("menuef4c0289d9b7465aacb405daa7a871ec", "补卡申请",
        "/whale/backstage/workflow/online/patch/index?menu=menuef4c0289d9b7465aacb405daa7a871ec");

    // $("#patchForm").hide();
    // $("#applyForm").show();
    // cleanForm();
    // var d = new Date();
    // $("#leaveFrom input[name$='date']").val(JY.Date.Format(d,"yyyy-MM-dd"));
    // if (type==1){
    //     $("#leaveFrom input[name$='morning']").val(JY.Date.Format(d,"hh:mm"));
    // }else if(type==2){
    //     $("#leaveFrom input[name$='beforenoon']").val(JY.Date.Format(d,"hh:mm"));
    // }else if(type==3){
    //     $("#leaveFrom input[name$='afternoon']").val(JY.Date.Format(d,"hh:mm"));
    // }else if(type==4){
    //     $("#leaveFrom input[name$='night']").val(JY.Date.Format(d,"hh:mm"));
    // }

}

function getWorkRule() {
    JY.Ajax.doRequest(null, jypath + '/backstage/workTime/find', null, function (data) {
        var l = data.obj;
        $("#morning").html(l.morning);
        if (JY.Object.notNull(l.beforenoon)) {
            $("#beforenoon").html(l.beforenoon);
        } else {
            $("#beforenoondiv").hide();
        }
        if (JY.Object.notNull(l.afternoon)) {
            $("#afternoon").html(l.afternoon);
        } else {
            $("#afternoondiv").hide();
        }
        $("#night").html(l.night);
        JY.Ajax.doRequest(null, jypath + '/backstage/workRecord/find', null, function (data) {
            var l = data.obj;
            if (JY.Object.notNull(l.morning)) {
                $("#morningtime").html(l.morning);
                $("#click1").hide();
            }
            if (JY.Object.notNull(l.beforenoon)) {
                $("#beforenoontime").html(l.beforenoon);
                $("#click2").hide();
            }
            if (JY.Object.notNull(l.afternoon)) {
                $("#afternoontime").html(l.afternoon);
                $("#click3").hide();
            }
            if (JY.Object.notNull(l.night)) {
                $("#nighttime").html(l.night);
                $("#click4").hide();
            }
        });
    });
}

function insertOrUpdate(type) {
    // e.preventDefault();
    JY.Model.confirm("确认打卡?", function () {
        JY.Ajax.doRequest(null, jypath + '/backstage/sign/insertOrUpdate', {type: type.toString()}, function (data) {
            JY.Model.info(data.resMsg, function () {
                location.reload();
            });
        });
    });
}