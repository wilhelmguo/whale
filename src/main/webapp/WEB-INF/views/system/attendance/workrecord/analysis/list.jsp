<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
    <%@include file="../../../common/includeBaseSet.jsp" %>
    <%@include file="../../../common/includeSystemSet.jsp" %>

    <%--<link rel="stylesheet" type="text/css" href="${jypath}/static/css/system/jquery/jquery.multiselect.css"/>--%>
    <%--<link rel="stylesheet" type="text/css" href="${jypath}/static/css/system/jquery/assets/style.css"/>--%>
    <%--<script type="text/javascript" src="${jypath}/static/css/system/jquery/assets/jquery.multiselect.js"></script>--%>
    <%--<script type="text/javascript" src="${jypath}/static/css/system/jquery/assets/prettify.js"></script>--%>

</head>
<body>
<div class="page-content">
    <div class="row-fluid">
        <div class="col-xs-12">
            <form id="baseForm" class="form-inline" method="POST" onsubmit="return false;">

                <div class="row">
                    <div class="widget-main">
                        <input type="text" name="keyWord" placeholder="搜索员工或部门" class="input-large">
                        <label>开始日期:</label><input type="text" jyValidate="required" name="starttime" id="starttime"
                                                   class="FormElement ui-widget-content ui-corner-all"/>
                        <label>结束日期:</label><input type="text" jyValidate="required" name="endtime" id="endtime"
                                                  />
                        <%--<label>考勤状态:</label>--%>
                        <%--<select id="selectisValid" name="status" class="FormElement ui-widget-content ui-corner-all"--%>
                                <%--multiple="multiple" style="width:200px;height: 50px;">--%>
                            <%--<option value="迟到">迟到</option>--%>
                            <%--<option value="早退">早退</option>--%>
                            <%--<option value="缺卡">缺卡</option>--%>
                            <%--<option value="旷工">旷工</option>--%>
                            <%--&lt;%&ndash;<option value="6">上午签退未打卡</option>&ndash;%&gt;--%>
                            <%--&lt;%&ndash;<option value="7">下午签到未打卡</option>&ndash;%&gt;--%>
                        <%--</select>--%>
                        <%--&nbsp;&nbsp;<span id="selectisValid"><label></label>：<select data-placeholder="状态"--%>
                        <%--name="isValid"--%>
                        <%--class="chosen-select isSelect95"></select></span>--%>
                        &nbsp;&nbsp;
                        <button id='searchBtn' class="btn btn-warning  btn-xs" title="过滤" type="button"
                                onclick="getbaseList(1)"><i class="icon-search bigger-110 icon-only"></i></button>
                    </div>
                </div>
                <input type='hidden' class='pageNum' name='pageNum' value='1'/>
                <input type='hidden' class='pageSize' name='pageSize' value='5'/>
            </form>
            <table id="baseTable" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th style="width:3%" class="center">
                        <label><input type="checkbox" class="ace"><span class="lbl"></span></label>
                    </th>
                    <th style="width:5%" class="center hidden-480">序号</th>
                    <th style="width:10%" class="center">部门</th>
                    <th style="width:10%" class="center">员工</th>
                    <th style="width:10%" class="center">考勤类型</th>
                    <%--<th style="width:10%" class="center hidden-480">所属员工</th>--%>
                    <th style="width:10%" class="center"><i class="icon-time bigger-110 hidden-480"></i>考勤日期</th>
                    <th style="width:10%" class="center"><i class="icon-time bigger-110 hidden-480"></i>上班时间</th>
                    <th style="width:10%" class="center"><i class="icon-time bigger-110 hidden-480"></i>中午签退时间</th>
                    <th style="width:10%" class="center"><i class="icon-time bigger-110 hidden-480"></i>中午签到时间</th>
                    <th style="width:10%" class="center"><i class="icon-time bigger-110 hidden-480"></i>下班时间</th>
                    <%--<th style="width:10%" class="center"><i class="icon-time bigger-110 hidden-480"></i>考勤状态</th>--%>
                    <th style="width:10%" class="center">操作</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
            <div class="row">
                <div class="col-sm-4">
                    <div class="dataTables_info customBtn">
                        <c:forEach var="pbtn" items="${permitBtn}">
                            <a href="#" title="${pbtn.name}" id="${pbtn.btnId}" class="lrspace3"><i
                                    class='${pbtn.icon} bigger-220'></i></a>
                        </c:forEach>
                    </div>
                </div>
                <div class="col-sm-8">
                    <!--设置分页位置-->
                    <div id="pageing" class="dataTables_paginate paging_bootstrap">
                        <ul class="pagination"></ul>
                    </div>
                </div>
            </div>
            <!-- #addorUpdateFrom -->
            <%@include file="form.jsp" %>
            <!-- #dialog-confirm -->
            <%@include file="../../../common/dialog.jsp" %>
        </div>
    </div>
</div>
<script src="${jypath}/static/js/system/attendance/workRecordAnalysis.js"></script>
</body>
</html>