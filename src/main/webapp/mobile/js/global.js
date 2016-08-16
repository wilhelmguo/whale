/**
 * Created by gsw on 16-8-7.
 */
function backIndex() {
    window.location.href = "index.html";
}
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        var re = r[2];
        return unescape(r[2]);
    }
    return null;
}


function GetChineseQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        var re = r[2];
        return decodeURI(re);
    }
    return null;
}

function comVar (variables) {
    var keys = "", values = "", types = "", vars = {};
    if (variables) {
        $.each(variables, function () {
            if (keys != "") {
                keys += ",";
                values += ",";
                types += ",";
            }
            keys += this.key;
            values += this.value;
            types += this.type;
        });
    }
    vars = {keys: keys, values: values, types: types};
    return vars;
}

function formatDate(date) {
    if (date == null || date == "") {
        return "";
    }
    var d = new Date(date);

    return d.Format("yyyy-MM-dd  hh:mm:ss");
}

function formatDateWithPatten(date, patten) {
    if (date == null || date == "") {
        return "";
    }
    var d = new Date(date);
    return d.Format(patten);
}

function formSerialize(form) {
    var o = {};
    $.each(form.serializeArray(), function (index) {
        if (o[this['name']]) {
            o[this['name']] = o[this['name']] + "," + this['value'];
        } else {
            o[this['name']] = this['value'];
        }
    });
    return o;
}

function doGetRequest(form, url, param, fn) {
    var params = form || param || {};
    if (typeof form == 'string') {
        params = $.extend(param || {}, formSerialize($("#" + form)));
    }
    $.ajax({
        type: 'GET', url: url, data: params, dataType: 'json', success: function (data, textStatus) {
            if (data.res == 1) {
                if (typeof(fn) == 'function') {
                    fn.call(this, data);
                }
            } else {
                alert(data.resMsg);
            }
        }, error: function () {
            return;
        }, beforeSend: function () {
        }, complete: function () {
        }
    });
}

function doPostRequest(form, url, param, fn) {
    var params = form || param || {};
    if (typeof form == 'string') {
        params = $.extend(param || {}, formSerialize($("#" + form)));
    }
    $.ajax({
        type: 'POST', url: url, data: params, dataType: 'json', success: function (data, textStatus) {
            if (data.res == 1) {
                if (typeof(fn) == 'function') {
                    fn.call(this, data);
                }
            } else {
                alert(data.resMsg);
            }
        }, error: function () {
            return;
        }, beforeSend: function () {
        }, complete: function () {
        }
    });
}

/*
 * 对Date的扩展，将 Date 转化为指定格式的String
 *月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
 *年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
 *例子：
 *(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
 *(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
 */
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt))fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt))fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    }
    return fmt;
};

function isEmpty(obj) {
    return !isNotEmpty(obj);
}

function isNotEmpty(obj) {
    if (obj === null)return false; else if (obj === undefined)return false; else if (obj === "undefined")return false; else if (obj === "")return false; else if (obj === "[]")return false; else if (obj === "{}")return false; else return true;
}

function notEmpty(obj) {
    if (obj === null)return ""; else if (obj === undefined)return ""; else if (obj === "undefined")return ""; else if (obj === "")return ""; else if (obj === "[]")return ""; else if (obj === "{}")return ""; else return obj;
}


function backPre() {
    history.back(-1);
    location.reload();
}