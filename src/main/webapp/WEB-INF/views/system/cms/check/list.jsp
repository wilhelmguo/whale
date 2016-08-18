<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <%@include file="../../common/includeBaseSet.jsp" %>
    <%@include file="../../common/includeSystemSet.jsp" %>

</head>
<body>
<div class="page-content">
    <div class="page-header">
        <h1>
            公告列表
            <small>
                <i class="icon-double-angle-right"></i>
            </small>
        </h1>
    </div>
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
                            <div class="row">
                                <input type="text" name="keyWord" placeholder="这里输入标题" class="input-large">
                                &nbsp;&nbsp;
                                <button id='searchBtn' class="btn btn-warning  btn-xs" title="过滤" type="button"
                                        onclick="getbaseList(1)"><i class="icon-search bigger-110 icon-only"></i>
                                </button>
                            </div>
                            <input type='hidden' class='pageNum' name='pageNum' value='1'/>
                            <input type='hidden' class='pageSize' name='pageSize' value='5'/>
                        </form>
                    </div>
                </div>
                <div class="message-list-container">
                    <div class="message-list" id="message-list">
                        <div class="message-item message-unread">
                            <img width="80px"
                                 src="../../../../../static/apidoc/JYUI/assets/avatars/avatar.png"
                                 alt="John's Avatar" class="middle">
                            <span class="sender" title="Alex John Red Smith">云峰李</span>
																	<span class="summary">
																		<span class="text">
																			天天想的发生多亏了放假快三点了方式打开就疯狂了;按说
																		</span>
																	</span>
                            <span class="time">1:33 pm</span>
                        </div>
                    </div>
                </div>
            </div>
            <%--<table id="baseTable" class="table table-striped table-bordered table-hover">--%>
            <%--<thead>--%>
            <%--<tr>--%>
            <%--<th style="width:3%" class="center">--%>
            <%--<label><input type="checkbox" class="ace"><span class="lbl"></span></label>--%>
            <%--</th>--%>
            <%--<th style="width:5%" class="center hidden-480">序号</th>--%>
            <%--<th style="width:10%" class="center">标题</th>--%>
            <%--<th style="width:10%" class="center hidden-480">发布人</th>--%>
            <%--<th style="width:12%" class="center "><i class="icon-time bigger-110 hidden-480"></i>发布时间</th>--%>
            <%--<th style="width:12%" class="center hidden-480"><i class="icon-time bigger-110 hidden-480"></i>修改时间--%>
            <%--</th>--%>
            <%--&lt;%&ndash;<th style="width:5%"  class="center hidden-480">发布IP</th>&ndash;%&gt;--%>
            <%--<th style="width:15%" class="center hidden-480">状态</th>--%>
            <%--<th style="width:15%" class="center">操作</th>--%>
            <%--</tr>--%>
            <%--</thead>--%>
            <%--<tbody></tbody>--%>
            <%--</table>--%>
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

    <!-- #addorUpdateFrom -->
    <%@include file="form.jsp" %>
    <!-- #dialog-confirm -->
    <%@include file="../../common/dialog.jsp" %>
</div>
<script src="${jypath}/static/plugins/tabs/js/tab-control.min.js"></script>

<script src="${jypath}/static/js/system/cms/newlist.js"></script>
</body>
</html>