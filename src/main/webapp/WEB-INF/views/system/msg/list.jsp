<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <%@include file="../common/includeBaseSet.jsp" %>
    <%@include file="../common/includeSystemSet.jsp" %>

</head>
<body>
<div class="page-content">
    <%--<div class="page-header">--%>
        <%--<h1>--%>
            <%--消息列表--%>
            <%--<small>--%>
                <%--<i class="icon-double-angle-right"></i>--%>
            <%--</small>--%>
        <%--</h1>--%>
    <%--</div>--%>
    <div id="row-fluid" class="row-fluid">
        <div class="col-xs-12">

            <div class="message-container">
                <div id="id-message-list-navbar"
                     class="message-navbar align-center clearfix">
                    <div class="message-bar">
                        <div class="message-infobar" id="id-message-infobar">
                            <span class="blue bigger-150"></span>
                            <span class="grey bigger-110"></span>
                        </div>


                    </div>


                    <div class="messagebar-item-left">
                        <form id="baseForm" class="form-inline" method="POST" onsubmit="return false;">
                            <div style="margin-left: 10px" class="row">
                                <h1 style="color:#2679b5;font-size:24px;margin: 0 8px;font-weight: lighter ">
                                消息列表
                                <small>
                                <i class="icon-double-angle-right"></i>
                                </small>
                                </h1>
                                <%--<input type="text" name="keyWord" placeholder="这里输入标题" class="input-large">--%>
                                <%--&nbsp;&nbsp;--%>
                                <%--<button id='searchBtn' class="btn btn-warning  btn-xs" title="过滤" type="button"--%>
                                        <%--onclick="getbaseList(1)"><i class="icon-search bigger-110 icon-only"></i>--%>
                                <%--</button>--%>
                            </div>
                            <input type='hidden' class='pageNum' name='pageNum' value='1'/>
                            <input type='hidden' class='pageSize' name='pageSize' value='5'/>
                        </form>
                    </div>
                </div>
                <div class="message-list-container">
                    <div class="message-list" id="message-list">

                    </div>
                </div>
            </div>
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

        </div>

    </div>
    <div id="writeDiv" hidden class="tab-pane">
        <div class="message-container">
            <div class="message-navbar align-center clearfix">
                <div class="message-bar">

                </div>
                <div class="message-item-bar">
                    <div class="messagebar-item-left">
                        <a onclick="detailClose();return false;" class="btn-back-message-list no-hover-underline"
                           href="#">
                            <i class="icon-arrow-left blue bigger-110 middle"></i>
                            <b class="middle bigger-110">关闭</b>
                        </a>
                    </div>


                </div>
            </div>
            <div class="message-list-container">
                <div class="form-group">
                    <div class="col-sm-11 col-xs-12">
                        <%--<input type="title" maxlength="5000" class="col-xs-12" name="toList">--%>
                        <h3 style="align-content: center" id="cmstitle">标题</h3>
                        <p>
                            <span style="color:gray;" id="cmsaddtime">发布时间</span>
                            <span style="color:gray;" id="cmspublisher">发布人</span>
                        </p>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-11 col-xs-12">
                        <p>
                            <span style="color:gray;" id="cmscover">封面</span>
                        </p>
                    </div>
                </div>

                <div class="hr hr-18 dotted"></div>
                <div class="form-group">
                    <div class="col-sm-11 col-xs-12">
                        <p id="cmscontent">内容</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- #addorUpdateFrom -->
    <%@include file="form.jsp" %>
    <!-- #dialog-confirm -->
    <%@include file="../common/dialog.jsp" %>
</div>
<script src="${jypath}/static/plugins/tabs/js/tab-control.min.js"></script>

<script src="${jypath}/static/js/system/msg/msg.js"></script>
</body>
</html>