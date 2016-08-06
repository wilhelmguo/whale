$(function () {
    // JY.Dict.setSelect("selectisValid", "deviceType", 2, '全部');
    // JY.Dict.setSelect("selectisValid", "deviceType");
    // JY.Dict.setSelect("type", "deviceType", 1, '全部');
    getbaseList();
    //增加回车事件
    $("#baseForm").keydown(function (e) {
        keycode = e.which || e.keyCode;
        if (keycode == 13) {
            search();
        }
    });

    // $("#selectisValid").multiselect({
    //     header: true,
    //     height: 250,
    //     minWidth: 225,
    //     classes: '',
    //     checkAllText: '选中全部',
    //     uncheckAllText: '取消全选',
    //     noneSelectedText: '请选择',
    //     selectedText: '# 选中',
    //     selectedList: 5,
    //     show: null,
    //     hide: null,
    //     autoOpen: false,
    //     multiple: true,
    //     position: {},
    //     appendTo: "body",
    //     menuWidth: null
    // });

    $((function ($) {
        $.datepicker.regional['zh-CN'] = {
            clearText: '清除',
            clearStatus: '清除已选日期',
            closeText: '关闭',
            closeStatus: '不改变当前选择',
            prevText: '<上月',
            prevStatus: '显示上月',
            prevBigText: '<<',
            prevBigStatus: '显示上一年',
            nextText: '下月>',
            nextStatus: '显示下月',
            nextBigText: '>>',
            nextBigStatus: '显示下一年',
            currentText: '今天',
            currentStatus: '显示本月',
            monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
            monthNamesShort: ['一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二'],
            monthStatus: '选择月份',
            yearStatus: '选择年份',
            weekHeader: '周',
            weekStatus: '年内周次',
            dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
            dayNamesShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
            dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
            dayStatus: '设置 DD 为一周起始',
            dateStatus: '选择 m月 d日, DD',
            dateFormat: 'yyyy-mm-dd',
            firstDay: 1,
            initStatus: '请选择日期',
            isRTL: false
        };
        $.datepicker.setDefaults($.datepicker.regional['zh-CN']);
    })(jQuery));

    $("#endtime").datepicker({
        dateFormat: 'yy-mm-dd',
        regional: 'zh-CN',
        showOtherMonths: true,
        selectOtherMonths: false,
    });

    $("#starttime").datepicker({
        dateFormat: 'yy-mm-dd',
        regional: 'zh-CN',
        showOtherMonths: true,
        selectOtherMonths: false,
    });

    //新加
    $('#addBtn').on('click', function (e) {
        //通知浏览器不要执行与事件关联的默认动作
        e.preventDefault();
        cleanForm();
        JY.Model.edit("auDiv", "新增", function () {
            if (JY.Validate.form("auForm")) {
                var that = $(this);

                JY.Ajax.doRequest("auForm", jypath + '/backstage/WorkRecordDept/add', null, function (data) {
                    that.dialog("close");
                    JY.Model.info(data.resMsg, function () {
                        search();
                    });
                });
            }
        });
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
                JY.Ajax.doRequest(null, jypath + '/backstage/WorkRecordDept/delBatch', {chks: chks.toString()}, function (data) {
                    JY.Model.info(data.resMsg, function () {
                        search();
                    });
                });
            });
        }
    });
});
function search() {
    $("#searchBtn").trigger("click");
    // getbaseList(1);
}


