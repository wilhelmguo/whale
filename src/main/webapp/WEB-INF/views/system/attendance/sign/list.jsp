<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
    <%@include file="../../common/includeBaseSet.jsp" %>
    <%@include file="../../common/includeSystemSet.jsp" %>
</head>
<body>


<div class="page-content">
    <%@include file="../../common/dialog.jsp" %>
    <div class="page-header">
        <h1>
            打卡签到
            <small><i class="icon-double-angle-right"></i>&nbsp;当前时间:
                <span id="timenow" style="color: #6f3cc4"></span>
            </small>
        </h1>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <div id="timeline-1">
                <div class="row">
                    <div class="col-xs-12 col-sm-10 col-sm-offset-1">
                        <div class="timeline-container">
                            <div class="timeline-label">
<span class="label label-primary arrowed-in-right label-lg">
<b>打卡时间轴</b>
</span>
                            </div>

                            <div class="timeline-items">
                                <div class="timeline-item clearfix">
                                    <div class="timeline-info">
                                        <img alt="Susan't Avatar" src="${jypath}/static/images/system/user/hpic0.jpg"/>
                                        <span id="morningtime" class="label label-info label-sm">未打卡</span>
                                    </div>

                                    <div class="widget-box transparent">
                                        <div class="widget-header widget-header-small">
                                            <h5 class="smaller">
                                                <a href="#" class="blue">上班打卡</a>
                                                <span class="grey" id="morning">09:30</span>
                                            </h5>



<span class="widget-toolbar">
<%--<a href="#" title="更新打卡时间" onclick="insertOrUpdate(1)" >--%>
<%--<i class="icon-refresh"></i>--%>
<%--</a>--%>

<a href="#" data-action="collapse">
<i class="icon-chevron-up"></i>
</a>
</span>
                                        </div>

                                        <div class="widget-body">
                                            <div class="widget-main">
                                                <div class="space-6"></div>

                                                <div class="widget-toolbox clearfix">
                                                    <div class="pull-left">
                                                        美好的一天开始了!上班记得打卡哦!
                                                    </div>
                                                    <div class="pull-right action-buttons">
                                                        <button id="click1" class="btn btn-sm btn-primary" onclick="insertOrUpdate(1)">打卡</button>
                                                        <button class="btn btn-sm btn-primary">补卡</button>
                                                    </div>

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>


                            </div>
                            <div id="beforenoondiv" class="timeline-items">
                                <div class="timeline-item clearfix">
                                    <div class="timeline-info">
                                        <img alt="Susan't Avatar" src="${jypath}/static/images/system/user/hpic0.jpg"/>
                                        <span id="beforenoontime" class="label label-info label-sm">未打卡</span>
                                    </div>

                                    <div class="widget-box transparent">
                                        <div class="widget-header widget-header-small">
                                            <h5 class="smaller">
                                                <a href="#" class="blue">上午签退</a>
                                                <span class="grey" id="beforenoon">09:30</span>
                                            </h5>



<span class="widget-toolbar">
<%--<a href="#" title="更新打卡时间" onclick="insertOrUpdate(2)" >--%>
<%--<i class="icon-refresh"></i>--%>
<%--</a>--%>

<a href="#" data-action="collapse">
<i class="icon-chevron-up"></i>
</a>
</span>
                                        </div>

                                        <div class="widget-body">
                                            <div class="widget-main">

                                                <div class="space-6"></div>

                                                <div class="widget-toolbox clearfix">
                                                    <div class="pull-left">
                                                        中午美餐一顿!记得签退哦!
                                                    </div>
                                                    <div class="pull-right action-buttons">
                                                        <button id="click2" class="btn btn-sm btn-primary" onclick="insertOrUpdate(2)">打卡</button>
                                                        <button class="btn btn-sm btn-primary">补卡</button>
                                                    </div>

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>


                            </div>
                            <div id="afternoondiv" class="timeline-items">
                                <div class="timeline-item clearfix">
                                    <div class="timeline-info">
                                        <img alt="Susan't Avatar" src="${jypath}/static/images/system/user/hpic0.jpg"/>
                                        <span id="afternoontime" class="label label-info label-sm">未打卡</span>
                                    </div>

                                    <div class="widget-box transparent">
                                        <div class="widget-header widget-header-small">
                                            <h5 class="smaller">
                                                <a href="#" class="blue">下午签到</a>
                                                <span class="grey" id="afternoon">09:30</span>
                                            </h5>



