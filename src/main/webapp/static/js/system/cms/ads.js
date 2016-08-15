$(function () {

    // 当domReady的时候开始初始化

    //下拉框
    JY.Dict.setSelect("selectisValid", "isPublished", 2, "全部");
    getbaseList();
    //增加回车事件
    $("#baseForm").keydown(function (e) {
        keycode = e.which || e.keyCode;
        if (keycode == 13) {
            search();
        }
    });


    ue = UE.getEditor('editor');
    //新加
    $('#addBtn').on('click', function (e) {
        //通知浏览器不要执行与事件关联的默认动作
        e.preventDefault();
        UE.getEditor('editor').setContent("");
        $("#row-fluid").hide();
        $("#auDiv").show();
        cleanForm();

    });
    //批量删除
    $('#delBatchBtn').on('click', function (e) {
        //通知浏览器不要执行与事件关联的默认动作
        e.preventDefault();
        var chks = [];
        $('#baseTable input[name="ids"]:checked').each(function () {
            chks.push($(this).val());
        });
        if (chks.length == 0) {
            JY.Model.info("您没有选择任何内容!");
        } else {
            JY.Model.confirm("确认要删除选中的数据吗?", function () {
                JY.Ajax.doRequest(null, jypath + '/backstage/ads/delBatch', {chks: chks.toString()}, function (data) {
                    JY.Model.info(data.resMsg, function () {
                        search();
                    });
                });
            });
        }
    });

    $("#clipArea").photoClip({
        width: 350,
        height: 200,
        file: "#file",
        view: "#view",
        ok: "#clipBtn",
        outputType: "image/png",
        loadStart: function () {
            console.log("照片读取中");
        },
        loadComplete: function () {
            console.log("照片读取完成");
        },
        clipFinish: function (data) {
            data = data.split(',')[1];
            data = window.atob(data);
            var ia = new Uint8Array(data.length);
            for (var i = 0; i < data.length; i++) {
                ia[i] = data.charCodeAt(i);
            }
            var blob = new Blob([ia], {type: "image/png"});
            var fd = new FormData();
            fd.append('file', blob);
            $.ajax({
                url: "/whale/backstage/tool/webuploader/uploadPicBase64",
                type: "POST",
                data: fd,
                processData: false,
                contentType: false,
                success: function (res) {
                    var json = eval('(' + res + ')');
                    JY.Model.info(json.resMsg);
                    $("#cover").val(json.saveUrl);

                }
            });
        }
    });
});


function add() {
    if (JY.Validate.form("auForm")) {
        // var that = $(this);
        var titile = $("#auForm input[name$='title']");
        if (!JY.Object.notNull(titile)) {
            titile.tips({side: 1, msg: "请填写标题", bg: '#FF2D2D', time: 1});
            titile.focus();
            return false;
        }
        var ueContent = ue.getContent();
        if (!JY.Object.notNull(ueContent)) {
            titile.tips({side: 1, msg: "请输入内容", bg: '#FF2D2D', time: 1});
            titile.focus();
            return false;
        } else {
            $("#auForm input[name$='content']").val(ueContent);
        }

        var id = $("#auForm input[name$='id']");
        if (JY.Object.notNull(id.val())) {
            JY.Ajax.doRequest("auForm", jypath + '/backstage/ads/update', null, function (data) {
                $("#auDiv").hide();
                $("#row-fluid").show();
                if (data.res == 1) {
                    // that.dialog("close");
                    JY.Model.info(data.resMsg, function () {
                        search();
                    });
                } else {
                    JY.Model.error(data.resMsg, function () {
                        search();
                    });
                }
            });
        } else {
            JY.Ajax.doRequest("auForm", jypath + '/backstage/ads/add', null, function (data) {
                // that.dialog("close");
                insertClose();
                JY.Model.info(data.resMsg, function () {
                    search();
                });
            });
        }
        // alert(id.val());

    }
}

function edit(id) {
    cleanForm();
    // location.reload();
    JY.Ajax.doRequest(null, jypath + '/backstage/ads/find', {id: id}, function (data) {
        $("#row-fluid").hide();
        $("#auDiv").show();
        setForm(data);
    });
}

function search() {
    $("#searchBtn").trigger("click");
}