function getbaseList(init) {
    if (init == 1) $("#baseForm .pageNum").val(1);
    JY.Model.loading();
    JY.Ajax.doRequest("baseForm", jypath + '/backstage/WorkRecordDept/findByPage', null, function (data) {
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
                html += "<td class='center'>" + JY.Object.notEmpty(l.department) + "</td>";
                html += "<td class='center'>" + JY.Object.notEmpty(l.employeeName) + "</td>";
                if (JY.Object.notEmpty(l.type) == 0) {
                    html += "<td class='center'>内勤</td>";
                } else {
                    html += "<td class='center'>外勤</td>";
                }
                html += "<td class='center hidden-480' >" + JY.Date.Format(l.date, "yyyy-MM-dd") + "("
                    + JY.Object.notEmpty(l.week) + ")</td>";
                html += "<td class='center'>" + JY.Object.notEmpty(l.morning) + "</td>";
                html += "<td class='center'>" + JY.Object.notEmpty(l.beforenoon) + "</td>";
                html += "<td class='center'>" + JY.Object.notEmpty(l.afternoon) + "</td>";
                html += "<td class='center'>" + JY.Object.notEmpty(l.night) + "</td>";
                // html += "<td class='center'>" + JY.Object.notEmpty(l.status) + "</td>";
                html += JY.Tags.setFunction(l.id, permitBtn);
                html += "</tr>";
            }
            $("#baseTable tbody").append(html);
            JY.Page.setPage("baseForm", "pageing", pageSize, pageNum, totalRecord, "getbaseList");
        } else {
            html += "<tr><td colspan='9' class='center'>没有相关数据</td></tr>";
            $("#baseTable tbody").append(html);
            $("#pageing ul").empty();//清空分页
        }
        JY.Model.loadingClose();
    });
}
function check(id) {
    cleanForm();
    JY.Ajax.doRequest(null, jypath + '/backstage/WorkRecordDept/find', {id: id}, function (data) {
        setForm(data);
        JY.Model.widthcheck("70%","auDiv");
    });
}
function del(id) {
    JY.Model.confirm("确认删除吗？", function () {
        JY.Ajax.doRequest(null, jypath + '/backstage/WorkRecordDept/del', {id: id}, function (data) {
            JY.Model.info(data.resMsg, function () {
                search();
            });
        });
    });
}
function edit(id) {
    cleanForm();
    JY.Ajax.doRequest(null, jypath + '/backstage/WorkRecordDept/find', {id: id}, function (data) {
        setForm(data);
        JY.Model.wideedit("70%", "auDiv", "修改", function () {
            if (JY.Validate.form("auForm")) {
                var that = $(this);
                var beforenoon = $("#auForm input[name$='beforenoon']").val();
                var afternoon = $("#auForm input[name$='afternoon']").val();
                var morning = $("#auForm input[name$='morning']").val();
                var night = $("#auForm input[name$='night']").val();
                if ((JY.Object.notNull(beforenoon) && !JY.Object.notNull(afternoon)) ||
                    (!JY.Object.notNull(beforenoon) && JY.Object.notNull(afternoon))) {
                    JY.Model.error("上午签退时间和下午签到时间必须同时设置!", function () {
                    });
                    return false;
                }
                if (beforenoon.replace(/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/, "")) {
                    JY.Model.error("上午签退时间格式有误");
                    return false;
                }
                if (afternoon.replace(/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/, "")) {
                    JY.Model.error("下午签到时间格式有误");
                    return false;
                }
                if (morning.replace(/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/, "")) {
                    JY.Model.error("上班时间格式有误");
                    return false;
                }
                if (night.replace(/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/, "")) {
                    JY.Model.error("下班时间格式有误");
                    return false;
                }

                JY.Ajax.doRequest("auForm", jypath + '/backstage/WorkRecordDept/update', null, function (data) {
                    if (data.res == 1) {
                        that.dialog("close");
                        JY.Model.info(data.resMsg, function () {
                            search();
                        });
                    } else {
                        JY.Model.error(data.resMsg);
                    }
                });
            }
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
    $("#auForm input[name$='employeeName']").val(JY.Object.notEmpty(l.employeeName));
    $("#auForm input[name$='date']").val(JY.Date.Format(l.date, "yyyy-MM-dd"));
    $("#auForm input[name$='morning']").val(JY.Object.notEmpty(l.morning));
    $("#auForm input[name$='beforenoon']").val(JY.Object.notEmpty(l.beforenoon));
    $("#auForm input[name$='afternoon']").val(JY.Object.notEmpty(l.afternoon));
    $("#auForm input[name$='night']").val(JY.Object.notEmpty(l.night));
    $("#auForm input[name$='location']").val(JY.Object.notEmpty(l.location));
    if (JY.Object.notNull(l.picture)) {
        var pictures = l.picture.split(",");
        var images = "";
        for (i = 0; i < pictures.length; i++) {
            images = images + "<img width='100px' src='" + pictures[i] + "'></td>"
        }
        $("#picture").html(images);
    }


}