<span class="widget-toolbar">
<%--<a href="#" title="更新打卡时间" onclick="insertOrUpdate(3)">--%>
<%--<i class="icon-refresh"></i>--%>
<%--</a>--%>

<a href="#" data-action="collapse">
<i class="icon-chevron-up"></i>
</a>
</span>
                                        </div>

                                        <div class="widget-body">
                                            <div class="widget-main">

                                                <div class="space-6"></div>

                                                <div class="widget-toolbox clearfix">
                                                    <div class="pull-left">
                                                        下午打足精神好好工作!加油!
                                                    </div>
                                                    <div class="pull-right action-buttons">
                                                        <button id="click3" class="btn btn-sm btn-primary" onclick="insertOrUpdate(3)">打卡</button>
                                                        <button class="btn btn-sm btn-primary">补卡</button>
                                                    </div>

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>


                            </div>
                            <div class="timeline-items">
                                <div class="timeline-item clearfix">
                                    <div class="timeline-info">
                                        <img alt="Susan't Avatar" src="${jypath}/static/images/system/user/hpic0.jpg"/>
                                        <span id="nighttime" class="label label-info label-sm">未打卡</span>
                                    </div>

                                    <div class="widget-box transparent">
                                        <div class="widget-header widget-header-small">
                                            <h5 class="smaller">
                                                <a href="#" class="blue">下班打卡</a>
                                                <span class="grey" id="night">09:30</span>
                                            </h5>



<span class="widget-toolbar">
<%--<a href="#" title="更新打卡时间" onclick="insertOrUpdate(4)" >--%>
<%--<i class="icon-refresh"></i>--%>
<%--</a>--%>

<a href="#" data-action="collapse">
<i class="icon-chevron-up"></i>
</a>
</span>
                                        </div>

                                        <div class="widget-body">
                                            <div class="widget-main">

                                                <div class="space-6"></div>

                                                <div class="widget-toolbox clearfix">
                                                    <div class="pull-left">
                                                        工作一天辛苦了,好好休息!
                                                    </div>
                                                    <div class="pull-right action-buttons">
                                                        <button id="click4" class="btn btn-sm btn-primary" onclick="insertOrUpdate(4)">打卡</button>
                                                        <button class="btn btn-sm btn-primary">补卡</button>
                                                    </div>

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>


                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<script src="${jypath}/static/js/system/attendance/sign.js"></script>
<script>
    function getCurDate() {
        var d = new Date();
        var week;
        switch (d.getDay()) {
            case 1:
                week = "星期一";
                break;
            case 2:
                week = "星期二";
                break;
            case 3:
                week = "星期三";
                break;
            case 4:
                week = "星期四";
                break;
            case 5:
                week = "星期五";
                break;
            case 6:
                week = "星期六";
                break;
            default:
                week = "星期天";
        }
        var years = d.getFullYear();
        var month = add_zero(d.getMonth() + 1);
        var days = add_zero(d.getDate());
        var hours = add_zero(d.getHours());
        var minutes = add_zero(d.getMinutes());
        var seconds = add_zero(d.getSeconds());
        var ndate = years + "年" + month + "月" + days + "日 " + hours + ":" + minutes + ":" + seconds + " " + week;
        timenow.innerHTML = ndate;
    }

    function add_zero(temp) {
        if (temp < 10) return "0" + temp;
        else return temp;
    }

    setInterval("getCurDate()", 100);

</script>
</body>
</html>