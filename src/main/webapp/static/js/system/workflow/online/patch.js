$(function () {
    // JY.Dict.setSelect("selectisValid", "overTimeUnit", 3);
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
    });
    loadPreOrgTree();
    loadDefaultApprover();
});
function loadDefaultApprover() {
    JY.Ajax.doGetRequest(null, jypath + '/backstage/workflow/approver/find', "", function (data) {
        var r = data.obj;
        $("#preOrg").val(r.accountId);
        $("#preOrgName").val(r.name);
    });

}
function submitApply() {
    if (JY.Validate.form("leaveFrom")) {
        var beforenoon = $("#leaveFrom input[name$='beforenoon']").val();
        var afternoon = $("#leaveFrom input[name$='afternoon']").val();
        var morning = $("#leaveFrom input[name$='morning']").val();
        var night = $("#leaveFrom input[name$='night']").val();

        if (beforenoon.replace(/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/, "")){
            JY.Model.error("上午签退时间格式有误");
            return false;
        }
        if (afternoon.replace(/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/, "")){
            JY.Model.error("下午签到时间格式有误");
            return false;
        }
        if (morning.replace(/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/, "")){
            JY.Model.error("上班时间格式有误");
            return false;
        }
        if (night.replace(/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/, "")){
            JY.Model.error("下班时间格式有误");
            return false;
        }
        JY.Model.confirm("确认提交申请吗?", function () {

            JY.Ajax.doRequest("leaveFrom", jypath + '/backstage/workflow/online/patch/start', "", function (data) {
                JY.Model.info(data.resMsg);
            });
        });
    }
}

function loadPreOrgTree() {
    JY.Ajax.doRequest(null, jypath + '/backstage/org/role/getCompanyUserPreOrgTree', null, function (data) {
        //设置
        $.fn.zTree.init($("#preOrgTree"), {
            view: {dblClickExpand: false, selectedMulti: false, nameIsHTML: true},
            data: {simpleData: {enable: true}},
            callback: {onClick: clickPreOrg}
        }, data.obj);
        //设置1
        $.fn.zTree.init($("#preOrgTree1"), {
            view: {dblClickExpand: false, selectedMulti: false, nameIsHTML: true},
            data: {simpleData: {enable: true}},
            callback: {onClick: clickPreOrg}
        }, data.obj);
    });
}

function clickPreOrg(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("preOrgTree"),
        nodes = zTree.getSelectedNodes(), v = "", n = "", o = "";
    for (var i = 0, l = nodes.length; i < l; i++) {
        v += nodes[i].name + ",";//获取name值
        n += nodes[i].id + ",";	//获取id值
        o += nodes[i].other + ",";//获取自定义值
    }
    if (v.length > 0) v = v.substring(0, v.length - 1);
    if (n.length > 0) n = n.substring(0, n.length - 1);
    if (o.length > 0) o = o.substring(0, o.length - 1);
    if (o != 'r') {
        return false;
    }

    $("#preOrgName").val(v);
    $("#preOrg").val(n);
    $("#auOrgForm input[name$='pId']").prop("value", n);
    //因为单选选择后直接关闭，如果多选请另外写关闭方法
    hidePreOrg();
}

var preisShow = false;//窗口是否显示
function showPreOrg() {
    if (preisShow) {
        hidePreOrg();
    } else {
        var obj = $("#preOrgName");
        var offpos = $("#preOrgName").position();
        $("#preOrgContent").css({left: offpos.left + "px", top: offpos.top + obj.heith + "px"}).slideDown("fast");
        preisShow = true;
    }
}

var preisShow1 = false;//窗口是否显示
function showPreOrg1() {
    if (preisShow1) {
        hidePreOrg1();
    } else {
        var obj = $("#preOrgName1");
        var offpos = $("#preOrgName1").position();
        $("#preOrgContent1").css({left: offpos.left + "px", top: offpos.top + obj.heith + "px"}).slideDown("fast");
        preisShow1 = true;
    }
}

function hidePreOrg1() {
    $("#preOrgContent1").fadeOut("fast");
    preisShow1 = false;
}

function hidePreOrg() {
    $("#preOrgContent").fadeOut("fast");
    preisShow = false;
}

function emptyPreOrg1() {
    $("#preOrg1").prop("value", "");
    $("#preOrgName1").prop("value", "");
    $("#leaveFrom input[name$='pId1']").prop("value", "0");
}

function emptyPreOrg() {
    var aid = $("#preOrg").val();
    var aName = $("#preOrgName").val();
    var length = 0;
    if (JY.Object.notNull(aid)) {
        length = aid.split(",").length;
    }
    if (length >= 2) {
        $("#preOrg").prop("value", aid.split(",")[0]);
        $("#preOrgName").prop("value", aName.split(",")[0]);
        $("#leaveFrom input[name$='pId']").prop("value", "0");
    }
}