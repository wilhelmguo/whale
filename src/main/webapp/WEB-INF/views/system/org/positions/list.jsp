<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
    <%@include file="../../common/includeBaseSet.jsp" %>
    <%@include file="../../common/includeSystemSet.jsp" %>
    <link rel="stylesheet" href="${jypath}/static/plugins/zTree/3.5/zTreeStyle.css"/>
    <script type="text/javascript" src="${jypath}/static/plugins/zTree/3.5/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="${jypath}/static/plugins/zTree/3.5/jquery.ztree.excheck-3.5.js"></script>
</head>
<body>
<div class="page-content">
    <div class="row-fluid">
        <div class="col-xs-12">
            <form id="baseForm" class="form-inline" method="POST" onsubmit="return false;">
                <div class="widget-main customBtn">
                    <input type="text" name="keyWord" placeholder="这里输入关键词" class="input-large">
                    &nbsp;&nbsp;<span id="selectisValid"><label></label>：<select data-placeholder="状态" name="isValid"
                                                                                 class="chosen-select isSelect75"></select></span>
                    &nbsp;&nbsp;
                    <button id='searchBtn' class="btn btn-warning  btn-xs" title="过滤" type="button"
                            onclick="getbaseList(1)"><i class="icon-search bigger-110 icon-only"></i></button>
                </div>
                <input type='hidden' class='pageNum' name='pageNum' value='1'/>
                <input type='hidden' class='pageSize' name='pageSize' value='5'/>
                <input type='hidden' name='orgId'/>
            </form>
            <table id="baseTable" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th style="width:3%" class="center">
                        <label><input type="checkbox" class="ace"><span class="lbl"></span></label>
                    </th>
                    <th style="width:7%" class="center hidden-480">序号</th>
                    <th style="width:10%" class="center">职务名称</th>
                    <th style="width:10%" class="center hidden-480">状态</th>
                    <th style="width:10%" class="center hidden-480">所属部门</th>
                    <th style="width:10%" class="center hidden-480">上级职务</th>
                    <th style="width:10%" class="center hidden-480">职务描述</th>
                    <th style="width:20%" class="center">操作</th>
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
            <%@include file="../../common/dialog.jsp" %>
        </div>
    </div>
</div>
<script src="${jypath}/static/js/system/org/positions.js"></script>
</body>
</html>