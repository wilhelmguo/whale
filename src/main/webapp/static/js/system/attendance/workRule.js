$(function () {
    JY.Dict.setSelect("selectisValid,selectYear,selectType", "year,year,workRule", 3, '全部');
    // JY.Dict.setSelect("selectYear", "isValid", 1, '全部');
    getbaseList();
    //增加回车事件
    $("#baseForm").keydown(function (e) {
        keycode = e.which || e.keyCode;
        if (keycode == 13) {
            search();
        }
    });

    $((function($){
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
            monthNames: ['一月','二月','三月','四月','五月','六月', '七月','八月','九月','十月','十一月','十二月'],
            monthNamesShort: ['一','二','三','四','五','六', '七','八','九','十','十一','十二'],
            monthStatus: '选择月份',
            yearStatus: '选择年份',
            weekHeader: '周',
            weekStatus: '年内周次',
            dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
            dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],
            dayNamesMin: ['日','一','二','三','四','五','六'],
            dayStatus: '设置 DD 为一周起始',
            dateStatus: '选择 m月 d日, DD',
            dateFormat: 'yyyy-mm-dd',
            firstDay: 1,
            initStatus: '请选择日期',
            isRTL: false};
        $.datepicker.setDefaults($.datepicker.regional['zh-CN']);
    })(jQuery));


    $("#datepicker").datepicker({
        dateFormat: 'yy-mm-dd',
        regional:'zh-CN',
        showOtherMonths: true,
        selectOtherMonths: false,
        //isRTL:true,



        /*
         changeMonth: true,
         changeYear: true,

         showButtonPanel: true,
         beforeShow: function() {
         //change button colors
         var datepicker = $(this).datepicker( "widget" );
         setTimeout(function(){
         var buttons = datepicker.find('.ui-datepicker-buttonpane')
         .find('button');
         buttons.eq(0).addClass('btn btn-xs');
         buttons.eq(1).addClass('btn btn-xs btn-success');
         buttons.wrapInner('<span class="bigger-110" />');
         }, 0);
         }
         */
    });
    //新加
    $('#addBtn').on('click', function (e) {
        //通知浏览器不要执行与事件关联的默认动作
        e.preventDefault();
        cleanForm();
        JY.Model.edit("auDiv", "新增", function () {
            if (JY.Validate.form("auForm")) {
                var that = $(this);
                JY.Ajax.doRequest("auForm", jypath + '/backstage/workRule/add', null, function (data) {
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
                JY.Ajax.doRequest(null, jypath + '/backstage/workRule/delBatch', {chks: chks.toString()}, function (data) {
                    JY.Model.info(data.resMsg, function () {
                        search();
                    });
                });
            });
        }
    });
});
function search() {
    // $("#searchBtn").trigger("click");
    getbaseList(1);
}


function getbaseList(init) {
    if (init == 1) $("#baseForm .pageNum").val(1);
    JY.Model.loading();
    JY.Ajax.doRequest("baseForm", jypath + '/backstage/workRule/findByPage', null, function (data) {
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
                html += "<td class='center'>" + JY.Object.notEmpty(l.year) + "</td>";
                html += "<td class='center hidden-480' >" + JY.Date.Format(l.workdate,"yyyy-MM-dd") + "</td>";
                if (l.status==0){
                    html += "<td class='center'>节假日</td>";
                }else{
                    html += "<td class='center'>调休</td>";
                }
                html += "<td class='center hidden-480'>" + JY.Object.notEmpty(l.name) + "</td>";
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
    JY.Ajax.doRequest(null, jypath + '/backstage/workRule/find', {id: id}, function (data) {
        setForm(data);
        JY.Model.check("auDiv");
    });
}
function del(id) {
    JY.Model.confirm("确认删除吗？", function () {
        JY.Ajax.doRequest(null, jypath + '/backstage/workRule/del', {id: id}, function (data) {
            JY.Model.info(data.resMsg, function () {
                search();
            });
        });
    });
}
function edit(id) {
    cleanForm();
    JY.Ajax.doRequest(null, jypath + '/backstage/workRule/find', {id: id}, function (data) {
        setForm(data);

        JY.Model.edit("auDiv", "修改", function () {
            if (JY.Validate.form("auForm")) {
                var that = $(this);

                JY.Ajax.doRequest("auForm", jypath + '/backstage/workRule/update', null, function (data) {
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
    $("#auForm select[name$='year']").val(JY.Object.notEmpty(l.year));
    $("#auForm input[name$='workdate']").val(JY.Date.Format(l.workdate,"yyyy-MM-dd"));
    $("#auForm select[name$='status']").val(JY.Object.notEmpty(l.status));
    $("#auForm input[name$='name']").val(JY.Object.notEmpty(l.name));
}
