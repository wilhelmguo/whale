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
        cleanForm();
        UE.getEditor('editor').setContent("");
        $("#row-fluid").hide();
        $("#auDiv").show();
        initPic();

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

});

function initPic() {
    var $ = jQuery, $list = $('#fileList'),
    // 优化retina, 在retina下这个值是2
        ratio = window.devicePixelRatio || 1,
    // 缩略图大小
        thumbnailWidth = 100 * ratio,
        thumbnailHeight = 100 * ratio,
    // Web Uploader实例
        uploader;
    // 初始化Web Uploader
    uploader = WebUploader.create({
        allowMagnify: true,
        fileNumLimit: 1,
        // 自动上传。
        auto: true,
        // swf文件路径
        swf: jypath + '/static/plugins/webuploader/js/Uploader.swf',
        // 文件接收服务端。
        server: jypath + '/backstage/tool/webuploader/uploadPic',
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#filePicker',
        // 只允许选择文件，可选。
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/*'
        },
        //不压缩文件
        compress: null
    });

    // 当有文件添加进来的时候
    uploader.on('fileQueued', function (file) {
        var $li = $('<div id="' + file.id + '" class="file-item thumbnail">' + '<img></div>'),
            $img = $li.find('img');
        $list.html($li);
        // 创建缩略图
        uploader.makeThumb(file, function (error, src) {
            if (error) {
                $img.replaceWith('<span>不能预览</span>');
                return;
            }
            $img.attr('src', src);
        }, thumbnailWidth, thumbnailHeight);
    });
    // 文件上传过程中创建进度条实时显示。
    uploader.on('uploadProgress', function (file, percentage) {
        var $li = $('#' + file.id), $percent = $li.find('.progress span');
        // 避免重复创建
        if (!$percent.length)$percent = $('<p class="progress"><span></span></p>').appendTo($li).find('span');
        $percent.css('width', percentage * 100 + '%');
    });
    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader.on('uploadSuccess', function (file, json) {

        if ("1" == json.res) {
            $('#' + file.id).addClass('upload-state-done');
            var cover = $("#cover").val();
            if (JY.Object.notNull(cover)) {
                cover += "," + json.saveUrl;
            } else {
                cover = json.saveUrl;
                // JY.Model.info(json.resMsg);
            }
            $("#cover").val(cover);
            // $("#filePicker").hide();

        } else {
            JY.Model.error(json.resMsg);
            var $li = $('#' + file.id), $error = $li.find('div.error');
            // 避免重复创建
            if (!$error.length)$error = $('<div class="error"></div>').appendTo($li);
            $error.text('上传失败');
        }
    });
    // 文件上传失败，现实上传出错。
    uploader.on('uploadError', function (file) {
        var $li = $('#' + file.id), $error = $li.find('div.error');
        // 避免重复创建
        if (!$error.length)$error = $('<div class="error"></div>').appendTo($li);
        $error.text('上传失败');
    });
    // 完成上传完了，成功或者失败，先删除进度条。
    uploader.on('uploadComplete', function (file) {
        $('#' + file.id).find('.progress').remove();
    });
}

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
    JY.Ajax.doRequest(null, jypath + '/backstage/ads/find', {id: id}, function (data) {
        $("#row-fluid").hide();
        $("#auDiv").show();
        setForm(data);
        initPic();
        // loadRoleTree();

        // JY.Model.wideedit("800", "auDiv", "修改", function () {
        //
        // if (JY.Validate.form("auForm")) {
        //     var that = $(this);
        //
        //     // var titile=$("#auForm input[name$='title']");
        //     // if (!JY.Object.notNull(titile)){
        //     // 	titile.tips({side:1,msg:"请填写标题",bg:'#FF2D2D',time:1});
        //     // 	titile.focus();
        //     // 	return false;
        //     // }
        //     // if (!JY.Object.notNull(ue.getContent())){
        //     // 	titile.tips({side:1,msg:"请输入内容",bg:'#FF2D2D',time:1});
        //     // 	titile.focus();
        //     // 	return false;
        //     // }else {
        //     // 	$("#auForm input[name$='content']").val(ue.getContent());
        //     // }
        //
        //     JY.Ajax.doRequest("auForm", jypath + '/backstage/ads/update', null, function (data) {
        //         if (data.res == 1) {
        //             that.dialog("close");
        //             JY.Model.info(data.resMsg, function () {
        //                 search();
        //             });
        //         } else {
        //             JY.Model.error(data.resMsg);
        //         }
        //     });
        // }
    });
    // });
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
                // html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.pip)+"</td>";
                // if(l.status==1) html+="<td class='center hidden-480'><span class='label label-sm label-success'>已发布</span></td>";
                // else             html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>未发布</span></td>";
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
    if (JY.Object.notNull(l.cover)){
        $("#cmscover").html(covers);
    }else {
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
    $("#auForm select[name$='status']").val(JY.Object.notEmpty(l.status));//状态

}