function getbaseList(init) {
    if (init == 1)$("#baseForm .pageNum").val(1);
    JY.Model.loading();
    JY.Ajax.doRequest("baseForm", jypath + '/backstage/ads/findByPage', null, function (data) {
        $("#baseTable tbody").empty();
        var obj = data.obj;
        var list = obj.list;
        var results = list.results;
        var permitBtn = obj.permitBtn;
        var pageNum = list.pageNum, pageSize = list.pageSize, totalRecord = list.totalRecord;
        var html = "";
        if (results != null && results.length > 0) {
            var leng = (pageNum - 1) * pageSize;//计算序号
            for (var i = 0; i < results.length; i++) {
                var l = results[i];
                html += "<tr>";
                html += "<td class='center'><label> <input type='checkbox' name='ids' value='" + l.id + "' class='ace' /> <span class='lbl'></span></label></td>";
                html += "<td class='center hidden-480'>" + (i + leng + 1) + "</td>";
                html += "<td class='center'>" + JY.Object.notEmpty(l.title) + "</td>";
                html += "<td class='center hidden-480' >" + JY.Object.notEmpty(l.publisher) + "</td>";
                html += "<td class='center '>" + JY.Date.Default(l.addtime) + "</td>";
                html += "<td class='center '>" + JY.Date.Default(l.uptime) + "</td>";
                // html += "<td class='center hidden-480'>" + JY.Object.notEmpty(l.pip) + "</td>";
                if (l.status == 1) html += "<td class='center hidden-480'><span class='label label-sm label-success'>已发布</span></td>";
                else             html += "<td class='center hidden-480'><span class='label label-sm arrowed-in'>未发布</span></td>";
                // html+="<td class='center hidden-480'>"+JY.Date.Default(l.loginLog.loginTime)+"</td>";
                // html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.loginLog.loginIP)+"</td>";
                html += JY.Tags.setFunction(l.id, permitBtn);
                html += "</tr>";
            }
            $("#baseTable tbody").append(html);
            JY.Page.setPage("baseForm", "pageing", pageSize, pageNum, totalRecord, "getbaseList");
        } else {
            html += "<tr><td colspan='10' class='center'>没有相关数据</td></tr>";
            $("#baseTable tbody").append(html);
            $("#pageing ul").empty();//清空分页
        }

        JY.Model.loadingClose();
    });
}
function check(id) {
    cleanForm();

    JY.Ajax.doRequest(null, jypath + '/backstage/ads/find', {id: id}, function (data) {
        $("#row-fluid").hide();
        $("#writeDiv").show();
        setDetailForm(data);
        // JY.Model.widthcheck("90%","auDiv");
    });
}

function setDetailForm(data) {
    var l = data.obj;
    $("#cmstitle").text(l.title);
    // JY.Tags.isValid("auForm",(JY.Object.notNull(l.isValid)?l.isValid:"0"));
    $("#cmsaddtime").text("发布时间:" + JY.Date.Default(l.addtime));
    $("#cmspublisher").text("发布人:" + JY.Object.notEmpty(l.publisher));
    var covers = "封面图片:<img style='max-width: 300px' src='" + jypath + JY.Object.notEmpty(l.cover) + "'/>"
    if (JY.Object.notNull(l.cover)) {
        $("#cmscover").html(covers);
    } else {
        $("#cmscover").html("封面图片:无");
    }

    $("#cmscontent").html(JY.Object.notEmpty(l.content));

}

function detailClose() {
    $("#row-fluid").show();
    $("#writeDiv").hide();
}

function insertClose() {
    $("#row-fluid").show();
    $("#auDiv").hide();
}

function del(id) {
    JY.Model.confirm("确认删除吗？", function () {
        JY.Ajax.doRequest(null, jypath + '/backstage/ads/del', {id: id}, function (data) {
            JY.Model.info(data.resMsg, function () {
                search();
            });
        });
    });
}


function cleanForm() {
    // $("#clipArea").html("");
    $(".photo-clip-rotateLayer").html("");
    $("#file").val("");
    $('#view').html("");
    $('#view').attr("style", "");
    JY.Tags.isValid("auForm", "1");
    JY.Tags.cleanForm("auForm");

}
function setForm(data) {
    var l = data.obj;
    $("#auForm input[name$='id']").val(l.id);
    // JY.Tags.isValid("auForm",(JY.Object.notNull(l.isValid)?l.isValid:"0"));
    $("#auForm input[name$='title']").val(JY.Object.notEmpty(l.title));
    $("#auForm input[name$='content']").val(JY.Object.notEmpty(l.content));
    ue.setContent(l.content);
    $("#auForm input[name$='cover']").val(JY.Object.notEmpty(l.cover));

    $("#auForm select[name$='status']").val(JY.Object.notEmpty(l.status));//状态
    var img = '<img width="210" src="/whale' + l.cover + '">';
    $('#view').html(img);
    // 创建缩略图


}
